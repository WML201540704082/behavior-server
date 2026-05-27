/**
 .
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lnsoft.device.api.warehouse.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lnsoft.cmdb.feign.ICmdbClient;
import com.lnsoft.common.cache.CacheNames;
import com.lnsoft.common.enums.device.WorkOrderTypeEnum;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.api.ResultCode;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.BeanUtil;
import com.lnsoft.core.tool.utils.CollectionUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.annotation.TripleApiLogA;
import com.lnsoft.device.api.asset.service.IProjectManagerDetailService;
import com.lnsoft.device.api.erp.entity.ErpTransEqunr;
import com.lnsoft.device.api.erp.entity.ErpTransEqunrItem;
import com.lnsoft.device.api.erp.response.ErpTransEqunrResp;
import com.lnsoft.device.api.erp.service.IErpService;
import com.lnsoft.device.api.res.constants.Constants;
import com.lnsoft.device.api.res.dto.HussarBpmCreateDTO;
import com.lnsoft.device.api.res.dto.HussarBpmDTO;
import com.lnsoft.device.entity.ApproveRecord;
import com.lnsoft.device.entity.LogOpt;
import com.lnsoft.device.api.res.service.IApproveRecordService;
import com.lnsoft.device.api.res.service.IHussarBpmService;
import com.lnsoft.device.api.res.service.ILogOptService;
import com.lnsoft.device.api.warehouse.dto.DeviceRecordDTO;
import com.lnsoft.device.api.warehouse.dto.ErpDeviceDetailDTO;
import com.lnsoft.device.api.warehouse.dto.ErpDeviceOrderDTO;
import com.lnsoft.device.api.warehouse.entity.DeviceRecord;
import com.lnsoft.common.enums.hussar.DeviceRecordBpmNodeEnum;
import com.lnsoft.common.enums.hussar.DeviceRecordEnum;
import com.lnsoft.common.enums.hussar.HussarBpmTypeEnum;
import com.lnsoft.device.api.warehouse.mapper.DeviceRecordMapper;
import com.lnsoft.device.api.warehouse.service.IDeviceRecordListService;
import com.lnsoft.device.api.warehouse.service.IDeviceRecordService;
import com.lnsoft.device.api.warehouse.service.IDeviceTransferService;
import com.lnsoft.device.api.warehouse.vo.DeviceRecordVO;
import com.lnsoft.device.constant.CommonConstant;
import com.lnsoft.device.constant.DictConstant;
import com.lnsoft.device.entity.DeviceRecordList;
import com.lnsoft.device.entity.ProjectManagerDetail;
import com.lnsoft.device.eums.ErpOperationEnum;
import com.lnsoft.common.enums.hussar.OptTypeEnum;
import com.lnsoft.device.eums.TripleApiLogValueEnum;
import com.lnsoft.device.eums.TripleTypeEnum;
import com.lnsoft.device.props.CheckProperties;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.props.CmdbDictProperties;
import com.lnsoft.device.utils.OrderNumberUtil;
import com.lnsoft.hussar.bpm.domain.dto.HussarCompleteParam;
import com.lnsoft.hussar.bpm.domain.vo.HussarAssignVo;
import com.lnsoft.hussar.bpm.domain.vo.HussarComplateVo;
import com.lnsoft.hussar.bpm.domain.vo.HussarCreateVo;
import com.lnsoft.hussar.bpm.domain.vo.HussarTaskVo;
import com.lnsoft.hussar.bpm.feign.IHussarBpmClient;
import com.lnsoft.system.entity.Role;
import com.lnsoft.system.feign.IDictClient;
import com.lnsoft.system.feign.ISysClient;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 设备建档 服务实现类
 *
 * @author Idevelop
 * @since 2024-02-07
 */
@Service
public class DeviceRecordServiceImpl extends BaseServiceImpl<DeviceRecordMapper, DeviceRecord> implements IDeviceRecordService {

	@Resource
	private IProjectManagerDetailService projectManagerDetailService;
	@Resource
	private IDictClient dictClient;
	@Resource
	private OrderNumberUtil orderNumberUtil;
	@Resource
	private IDeviceRecordListService deviceRecordListService;
	@Resource
	private IApproveRecordService approveRecordService;
	@Resource
	private ILogOptService logOptService;
	@Resource
	private IHussarBpmService hussarBpmService;
	@Resource
	private IHussarBpmClient hussarBpmClient;
	@Resource
	private ISysClient sysClient;
	@Resource
	private IDeviceTransferService deviceTransferService;
	@Resource
	private IErpService erpService;
	@Resource
	private CmdbDictProperties cmdbDictProperties;
	@Resource
	private CheckProperties checkProperties;
	@Resource
	private CmdbCientityProperties cmdbCientityProperties;
	@Resource
	private ICmdbClient cmdbClient;
	@Value(value = "${third.api-erp}")
	private boolean erpPush;
	@Value(value = "${device.record.boolean}")
	private boolean recordBoolean;



	@Override
	public DeviceRecordVO deviceRecordGet() {
		//获取用户信息
		IdevelopUser user = SecureUtil.getUser();
		DeviceRecordVO deviceRecordVO = new DeviceRecordVO();
		deviceRecordVO.setReceiver(user.getUserId().toString());
		deviceRecordVO.setUseKeepPerson(user.getRealName());
		//VO封装固定数据，字典值取第一个，时间取当前时间
		deviceRecordVO.setWbsElement(dictClient.getValue(DictConstant.WBSELEMENT, 1).getData());
		deviceRecordVO.setDeviceName(CommonConstant.DEVICENAME);
		deviceRecordVO.setFullName(CommonConstant.FULLNAME);
		deviceRecordVO.setIsToErp(1);
		deviceRecordVO.setIsSendErp(0);
		deviceRecordVO.setNameplateNo(CommonConstant.NAMEPLATENO);
		deviceRecordVO.setMaintenanceCountry(CommonConstant.MAINTENANCECOUNTRY);
		deviceRecordVO.setFactoryDate(new Date());
		deviceRecordVO.setOwnerUnit(user.getCorpId());
		deviceRecordVO.setOwnerUnitName(String.valueOf(user.getExt().get("corpFullName")));
		deviceRecordVO.setPropertyDept(user.getDeptId());
		deviceRecordVO.setPropertyDeptName(user.getDeptName());
		//工厂区域
		if ((user.getRegionCode() + "").length() == 6) {
			//004 县公司
			deviceRecordVO.setFactoryAreaCode(cmdbCientityProperties.getCountyFactoryArea());
			deviceRecordVO.setFactoryArea(Constants.COUNTY_FACTORY_AREA);
		} else {
			//003 省/市公司
			deviceRecordVO.setFactoryAreaCode(cmdbCientityProperties.getCityFactoryArea());
			deviceRecordVO.setFactoryArea(Constants.CITY_FACTORY_AREA);
		}
		//维护工厂
		deviceRecordVO.setMaintenanceFactory(user.getErpDept());
		deviceRecordVO.setMaintenanceFactoryCode(user.getErpDeptCode());
		deviceRecordVO.setLineStation(CommonConstant.LINESTATION);
		deviceRecordVO.setReceiverTime(new Date());
		deviceRecordVO.setReceiverName(user.getRealName());
		deviceRecordVO.setOprtDate(new Date());
		return deviceRecordVO;
	}

