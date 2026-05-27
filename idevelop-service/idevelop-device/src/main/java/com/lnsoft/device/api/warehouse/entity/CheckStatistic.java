package com.lnsoft.device.api.warehouse.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CheckStatistic {
	@ApiModelProperty(value = "盘盈数量")
	private Integer py;
	@ApiModelProperty(value = "盘亏数量")
	private Integer pk;
	@ApiModelProperty(value = "部门名称")
	private String dept;
	@ApiModelProperty(value = "数量")
	private String num;
}
