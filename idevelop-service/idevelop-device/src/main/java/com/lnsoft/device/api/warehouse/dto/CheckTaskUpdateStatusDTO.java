package com.lnsoft.device.api.warehouse.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CheckTaskUpdateStatusDTO extends OrderUpdateStatusDTO{

	private static final long serialVersionUID = 1L;

	/**
	 * 关联的工单编号
	 */
	@ApiModelProperty(value = "关联的工单编号")
	private String filingNo;
	/**
	 * 工单类型
	 */
	@ApiModelProperty(value = "工单类型")
	private String orderType;
}
