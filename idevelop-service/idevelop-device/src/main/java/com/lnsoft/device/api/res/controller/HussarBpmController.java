package com.lnsoft.device.api.res.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.res.dto.HussarBpmDTO;
import com.lnsoft.device.api.res.service.IHussarBpmService;
import com.lnsoft.hussar.bpm.domain.vo.*;
import com.lnsoft.system.user.vo.BpmUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 轻骑兵工作流引擎
 *
 * @author Idevelop
 * @since 2024-03-08
 */
@RestController
@RequestMapping("/hussar/bpm")
@Api(value = "轻骑兵工作流", tags = "轻骑兵工作流")
public class HussarBpmController {

	@Resource
	private IHussarBpmService hussarBpmService;

	/**
	 * 获取当前流程的taskId
	 */
	@GetMapping("/query/taskId")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取当前流程的taskId", notes = "businessKey 工单编号")
	public R<List<HussarTaskVo>> queryTaskId(String businessKey) throws Exception {
		return R.data(hussarBpmService.queryTaskId(businessKey));
	}

	/**
	 * 获取当前节点参与者
	 */
	@GetMapping("/query/assignee")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "获取当前节点参与者", notes = "businessKey 工单编号")
	public R<List<BpmUserVO>> queryAssignee(String businessKey) throws Exception {
		return R.data(hussarBpmService.queryAssignee(businessKey));
	}

	/**
	 * 获取下一节点信息
	 */
	@GetMapping("/query/next/info")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "获取下一节点信息", notes = "businessKey 工单编号")
	public R<List<HussarNodeVo>> queryNextInfo(HussarBpmDTO hussarBpmDTO) throws Exception {
		return R.data(hussarBpmService.queryNextInfo(hussarBpmDTO));
	}

	/**
	 * 获取下一节点参与者
	 */
	@GetMapping("/query/next/participant")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "获取下一节点参与者", notes = "hussarBpmDTO 请求信息")
	public R<List<HussarParticipantVo>> queryNextParticipant(HussarBpmDTO hussarBpmDTO) throws Exception {
		return R.data(hussarBpmService.queryNextParticipant(hussarBpmDTO));
	}

	/**
	 * 获取下一节点参与者角色-人员
	 */
	@GetMapping("/query/next/participant/all")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "获取下一节点参与者角色-人员", notes = "hussarBpmDTO 请求信息")
	public R<List<HussarAssignVo>> queryNextParticipantAll(HussarBpmDTO hussarBpmDTO) throws Exception {
		return hussarBpmService.queryNextParticipantAll(hussarBpmDTO);
	}

	/**
	 * 提交流程表单
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "发起流程", notes = "请求信息 hussarBpmDTO")
	public R<List<HussarComplateVo>> submit(@RequestBody HussarBpmDTO hussarBpmDTO) throws Exception {
		return hussarBpmService.hussarSubmit(hussarBpmDTO);
	}

	/**
	 * 驳回至上一节点
	 */
	@PostMapping("/reject")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "驳回至上一节点", notes = "请求信息 hussarBpmDTO")
	public R<List<HussarComplateVo>> prevNodeReject(@RequestBody HussarBpmDTO hussarBpmDTO) throws Exception {
		return hussarBpmService.prevNodeReject(hussarBpmDTO);
	}

	/**
	 * 获取任意节点参与者
	 */
	@PostMapping("/query/any/participant")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "获取任意节点参与者", notes = "请求信息 hussarBpmDTO")
	public R<List<HussarAssignVo>> getAnyNodeParticipant(@RequestBody HussarBpmDTO hussarBpmDTO) throws Exception {
		return R.data(hussarBpmService.getAnyNodeParticipant(hussarBpmDTO));
	}

	/**
	 * 获取个人待办列表
	 */
	@GetMapping("/query/todo/list")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "获取个人待办列表", notes = "processKeys 流程标识多个用英文逗号拼接")
	public R<String> getAllTodoList(String processKeys) throws Exception {
		return R.data(hussarBpmService.getAllTodoList(processKeys));
	}

	/**
	 * 获取个人已办列表
	 */
	@GetMapping("/query/finish/list")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "获取个人已办列表", notes = "processKeys 流程标识多个用英文逗号拼接")
	public R<String> getAllDoneListByUnfinished(String processKeys) throws Exception {
		return R.data(hussarBpmService.getAllDoneListByUnfinished(processKeys));
	}

	/**
	 * 获取当前节点参与者角色-人员
	 */
	@GetMapping("/query/task/info")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "获取当前节点参与者角色-人员", notes = "businessKey 工单编号")
	public R<List<HussarAssignVo>> queryTaskInfo(String businessKey) throws Exception {
		return R.data(hussarBpmService.queryTaskInfo(businessKey));
	}

	/**
	 * 自由跳转
	 */
	@GetMapping("/free/jump")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "自由跳转（未测试，暂不使用）", notes = "传入 hussarBpmDTO")
	public R<List<HussarComplateVo>> freeJump(@RequestBody HussarBpmDTO hussarBpmDTO) throws Exception {
		return hussarBpmService.freeJump(hussarBpmDTO);
	}

	/**
	 * 获取当前节点参与者
	 */
	@GetMapping("/query/user/info")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "获取当前节点参与者", notes = "businessKey 工单编号")
	public R<List<BpmUserVO>> queryUserInfo(String businessKey) throws Exception {
		return R.data(hussarBpmService.queryUserInfo(businessKey));
	}
}