	/**
	 * 新增或修改 设备建档
	 *
	 * @param deviceRecordDTO 新增修改参数
	 * @return R
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<DeviceRecordVO> deviceRecordSaveOrUpdate(DeviceRecordDTO deviceRecordDTO) {
		R check = nameCheck(deviceRecordDTO.getDeviceName());
		if (!check.isSuccess()){
			return check;
		}
		IdevelopUser user = SecureUtil.getUser();
		// R<DeviceRecordVO> x = checkDeviceRecord(deviceRecordDTO);
		// if (x != null) {
		// 	return x;
		// }
		DeviceRecord deviceRecord = BeanUtil.copy(deviceRecordDTO, DeviceRecord.class);
		deviceRecord.setDeviceStatus(Constants.DEVICE_STATUS_RESERVE);
		if (Objects.isNull(deviceRecord.getId())) {
			deviceRecord.setRegionCode(user.getRegionCode());
			deviceRecord.setFilingNo(orderNumberUtil.generateNumber(WorkOrderTypeEnum.JD));
			deviceRecord.setCreateDept(Long.parseLong(user.getDeptId()));
			deviceRecord.setCreateTime(new Date());
			deviceRecord.setCreateUser(user.getUserId());
			deviceRecord.setStatus(1);
			deviceRecord.setErpStatus("0");
			deviceRecord.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
			//默认计量单位
			deviceRecord.setUnitName("台");
			deviceRecord.setUnit("1125222511345664");
			//获取计量单位
			R<Map<String, Object>> mapR = cmdbClient.feignCientityDetailById(cmdbDictProperties.getDeviceType(), Long.parseLong(deviceRecord.getDeviceType()));
			if (mapR.isSuccess()){
				String unitCode = mapR.getData().get("UNIT_CODE").toString();
				if (StringUtil.isNotBlank(unitCode)){
					R<Map<String, Object>> unit = cmdbClient.feignCientityDetailById(cmdbDictProperties.getUnifiedCode(), Long.parseLong(unitCode));
					if (unit.isSuccess()){
						String unitName = unit.getData().get("dictKey").toString();
						deviceRecord.setUnitName(unitName);
						deviceRecord.setUnit(unitCode);
					}
				}
			}
			baseMapper.insert(deviceRecord);
			// 增加操作记录
			logOptService.commonLogOpt(LogOpt.builder().logId(deviceRecord.getId()).logData(deviceRecordDTO.toString()).params(deviceRecordDTO.toString())
				.optRole("--").optType(OptTypeEnum.DEVICE_FILING.getCode()).title("新增暂存设备建档").build());
		} else {
			// 判断是否还可以进行修改操作
			DeviceRecord deviceRecodeInfo = baseMapper.selectOne(new LambdaQueryWrapper<DeviceRecord>().eq(DeviceRecord::getId, deviceRecordDTO.getId())
				.eq(DeviceRecord::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
			if (Objects.isNull(deviceRecodeInfo) || !(DeviceRecordEnum.TEMPORARILY.getCode().equals(deviceRecodeInfo.getStatus())
				|| (StringUtils.isNotBlank(deviceRecodeInfo.getProcessStatus()) && DeviceRecordBpmNodeEnum.DEVICE_RECORD_APPLY.getNode().equals(deviceRecodeInfo.getProcessStatus())))) {
				return R.fail("当前设备建档无法进行暂存");
			}
			deviceRecord.setUpdateTime(new Date());
			deviceRecord.setUpdateUser(user.getUserId());
			baseMapper.updateById(deviceRecord);
			// 增加操作记录
			logOptService.commonLogOpt(LogOpt.builder().logId(deviceRecord.getId()).logData(deviceRecordDTO.toString()).params(deviceRecordDTO.toString())
				.optRole("--").optType(OptTypeEnum.DEVICE_FILING.getCode()).title("修改暂存设备建档").build());
		}
		return R.data(Convert.convert(DeviceRecordVO.class, deviceRecord));
	}

	/**
	 * 设备建档参数校验
	 *
	 * @param deviceRecordDTO 请求参数
	 * @return R
	 */
	private R<DeviceRecordVO> checkDeviceRecord(DeviceRecordDTO deviceRecordDTO) {
		if (deviceRecordDTO.getOprtDate().before(deviceRecordDTO.getProcureDate())) {
			return R.fail("投运日期不能早于采购日期");
		}
		return null;
	}

