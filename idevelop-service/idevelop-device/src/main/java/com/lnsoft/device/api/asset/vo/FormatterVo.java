package com.lnsoft.device.api.asset.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@ApiModel(value = "DeviceInfoVo", description = "DeviceInfoVo")
@Builder
public class FormatterVo {

	@ApiModelProperty(value = "时间")
	private String name;

	@ApiModelProperty(value = "数量")
	private Integer value;
}
