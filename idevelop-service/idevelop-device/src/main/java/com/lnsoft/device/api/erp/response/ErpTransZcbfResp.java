package com.lnsoft.device.api.erp.response;

import lombok.Data;

import java.util.List;

/**
 * @Author: xuel
 * @CreateTime: 2024/3/31 14:21
 * @Description: ErpTransEqunrResp
 */

@Data
public class ErpTransZcbfResp {

	/**
	 * 返回消息号
	 * S：全部成功
	 * E：全部失败
	 * W：部分成功
	 */
	private String code;

	/**
	 * 返回消息
	 */
	private String message;

	/**
	 * 设备行
	 */
	private List<ItemResp> itemResp;

	@Data
	public static class ItemResp {

		/**
		 * 信通设备ID
		 */
		private String xtbm;

		/**
		 * ERP设备编码
		 */
		private String equnr;

		/**
		 * 同步标识 S成功, E失败
		 */
		private String tbbs;

		/**
		 * 消息
		 */
		private String msg;

	}

}
