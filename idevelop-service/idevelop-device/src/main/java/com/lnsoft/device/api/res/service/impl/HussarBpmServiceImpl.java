package com.lnsoft.device.api.res.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lnsoft.common.enums.device.WorkOrderTypeEnum;
import com.lnsoft.common.enums.hussar.*;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.api.ResultCode;
import com.lnsoft.core.tool.utils.CollectionUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.common.enums.hussar.InterfaceBpmNodeEnum;
import com.lnsoft.common.enums.hussar.DeviceChangeBpmNodeEnum;
import com.lnsoft.common.enums.hussar.DeviceRepairBpmNodeEnum;
import com.lnsoft.device.api.res.dto.HussarBpmCreateDTO;
import com.lnsoft.device.api.res.dto.HussarBpmDTO;
import com.lnsoft.device.entity.ApproveRecord;
import com.lnsoft.device.api.res.service.IApproveRecordService;
import com.lnsoft.device.api.res.service.IHussarBpmService;
import com.lnsoft.common.enums.hussar.OptTypeEnum;
import com.lnsoft.hussar.bpm.constants.ParticipantType;
import com.lnsoft.hussar.bpm.domain.dto.*;
import com.lnsoft.hussar.bpm.domain.vo.*;
import com.lnsoft.hussar.bpm.feign.IHussarBpmClient;
import com.lnsoft.system.user.feign.IUserClient;
import com.lnsoft.system.user.vo.BpmUserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 轻骑兵工作流引擎
 *
 * @author Idevelop
 * @since 2024-03-08
 */
@Service
@Slf4j
public class HussarBpmServiceImpl implements IHussarBpmService {

	@Resource
	private IHussarBpmClient hussarBpmClient;
	@Resource
	private IApproveRecordService approveRecordService;
	@Resource
	private IUserClient userClient;
	private static final String FINISH_FLAG = "2";
	private static final String FINISH_EXCEPTION = "4";

	/**
	 * 流程创建
	 *
	 * @param hussarBpmCreateDTO 流程创建需要参数
	 * @return HussarCreateVo 返回流程信息
	 */
	@Override
	public HussarCreateVo createHussarBpm(HussarBpmCreateDTO hussarBpmCreateDTO) {
		IdevelopUser user = SecureUtil.getUser();
		// 组装创建流程参数
		HussarCreateParam hussarCreateParam = new HussarCreateParam();
		hussarCreateParam.setProcessDefinitionKey(hussarBpmCreateDTO.getProcessDefinitionKey());
		hussarCreateParam.setUserId(user.getUserId().toString());
		hussarCreateParam.setBusinessKey(hussarBpmCreateDTO.getBusinessKey());
		hussarCreateParam.setVariable(hussarBpmCreateDTO.getVariable());
		com.lnsoft.hussar.bpm.tool.api.R<HussarCreateVo> hussarCreateVoR = hussarBpmClient.flowCreate(hussarCreateParam);
		HussarCreateVo hussarCreateVo = new HussarCreateVo();
		if (ResultCode.SUCCESS.getCode() == hussarCreateVoR.getCode()) {
			hussarCreateVo = hussarCreateVoR.getData();
		}
		log.debug("创建流程返回信息；" + hussarCreateVo.toString());
		return hussarCreateVo;
	}

	/**
	 * 获取当前流程的taskId
	 *
	 * @param businessKey 关联的业务数据值
	 * @return List
	 */
	@Override
	public List<HussarTaskVo> queryTaskId(String businessKey) throws Exception {
		com.lnsoft.hussar.bpm.tool.api.R<List<HussarTaskVo>> listR = hussarBpmClient.queryTaskIdByBusinessKey(businessKey);
		List<HussarTaskVo> hussarTaskVoList;
		if (ResultCode.SUCCESS.getCode() == listR.getCode()) {
			hussarTaskVoList = listR.getData();
		} else {
			throw new Exception(listR.getMsg());
		}
		return hussarTaskVoList;
	}

	/**
	 * 获取当前节点参与者
	 *
	 * @param businessKey 工单编号
	 * @return List
	 */
	@Override
	public List<BpmUserVO> queryAssignee(String businessKey) throws Exception {
		List<HussarTaskVo> hussarTaskVoList = queryTaskId(businessKey);
		List<BpmUserVO> bpmUserVOList = new ArrayList<>();
		if (CollectionUtils.isEmpty(hussarTaskVoList)) {
			return bpmUserVOList;
		}
		// List<HussarBpmUserVO> hussarBpmUserVOList;
		com.lnsoft.hussar.bpm.tool.api.R<List<String>> listR = hussarBpmClient.queryAssigneeByTaskId(hussarTaskVoList.get(0).getTaskId());
		if (ResultCode.SUCCESS.getCode() == listR.getCode()) {
			List<String> assigneeList = listR.getData();
			// 根据获取的用户id获取用户信息
			// hussarBpmUserVOList = hussarBpmMapper.selectUserList(assigneeList);
			R<List<BpmUserVO>> queryUserInfo = userClient.batchQueryUserInfo(String.join(",", assigneeList));
			if (ResultCode.SUCCESS.getCode() == queryUserInfo.getCode()) {
				bpmUserVOList = queryUserInfo.getData();
			}
		} else {
			throw new Exception(listR.getMsg());
		}
		// return hussarBpmUserVOList;
		return bpmUserVOList;
	}

