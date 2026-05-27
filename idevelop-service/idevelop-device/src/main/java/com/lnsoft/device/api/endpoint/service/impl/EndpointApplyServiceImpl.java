/**
 * .
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
package com.lnsoft.device.api.endpoint.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.common.enums.device.WorkOrderTypeEnum;
import com.lnsoft.common.enums.hussar.HussarBpmTypeEnum;
import com.lnsoft.common.enums.hussar.InterfaceBpmNodeEnum;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.api.ResultCode;
import com.lnsoft.core.tool.utils.BeanUtil;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.endpoint.dto.EndpointApplyDTO;
import com.lnsoft.device.api.endpoint.entity.EndpointApply;
import com.lnsoft.device.api.endpoint.entity.EndpointPortVO;
import com.lnsoft.device.api.endpoint.mapper.EndpointApplyMapper;
import com.lnsoft.device.api.endpoint.service.IEndpointApplyService;
import com.lnsoft.device.api.endpoint.vo.EndpointApplyVO;
import com.lnsoft.device.api.res.dto.HussarBpmCreateDTO;
import com.lnsoft.device.api.res.dto.HussarBpmDTO;
import com.lnsoft.device.entity.ApproveRecord;
import com.lnsoft.device.entity.LogOpt;
import com.lnsoft.device.api.res.service.IApproveRecordService;
import com.lnsoft.device.api.res.service.IHussarBpmService;
import com.lnsoft.device.api.res.service.ILogOptService;
import com.lnsoft.device.api.warehouse.dto.OrderUpdateStatusDTO;
import com.lnsoft.device.constant.DeviceConstant;
import com.lnsoft.common.enums.hussar.OptTypeEnum;
import com.lnsoft.device.utils.OrderNumberUtil;
import com.lnsoft.endpoint.entity.EndpointPortUser;
import com.lnsoft.hussar.bpm.domain.vo.HussarAssignVo;
import com.lnsoft.hussar.bpm.domain.vo.HussarCreateVo;
import com.lnsoft.system.user.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据共享接口申请表 服务实现类
 *
 * @author Idevelop
 * @since 2024-07-17
 */
@Service
public class EndpointApplyServiceImpl extends BaseServiceImpl<EndpointApplyMapper, EndpointApply> implements IEndpointApplyService {
	@Resource
	private IHussarBpmService hussarBpmService;
	@Resource
	private IApproveRecordService approveRecordService;
	@Resource
	private OrderNumberUtil orderNumberUtil;
	@Resource
	private ILogOptService logOptService;

	@Override
	public IPage<EndpointApplyVO> selectEndpointApplyPage(IPage<EndpointApplyVO> page, EndpointApplyVO endpointApply) {
		return page.setRecords(baseMapper.selectEndpointApplyPage(page, endpointApply));
	}

