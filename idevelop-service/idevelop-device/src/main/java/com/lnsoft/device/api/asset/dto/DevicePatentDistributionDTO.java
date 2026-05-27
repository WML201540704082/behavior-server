package com.lnsoft.device.api.asset.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2024/7/8 18:56
 * @Description: DevicePatentDistributionDTO
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "DevicePatentDistributionDTO", description = "DevicePatentDistributionDTO")
public class DevicePatentDistributionDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "类型: CORP, DEPT")
	private String type;

}
