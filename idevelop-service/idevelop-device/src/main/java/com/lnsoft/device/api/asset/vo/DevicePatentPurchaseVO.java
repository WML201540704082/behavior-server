package com.lnsoft.device.api.asset.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2024/7/5 12:18
 * @Description: DevicePatentNumber
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "DevicePatentNumber", description = "DevicePatentNumber")
public class DevicePatentPurchaseVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "采购方式")
	private String procureTypeCode;

	@ApiModelProperty(value = "数量")
	private Integer number;

	@ApiModelProperty(value = "占比")
	private Double proportion;
}