	/**
	 * 获取下一节点信息
	 *
	 * @param hussarBpmDTO 流程信息
	 * @return List
	 */
	@Override
	public List<HussarNodeVo> queryNextInfo(HussarBpmDTO hussarBpmDTO) throws Exception {
		List<HussarTaskVo> hussarTaskVoList = queryTaskId(hussarBpmDTO.getBusinessKey());
		HussarTaskVo hussarTaskVo = hussarTaskVoList.get(0);
		HussarNodeParam hussarNodeParam = new HussarNodeParam();
		hussarNodeParam.setTaskId(hussarTaskVo.getTaskId());
		hussarNodeParam.setProcessDefinitionKey(hussarTaskVo.getTaskDefinitionKey());
		hussarNodeParam.setVariable(hussarBpmDTO.getVariable());
		List<HussarNodeVo> hussarNodeVoList;
		com.lnsoft.hussar.bpm.tool.api.R<List<HussarNodeVo>> listR = hussarBpmClient.getNextNode(hussarNodeParam);
		if (ResultCode.SUCCESS.getCode() == listR.getCode()) {
			hussarNodeVoList = listR.getData();
		} else {
			throw new Exception(listR.getMsg());
		}
		return hussarNodeVoList;
	}

	/**
	 * 获取下一节点参与者
	 *
	 * @param hussarBpmDTO 请求信息
	 * @return List
	 */
	@Override
	public List<HussarParticipantVo> queryNextParticipant(HussarBpmDTO hussarBpmDTO) throws Exception {
		List<HussarNodeVo> hussarNodeVos = queryNextInfo(hussarBpmDTO);
		List<HussarTaskVo> hussarTaskVoList = queryTaskId(hussarBpmDTO.getBusinessKey());
		HussarParticipantParam hussarParticipantParam = new HussarParticipantParam();
		hussarParticipantParam.setNodeId(hussarNodeVos.get(0).getId());
		hussarParticipantParam.setTaskId(hussarTaskVoList.get(0).getTaskId());
		hussarParticipantParam.setVariable(hussarBpmDTO.getVariable());
		com.lnsoft.hussar.bpm.tool.api.R<List<HussarParticipantVo>> nextNodeParticipant = hussarBpmClient.getNextNodeParticipant(hussarParticipantParam);
		List<HussarParticipantVo> hussarParticipantVoList;
		if (ResultCode.SUCCESS.getCode() == nextNodeParticipant.getCode()) {
			hussarParticipantVoList = nextNodeParticipant.getData();
		} else {
			throw new Exception(nextNodeParticipant.getMsg());
		}
		return hussarParticipantVoList;
	}

