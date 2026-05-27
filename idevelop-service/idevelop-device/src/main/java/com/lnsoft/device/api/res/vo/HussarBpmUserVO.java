package com.lnsoft.device.api.res.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 轻骑兵工作流引擎参与者实体类
 *
 * @author Idevelop
 * @since 2024-03-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HussarBpmUserVO implements Serializable {
	private static final long serialVersionUID = 8402395997105590981L;

	/**
	 * 用户id
	 */
	@ApiModelProperty(name = "用户id")
	private String userId;

	/**
	 * 用户名称
	 */
	@ApiModelProperty(name = "用户名称")
	private String realName;

	/**
	 * 用户code
	 */
	private String regionCode;

	/**
	 * 手机号
	 */
	@ApiModelProperty(name = "手机号")
	private String phone;
}
