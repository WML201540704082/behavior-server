package com.lnsoft.device.api.feign;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.api.ResultCode;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.DateUtil;
import com.lnsoft.device.api.asset.entity.DeviceInventory;
import com.lnsoft.device.api.asset.service.IDeviceInventoryService;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.i6000.service.II6000Service;
import com.lnsoft.device.api.res.constants.Constants;
import com.lnsoft.device.entity.ApproveRecord;
import com.lnsoft.device.entity.LogOpt;
import com.lnsoft.device.api.res.service.IApproveRecordService;
import com.lnsoft.device.api.res.service.ILogOptService;
import com.lnsoft.device.entity.DeviceReturned;
import com.lnsoft.device.entity.DeviceReturnedDetail;
import com.lnsoft.device.entity.SafeaccessSwitche;
import com.lnsoft.device.entity.SafeaccessUserAccess;
import com.lnsoft.device.api.safeaccess.mapper.SafeaccessIppoolMapper;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessSwitcheService;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessUserAccessService;
import com.lnsoft.device.api.warehouse.dto.SwitcherDeviceListDTO;
import com.lnsoft.device.api.warehouse.entity.*;
import com.lnsoft.common.enums.hussar.DeviceReturnedBpmNodeEnum;
import com.lnsoft.common.enums.device.WorkOrderTypeEnum;
import com.lnsoft.device.api.warehouse.mapper.DeviceOperationDetailMapper;
import com.lnsoft.device.api.warehouse.mapper.DeviceOperationMapper;
import com.lnsoft.device.api.warehouse.service.*;
import com.lnsoft.common.enums.hussar.OptTypeEnum;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.feign.IDeviceReturnClient;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.props.CmdbDictProperties;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.utils.OrderNumberUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@ApiIgnore
@RestController
public class DeviceReturnClient implements IDeviceReturnClient {

	@Resource
	private DeviceOperationMapper deviceOperationMapper;
	@Resource
	private DeviceOperationDetailMapper deviceOperationDetailMapper;
	@Resource
	private CmdbCientityProperties modelProperties;
	@Resource
	private IDeviceReturnedService deviceReturnedService;
	@Resource
	private IDeviceReturnedDetailService deviceReturnedDetailService;
	@Resource
	private IDSwitcherSyncService idSwitcherSyncService;
	@Resource
	private ISafeaccessSwitcheService safeAccessSwitchesService;
	@Resource
	private ISafeaccessUserAccessService userAccessService;
	@Resource
	private SafeaccessIppoolMapper safeaccessIppoolMapper;
	@Resource
	private OrderNumberUtil orderNumberUtil;
	@Resource
	private IDeviceOutboundService deviceOutboundService;
	@Resource
	private IWarehouseService warehouseService;
	@Resource
	private ILogOptService logOptService;
	@Resource
	private IApproveRecordService approveRecordService;
	@Resource
	private IDeviceInventoryService deviceInventoryService;
	@Resource
	private CmdbDictProperties cmdbDictProperties;
	@Resource
	private ICmdbService iCmdbService;
	@Resource
	private II6000Service i6000Service;

