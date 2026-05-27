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

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lnsoft.cmdb.entity.FeignCiCientity;
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
import com.lnsoft.core.tool.utils.DateUtil;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.operation.dto.DeviceChangeDTO;
import com.lnsoft.device.api.operation.entity.DeviceChange;
import com.lnsoft.device.api.operation.entity.DeviceChangeList;
import com.lnsoft.common.enums.hussar.DeviceChangeTypeEnum;
import com.lnsoft.device.api.operation.mapper.DeviceChangeMapper;
import com.lnsoft.device.api.operation.mapper.DeviceRepairMapper;
import com.lnsoft.device.api.operation.service.IDeviceChangeService;
import com.lnsoft.device.api.operation.vo.DeviceChangeVO;
import com.lnsoft.device.entity.ApproveRecord;
import com.lnsoft.device.entity.LogOpt;
import com.lnsoft.device.api.res.service.IApproveRecordService;
import com.lnsoft.device.api.res.service.ILogOptService;
import com.lnsoft.device.api.safeaccess.mapper.SafeaccessSwitcheMapper;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessIppoolService;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessSwitcheService;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessUserAccessDisableService;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessUserAccessService;
import com.lnsoft.device.api.warehouse.dto.*;
import com.lnsoft.device.api.warehouse.entity.*;
import com.lnsoft.common.enums.hussar.DeviceReturnedBpmNodeEnum;
import com.lnsoft.common.enums.hussar.DeviceReturnedEnum;
import com.lnsoft.device.api.warehouse.mapper.*;
import com.lnsoft.device.api.warehouse.service.ICheckTaskDeviceService;
import com.lnsoft.device.api.warehouse.service.IDeviceApplyService;
import com.lnsoft.device.api.warehouse.service.IDeviceOperationService;
import com.lnsoft.device.api.warehouse.vo.*;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.constant.CmdbCientityConstant;
import com.lnsoft.device.entity.*;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.common.enums.hussar.OptTypeEnum;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.utils.OrderNumberUtil;
import com.lnsoft.device.vo.CiCientitySearchVO;
import com.lnsoft.device.vo.DeviceRepairVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 盘点任务设备详情 服务实现类
 *
 * @author Idevelop
 * @since 2024-04-19
 */
@Service
@Slf4j
public class CheckTaskDeviceServiceImpl extends BaseServiceImpl<CheckTaskDeviceMapper, CheckTaskDevice> implements ICheckTaskDeviceService {

	@Resource
	private CheckTaskMapper taskService;
	@Resource
	private OrderNumberUtil orderNumberUtil;
	@Resource
	private ILogOptService logOptService;
	@Resource
	private DeviceReturnedMapper returnedMapper;
	@Resource
	private DeviceReturnedDetailMapper returnedDetailMapper;
	@Resource
	private IDeviceOperationService deviceOperationService;
	@Resource
	private CmdbCientityProperties cmdbCientityProperties;
	@Resource
	private SafeaccessSwitcheMapper safeaccessSwitcheService;
	@Autowired
	private ISafeaccessIppoolService safeaccessIppoolService;
	@Resource
	private ISafeaccessSwitcheService safeAccessSwitchesService;
	@Resource
	private ISafeaccessUserAccessService userAccessService;
	@Resource
	private ICmdbService iCmdbService;
	@Resource
	private IDeviceChangeService deviceChangeService;
	@Resource
	private DeviceStorageMapper deviceStorageMapper;
	@Resource
	private DeviceOutboundMapper deviceOutboundMapper;
	@Resource
	private DeviceApplyMapper deviceApplyMapper;
	@Resource
	private DeviceOperationMapper deviceOperationMapper;
	@Resource
	private DeviceChangeMapper deviceChangeMapper;
	@Resource
	private DeviceRepairMapper deviceRepairMapper;
	@Resource
	private CheckTaskMapper checkTaskMapper;
	@Resource
	private IDeviceChangeService changeService;
	@Resource
	private IDeviceApplyService applyService;
	@Resource
	private ISafeaccessUserAccessDisableService safeaccessUserAccessDisableService;
	@Resource
	private ISafeaccessUserAccessService safeaccessUserAccessService;
	@Resource
	private IApproveRecordService approveRecordService;

	@Override
	public IPage<CheckTaskDeviceVO> selectCheckTaskDevicePage(IPage<CheckTaskDeviceVO> page, CheckTaskDeviceVO checkTaskDevice) {
		return page.setRecords(baseMapper.selectCheckTaskDevicePage(page, checkTaskDevice));
	}

	/**
	 * 盘点任务设备列表
	 *
	 * @param page
	 * @param queryDto
	 * @return
	 */
	@Override
	public IPage<CheckTaskDevice> getList(IPage<Object> page, CheckDeviceQueryDto queryDto) {
		IdevelopUser user = SecureUtil.getUser();
		if (StringUtil.isNotBlank(queryDto.getLastDeviceStr())) {
			queryDto.setLastDevice(Arrays.asList(queryDto.getLastDeviceStr().split(",")));
		}
		if ("1".equals(queryDto.getIsOwner())){
			String account = user.getAccount();
			queryDto.setAccount(account);
		}
		IPage<CheckTaskDevice> taskDevicePage = baseMapper.checkTaskList(page, queryDto);
		return taskDevicePage;
	}