	/**
	 * 提交建档
	 *
	 * @param deviceRecordDTO 建档参数
	 * @return R
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<DeviceRecordVO> deviceRecordSubmit(DeviceRecordDTO deviceRecordDTO) throws Exception {

		R check = nameCheck(deviceRecordDTO.getDeviceName());
		if (!check.isSuccess()){
			return check;
		}
		IdevelopUser user = SecureUtil.getUser();
		// R<DeviceRecordVO> x = checkDeviceRecord(deviceRecordDTO);
		// if (x != null) {
		// 	return x;
		// }
		DeviceRecord deviceRecord = Convert.convert(DeviceRecord.class, deviceRecordDTO);
		deviceRecord.setReceiver(user.getUserId().toString());
		deviceRecord.setReceiverTime(new Date());
		deviceRecord.setProcessStatus(DeviceRecordBpmNodeEnum.DEVICE_RECORD_APPLY.getNode());
		deviceRecord.setSubmitTime(new Date());
		deviceRecord.setSubmitUser(user.getUserId().toString());
		deviceRecord.setErpStatus("1");
		deviceRecord.setStatus(2);
		//默认计量单位
		deviceRecord.setUnitName("台");
		deviceRecord.setUnit("1125222511345664");
		//获取计量单位
		R<Map<String, Object>> mapR = cmdbClient.feignCientityDetailById(cmdbDictProperties.getDeviceType(), Long.parseLong(deviceRecord.getDeviceType()));
		if (mapR.isSuccess()){
			String unitCode = mapR.getData().get("UNIT_CODE").toString();
			if (StringUtil.isNotBlank(unitCode)){
				R<Map<String, Object>> unit = cmdbClient.feignCientityDetailById(cmdbDictProperties.getUnifiedCode(), Long.parseLong(unitCode));
				if (unit.isSuccess()){
					String unitName = unit.getData().get("dictKey").toString();
					deviceRecord.setUnitName(unitName);
					deviceRecord.setUnit(unitCode);
				}
			}
		}
		// 用于判断是否推送给ERP,是否删除已推送给ERP的信息
		Boolean isSendErp = Boolean.TRUE;
		deviceRecord.setDeviceStatus(Constants.DEVICE_STATUS_RESERVE);
		if (Objects.isNull(deviceRecordDTO.getId())) {
			deviceRecord.setRegionCode(user.getRegionCode());
			deviceRecord.setFilingNo(orderNumberUtil.generateNumber(WorkOrderTypeEnum.JD));
			deviceRecord.setCreateTime(new Date());
			deviceRecord.setCreateUser(user.getUserId());
			deviceRecord.setCreateDept(Long.parseLong(user.getDeptId()));
			deviceRecord.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
			baseMapper.insert(deviceRecord);
		} else {
			// 判断是否还可以进行设备建档
			DeviceRecord record = baseMapper.selectOne(new LambdaQueryWrapper<DeviceRecord>().eq(DeviceRecord::getId, deviceRecordDTO.getId())
				.eq(DeviceRecord::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
			if (record.getIsSendErp() == 1) {
				isSendErp = Boolean.FALSE;
			}
			if (!(DeviceRecordEnum.TEMPORARILY.getCode().equals(record.getStatus())
				|| DeviceRecordBpmNodeEnum.DEVICE_RECORD_APPLY.getNode().equals(record.getProcessStatus()))) {
				return R.fail("无法进行设备建档");
			}
			deviceRecord.setUpdateTime(new Date());
			deviceRecord.setUpdateUser(user.getUserId());
			baseMapper.updateById(deviceRecord);
		}
		if (isSendErp) {
			deviceRecordDTO.setId(deviceRecord.getId());
			deviceRecordListService.remove(new LambdaQueryWrapper<DeviceRecordList>().eq(DeviceRecordList::getRecordId, deviceRecord.getId()));
			// 生成列表详情数据
			Integer deviceNum = deviceRecordDTO.getDeviceNum();
			List<DeviceRecordList> deviceRecordList = new ArrayList<>();
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			Integer number = 1;
			for (int i = 0; i < deviceNum; i++) {
				String format = dateTimeFormatter.format(LocalDateTime.now());
				DeviceRecordList recordList = DeviceRecordList.builder()
					.recordId(deviceRecord.getId())
					.wbsProject(deviceRecordDTO.getWbsProject())
					.wbsElement(deviceRecordDTO.getWbsElement())
					.filingNo(deviceRecord.getFilingNo())
					.deviceUuid(UUID.randomUUID().toString().replace("-", ""))
					.deviceCategory(deviceRecordDTO.getDeviceCategory())
					.deviceType(deviceRecordDTO.getDeviceType())
					.deviceStatus(1)
					.deviceName(deviceRecordDTO.getDeviceName() + format + "-" + number)
					.deviceCode(orderNumberUtil.generateCode(deviceRecordDTO.getDeviceType()))
					.erpStatus("0")
					.i6000Status("0")
					.erpAssetStatus("0")
					.funLocation(deviceRecordDTO.getFunLocation())
					.createDept(user.getDeptId())
					.build();
				recordList.setStatus(1);
				recordList.setCreateTime(new Date());
				recordList.setCreateUser(user.getUserId());
				recordList.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
				number++;
				deviceRecordList.add(recordList);
			}
			deviceRecordListService.saveBatch(deviceRecordList);
		} else {
			deviceRecordDTO.setId(deviceRecord.getId());
			List<DeviceRecordList> deviceRecordList = deviceRecordListService.list(new LambdaQueryWrapper<DeviceRecordList>().eq(DeviceRecordList::getRecordId, deviceRecord.getId()));
			for (DeviceRecordList recordList : deviceRecordList) {
				recordList.setWbsProject(deviceRecordDTO.getWbsProject());
				recordList.setWbsElement(deviceRecordDTO.getWbsElement());
				recordList.setDeviceCategory(deviceRecordDTO.getDeviceCategory());
				recordList.setDeviceType(deviceRecordDTO.getDeviceType());
				recordList.setDeviceName(deviceRecordDTO.getDeviceName());
				recordList.setDeviceCode(orderNumberUtil.generateCode(deviceRecordDTO.getDeviceType()));
				recordList.setFunLocation(deviceRecordDTO.getFunLocation());
				recordList.setCreateDept(user.getDeptId());
			}
			deviceRecordListService.updateBatchById(deviceRecordList);
		}
		String roleName;
		String processInsId = null;
		if (StringUtils.isNotBlank(deviceRecord.getProcessInsId())) {
			//  获取当前节点操作角色
			List<HussarAssignVo> hussarAssignVoList = hussarBpmService.queryTaskInfo(deviceRecord.getFilingNo());
			roleName = hussarAssignVoList.stream().map(HussarAssignVo::getRoleName).collect(Collectors.joining(","));
			HussarBpmDTO hussarBpmDTO = new HussarBpmDTO();
			hussarBpmDTO.setBusinessKey(deviceRecord.getFilingNo());
			hussarBpmDTO.setParticipantType("2");
			hussarBpmDTO.setTaskType("1");
			hussarBpmDTO.setProcessDefinitionKey(HussarBpmTypeEnum.DEVICE_RECORD.getBpmMark());
			hussarBpmService.hussarSubmit(hussarBpmDTO);
		} else {
			// 组装发起流程需要的参数
			Map<String, Object> variable = new HashMap<>();
			variable.put("orderId", deviceRecord.getId());
			variable.put("orderNo", deviceRecord.getFilingNo());
			variable.put("userId", user.getUserId());
			variable.put("userName", user.getRealName());
			variable.put("regionCode", user.getRegionCode());
			HussarBpmCreateDTO hussarBpmCreateDTO = HussarBpmCreateDTO.builder().processDefinitionKey(HussarBpmTypeEnum.DEVICE_RECORD.getBpmMark())
				.businessKey(deviceRecord.getFilingNo()).variable(variable).build();
			HussarCreateVo hussarBpm;
			// 发起流程
			try {
				hussarBpm = hussarBpmService.createHussarBpm(hussarBpmCreateDTO);
				//  获取当前节点操作角色
				List<HussarAssignVo> hussarAssignVoList = hussarBpmService.queryTaskInfo(deviceRecord.getFilingNo());
				roleName = hussarAssignVoList.stream().map(HussarAssignVo::getRoleName).collect(Collectors.joining(","));
				processInsId = hussarBpm.getProcessInsId();
			} catch (Exception e) {
				throw new Exception("创建流程发生异常");
			}
			HussarBpmDTO hussarBpmDTO = new HussarBpmDTO();
			hussarBpmDTO.setBusinessKey(deviceRecord.getFilingNo());
			hussarBpmDTO.setParticipantType("2");
			hussarBpmDTO.setTaskType("1");
			hussarBpmDTO.setProcessDefinitionKey(HussarBpmTypeEnum.DEVICE_RECORD.getBpmMark());
			hussarBpmDTO.setApprovalOpinion("发起设备建档申请");
			hussarBpmService.hussarSubmit(hussarBpmDTO);
		}
		// 记录流程id
		baseMapper.update(new LambdaUpdateWrapper<DeviceRecord>().eq(DeviceRecord::getId, deviceRecord.getId())
			.set(StringUtils.isNotBlank(processInsId), DeviceRecord::getProcessInsId, processInsId)
			.set(DeviceRecord::getProcessStatus, DeviceRecordBpmNodeEnum.DEVICE_RECORD_PROFESSIONAL_REVIEW.getNode()));
		// 增加操作记录
		logOptService.commonLogOpt(LogOpt.builder().logId(deviceRecord.getId()).logData(deviceRecordDTO.toString()).params(deviceRecordDTO.toString())
			.optRole(roleName).optType(OptTypeEnum.DEVICE_FILING.getCode()).title("发起设备建档申请").build());
		// 记录审核流程
		approveRecordService.commonRecord(ApproveRecord.builder().wbsId(deviceRecord.getWbsProject()).filingNo(deviceRecord.getId())
			.optRole(roleName).optType(OptTypeEnum.DEVICE_FILING.getCode()).nodeId(DeviceRecordBpmNodeEnum.DEVICE_RECORD_APPLY.getNode())
			.nodeName(DeviceRecordBpmNodeEnum.getMessage(DeviceRecordBpmNodeEnum.DEVICE_RECORD_APPLY.getNode())).optTitle("发起设备建档申请").
			optOpinion("发起设备建档申请").approveStatus(0).filingCode(deviceRecord.getFilingNo()).build());
		return R.data(Convert.convert(DeviceRecordVO.class, deviceRecord));
	}

	/**
	 * 删除设备建档
	 *
	 * @param ids 删除id
	 * @return R<Integer>
	 */
	@Override
	public R<Integer> removeDeviceRecord(String ids) {
		IdevelopUser user = SecureUtil.getUser();
		List<String> idList = Arrays.asList(ids.split(","));
		List<DeviceRecord> deviceRecordList = baseMapper.selectList(new LambdaQueryWrapper<DeviceRecord>().in(DeviceRecord::getId, idList)
			.eq(DeviceRecord::getStatus, DeviceRecordEnum.TEMPORARILY.getCode())
			.eq(DeviceRecord::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		if (deviceRecordList.size() != idList.size()) {
			return R.fail("所选设备建档无法删除");
		}
		baseMapper.update(new LambdaUpdateWrapper<DeviceRecord>().in(DeviceRecord::getId, idList).eq(DeviceRecord::getIsDeleted, IdevelopConstant.DB_NOT_DELETED)
			.set(DeviceRecord::getIsDeleted, IdevelopConstant.DB_IS_DELETED).set(DeviceRecord::getUpdateTime, new Date()).set(DeviceRecord::getUpdateUser, user.getUserId()));
		// 增加操作记录
		idList.forEach(item -> logOptService.commonLogOpt(LogOpt.builder().logId(item).logData(ids).params(ids).optType(OptTypeEnum.DEVICE_TRANSFER.getCode()).title("删除暂存设备建档").build()));
		return R.success(ResultCode.SUCCESS);
	}

	/**
	 * 设备建档列表查询
	 *
	 * @param deviceRecordDTO 查询条件
	 * @param query           分页参数
	 * @return R
	 */
	@Override
	public R<IPage<DeviceRecord>> deviceRecordList(DeviceRecordDTO deviceRecordDTO, Query query) {
		IdevelopUser user = SecureUtil.getUser();
		LambdaQueryWrapper<DeviceRecord> queryWrapper = new LambdaQueryWrapper<DeviceRecord>().eq(DeviceRecord::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
		// queryWrapper.like(StringUtils.isNotBlank(deviceRecordDTO.getFilingNo()), DeviceRecord::getFilingNo, deviceRecordDTO.getFilingNo());
		// queryWrapper.eq(StringUtils.isNotBlank(deviceRecordDTO.getWbsProject()), DeviceRecord::getWbsProject, deviceRecordDTO.getWbsProject());
		// queryWrapper.eq(StringUtils.isNotBlank(deviceRecordDTO.getWbsElement()), DeviceRecord::getWbsElement, deviceRecordDTO.getWbsElement());
		// queryWrapper.eq(Objects.nonNull(deviceRecordDTO.getStatus()), DeviceRecord::getStatus, deviceRecordDTO.getStatus());
		// queryWrapper.ge(Objects.nonNull(deviceRecordDTO.getStartDate()), DeviceRecord::getCreateTime, deviceRecordDTO.getStartDate());
		// queryWrapper.le(Objects.nonNull(deviceRecordDTO.getEndDate()), DeviceRecord::getCreateTime, deviceRecordDTO.getEndDate());
		// queryWrapper.eq(StringUtils.isNotBlank(deviceRecordDTO.getUseKeepDept()), DeviceRecord::getUseKeepDept, deviceRecordDTO.getUseKeepDept());
		// queryWrapper.eq(StringUtils.isNotBlank(deviceRecordDTO.getEntityKeepDept()), DeviceRecord::getEntityKeepDept, deviceRecordDTO.getEntityKeepDept());
		// queryWrapper.eq(StringUtils.isNotBlank(deviceRecordDTO.getDeviceAddType()), DeviceRecord::getDeviceAddType, deviceRecordDTO.getDeviceAddType());
		// queryWrapper.eq(StringUtils.isNotBlank(deviceRecordDTO.getDeviceChangeType()), DeviceRecord::getDeviceChangeType, deviceRecordDTO.getDeviceChangeType());
		// queryWrapper.eq(StringUtils.isNotBlank(deviceRecordDTO.getErpStatus()), DeviceRecord::getErpStatus, deviceRecordDTO.getErpStatus());
		// queryWrapper.likeRight(DeviceRecord::getRegionCode, user.getRegionCode());
		queryWrapper.eq(StringUtils.isNotBlank(deviceRecordDTO.getOwnerUnit()),DeviceRecord::getOwnerUnit,deviceRecordDTO.getOwnerUnit());
		// queryWrapper.eq(StringUtils.isNotBlank(deviceRecordDTO.getPropertyDept()),DeviceRecord::getPropertyDept,deviceRecordDTO.getPropertyDept());
		// queryWrapper.orderByDesc(DeviceRecord::getCreateTime);
		IPage<DeviceRecord> pages = baseMapper.selectPage(Condition.getPage(query), queryWrapper);
		return R.data(pages);
	}

	/**
	 * 工作台获取设备建档列表
	 *
	 * @param deviceRecordDTO 查询条件
	 * @param query           分页
	 * @return R
	 */
	@Override
	public R<IPage<DeviceRecordVO>> deskDeviceRecodeList(DeviceRecordDTO deviceRecordDTO, Query query) {
		if (StringUtils.isBlank(deviceRecordDTO.getOrderNoList())) {
			return R.data(new Page<>());
		}
		List<String> orderNoList = Arrays.asList(deviceRecordDTO.getOrderNoList().split(","));
		LambdaQueryWrapper<DeviceRecord> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.in(DeviceRecord::getFilingNo, orderNoList).orderByDesc(DeviceRecord::getCreateTime);
		IPage<DeviceRecord> deviceRecordIPage = baseMapper.selectPage(Condition.getPage(query), queryWrapper);
		return R.data(Convert.convert(new TypeReference<IPage<DeviceRecordVO>>() {
		}, deviceRecordIPage));
//		return R.data(baseMapper.deskDeviceRecodeList(deviceRecordDTO, Condition.getPage(query)));
	}

	/**
	 * 更新设备建档工单状态
	 *
	 * @param deviceRecordDTO 更新参数
	 * @return R
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<Integer> deskDeviceRecodeStatus(DeviceRecordDTO deviceRecordDTO) throws Exception {
		DeviceRecord deviceRecord = baseMapper.selectOne(new LambdaQueryWrapper<DeviceRecord>()
			.eq(DeviceRecord::getId, deviceRecordDTO.getId()).eq(DeviceRecord::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		//  获取当前节点操作角色
		List<HussarAssignVo> hussarAssignVoList = hussarBpmService.queryTaskInfo(deviceRecord.getFilingNo());
		String roleName = hussarAssignVoList.stream().map(HussarAssignVo::getRoleName).collect(Collectors.joining(","));
		IdevelopUser user = SecureUtil.getUser();
		String recordNode = null;
		String processStatus = null;
		if (DeviceRecordBpmNodeEnum.DEVICE_RECORD_APPLY.getNode().equals(deviceRecord.getProcessStatus())
			&& deviceRecordDTO.getWorkerStatus() == 0) {
			processStatus = DeviceRecordBpmNodeEnum.DEVICE_RECORD_PROFESSIONAL_REVIEW.getNode();
			recordNode = DeviceRecordBpmNodeEnum.DEVICE_RECORD_APPLY.getNode();
			deviceRecordDTO.setComment("发起设备建档申请");
		}
		if (DeviceRecordBpmNodeEnum.DEVICE_RECORD_PROFESSIONAL_REVIEW.getNode().equals(deviceRecord.getProcessStatus())
			&& deviceRecordDTO.getWorkerStatus() == 0) {
			processStatus = DeviceRecordBpmNodeEnum.DEVICE_RECORD_PROFESSIONAL_SERVICE.getNode();
			recordNode = DeviceRecordBpmNodeEnum.DEVICE_RECORD_PROFESSIONAL_REVIEW.getNode();
			deviceRecordDTO.setComment(deviceRecordDTO.getComment());
			// 推送ERP ERP没有的设备类型，不进行推送
			if (erpPush) {
				Map<Object, Object> deviceTypeMap = cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getDeviceType());
				if (Objects.nonNull(deviceTypeMap.get(deviceRecord.getDeviceType())) && !"".equals(deviceTypeMap.get(deviceRecord.getDeviceType()))) {
					pushErp(deviceRecord);
				}
			}
		}
		if (DeviceRecordBpmNodeEnum.DEVICE_RECORD_PROFESSIONAL_REVIEW.getNode().equals(deviceRecord.getProcessStatus())
			&& deviceRecordDTO.getWorkerStatus() == 1) {
			processStatus = DeviceRecordBpmNodeEnum.DEVICE_RECORD_APPLY.getNode();
			recordNode = DeviceRecordBpmNodeEnum.DEVICE_RECORD_PROFESSIONAL_REVIEW.getNode();
			LambdaUpdateWrapper<ApproveRecord> updateWrapper = new LambdaUpdateWrapper<>();
			updateWrapper.set(ApproveRecord::getOptOpinion,"待审批").set(ApproveRecord::getOptTitle,"待审批").eq(ApproveRecord::getFilingNo,deviceRecordDTO.getId()).eq(ApproveRecord::getNodeId,processStatus);
			approveRecordService.update(updateWrapper);
		}
		// 增加操作记录
		logOptService.commonLogOpt(LogOpt.builder().logId(deviceRecordDTO.getId()).logData(deviceRecordDTO.toString()).params(deviceRecordDTO.toString())
			.optType(OptTypeEnum.DEVICE_FILING.getCode()).title(deviceRecordDTO.getComment()).optRole(roleName).time(new Date()).build());
		// 记录审核流程
		approveRecordService.commonRecord(ApproveRecord.builder().wbsId(deviceRecord.getWbsProject()).filingNo(deviceRecordDTO.getId())
			.optType(OptTypeEnum.DEVICE_FILING.getCode()).nodeId(recordNode).optRole(roleName)
			.nodeName(DeviceRecordBpmNodeEnum.getMessage(recordNode)).optTitle(deviceRecordDTO.getComment())
			.optOpinion(deviceRecordDTO.getComment())
			.approveStatus(deviceRecordDTO.getWorkerStatus())
			.filingCode(deviceRecord.getFilingNo())
			.build());
		// 更新工单信息
		baseMapper.update(new LambdaUpdateWrapper<DeviceRecord>().eq(DeviceRecord::getId, deviceRecordDTO.getId()).set(DeviceRecord::getUpdateTime, new Date())
			.set(DeviceRecord::getUpdateUser, user.getUserId()).set(DeviceRecord::getProcessStatus, processStatus));
		if (deviceRecordDTO.getWorkerStatus() == 0) {
			if (DeviceRecordBpmNodeEnum.DEVICE_RECORD_PROFESSIONAL_SERVICE.getNode().equals(processStatus)) {
				processStatus = DeviceRecordBpmNodeEnum.DEVICE_RECORD_PROFESSIONAL_FINANCE.getNode();
				recordNode = DeviceRecordBpmNodeEnum.DEVICE_RECORD_PROFESSIONAL_SERVICE.getNode();
			}
			// 增加操作记录
			LogOpt logOpt = LogOpt.builder().logId(deviceRecordDTO.getId()).logData(null).params(null).optName("ERP审核")
				.optType(OptTypeEnum.DEVICE_FILING.getCode()).title("ERP业务层审批通过").optRole("--").time(new Date()).build();
			logOpt.setStatus(0);
			logOptService.commonLogOpt(logOpt);
			// 记录审核流程
			ApproveRecord approveRecord = ApproveRecord.builder().wbsId(deviceRecord.getWbsProject()).filingNo(deviceRecordDTO.getId())
				.optType(OptTypeEnum.DEVICE_FILING.getCode()).nodeId(recordNode).optRole("ERP审核角色")
				.nodeName(DeviceRecordBpmNodeEnum.getMessage(recordNode)).optTitle("ERP业务层审批通过")
				.filingCode(deviceRecord.getFilingNo()).approveStatus(deviceRecordDTO.getWorkerStatus())
				.optOpinion("ERP业务层审批通过").build();
			approveRecord.setStatus(0);
			approveRecordService.commonRecord(approveRecord);
			// 更新工单信息
			baseMapper.update(new LambdaUpdateWrapper<DeviceRecord>().eq(DeviceRecord::getId, deviceRecordDTO.getId()).set(DeviceRecord::getUpdateTime, new Date())
				.set(DeviceRecord::getUpdateUser, user.getUserId()).set(DeviceRecord::getProcessStatus, processStatus));
			HussarBpmDTO hussarBpmDTO = new HussarBpmDTO();
			hussarBpmDTO.setBusinessKey(deviceRecord.getFilingNo());
			hussarBpmDTO.setParticipantType("2");
			hussarBpmDTO.setTaskType("1");
			hussarBpmDTO.setProcessDefinitionKey(HussarBpmTypeEnum.DEVICE_RECORD.getBpmMark());
			hussarBpmDTO.setApprovalOpinion(deviceRecordDTO.getComment());
			hussarBpmService.hussarSubmit(hussarBpmDTO);

			HussarBpmDTO hussarBpmDTO1 = new HussarBpmDTO();
			hussarBpmDTO1.setBusinessKey(deviceRecord.getFilingNo());
			hussarBpmDTO1.setParticipantType("2");
			hussarBpmDTO1.setTaskType("1");
			Map<String, Object> variable1 = new HashMap<>();
			variable1.put("flag", 0);
			hussarBpmDTO1.setVariable(variable1);
			hussarBpmDTO1.setProcessDefinitionKey(HussarBpmTypeEnum.DEVICE_RECORD.getBpmMark());
			hussarBpmService.hussarSubmit(hussarBpmDTO1);
		}
		if (deviceRecordDTO.getWorkerStatus() == 1) {
			HussarBpmDTO hussarBpmDTO = new HussarBpmDTO();
			hussarBpmDTO.setOrderId(deviceRecord.getId());
			hussarBpmDTO.setBusinessKey(deviceRecord.getFilingNo());
			hussarBpmDTO.setApprovalOpinion(deviceRecordDTO.getComment());
			hussarBpmService.prevNodeReject(hussarBpmDTO);
		}
		return R.success(ResultCode.SUCCESS);
	}

	/**
	 * 推送ERP
	 *
	 * @param deviceRecord 建档信息
	 */
	private void pushErp(DeviceRecord deviceRecord) {
		SimpleDateFormat DATE_FORMAT_NO_UNDERLINE = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
		// Map<Object, Object> deviceStatusMap = cmdbDictProperties.getDeviceStatusMapErp();
		// String deviceStatus = deviceStatusMap.get(deviceRecord.getDeviceStatus()).toString();
		Map<Object, Object> deviceAddMap = cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getDeviceAdd());
		String deviceAdd = deviceAddMap.get(deviceRecord.getDeviceAddType()).toString();
		Map<Object, Object> deviceTypeMap = cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getDeviceType());
		String deviceType = deviceTypeMap.get(deviceRecord.getDeviceType()).toString();
		Map<Object, Object> deviceUnifiedErp = cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getUnifiedCode());
		String unit = deviceUnifiedErp.get(deviceRecord.getUnit()).toString();
		Map<Object, Object> factoryAreaCodeErp = cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getFactoryAreaCode());
		String factoryCode = factoryAreaCodeErp.get(deviceRecord.getFactoryAreaCode()).toString();
		// 推送ERP
		ErpTransEqunr erpTransEqunr = new ErpTransEqunr();
		erpTransEqunr.setXtdocId(deviceRecord.getId());
		erpTransEqunr.setXtdocNo(deviceRecord.getFilingNo());
		erpTransEqunr.setOperationType(ErpOperationEnum.C);
		ArrayList<ErpTransEqunrItem> erpTransEqunrItems = new ArrayList<>();
		List<DeviceRecordList> list = deviceRecordListService.list(new LambdaQueryWrapper<DeviceRecordList>()
			.eq(DeviceRecordList::getRecordId, deviceRecord.getId()));
		list.forEach(item -> {
			ErpTransEqunrItem erpTransEqunrItem = new ErpTransEqunrItem();
			erpTransEqunrItem.setXtbm(item.getDeviceUuid());
			erpTransEqunrItem.setXtbmNo("");
			erpTransEqunrItem.setSwid("");
			erpTransEqunrItem.setEqktx(item.getDeviceName());
			erpTransEqunrItem.setZsb001(deviceRecord.getUseKeepDept());
			erpTransEqunrItem.setZsb002(deviceRecord.getEntityKeepDept());
			erpTransEqunrItem.setZsb010(deviceRecord.getUseKeepPerson());
			erpTransEqunrItem.setZsb004(Constants.ERP_ZSB004);
			erpTransEqunrItem.setStat("E0002");
			erpTransEqunrItem.setZsb005(deviceAdd);
			erpTransEqunrItem.setStort("");
			erpTransEqunrItem.setEqart("");
			erpTransEqunrItem.setSbfl(deviceType);
			erpTransEqunrItem.setHerst(deviceRecord.getManufacturer());
			erpTransEqunrItem.setHerld("CN");
			erpTransEqunrItem.setPosid(item.getWbsElement());
			erpTransEqunrItem.setZsb006(deviceRecord.getFunLocation());
			erpTransEqunrItem.setTplnr(deviceRecord.getFunLocationCode());
			erpTransEqunrItem.setZcabn_ztpm1005(1);
			erpTransEqunrItem.setZcabn_ztpm1006(unit);
			erpTransEqunrItem.setBeber(factoryCode);
			erpTransEqunrItem.setInbdt(DATE_FORMAT_NO_UNDERLINE.format(deviceRecord.getOprtDate()));
			erpTransEqunrItem.setTypbz(deviceRecord.getDeviceModel());
			erpTransEqunrItem.setSerge(deviceRecord.getNameplateNo());
			String factoryDate = DATE_FORMAT.format(deviceRecord.getFactoryDate());
			erpTransEqunrItem.setBaujj(factoryDate.substring(0, 4));
			erpTransEqunrItem.setBaumm(factoryDate.substring(5, 7));
			erpTransEqunrItem.setSwerk(deviceRecord.getMaintenanceFactoryCode());
			erpTransEqunrItem.setAnlnr("");
			erpTransEqunrItem.setEqunr("");
			erpTransEqunrItem.setTbbs("");
			erpTransEqunrItem.setZsb011(deviceRecord.getLineStation());
			erpTransEqunrItems.add(erpTransEqunrItem);
		});
		erpTransEqunr.setErpTransEqunrItemList(erpTransEqunrItems);
		log.debug("建档推送erp信息：" + erpTransEqunr.toString());
		ErpTransEqunrResp erpTransEqunrResp = erpService.transEqunr(erpTransEqunr);
		if (StringUtils.equals("S", erpTransEqunrResp.getCode())) {
			DeviceRecord updateRecord = new DeviceRecord();
			updateRecord.setId(deviceRecord.getId());
			updateRecord.setIsSendErp(1);
			baseMapper.updateById(updateRecord);
		}
		log.debug("设备建档推送erp信息返回：" + erpTransEqunrResp.toString());
	}

	/**
	 * ERP审核回传接口
	 *
	 * @param erpDeviceOrderDTO 审核参数
	 * @return R
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@TripleApiLogA(value = TripleApiLogValueEnum.ERP_XTYTH_CALLBACK, tripleType = TripleTypeEnum.ERP)
	public R<Integer> erpDeviceRecord(ErpDeviceOrderDTO erpDeviceOrderDTO) throws Exception {
		if (erpDeviceOrderDTO.getFilingNo().startsWith(WorkOrderTypeEnum.JD.getValue())) {
			return erpCheckDeviceRecord(erpDeviceOrderDTO);
		}
		if (erpDeviceOrderDTO.getFilingNo().startsWith(WorkOrderTypeEnum.ZZ.getValue())) {
			try {
				return deviceTransferService.erpDeviceTransfer(erpDeviceOrderDTO);
			} catch (Exception e) {
				return R.fail("erp回调转资失败！");
			}
		}
		return R.success(ResultCode.SUCCESS);
	}

	/**
	 * ERP审核回调设备建档业务处理
	 *
	 * @param erpDeviceOrderDTO ERP审核信息
	 * @return R
	 */
	private R<Integer> erpCheckDeviceRecord(ErpDeviceOrderDTO erpDeviceOrderDTO) {
		DeviceRecord deviceRecord = baseMapper.selectOne(new LambdaQueryWrapper<DeviceRecord>()
			.eq(DeviceRecord::getId, erpDeviceOrderDTO.getDeviceRecordId()).eq(DeviceRecord::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		String recordStatus = deviceRecord.getProcessStatus();
		String processStatus = null;
		List<ErpDeviceDetailDTO> deviceRecordListDTOList = erpDeviceOrderDTO.getDeviceRecordListDTOList();
		if (DeviceRecordBpmNodeEnum.DEVICE_RECORD_FINISH.getNode().equals(recordStatus)) {
			if (0 == erpDeviceOrderDTO.getErpExamineStatus()) {
				if (CollectionUtil.isNotEmpty(erpDeviceOrderDTO.getDeviceRecordListDTOList())) {
					addErpDeatilList(deviceRecordListDTOList, deviceRecord);
					deviceRecordListService.updateBatchDeviceId(erpDeviceOrderDTO.getDeviceRecordListDTOList());
				}
			}
		} else {
			HussarCompleteParam hussarCompleteParam = new HussarCompleteParam();
			HussarTaskVo hussarTaskVo;
			com.lnsoft.hussar.bpm.tool.api.R<List<HussarTaskVo>> listR = hussarBpmClient.queryTaskIdByBusinessKey(erpDeviceOrderDTO.getFilingNo());
			if (ResultCode.SUCCESS.getCode() == listR.getCode()) {
				hussarTaskVo = listR.getData().get(0);
			} else {
				return R.fail(listR.getMsg());
			}
			hussarCompleteParam.setTaskId(hussarTaskVo.getTaskId());
			hussarCompleteParam.setTaskType("1");
			if (0 == erpDeviceOrderDTO.getErpExamineStatus()) {
				hussarCompleteParam.setParticipantType("2");
				// TODO 待修复问题
				// 查询role的id
				Role role = sysClient.getRoleByRoleName("ERP审核角色");
				if (Objects.isNull(role)) {
					return R.fail("未获取到审核角色，请联系运维人员");
				}
				hussarCompleteParam.setParticipantWithRoles("1773271394536218625");
			} else {
				hussarCompleteParam.setParticipantType("1");
				ApproveRecord approveRecord = approveRecordService.getOne(new LambdaQueryWrapper<ApproveRecord>().eq(ApproveRecord::getFilingNo, deviceRecord.getId())
					.eq(ApproveRecord::getOptType, OptTypeEnum.DEVICE_FILING.getCode()).eq(ApproveRecord::getNodeId, DeviceRecordBpmNodeEnum.DEVICE_RECORD_APPLY.getNode())
					.orderByDesc(ApproveRecord::getCreateTime).last("LIMIT 1"));
				hussarCompleteParam.setParticipantWithUsers(approveRecord.getCreateUser().toString());
			}
			Map<String, Object> variable = new HashMap<>();
			if (DeviceRecordBpmNodeEnum.DEVICE_RECORD_PROFESSIONAL_SERVICE.getNode().equals(recordStatus) && 0 == erpDeviceOrderDTO.getErpExamineStatus()) {
				processStatus = DeviceRecordBpmNodeEnum.DEVICE_RECORD_PROFESSIONAL_FINANCE.getNode();
				erpDeviceOrderDTO.setErpExamineReason("ERP业务层审批通过");
			}
			boolean finishFlag = false;
			if (DeviceRecordBpmNodeEnum.DEVICE_RECORD_PROFESSIONAL_FINANCE.getNode().equals(recordStatus) && 0 == erpDeviceOrderDTO.getErpExamineStatus()) {
				if (recordBoolean) {
					erpDeviceOrderDTO.setErpExamineReason(null);
					long count = deviceRecordListService.count(new LambdaQueryWrapper<DeviceRecordList>().eq(DeviceRecordList::getRecordId, deviceRecord.getId())
						.isNull(DeviceRecordList::getErpAssetCode));
					if (count == erpDeviceOrderDTO.getDeviceRecordListDTOList().size()) {
						finishFlag = true;
						processStatus = DeviceRecordBpmNodeEnum.DEVICE_RECORD_FINISH.getNode();
						erpDeviceOrderDTO.setErpExamineReason("ERP财务层审批通过");
					}
				} else {
					List<DeviceRecordList> list = deviceRecordListService.list(new LambdaQueryWrapper<DeviceRecordList>()
						.eq(DeviceRecordList::getRecordId, deviceRecord.getId()));
					List<ErpDeviceDetailDTO> deviceRecordListDTOList1 = new ArrayList<>();
					for (DeviceRecordList deviceRecordList : list) {
						deviceRecordList.setErpStatus("2");
						deviceRecordList.setErpAssetStatus("0");
						String erpNum = orderNumberUtil.generateRecordErpNumber(CacheNames.DEVICE_RECORD_ERP_NUMBER);
						deviceRecordList.setErpAssetCode(erpNum);
						String erpAccountCode = erpNum.replaceAll(Constants.RECORD_ERP_TEST, Constants.RECORD_ERP_ACC_TEST);

						deviceRecordList.setErpAccountCode(erpAccountCode);
						ErpDeviceDetailDTO erpDeviceDetailDTO = new ErpDeviceDetailDTO();
						erpDeviceDetailDTO.setId(deviceRecordList.getDeviceUuid());
						erpDeviceDetailDTO.setErpAccountCode(erpAccountCode);
						erpDeviceDetailDTO.setErpAssetCode(erpNum);
						deviceRecordListDTOList1.add(erpDeviceDetailDTO);
					}
					finishFlag = true;
					addErpDeatilList(deviceRecordListDTOList1, deviceRecord);
					processStatus = DeviceRecordBpmNodeEnum.DEVICE_RECORD_FINISH.getNode();
					deviceRecordListService.updateBatchById(list);
					erpDeviceOrderDTO.setErpExamineReason("ERP财务层审批通过");
				}
			}
			if (1 == erpDeviceOrderDTO.getErpExamineStatus()) {
				processStatus = DeviceRecordBpmNodeEnum.DEVICE_RECORD_APPLY.getNode();
				variable.put("flag", 1);
			} else {
				variable.put("flag", 0);
			}
			variable.put("orderNo", erpDeviceOrderDTO.getFilingNo());
			hussarCompleteParam.setVariable(variable);
			hussarCompleteParam.setComment(erpDeviceOrderDTO.getErpExamineReason());
			/*if ((DeviceRecordBpmNodeEnum.DEVICE_RECORD_PROFESSIONAL_SERVICE.getNode().equals(recordStatus) && 0 == erpDeviceOrderDTO.getErpExamineStatus())
				|| (DeviceRecordBpmNodeEnum.DEVICE_RECORD_PROFESSIONAL_FINANCE.getNode().equals(recordStatus) && 0 == erpDeviceOrderDTO.getErpExamineStatus() && finishFlag)) {*/
			// 增加操作记录
			LogOpt logOpt = LogOpt.builder().logId(erpDeviceOrderDTO.getDeviceRecordId()).logData(erpDeviceOrderDTO.toString()).params(erpDeviceOrderDTO.toString())
				.optType(OptTypeEnum.DEVICE_FILING.getCode()).title(erpDeviceOrderDTO.getErpExamineReason()).optRole("--").build();
			// 代表erp操作日志
			logOpt.setStatus(0);
			logOptService.commonLogOpt(logOpt);
			// 记录审核流程
			ApproveRecord record = ApproveRecord.builder().wbsId(deviceRecord.getWbsProject()).filingNo(erpDeviceOrderDTO.getDeviceRecordId())
				.optType(OptTypeEnum.DEVICE_FILING.getCode()).nodeId(hussarTaskVo.getTaskDefinitionKey())
				.nodeName(DeviceRecordBpmNodeEnum.getMessage(hussarTaskVo.getTaskDefinitionKey())).optTitle(erpDeviceOrderDTO.getErpExamineReason())
				.optOpinion(erpDeviceOrderDTO.getErpExamineReason()).optRole("ERP审核角色")
				.approveStatus(erpDeviceOrderDTO.getErpExamineStatus()).filingCode(deviceRecord.getFilingNo()).build();
			approveRecordService.commonRecord(record);
			// }
			if (DeviceRecordBpmNodeEnum.DEVICE_RECORD_PROFESSIONAL_FINANCE.getNode().equals(recordStatus) && 0 == erpDeviceOrderDTO.getErpExamineStatus()) {
				if (recordBoolean) {
					if (CollectionUtils.isNotEmpty(erpDeviceOrderDTO.getDeviceRecordListDTOList())) {
						addErpDeatilList(deviceRecordListDTOList, deviceRecord);
						deviceRecordListService.updateBatchDeviceId(erpDeviceOrderDTO.getDeviceRecordListDTOList());
					}
				}
				if (finishFlag) {
					// 增加操作记录
					LogOpt build = LogOpt.builder().logId(erpDeviceOrderDTO.getDeviceRecordId()).logData(erpDeviceOrderDTO.toString()).params(erpDeviceOrderDTO.toString()).optRole("--")
						.optType(OptTypeEnum.DEVICE_FILING.getCode()).title(DeviceRecordBpmNodeEnum.getMessage(DeviceRecordBpmNodeEnum.DEVICE_RECORD_FINISH.getNode())).optName("系统").build();
					build.setStatus(0);
					logOptService.commonLogOpt(build);
					// 记录审核流程
					ApproveRecord approveRecord = ApproveRecord.builder().wbsId(deviceRecord.getWbsProject()).filingNo(erpDeviceOrderDTO.getDeviceRecordId())
						.optType(OptTypeEnum.DEVICE_FILING.getCode()).nodeId(DeviceRecordBpmNodeEnum.DEVICE_RECORD_FINISH.getNode())
						.nodeName(DeviceRecordBpmNodeEnum.getMessage(DeviceRecordBpmNodeEnum.DEVICE_RECORD_FINISH.getNode()))
						.optTitle(DeviceRecordBpmNodeEnum.getMessage(DeviceRecordBpmNodeEnum.DEVICE_RECORD_FINISH.getNode()))
						.optOpinion(DeviceRecordBpmNodeEnum.getMessage(DeviceRecordBpmNodeEnum.DEVICE_RECORD_FINISH.getNode()))
						.approveStatus(0).filingCode(erpDeviceOrderDTO.getFilingNo())
						.optName("系统").optRole("--").build();
					approveRecord.setStatus(1);
					approveRecordService.commonRecord(approveRecord);
					// 更新工单信息
					baseMapper.update(new LambdaUpdateWrapper<DeviceRecord>().eq(DeviceRecord::getId, erpDeviceOrderDTO.getDeviceRecordId()).set(DeviceRecord::getUpdateTime, new Date())
						.set(DeviceRecord::getProcessStatus, DeviceRecordBpmNodeEnum.DEVICE_RECORD_FINISH.getNode()).set(DeviceRecord::getStatus, "3").set(DeviceRecord::getErpStatus, "2")
						.set((StringUtils.isNotBlank(erpDeviceOrderDTO.getUseKeepDept()) && !erpDeviceOrderDTO.getUseKeepDept().equals(deviceRecord.getUseKeepDept())),
							DeviceRecord::getUseKeepDept, erpDeviceOrderDTO.getUseKeepDept())
						.set((StringUtils.isNotBlank(erpDeviceOrderDTO.getUseKeepDeptName()) && !erpDeviceOrderDTO.getUseKeepDeptName().equals(deviceRecord.getUseKeepDeptName())),
							DeviceRecord::getUseKeepDeptName, erpDeviceOrderDTO.getUseKeepDeptName()));
					com.lnsoft.hussar.bpm.tool.api.R<List<HussarComplateVo>> listComplete = hussarBpmClient.flowSubmit(hussarCompleteParam);
					if (ResultCode.SUCCESS.getCode() != listComplete.getCode()) {
						return R.fail(listR.getMsg());
					}
				}
			} else {
				// 更新工单信息
				baseMapper.update(new LambdaUpdateWrapper<DeviceRecord>().eq(DeviceRecord::getId, erpDeviceOrderDTO.getDeviceRecordId()).set(DeviceRecord::getUpdateTime, new Date())
					.set((StringUtils.isNotBlank(erpDeviceOrderDTO.getUseKeepDept()) && !erpDeviceOrderDTO.getUseKeepDept().equals(deviceRecord.getUseKeepDept())),
						DeviceRecord::getUseKeepDept, erpDeviceOrderDTO.getUseKeepDept())
					.set((StringUtils.isNotBlank(erpDeviceOrderDTO.getUseKeepDeptName()) && !erpDeviceOrderDTO.getUseKeepDeptName().equals(deviceRecord.getUseKeepDeptName())),
						DeviceRecord::getUseKeepDeptName, erpDeviceOrderDTO.getUseKeepDeptName())
					.set(DeviceRecord::getProcessStatus, processStatus));
				com.lnsoft.hussar.bpm.tool.api.R<List<HussarComplateVo>> listComplete = hussarBpmClient.flowSubmit(hussarCompleteParam);
				if (ResultCode.SUCCESS.getCode() != listComplete.getCode()) {
					return R.fail(listR.getMsg());
				}
			}
		}
		return R.success(ResultCode.SUCCESS);
	}

	/**
	 * 用于记录erp的生成情况
	 *
	 * @param deviceRecordListDTOList
	 * @param deviceRecord
	 * @return
	 */
	private Boolean addErpDeatilList(List<ErpDeviceDetailDTO> deviceRecordListDTOList, DeviceRecord deviceRecord) {
		List<ProjectManagerDetail> detailList = deviceRecordListDTOList.stream().map(item ->
			ProjectManagerDetail.builder()
				.uuid(item.getId())
				.erpAssetCode(item.getErpAssetCode())
				.erpAccountCode(item.getErpAccountCode())
				.deviceType(deviceRecord.getDeviceType())
				.wbsCode(deviceRecord.getWbsElement())
				.wbsName(deviceRecord.getWbsProject())
				.stage(WorkOrderTypeEnum.JD.getText())
				.erpStatus(2)
				.erpTransferStatus(cmdbCientityProperties.getErpTransferStatus2())
				.build()
		).collect(Collectors.toList());
		Boolean result = projectManagerDetailService.saveOrUpdateBatch(detailList);
		return result;
	}
	/**
	 * 名称校验规则
	 */
	private R nameCheck(String name){
		//名称中包含关键词校验
		List<String> invalidKeywords = checkProperties.getInvalidKeywords();
		for (String invalidKeyword : invalidKeywords) {
			if (name.contains(invalidKeyword)){
				return R.fail("设备名称中包含特殊词语 ["+invalidKeyword+"]，请进行修改");
			}
		}
		//特殊符号校验
		List<String> invalidCharacters = checkProperties.getInvalidCharacters();
		for (String invalidCharacter : invalidCharacters) {
			if (name.contains(invalidCharacter)){
				return R.fail("设备名称中包含特殊符号 ["+invalidCharacter+"]，请进行修改");
			}
		}
		//名称以关键词结尾校验
		List<String> invalidEndings = checkProperties.getInvalidEndings();
		for (String invalidEnding : invalidEndings) {
			if (name.endsWith(invalidEnding)){
				return R.fail("设备名称以特殊名称 ["+invalidEnding+"]结尾，请进行修改");
			}
		}
		//包含特殊词汇校验
		List<String> invalidEqualWords = checkProperties.getInvalidEqualWords();
		for (String invalidEqualWord : invalidEqualWords) {
			if (name.contains(invalidEqualWord)){
				return R.fail("设备名称中包含特殊词语 ["+invalidEqualWord+"]，请进行修改");
			}
		}
		return R.success("校验成功");
	}
}
