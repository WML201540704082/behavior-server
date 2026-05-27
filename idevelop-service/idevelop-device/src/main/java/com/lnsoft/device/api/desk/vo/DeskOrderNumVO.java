package com.lnsoft.device.api.desk.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class DeskOrderNumVO implements Serializable {
	private static final long serialVersionUID = -7451178818025121636L;

	/**
	 * 设备建档工单数量
	 */
	@ApiModelProperty(value = "设备建档工单数量")
	private Integer deviceRecordNum;

	/**
	 * 设备申请工单数量
	 */
	@ApiModelProperty(value = "设备申请工单数量")
	private Integer deviceApplyNum;

	/**
	 * 设备出库工单数量
	 */
	@ApiModelProperty(value = "设备出库工单数量")
	private Integer deviceOutboundNum;

	/**
	 * 设备投运工单数量
	 */
	@ApiModelProperty(value = "设备投运工单数量")
	private Integer deviceOperationNum;

	/**
	 * 设备转资工单数量
	 */
	@ApiModelProperty(value = "设备转资工单数量")
	private Integer deviceTransferNum;

	/**
	 * 设备变更工单数量
	 */
	@ApiModelProperty(value = "设备变更工单数量")
	private Integer deviceChangeNum;
	/**
	 * 设备报修工单数量
	 */
	@ApiModelProperty(value = "设备报修工单数量")
	private Integer deviceRepairNum;
	/**
	 * 设备报修工单数量
	 */
	@ApiModelProperty(value = "设备退运工单数量")
	private Integer deviceReturnNum;
	/**
	 * 设备报修工单数量
	 */
	@ApiModelProperty(value = "设备报废工单数量")
	private Integer deviceScrapNum;
	/**
	 * 盘点任务数量
	 */
	@ApiModelProperty(value = "设备报废工单数量")
	private Integer checkTaskNum;

	/**
	 * 自评工单数量
	 */
	@ApiModelProperty(value = "自评工单数量")
	private Integer deviceEvaluationNum;

	/**
	 * 评审工单数量
	 */
	@ApiModelProperty(value = "评审工单数量")
	private Integer deviceLibraryNum;

	public DeskOrderNumVO() {
		this.deviceRecordNum = 0;
		this.deviceApplyNum = 0;
		this.deviceOutboundNum = 0;
		this.deviceOperationNum = 0;
		this.deviceTransferNum = 0;
		this.deviceRepairNum = 0;
		this.deviceReturnNum = 0;
		this.deviceScrapNum = 0;
		this.deviceChangeNum = 0;
		this.checkTaskNum = 0;
		this.deviceEvaluationNum = 0;
	}
}
