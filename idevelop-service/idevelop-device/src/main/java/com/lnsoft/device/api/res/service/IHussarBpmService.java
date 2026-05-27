package com.lnsoft.device.api.res.service;


import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.res.dto.HussarBpmCreateDTO;
import com.lnsoft.device.api.res.dto.HussarBpmDTO;
import com.lnsoft.hussar.bpm.domain.vo.*;
import com.lnsoft.system.user.vo.BpmUserVO;

import java.util.List;

/**
 * 轻骑兵工作流引擎
 *
 * @author Idevelop
 * @since 2024-03-08
 */
public interface IHussarBpmService {

	/**
	 * 流程创建
	 *
	 * @param hussarBpmCreateDTO 流程创建需要参数
	 * @return HussarCreateVo 返回流程信息
	 */
	HussarCreateVo createHussarBpm(HussarBpmCreateDTO hussarBpmCreateDTO);

	/**
	 * 获取当前流程的taskId
	 *
	 * @param businessKey 关联的业务数据值
	 * @return List
	 * @throws Exception 异常
	 */
	List<HussarTaskVo> queryTaskId(String businessKey) throws Exception;

	/**
	 * 获取当前节点参与者
	 *
	 * @param businessKey 工单编号
	 * @return List
	 * @throws Exception 异常
	 */
	List<BpmUserVO> queryAssignee(String businessKey) throws Exception;

	/**
	 * 获取下一节点信息
	 *
	 * @param hussarBpmDTO 工单信息
	 * @return List
	 * @throws Exception 异常
	 */
	List<HussarNodeVo> queryNextInfo(HussarBpmDTO hussarBpmDTO) throws Exception;

	/**
	 * 获取下一节点参与者
	 *
	 * @param hussarBpmDTO 请求信息
	 * @return List
	 * @throws Exception 异常
	 */
	List<HussarParticipantVo> queryNextParticipant(HussarBpmDTO hussarBpmDTO) throws Exception;

	/**
	 * 获取下一节点参与者角色-人员
	 *
	 * @param hussarBpmDTO 请求信息
	 * @return R
	 * @throws Exception 异常
	 */
	R<List<HussarAssignVo>> queryNextParticipantAll(HussarBpmDTO hussarBpmDTO) throws Exception;

	/**
	 * 提交流程表单
	 *
	 * @param hussarBpmDTO 提交流程表单
	 * @return R
	 * @throws Exception 异常
	 */
	R<List<HussarComplateVo>> hussarSubmit(HussarBpmDTO hussarBpmDTO) throws Exception;

	/**
	 * 驳回至上一节点
	 *
	 * @param hussarBpmDTO 驳回参数
	 * @return R
	 * @throws Exception 异常
	 */
	R<List<HussarComplateVo>> prevNodeReject(HussarBpmDTO hussarBpmDTO) throws Exception;

	/**
	 * 获取任意节点参与者
	 *
	 * @param hussarBpmDTO 请求参数
	 * @return List
	 * @throws Exception 异常
	 */
	List<HussarAssignVo> getAnyNodeParticipant(HussarBpmDTO hussarBpmDTO) throws Exception;

	/**
	 * 获取个人待办列表
	 *
	 * @param processKeys 流程标识多个用英文逗号拼接
	 * @return String
	 * @throws Exception 异常
	 */
	String getAllTodoList(String processKeys) throws Exception;

	/**
	 * 获取个人已办列表
	 *
	 * @param processKeys 流程标识多个用英文逗号拼接
	 * @return String
	 * @throws Exception 异常
	 */
	String getAllDoneListByUnfinished(String processKeys) throws Exception;

	/**
	 * 获取当前节点参与者角色-人员
	 *
	 * @param businessKey 工单编号
	 * @return List
	 * @throws Exception 异常
	 */
	List<HussarAssignVo> queryTaskInfo(String businessKey) throws Exception;

	/**
	 * 自由跳转
	 *
	 * @param hussarBpmDTO 跳转参数
	 * @return R
	 */
	R<List<HussarComplateVo>> freeJump(HussarBpmDTO hussarBpmDTO) throws Exception;

	/**
	 * 获取当前节点参与者
	 *
	 * @param businessKey 工单编号
	 * @return List
	 */
	List<BpmUserVO> queryUserInfo(String businessKey) throws Exception;

	/**
	 * 终结流程
	 *
	 * @param businessKey
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	R endProcess(String businessKey, String userId) throws Exception;
}