	@PostMapping(API_PREFIX + "/device/return")
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<Integer> deviceReturn() throws Exception {

		// 获取退运时间在昨天的设备
		LocalDate localDate = LocalDate.now().minusDays(1);
		List<DeviceOperationDetail> deviceOperationDetailList = deviceOperationDetailMapper.selectList(new LambdaQueryWrapper<DeviceOperationDetail>()
			.eq(DeviceOperationDetail::getTemporaryType, 0)
			.eq(DeviceOperationDetail::getTemporaryEndTime, localDate)
		);
		if (CollectionUtils.isNotEmpty(deviceOperationDetailList)) {
			List<String> operationDetailIdList = deviceOperationDetailList.stream().map(DeviceOperationDetail::getOperationId).distinct().collect(Collectors.toList());
			List<DeviceOperation> deviceOperationList = deviceOperationMapper.selectList(new LambdaQueryWrapper<DeviceOperation>()
				.in(DeviceOperation::getId, operationDetailIdList));
			Map<String, List<DeviceOperation>> operationGroupByRegion = deviceOperationList.stream().collect(Collectors.groupingBy(DeviceOperation::getRegionCode));
			// 根据地市分别执行，匹配地市数据
			List<String> switchesTypeList = modelProperties.getModelIdList(Constants.SWITCHES_TYPE);
			List<String> deviceTypeList = modelProperties.getModelIdList(Constants.DEVICE_SAFE_ACCESS_TYPE);
			// 获取投运单对应的出库单，为后面的设备归还仓库进行赋值
			List<DeviceOutbound> deviceOutboundList = deviceOutboundService.list(new LambdaQueryWrapper<DeviceOutbound>()
				.in(DeviceOutbound::getOperationNo, deviceOperationList.stream().map(DeviceOperation::getOperationNo).distinct().collect(Collectors.toList())));
			Map<String, List<SwitcherDeviceListDTO>> switcherDeviceMap = new HashMap<>();
			List<DeviceSdnUserAccess> deviceSdnUserAccessList = new ArrayList<>();
			for (Map.Entry<String, List<DeviceOperation>> entry : operationGroupByRegion.entrySet()) {
				Map<Long, Map<String, Object>> oldHashMap = new HashMap<>();
				Map<String, Map<String, Object>> i6000OldHashMap = new HashMap<>();
				String regionCode = entry.getKey();
				List<DeviceInventory> deviceInventoryList = new ArrayList<>();
				List<DeviceInventory> inventoryList = new ArrayList<>();
				// 获取区域下面的工单信息
				List<DeviceOperation> deviceOperationListByRegion = entry.getValue();
				List<String> deviceOperationIdList = deviceOperationListByRegion.stream().map(DeviceOperation::getId).collect(Collectors.toList());
				// 获取所有的仓库信息，为后面的设备归还仓库位置进行赋值
				List<Warehouse> warehouseList = warehouseService.list(new LambdaQueryWrapper<Warehouse>().eq(Warehouse::getRegionCode, regionCode)
					.in(Warehouse::getUuid, deviceOutboundList.stream().map(DeviceOutbound::getWarehouse).distinct().collect(Collectors.toList())));
				// 获取区域下面的工单设备信息
				List<DeviceOperationDetail> deviceOperationDetailReturnList = deviceOperationDetailList.stream().filter(deviceOperationDetail ->
					deviceOperationIdList.contains(deviceOperationDetail.getOperationId())).collect(Collectors.toList());
				// 筛选出交换机设备
				List<DeviceOperationDetail> switchesDeviceList = deviceOperationDetailReturnList.stream().filter(deviceOperationDetail ->
					switchesTypeList.contains(deviceOperationDetail.getDeviceType())).collect(Collectors.toList());
				// 筛选出终端设备和用户入网数据（因为是删除操作，因此，暂不过滤网络交换机是接入层类型）
				List<DeviceOperationDetail> safeAccessDeviceList = deviceOperationDetailReturnList.stream().filter(deviceOperationDetail ->
					modelProperties.getT105().equals(deviceOperationDetail.getDeviceCategory()) || deviceTypeList.contains(deviceOperationDetail.getDeviceType())).collect(Collectors.toList());
				// 进行交换机的状态更新
				if (CollectionUtils.isNotEmpty(switchesDeviceList)) {
					safeAccessSwitchesService.update(new LambdaUpdateWrapper<SafeaccessSwitche>().set(SafeaccessSwitche::getSwState, "1")
						.in(SafeaccessSwitche::getDeviceId, switchesDeviceList.stream().map(DeviceOperationDetail::getDeviceId).collect(Collectors.toList())));
				}
				// 用户入网退网处理
				if (CollectionUtils.isNotEmpty(safeAccessDeviceList)) {
					userAccessService.remove(new LambdaQueryWrapper<SafeaccessUserAccess>()
						.in(SafeaccessUserAccess::getDeviceId, safeAccessDeviceList.stream().map(DeviceOperationDetail::getDeviceId).collect(Collectors.toList())));
					// IP地址池进行释放处理
					String updateSurface = Constants.IP_POOL + regionCode.substring(0, 4);
					Map<String, List<DeviceOperationDetail>> collect = safeAccessDeviceList.stream().filter(deviceOperationDetail -> StringUtils.isNotBlank(deviceOperationDetail.getDeviceSubnet()))
						.collect(Collectors.groupingBy(DeviceOperationDetail::getDeviceSubnet));
					for (Map.Entry<String, List<DeviceOperationDetail>> poolEntry : collect.entrySet()) {
						List<String> deviceIpList = poolEntry.getValue().stream().map(DeviceOperationDetail::getDeviceIp).collect(Collectors.toList());
						safeaccessIppoolMapper.updateBatchIpPool(updateSurface, deviceIpList, null, "0", poolEntry.getKey());
					}
				}
				// 区域下的设备根据领用部门进行发起退运单
				Map<String, List<DeviceOperationDetail>> deviceOperationGroupByDept = deviceOperationDetailReturnList
					.stream().collect(Collectors.groupingBy(DeviceOperationDetail::getReceiveDutyDept));
				for (Map.Entry<String, List<DeviceOperationDetail>> deptEntry : deviceOperationGroupByDept.entrySet()) {
					List<DeviceOperationDetail> deviceOperationDetailsList = deptEntry.getValue();
					DeviceOperationDetail deviceOperationDetail = deviceOperationDetailsList.get(0);
					DeviceReturned deviceReturned = new DeviceReturned();
					deviceReturned.setFilingNo(orderNumberUtil.generateNumberByRegionCode(WorkOrderTypeEnum.TYD, regionCode));
					deviceReturned.setApplyUnit(deviceOperationDetail.getReceiveUnit());
					deviceReturned.setApplyUnitName(deviceOperationDetail.getReceiveUnitName());
					deviceReturned.setAcceptUser(null);
					deviceReturned.setAcceptUserName("自动退运");
					deviceReturned.setApplyDept(deviceOperationDetail.getReceiveDutyDept());
					deviceReturned.setApplyDeptName(deviceOperationDetail.getReceiveDutyDeptName());
					deviceReturned.setAcceptPhone(null);
					deviceReturned.setAcceptTime(DateUtil.formatDateTime(new Date()));
					deviceReturned.setReturnReason("临时使用设备进行设备退运");
					deviceReturned.setDeviceReturnNum(String.valueOf(deviceOperationDetailsList.size()));
					deviceReturned.setIsAllReturned("1");
					deviceReturned.setOperationId(null);
					deviceReturned.setProcessStatus(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_FINISH.getNode());
					deviceReturned.setRegionCode(regionCode);
					deviceReturned.setCreateUser(null);
					deviceReturned.setCreateTime(new Date());
					deviceReturned.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
					deviceReturned.setIsAuto("0");
					deviceReturnedService.save(deviceReturned);
					List<DeviceReturnedDetail> deviceReturnedDetailList = new ArrayList<>();
					deviceOperationDetailsList.forEach(item -> {
						// 2025-02-11 根据IP和MAC增加查询用户入网表里面的认证用户(approveu_user)
						SafeaccessUserAccess safeaccessUserAccess = new SafeaccessUserAccess();
						safeaccessUserAccess.setIpAddress(item.getDeviceIp());
						safeaccessUserAccess.setMacAddress(item.getDeviceMac());
						QueryWrapper<SafeaccessUserAccess> queryWrapper = Condition.getQueryWrapper(safeaccessUserAccess);
						SafeaccessUserAccess safeaccessUserAccess1 = userAccessService.getOne(queryWrapper);
						if (Objects.isNull(safeaccessUserAccess1)) {
							throw new RuntimeException("自动退网时未查询到用户入网信息, 联系管理员处理.");
						}
						if (modelProperties.getT105().equals(item.getDeviceCategory())) {
							deviceSdnUserAccessList.add(DeviceSdnUserAccess.builder()
								.sbbm(item.getDeviceCode())
								.authUser(safeaccessUserAccess1.getApproveuUser())
								.syncSign("D").readState("0").dataFrom("0")
								.region(regionCode.length() > 4 ? regionCode.substring(0, 4) : regionCode)
								.build());
						}
						DeviceReturnedDetail deviceReturnedDetail = new DeviceReturnedDetail();
						deviceReturnedDetail.setReturnedId(deviceReturned.getId());
						deviceReturnedDetail.setDeviceId(Long.parseLong(item.getDeviceId()));
						deviceReturnedDetail.setCiId(Long.parseLong(item.getDeviceCid()));
						deviceReturnedDetail.setUuid(item.getDeviceUuid());
						deviceReturnedDetail.setDeviceCode(item.getDeviceCode());
						deviceReturnedDetail.setDeviceName(item.getDeviceName());
						deviceReturnedDetail.setDeviceCategory(item.getDeviceCategory());
						deviceReturnedDetail.setDeviceType(item.getDeviceType());
						deviceReturnedDetail.setDeviceSource(item.getDeviceSource());
						deviceReturnedDetail.setDeviceStatus(modelProperties.getReturnWarehouse());
						deviceReturnedDetail.setDeviceIp(item.getDeviceIp());
						deviceReturnedDetail.setSubnetId(item.getDeviceSubnet());
						deviceReturnedDetail.setReceivingPerson(item.getUserName());
						deviceReturnedDetail.setReceivingTel(item.getUserPhone());
						// 定时任务退运归还仓库至出库选择仓库
						List<DeviceOperation> operationList = deviceOperationList.stream().filter(deviceOperation -> item.getOperationId().equals(deviceOperation.getId()))
							.collect(Collectors.toList());
						if (CollectionUtils.isNotEmpty(operationList)) {
							List<DeviceOutbound> outboundList = deviceOutboundList.stream().filter(deviceOutbound -> deviceOutbound.getOperationNo().equals(operationList.get(0).getOperationNo()))
								.collect(Collectors.toList());
							if (CollectionUtils.isNotEmpty(outboundList)) {
								DeviceOutbound deviceOutbound = outboundList.get(0);
								deviceReturnedDetail.setInWarehouseId(deviceOutbound.getWarehouse());
								deviceReturnedDetail.setInWarehouse(deviceOutbound.getWarehouseName());
								item.setReturnWarehouse(deviceOutbound.getWarehouse());
								item.setReturnWarehouseName(deviceOutbound.getWarehouseName());
								List<Warehouse> warehouses = warehouseList.stream().filter(warehouse -> warehouse.getUuid().equals(deviceOutbound.getWarehouse())).collect(Collectors.toList());
								if (CollectionUtils.isNotEmpty(outboundList)) {
									deviceReturnedDetail.setInWarehouseCode(warehouses.get(0).getWarehouseId());
									deviceReturnedDetail.setAddress(warehouses.get(0).getAddress());
									item.setReturnAddress(warehouses.get(0).getAddress());
								}
							}
						}
						deviceReturnedDetail.setDeviceMac(item.getDeviceMac());
						deviceReturnedDetail.setSwIp(item.getSwitchesIp());
						deviceReturnedDetail.setSwPass(item.getSwitchesPassword());
						deviceReturnedDetail.setIsReturned("0");
						deviceReturnedDetail.setCreateDept(null);
						deviceReturnedDetail.setCreateUser(null);
						deviceReturnedDetail.setCreateTime(new Date());
						deviceReturnedDetail.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
						deviceReturnedDetailList.add(deviceReturnedDetail);
						if (StringUtils.isNotBlank(deviceReturnedDetail.getInWarehouseId())) {
							deviceInventoryList.add(DeviceInventory.builder()
								.regionCode(regionCode).cropId(Long.parseLong(item.getReceiveUnit()))
								.deviceCategory(item.getDeviceCategory()).deviceCategoryName(item.getDeviceCategoryName())
								.deviceType(item.getDeviceType()).deviceTypeName(item.getDeviceTypeName())
								.warehouse(deviceReturnedDetail.getInWarehouseId()).warehouseName(deviceReturnedDetail.getInWarehouse())
								.inventoryNum(1)
								.build());
						}
					});
					deviceReturnedDetailService.saveBatch(deviceReturnedDetailList);
					// 仓库库存归还 根据仓库和设备类型进行分组
					Map<String, Map<String, List<DeviceInventory>>> deviceInventoryMap = deviceInventoryList.stream()
						.collect(Collectors.groupingBy(DeviceInventory::getWarehouse, Collectors.groupingBy(DeviceInventory::getDeviceType)));
					for (Map.Entry<String, Map<String, List<DeviceInventory>>> warehouse : deviceInventoryMap.entrySet()) {
						Map<String, List<DeviceInventory>> warehouseDeviceTypeMap = warehouse.getValue();
						for (Map.Entry<String, List<DeviceInventory>> warehouseDeviceType : warehouseDeviceTypeMap.entrySet()) {
							DeviceInventory deviceInventory = warehouseDeviceType.getValue().get(0);
							int inventoryNum = warehouseDeviceType.getValue().size();
							inventoryList.add(DeviceInventory.builder()
								.regionCode(regionCode).cropId(deviceInventory.getCropId())
								.deviceCategory(deviceInventory.getDeviceCategory()).deviceCategoryName(deviceInventory.getDeviceCategoryName())
								.deviceType(deviceInventory.getDeviceType()).deviceTypeName(deviceInventory.getDeviceTypeName())
								.warehouse(deviceInventory.getWarehouse()).warehouseName(deviceInventory.getWarehouseName())
								.inventoryNum(inventoryNum)
								.build());
						}
					}
					// 增加操作记录
					LogOpt createLogOpt = LogOpt.builder().logId(deviceReturned.getId()).logData(deviceReturned.toString()).params(deviceReturned.toString()).optRole("--")
						.optType(OptTypeEnum.DEVICE_RETURNED.getCode())
						.title(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_APPLY.getNode())).optName("系统").build();
					createLogOpt.setStatus(0);
					logOptService.commonLogOpt(createLogOpt);
					// 记录审核流程
					ApproveRecord approveRecord = ApproveRecord.builder().filingNo(deviceReturned.getId())
						.optType(OptTypeEnum.DEVICE_RETURNED.getCode())
						.nodeId(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_APPLY.getNode())
						.nodeName(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_APPLY.getNode()))
						.optTitle(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_APPLY.getNode()))
						.optOpinion(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_APPLY.getNode()))
						.optName("系统").optRole("--").build();
					approveRecord.setStatus(1);
					approveRecordService.commonRecord(approveRecord);
					// 增加归档记录
					LogOpt logOpt = LogOpt.builder().logId(deviceReturned.getId()).logData(deviceReturned.toString()).params(deviceReturned.toString()).optRole("--")
						.optType(OptTypeEnum.DEVICE_RETURNED.getCode())
						.title(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_FINISH.getNode())).optName("系统").build();
					logOpt.setStatus(0);
					logOptService.commonLogOpt(logOpt);
					// 记录审核流程
					ApproveRecord record = ApproveRecord.builder().filingNo(deviceReturned.getId())
						.optType(OptTypeEnum.DEVICE_RETURNED.getCode())
						.nodeId(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_FINISH.getNode())
						.nodeName(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_FINISH.getNode()))
						.optTitle(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_FINISH.getNode()))
						.optOpinion(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_FINISH.getNode()))
						.optName("系统").optRole("--").build();
					record.setStatus(1);
					approveRecordService.commonRecord(record);
				}
				List<DeviceInventory> deviceInventoryListByRegion = new ArrayList<>();
				// 以旧换新归还仓库
				for (DeviceInventory deviceInventory : inventoryList) {
					DeviceInventory inventory = deviceInventoryService.getOne(new LambdaQueryWrapper<DeviceInventory>()
						.eq(DeviceInventory::getDeviceCategory, deviceInventory.getDeviceCategory())
						.eq(DeviceInventory::getDeviceType, deviceInventory.getDeviceType())
						.eq(DeviceInventory::getWarehouse, deviceInventory.getWarehouse())
						.eq(DeviceInventory::getRegionCode, regionCode)
						.eq(DeviceInventory::getCropId, deviceInventory.getCropId())
						.last(" FOR UPDATE"));
					if (Objects.isNull(inventory.getId())) {
						deviceInventory.setVersion(0L);
						deviceInventory.setCreateTime(new Date());
						deviceInventory.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
						deviceInventoryListByRegion.add(deviceInventory);
					} else {
						inventory.setInventoryNum(inventory.getInventoryNum() + deviceInventory.getInventoryNum());
						inventory.setVersion(inventory.getVersion());
						deviceInventoryListByRegion.add(inventory);
					}
				}
				List<DeviceInventory> insertDeviceInventory = deviceInventoryListByRegion.stream().filter(deviceInventory -> Objects.isNull(deviceInventory.getId())).collect(Collectors.toList());
				List<DeviceInventory> updateDeviceInventory = deviceInventoryListByRegion.stream().filter(deviceInventory -> Objects.nonNull(deviceInventory.getId())).collect(Collectors.toList());
				if (CollectionUtils.isNotEmpty(insertDeviceInventory)) {
					deviceInventoryService.saveBatch(insertDeviceInventory);
				}
				if (CollectionUtils.isNotEmpty(updateDeviceInventory)) {
					deviceInventoryService.batchUpdateInventory(updateDeviceInventory);
				}
				// 组装数据推送服务数据
				List<SwitcherDeviceListDTO> switcherDeviceListDTOList = new ArrayList<>();
				deviceOperationDetailReturnList.forEach(item -> {
					if (modelProperties.getT105().equals(item.getDeviceCategory()) || (switchesTypeList.contains(item.getDeviceType())
						&& modelProperties.getSwitchesType().equals(item.getNetworkDeviceType()))) {
						switcherDeviceListDTOList.add(SwitcherDeviceListDTO.builder()
								.deviceCategory(item.getDeviceCategory())
								.deviceType(item.getDeviceType())
								.deviceIp(item.getDeviceIp())
								.deviceMac(item.getDeviceMac())
								.deviceSubnet(item.getDeviceSubnet())
								.switchesIp(item.getSwitchesIp())
								.switchesPassword(item.getSwitchesPassword())
								.deviceCode(item.getDeviceCode())
								.is802(Constants.NO_AUTHENTICATION.equals(item.getIs802()) ? "0" : Constants.I802_AUTHENTICATION.equals(item.getIs802()) ? "1" : Constants.MAC_AUTHENTICATION.equals(item.getIs802()) ? "2" : "3")
								.build());
					}
				});
				if (CollectionUtils.isNotEmpty(switcherDeviceListDTOList)) {
					switcherDeviceMap.put(regionCode, switcherDeviceListDTOList);
				}
				//  更新CMDB和I6000
				Map<String, String> erpI6000MapByCiId = cmdbDictProperties.getErpI6000MapByCiId(cmdbDictProperties.getDeviceType());
				// 修改 资产台账
				deviceOperationDetailList.forEach(deviceOperationDetail -> {
					String deviceType = deviceOperationDetail.getDeviceType();
					String deviceCiTypeId = erpI6000MapByCiId.get(deviceType);
					HashMap<String, Object> deviceMap = new HashMap<>();
					deviceMap.put(CmdbAttrConstant.OPRT_DEPT, "");
					deviceMap.put(CmdbAttrConstant.DEVICE_USE_DEPT, "");
					deviceMap.put(CmdbAttrConstant.OPERATION_CHAGE_ACCOUNT, "");
					deviceMap.put(CmdbAttrConstant.COMPUTER_ROOM, "");
					deviceMap.put(CmdbAttrConstant.COMPUTER_ROOM_CODE, "");
					deviceMap.put(CmdbAttrConstant.CABINET_CODE, "");
					deviceMap.put(CmdbAttrConstant.CABINET, "");
					deviceMap.put(CmdbAttrConstant.IP, "");
					deviceMap.put(CmdbAttrConstant.DEVICE_STATUS_CODE, modelProperties.getReturnWarehouse());
					deviceMap.put(CmdbAttrConstant.DEVICE_STATUS, Constants.IN_WAREHOUSE);
					deviceMap.put(CmdbAttrConstant.IN_WAREHOUSE_CODE, deviceOperationDetail.getReturnWarehouse());
					deviceMap.put(CmdbAttrConstant.IN_WAREHOUSE, deviceOperationDetail.getReturnWarehouseName());
					deviceMap.put(CmdbAttrConstant.WAREHOUSE_LOCATION, deviceOperationDetail.getReturnAddress());
					deviceMap.put(CmdbAttrConstant.SUBNET_ID, "");
					deviceMap.put(CmdbAttrConstant.SUBNET_NAME, "");
					deviceMap.put(CmdbAttrConstant.USER, "");
					deviceMap.put(CmdbAttrConstant.DEVICE_USER_TEAM, "");
					deviceMap.put(CmdbAttrConstant.USER_TEL, "");
					deviceMap.put(CmdbAttrConstant.DEVICE_USER_ID_CARD, "");
					deviceMap.put(CmdbAttrConstant.USER_EMAIL, "");
					deviceMap.put(CmdbAttrConstant.RECEIVING_GROUP, "");
					deviceMap.put(CmdbAttrConstant.RECEIVING_PHONE_NUMBER, "");
					deviceMap.put(CmdbAttrConstant.RECEIVE_PERSON_UNIFIED_ACC, "");
					deviceMap.put(CmdbAttrConstant.RECEIVE_UNIT, "");
					deviceMap.put(CmdbAttrConstant.RECEIVE_DEPT, "");
					deviceMap.put(CmdbAttrConstant.RECEIVE_DEPT_CODE, "");
					deviceMap.put(CmdbAttrConstant.RECEIVE_UNIT_CODE, "");
					deviceMap.put(CmdbAttrConstant.RECEIVING_PERSON, "");
					deviceMap.put(CmdbAttrConstant.RECEIVING_TEL, "");
					deviceMap.put(CmdbAttrConstant.RECEIVING_ID_CARD, "");
					deviceMap.put(CmdbAttrConstant.RECEIVING_DATE, "");
					deviceMap.put(CmdbAttrConstant.MANAGE_USERS, "");
					deviceMap.put(CmdbAttrConstant.MANAGE_PASSWORD, "");
					deviceMap.put(CmdbAttrConstant.SWITCH_PASSWORD, "");
					deviceMap.put(CmdbAttrConstant.SNMP_READ_STRING, "");
					deviceMap.put(CmdbAttrConstant.SNMP_WRITE_STRING, "");
					deviceMap.put(CmdbAttrConstant.SNMP_VERSION, "");
					deviceMap.put(CmdbAttrConstant.NETWORK_ACCESS_METHOD, "");
					deviceMap.put(CmdbAttrConstant.WORK_VLAN, "");
					deviceMap.put(CmdbAttrConstant.CONFIG_PASSWORD, "");
					deviceMap.put(CmdbAttrConstant.DEVICE_HEIGHT_BEGIN, "");
					deviceMap.put(CmdbAttrConstant.DEVICE_HEIGHT_END, "");
					deviceMap.put(CmdbAttrConstant.CI_ID, deviceOperationDetail.getDeviceCid());
					deviceMap.put(CmdbAttrConstant.UUID, deviceOperationDetail.getDeviceUuid());
					oldHashMap.put(Long.parseLong(deviceOperationDetail.getDeviceId()), deviceMap);
					if (StringUtils.isNotBlank(deviceCiTypeId)) {
						deviceMap.put(CmdbAttrConstant.CITYPE_ID, deviceCiTypeId);
						deviceMap.put(CmdbAttrConstant.REGION_CODE, regionCode);
						i6000OldHashMap.put(deviceOperationDetail.getDeviceUuid(), deviceMap);
					}
				});
				//保存cmdb
				iCmdbService.cientityBatchupdate(oldHashMap, TransactionActionType.UPDATE);
				// if (i6000OldHashMap.size() > 0) {
				// 	i6000Service.i6000Batchupdate(i6000OldHashMap);
				// }
			}
			// 更新数据推送服务
			for (Map.Entry<String, List<SwitcherDeviceListDTO>> entry : switcherDeviceMap.entrySet()) {
				idSwitcherSyncService.insertDSwitcherSync(entry.getValue(), entry.getKey(), "1");
			}
			if (CollectionUtils.isNotEmpty(deviceSdnUserAccessList)) {
				idSwitcherSyncService.deviceSdnUserAccess(deviceSdnUserAccessList);
			}
		}
		return R.data(ResultCode.SUCCESS.getCode());
	}
}
