package com.lnsoft.device.api.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lnsoft.common.cache.CacheNames;
import com.lnsoft.common.msg.service.MessageSendService;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.RedisUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.data.dto.WarningListDTO;
import com.lnsoft.data.entity.DevelopWarning;
import com.lnsoft.device.api.asset.dto.DevicePressDoDTO;
import com.lnsoft.device.api.asset.dto.WorkBenchFinishDTO;
import com.lnsoft.device.api.asset.entity.WorkBenchDone;
import com.lnsoft.device.api.asset.mapper.DeviceWorkBenchMapper;
import com.lnsoft.device.api.asset.service.IDeviceWorkBenchService;
import com.lnsoft.device.api.asset.vo.*;
import com.lnsoft.device.api.asset.wrapper.HardwareBasicWrapper;
import com.lnsoft.device.api.desk.service.IDeskService;
import com.lnsoft.device.api.desk.vo.DictValueVO;
import com.lnsoft.device.api.res.constants.Constants;
import com.lnsoft.device.entity.ApproveRecord;
import com.lnsoft.device.api.res.service.IApproveRecordService;
import com.lnsoft.device.api.res.service.IHussarBpmService;
import com.lnsoft.device.api.safeaccess.mapper.SafeaccessIppoolMapper;
import com.lnsoft.device.api.warehouse.entity.DeviceOperation;
import com.lnsoft.device.api.warehouse.entity.DeviceOutbound;
import com.lnsoft.common.enums.hussar.HussarBpmTypeEnum;
import com.lnsoft.device.api.warehouse.service.IDeviceOperationService;
import com.lnsoft.device.api.warehouse.service.IDeviceOutboundService;
import com.lnsoft.device.constant.DeviceConstant;
import com.lnsoft.common.enums.hussar.OptTypeEnum;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.system.user.entity.UserInfo;
import com.lnsoft.system.user.feign.IUserClient;
import com.lnsoft.system.user.vo.BpmUserVO;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author: xuel
 * @CreateTime: 2024/7/18 11:27
 * @Description: 个人工作台服务类 DeviceWorkBenchServiceImpl
 */

@Service
@AllArgsConstructor
public class DeviceWorkBenchServiceImpl implements IDeviceWorkBenchService {

	private IApproveRecordService approveRecordService;
	private CmdbCientityProperties cientityProp;
	private IHussarBpmService hussarBpmService;
	private IDeskService deskService;
	private IDeviceOutboundService deviceOutboundService;
	private IDeviceOperationService deviceOperationService;