	/**
	 * 获取下一节点参与者角色-人员
	 *
	 * @param hussarBpmDTO 请求信息
	 * @return List
	 */
	@Override
	public R<List<HussarAssignVo>> queryNextParticipantAll(HussarBpmDTO hussarBpmDTO) throws Exception {
		IdevelopUser user = SecureUtil.getUser();
		// List<HussarNodeVo> hussarNodeVos = queryNextInfo(hussarBpmDTO.getBusinessKey());
		List<HussarTaskVo> hussarTaskVoList = queryTaskId(hussarBpmDTO.getBusinessKey());
		HussarTaskVo hussarTaskVo = hussarTaskVoList.get(0);
		HussarNodeParam hussarNodeParam = new HussarNodeParam();
		hussarNodeParam.setTaskId(hussarTaskVo.getTaskId());
		hussarNodeParam.setProcessDefinitionKey(hussarTaskVo.getTaskDefinitionKey());
		hussarNodeParam.setVariable(hussarBpmDTO.getVariable());
		List<HussarNodeVo> hussarNodeVos;
		com.lnsoft.hussar.bpm.tool.api.R<List<HussarNodeVo>> listR1 = hussarBpmClient.getNextNode(hussarNodeParam);
		if (ResultCode.SUCCESS.getCode() == listR1.getCode()) {
			hussarNodeVos = listR1.getData();
		} else {
			throw new Exception(listR1.getMsg());
		}
		List<HussarAssignVo> hussarAssignVoList = new ArrayList<>();
		if (HussarBpmTypeEnum.DEVICE_RECORD.getBpmMark().equals(hussarBpmDTO.getProcessDefinitionKey())) {
			String type = DeviceRecordBpmNodeEnum.getType(hussarNodeVos.get(0).getId());
			if (FINISH_FLAG.equals(type) || FINISH_EXCEPTION.equals(type)) {
				return R.data(ResultCode.SUCCESS.getCode(), hussarAssignVoList, null);
			}
		}
		if (HussarBpmTypeEnum.DEVICE_TRANSFER.getBpmMark().equals(hussarBpmDTO.getProcessDefinitionKey())) {
			String type = DeviceTransferBpmNodeEnum.getType(hussarNodeVos.get(0).getId());
			if (FINISH_FLAG.equals(type) || FINISH_EXCEPTION.equals(type)) {
				return R.data(ResultCode.SUCCESS.getCode(), hussarAssignVoList, null);
			}
		}
		if (HussarBpmTypeEnum.DEVICE_SCRAP.getBpmMark().equals(hussarBpmDTO.getProcessDefinitionKey())) {
			String type = DeviceScrapBpmNodeEnum.getType(hussarNodeVos.get(0).getId());
			if (FINISH_FLAG.equals(type) || FINISH_EXCEPTION.equals(type)) {
				return R.data(ResultCode.SUCCESS.getCode(), hussarAssignVoList, null);
			}
		}
		if (HussarBpmTypeEnum.DEVICE_RETURNED.getBpmMark().equals(hussarBpmDTO.getProcessDefinitionKey())) {
			String type = DeviceReturnedBpmNodeEnum.getType(hussarNodeVos.get(0).getId());
			if (FINISH_FLAG.equals(type) || FINISH_EXCEPTION.equals(type)) {
				return R.data(ResultCode.SUCCESS.getCode(), hussarAssignVoList, null);
			}
		}
		if (HussarBpmTypeEnum.DEVICE_CHANGE.getBpmMark().equals(hussarBpmDTO.getProcessDefinitionKey())) {
			String type = DeviceChangeBpmNodeEnum.getType(hussarNodeVos.get(0).getId());
			if (FINISH_FLAG.equals(type) || FINISH_EXCEPTION.equals(type)) {
				return R.data(ResultCode.SUCCESS.getCode(), hussarAssignVoList, null);
			}
		}
		if (HussarBpmTypeEnum.DEVICE_REPAIR.getBpmMark().equals(hussarBpmDTO.getProcessDefinitionKey())) {
			String type = DeviceRepairBpmNodeEnum.getType(hussarNodeVos.get(0).getId());
			if (FINISH_FLAG.equals(type) || FINISH_EXCEPTION.equals(type)) {
				return R.data(ResultCode.SUCCESS.getCode(), hussarAssignVoList, null);
			}
		}
		if (HussarBpmTypeEnum.DEVICE_APPLY_OUTBOUND_OPERATION.getBpmMark().equals(hussarBpmDTO.getProcessDefinitionKey())) {
			String type = DeviceApplyOutboundOperationBpmNodeEnum.getType(hussarNodeVos.get(0).getId());
			if (FINISH_FLAG.equals(type) || FINISH_EXCEPTION.equals(type)) {
				return R.data(ResultCode.SUCCESS.getCode(), hussarAssignVoList, null);
			}
			String regionName = (String) hussarBpmDTO.getVariable().get("regionName");
			if(StringUtil.isNotBlank(regionName)){
				String regionFullName = user.getRegionFullName();
				int i = regionFullName.lastIndexOf('_');
				if (i!=-1){
					String substring = regionFullName.substring(i + 1);
					if (!substring.equals(regionName)){
						user.setRegionFullName(regionFullName+"_"+regionName);
					}
				}
			}
		}
		if (HussarBpmTypeEnum.INTERFACE_APPLY.getBpmMark().equals(hussarBpmDTO.getProcessDefinitionKey())) {
			String type = InterfaceBpmNodeEnum.getType(hussarNodeVos.get(0).getId());
			if (FINISH_FLAG.equals(type) || FINISH_EXCEPTION.equals(type)) {
				return R.data(ResultCode.SUCCESS.getCode(), hussarAssignVoList, null);
			}
		}
		List<String> message = new ArrayList<>();
		HussarAssignParam hussarAssignParam = new HussarAssignParam();
		hussarAssignParam.setTaskDefinitionKey(hussarNodeVos.get(0).getId());
		hussarAssignParam.setProcessDefinitionKey(hussarBpmDTO.getProcessDefinitionKey());
		com.lnsoft.hussar.bpm.tool.api.R<List<HussarAssignVo>> listR = hussarBpmClient.getAssignRolesWithUserFromNode(hussarAssignParam);
		log.info("获取下一节点审批角色：{}", listR.toString());
		if (ResultCode.SUCCESS.getCode() == listR.getCode()) {
			List<HussarAssignVo> hussarAssignVo = listR.getData();
			// 获取工作流角色信息
			getHussarBpmRoleNameMessage(user, hussarAssignVoList, hussarAssignVo, message);
		} else {
			throw new Exception(listR.getMsg());
		}
		return R.data(ResultCode.SUCCESS.getCode(), hussarAssignVoList, String.join(",", message));
	}

