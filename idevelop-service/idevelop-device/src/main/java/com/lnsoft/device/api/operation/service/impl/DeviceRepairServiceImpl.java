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
package com.lnsoft.device.api.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lnsoft.cmdb.vo.HardwareBasicQueryVO;
import com.lnsoft.common.enums.device.WorkOrderTypeEnum;
import com.lnsoft.common.enums.hussar.*;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.api.ResultCode;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.operation.entity.DeviceChange;
import com.lnsoft.device.api.operation.entity.DeviceChangeList;
import com.lnsoft.device.api.operation.mapper.DeviceChangeListMapper;
import com.lnsoft.device.api.operation.mapper.DeviceChangeMapper;
import com.lnsoft.device.api.operation.mapper.DeviceRepairListMapper;
import com.lnsoft.device.api.operation.mapper.DeviceRepairMapper;
import com.lnsoft.device.api.operation.service.IDeviceRepairListService;
import com.lnsoft.device.api.operation.service.IDeviceRepairService;
import com.lnsoft.device.api.operation.vo.HardwareBasicQueryRepairVO;
import com.lnsoft.device.api.res.dto.HussarBpmCreateDTO;
import com.lnsoft.device.api.res.dto.HussarBpmDTO;
import com.lnsoft.device.entity.ApproveRecord;
import com.lnsoft.device.entity.LogOpt;
import com.lnsoft.device.api.res.service.IApproveRecordService;
import com.lnsoft.device.api.res.service.IHussarBpmService;
import com.lnsoft.device.api.res.service.ILogOptService;
import com.lnsoft.device.api.warehouse.dto.OrderUpdateStatusDTO;
import com.lnsoft.device.api.warehouse.entity.DeviceScrap;
import com.lnsoft.device.api.warehouse.entity.DeviceScrapList;
import com.lnsoft.device.api.warehouse.entity.DeviceSdnQingDao;
import com.lnsoft.device.api.warehouse.mapper.DeviceScrapListMapper;
import com.lnsoft.device.api.warehouse.mapper.DeviceScrapMapper;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.constant.CmdbCientityConstant;
import com.lnsoft.device.dto.DeviceRepairDTO;
import com.lnsoft.device.entity.DeviceRepair;
import com.lnsoft.device.entity.DeviceRepairList;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.common.enums.hussar.OptTypeEnum;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.publisher.QDDSwitcherPublisher;
import com.lnsoft.device.utils.OrderNumberUtil;
import com.lnsoft.device.vo.CiCientitySearchVO;
import com.lnsoft.device.vo.DeviceRepairVO;
import com.lnsoft.endpoint.fegin.IEndpointClient;
import com.lnsoft.endpoint.vo.DeviceTicketVO;
import com.lnsoft.hussar.bpm.domain.vo.HussarAssignVo;
import com.lnsoft.hussar.bpm.domain.vo.HussarCreateVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 设备报修 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-19
 */
@Service
public class DeviceRepairServiceImpl extends BaseServiceImpl<DeviceRepairMapper, DeviceRepair> implements IDeviceRepairService {
	@Resource
	private IDeviceRepairListService deviceRepairListService;
	@Resource
	private OrderNumberUtil orderNumberUtil;
	@Resource
	private ILogOptService logOptService;
	@Resource
	private IHussarBpmService hussarBpmService;
	@Resource
	private IApproveRecordService approveRecordService;
	@Resource
	private CmdbCientityProperties cmdbCientityProperties;
	@Resource
	private DeviceChangeMapper deviceChangeMapper;
	@Resource
	private DeviceChangeListMapper deviceChangeListMapper;
	@Resource
	private DeviceScrapListMapper deviceScrapListMapper;
	@Resource
	private DeviceScrapMapper deviceScrapMapper;
	@Resource
	private IEndpointClient iEndpointClient;
	@Resource
	private DeviceRepairListMapper deviceRepairListMapper;

