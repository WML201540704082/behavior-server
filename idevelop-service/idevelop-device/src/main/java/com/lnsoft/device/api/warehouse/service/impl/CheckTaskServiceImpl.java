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
package com.lnsoft.device.api.warehouse.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lnsoft.common.enums.device.WorkOrderTypeEnum;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.oss.AliossTemplate;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.api.ResultCode;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.BeanUtil;
import com.lnsoft.core.tool.utils.CollectionUtil;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.res.dto.HussarBpmCreateDTO;
import com.lnsoft.device.api.res.dto.HussarBpmDTO;
import com.lnsoft.device.entity.ApproveRecord;
import com.lnsoft.device.entity.LogOpt;
import com.lnsoft.device.api.res.service.IApproveRecordService;
import com.lnsoft.device.api.res.service.IHussarBpmService;
import com.lnsoft.device.api.res.service.ILogOptService;
import com.lnsoft.device.api.warehouse.dto.CheckTaskDTO;
import com.lnsoft.device.api.warehouse.dto.CheckTaskDeviceDTO;
import com.lnsoft.device.api.warehouse.dto.CheckTaskQueryDto;
import com.lnsoft.device.api.warehouse.entity.CheckTask;
import com.lnsoft.device.api.warehouse.entity.CheckTaskDevice;
import com.lnsoft.device.api.warehouse.entity.CheckTaskRemind;
import com.lnsoft.common.enums.hussar.CheckTaskBpmNodeEnum;
import com.lnsoft.common.enums.hussar.CheckTaskEnum;
import com.lnsoft.common.enums.hussar.DeviceReturnedBpmNodeEnum;
import com.lnsoft.common.enums.hussar.HussarBpmTypeEnum;
import com.lnsoft.device.api.warehouse.mapper.CheckTaskDeviceMapper;
import com.lnsoft.device.api.warehouse.mapper.CheckTaskMapper;
import com.lnsoft.device.api.warehouse.service.ICheckTaskDeviceService;
import com.lnsoft.device.api.warehouse.service.ICheckTaskRemindService;
import com.lnsoft.device.api.warehouse.service.ICheckTaskService;
import com.lnsoft.device.api.warehouse.vo.CheckDeviceNumVo;
import com.lnsoft.device.api.warehouse.vo.CheckTaskDeviceVO;
import com.lnsoft.device.api.warehouse.vo.CheckTaskVO;
import com.lnsoft.common.enums.hussar.OptTypeEnum;
import com.lnsoft.device.utils.OrderNumberUtil;
import com.lnsoft.hussar.bpm.domain.dto.HussarBpmDto;
import com.lnsoft.hussar.bpm.domain.vo.HussarAssignVo;
import com.lnsoft.hussar.bpm.domain.vo.HussarComplateVo;
import com.lnsoft.hussar.bpm.domain.vo.HussarCreateVo;
import com.lnsoft.hussar.bpm.domain.vo.HussarTaskVo;
import com.lnsoft.hussar.bpm.feign.IHussarBpmClient;
import com.lnsoft.system.user.entity.UserDTO;
import com.lnsoft.system.user.feign.IUserClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 盘点任务 服务实现类
 *
 * @author Idevelop
 * @since 2024-04-19
 */
@Service
public class CheckTaskServiceImpl extends BaseServiceImpl<CheckTaskMapper, CheckTask> implements ICheckTaskService {

	@Resource
	private CheckTaskDeviceMapper taskDeviceMapper;
	@Resource
	private ICheckTaskDeviceService taskDeviceService;
	@Resource
	private OrderNumberUtil orderNumberUtil;
	@Resource
	private ILogOptService logOptService;
	@Resource
	private IHussarBpmService hussarBpmService;
	@Resource
	private IApproveRecordService approveRecordService;
	@Resource
	private IHussarBpmClient hussarBpmClient;
	@Resource
	private ICheckTaskRemindService checkTaskRemindService;
	@Resource
	private IUserClient userClient;
	@Resource
	private AliossTemplate aliossTemplate;


	@Override
	public IPage<CheckTaskVO> selectCheckTaskPage(IPage<CheckTaskVO> page, CheckTaskVO checkTask) {
		return page.setRecords(baseMapper.selectCheckTaskPage(page, checkTask));
	}