	/**
	 * 获取工作流角色信息（组装角色信息）
	 *
	 * @param user               用户信息
	 * @param hussarAssignVoList 工作流角色-用户信息
	 * @param hussarAssignVo     查询到的工作流信息
	 * @param message            角色信息
	 */
	private void getHussarBpmRoleNameMessage(IdevelopUser user, List<HussarAssignVo> hussarAssignVoList, List<HussarAssignVo> hussarAssignVo, List<String> message) {
		// 用户中获取的是简称，例如历下、槐荫
		String regionName = user.getRegionFullName();
		// 根据区域编码获取区域信息
		hussarAssignVo.forEach(item -> {
			// 获取的工作流程角色例如：山东_济南_历下_运维人员
			String roleName = item.getRoleName();
			if (roleName.contains("_")) {
				String userRoleName = roleName.substring(0, roleName.lastIndexOf("_"));
				if (regionName.equals(userRoleName)) {
					hussarAssignVoList.add(item);
					message.add(roleName.substring(roleName.lastIndexOf("_") + 1));
				}
			} else {
				hussarAssignVoList.add(item);
			}
		});
	}

	/**
	 * 获取工作流角色信息
	 *
	 * @param user               用户信息
	 * @param hussarAssignVoList 工作流角色-用户信息
	 * @param hussarAssignVo     查询到的工作流信息
	 */
	private void getHussarBpmRoleName(IdevelopUser user, List<HussarAssignVo> hussarAssignVoList, List<HussarAssignVo> hussarAssignVo) {
		// 用户中获取的是简称，例如历下、槐荫
		String userRoleName = user.getRoleName();
		List<String> stringList = Arrays.asList(userRoleName.split(","));
		// 根据区域编码获取区域信息
		hussarAssignVo.forEach(item -> {
			// 获取的工作流程角色例如：山东_济南_历下_运维人员
			String roleName = item.getRoleName();
			int indexLength = stringList.indexOf(roleName);
			if (indexLength != -1) {
				hussarAssignVoList.add(item);
			}
		});
	}

