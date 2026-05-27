package com.lnsoft.device.api.desk.service;

import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.desk.vo.DeskOrderNumVO;
import com.lnsoft.device.api.desk.vo.DictValueVO;

import java.util.List;

public interface IDeskService {

	/**
	 * 获取个人工作台工单数量
	 *
	 * @return DeskOrderNumVO
	 */
	DeskOrderNumVO queryOrderNum() throws Exception;

	/**
	 * 获取工单节点字典
	 *
	 * @param orderType 工单类型
	 * @param orderNo   工单编号
	 * @return List
	 */
	List<DictValueVO> deviceRecordDict(String orderType, String orderNo);

	/**
	 * 短信测试
	 * @param phone
	 * @param msg
	 * @return
	 */
    R send(String phone, String msg);
}
