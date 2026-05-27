package com.lnsoft.device.api.asset.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2024/5/29 15:40
 * @Description: SelectI6000Info
 */

@Data
public class I6000RequestDTO implements Serializable {
	private static final long serialVersionUID = -6998369498426273054L;

	/**
	 * 设备类型编码
	 */
	@ApiModelProperty(value = "设备类型编码")
	private String deviceTypeCode;

	/**
	 * ERP资产编码
	 */
	@ApiModelProperty(value = "ERP资产编码")
	private String assetCodeErp;

}
