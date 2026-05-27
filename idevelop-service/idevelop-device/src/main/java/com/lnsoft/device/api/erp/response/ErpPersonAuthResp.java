package com.lnsoft.device.api.erp.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: xuel
 * @CreateTime: 2024/3/31 14:21
 * @Description: ErpTransEqunrResp
 */

@Data
public class ErpPersonAuthResp implements Serializable {

	/**
	 * 返回消息号
	 * S：成功
	 * E：失败
	 */
	private String code;

	/**
	 * 返回消息
	 */
	private String message;

	/**
	 * 人员集合
	 */
	private List<ItemResp> itemResp;

	@Data
	public static class ItemResp {

		/**
		 * 用户账号
		 */
		private String bname;

		/**
		 * 用户名称
		 */
		private String nameTextc;

		/**
		 * 所在部门
		 */
		private String department;
	}

}