	@Resource
	private SafeaccessIppoolMapper safeaccessIppoolMapper;
	@Resource
	private DeviceWorkBenchMapper deviceWorkBenchMapper;
	@Resource
	private MessageSendService messageSendService;
	@Resource
	private RedisUtil redisUtil;
	@Resource
	private IUserClient userClient;
	/**
	 * 个人工作台 - 我处理的 获取个人已办列表
	 *
	 * @param workBenchFinishDTO
	 * @return
	 */
	@Override
	public IPage<WorkBenchFinishVO> getDoneList(WorkBenchFinishDTO workBenchFinishDTO) {

		if (Objects.isNull(workBenchFinishDTO.getCurrent())) {
			workBenchFinishDTO.setCurrent(1);
		}
		if (Objects.isNull(workBenchFinishDTO.getSize())) {
			workBenchFinishDTO.setSize(10);
		}

		IdevelopUser user = SecureUtil.getUser();
		workBenchFinishDTO.setCreateUser(user.getUserId());

		if (Objects.isNull(workBenchFinishDTO.getCreateUser())) {
			workBenchFinishDTO.setCreateUser(user.getUserId());
		}
		ApproveRecord approveRecord = new ApproveRecord();
		approveRecord.setOptBy(user.getUserId().toString());

		Query query = new Query();
		query.setCurrent(workBenchFinishDTO.getCurrent());
		query.setSize(workBenchFinishDTO.getSize());
		IPage<ApproveRecord> page = Condition.getPage(query);
		QueryWrapper<ApproveRecord> queryWrapper = Condition.getQueryWrapper(approveRecord);
		queryWrapper.lambda().orderByDesc(ApproveRecord::getCreateTime);
//		List<ApproveRecord> approveRecordList = approveRecordService.list(page, queryWrapper);
		if (workBenchFinishDTO.getCurrent()!=0){
			workBenchFinishDTO.setCurrent(workBenchFinishDTO.getCurrent()-1);
		}
		List<ApproveRecord> approveRecordList1 = deviceWorkBenchMapper.selectWorkDoneMyList(workBenchFinishDTO);
		Integer total1 = deviceWorkBenchMapper.selectWorkDoneMyCount(workBenchFinishDTO);
//		long total = approveRecordService.count(queryWrapper);

		List<WorkBenchFinishVO> workBenchFinishVOList = approveRecordList1.stream().map(item -> {
			WorkBenchFinishVO workBenchFinishVO = new WorkBenchFinishVO();

			workBenchFinishVO.setId(item.getFilingNo());
			// 单号
			String businessId = item.getFilingCode();
			workBenchFinishVO.setBusinessId(businessId);

			// 工单类型
			String processName = OptTypeEnum.getValue(item.getOptType());
			workBenchFinishVO.setProcessName(processName);

			// 处理时间
			Date endTime = item.getOptDate();
			LocalDateTime parse = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			workBenchFinishVO.setProcessing(parse);

			// 处理结果
			Integer state = item.getApproveStatus();
			// 审批意见
			String approvalOpinion = item.getOptOpinion();

			if (state == 1) {
				String processResult = "审批结果: 驳回, 审批意见: " + approvalOpinion;
				workBenchFinishVO.setProcessResult(processResult);
			}else if (state == 3){
				workBenchFinishVO.setProcessResult(approvalOpinion);
			}else {
				workBenchFinishVO.setProcessResult("审批结果: 同意, 审批意见: " + approvalOpinion);
			}

			return workBenchFinishVO;
		}).collect(Collectors.toList());

		IPage<WorkBenchFinishVO> workBenchFinishVOIPage = Condition.getPage(query);
		workBenchFinishVOIPage.setRecords(workBenchFinishVOList);
		workBenchFinishVOIPage.setTotal(total1);

		return workBenchFinishVOIPage;
	}

