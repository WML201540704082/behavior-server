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
 * @Description: DevicePatentDistribution
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "DevicePatentDistribution", description = "DevicePatentDistribution")
public class DevicePatentDistributionVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "地市")
	private String unitName;

	@ApiModelProperty(value = "已分发数量")
	private Integer yesDistribution;

	@ApiModelProperty(value = "未分发数量")
	private Integer NoDistribution;
}
