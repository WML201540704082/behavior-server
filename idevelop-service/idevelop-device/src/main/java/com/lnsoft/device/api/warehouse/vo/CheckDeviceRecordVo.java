package com.lnsoft.device.api.warehouse.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author cwb
 * @date 2024/4/19
 */
@Data
public class CheckDeviceRecordVo {

	@ApiModelProperty("设备编码")
	private String deviceCode;

	@ApiModelProperty("出入库记录")
	private String inOrOutWarehouse;

	@ApiModelProperty("申请记录")
	private String apply;

	@ApiModelProperty("投运记录")
	private String operation;

	@ApiModelProperty("变更记录")
	private String change;

	@ApiModelProperty("报修记录")
	private String repair;

	@ApiModelProperty("巡检记录")
	private String inspection;

	@ApiModelProperty("盘点记录")
	private String check;

	@ApiModelProperty("开机次数")
	private Integer openCount;

	@ApiModelProperty("告警次数")
	private Integer dangerCount;

}