	/**
	 * 代办任务：我发起的(进行中)
	 *
	 * @return
	 */
	@Override
	public IPage<WorkBenchUnderwayVO> underwayTask(WorkBenchFinishDTO workBenchFinishDTO) {

		if (Objects.isNull(workBenchFinishDTO.getCurrent())) {
			workBenchFinishDTO.setCurrent(1);
		}
		if (Objects.isNull(workBenchFinishDTO.getSize())) {
			workBenchFinishDTO.setSize(10);
		}

		workBenchFinishDTO.setCurrent((workBenchFinishDTO.getCurrent() - 1) * workBenchFinishDTO.getSize());

		IdevelopUser user = SecureUtil.getUser();
		if (Objects.isNull(workBenchFinishDTO.getCreateUser())) {
			workBenchFinishDTO.setCreateUser(user.getUserId());
		}

		Integer count = deviceWorkBenchMapper.selectWorkTodoListCount(workBenchFinishDTO);
		List<WorkBenchDone> workBenchDones = deviceWorkBenchMapper.selectWorkTodoList(workBenchFinishDTO);

		List<WorkBenchUnderwayVO> result = workBenchDones.stream().map(item -> {

			WorkBenchUnderwayVO workBenchUnderwayVO = new WorkBenchUnderwayVO();
			String processName = item.getProcessName();
			String processNameBpm = item.getProcessName();
			String processCode = item.getProcessCode();
			String businessId = item.getBusinessId();
			String businessIdBpm = item.getBusinessId();
			String id = item.getId();
			workBenchUnderwayVO.setId(item.getId());
			workBenchUnderwayVO.setBusinessId(businessId);
			workBenchUnderwayVO.setProcessName(processName);
			workBenchUnderwayVO.setSponsorTime(item.getCreateTime());
			workBenchUnderwayVO.setProcessStatus(item.getProcessStatus());
			workBenchUnderwayVO.setProcessCode(processCode);

			if (StringUtils.containsAny(processName, "设备申请", "设备出库", "设备投运")) {
				processName = HussarBpmTypeEnum.DEVICE_APPLY_OUTBOUND_OPERATION.getMessage();
			}
			String bpmMarks = HussarBpmTypeEnum.getBpmMarks(processName);
			List<DictValueVO> dictValueVOS = deskService.deviceRecordDict(bpmMarks, null);

			String processStatus = item.getProcessStatus();
			for (DictValueVO dictValueVO : dictValueVOS) {
				if (StringUtils.equals(dictValueVO.getNode(), processStatus)) {
					processStatus = dictValueVO.getNodeName();
				}else if (StringUtils.equals("nanrui_hussar_1",processStatus)){
					processStatus = "审批";
				}
			}
			workBenchUnderwayVO.setNodeCurrent(processStatus);
			// 未发起工单的
			if (Objects.isNull(item.getProcessId())) {
				workBenchUnderwayVO.setNodeCurrent("未上报");
			}

			// 设备出库单独处理.
			if (StringUtils.equals(processNameBpm, "设备出库")) {
				DeviceOutbound deviceOutbound = deviceOutboundService.getById(id);
				businessIdBpm = deviceOutbound.getApplyNo();
			}

			// 设备投运单独处理.
			if (StringUtils.equals(processNameBpm, "设备投运")) {
				DeviceOperation deviceOutbound = deviceOperationService.getById(id);
				businessIdBpm = deviceOutbound.getApplyNo();
			}

			// 查询BPM中的信息
			try {
				if (Objects.isNull(item.getProcessId())) {
					workBenchUnderwayVO.setNodeCurrent("未上报");
					workBenchUnderwayVO.setCurrentProcess(user.getUserName());
					workBenchUnderwayVO.setCurrentProcessId(user.getUserId().toString());
				} else {
					List<BpmUserVO> bpmUserVOS = hussarBpmService.queryAssignee(businessIdBpm);
					List<String> nameList = bpmUserVOS.stream().map(BpmUserVO::getName).collect(Collectors.toList());
					String nameStr = String.join(",", nameList);
					workBenchUnderwayVO.setCurrentProcess(nameStr);
					List<String> idList = bpmUserVOS.stream().map(entry -> entry.getId().toString()).collect(Collectors.toList());
					String idStr = String.join(",", idList);
					workBenchUnderwayVO.setCurrentProcessId(idStr);
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			return workBenchUnderwayVO;
		}).collect(Collectors.toList());

		Query query = new Query();
		query.setCurrent(workBenchFinishDTO.getCurrent());
		query.setSize(workBenchFinishDTO.getSize());
		IPage<WorkBenchUnderwayVO> workBenchUnderwayVOIPage = Condition.getPage(query);
		workBenchUnderwayVOIPage.setTotal(count);
		workBenchUnderwayVOIPage.setRecords(result);

		return workBenchUnderwayVOIPage;
	}


	/**
	 * 个人工作台 - 代办任务：我处理的-已完成
	 *
	 * @param workBenchFinishDTO
	 * @return
	 */
	@Override
	public IPage<WorkBenchCompleteVO> completeTask(WorkBenchFinishDTO workBenchFinishDTO) {

		if (Objects.isNull(workBenchFinishDTO.getCurrent())) {
			workBenchFinishDTO.setCurrent(1);
		}
		if (Objects.isNull(workBenchFinishDTO.getSize())) {
			workBenchFinishDTO.setSize(10);
		}
		workBenchFinishDTO.setCurrent((workBenchFinishDTO.getCurrent() - 1) * workBenchFinishDTO.getSize());

		if (Objects.isNull(workBenchFinishDTO.getCreateUser())) {
			IdevelopUser user = SecureUtil.getUser();
			workBenchFinishDTO.setCreateUser(user.getUserId());
		}

		Integer count = deviceWorkBenchMapper.selectWorkDoneListCount(workBenchFinishDTO);

		List<WorkBenchDone> workBenchDones = deviceWorkBenchMapper.selectWorkDoneList(workBenchFinishDTO);

		List<WorkBenchCompleteVO> result = workBenchDones.stream().map(item -> {
			WorkBenchCompleteVO workBenchCompleteVO = new WorkBenchCompleteVO();
			workBenchCompleteVO.setId(item.getId());
			workBenchCompleteVO.setBusinessId(item.getBusinessId());
			workBenchCompleteVO.setProcessName(item.getProcessName());
			workBenchCompleteVO.setSponsorTime(item.getCreateTime());
			workBenchCompleteVO.setExplain(item.getDescription());

			return workBenchCompleteVO;
		}).collect(Collectors.toList());

		Query query = new Query();
		query.setCurrent(workBenchFinishDTO.getCurrent());
		query.setSize(workBenchFinishDTO.getSize());
		IPage<WorkBenchCompleteVO> workBenchCompleteVOIPage = Condition.getPage(query);
		workBenchCompleteVOIPage.setTotal(count);
		workBenchCompleteVOIPage.setRecords(result);

		return workBenchCompleteVOIPage;
	}

	/**
	 * 个人工作台 - IP资源：IP地址总数 = 内网 + 外网-统一出口外网 + 外网-集体企业外网
	 * 只需要 已分配 和 未分配
	 *
	 * @return
	 */
	@Override
	public Map<String, Object> getIpAllNumber() {
		IdevelopUser user = SecureUtil.getUser();
		String regionCode = user.getRegionCode();

		Map<String, Object> resultMap = new HashMap<>();

		if (StringUtils.equals(regionCode, "37")) {
			resultMap.put(DeviceConstant.NUMBER, 0);
			return resultMap;
		}
		String ipPoolDB = Constants.IP_POOL + regionCode.substring(0, 4);

		Integer allNumber = safeaccessIppoolMapper.getIpAllNumber(regionCode, ipPoolDB);

		resultMap.put(DeviceConstant.NUMBER, allNumber);
		return resultMap;
	}

	/**
	 * 个人工作台 - IP资源：内网/外网使用率：内网 + 外网-统一出口外网 + 外网-集体企业外网
	 * 只需要 已分配 和 未分配
	 *
	 * @return
	 */
	@Override
	public List<WorkBenchIPNumberVO> getIpAssignNumber() {
		IdevelopUser user = SecureUtil.getUser();
		String regionCode = user.getRegionCode();

		if (StringUtils.equals(regionCode, "37")) {
			return new ArrayList<>();
		}

		String ipPoolDB = Constants.IP_POOL + regionCode.substring(0, 4);

		List<WorkBenchIPNumberVO> resultList = new ArrayList<>();

		// 内网
		List<String> list1 = Arrays.asList(cientityProp.getNetwork2());
		List<WorkBenchIpAssignNumberVO> resultList1 = safeaccessIppoolMapper.getIpAssignNumber(regionCode, ipPoolDB, list1);

		WorkBenchIPNumberVO workBenchIPNumberVO1 = converWorkBenchIPNumberVO(resultList1, "intranet");
		resultList.add(workBenchIPNumberVO1);

		// 外网
		List<String> list2 = Arrays.asList(cientityProp.getNetwork0(), cientityProp.getNetwork1());
		List<WorkBenchIpAssignNumberVO> resultList2 = safeaccessIppoolMapper.getIpAssignNumber(regionCode, ipPoolDB, list2);

		WorkBenchIPNumberVO workBenchIPNumberVO2 = converWorkBenchIPNumberVO(resultList2, "outernet");
		resultList.add(workBenchIPNumberVO2);

		return resultList;
	}

	/**
	 * 构建转换 个人工作台 - IP资源使用率
	 *
	 * @param resultList
	 * @param type
	 * @return
	 */
	private WorkBenchIPNumberVO converWorkBenchIPNumberVO(List<WorkBenchIpAssignNumberVO> resultList, String type) {
		WorkBenchIPNumberVO workBenchIPNumberVO = new WorkBenchIPNumberVO();
		workBenchIPNumberVO.setType(type);
		for (WorkBenchIpAssignNumberVO ipAssignNumberVO : resultList) {
			if (StringUtils.equals("0", ipAssignNumberVO.getIsUsed())) {
				workBenchIPNumberVO.setUndistributedNumber(ipAssignNumberVO.getNumber());
			} else {
				workBenchIPNumberVO.setAssignNumber(ipAssignNumberVO.getNumber());
			}
		}

		if (Objects.isNull(workBenchIPNumberVO.getAssignNumber())) {
			workBenchIPNumberVO.setAssignNumber(0);
		}
		if (Objects.isNull(workBenchIPNumberVO.getUndistributedNumber())) {
			workBenchIPNumberVO.setUndistributedNumber(0);
		}

		Integer total1 = workBenchIPNumberVO.getAssignNumber();
		Integer total2 = workBenchIPNumberVO.getUndistributedNumber();
		Integer total3 = total1 + total2;

		if (total3 != 0) {
			BigDecimal numberBig2 = new BigDecimal(total1);
			BigDecimal numberBig3 = new BigDecimal(total3);
			BigDecimal divideTotal = numberBig2.divide(numberBig3, 2, RoundingMode.HALF_UP);

			Double doubleValue = divideTotal.doubleValue();
			workBenchIPNumberVO.setUsageRate(doubleValue);
		} else {
			workBenchIPNumberVO.setUsageRate(0.00);
		}

		return workBenchIPNumberVO;
	}

	/**
	 * 实时告警信息
	 *
	 * @return
	 */
	@Override
	public Page<DevelopWarning> warningList(WarningListDTO warningListDTO) {
		IdevelopUser user = SecureUtil.getUser();
		DevelopWarning developWarning = new DevelopWarning();
		developWarning.setRegionCode(user.getRegionCode());
		warningListDTO.setDevelopWarning(developWarning);

		return HardwareBasicWrapper.build().warningList(warningListDTO);
	}

	/**
	 * 告警处置一周统计
	 *
	 * @return
	 */
	@Override
	public Map<String, Integer> warningStatistics(DevelopWarning developWarning) {
		IdevelopUser user = SecureUtil.getUser();
		developWarning.setRegionCode(user.getRegionCode());

		return HardwareBasicWrapper.build().warningStatistics(developWarning);
	}

	/**
	 * 代办任务：我发起的统计
	 *
	 * @param workBenchFinishDTO
	 * @return
	 */
	@Override
	public Map<String, Integer> wnderwayCompleteNum(WorkBenchFinishDTO workBenchFinishDTO) {

		if (Objects.isNull(workBenchFinishDTO.getCreateUser())) {
			IdevelopUser user = SecureUtil.getUser();
			workBenchFinishDTO.setCreateUser(user.getUserId());
		}

		// 进行中
		Integer wnderwayCount = deviceWorkBenchMapper.selectWorkTodoListCount(workBenchFinishDTO);

		// 已完成
		Integer completeCount = deviceWorkBenchMapper.selectWorkDoneListCount(workBenchFinishDTO);

		Map<String, Integer> resultMap = new HashMap<>();
		resultMap.put("wnderwayCount", wnderwayCount);
		resultMap.put("completeCount", completeCount);

		return resultMap;
	}

	@Override
	public R pressDo(DevicePressDoDTO devicePressDoDTO) {
		IdevelopUser user = SecureUtil.getUser();
		//手机号校验
		boolean validPhone = isValidPhone(devicePressDoDTO.getPhone());
		if (!validPhone){
			return R.fail("催办手机号不正确！");
		}
		String key = CacheNames.SEND_MSG + devicePressDoDTO.getPhone();
		Object redisKey = redisUtil.get(key);
		if (ObjectUtil.isEmpty(redisKey)){
			redisUtil.set(key,1,3000);
		}else {
			return R.fail("该手机号已催办，5分钟内请勿重复催办！");
		}
		String type = devicePressDoDTO.getType();
		String orderType = HussarBpmTypeEnum.getMessage(type);
		Long userId = user.getUserId();
		R<UserInfo> userInfoR = userClient.userInfo(userId);
		String phone = userInfoR.getData().getUser().getPhone();
		String msg =
			"您好，您有信通一体化管理平台"+orderType
				+"工单需要处理，单号"+devicePressDoDTO.getFilingCode()
				+"，请及时处理,催办人"+user.getRealName()
				+"("+phone+")";
		String send = messageSendService.send(msg,devicePressDoDTO.getPhone());
		if ("1".equals(send)){
			return R.success("催办成功！");
		}else if("3".equals(send)){
			return R.fail("催办失败！");
		}
		return R.data("请联系管理员！");
	}
	private boolean isValidPhone(String phone){
		if (StringUtil.isNotBlank(phone)){
			return Pattern.matches("^1[3-9]\\d{9}$",phone);
		}
		return false;
	}
}