	@Override
	public IPage<DeviceRepairVO> selectDeviceRepairPage(IPage<DeviceRepairVO> page, DeviceRepairVO deviceRepair) {
		return page.setRecords(baseMapper.selectDeviceRepairPage(page, deviceRepair));
	}

	@Override
	public void delete(String ids) {
		List<String> list = Func.toStrList(ids);
		for (String repairId : list) {
			deviceRepairListService.deleteByRepairId(repairId);
		}
		baseMapper.deleteBatchIds(list);
	}

	@Override
	public IPage<DeviceRepair> deviceRepairPage(DeviceRepairVO deviceRepair, Query query) {
		IdevelopUser user = SecureUtil.getUser();
		deviceRepair.setRegionCode(user.getRegionCode());
		return baseMapper.findPage(Condition.getPage(query), deviceRepair);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R submit(DeviceRepairDTO deviceRepairDTO) throws Exception {
		R check = check(deviceRepairDTO);
		if (ResultCode.FAILURE.getCode() == check.getCode()) {
			return check;
		}
		IdevelopUser user = SecureUtil.getUser();
		DeviceRepair deviceRepair = new DeviceRepair();
		BeanUtils.copyProperties(deviceRepairDTO, deviceRepair);
		deviceRepair.setRegionCode(user.getRegionCode());
		deviceRepair.setReceiverTime(LocalDateTime.now());
		deviceRepair.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
		deviceRepair.setTicketStatus(DeviceRepairEnum.TEMPORARILY.getCode());
		if (StringUtil.isEmpty(deviceRepair.getFilingNo())) {
			deviceRepair.setFilingNo(orderNumberUtil.generateNumber(WorkOrderTypeEnum.BX));
		}
		if (DeviceChangeSubmitEnum.WAIT_APPROVAL.getCode().equals(deviceRepairDTO.getSubmitType())) {
			deviceRepair.setTicketStatus(DeviceRepairEnum.WAIT_APPROVAL.getCode());
		}
		List<DeviceRepairList> deviceRepairLists = deviceRepairDTO.getDeviceRepairLists();
		if (deviceRepairLists == null) {
			return R.fail("请选择要报修的设备");
		}
		deviceRepair.setRepairDeviceCount(String.valueOf(deviceRepairLists.size()));
		String id = deviceRepairDTO.getId();
		deviceRepair.setUpdateUser(user.getUserId());
		deviceRepair.setUpdateTime(new Date());
		if (StringUtils.isEmpty(deviceRepairDTO.getId())) {
			deviceRepair.setCreateTime(new Date());
			deviceRepair.setCreateUser(user.getUserId());
			baseMapper.insert(deviceRepair);
			// 增加操作记录
			logOptService.commonLogOpt(LogOpt.builder().logId(deviceRepair.getId()).logData(deviceRepair.toString()).params(deviceRepair.toString())
				.optRole("--").optType(OptTypeEnum.DEVICE_REPAIR.getCode()).title("新增暂存设备报修").build());
			id = deviceRepair.getId();
		} else {
			baseMapper.updateById(deviceRepair);
			logOptService.commonLogOpt(LogOpt.builder().logId(deviceRepair.getId()).logData(deviceRepair.toString()).params(deviceRepair.toString())
				.optRole("--").optType(OptTypeEnum.DEVICE_REPAIR.getCode()).title("修改暂存设备报修").build());
		}
		//每次清空设备列表
		deviceRepairListService.deleteByRepairId(id);
		for (DeviceRepairList device : deviceRepairLists) {
			device.setId(null);
			device.setRepairId(id);
			try {
				deviceRepairListService.insertDevice(device);
			} catch (Exception e) {
				return R.fail("设备已经报修，请选择其他设备！");
			}
		}
		// 提交发起流程
		if (DeviceChangeSubmitEnum.WAIT_APPROVAL.getCode().equals(deviceRepairDTO.getSubmitType())) {
			String processStatus;
			String roleName = null;
			String processInsId = null;
			// 判断报修类型
			if (DeviceRepairTypeEnum.COMPUTER.getCode().toString().equals(deviceRepairDTO.getRepairType()) || DeviceRepairTypeEnum.PRINTER.getCode().toString().equals(deviceRepairDTO.getRepairType())) {
				processStatus = DeviceRepairBpmNodeEnum.DEVICE_REPAIR_COMMISSIONER.getNode();
			} else {
				processStatus = DeviceRepairBpmNodeEnum.DEVICE_REPAIR_MONITOR_REVIEW.getNode();
			}
			if (StringUtil.isNotBlank(deviceRepairDTO.getProcessInsId())) {
				//  获取当前节点操作角色
				List<HussarAssignVo> hussarAssignVos = hussarBpmService.queryTaskInfo(deviceRepairDTO.getFilingNo());
				roleName = hussarAssignVos.stream().map(HussarAssignVo::getRoleName).collect(Collectors.joining(","));
				HussarBpmDTO hussarBpmDTO = new HussarBpmDTO();
				hussarBpmDTO.setBusinessKey(deviceRepairDTO.getFilingNo());
				hussarBpmDTO.setParticipantType("2");
				hussarBpmDTO.setTaskType("1");
				hussarBpmDTO.setProcessDefinitionKey(HussarBpmTypeEnum.DEVICE_REPAIR.getBpmMark());
				Map<String, Object> variable = new HashMap<>();
				variable.put("deviceType", DeviceRepairTypeEnum.OTHER.getCode().toString().equals(deviceRepairDTO.getRepairType()) ? "1" : "0");
				hussarBpmDTO.setVariable(variable);
				hussarBpmService.hussarSubmit(hussarBpmDTO);
			} else {
				Map<String, Object> variable = new HashMap<>();
				variable.put("deviceType", DeviceRepairTypeEnum.OTHER.getCode().toString().equals(deviceRepairDTO.getRepairType()) ? "1" : "0");
				HussarBpmCreateDTO hussarBpmCreateDTO = HussarBpmCreateDTO.builder().processDefinitionKey(HussarBpmTypeEnum.DEVICE_REPAIR.getBpmMark())
					.businessKey(deviceRepair.getFilingNo()).variable(variable).build();
				HussarCreateVo hussarBpm;
				// 发起流程
				try {
					hussarBpm = hussarBpmService.createHussarBpm(hussarBpmCreateDTO);
					//  获取当前节点操作角色
					List<HussarAssignVo> hussarAssignVoList = hussarBpmService.queryTaskInfo(deviceRepair.getFilingNo());
					processInsId = hussarBpm.getProcessInsId();
					roleName = hussarAssignVoList.stream().map(HussarAssignVo::getRoleName).collect(Collectors.joining(","));
				} catch (Exception e) {
					throw new Exception("创建流程发生异常");
				}
				HussarBpmDTO hussarBpmDTO = new HussarBpmDTO();
				hussarBpmDTO.setBusinessKey(deviceRepair.getFilingNo());
				hussarBpmDTO.setParticipantType("2");
				hussarBpmDTO.setTaskType("1");
				hussarBpmDTO.setProcessDefinitionKey(HussarBpmTypeEnum.DEVICE_REPAIR.getBpmMark());
				hussarBpmDTO.setVariable(variable);
				hussarBpmService.hussarSubmit(hussarBpmDTO);

			}
			// 记录流程id
			baseMapper.update(new LambdaUpdateWrapper<DeviceRepair>()
				.eq(DeviceRepair::getId, deviceRepair.getId())
				.set(StringUtils.isNotBlank(processInsId), DeviceRepair::getProcessInsId, processInsId)
				.set(DeviceRepair::getProcessStatus, processStatus));
			// 增加操作记录
			logOptService.commonLogOpt(LogOpt.builder()
				.logId(String.valueOf(deviceRepair.getId()))
				.logData(deviceRepairDTO.toString())
				.params(deviceRepairDTO.toString())
				.optRole(roleName)
				.optType(OptTypeEnum.DEVICE_REPAIR.getCode())
				.title("发起设备报修申请").build());
			// 记录审核流程
			approveRecordService.commonRecord(ApproveRecord.builder()
				.filingNo(deviceRepair.getId())
				.optRole(roleName)
				.optType(OptTypeEnum.DEVICE_REPAIR.getCode())
				.nodeId(DeviceRepairBpmNodeEnum.DEVICE_REPAIR_APPLY.getNode())
				.nodeName(DeviceRecordBpmNodeEnum.getMessage(DeviceRepairBpmNodeEnum.DEVICE_REPAIR_APPLY.getNode()))
				.optTitle("发起设备报修申请")
				.optOpinion("发起设备报修申请")
				.filingCode(deviceRepair.getFilingNo())
				.approveStatus(0)
				.build());
			return R.data(deviceRepair);
		}
		return R.data(deviceRepair);
	}

	@Override
	public R load() {
		//初始化数据
		IdevelopUser user = SecureUtil.getUser();
		Map<String, Object> ext = user.getExt();
		DeviceRepairVO deviceRepairVO = new DeviceRepairVO();
		deviceRepairVO.setApplyDept(user.getDeptId());
		deviceRepairVO.setApplyDeptName(user.getDeptName());
		deviceRepairVO.setApplyUnit(user.getCorpId());
		deviceRepairVO.setApplyUnitName(String.valueOf(ext.get("corpFullName")));
		deviceRepairVO.setApplyUserName(user.getRealName());
		deviceRepairVO.setApplyUser(String.valueOf(user.getUserId()));
		deviceRepairVO.setReceiverTime(LocalDateTime.now());
		return R.data(deviceRepairVO);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<Integer> deskUpdateStatus(OrderUpdateStatusDTO orderUpdateStatusDTO) throws Exception {
		IdevelopUser user = SecureUtil.getUser();
		DeviceRepair deviceRepair = baseMapper.selectOne(new LambdaQueryWrapper<DeviceRepair>().eq(DeviceRepair::getId, orderUpdateStatusDTO.getId()));
		if (Objects.isNull(deviceRepair)) {
			return R.fail("当前工单不存在");
		}
		// 获取当前节点操作角色
		List<HussarAssignVo> hussarAssignVos = hussarBpmService.queryTaskInfo(deviceRepair.getFilingNo());
		String roleName = hussarAssignVos.stream().map(HussarAssignVo::getRoleName).collect(Collectors.joining(","));
		String recordNode = null;
		String processStatus = null;
		// 根据当前节点以及审批意见，更新工单状态
		if (DeviceRepairBpmNodeEnum.DEVICE_REPAIR_APPLY.getNode().equals(deviceRepair.getProcessStatus()) && orderUpdateStatusDTO.getWorkerStatus() == 0) {
			String repairType = deviceRepair.getRepairType();
			recordNode = DeviceRepairBpmNodeEnum.DEVICE_REPAIR_APPLY.getNode();
			if (DeviceRepairTypeEnum.COMPUTER.getCode().toString().equals(repairType) || DeviceRepairTypeEnum.PRINTER.getCode().toString().equals(repairType)) {
				processStatus = DeviceRepairBpmNodeEnum.DEVICE_REPAIR_COMMISSIONER.getNode();
			} else {
				processStatus = DeviceRepairBpmNodeEnum.DEVICE_REPAIR_MONITOR_REVIEW.getNode();
			}
		}
		if (DeviceRepairBpmNodeEnum.DEVICE_REPAIR_COMMISSIONER.getNode().equals(deviceRepair.getProcessStatus()) && orderUpdateStatusDTO.getWorkerStatus() == 0) {
			processStatus = DeviceRepairBpmNodeEnum.DEVICE_REPAIR_FINISH.getNode();
			recordNode = DeviceRepairBpmNodeEnum.DEVICE_REPAIR_COMMISSIONER.getNode();
		} else if (DeviceRepairBpmNodeEnum.DEVICE_REPAIR_COMMISSIONER.getNode().equals(deviceRepair.getProcessStatus()) && orderUpdateStatusDTO.getWorkerStatus() == 1) {
			processStatus = DeviceRepairBpmNodeEnum.DEVICE_REPAIR_APPLY.getNode();
			recordNode = DeviceRepairBpmNodeEnum.DEVICE_REPAIR_COMMISSIONER.getNode();
			LambdaUpdateWrapper<ApproveRecord> updateWrapper = new LambdaUpdateWrapper<>();
			updateWrapper.set(ApproveRecord::getOptOpinion, "待审批").set(ApproveRecord::getOptTitle, "待审批").eq(ApproveRecord::getFilingNo, orderUpdateStatusDTO.getId()).eq(ApproveRecord::getNodeId, processStatus);
			approveRecordService.update(updateWrapper);
		}
		if (DeviceRepairBpmNodeEnum.DEVICE_REPAIR_MONITOR_REVIEW.getNode().equals(deviceRepair.getProcessStatus()) && orderUpdateStatusDTO.getWorkerStatus() == 0) {
			processStatus = DeviceRepairBpmNodeEnum.DEVICE_REPAIR_CLASS_MEMBERS.getNode();
			recordNode = DeviceRepairBpmNodeEnum.DEVICE_REPAIR_MONITOR_REVIEW.getNode();
		} else if (DeviceRepairBpmNodeEnum.DEVICE_REPAIR_MONITOR_REVIEW.getNode().equals(deviceRepair.getProcessStatus()) && orderUpdateStatusDTO.getWorkerStatus() == 1) {
			processStatus = DeviceRepairBpmNodeEnum.DEVICE_REPAIR_APPLY.getNode();
			recordNode = DeviceRepairBpmNodeEnum.DEVICE_REPAIR_MONITOR_REVIEW.getNode();
			LambdaUpdateWrapper<ApproveRecord> updateWrapper = new LambdaUpdateWrapper<>();
			updateWrapper.set(ApproveRecord::getOptOpinion, "待审批").set(ApproveRecord::getOptTitle, "待审批").eq(ApproveRecord::getFilingNo, orderUpdateStatusDTO.getId()).eq(ApproveRecord::getNodeId, processStatus);
			approveRecordService.update(updateWrapper);
		}
		if (DeviceRepairBpmNodeEnum.DEVICE_REPAIR_CLASS_MEMBERS.getNode().equals(deviceRepair.getProcessStatus()) && orderUpdateStatusDTO.getWorkerStatus() == 0) {
			processStatus = DeviceRepairBpmNodeEnum.DEVICE_REPAIR_FINISH.getNode();
			recordNode = DeviceRepairBpmNodeEnum.DEVICE_REPAIR_CLASS_MEMBERS.getNode();
		} else if (DeviceRepairBpmNodeEnum.DEVICE_REPAIR_CLASS_MEMBERS.getNode().equals(deviceRepair.getProcessStatus()) && orderUpdateStatusDTO.getWorkerStatus() == 1) {
			processStatus = DeviceRepairBpmNodeEnum.DEVICE_REPAIR_MONITOR_REVIEW.getNode();
			recordNode = DeviceRepairBpmNodeEnum.DEVICE_REPAIR_CLASS_MEMBERS.getNode();
		}
		//推送当前节点信息
		DeviceTicketVO deviceTicketVO = DeviceTicketVO.builder().approveStatus(orderUpdateStatusDTO.getWorkerStatus())
			.name(roleName)
			.filingCode(deviceRepair.getFilingNo())
			.nodeName(DeviceRepairBpmNodeEnum.getMessage(recordNode))
			.optOpinion(orderUpdateStatusDTO.getComment())
			.filingNo(orderUpdateStatusDTO.getId()).type(OptTypeEnum.DEVICE_REPAIR.getCode()).build();
		iEndpointClient.getNode(deviceTicketVO);
		// 增加操作记录
		logOptService.commonLogOpt(LogOpt.builder()
			.logId(orderUpdateStatusDTO.getId())
			.logData(orderUpdateStatusDTO.toString())
			.params(orderUpdateStatusDTO.toString())
			.optType(OptTypeEnum.DEVICE_REPAIR.getCode())
			.title(orderUpdateStatusDTO.getComment())
			.optRole(roleName)
			.time(new Date()).build());
		// 记录审核流程
		approveRecordService.commonRecord(ApproveRecord.builder()
			.filingNo(orderUpdateStatusDTO.getId())
			.optType(OptTypeEnum.DEVICE_REPAIR.getCode())
			.nodeId(recordNode)
			.optRole(roleName)
			.nodeName(DeviceRepairBpmNodeEnum.getMessage(recordNode))
			.optTitle(orderUpdateStatusDTO.getComment())
			.optOpinion(orderUpdateStatusDTO.getComment())
			.filingCode(deviceRepair.getFilingNo())
			.approveStatus(orderUpdateStatusDTO.getWorkerStatus())
			.build());
		// 更新工单信息
		baseMapper.update(new LambdaUpdateWrapper<DeviceRepair>()
			.eq(DeviceRepair::getId, orderUpdateStatusDTO.getId())
			.set(DeviceRepair::getUpdateTime, new Date())
			.set(DeviceRepair::getUpdateUser, user.getUserId())
			.set(DeviceRepair::getProcessStatus, processStatus)
			.set(DeviceRepair::getTicketStatus, DeviceRepairEnum.getCode(DeviceRepairBpmNodeEnum.getMessage(processStatus))));
		// 青岛同步sdn
		if (deviceRepair.getRegionCode().contains("3702")) {
			DeviceSdnQingDao deviceSdnQingDao = new DeviceSdnQingDao();
			deviceSdnQingDao.setFlag("2");
			ArrayList<DeviceRepairDTO> deviceRepairDTOS = new ArrayList<>();
			DeviceRepairDTO deviceRepairDTO = new DeviceRepairDTO();
			BeanUtils.copyProperties(deviceRepair, deviceRepairDTO);
			deviceRepairDTO.setAcceptanceCheckOpinion(orderUpdateStatusDTO.getComment());
			deviceRepairDTO.setRepairType(deviceRepairDTO.getRepairType().equals("1") ? "终端设备维修" : "其他");
			deviceRepairDTO.setProcessStatus("已归档");
			// 归档人
			deviceRepairDTO.setAcceptorMan(user.getUserName());
			String format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
			// 解决开始时间
			deviceRepairDTO.setSolveStartDate(format);
			// 解决人
			deviceRepairDTO.setSolveMan("null");
			// 协助解决人
			deviceRepairDTO.setAssistSolveMan(user.getUserName());
			// 解决方案
			deviceRepairDTO.setSolution(orderUpdateStatusDTO.getComment());
			// 解决结果
			deviceRepairDTO.setSolveResult("已解决");
			// 解决结束日期
			deviceRepairDTO.setSolveEndDate(format);
			deviceRepairDTOS.add(deviceRepairDTO);
			deviceSdnQingDao.setRepairDTOList(deviceRepairDTOS);
			try {
				QDDSwitcherPublisher.publishEvent(deviceSdnQingDao);
			} catch (Exception e) {
				log.error("青岛地市同步sdn设备报修失败");
				throw new Exception(e);
			}
		}

		if (orderUpdateStatusDTO.getWorkerStatus() == 0) {
			HussarBpmDTO hussarBpmDTO = new HussarBpmDTO();
			hussarBpmDTO.setBusinessKey(deviceRepair.getFilingNo());
			hussarBpmDTO.setParticipantType("2");
			hussarBpmDTO.setTaskType("1");
			hussarBpmDTO.setProcessDefinitionKey(HussarBpmTypeEnum.DEVICE_REPAIR.getBpmMark());
			hussarBpmService.hussarSubmit(hussarBpmDTO);
		}
		if (orderUpdateStatusDTO.getWorkerStatus() == 1) {
			HussarBpmDTO hussarBpmDTO = new HussarBpmDTO();
			hussarBpmDTO.setBusinessKey(deviceRepair.getFilingNo());
			hussarBpmDTO.setOrderId(deviceRepair.getId());
			hussarBpmService.prevNodeReject(hussarBpmDTO);
		}
		if (StringUtils.equals(DeviceRepairBpmNodeEnum.DEVICE_REPAIR_FINISH.getNode(), processStatus)) {
			ApproveRecord approveRecord = ApproveRecord.builder()
				.filingNo(orderUpdateStatusDTO.getId())
				.optType(OptTypeEnum.DEVICE_REPAIR.getCode())
				.nodeId(DeviceRepairBpmNodeEnum.DEVICE_REPAIR_FINISH.getNode())
				.optRole("--")
				.optName("系统")
				.filingCode(deviceRepair.getFilingNo())
				.approveStatus(orderUpdateStatusDTO.getWorkerStatus())
				.nodeName(DeviceRepairBpmNodeEnum.getMessage(DeviceRepairBpmNodeEnum.DEVICE_REPAIR_FINISH.getNode()))
				.optTitle("自动归档").optOpinion("自动归档").build();
			approveRecord.setStatus(1);
			approveRecordService.commonRecord(approveRecord);

			LogOpt logOpt = LogOpt.builder().logId(orderUpdateStatusDTO.getId())
				.logData(orderUpdateStatusDTO.toString())
				.params(orderUpdateStatusDTO.toString())
				.optType(OptTypeEnum.DEVICE_REPAIR.getCode())
				.title("自动归档")
				.optName("系统")
				.optRole("--").time(new Date()).build();
			logOpt.setStatus(0);
			logOptService.commonLogOpt(logOpt);
		}
		return R.success(ResultCode.SUCCESS);
	}

	@Override
	public R<IPage<DeviceRepairVO>> deskDeviceRepairList(DeviceRepairDTO dto, Query query) {
		if (StringUtil.isBlank(dto.getOrderNoList())) {
			return R.data(new Page<>());
		}
		dto.setFilingNoList(Arrays.asList(dto.getOrderNoList().split(",")));
		return R.data(baseMapper.deskDeviceRepairList(dto, Condition.getPage(query)));
	}

	@Override
	public List<CiCientitySearchVO> assemble(HardwareBasicQueryRepairVO hardwareBasicQueryVO) {
		HardwareBasicQueryVO vo = new HardwareBasicQueryVO();
		// 2025-01-26 增加实际区域查询
		if (StringUtils.isNotEmpty(hardwareBasicQueryVO.getOwnerUnitCode())) {
			hardwareBasicQueryVO.setOwnerUnitCode("");
		}
		BeanUtils.copyProperties(hardwareBasicQueryVO, vo);
		List<CiCientitySearchVO> ciCientitySearchVOS = CiCientitySearchVO.convertCiCientitySearchVO(vo);
		IdevelopUser user = SecureUtil.getUser();
		String noteComputer = cmdbCientityProperties.getCientityId(CmdbCientityConstant.T10503);
		String tabletComputer = cmdbCientityProperties.getCientityId(CmdbCientityConstant.T10505);
		String computer = cmdbCientityProperties.getCientityId(CmdbCientityConstant.T10501);
		String printer = cmdbCientityProperties.getCientityId(CmdbCientityConstant.T10701);
		String regionCode = user.getRegionCode();
		// 2025-01-26 增加实际区域查询
		if (StringUtils.isNotEmpty(hardwareBasicQueryVO.getOwnerUnitCode())) {
			hardwareBasicQueryVO.setOwnerUnitCode("");
		}
		if (regionCode.length() == 6) {
			CiCientitySearchVO realArea = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.REAL_AREA).expression(Expression.EQUAL).attrValue(regionCode).build();
			ciCientitySearchVOS.add(realArea);
			regionCode = regionCode.substring(0, 4);
		}
		CiCientitySearchVO build = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.AREA).expression(Expression.LIKE).attrValue(regionCode).build();
		ciCientitySearchVOS.add(build);

		if (StringUtil.equals(String.valueOf(DeviceRepairTypeEnum.COMPUTER.getCode()), hardwareBasicQueryVO.getRepairType())) {
			//台式机、笔记本、平板电脑
			CiCientitySearchVO searchVO = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_TYPE_CODE).attrValue(noteComputer + "," + tabletComputer + "," + computer).expression(Expression.LIKE).build();
			ciCientitySearchVOS.add(searchVO);
		}
		if (StringUtil.equals(String.valueOf(DeviceRepairTypeEnum.PRINTER.getCode()), hardwareBasicQueryVO.getRepairType())) {
			//打印机
			CiCientitySearchVO searchVO = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_TYPE_CODE).attrValue(printer).expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO);
		}
		if (StringUtil.equals(String.valueOf(DeviceRepairTypeEnum.OTHER.getCode()), hardwareBasicQueryVO.getRepairType())) {
			CiCientitySearchVO searchVO = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_TYPE_CODE).attrValue(noteComputer + "," + tabletComputer + "," + computer + "," + printer).expression(Expression.NOTLIKE).build();
			ciCientitySearchVOS.add(searchVO);
		}
		return ciCientitySearchVOS;
	}

	@Override
	public List<DeviceRepairList> getDeviceByRepairIdAndStatus(String repairId) {
		return baseMapper.getDeviceByRepairIdAndStatus(repairId);
	}

	@Override
	public R check(DeviceRepairDTO deviceRepairDTO) {
		List<DeviceRepairList> deviceRepairLists = deviceRepairDTO.getDeviceRepairLists();
		for (DeviceRepairList deviceRepairList : deviceRepairLists) {
			//报修校验
			String deviceCode = deviceRepairList.getDeviceCode();
			List<DeviceRepairList> repairLists = deviceRepairListService.getByDeviceCode(deviceCode);
			if (ObjectUtil.isNotEmpty(repairLists)) {
				for (DeviceRepairList repairList : repairLists) {
					DeviceRepair deviceRepair = baseMapper.selectById(repairList.getRepairId());
					if (ObjectUtil.isEmpty(deviceRepair)) {
						continue;
					} else {
						if (!DeviceRepairEnum.FINISH.getCode().equals(deviceRepair.getTicketStatus()) && StringUtil.isNotBlank(deviceRepair.getProcessInsId())
							&& !DeviceRepairBpmNodeEnum.DEVICE_REPAIR_APPLY.getNode().equals(deviceRepair.getProcessStatus())) {
							return R.fail("设备：" + repairList.getDeviceCode() + "已经提交报修，请不要重复提交");
						}
					}
				}
			}
			//变更校验
			List<DeviceChangeList> changeDeviceList = deviceChangeListMapper.getByDeviceCode(deviceCode);
			if (ObjectUtil.isNotEmpty(changeDeviceList)) {
				for (DeviceChangeList deviceChangeList : changeDeviceList) {
					String changeId = deviceChangeList.getChangeId();
					DeviceChange deviceChange = deviceChangeMapper.selectById(changeId);
					if (ObjectUtil.isEmpty(deviceChange)) {
						continue;
					} else {
						if (!DeviceChangeEnum.FINISH.getCode().equals(deviceChange.getTicketStatus())) {
							return R.fail("设备：" + deviceChangeList.getDeviceCode() + "已经提交变更，无法发起报修");
						}
					}
				}
			}
			//报废校验
			List<DeviceScrapList> scrapDeviceList = deviceScrapListMapper.getByDeviceCode(deviceCode);
			if (ObjectUtil.isNotEmpty(scrapDeviceList)) {
				for (DeviceScrapList deviceScrapList : scrapDeviceList) {
					String scrapId = deviceScrapList.getScrapId();
					DeviceScrap deviceScrap = deviceScrapMapper.selectById(scrapId);
					if (ObjectUtil.isEmpty(deviceScrap)) {
						continue;
					} else {
						Integer status = deviceScrap.getStatus();
						if (!DeviceScrapEnum.FINISH.getCode().equals(status)) {
							return R.fail("设备：" + deviceScrapList.getDeviceCode() + "正在报废，无法发起变更");
						}
					}
				}
			}

		}
		return R.success("操作成功");
	}


}