	/**
	 * 盘点任务详情
	 * @param id
	 * @return
	 */
	@Override
	public CheckTaskVO getDetail(String id) {
		CheckTask checkTask = getOne(Wrappers.<CheckTask>lambdaQuery().eq(CheckTask::getId, id).eq(CheckTask::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		if (Objects.isNull(checkTask)) {
			return null;
		}
		CheckTaskVO checkTaskVO = Convert.convert(new TypeReference<CheckTaskVO>() {
		}, checkTask);
		checkTaskVO.setCheckDeptIds(JSONObject.parseArray(checkTask.getCheckDept(), String.class));
		checkTaskVO.setDeviceCategoryIds(JSONObject.parseArray(checkTask.getDeviceCategory(), String.class));
		checkTaskVO.setDeviceTypeIds(JSONObject.parseArray(checkTask.getDeviceType(), String.class));
		checkTaskVO.setReceiverIds(JSONObject.parseArray(checkTask.getReceiver(), String.class));
		checkTaskVO.setLastDevice(JSONObject.parseArray(checkTask.getHistoryCheckType(), String.class));
		List<CheckDeviceNumVo> numVoArrayList = new ArrayList<>();
		CheckDeviceNumVo checkDeviceNumVo = new CheckDeviceNumVo();
		Long checkNumNo = taskDeviceMapper.selectCheckNumNo(id);
		Long checkNumIs = taskDeviceMapper.selectCheckNumIs(id);
		Long checkNumPy = taskDeviceMapper.selectCheckNumPy(id);
		Long checkNumPys = taskDeviceMapper.selectCheckNumPys(id);
		Long checkNumPk = taskDeviceMapper.selectCheckNumPk(id);
		Long checkNumPks = taskDeviceMapper.selectCheckNumPks(id);
		checkDeviceNumVo.setTaskId(id);
		checkDeviceNumVo.setNoCheckNum(checkNumNo);
		checkDeviceNumVo.setIsCheckNum(checkNumIs);
		checkDeviceNumVo.setPy(checkNumPy);
		checkDeviceNumVo.setPys(checkNumPys);
		checkDeviceNumVo.setPk(checkNumPk);
		checkDeviceNumVo.setPks(checkNumPks);
		numVoArrayList.add(checkDeviceNumVo);
//		List<CheckDeviceNumVo> checkNumList = taskDeviceMapper.selectCheckNum(Arrays.asList(checkTaskVO.getId()));
		Map<String, CheckDeviceNumVo> numVoMap = numVoArrayList.stream().collect(Collectors.toMap(CheckDeviceNumVo::getTaskId, Function.identity()));
		CheckDeviceNumVo numVo = numVoMap.get(checkTaskVO.getId());
		if (!Objects.isNull(numVo)) {
			checkTaskVO.setIsCheckNum(numVo.getIsCheckNum());
			checkTaskVO.setNoCheckNum(numVo.getNoCheckNum());
			checkTaskVO.setPyProgress(numVo.getPy());
			checkTaskVO.setPkProgress(numVo.getPk());
			//checkTaskVO.setCheckProgress(numVo.getIsCheckNum() + numVo.getPy() + numVo.getPk() + "/" + numVo.getIsCheckNum() + numVo.getPy() + numVo.getPk() + numVo.getNoCheckNum());
			checkTaskVO.setCheckProgress((numVo.getIsCheckNum() + numVo.getPy() + numVo.getPk()) + "/" + (numVo.getIsCheckNum() + numVo.getPy() + numVo.getPk()+numVo.getNoCheckNum()));
		}
		return checkTaskVO;
	}

	/**
	 * 盘点任务列表
	 * @param page
	 * @param queryDto
	 * @return
	 */
	@Override
	public IPage<CheckTaskVO> getList(IPage<CheckTask> page, CheckTaskQueryDto queryDto) {
		IdevelopUser user = SecureUtil.getUser();
		queryDto.setRegionCode(user.getRegionCode());
		IPage<CheckTask> taskPage = baseMapper.checkTaskList(page, queryDto);
		IPage<CheckTaskVO> result = new Page<>(taskPage.getCurrent(), taskPage.getSize(), taskPage.getTotal());
		if (CollectionUtil.isEmpty(taskPage.getRecords())) {
			return result;
		}
		List<CheckTask> records = taskPage.getRecords();
		List<String> ids = records.stream().map(CheckTask::getId).collect(Collectors.toList());
//		List<CheckDeviceNumVo> checkNumList = taskDeviceMapper.selectCheckNum(ids);
		List<CheckDeviceNumVo> numVoArrayList = new ArrayList<>();
		for (String id : ids) {
			CheckDeviceNumVo checkDeviceNumVo = new CheckDeviceNumVo();
			Long checkNumNo = taskDeviceMapper.selectCheckNumNo(id);
			Long checkNumIs = taskDeviceMapper.selectCheckNumIs(id);
			Long checkNumPy = taskDeviceMapper.selectCheckNumPy(id);
			Long checkNumPys = taskDeviceMapper.selectCheckNumPys(id);
			Long checkNumPk = taskDeviceMapper.selectCheckNumPk(id);
			Long checkNumPks = taskDeviceMapper.selectCheckNumPks(id);
			checkDeviceNumVo.setTaskId(id);
			checkDeviceNumVo.setNoCheckNum(checkNumNo);
			checkDeviceNumVo.setIsCheckNum(checkNumIs);
			checkDeviceNumVo.setPy(checkNumPy);
			checkDeviceNumVo.setPys(checkNumPys);
			checkDeviceNumVo.setPk(checkNumPk);
			checkDeviceNumVo.setPks(checkNumPks);
			numVoArrayList.add(checkDeviceNumVo);
		}
		Map<String, CheckDeviceNumVo> numVoMap = numVoArrayList.stream().collect(Collectors.toMap(CheckDeviceNumVo::getTaskId, Function.identity()));
		List<CheckTaskVO> collect = records.stream().map(item -> {
			CheckTaskVO checkTaskVO = Convert.convert(new TypeReference<CheckTaskVO>() {
			}, item);
			CheckDeviceNumVo numVo = numVoMap.get(item.getId());
			if (!Objects.isNull(numVo)) {
				checkTaskVO.setIsCheckNum(numVo.getIsCheckNum());
				checkTaskVO.setNoCheckNum(numVo.getNoCheckNum());
				checkTaskVO.setPyProgress(numVo.getPy());
				checkTaskVO.setPkProgress(numVo.getPk());
				if (numVo.getPy() != 0) {
					checkTaskVO.setWpy(numVo.getPy() - numVo.getPys());
				}
				if (numVo.getPk() != 0) {
					checkTaskVO.setWpk(numVo.getPk() - numVo.getPks());
				}
				//checkTaskVO.setCheckProgress((numVo.getIsCheckNum() + numVo.getPy() + numVo.getPk()) + "/" + (numVo.getIsCheckNum() + numVo.getPy() + numVo.getPk() + numVo.getNoCheckNum()));
				checkTaskVO.setCheckProgress((numVo.getIsCheckNum() + numVo.getPy() + numVo.getPk()) + "/" + (numVo.getIsCheckNum() + numVo.getPy() + numVo.getPk()+numVo.getNoCheckNum()));
			}
			return checkTaskVO;
		}).collect(Collectors.toList());
		result.setRecords(collect);
		return result;
	}

	/**
	 * 暂存盘点任务
	 * @param dto
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<CheckTaskVO> saveTask(CheckTaskDTO dto) {
		//校验
		IdevelopUser user = SecureUtil.getUser();
		if (StringUtil.isBlank(dto.getTaskName()) || Objects.isNull(dto.getTaskStartTime()) || CollectionUtil.isEmpty(dto.getReceiverIds())) {
			return R.fail("缺失必填项信息！");
		}
		CheckTask checkTask = Convert.convert(CheckTask.class, dto);
		checkTask.setRegionCode(user.getRegionCode());
		checkTask.setProcessStatus("1");
		CheckTaskVO vo;
		if (StringUtil.isBlank(dto.getId())) {
			//新增
			checkTask.setFilingNo(orderNumberUtil.generateNumber(WorkOrderTypeEnum.PD));
			checkTask.setReceiver(JSONObject.toJSONString(dto.getReceiverIds()));
			checkTask.setCheckDept(JSONObject.toJSONString(dto.getCheckDeptIds()));
			checkTask.setDeviceCategory(JSONObject.toJSONString(dto.getDeviceCategoryIds()));
			checkTask.setDeviceType(JSONObject.toJSONString(dto.getDeviceTypeIds()));
			checkTask.setHistoryCheckType(JSONObject.toJSONString(dto.getLastDevice()));
			checkTask.setStatus(CheckTaskEnum.TEMPORARILY.getCode());
			checkTask.setCreateUser(user.getUserId());
			checkTask.setCreateDept(Long.valueOf(user.getDeptId()));
			checkTask.setCreateTime(new Date());
			checkTask.setIsExpire("0");
			checkTask.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
			baseMapper.insert(checkTask);
			//添加操作记录
			logOptService.commonLogOpt(LogOpt.builder().logId(checkTask.getId()).logData(dto.toString()).params(dto.toString())
				.optRole("--").optType(OptTypeEnum.CHECK_TASK.getCode()).title("新增暂存盘点任务").build());
			BeanUtil.copyProperties(checkTask, dto);
		} else {
			//修改
			CheckTask task = getOne(Wrappers.<CheckTask>lambdaQuery().eq(CheckTask::getId, dto.getId()).eq(CheckTask::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
			if (Objects.isNull(task) || !CheckTaskEnum.TEMPORARILY.getCode().equals(task.getStatus())) {
				return R.fail("当前盘点任务无法进行暂存");
			}
			checkTask.setUpdateUser(user.getUserId());
			checkTask.setUpdateTime(new Date());
			baseMapper.updateById(checkTask);
			logOptService.commonLogOpt(LogOpt.builder().logId(checkTask.getId()).logData(dto.toString()).params(dto.toString())
				.optRole("--").optType(OptTypeEnum.CHECK_TASK.getCode()).title("修改盘点任务").build());
		}
		vo = saveTaskDevice(dto, user);
		return R.data(vo);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<Integer> removeTask(String ids) {
		IdevelopUser user = SecureUtil.getUser();
		List<String> idList = Arrays.asList(ids.split(","));
		List<CheckTask> checkTaskList = list(Wrappers.<CheckTask>lambdaQuery().eq(CheckTask::getStatus, CheckTaskEnum.TEMPORARILY.getCode())
			.eq(CheckTask::getIsDeleted, IdevelopConstant.DB_NOT_DELETED).in(CheckTask::getId, idList));

		if (checkTaskList.size() != idList.size()) {
			return R.fail("所选盘点任务无法删除!");
		}
		baseMapper.update(Wrappers.<CheckTask>lambdaUpdate().set(CheckTask::getIsDeleted, IdevelopConstant.DB_IS_DELETED).set(CheckTask::getUpdateUser, user.getUserId())
			.set(CheckTask::getUpdateTime, new Date()).eq(CheckTask::getIsDeleted, IdevelopConstant.DB_NOT_DELETED).in(CheckTask::getId, idList));
		for (String id : idList) {
			taskDeviceMapper.removeById(id);
		}
		// 增加操作记录
		idList.forEach(item -> logOptService.commonLogOpt(LogOpt.builder().logId(item).logData(ids).params(ids).optType(OptTypeEnum.CHECK_TASK.getCode()).title("删除暂存盘点任务").build()));
		return R.success(ResultCode.SUCCESS);
	}

	/**
	 * 发起盘点任务流程
	 * @param dto
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<CheckTaskVO> submitCheck(CheckTaskDTO dto) throws Exception {
		IdevelopUser user = SecureUtil.getUser();
		if (CollectionUtil.isEmpty(dto.getCheckTaskDeviceDTOS())) {
			return R.fail("请选择盘点设备！");
		}
		R<CheckTaskVO> saveResult = saveTask(dto);
		if (saveResult.getCode() != 200) {
			return saveResult;
		}
		CheckTaskVO vo = saveResult.getData();
		//处理数据
		// 组装发起流程需要的参数
		Map<String, Object> variable = new HashMap<>();
		variable.put("orderId", vo.getId());
		variable.put("orderNo", vo.getFilingNo());
		variable.put("userId", user.getUserId());
		variable.put("userName", user.getUserName());
		variable.put("regionCode", user.getRegionCode());
		HussarBpmCreateDTO hussarBpmCreateDTO = HussarBpmCreateDTO.builder().processDefinitionKey(HussarBpmTypeEnum.DEVICE_CHANGE.getBpmMark())
			.businessKey(vo.getFilingNo()).variable(variable).build();
		HussarCreateVo hussarBpm;
		// 发起流程
		try {
			hussarBpm = hussarBpmService.createHussarBpm(hussarBpmCreateDTO);
			//  获取当前节点操作角色
			List<HussarAssignVo> hussarAssignVoList = hussarBpmService.queryTaskInfo(vo.getFilingNo());
		} catch (Exception e) {
			throw new Exception("创建流程发生异常");
		}
		dto.setTaskDefinitionKey(CheckTaskBpmNodeEnum.CHECK_TASK_APPLY.getNode());
		dto.setWorkerStatus(0);
		dto.setComment("发起盘点任务申请");
		dto.setFilingNo(vo.getFilingNo());
		deskCheckTaskStatus(dto);
		//新增提醒
		List<CheckTaskDeviceDTO> checkTaskDeviceDTOS = dto.getCheckTaskDeviceDTOS();
		ArrayList<CheckTaskRemind> checkTaskReminds = new ArrayList<>();
		if (ObjectUtil.isNotEmpty(checkTaskDeviceDTOS)) {
			checkTaskDeviceDTOS.forEach(item -> {
				CheckTaskRemind checkTaskRemind = new CheckTaskRemind();
				checkTaskRemind.setTaskName(dto.getTaskName());
				checkTaskRemind.setTaskStartTime(dto.getTaskStartTime());
				checkTaskRemind.setTaskEndTime(dto.getTaskEndTime());
				checkTaskRemind.setUser(item.getReceivingPerson());
				checkTaskRemind.setStatus(0);
				R<List<UserDTO>> userByIdCard = userClient.getUserByIdCard(item.getReceivingPerson(), item.getReceiveDeptCode(), item.getReceivingIDCard());
				if (ObjectUtil.isNotEmpty(userByIdCard.getData())) {
					Long userId = userByIdCard.getData().get(0).getId();
					checkTaskRemind.setUserId(userId);
				}
				checkTaskReminds.add(checkTaskRemind);
			});
		}
		checkTaskRemindService.saveBatch(checkTaskReminds);
		//工单同步流程信息
		baseMapper.update(Wrappers.<CheckTask>lambdaUpdate().set(CheckTask::getProcessInsId, hussarBpm.getProcessInsId()).set(CheckTask::getReceiverTime, new Date()).set(CheckTask::getLaunchUnit, user.getCorpId())
			.set(CheckTask::getLaunchUnitName, user.getCorpName()).set(CheckTask::getProcessStatus, CheckTaskBpmNodeEnum.CHECK_TASK_REVIEW.getNode()).eq(CheckTask::getId, vo.getId()));
		return R.data(vo);
	}

	/**
	 * 更新盘点任务工单状态
	 * @param dto
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<Integer> deskCheckTaskStatus(CheckTaskDTO dto) throws Exception {
		IdevelopUser user = SecureUtil.getUser();
		CheckTask checkTask = getOne(Wrappers.<CheckTask>lambdaQuery().eq(CheckTask::getId, dto.getId()).eq(CheckTask::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		String taskNode = null;
		String optTitle = null;
		if (StringUtil.isBlank(dto.getTaskDefinitionKey())) {
			dto.setTaskDefinitionKey(checkTask.getProcessStatus());
		}
		dto.setProcessStatus(dto.getTaskDefinitionKey());
		if (CheckTaskBpmNodeEnum.CHECK_TASK_APPLY.getNode().equals(dto.getTaskDefinitionKey())) {
			taskNode = CheckTaskBpmNodeEnum.CHECK_TASK_REVIEW.getNode();
			optTitle = "发起盘点任务";
		}
		if (CheckTaskBpmNodeEnum.CHECK_TASK_REVIEW.getNode().equals(dto.getTaskDefinitionKey())) {
			if (dto.getWorkerStatus() == 1) {
				taskNode = CheckTaskBpmNodeEnum.CHECK_TASK_APPLY.getNode();
				dto.setComment("发起盘点任务");
				optTitle = "发起盘点任务";
			} else {
				taskNode = CheckTaskBpmNodeEnum.CHECK_TASK_FINISH.getNode();
				optTitle = "部门主任审批通过";
			}
		}
		Map<String, Object> variable = new HashMap<>();
		variable.put("orderId", checkTask.getId());
		variable.put("orderNo", checkTask.getFilingNo());
		variable.put("userId", user.getUserId());
		variable.put("userName", user.getUserName());
		variable.put("regionCode", user.getRegionCode());
		// 记录审核流程
		if (StringUtil.isBlank(dto.getExamineRole())) {
			//  获取当前节点操作角色
			List<HussarAssignVo> hussarAssignVoList = hussarBpmService.queryTaskInfo(checkTask.getFilingNo());
			String roleName = hussarAssignVoList.stream().map(HussarAssignVo::getRoleName).collect(Collectors.joining(","));
			dto.setExamineRole(roleName);
		}
		// 增加操作记录
		Date date = new Date();
		logOptService.commonLogOpt(LogOpt.builder().logId(dto.getId()).logData(dto.toString()).params(dto.toString())
			.optType(OptTypeEnum.CHECK_TASK.getCode()).title(optTitle).optRole(dto.getExamineRole()).time(date).build());
		approveRecordService.commonRecord(ApproveRecord.builder().filingNo(dto.getId()).optType(OptTypeEnum.CHECK_TASK.getCode()).nodeId(dto.getTaskDefinitionKey())
			.optRole(dto.getExamineRole())
			.filingCode(dto.getFilingNo()).nodeName(CheckTaskBpmNodeEnum.getMessage(dto.getTaskDefinitionKey())).optTitle(optTitle).optOpinion(dto.getComment()).build());
		//更新工单
		baseMapper.update(Wrappers.<CheckTask>lambdaUpdate().set(CheckTask::getUpdateTime, date).set(CheckTask::getUpdateUser, user.getUserId()).set(CheckTask::getProcessStatus, taskNode)
			.set(CheckTask::getStatus, CheckTaskEnum.TEMPORARILY.getCode()).eq(CheckTask::getId, dto.getId()));
		if (CheckTaskBpmNodeEnum.CHECK_TASK_REVIEW.getNode().equals(dto.getTaskDefinitionKey()) && dto.getWorkerStatus() == 0) {
			// 增加操作记录
			LogOpt build = LogOpt.builder().logId(dto.getId()).logData(dto.toString()).params(dto.toString()).optRole("--")
				.optType(OptTypeEnum.CHECK_TASK.getCode()).title(CheckTaskBpmNodeEnum.getMessage(CheckTaskBpmNodeEnum.CHECK_TASK_FINISH.getNode())).optName("系统").build();
			build.setStatus(0);
			logOptService.commonLogOpt(build);
			// 记录审核流程
			ApproveRecord approveRecord = ApproveRecord.builder().filingNo(dto.getId())
				.optType(OptTypeEnum.CHECK_TASK.getCode()).nodeId(CheckTaskBpmNodeEnum.CHECK_TASK_FINISH.getNode())
				.nodeName(CheckTaskBpmNodeEnum.getMessage(CheckTaskBpmNodeEnum.CHECK_TASK_FINISH.getNode()))
				.optTitle(CheckTaskBpmNodeEnum.getMessage(CheckTaskBpmNodeEnum.CHECK_TASK_FINISH.getNode()))
				.optOpinion(CheckTaskBpmNodeEnum.getMessage(CheckTaskBpmNodeEnum.CHECK_TASK_FINISH.getNode()))
				.filingCode(dto.getFilingNo())
				.optName("系统").optRole("--").build();
			approveRecord.setStatus(1);
			approveRecordService.commonRecord(approveRecord);
			baseMapper.update(Wrappers.<CheckTask>lambdaUpdate().set(CheckTask::getUpdateTime, date).set(CheckTask::getUpdateUser, user.getUserId()).set(CheckTask::getProcessStatus, taskNode)
				.set(CheckTask::getStatus, CheckTaskEnum.APPROVAL.getCode()).eq(CheckTask::getId, dto.getId()));
		}
		if (dto.getWorkerStatus() == 1) {
			//驳回
			try {
				List<HussarTaskVo> hussarTaskVos = hussarBpmService.queryTaskId(checkTask.getFilingNo());
				HussarBpmDto hussarBpmDto = new HussarBpmDto();
				hussarBpmDto.setTaskId(hussarTaskVos.get(0).getTaskId());
				hussarBpmDto.setUserId(user.getUserId().toString());
				hussarBpmDto.setComment(StringUtil.isNotBlank(dto.getComment()) ? dto.getComment() : "驳回");
				hussarBpmDto.setProcessDefinitionKey(HussarBpmTypeEnum.DEVICE_RETURNED.getBpmMark());
				hussarBpmDto.setRejectNode(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_APPLY.getNode());
				hussarBpmDto.setVariable(variable);
				hussarBpmDto.setBusinessKey(checkTask.getFilingNo());
				com.lnsoft.hussar.bpm.tool.api.R<JSONArray> hussarR = hussarBpmClient.anyNodeReject(hussarBpmDto);
				if (hussarR.getCode() != ResultCode.SUCCESS.getCode()) {
					throw new Exception("驳回流程异常!");
				}
			} catch (Exception e) {
				throw new Exception("驳回流程异常!");
			}
		} else {
			//同意
			HussarBpmDTO hussarBpmDTO = new HussarBpmDTO();
			hussarBpmDTO.setBusinessKey(checkTask.getFilingNo());
			hussarBpmDTO.setComment(dto.getComment());
			hussarBpmDTO.setVariable(variable);
			hussarBpmDTO.setParticipantType("2");
			hussarBpmDTO.setProcessDefinitionKey(HussarBpmTypeEnum.DEVICE_RETURNED.getBpmMark());
			hussarBpmDTO.setTaskDefinitionKey(dto.getTaskDefinitionKey());
			hussarBpmDTO.setTaskType("1");
			try {
				R<List<HussarComplateVo>> hussarR = hussarBpmService.hussarSubmit(hussarBpmDTO);
				if (hussarR.getCode() != ResultCode.SUCCESS.getCode()) {
					throw new Exception("提交流程异常!");
				}
			} catch (Exception e) {
				throw new Exception("提交流程异常!");
			}
		}
		return R.success(ResultCode.SUCCESS);
	}

	@Override
	public IPage<CheckTaskVO> deskCheckTaskList(CheckTaskDTO dto, Query query) {
		if (StringUtil.isBlank(dto.getOrderNoList())) {
			return new Page<>();
		}
		IdevelopUser user = SecureUtil.getUser();
		dto.setRegionCode(user.getRegionCode());
		dto.setFilingNoList(Arrays.asList(dto.getOrderNoList().split(",")));
		IPage<CheckTaskVO> page = baseMapper.deskCheckTaskList(dto, Condition.getPage(query));
		page.setRecords(page.getRecords().stream().map(item -> {
			if (StringUtil.isNotBlank(item.getHistoryTask())) {
				item.setIsHistory("是");
			} else {
				item.setIsHistory("否");
			}
			return item;
		}).collect(Collectors.toList()));
		return page;
	}

	@Override
	public List<CheckDeviceNumVo> historyTask() {
		List<CheckTask> list = list(Wrappers.<CheckTask>lambdaQuery().eq(CheckTask::getStatus, "3").eq(CheckTask::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		List<CheckDeviceNumVo> result = new ArrayList<>();
		if (CollectionUtil.isNotEmpty(list)) {
			list.stream().forEach(item -> {
				CheckDeviceNumVo vo = new CheckDeviceNumVo();
				vo.setTaskId(item.getId());
				vo.setTaskName(item.getTaskName());
				vo.setReceiverIds(JSONObject.parseArray(item.getReceiver(), String.class));
				vo.setReceiverName(item.getReceiverName());
				result.add(vo);
			});
		}
		return result;
	}

	@Override
	public R<List<CheckTask>> getCheckListByUser() {
		IdevelopUser user = SecureUtil.getUser();
		List<CheckTask> result = baseMapper.getCheckListByUser(user.getCorpId(), user.getDeptId(), user.getUserId().toString());
		return R.data(result);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void checkTaskEnd(String format) {
		List<CheckTask> list = list(Wrappers.<CheckTask>lambdaQuery()
			.eq(CheckTask::getStatus, "2")
			.eq(CheckTask::getTaskEndTime, format + " 00:00:00")
			.eq(CheckTask::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		if (CollectionUtil.isNotEmpty(list)) {
			List<String> ids = list.stream().map(CheckTask::getId).collect(Collectors.toList());
			baseMapper.update(Wrappers.<CheckTask>lambdaUpdate().set(CheckTask::getStatus, "4").set(CheckTask::getUpdateUser, 1).set(CheckTask::getUpdateTime, new Date()).in(CheckTask::getId, ids));
			taskDeviceMapper.update(Wrappers.<CheckTaskDevice>lambdaUpdate().set(CheckTaskDevice::getCheckStatus, "3").set(CheckTaskDevice::getUpdateUser, 1).set(CheckTaskDevice::getUpdateTime, new Date())
				.eq(CheckTaskDevice::getCheckStatus, "1").eq(CheckTaskDevice::getIsDeleted, IdevelopConstant.DB_NOT_DELETED).in(CheckTaskDevice::getTaskId, ids));
		}
	}

	@Override
	public R<String> online() {
//		List<CheckTask> list = this.list();
//		List<CheckTaskExport> checkTaskExports = new ArrayList<>();
//		for (CheckTask checkTask : list) {
//			CheckTaskExport checkTaskExport = new CheckTaskExport();
//			BeanUtils.copyProperties(checkTask,checkTaskExport);
//			checkTaskExports.add(checkTaskExport);
//		}
//		for (CheckTaskExport checkTaskExport : checkTaskExports) {
//			String id = checkTaskExport.getId();
//			Long checkNumNo = taskDeviceMapper.selectCheckNumNo(id);
//			Long checkNumIs = taskDeviceMapper.selectCheckNumIs(id);
//			Long checkNumPy = taskDeviceMapper.selectCheckNumPy(id);
//			Long checkNumPk = taskDeviceMapper.selectCheckNumPk(id);
//			checkTaskExport.setCheckProgress((checkNumIs + checkNumPy + checkNumPk) + "/" + (checkNumIs + checkNumPy + checkNumPk+ checkNumNo));
//			checkTaskExport.setIsCheckNum(checkNumIs);
//			checkTaskExport.setNoCheckNum(checkNumNo);
//			checkTaskExport.setPk(checkNumPk);
//			checkTaskExport.setPy(checkNumPy);
//			Long returned = taskDeviceMapper.selectCheckReturned(id);
//			Long checklstw = taskDeviceMapper.selectChecklstw(id);
//			checkTaskExport.setReturnedDevice(returned);
//			checkTaskExport.setLstwDevice(checklstw);
//		}
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		EasyExcel.write(outputStream, CheckTaskExport.class).sheet("列表").doWrite(checkTaskExports);
//		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
//		IdevelopFile idevelopFile = aliossTemplate.putFile("在线预览", inputStream);
		return R.data("http://xt-public-storage.oss-cn-jinan-sddlyf-d01-a.ops-devcloud.sd.sgcc.com.cn/upload%2F20251105%2Fresponse.pdf");
		//http://xt-public-storage.oss-cn-jinan-sddlyf-d01-a.ops-devcloud.sd.sgcc.com.cn/upload%2F20251105%2Fresponse.pdf?Expires=1762326156&OSSAccessKeyId=JO7TEcdHP1uJTZXQ&Signature=xzm9QQo6VsUstEn2elhaftABwoE%3D
	}



	/**
	 * 保存盘点任务设备详情
	 * @param dto
	 * @param user
	 * @return
	 */
	private CheckTaskVO saveTaskDevice(CheckTaskDTO dto, IdevelopUser user) {
		CheckTaskVO vo = Convert.convert(CheckTaskVO.class, dto);
		List<CheckTaskDeviceDTO> deviceDTOS = dto.getCheckTaskDeviceDTOS();
		//解除原绑定设备信息
		taskDeviceMapper.update(Wrappers.<CheckTaskDevice>lambdaUpdate().set(CheckTaskDevice::getIsDeleted, IdevelopConstant.DB_IS_DELETED).eq(CheckTaskDevice::getTaskId, dto.getId()));
		if (CollectionUtil.isNotEmpty(deviceDTOS)) {
			List<CheckTaskDevice> taskDeviceList = deviceDTOS.stream().map(item -> {
				CheckTaskDevice taskDevice = Convert.convert(CheckTaskDevice.class, item);
				taskDevice.setId(null);
				taskDevice.setTaskId(dto.getId());
				taskDevice.setCreateUser(user.getUserId());
				taskDevice.setCreateDept(Long.valueOf(user.getDeptId()));
				taskDevice.setCreateTime(new Date());
				taskDevice.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
				taskDevice.setCheckStatus("1");
				taskDevice.setDisposeResult("0");
				taskDevice.setDisposeStatus("0");
				taskDevice.setRegionCode(user.getRegionCode());
				if (CollectionUtil.isNotEmpty(item.getEntity())) {
					taskDevice.setCmdbDevice(JSONObject.toJSONString(item.getEntity()));
				}
				return taskDevice;
			}).collect(Collectors.toList());
			taskDeviceService.saveBatch(taskDeviceList);
			vo.setTaskDeviceVOS(Convert.convert(new TypeReference<List<CheckTaskDeviceVO>>() {
			}, deviceDTOS));
		}
		return vo;
	}

}
