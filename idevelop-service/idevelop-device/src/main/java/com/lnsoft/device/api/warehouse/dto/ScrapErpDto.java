package com.lnsoft.device.api.warehouse.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cwb
 * @date 2024/4/2
 */
@Data
public class ScrapErpDto {

	/**
	 * 工单ID
	 */
	@ApiModelProperty(value = "工单id")
	private String id;

	/**
	 * 工单编号
	 */
	@ApiModelProperty(value = "工单编号")
	private String filingNo;

	/**
	 * 审批状态
	 */
	@ApiModelProperty(value = "erp审批状态")
	private String status;

	/**
	 * 审批内容
	 */
	@ApiModelProperty(value = "审批其他信息JSON")
	private String scrapCode;
}
