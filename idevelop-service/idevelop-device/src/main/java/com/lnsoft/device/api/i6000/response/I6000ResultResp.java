package com.lnsoft.device.api.i6000.response;

import lombok.Data;

/**
 * @Author: xuel
 * @CreateTime: 2024/3/31 14:21
 * @Description: ErpTransEqunrResp
 */

@Data
public class I6000ResultResp {

	/**
	 * 执行是否成功
	 */
	private String successful;

	/**
	 * 返回的消息
	 */
	private String resultHint;

	/**
	 * 返回结果数据
	 */
	private String resultValue;

}
