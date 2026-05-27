package com.lnsoft.device.api.warehouse.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErpDeviceDetailDTO implements Serializable {

	private static final long serialVersionUID = -9198782424856258768L;
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private String id;
	/**
	 * ERP同步状态
	 */
	@ApiModelProperty(value = "ERP同步状态")
	private String erpStatus;
	/**
	 * ERP资产编号
	 */
	@ApiModelProperty(value = "ERP资产编号")
	private String erpAssetCode;
	/**
	 * ERP资产编码使用状态
	 */
	@ApiModelProperty(value = "ERP资产编码使用状态")
	private String erpAssetStatus;
	/**
	 * ERP台账编号
	 */
	@ApiModelProperty(value = "ERP台账编号")
	private String erpAccountCode;
	/**
	 * 设备资产信息
	 */
	@ApiModelProperty(value = "设备资产信息")
	private Map<String, Object> deviceDetail;
}