	/**
	 * 提交流程表单
	 *
	 * @param hussarBpmDTO 提交流程表单
	 * @return R
	 */
	@Override
	public R<List<HussarComplateVo>> hussarSubmit(HussarBpmDTO hussarBpmDTO) throws Exception {
		IdevelopUser user = SecureUtil.getUser();
		List<HussarAssignVo> hussarAssignVoList = queryTaskInfo(hussarBpmDTO.getBusinessKey());
		String message = hussarAssignVoList.stream().map(HussarAssignVo::getRoleName).collect(Collectors.joining(","));
		R<List<HussarAssignVo>> participantAll = queryNextParticipantAll(hussarBpmDTO);
		List<HussarAssignVo> hussarAssignVos;
		if (ResultCode.SUCCESS.getCode() == participantAll.getCode()) {
			hussarAssignVos = participantAll.getData();
		} else {
			return R.fail(participantAll.getMsg());
		}
		HussarCompleteParam hussarCompleteParam = new HussarCompleteParam();
		hussarCompleteParam.setComment(hussarBpmDTO.getComment());
		List<HussarNodeVo> hussarNodeVos = queryNextInfo(hussarBpmDTO);
		hussarCompleteParam.setTaskDefinitionKey(hussarNodeVos.get(0).getId());
		List<HussarTaskVo> hussarTaskVoList = queryTaskId(hussarBpmDTO.getBusinessKey());
		HussarTaskVo hussarTaskVo = hussarTaskVoList.get(0);
		hussarCompleteParam.setTaskId(hussarTaskVo.getTaskId());
		hussarCompleteParam.setUserId(user.getUserId().toString());
		hussarCompleteParam.setParticipantType(StringUtil.isNotBlank(hussarBpmDTO.getParticipantType()) ? hussarBpmDTO.getParticipantType() : "1");
		if (hussarBpmDTO.getBusinessKey().startsWith(WorkOrderTypeEnum.SQ.getValue()) && Objects.nonNull(hussarBpmDTO.getVariable())
			&& Objects.nonNull(hussarBpmDTO.getVariable().get("returnType"))
			&& Objects.nonNull(hussarBpmDTO.getVariable().get("orderUser"))) {
			hussarCompleteParam.setParticipantWithUsers(hussarBpmDTO.getVariable().get("orderUser").toString());
		} else {
			if (CollectionUtil.isNotEmpty(hussarAssignVos)) {
				if (ParticipantType.ROLE.getCode().equals(hussarBpmDTO.getParticipantType())) {
					hussarCompleteParam.setParticipantWithRoles(hussarAssignVos.stream().map(HussarAssignVo::getRoleId).collect(Collectors.joining(",")));
				}
				if (ParticipantType.USER.getCode().equals(hussarBpmDTO.getParticipantType())) {
					List<String> hussarUserList = new ArrayList<>();
					List<String> finalHussarUserList = hussarUserList;
					hussarAssignVos.forEach(item -> {
						List<HussarUser> users = item.getUsers();
						if (CollectionUtil.isNotEmpty(users)) {
							finalHussarUserList.addAll(users.stream().map(HussarUser::getUserId).collect(Collectors.toList()));
						}
					});
					if (CollectionUtil.isNotEmpty(hussarUserList)) {
						if (hussarBpmDTO.getBusinessKey().startsWith(WorkOrderTypeEnum.SQ.getValue()) && Objects.nonNull(hussarBpmDTO.getVariable())
							&& Objects.nonNull(hussarBpmDTO.getVariable().get("digitalFlag"))
							&& Objects.nonNull(hussarBpmDTO.getVariable().get("orderDept"))) {
							// 获取部门下的用户
							R<List<BpmUserVO>> deptUserList = userClient.selectUserListByDept(hussarBpmDTO.getVariable().get("orderDept").toString(), String.join(",", hussarUserList));
							if (ResultCode.SUCCESS.getCode() == deptUserList.getCode()) {
								List<BpmUserVO> userList = deptUserList.getData();
								if (CollectionUtils.isNotEmpty(userList)) {
									hussarUserList = userList.stream().map(BpmUserVO::getId).map(Objects::toString).collect(Collectors.toList());
								}
							}
						}
						hussarCompleteParam.setParticipantWithUsers(String.join(",", hussarUserList));
					}
				}
				hussarCompleteParam.setTaskType(StringUtil.isNotBlank(hussarBpmDTO.getTaskType()) ? hussarBpmDTO.getTaskType() : "1");
			} else {
				hussarCompleteParam.setParticipantType(null);
			}
		}
		Map<String, Object> variable = hussarBpmDTO.getVariable();
		if (Objects.isNull(variable)) {
			variable = new HashMap<>();
		}
		variable.put("orderNo", hussarBpmDTO.getBusinessKey());
		variable.put("userId", user.getUserId());
		variable.put("userName", user.getUserName());
		variable.put("regionCode", user.getRegionCode());
		hussarCompleteParam.setVariable(variable);
		com.lnsoft.hussar.bpm.tool.api.R<List<HussarComplateVo>> listR = hussarBpmClient.flowSubmit(hussarCompleteParam);
		List<HussarComplateVo> hussarCompleteVoList;
		if (ResultCode.SUCCESS.getCode() == listR.getCode()) {
			hussarCompleteVoList = listR.getData();
		} else {
			throw new Exception(listR.getMsg());
		}
		return R.data(ResultCode.SUCCESS.getCode(), hussarCompleteVoList, message);
	}