	@Override
	public EndpointApplyVO staging(EndpointApplyDTO endpointApply) {
		if (ObjectUtil.isEmpty(endpointApply.getVaildTime())) {
			endpointApply.setVaildTime(LocalDate.of(2029, 12, 31));
		}
		String filingNo = orderNumberUtil.generateNumber(WorkOrderTypeEnum.JK);
		endpointApply.setFilingNo(filingNo);
		endpointApply.setCreateTime(new Date());
		endpointApply.setCreateUser(SecureUtil.getUserId());
		if (StringUtils.isNotEmpty(endpointApply.getId())) {
			baseMapper.updateById(endpointApply);
			//更新工单与接口清单绑定表
			baseMapper.deletePort(endpointApply.getId());
			baseMapper.insertPort(endpointApply.getPortId(), endpointApply.getFilingNo(), endpointApply.getId());
		} else {
			int insert = baseMapper.insert(endpointApply);
			baseMapper.insertPort(endpointApply.getPortId(), endpointApply.getFilingNo(), endpointApply.getId());
		}

		EndpointApplyVO endpointApplyVO = new EndpointApplyVO();
		BeanUtil.copy(endpointApply, endpointApplyVO);
		return endpointApplyVO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public EndpointApplyVO submit(EndpointApplyDTO endpointApply) throws Exception {
		if (ObjectUtil.isEmpty(endpointApply.getVaildTime())) {
			endpointApply.setVaildTime(LocalDate.of(2029, 12, 31));
		}
		IdevelopUser user = SecureUtil.getUser();
		//保存工单信息
		LambdaQueryWrapper<EndpointApply> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(EndpointApply::getId, endpointApply.getId());
		EndpointApply selectOne = baseMapper.selectOne(queryWrapper);
		if (ObjectUtil.isNotEmpty(selectOne)) {
			//更新工单表
			baseMapper.updateById(endpointApply);
			//更新工单与接口清单绑定表
			baseMapper.deletePort(endpointApply.getId());
			baseMapper.insertPort(endpointApply.getPortId(), endpointApply.getFilingNo(), endpointApply.getId());
		} else {
			String filingNo = orderNumberUtil.generateNumber(WorkOrderTypeEnum.JK);
			endpointApply.setFilingNo(filingNo);
			endpointApply.setCreateTime(new Date());
			endpointApply.setCreateUser(SecureUtil.getUserId());
			int insert = baseMapper.insert(endpointApply);
			baseMapper.insertPort(endpointApply.getPortId(), endpointApply.getFilingNo(), endpointApply.getId());
		}
		String roleName = null;
		String processInsId = null;
		if (StringUtil.isNotBlank(endpointApply.getProcessInsId())) {
			//  获取当前节点操作角色
			List<HussarAssignVo> hussarAssignVos = hussarBpmService.queryTaskInfo(selectOne.getFilingNo());
			roleName = hussarAssignVos.stream().map(HussarAssignVo::getRoleName).collect(Collectors.joining(","));
			HussarBpmDTO hussarBpmDTO = new HussarBpmDTO();
			hussarBpmDTO.setBusinessKey(selectOne.getFilingNo());
			hussarBpmDTO.setParticipantType("2");
			hussarBpmDTO.setTaskType("1");
			hussarBpmDTO.setProcessDefinitionKey(HussarBpmTypeEnum.INTERFACE_APPLY.getBpmMark());
			hussarBpmService.hussarSubmit(hussarBpmDTO);
		} else {
			Map<String, Object> variable = new HashMap<>();
			variable.put("orderId", endpointApply.getId());
			variable.put("orderNo", endpointApply.getFilingNo());
			variable.put("userId", user.getUserId());
			variable.put("userName", user.getUserName());
			variable.put("regionCode", user.getRegionCode());
			variable.put(DeviceConstant.APPROVAL_OPINION, "发起接口申请审批");
			HussarBpmCreateDTO hussarBpmCreateDTO = HussarBpmCreateDTO.builder().processDefinitionKey(HussarBpmTypeEnum.INTERFACE_APPLY.getBpmMark())
				.businessKey(endpointApply.getFilingNo()).variable(variable).build();
			HussarCreateVo hussarBpm;
			// 发起流程
			try {
				hussarBpm = hussarBpmService.createHussarBpm(hussarBpmCreateDTO);
				//  获取当前节点操作角色
				List<HussarAssignVo> hussarAssignVoList = hussarBpmService.queryTaskInfo(endpointApply.getFilingNo());
				roleName = hussarAssignVoList.stream().map(HussarAssignVo::getRoleName).collect(Collectors.joining(","));
				processInsId = hussarBpm.getProcessInsId();
			} catch (Exception e) {
				throw new Exception("创建流程发生异常");
			}
			HussarBpmDTO hussarBpmDTO = new HussarBpmDTO();
			hussarBpmDTO.setBusinessKey(endpointApply.getFilingNo());
			hussarBpmDTO.setParticipantType("2");
			hussarBpmDTO.setTaskType("1");
			hussarBpmDTO.setProcessDefinitionKey(HussarBpmTypeEnum.DEVICE_CHANGE.getBpmMark());
			hussarBpmService.hussarSubmit(hussarBpmDTO);
		}


		// 记录流程id
		baseMapper.update(new LambdaUpdateWrapper<EndpointApply>().eq(EndpointApply::getId, endpointApply.getId())
			.set(StringUtils.isNotBlank(processInsId), EndpointApply::getProcessInsId, processInsId)
			.set(EndpointApply::getProcessStatus, InterfaceBpmNodeEnum.CITY_LEADERS_REVIEW.getNode()));
		//增加操作记录
		logOptService.commonLogOpt(LogOpt.builder().logId(String.valueOf(endpointApply.getId())).logData(endpointApply.toString()).params(endpointApply.toString())
			.optRole("--").optType(OptTypeEnum.INTERFACE_APPLY.getCode()).title("提交接口申请").build());
		// 记录审核流程
		approveRecordService.commonRecord(ApproveRecord.builder().filingNo(String.valueOf(endpointApply.getId()))
			.optRole(roleName).optType(OptTypeEnum.INTERFACE_APPLY.getCode()).nodeId(InterfaceBpmNodeEnum.INTERFACE_APPLY.getNode())
			.nodeName(InterfaceBpmNodeEnum.getMessage(InterfaceBpmNodeEnum.INTERFACE_APPLY.getNode()))
			.optTitle("发起接口申请审批").optOpinion("发起接口申请审批").filingCode(endpointApply.getFilingNo()).approveStatus(0).build());
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R approval(OrderUpdateStatusDTO orderUpdateStatusDTO) throws Exception {
		IdevelopUser user = SecureUtil.getUser();
		EndpointApply endpointApply = baseMapper.selectOne(new LambdaQueryWrapper<EndpointApply>().eq(EndpointApply::getId, orderUpdateStatusDTO.getId()));
		if (ObjectUtil.isEmpty(endpointApply)) {
			return R.fail("当前工单不存在");
		}
		//获取当前节点审批角色
		List<HussarAssignVo> hussarAssignVos = hussarBpmService.queryTaskInfo(endpointApply.getFilingNo());
		String roleName = hussarAssignVos.stream().map(HussarAssignVo::getRoleName).collect(Collectors.joining(","));
		String recordNode = null;
		String processStatus = null;
		if (orderUpdateStatusDTO.getWorkerStatus() == 0) {
			//同意
			if (InterfaceBpmNodeEnum.INTERFACE_APPLY.getNode().equals(endpointApply.getProcessStatus())) {
				processStatus = InterfaceBpmNodeEnum.CITY_LEADERS_REVIEW.getNode();
				recordNode = InterfaceBpmNodeEnum.INTERFACE_APPLY.getNode();
			}
			if (InterfaceBpmNodeEnum.CITY_LEADERS_REVIEW.getNode().equals(endpointApply.getProcessStatus())) {
				processStatus = InterfaceBpmNodeEnum.PROVINCIAL_LEADERS_REVIEW.getNode();
				recordNode = InterfaceBpmNodeEnum.CITY_LEADERS_REVIEW.getNode();
			}
			if (InterfaceBpmNodeEnum.PROVINCIAL_LEADERS_REVIEW.getNode().equals(endpointApply.getProcessStatus())) {
				processStatus = InterfaceBpmNodeEnum.FINISH.getNode();
				recordNode = InterfaceBpmNodeEnum.PROVINCIAL_LEADERS_REVIEW.getNode();

			}
			HussarBpmDTO hussarBpmDTO = new HussarBpmDTO();
			hussarBpmDTO.setBusinessKey(endpointApply.getFilingNo());
			hussarBpmDTO.setParticipantType("2");
			hussarBpmDTO.setTaskType("1");
			hussarBpmDTO.setProcessDefinitionKey(HussarBpmTypeEnum.INTERFACE_APPLY.getBpmMark());
			hussarBpmService.hussarSubmit(hussarBpmDTO);
		} else {
			//驳回
			if (InterfaceBpmNodeEnum.CITY_LEADERS_REVIEW.getNode().equals(endpointApply.getProcessStatus())) {
				processStatus = InterfaceBpmNodeEnum.INTERFACE_APPLY.getNode();
				recordNode = InterfaceBpmNodeEnum.CITY_LEADERS_REVIEW.getNode();
			}
			if (InterfaceBpmNodeEnum.PROVINCIAL_LEADERS_REVIEW.getNode().equals(endpointApply.getProcessStatus())) {
				processStatus = InterfaceBpmNodeEnum.CITY_LEADERS_REVIEW.getNode();
				recordNode = InterfaceBpmNodeEnum.PROVINCIAL_LEADERS_REVIEW.getNode();
			}
			HussarBpmDTO hussarBpmDTO = new HussarBpmDTO();
			hussarBpmDTO.setBusinessKey(endpointApply.getFilingNo());
			hussarBpmDTO.setOrderId(String.valueOf(endpointApply.getId()));
			hussarBpmService.prevNodeReject(hussarBpmDTO);
		}
		//更新工单信息
		LambdaUpdateWrapper<EndpointApply> queryWrapper = new LambdaUpdateWrapper<>();
		baseMapper.update(queryWrapper.eq(EndpointApply::getId, endpointApply.getId())
			.set(EndpointApply::getUpdateTime, new Date())
			.set(EndpointApply::getProcessStatus, processStatus)
			.set(EndpointApply::getUpdateUser, user.getUserId()));
		//记录流程节点
		approveRecordService.commonRecord(ApproveRecord.builder()
			.filingNo(orderUpdateStatusDTO.getId())
			.optType(OptTypeEnum.INTERFACE_APPLY.getCode())
			.nodeId(recordNode)
			.optRole(roleName)
			.nodeName(InterfaceBpmNodeEnum.getMessage(recordNode))
			.optTitle(orderUpdateStatusDTO.getComment())
			.optOpinion(orderUpdateStatusDTO.getComment())
			.approveStatus(orderUpdateStatusDTO.getWorkerStatus())
			.filingCode(endpointApply.getFilingNo())
			.build());
		//增加操作记录
		logOptService.commonLogOpt(LogOpt.builder().logId(String.valueOf(endpointApply.getId())).logData(endpointApply.toString()).params(endpointApply.toString())
			.optRole(roleName).optType(OptTypeEnum.INTERFACE_APPLY.getCode()).title(orderUpdateStatusDTO.getComment()).build());
		if (StringUtils.equals(InterfaceBpmNodeEnum.FINISH.getNode(), processStatus)) {
			ApproveRecord approveRecord = ApproveRecord.builder()
				.filingNo(orderUpdateStatusDTO.getId())
				.optType(OptTypeEnum.INTERFACE_APPLY.getCode())
				.nodeId(InterfaceBpmNodeEnum.FINISH.getNode())
				.optRole("--")
				.nodeName(InterfaceBpmNodeEnum.getMessage(InterfaceBpmNodeEnum.FINISH.getNode()))
				.optTitle("自动归档")
				.optOpinion("自动归档")
				.optName("系统")
				.approveStatus(orderUpdateStatusDTO.getWorkerStatus())
				.filingCode(endpointApply.getFilingNo())
				.build();
			approveRecord.setStatus(1);
			approveRecordService.commonRecord(approveRecord);
			//新增数据共享接口权限表

			List<EndpointPortUser> endpointPortUserList = orderUpdateStatusDTO.getEndpointPortUserList();
			String id = endpointApply.getId();
			//接口清单id
			EndpointPortVO portById = baseMapper.getPortById(id);
			EndpointPortUser endpointPortUser = new EndpointPortUser();
			endpointPortUser.setUserId(Long.parseLong(endpointApply.getUserId()));
			endpointPortUser.setVaildTime(endpointApply.getVaildTime());
			endpointPortUser.setPortId(Long.parseLong(portById.getId()));
			String address = baseMapper.getAddress(portById.getId());
			endpointPortUser.setPortAddress(address);
			baseMapper.insertPortUser(endpointPortUser);
//			for (EndpointPortUser endpointPortUser : endpointPortUserList) {
//				endpointPortUser.setUserId(endpointApply.getCreateUser());
//				endpointPortUser.setVaildTime(endpointApply.getVaildTime());
//				baseMapper.insertPortUser(endpointPortUser);
//			}

		}
		return R.success(ResultCode.SUCCESS);
	}

	@Override
	public R<List<User>> getUserList(String name) {
		List<User> list = baseMapper.getUserList(name);
		return R.data(list);
	}

	@Override
	public EndpointPortVO getPortById(EndpointApply detail) {
		return baseMapper.getPortById(detail.getId());
	}

}
