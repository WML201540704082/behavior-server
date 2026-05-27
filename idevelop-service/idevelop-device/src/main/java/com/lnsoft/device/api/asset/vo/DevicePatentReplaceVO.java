package com.lnsoft.device.api.asset.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: xuel
 * @CreateTime: 2024/7/5 19:26
 * @Description: DevicePatentReplaceVO
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "DevicePatentReplaceVO", description = "DevicePatentReplaceVO")
public class DevicePatentReplaceVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "名称 设备数量: all, 信创: isItAi, 非信创: notItAi")
	private String name;

	@ApiModelProperty(value = "数量")
	private Integer number;

	@ApiModelProperty(value = "占比")
	private Double proportion;

}