	/**
	 * 驳回至上一节点
	 *
	 * @param hussarBpmDTO 驳回参数
	 * @return List
	 */
	@Override
	public R<List<HussarComplateVo>> prevNodeReject(HussarBpmDTO hussarBpmDTO) throws Exception {
		IdevelopUser user = SecureUtil.getUser();
		List<HussarAssignVo> hussarAssignVoList = queryTaskInfo(hussarBpmDTO.getBusinessKey());
		String message = hussarAssignVoList.stream().map(HussarAssignVo::getRoleName).collect(Collectors.joining(","));
		List<HussarTaskVo> hussarTaskVoList = queryTaskId(hussarBpmDTO.getBusinessKey());
		HussarTaskVo hussarTaskVo = hussarTaskVoList.get(0);
		List<HussarComplateVo> hussarCompleteVoList;
		HussarRejectParam hussarRejectParam = new HussarRejectParam();
		hussarRejectParam.setUserId(user.getUserId().toString());
		hussarRejectParam.setTaskId(hussarTaskVo.getTaskId());
		hussarRejectParam.setComment(hussarBpmDTO.getComment());
		Map<String, Object> variable = hussarBpmDTO.getVariable();
		hussarRejectParam.setVariable(variable);
		List<String> assignees = new ArrayList<>();
		boolean flag = false;
		String optType = null;
		String node = null;
		// 在业务中判断是否是驳回到发起节点，如果驳回到发起节点，assignees 需要传驳回人员信息
		if (hussarBpmDTO.getBusinessKey().startsWith(WorkOrderTypeEnum.JD.getValue()) && hussarTaskVo.getTaskId().equals(DeviceRecordBpmNodeEnum.DEVICE_RECORD_PROFESSIONAL_REVIEW.getNode())) {
			flag = true;
			optType = OptTypeEnum.DEVICE_FILING.getCode();
			node = DeviceRecordBpmNodeEnum.DEVICE_RECORD_APPLY.getNode();
		}
		if (hussarBpmDTO.getBusinessKey().startsWith(WorkOrderTypeEnum.SQ.getValue()) && hussarTaskVo.getTaskId().equals(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_APPLY_DIRECTOR.getNode())) {
			flag = true;
			optType = OptTypeEnum.DEVICE_APPLY.getCode();
			node = DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_APPLY.getNode();
		}
		if (hussarBpmDTO.getBusinessKey().startsWith(WorkOrderTypeEnum.BG.getValue()) && hussarTaskVo.getTaskId().equals(DeviceChangeBpmNodeEnum.DEVICE_CHANGE_OPERATION_REVIEW.getNode())) {
			flag = true;
			optType = OptTypeEnum.DEVICE_CHANGE.getCode();
			node = DeviceChangeBpmNodeEnum.DEVICE_CHANGE_APPLY.getNode();
		}
		if (hussarBpmDTO.getBusinessKey().startsWith(WorkOrderTypeEnum.BX.getValue()) &&
			(hussarTaskVo.getTaskId().equals(DeviceRepairBpmNodeEnum.DEVICE_REPAIR_MONITOR_REVIEW.getNode()) || hussarTaskVo.getTaskId().equals(DeviceRepairBpmNodeEnum.DEVICE_REPAIR_COMMISSIONER.getNode()))) {
			flag = true;
			optType = OptTypeEnum.DEVICE_REPAIR.getCode();
			node = DeviceRepairBpmNodeEnum.DEVICE_REPAIR_APPLY.getNode();
		}
		if (flag) {
			ApproveRecord approveRecord = approveRecordService.getOne(new LambdaQueryWrapper<ApproveRecord>().eq(ApproveRecord::getFilingNo, hussarBpmDTO.getOrderId())
				.eq(ApproveRecord::getOptType, optType).eq(ApproveRecord::getNodeId, node).orderByDesc(ApproveRecord::getCreateTime).last("LIMIT 1"));
			assignees.add(approveRecord.getCreateUser().toString());
		}
		hussarRejectParam.setAssignees(assignees);
		com.lnsoft.hussar.bpm.tool.api.R<List<HussarComplateVo>> listR = hussarBpmClient.prevNodeReject(hussarRejectParam);
		if (ResultCode.SUCCESS.getCode() == listR.getCode()) {
			hussarCompleteVoList = listR.getData();
		} else {
			throw new Exception(listR.getMsg());
		}
		return R.data(ResultCode.SUCCESS.getCode(), hussarCompleteVoList, message);
	}

	/**
	 * 获取任意节点参与者
	 *
	 * @param hussarBpmDTO 请求参数
	 * @return List
	 */
	@Override
	public List<HussarAssignVo> getAnyNodeParticipant(HussarBpmDTO hussarBpmDTO) throws Exception {
		IdevelopUser user = SecureUtil.getUser();
		HussarAssignParam hussarAssignParam = new HussarAssignParam();
		hussarAssignParam.setTaskDefinitionKey(hussarBpmDTO.getTaskDefinitionKey());
		hussarAssignParam.setProcessDefinitionKey(hussarBpmDTO.getProcessDefinitionKey());
		List<HussarAssignVo> hussarAssignVoList = new ArrayList<>();
		com.lnsoft.hussar.bpm.tool.api.R<List<HussarAssignVo>> listR = hussarBpmClient.getAssignRolesWithUserFromNode(hussarAssignParam);
		if (ResultCode.SUCCESS.getCode() == listR.getCode()) {
			List<HussarAssignVo> hussarAssignVo = listR.getData();
			// 获取工作流角色信息
			getHussarBpmRoleName(user, hussarAssignVoList, hussarAssignVo);
		} else {
			throw new Exception(listR.getMsg());
		}
		return hussarAssignVoList;
	}

