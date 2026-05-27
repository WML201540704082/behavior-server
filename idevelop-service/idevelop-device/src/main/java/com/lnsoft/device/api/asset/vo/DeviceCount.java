package com.lnsoft.device.api.asset.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xyzadmin
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "DeviceCount", description = "首页台账概览统计返回类")
public class DeviceCount implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 设备总数
	 */
	@ApiModelProperty(value = "设备总数")
	private Integer deviceCount;
	/**
	 * 在运设备数量
	 */
	@ApiModelProperty(value = "在运设备数量")
	private Integer operationCount;
	/**
	 * 退运在库设备数量
	 */
	@ApiModelProperty(value = "退运在库设备数量")
	private Integer returnedCount;
	/**
	 * 资产总数
	 */
	@ApiModelProperty(value = "资产总数")
	private Integer assetCount;
	/**
	 * 已转资数量
	 */
	@ApiModelProperty(value = "已转资数量")
	private Integer transferCount;
	/**
	 * 待转资数量
	 */
	@ApiModelProperty(value = "待转资数量")
	private Integer noTransferCount;

}