	/**
	 * 更新盘点任务设备处置状态
	 *
	 * @param dto
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<Integer> editTaskDevice(CheckTaskDeviceDTO dto) throws Exception {
		//校验
		IdevelopUser user = SecureUtil.getUser();
		Map<String, Object> ext = user.getExt();
		String phone = String.valueOf(ext.get("phone"));
		Date date = new Date();
		CheckTaskDevice checkTaskDevice = baseMapper.selectOne(Wrappers.<CheckTaskDevice>lambdaQuery().eq(CheckTaskDevice::getId, dto.getId()));
		if (dto.getEditType().equals("0")) {
			OrderUpdateStatusDTO orderUpdateStatusDTO = new OrderUpdateStatusDTO();
			orderUpdateStatusDTO.setComment(dto.getDisComment());
			orderUpdateStatusDTO.setWorkerStatus(dto.getWorkerStatus());
			DeviceChange deviceChange = deviceChangeMapper.selectOne(Wrappers.<DeviceChange>lambdaQuery().eq(DeviceChange::getFilingNo, dto.getFilingNo()));
			String id = deviceChange.getId();
			orderUpdateStatusDTO.setId(id);
			changeService.deskUpdateStatus(orderUpdateStatusDTO);
			//异常设备处置
			baseMapper.update(Wrappers.<CheckTaskDevice>lambdaUpdate()
				.set(CheckTaskDevice::getDisPerson, user.getUserName())
				.set(CheckTaskDevice::getDisTel, phone)
				.set(CheckTaskDevice::getDisDept, user.getDeptName())
				.set(CheckTaskDevice::getDisTime, date)
				.set(CheckTaskDevice::getDisposeResult, dto.getDisposeResult())
				.set(CheckTaskDevice::getDisComment, dto.getDisComment())
				.set(CheckTaskDevice::getCheckStatus, "0")
				.set(CheckTaskDevice::getUpdateUser, user.getUserId())
				.set(CheckTaskDevice::getUpdateTime, date)
				.set(CheckTaskDevice::getDisposeStatus, dto.getWorkerStatus().equals("0") ? "1" : "0")
				.eq(CheckTaskDevice::getId, dto.getId()).eq(CheckTaskDevice::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		} else if (dto.getEditType().equals("1")) {
			String fillingNo = null;
			if (dto.getDisposeResult().equals("1") && cmdbCientityProperties.getCientityId(CmdbCientityConstant.IN_OPERATION).equals(dto.getDeviceStatusCode())) {
				//生成投运单
				fillingNo = taskCreateDeviceOperation(user, dto.getId());
			}else {
				log.info("存台账");
			}
			//盘盈设备处置
			baseMapper.update(Wrappers.<CheckTaskDevice>lambdaUpdate().set(CheckTaskDevice::getDisPerson, user.getUserName()).set(CheckTaskDevice::getDisTel, phone).set(CheckTaskDevice::getDisDept, user.getDeptName())
				.set(CheckTaskDevice::getDisTime, date).set(CheckTaskDevice::getDisposeResult, dto.getDisposeResult()).set(CheckTaskDevice::getDisComment, dto.getDisComment()).set(StringUtil.isNotBlank(fillingNo), CheckTaskDevice::getFilingNo, fillingNo)
				.set(CheckTaskDevice::getCheckStatus, "2").set(CheckTaskDevice::getUpdateUser, user.getUserId()).set(CheckTaskDevice::getUpdateTime, date).set(StringUtil.isNotBlank(fillingNo), CheckTaskDevice::getFilingTime, date)
				.set(CheckTaskDevice::getDisposeStatus, dto.getDisposeResult().equals("1") ? "1" : "0").eq(CheckTaskDevice::getId, dto.getId()).eq(CheckTaskDevice::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
			LambdaQueryWrapper<CheckTaskDevice> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(CheckTaskDevice::getTaskId,dto.getTaskId()).eq(CheckTaskDevice::getCheckStatus,1).eq(CheckTaskDevice::getIsDeleted,0);
			List<CheckTaskDevice> checkTaskDevices = baseMapper.selectList(queryWrapper);
			if (ObjectUtil.isNotEmpty(checkTaskDevices)){
				CheckTask checkTask = new CheckTask();
				checkTask.setId(dto.getTaskId());
				checkTask.setStatus(3);
				checkTaskMapper.updateById(checkTask);
			}
		} else{
			String fillingNo = null;
			if (dto.getDisposeResult().equals("3")) {
				//生成退运单
				fillingNo = taskCreateDeviceReturned(user, dto.getId());
			}else if (dto.getDisposeResult().equals("5")){
				//临时退网
				SafeaccessUserAccessDisable disable = new SafeaccessUserAccessDisable();
				disable.setMac(checkTaskDevice.getMac());
				disable.setDeviceCode(checkTaskDevice.getDeviceCode());
				disable.setIsDeleted(0);
				safeaccessUserAccessDisableService.save(disable);
				safeaccessUserAccessService.update(Wrappers.<SafeaccessUserAccess>lambdaUpdate()
					.set(SafeaccessUserAccess::getDisableStatus,"1")
					.eq(SafeaccessUserAccess::getDeviceCode,checkTaskDevice.getDeviceCode()));
			}else if (dto.getDisposeResult().equals("4")){
				//恢复入网
				SafeaccessUserAccessDisable one = safeaccessUserAccessDisableService.getOne(Wrappers.<SafeaccessUserAccessDisable>lambdaQuery()
					.eq(SafeaccessUserAccessDisable::getDeviceCode, checkTaskDevice.getDeviceCode()));
				String mac = one.getMac();
				safeaccessUserAccessService.update(Wrappers.<SafeaccessUserAccess>lambdaUpdate()
					.set(SafeaccessUserAccess::getMacAddress,mac)
					.set(SafeaccessUserAccess::getDisableStatus,"2")
					.eq(SafeaccessUserAccess::getDeviceCode,checkTaskDevice.getDeviceCode()));
			}
			//盘亏设备处置
			baseMapper.update(Wrappers.<CheckTaskDevice>lambdaUpdate()
				.set(CheckTaskDevice::getDisPerson, user.getUserName())
				.set(CheckTaskDevice::getDisTel, phone)
				.set(CheckTaskDevice::getDisDept, user.getDeptName())
				.set(CheckTaskDevice::getDisTime, date)
				.set(CheckTaskDevice::getDisposeResult, dto.getDisposeResult())
				.set(CheckTaskDevice::getDisComment, dto.getDisComment())
				.set(StringUtil.isNotBlank(fillingNo), CheckTaskDevice::getFilingNo, fillingNo)
				.set(CheckTaskDevice::getCheckStatus, "3")
				.set(CheckTaskDevice::getUpdateUser, user.getUserId())
				.set(CheckTaskDevice::getUpdateTime, date)
				.set(StringUtil.isNotBlank(fillingNo), CheckTaskDevice::getFilingTime, date)
				.set(dto.getDisposeResult().equals("5"), CheckTaskDevice::getOfflineStartTime, date)
				.set(dto.getDisposeResult().equals("4"), CheckTaskDevice::getOfflineEndTime, date)
				.set(CheckTaskDevice::getDisposeStatus, "1")
				.eq(CheckTaskDevice::getId, dto.getId())
				.eq(CheckTaskDevice::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
			LambdaQueryWrapper<CheckTaskDevice> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(CheckTaskDevice::getTaskId,dto.getTaskId()).eq(CheckTaskDevice::getCheckStatus,1).eq(CheckTaskDevice::getIsDeleted,0);
			List<CheckTaskDevice> checkTaskDevices = baseMapper.selectList(queryWrapper);
			if (ObjectUtil.isNotEmpty(checkTaskDevices)){
				CheckTask checkTask = new CheckTask();
				checkTask.setId(dto.getTaskId());
				checkTask.setStatus(3);
				checkTaskMapper.updateById(checkTask);
			}
		}
		return R.success(ResultCode.SUCCESS);
	}

	/**
	 * 生成投运信息
	 *
	 * @param user
	 * @param id
	 */
	public String taskCreateDeviceOperation(IdevelopUser user, String id) throws Exception {
		DeviceOperationDTO operationDTO = new DeviceOperationDTO();
		DeviceOperationDetailDTO operationDetailDTO = new DeviceOperationDetailDTO();
		CheckTaskDevice checkTaskDevice = baseMapper.selectOne(Wrappers.<CheckTaskDevice>lambdaQuery().eq(CheckTaskDevice::getId, id).eq(CheckTaskDevice::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		operationDTO.setOldToNew("1");
		operationDTO.setOperation("0");
		operationDTO.setDeviceType(checkTaskDevice.getDeviceTypeCode());
		operationDTO.setDeviceTypeName(checkTaskDevice.getDeviceType());
		operationDTO.setDeviceCategory(checkTaskDevice.getDeviceCategoryCode());
		operationDTO.setDeviceCategoryName(checkTaskDevice.getDeviceCategory());
		operationDTO.setReceiveUnit(checkTaskDevice.getReceiveUnitCode());
		operationDTO.setReceiveUnitName(checkTaskDevice.getReceiveUnit());
		operationDTO.setReceiveDutyDept(checkTaskDevice.getReceiveDeptCode());
		operationDTO.setReceiveDutyDeptName(checkTaskDevice.getReceiveDept());
		operationDTO.setApplyUser(user.getUserId().toString());
		operationDTO.setApplyUserName(user.getUserName());
		operationDTO.setApplyDate(new Date());
		operationDTO.setRegionCode(user.getRegionCode());
		operationDTO.setOperationUnit(user.getCorpId());
		operationDTO.setOperationUnitName(user.getCorpName());
		operationDTO.setOperationDept(user.getDeptId());
		operationDTO.setOperationDeptName(user.getDeptName());
		operationDTO.setOperationUse(user.getUserId().toString());
		operationDTO.setOperationUseName(user.getUserName());
		operationDTO.setOperationPhone(user.getExt().get("phone").toString());
		operationDetailDTO.setFirstReceiveFlag(0);
		operationDetailDTO.setSwitchesType(checkTaskDevice.getDeviceType().contains("接入层交换") ? 0 : 1);
		operationDetailDTO.setDeviceType(checkTaskDevice.getDeviceTypeCode());
		operationDetailDTO.setDeviceTypeName(checkTaskDevice.getDeviceType());
		operationDetailDTO.setDeviceCategory(checkTaskDevice.getDeviceCategoryCode());
		operationDetailDTO.setDeviceCategoryName(checkTaskDevice.getDeviceCategory());
		operationDetailDTO.setDeviceId(checkTaskDevice.getDeviceId().toString());
		operationDetailDTO.setDeviceCid(checkTaskDevice.getCiId().toString());
		operationDetailDTO.setDeviceUuid(checkTaskDevice.getUuid());
		operationDetailDTO.setUserName(checkTaskDevice.getUser());
		operationDetailDTO.setUserPhone(checkTaskDevice.getUserTel());
		operationDetailDTO.setUserCard(checkTaskDevice.getDeviceUserIDCard());
		operationDetailDTO.setDeviceCode(checkTaskDevice.getDeviceCode());
		operationDetailDTO.setDeviceName(checkTaskDevice.getDeviceName());
		operationDetailDTO.setDeviceStatus(checkTaskDevice.getDeviceStatusCode());
		operationDetailDTO.setFactoryNumber(checkTaskDevice.getFactorySerial());
		operationDetailDTO.setUserTime(new Date());
		operationDetailDTO.setUserType(checkTaskDevice.getUserType().equals("个人") ? 1 : 0);
		operationDetailDTO.setDeviceSubnet(checkTaskDevice.getSubnetId());
		operationDetailDTO.setDeviceSubnetName(checkTaskDevice.getSubnetName());
		operationDetailDTO.setNetworkType(checkTaskDevice.getNetWorkCode());
		operationDetailDTO.setDeviceIp(checkTaskDevice.getIp());
		operationDetailDTO.setDeviceMac(checkTaskDevice.getMac());
		operationDetailDTO.setOperationUnit(user.getCorpId());
		operationDetailDTO.setOperationUnitName(user.getCorpName());
		operationDetailDTO.setOperationDept(user.getDeptId());
		operationDetailDTO.setOperationDeptName(user.getDeptName());
		operationDetailDTO.setOperationUse(user.getUserId().toString());
		operationDetailDTO.setOperationPhone(user.getExt().get("phone").toString());
		operationDetailDTO.setBrand(checkTaskDevice.getBrandCode());
		operationDetailDTO.setBrandName(checkTaskDevice.getBrand());
		operationDetailDTO.setSeries(checkTaskDevice.getSeriesCode());
		operationDetailDTO.setSeriesName(checkTaskDevice.getSeries());
		operationDetailDTO.setDeviceModel(checkTaskDevice.getDeviceModelCode());
		operationDetailDTO.setDeviceModelName(checkTaskDevice.getDeviceModel());
		operationDetailDTO.setIsItal(checkTaskDevice.getIsITAICode());
		operationDetailDTO.setOprtDept(checkTaskDevice.getOprtDeptCode());
		operationDetailDTO.setOprtDeptName(checkTaskDevice.getOprtDept());
		operationDetailDTO.setReceiveUnit(checkTaskDevice.getReceiveUnitCode());
		operationDetailDTO.setReceiveUnitName(checkTaskDevice.getReceiveUnit());
		operationDetailDTO.setReceiveDutyDept(checkTaskDevice.getReceiveDeptCode());
		operationDetailDTO.setReceiveDutyDeptName(checkTaskDevice.getReceiveDept());
		operationDetailDTO.setReceiveUseName(checkTaskDevice.getReceivingPerson());
		operationDetailDTO.setReceiveUseCard(checkTaskDevice.getReceivingIDCard());
		operationDetailDTO.setReceiveUsePhone(checkTaskDevice.getReceivingTel());
		operationDetailDTO.setTemporaryType(checkTaskDevice.getIsInterim().equals("是") ? 0 : 1);
		operationDTO.setDeviceOperationDetailDTOList(Arrays.asList(operationDetailDTO));
		R<DeviceOperationVO> result = deviceOperationService.submitOperation(operationDTO);
		return result.getData().getOperationNo();
	}

	/**
	 * 生成退运信息
	 *
	 * @param user
	 * @param id
	 */
	private String taskCreateDeviceReturned(IdevelopUser user, String id) {
		String phone = user.getExt().get("phone").toString();
		DeviceReturned returned = new DeviceReturned();
		returned.setFilingNo(orderNumberUtil.generateNumber(WorkOrderTypeEnum.TYD));
		returned.setReturnReason("盘点任务生成");
		returned.setAcceptPhone(phone);
		returned.setDevicePhoto("");
		returned.setDeviceReturnNum("1");
		returned.setRegionCode(user.getRegionCode());
		returned.setAcceptUser(user.getUserId());
		returned.setAcceptUserName(user.getRealName());
		returned.setApplyDept(user.getDeptId());
		returned.setApplyDeptName(user.getDeptName());
		returned.setApplyUnit(user.getCorpId());
		returned.setApplyUnitName(user.getCorpName());
		returned.setAcceptTime(DateUtil.formatDateTime(new Date()));
		returned.setCreateTime(new Date());
		returned.setCreateUser(user.getUserId());
		returned.setStatus(DeviceReturnedEnum.FINISH.getCode());
		returned.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
		returned.setProcessStatus(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_FINISH.getNode());
		returned.setIsAuto("0");
		returnedMapper.insert(returned);
		CheckTaskDevice checkTaskDevice = baseMapper.selectOne(Wrappers.<CheckTaskDevice>lambdaQuery().eq(CheckTaskDevice::getId, id).eq(CheckTaskDevice::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		DeviceReturnedDetail returnedDetail = new DeviceReturnedDetail();
		BeanUtil.copyProperties(checkTaskDevice, returnedDetail);
		returnedDetail.setId(null);
		returnedDetail.setReturnedId(returned.getId());
		returnedDetail.setDeviceStatus(cmdbCientityProperties.getReturnWarehouse());
		returnedDetail.setDeviceCategory(checkTaskDevice.getDeviceCategoryCode());
		returnedDetail.setDeviceType(checkTaskDevice.getDeviceTypeCode());
		returnedDetail.setDeviceIp(checkTaskDevice.getIp());
		returnedDetail.setDeviceMac(checkTaskDevice.getMac());
		returnedDetail.setCreateTime(new Date());
		returnedDetail.setCreateUser(user.getUserId());
		returnedDetail.setCreateDept(user.getDeptId());
		returnedDetail.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
		returnedDetail.setDeviceIp(checkTaskDevice.getIp());
		if (checkTaskDevice.getDeviceType().contains("交换")) {
			SafeaccessSwitche switche = safeaccessSwitcheService.selectOne(Wrappers.<SafeaccessSwitche>lambdaQuery().eq(SafeaccessSwitche::getIsDeleted, IdevelopConstant.DB_NOT_DELETED).eq(SafeaccessSwitche::getSwState, "1").eq(SafeaccessSwitche::getDeviceUuid, checkTaskDevice.getUuid()));
			returnedDetail.setSwIp(switche.getSwIp());
			returnedDetail.setSwPass(switche.getSwPass());
		}
		returnedDetailMapper.insert(returnedDetail);
		// 增加操作记录
		LogOpt createLogOpt = LogOpt.builder().logId(returned.getId()).logData(returned.toString()).params(returned.toString()).optRole("--")
			.optType(OptTypeEnum.DEVICE_RETURNED.getCode())
			.title(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_APPLY.getNode())).optName("系统").build();
		createLogOpt.setStatus(0);
		logOptService.commonLogOpt(createLogOpt);
		// 记录审核流程
		ApproveRecord approveRecord = ApproveRecord.builder().filingNo(returned.getId())
			.optType(OptTypeEnum.DEVICE_RETURNED.getCode())
			.nodeId(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_APPLY.getNode())
			.nodeName(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_APPLY.getNode()))
			.optTitle(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_APPLY.getNode()))
			.optOpinion(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_APPLY.getNode()))
			.approveStatus(0).filingCode(returned.getFilingNo())
			.optName("系统").optRole("--").build();
		approveRecord.setStatus(1);
		approveRecordService.commonRecord(approveRecord);
		// 增加归档记录
		LogOpt logOpt = LogOpt.builder().logId(returned.getId()).logData(returned.toString()).params(returned.toString()).optRole("--")
			.optType(OptTypeEnum.DEVICE_RETURNED.getCode())
			.title(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_FINISH.getNode())).optName("系统").build();
		logOpt.setStatus(0);
		logOptService.commonLogOpt(logOpt);
		// 记录审核流程
		ApproveRecord record = ApproveRecord.builder().filingNo(returned.getId())
			.optType(OptTypeEnum.DEVICE_RETURNED.getCode())
			.nodeId(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_FINISH.getNode())
			.nodeName(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_FINISH.getNode()))
			.optTitle(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_FINISH.getNode()))
			.optOpinion(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_FINISH.getNode()))
			.optName("系统").optRole("--").filingCode(returned.getFilingNo()).build();
		record.setStatus(1);
		approveRecordService.commonRecord(record);
		//释放IP
		safeaccessIppoolService.releaseIpBySubnetIdAndIp(returnedDetail.getSubnetId(), returnedDetail.getDeviceIp());
		//清除入网信息
		userAccessService.update(Wrappers.<SafeaccessUserAccess>lambdaUpdate().set(SafeaccessUserAccess::getIsDeleted, IdevelopConstant.DB_IS_DELETED).eq(SafeaccessUserAccess::getDeviceId, returnedDetail.getDeviceId()));
		//修改交换机资源
		if (checkTaskDevice.getDeviceType().contains("交换")) {
			safeAccessSwitchesService.update(Wrappers.<SafeaccessSwitche>lambdaUpdate().set(SafeaccessSwitche::getSwState, "0").eq(SafeaccessSwitche::getDeviceId, returnedDetail.getDeviceId()));
		}
		//同步cmdb
		updateCmdbEntity(returnedDetail);
		return returned.getFilingNo();
	}

	@Override
	public CheckTaskDeviceVO getTaskDevice(String id, String editType) {
		CheckTaskDevice taskDevice = getOne(Wrappers.<CheckTaskDevice>lambdaQuery().eq(CheckTaskDevice::getId, id).eq(CheckTaskDevice::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		CheckTask task = taskService.selectOne(Wrappers.<CheckTask>lambdaQuery().eq(CheckTask::getId, taskDevice.getTaskId()).eq(CheckTask::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		CheckTaskDeviceVO vo = new CheckTaskDeviceVO();
		if (Objects.isNull(taskDevice) || Objects.isNull(task)) {
			return vo;
		}
		BeanUtil.copyProperties(taskDevice, vo);
		if (editType.equals("0")) {
			//异常设备
			vo.setHandleVos(JSONObject.parseArray(vo.getChangeContent(), CheckDeviceHandleVo.class));
		} else {
			//盘盈设备
			vo.setDevices(Arrays.asList(taskDevice));
		}
		vo.setTaskNo(task.getFilingNo());
		vo.setTaskName(task.getTaskName());
		return vo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R addTestData() {
		List<CheckDeviceHandleVo> list = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			CheckDeviceHandleVo handleVo = new CheckDeviceHandleVo();
			handleVo.setProperty("异常修改测试");
			handleVo.setLastComment("test1");
			handleVo.setNewComment("test2");
			list.add(handleVo);
		}
		String s = JSONObject.toJSONString(list);
		update(Wrappers.<CheckTaskDevice>lambdaUpdate().set(CheckTaskDevice::getChangeContent, s).eq(CheckTaskDevice::getCheckStatus, "1"));
		List<CheckTaskDevice> taskDevices = list(Wrappers.<CheckTaskDevice>lambdaQuery().eq(CheckTaskDevice::getCheckStatus, "2"));
		for (CheckTaskDevice taskDevice : taskDevices) {
			taskDevice.setAssetCodeErp("erpAssect");
			taskDevice.setAccountCodeErp("erpCount");
			taskDevice.setFactorySerial("022021201");
			taskDevice.setOprtDept("运行单位");
			taskDevice.setOprtDeptCode("yxdwbm001");
			taskDevice.setReceiveUnit("领用单位");
			taskDevice.setReceiveUnitCode("lydwbm001");
			taskDevice.setReceiveDept("领用部门");
			taskDevice.setReceiveDeptCode("lybmbm001");
			taskDevice.setIp("127.0.0.1");
			taskDevice.setMac("255.255.255.0");
			taskDevice.setIsInterim("否");
			taskDevice.setSubPerson("测试人员");
			taskDevice.setSubTel("131000100000");
			taskDevice.setSubDept("测试部门");
			taskDevice.setSubTime(new Date());
		}
		updateBatchById(taskDevices);
		return R.success(ResultCode.SUCCESS);
	}

	@Override
	public R<IPage<CheckTaskDevice>> getCheckDeviceListByUser(String type, Query query) {
		IdevelopUser user = SecureUtil.getUser();
		List<CheckTask> checkListByUser = taskService.getCheckListByUser(user.getCorpId(), user.getDeptId(), user.getUserId().toString());
		List<String> ids = checkListByUser.stream().map(CheckTask::getId).collect(Collectors.toList());
		IPage<CheckTaskDevice> checkTaskDeviceIPage = baseMapper.selectPage(Condition.getPage(query), Wrappers.<CheckTaskDevice>lambdaQuery()
			.eq(CheckTaskDevice::getIsDeleted, IdevelopConstant.DB_NOT_DELETED)
			.eq(CheckTaskDevice::getCheckStatus, type)
			.in(ObjectUtil.isNotEmpty(ids), CheckTaskDevice::getTaskId, ids));
//		List<CheckTaskDevice> taskDeviceList = baseMapper.selectList(Wrappers.<CheckTaskDevice>lambdaQuery()
//			.eq(CheckTaskDevice::getIsDeleted, IdevelopConstant.DB_NOT_DELETED)
//			.eq(CheckTaskDevice::getCheckStatus, type)
//			.in(ObjectUtil.isNotEmpty(ids),CheckTaskDevice::getTaskId, ids));
		return R.data(checkTaskDeviceIPage);
	}

	/**
	 * 新增或修改盘点设备信息
	 *
	 * @param checkTaskDevice
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public CheckTaskDevice saveDevice(CheckTaskDeviceDTO checkTaskDevice) throws Exception{
		Date date = new Date();
		IdevelopUser user = SecureUtil.getUser();
		CheckTaskDevice taskDevice = getOne(Wrappers.<CheckTaskDevice>lambdaQuery().eq(CheckTaskDevice::getTaskId, checkTaskDevice.getTaskId()).eq(CheckTaskDevice::getDeviceCode, checkTaskDevice.getDeviceCode()).eq(CheckTaskDevice::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		if (Objects.isNull(taskDevice)) {
			taskDevice = new CheckTaskDevice();
			BeanUtils.copyProperties(checkTaskDevice, taskDevice);
			taskDevice.setCheckStatus("2");
			taskDevice.setDisposeResult("0");
			taskDevice.setDisposeStatus("0");
			taskDevice.setIsDeleted(0);
			taskDevice.setSubTime(date);
			taskDevice.setCreateUser(user.getUserId());
			taskDevice.setCreateTime(date);

		} else {
			BeanUtils.copyProperties(checkTaskDevice, taskDevice);
			taskDevice.setSubTime(date);
			// 1.可以直接传变更信息
			if (ObjectUtil.isEmpty(checkTaskDevice.getHandleVos())){
				taskDevice.setDisposeResult("1");
				taskDevice.setDisposeStatus("1");
			}else {
				taskDevice.setDisposeResult("0");
				taskDevice.setDisposeStatus("0");
				taskDevice.setChangeContent(JSONObject.toJSONString(checkTaskDevice.getHandleVos()));
				//发起变更工单
				R<DeviceChangeVO> add = add(checkTaskDevice);
				String filingNo = add.getData().getFilingNo();
				taskDevice.setFilingNo(filingNo);
				taskDevice.setFilingTime(date);
			}
//			// 2.没法传需要确认哪些字段可以变更，例：
//			List<CheckDeviceHandleVo> list = new ArrayList<>();
//			if (!checkTaskDevice.getDeviceCategoryCode().equals(taskDevice.getDeviceCategoryCode())) {
//				CheckDeviceHandleVo vo = new CheckDeviceHandleVo();
//				vo.setProperty("设备分类");
//				vo.setLastComment(taskDevice.getDeviceCategory());
//				vo.setNewComment(checkTaskDevice.getDeviceCategory());
//				list.add(vo);
//			}
			//最后进行数据更新
			taskDevice.setCheckStatus("0");
			taskDevice.setUpdateUser(user.getUserId());
			taskDevice.setUpdateTime(new Date());
		}
		saveOrUpdate(taskDevice);
		return taskDevice;
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public void submitCheck(String id) {
		IdevelopUser user = SecureUtil.getUser();
		update(Wrappers.<CheckTaskDevice>lambdaUpdate().set(CheckTaskDevice::getCheckStatus, "0").set(CheckTaskDevice::getUpdateUser, user.getUserId()).set(CheckTaskDevice::getUpdateTime, new Date())
			.eq(CheckTaskDevice::getId, id).eq(CheckTaskDevice::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
	}

	@Override
	public R<FeignCiCientity> getDeviceByUser(String type, Query query) {
		IdevelopUser user = SecureUtil.getUser();
		List<CiCientitySearchVO> ciCientitySearchVOS = new ArrayList<>();
		FeignCiCientity ciCientityListByClaccify = null;
		if ("0".equals(type)) {
			String account = user.getAccount();
			CiCientitySearchVO searchVO = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.RECEIVE_PERSON_UNIFIED_ACC).attrValue(account).expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO);
			ciCientityListByClaccify = iCmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
		} else {
			String corpId = user.getCorpId();
			CiCientitySearchVO searchVO = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.RECEIVE_UNIT_CODE).attrValue(corpId).expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO);
			ciCientityListByClaccify = iCmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
		}

		return R.data(ciCientityListByClaccify);
	}

	@Override
	public R<FeignCiCientity> getDeviceByIpAndMac(CheckTaskDevice checkTaskDevice, Query query) {
		List<CiCientitySearchVO> ciCientitySearchVOS = new ArrayList<>();
		//根据ip查询
		CiCientitySearchVO searchVO = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.IP).attrValue(checkTaskDevice.getIp()).expression(Expression.EQUAL).build();
		ciCientitySearchVOS.add(searchVO);
		FeignCiCientity cientityListByClaccify = iCmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
		List<CiCientitySearchVO> ciCientitySearchVOS1 = new ArrayList<>();
		//根据mac查询
		CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.MAC).attrValue(checkTaskDevice.getMac()).expression(Expression.EQUAL).build();
		ciCientitySearchVOS1.add(searchVO1);
		FeignCiCientity ciCientityListByClaccify = iCmdbService.getCiCientityListByClaccify(ciCientitySearchVOS1, query);
		//根据ip和mac查询
		List<CiCientitySearchVO> ciCientitySearchVOS2 = new ArrayList<>();
		ciCientitySearchVOS2.add(searchVO);
		ciCientitySearchVOS2.add(searchVO1);
		FeignCiCientity ciCientityListByClaccify1 = iCmdbService.getCiCientityListByClaccify(ciCientitySearchVOS2, query);
		FeignCiCientity feignCiCientity = new FeignCiCientity();
		List<Map<String, Object>> data = new ArrayList<>();
		data.addAll(cientityListByClaccify.getData());
		data.addAll(ciCientityListByClaccify.getData());
		data.addAll(ciCientityListByClaccify1.getData());
		List<Map<String, Object>> mapList = data.stream().distinct().collect(Collectors.toList());
		feignCiCientity.setData(mapList);
		feignCiCientity.setTotal(mapList.size());
		return R.data(feignCiCientity);
	}
	@Override
	public R<DeviceChangeVO> add(CheckTaskDeviceDTO checkTaskDevice) throws Exception {
		DeviceChangeDTO deviceChangeDTO = new DeviceChangeDTO();
		DeviceChange deviceChange = (DeviceChange) deviceChangeService.load().getData();
		BeanUtil.copy(deviceChange, deviceChangeDTO);
		deviceChangeDTO.setTicketCreatType(DeviceChangeTypeEnum.AUTO.getCode());
		deviceChangeDTO.setSubmitStatus("2");
		deviceChangeDTO.setDeviceCount("1");
		List<DeviceChangeList> oldList = new ArrayList<>();
		DeviceChangeList oldDevice = changeAssemble(checkTaskDevice,"old");
		oldList.add(oldDevice);
		deviceChangeDTO.setOldChangeDeviceDTOList(oldList);
		List<DeviceChangeList> newList = new ArrayList<>();
		DeviceChangeList newDevice = changeAssemble(checkTaskDevice,"new");
		newList.add(newDevice);
		deviceChangeDTO.setNewChangeDeviceDTOList(newList);
		List<CheckDeviceHandleVo> handleVos = checkTaskDevice.getHandleVos();
		for (CheckDeviceHandleVo handleVo : handleVos) {
			String property = handleVo.getProperty();
			if ("ip".equals(property) || "mac".equals(property)){
				deviceChangeDTO.setChangeType("2");
			}
		}
		return deviceChangeService.add(deviceChangeDTO);
	}
	private DeviceChangeList changeAssemble(CheckTaskDeviceDTO checkTaskDevice,String type){
		CheckTaskDevice taskDevice = new CheckTaskDevice();
		BeanUtil.copy(checkTaskDevice,taskDevice);
		//变更参数组装
		if ("old".equals(type)){
			List<CheckDeviceHandleVo> handleVos = checkTaskDevice.getHandleVos();
			for (CheckDeviceHandleVo handleVo : handleVos) {
				String property = handleVo.getProperty();
				String lastComment = handleVo.getLastComment();
				try {
					Field field = taskDevice.getClass().getDeclaredField(property);
					field.setAccessible(true);
					field.set(taskDevice,lastComment);
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
		}
		DeviceChangeList device = new DeviceChangeList();
		device.setDeviceId(String.valueOf(taskDevice.getDeviceId()));
		device.setUuid(taskDevice.getUuid());
		device.setDeviceCode(taskDevice.getDeviceCode());
		device.setDeviceCodeErp(taskDevice.getAssetCodeErp());
		device.setFullName(taskDevice.getFullName());
		device.setSn(taskDevice.getFactorySerial());
		device.setReceiveUnit(taskDevice.getReceiveUnit());
		device.setReceiveUnitCode(taskDevice.getReceiveUnitCode());
		device.setReceiveDept(taskDevice.getReceiveDept());
		device.setReceiveDeptCode(taskDevice.getReceiveDeptCode());
		device.setReceivingPerson(taskDevice.getReceivingPerson());
		device.setReceivingIDCard(taskDevice.getReceivingIDCard());
		device.setReceivingTel(taskDevice.getReceivingTel());
		// todo 统一权限账号
		device.setUser(taskDevice.getUser());
		device.setUserTel(taskDevice.getUserTel());
		device.setDeviceUserIDCard(taskDevice.getDeviceUserIDCard());
		device.setInstallationSite(taskDevice.getInstallationSite());
		// todo 运维单位 运维部门 运维责任人 运维等级 运维联系电话
		device.setNetworkAccessMethod(taskDevice.getAuthentication());
		device.setDeviceStatus(taskDevice.getDeviceStatus());
		device.setBrand(taskDevice.getBrand());
		device.setSeries(taskDevice.getSeries());
		device.setDeviceModel(taskDevice.getDeviceModel());
		device.setDeviceSource(taskDevice.getDeviceSource());
		device.setMAC(taskDevice.getMac());
		device.setIP(taskDevice.getIp());
		device.setSubnetId(taskDevice.getSubnetId());
		device.setSubnetName(taskDevice.getSubnetName());
		device.setCiId(String.valueOf(taskDevice.getCiId()));
		device.setDeviceType(taskDevice.getDeviceType());
		device.setDeviceTypeCode(taskDevice.getDeviceTypeCode());
		device.setDeviceCategory(taskDevice.getDeviceCategory());
		device.setDeviceCategoryCode(taskDevice.getDeviceCategoryCode());
		return device;
	}

	@Override
	public CheckDeviceRecordVo getRecord(String deviceCode) {
		CheckDeviceRecordVo checkDeviceRecordVo = new CheckDeviceRecordVo();
		checkDeviceRecordVo.setDeviceCode(deviceCode);
		Query query = new Query();
		query.setCurrent(1);
		query.setSize(999);
		//入库记录
		DeviceStorageSO so = new DeviceStorageSO();
		so.setDeptCode(deviceCode);
		IPage<DeviceStorage> deviceStorageIPage = deviceStorageMapper.customSelectPage(Condition.getPage(query), so);
		String storageTime = null;
		if (ObjectUtil.isNotEmpty(deviceStorageIPage.getRecords())) {
			DeviceStorage deviceStorage = deviceStorageIPage.getRecords().get(0);
			storageTime = String.valueOf(deviceStorage.getStorageTime());
		}
		//出库记录
		DeviceOutboundDTO deviceOutboundDTO = new DeviceOutboundDTO();
		deviceOutboundDTO.setDeviceCode(deviceCode);
		IPage<DeviceOutboundVO> deviceOutboundIPage = deviceOutboundMapper.getPage(Condition.getPage(query), deviceOutboundDTO);
		String outBoundTime = null;
		if (ObjectUtil.isNotEmpty(deviceOutboundIPage.getRecords())) {

			DeviceOutboundVO deviceOutbound = deviceOutboundIPage.getRecords().get(0);
			outBoundTime = String.valueOf(deviceOutbound.getOutboundTime());
		}
		checkDeviceRecordVo.setInOrOutWarehouse(storageTime + " 入库" + outBoundTime + " 出库");
		//申请记录
		List<DeviceApply> applyList = deviceApplyMapper.getApply(deviceCode);
		String applyTime = null;
		if (applyList.size() > 0) {
			DeviceApply apply = applyList.get(0);
			applyTime = String.valueOf(apply.getSubmitTime());
		}
		checkDeviceRecordVo.setApply(applyTime);
		//投运记录
		DeviceOperationDTO deviceOperationDTO = new DeviceOperationDTO();
		deviceOperationDTO.setDeviceCode(deviceCode);
		IPage<DeviceOperationVO> deviceOperationIPage = deviceOperationMapper.getPage(Condition.getPage(query), deviceOperationDTO);
		String operationTime = null;
		if (ObjectUtil.isNotEmpty(deviceOperationIPage.getRecords())) {
			DeviceOperationVO deviceOperation = deviceOperationIPage.getRecords().get(0);
			operationTime = String.valueOf(deviceOperation.getSubmitTime());
		}

		checkDeviceRecordVo.setOperation(operationTime);
		//变更记录
		DeviceChangeDTO deviceChangeDTO = new DeviceChangeDTO();
		deviceChangeDTO.setDeviceCode(deviceCode);
		IPage<DeviceChange> deviceChangePage = deviceChangeMapper.findPage(Condition.getPage(query), deviceChangeDTO);
		String changeTime = null;
		if (ObjectUtil.isNotEmpty(deviceChangePage.getRecords())) {
			DeviceChange deviceChange = deviceChangePage.getRecords().get(0);
			changeTime = String.valueOf(deviceChange.getReceiverTime());
		}
		checkDeviceRecordVo.setChange(changeTime);
		//报修记录
		DeviceRepairVO deviceRepairVO = new DeviceRepairVO();
		deviceRepairVO.setDeviceCode(deviceCode);
		IPage<DeviceRepair> deviceRepairPage = deviceRepairMapper.findPage(Condition.getPage(query), deviceRepairVO);
		String repairTime = null;
		if (ObjectUtil.isNotEmpty(deviceChangePage.getRecords())) {
			DeviceRepair deviceRepair = deviceRepairPage.getRecords().get(0);
			repairTime = String.valueOf(deviceRepair.getReceiverTime());
		}

		checkDeviceRecordVo.setRepair(repairTime);
		//巡检记录
		//TODO 巡检记录待处理
		//盘点记录
		List<CheckTask> checkTaskList = checkTaskMapper.getCheck(deviceCode);
		String checkTime = null;
		if (checkTaskList.size() > 0) {
			CheckTask checkTask = checkTaskList.get(0);
			checkTime = String.valueOf(checkTask.getReceiverTime());
		}
		checkDeviceRecordVo.setCheck(checkTime);
		return checkDeviceRecordVo;
	}

	@Override
	public CheckTaskDeviceDTO isNewDevice(CheckTaskDevice checkTaskDevice) {
		CheckTaskDeviceDTO checkTaskDeviceDTO = new CheckTaskDeviceDTO();
		//查子表，
		LambdaQueryWrapper<CheckTaskDevice> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(CheckTaskDevice::getTaskId, checkTaskDevice.getTaskId()).eq(CheckTaskDevice::getMac, checkTaskDevice.getMac())
			.eq(CheckTaskDevice::getIp, checkTaskDevice.getIp());
		CheckTaskDevice device = baseMapper.selectOne(queryWrapper);
		//如果为空，查台账
		if (ObjectUtil.isNotEmpty(device)) {
			BeanUtil.copy(device, checkTaskDeviceDTO);
			checkTaskDeviceDTO.setShowType("0");
			return checkTaskDeviceDTO;
		}
		Query query = new Query();
		List<CiCientitySearchVO> ciCientitySearchVOS = new ArrayList<>();
		CiCientitySearchVO searchVO = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.IP).attrValue(checkTaskDevice.getIp()).expression(Expression.EQUAL).build();
		CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.MAC).attrValue(checkTaskDevice.getMac()).expression(Expression.EQUAL).build();
		ciCientitySearchVOS.add(searchVO);
		ciCientitySearchVOS.add(searchVO1);
		FeignCiCientity ciCientityListByClaccify = iCmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
		Map<String, Object> map = ciCientityListByClaccify.getData().get(0);
		if (ObjectUtil.isNotEmpty(map)) {
			checkTaskDeviceDTO.setShowType("1");
			checkTaskDeviceDTO.setDeviceCode(String.valueOf(map.get(CmdbAttrConstant.DEVICE_CODE)));
			checkTaskDeviceDTO.setDeviceName(String.valueOf(map.get(CmdbAttrConstant.DEVICE_NAME)));
			checkTaskDeviceDTO.setDeviceCategory(String.valueOf(map.get(CmdbAttrConstant.DEVICE_CATEGORY)));
			checkTaskDeviceDTO.setDeviceCategoryCode(String.valueOf(map.get(CmdbAttrConstant.DEVICE_CATEGORY_CODE)));
			checkTaskDeviceDTO.setDeviceType(String.valueOf(map.get(CmdbAttrConstant.DEVICE_TYPE)));
			checkTaskDeviceDTO.setDeviceTypeCode(String.valueOf(map.get(CmdbAttrConstant.DEVICE_TYPE_CODE)));
			checkTaskDeviceDTO.setDeviceSource(String.valueOf(map.get(CmdbAttrConstant.DEVICE_SOURCE)));
			checkTaskDeviceDTO.setDeviceSourceCode(String.valueOf(map.get(CmdbAttrConstant.DEVICE_SOURCE_CODE)));
			checkTaskDeviceDTO.setBrand(String.valueOf(map.get(CmdbAttrConstant.BRAND)));
			checkTaskDeviceDTO.setBrandCode(String.valueOf(map.get(CmdbAttrConstant.BRAND_CODE)));
			checkTaskDeviceDTO.setSeries(String.valueOf(map.get(CmdbAttrConstant.SERIES)));
			checkTaskDeviceDTO.setSeriesCode(String.valueOf(map.get(CmdbAttrConstant.SERIES_CODE)));
			checkTaskDeviceDTO.setDeviceModel(String.valueOf(map.get(CmdbAttrConstant.DEVICE_MODEL)));
			checkTaskDeviceDTO.setDeviceModelCode(String.valueOf(map.get(CmdbAttrConstant.DEVICE_MODEL_CODE)));
			checkTaskDeviceDTO.setIsITAICode(String.valueOf(map.get(CmdbAttrConstant.IS_IT_AI_CODE)));
			checkTaskDeviceDTO.setReceiveDept(String.valueOf(map.get(CmdbAttrConstant.RECEIVE_DEPT)));
			checkTaskDeviceDTO.setReceiveDeptCode(String.valueOf(map.get(CmdbAttrConstant.RECEIVE_DEPT_CODE)));
			checkTaskDeviceDTO.setReceivingPerson(String.valueOf(map.get(CmdbAttrConstant.RECEIVING_PERSON)));
			checkTaskDeviceDTO.setReceivingIDCard(String.valueOf(map.get(CmdbAttrConstant.RECEIVING_ID_CARD)));
			checkTaskDeviceDTO.setReceivingTel(String.valueOf(map.get(CmdbAttrConstant.RECEIVING_TEL)));
			checkTaskDeviceDTO.setUser(String.valueOf(map.get(CmdbAttrConstant.USER)));
			checkTaskDeviceDTO.setInstallationSite(String.valueOf(map.get(CmdbAttrConstant.INSTALLATION_SITE)));
			checkTaskDeviceDTO.setIp(String.valueOf(map.get(CmdbAttrConstant.IP)));
			checkTaskDeviceDTO.setMac(String.valueOf(map.get(CmdbAttrConstant.MAC)));
			checkTaskDeviceDTO.setNetWorkCode(String.valueOf(map.get(CmdbAttrConstant.NET_WORK_CODE)));
			checkTaskDeviceDTO.setFactorySerial(String.valueOf(map.get(CmdbAttrConstant.SN)));
			checkTaskDeviceDTO.setDeviceHeight(Double.parseDouble((ObjectUtil.isNotEmpty(map.get(CmdbAttrConstant.DEVICE_HEIGHT)) ? map.get(CmdbAttrConstant.DEVICE_HEIGHT) : "0").toString()));
			checkTaskDeviceDTO.setDeviceHeightBegin(Double.parseDouble((ObjectUtil.isNotEmpty(map.get(CmdbAttrConstant.DEVICE_HEIGHT_BEGIN)) ? map.get(CmdbAttrConstant.DEVICE_HEIGHT_BEGIN) : "0").toString()));
			checkTaskDeviceDTO.setDeviceHeightEnd(Double.parseDouble((ObjectUtil.isNotEmpty(map.get(CmdbAttrConstant.DEVICE_HEIGHT_END)) ? map.get(CmdbAttrConstant.DEVICE_HEIGHT_END) : "0").toString()));
			checkTaskDeviceDTO.setComputerRoom(String.valueOf(ObjectUtil.isNotEmpty(map.get(CmdbAttrConstant.COMPUTER_ROOM)) ? map.get(CmdbAttrConstant.COMPUTER_ROOM) : ""));
			checkTaskDeviceDTO.setComputerRoomCode(String.valueOf(ObjectUtil.isNotEmpty(map.get(CmdbAttrConstant.COMPUTER_ROOM_CODE)) ? map.get(CmdbAttrConstant.COMPUTER_ROOM_CODE) : ""));
			checkTaskDeviceDTO.setCabinet(String.valueOf(ObjectUtil.isNotEmpty(map.get(CmdbAttrConstant.CABINET)) ? map.get(CmdbAttrConstant.CABINET) : ""));
			checkTaskDeviceDTO.setCabinetCode(String.valueOf(ObjectUtil.isNotEmpty(map.get(CmdbAttrConstant.CABINET_CODE)) ? map.get(CmdbAttrConstant.CABINET_CODE) : ""));
			checkTaskDeviceDTO.setNetworkDeviceType(String.valueOf(ObjectUtil.isNotEmpty(map.get(CmdbAttrConstant.NETWORK_DEVICE_TYPE)) ? map.get(CmdbAttrConstant.NETWORK_DEVICE_TYPE) : ""));
			checkTaskDeviceDTO.setSecurityBoundary(String.valueOf(ObjectUtil.isNotEmpty(map.get(CmdbAttrConstant.SECURITY_BOUNDARY)) ? map.get(CmdbAttrConstant.SECURITY_BOUNDARY) : ""));
			return checkTaskDeviceDTO;
		}
		//如果为空，返回空，设备注册
		checkTaskDeviceDTO.setShowType("2");
		return checkTaskDeviceDTO;
	}

	@Override
	public R<CheckDeviceCountVO> getDeviceCount() {
		CheckDeviceCountVO checkDeviceCountVO = new CheckDeviceCountVO();
		Query query = new Query();
		query.setSize(10);
		query.setCurrent(1);
		IdevelopUser user = SecureUtil.getUser();
		List<CiCientitySearchVO> ciCientitySearchVOS = new ArrayList<>();
		String account = user.getAccount();
		CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.RECEIVE_PERSON_UNIFIED_ACC).attrValue(account).expression(Expression.EQUAL).build();
		ciCientitySearchVOS.add(searchVO1);
		FeignCiCientity ciCientityListByClaccify = iCmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);

		String corpId = user.getCorpId();
		CiCientitySearchVO searchVO2 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.RECEIVE_UNIT_CODE).attrValue(corpId).expression(Expression.EQUAL).build();
		ciCientitySearchVOS.add(searchVO2);
		FeignCiCientity ciCientityListByClaccify1 = iCmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
		checkDeviceCountVO.setIndividualCount(ciCientityListByClaccify.getTotal());
		checkDeviceCountVO.setManageCount(ciCientityListByClaccify1.getTotal());
		return R.data(checkDeviceCountVO);
	}

	@Override
	public R deviceAdd(String id) throws Exception{
		IdevelopUser user = SecureUtil.getUser();
		String fillingNo = taskCreateDeviceOperation(user, id);
		Map<String, Object> ext = user.getExt();
		String phone = String.valueOf(ext.get("phone"));
		Date date = new Date();
		//盘盈设备处置
		baseMapper.update(Wrappers.<CheckTaskDevice>lambdaUpdate().set(CheckTaskDevice::getDisPerson, user.getUserName()).set(CheckTaskDevice::getDisTel, phone).set(CheckTaskDevice::getDisDept, user.getDeptName())
			.set(CheckTaskDevice::getDisTime, date).set(CheckTaskDevice::getDisposeResult, "0").set(StringUtil.isNotBlank(fillingNo), CheckTaskDevice::getFilingNo, fillingNo)
			.set(CheckTaskDevice::getCheckStatus, "2").set(CheckTaskDevice::getUpdateUser, user.getUserId()).set(CheckTaskDevice::getUpdateTime, date).set(StringUtil.isNotBlank(fillingNo), CheckTaskDevice::getFilingTime, date)
			.set(CheckTaskDevice::getDisposeStatus, "0").eq(CheckTaskDevice::getId, id).eq(CheckTaskDevice::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		return null;
	}

	@Override
	public void checkTaskNetwork() {
		Date date = new Date();
		List<CheckTaskDevice> checkTaskDevices = baseMapper.selectList(Wrappers.<CheckTaskDevice>lambdaQuery().eq(CheckTaskDevice::getIsDeleted, 0));
		for (CheckTaskDevice taskDevice : checkTaskDevices) {
			Date startTime = taskDevice.getOfflineStartTime();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startTime);
			calendar.add(Calendar.DAY_OF_YEAR,7);
			Date endTime = calendar.getTime();
			if (date.compareTo(endTime)>=0){
				//恢复入网
				SafeaccessUserAccessDisable one = safeaccessUserAccessDisableService.getOne(Wrappers.<SafeaccessUserAccessDisable>lambdaQuery()
					.eq(SafeaccessUserAccessDisable::getDeviceCode, taskDevice.getDeviceCode()));
				String mac = one.getMac();
				safeaccessUserAccessService.update(Wrappers.<SafeaccessUserAccess>lambdaUpdate()
					.set(SafeaccessUserAccess::getMacAddress,mac)
					.set(SafeaccessUserAccess::getDisableStatus,"2")
					.eq(SafeaccessUserAccess::getDeviceCode,one.getDeviceCode()));
			}
		}
	}

	@Override
	public R<IPage<CheckTaskDevice>> getListByUser(String type,String id, Query query) {
		IdevelopUser user = SecureUtil.getUser();
		IPage<CheckTaskDevice> checkTaskDeviceIPage = baseMapper.selectPage(Condition.getPage(query), Wrappers.<CheckTaskDevice>lambdaQuery()
			.eq(CheckTaskDevice::getIsDeleted, IdevelopConstant.DB_NOT_DELETED)
			.eq(CheckTaskDevice::getCheckStatus, type)
			.eq(ObjectUtil.isNotEmpty(id), CheckTaskDevice::getTaskId, id));
		return null;
	}

	@Override
	public CheckStatistic statistic() {
		LambdaQueryWrapper<CheckTaskDevice> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(CheckTaskDevice::getCheckStatus,2).eq(CheckTaskDevice::getIsDeleted,0);
		List<CheckTaskDevice> taskDeviceListPy = baseMapper.selectList(queryWrapper);
		LambdaQueryWrapper<CheckTaskDevice> queryWrapper1 = new LambdaQueryWrapper<>();
		queryWrapper1.eq(CheckTaskDevice::getCheckStatus,3).eq(CheckTaskDevice::getIsDeleted,0);
		List<CheckTaskDevice> taskDeviceListPk = baseMapper.selectList(queryWrapper1);
		CheckStatistic checkStatistic = new CheckStatistic();
		checkStatistic.setPy(taskDeviceListPy.size());
		checkStatistic.setPk(taskDeviceListPk.size());
		return checkStatistic;

	}

	@Override
	public R<List<CheckStatistic>> dept() {
		return R.data(baseMapper.dept());

	}


	public boolean updateCmdbEntity(DeviceReturnedDetail returnedDetail) {
		if (Objects.isNull(returnedDetail)) {
			return false;
		}
		Map<Long, Map<String, Object>> hashMap = new HashMap<>();
		// 修改 资产台账
		//获取模型 cid
		Long ciId = returnedDetail.getCiId();
		Map<String, Object> ent = new HashMap<>();
		ent.put("erpAssetStatus", 3);
		ent.put(CmdbAttrConstant.ID, returnedDetail.getDeviceId());
		ent.put(CmdbAttrConstant.UUID, returnedDetail.getUuid());
		ent.put(CmdbAttrConstant.CI_ID, ciId);
		ent.put(CmdbAttrConstant.DEVICE_NAME, returnedDetail.getDeviceName());
		ent.put(CmdbAttrConstant.DEVICE_TYPE, returnedDetail.getDeviceType());
		ent.put(CmdbAttrConstant.DEVICE_CODE, returnedDetail.getDeviceCode());
		ent.put(CmdbAttrConstant.DEVICE_STATUS_CODE, returnedDetail.getDeviceStatus());
		ent.put(CmdbAttrConstant.DEVICE_STATUS, cmdbCientityProperties.getReturnWarehouse().equals(returnedDetail.getDeviceStatus()) ? "退运在库" : "待报废");
		ent.put(CmdbAttrConstant.IN_WAREHOUSE_CODE, returnedDetail.getInWarehouseCode());
		ent.put(CmdbAttrConstant.IN_WAREHOUSE, returnedDetail.getInWarehouse());
		ent.put(CmdbAttrConstant.WAREHOUSE_LOCATION, returnedDetail.getAddress());
		ent.put(CmdbAttrConstant.IP, "");
		// todo 应该获取实际的ERP编码
		ent.put(CmdbAttrConstant.ASSET_CODE_ERP, returnedDetail.getAssetCodeErp());
		hashMap.put(Long.parseLong(ent.get("id").toString()), ent);
		//保存cmdb
		iCmdbService.cientityBatchupdate(hashMap, TransactionActionType.UPDATE);
		return true;
	}
}