	/**
	 * 获取个人待办列表
	 *
	 * @param processKeys 流程标识多个用英文逗号拼接
	 * @return String
	 */
	@Override
	public String getAllTodoList(String processKeys) throws Exception {
		IdevelopUser user = SecureUtil.getUser();
		com.lnsoft.hussar.bpm.tool.api.R<List<HussarFlowTaskVo>> allTodoList = hussarBpmClient.getAllTodoList(user.getUserId().toString(), processKeys);
		String orderNo;
		if (ResultCode.SUCCESS.getCode() == allTodoList.getCode()) {
			List<HussarFlowTaskVo> allTodoListData = allTodoList.getData();
			orderNo = allTodoListData.stream().map(HussarFlowTaskVo::getBusinessId).collect(Collectors.joining(","));
		} else {
			throw new Exception(allTodoList.getMsg());
		}
		return orderNo;
	}

	/**
	 * 获取个人已办列表
	 *
	 * @param processKeys 流程标识多个用英文逗号拼接
	 * @return String
	 * @throws Exception 异常
	 */
	@Override
	public String getAllDoneListByUnfinished(String processKeys) throws Exception {
		IdevelopUser user = SecureUtil.getUser();
		com.lnsoft.hussar.bpm.tool.api.R<List<HussarFlowProcinst>> allDoneListByUnfinished = hussarBpmClient.getAllDoneListByUnfinished(user.getUserId().toString(), processKeys);
		String orderNo = null;
		if (ResultCode.SUCCESS.getCode() == allDoneListByUnfinished.getCode()) {
			List<HussarFlowProcinst> allDoneListByUnfinishedData = allDoneListByUnfinished.getData();
			if (CollectionUtil.isNotEmpty(allDoneListByUnfinishedData)) {
				orderNo = allDoneListByUnfinishedData.stream().map(HussarFlowProcinst::getBusinessId).collect(Collectors.joining(","));
			}
		} else {
			throw new Exception(allDoneListByUnfinished.getMsg());
		}
		return orderNo;
	}

	/**
	 * 获取当前节点参与者角色-人员
	 *
	 * @param businessKey 工单编号
	 * @return List
	 */
	@Override
	public List<HussarAssignVo> queryTaskInfo(String businessKey) throws Exception {
		List<HussarTaskVo> hussarTaskVoList = queryTaskId(businessKey);
		HussarTaskVo hussarTaskVo = hussarTaskVoList.get(0);
		com.lnsoft.hussar.bpm.tool.api.R<List<HussarAssignVo>> roleWithUserFromTask = hussarBpmClient.getRoleWithUserFromTask(hussarTaskVo.getTaskId());
		List<HussarAssignVo> hussarAssignVoList = new ArrayList<>();
		IdevelopUser user = SecureUtil.getUser();
		if (ResultCode.SUCCESS.getCode() == roleWithUserFromTask.getCode()) {
			List<HussarAssignVo> hussarAssignVo = roleWithUserFromTask.getData();
			// 获取工作流角色信息
			getHussarBpmRoleName(user, hussarAssignVoList, hussarAssignVo);
		} else {
			throw new Exception(roleWithUserFromTask.getMsg());
		}
		return hussarAssignVoList;
	}

	/**
	 * 自由跳转
	 *
	 * @param hussarBpmDTO 跳转参数
	 * @return R
	 */
	@Override
	public R<List<HussarComplateVo>> freeJump(HussarBpmDTO hussarBpmDTO) throws Exception {
		IdevelopUser user = SecureUtil.getUser();
		List<HussarTaskVo> hussarTaskVoList = queryTaskId(hussarBpmDTO.getBusinessKey());
		HussarTaskVo hussarTaskVo = hussarTaskVoList.get(0);
		if (Objects.isNull(hussarTaskVo)) {
			return R.fail("流程异常");
		}
		List<HussarAssignVo> hussarAssignVoList = queryTaskInfo(hussarBpmDTO.getBusinessKey());
		String message = hussarAssignVoList.stream().map(HussarAssignVo::getRoleName).collect(Collectors.joining(","));
		List<HussarAssignVo> anyNodeParticipant = getAnyNodeParticipant(hussarBpmDTO);
		HussarCompleteParam hussarCompleteParam = new HussarCompleteParam();
		if (CollectionUtil.isNotEmpty(anyNodeParticipant)) {
			hussarCompleteParam.setParticipantType(StringUtil.isNotBlank(hussarBpmDTO.getParticipantType()) ? hussarBpmDTO.getParticipantType() : "1");
			if (ParticipantType.ROLE.getCode().equals(hussarBpmDTO.getParticipantType())) {
				hussarCompleteParam.setParticipantWithRoles(anyNodeParticipant.stream().map(HussarAssignVo::getRoleId).collect(Collectors.joining(",")));
			}
			if (ParticipantType.USER.getCode().equals(hussarBpmDTO.getParticipantType())) {
				List<String> hussarUserList = new ArrayList<>();
				anyNodeParticipant.forEach(item -> {
					List<HussarUser> users = item.getUsers();
					if (CollectionUtil.isNotEmpty(users)) {
						hussarUserList.addAll(users.stream().map(HussarUser::getUserId).collect(Collectors.toList()));
					}
				});
				if (CollectionUtil.isNotEmpty(hussarUserList)) {
					hussarCompleteParam.setParticipantWithUsers(String.join(",", hussarUserList));
				}
			}
			hussarCompleteParam.setTaskType(StringUtil.isNotBlank(hussarBpmDTO.getTaskType()) ? hussarBpmDTO.getTaskType() : "1");
		} else {
			hussarCompleteParam.setParticipantType(null);
		}
		Map<String, Object> variable = hussarBpmDTO.getVariable();
		variable.put("orderNo", hussarBpmDTO.getBusinessKey());
		variable.put("userId", user.getUserId());
		variable.put("userName", user.getUserName());
		variable.put("regionCode", user.getRegionCode());
		hussarCompleteParam.setVariable(variable);
		hussarCompleteParam.setUserId(user.getUserId().toString());
		hussarCompleteParam.setTaskId(hussarTaskVo.getTaskId());
		hussarCompleteParam.setComment(hussarBpmDTO.getComment());
		hussarCompleteParam.setTaskDefinitionKey(hussarBpmDTO.getTaskDefinitionKey());
		com.lnsoft.hussar.bpm.tool.api.R<List<HussarComplateVo>> listR = hussarBpmClient.freeJump(hussarCompleteParam);
		List<HussarComplateVo> hussarCompleteVoList;
		if (ResultCode.SUCCESS.getCode() == listR.getCode()) {
			hussarCompleteVoList = listR.getData();
		} else {
			throw new Exception(listR.getMsg());
		}
		return R.data(ResultCode.SUCCESS.getCode(), hussarCompleteVoList, message);
	}

