package com.lnsoft.device.api.warehouse.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CheckDeviceCountVO {
	@ApiModelProperty(value = "管理设备数量")
	private Integer manageCount;
	@ApiModelProperty(value = "个人名下数量")
	private Integer individualCount;
}
