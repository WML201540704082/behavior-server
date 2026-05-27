package com.lnsoft.device.api.asset.dto;

import com.lnsoft.system.user.entity.User;
import lombok.Data;

/**
 * 催办参数接收类
 * @author xyzadmin
 */
@Data
public class DevicePressDoDTO {
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 工单编号
	 */
	private String filingCode;
	/**
	 * 工单类型
	 */
	private String type;
	/**
	 * 催办人
	 */
	private User user;
}