	/**
	 * 获取当前节点参与者
	 *
	 * @param businessKey 工单编号
	 * @return List
	 */
	@Override
	public List<BpmUserVO> queryUserInfo(String businessKey) throws Exception {
		List<HussarTaskVo> hussarTaskVoList = queryTaskId(businessKey);
		if (CollectionUtil.isEmpty(hussarTaskVoList)) {
			return new ArrayList<>();
		}
		IdevelopUser user = SecureUtil.getUser();
		HussarTaskVo hussarTaskVo = hussarTaskVoList.get(0);
		com.lnsoft.hussar.bpm.tool.api.R<List<HussarAssignVo>> roleWithUserFromTask = hussarBpmClient.getRoleWithUserFromTask(hussarTaskVo.getTaskId());
		List<HussarAssignVo> hussarAssignVoList = new ArrayList<>();
		List<String> userIdList = new ArrayList<>();
		if (ResultCode.SUCCESS.getCode() == roleWithUserFromTask.getCode()) {
			List<HussarAssignVo> roleWithUserFromTaskData = roleWithUserFromTask.getData();
			// 获取工作流角色信息
			getHussarBpmRoleName(user, hussarAssignVoList, roleWithUserFromTaskData);
			hussarAssignVoList.forEach(item -> {
				List<HussarUser> users = item.getUsers();
				if (CollectionUtil.isNotEmpty(users)) {
					List<String> collect = users.stream().map(HussarUser::getUserId).collect(Collectors.toList());
					userIdList.addAll(collect);
				}
			});
		} else {
			throw new Exception(roleWithUserFromTask.getMsg());
		}
		List<BpmUserVO> bpmUserVoList = new ArrayList<>();
		if (CollectionUtil.isNotEmpty(userIdList)) {
			R<List<BpmUserVO>> bpmUserVOR = userClient.batchQueryUserInfo(StringUtil.join(userIdList, ","));
			if (ResultCode.SUCCESS.getCode() == bpmUserVOR.getCode()) {
				bpmUserVoList = bpmUserVOR.getData();
			}
		}
		return bpmUserVoList;
	}

	/**
	 * 终结流程
	 *
	 * @param businessKey
	 * @return
	 * @throws Exception
	 */
	@Override
	public R endProcess(String businessKey, String userId) throws Exception {
		List<HussarTaskVo> hussarTaskVos = queryTaskId(businessKey);
		HussarEndProcessParam endProcessParam = new HussarEndProcessParam();
		HussarTaskVo hussarTaskVo = hussarTaskVos.get(0);
		endProcessParam.setTaskId(hussarTaskVo.getTaskId());
		// erp角色
		endProcessParam.setUserId(userId);
		endProcessParam.setComment("ERP未通过，终结流程！");
		com.lnsoft.hussar.bpm.tool.api.R result = hussarBpmClient.endProcess(endProcessParam);
		if (ResultCode.SUCCESS.getCode() == result.getCode()) {
			return R.success(ResultCode.SUCCESS);
		} else {
			throw new Exception(result.getMsg());
		}
	}

}
