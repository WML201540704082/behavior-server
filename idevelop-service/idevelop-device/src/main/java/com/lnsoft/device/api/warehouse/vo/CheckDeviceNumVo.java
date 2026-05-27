package com.lnsoft.device.api.warehouse.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author cwb
 * @date 2024/4/19
 */
@Data
public class CheckDeviceNumVo {

	@ApiModelProperty("任务ID")
	private String taskId;
	@ApiModelProperty("任务名称")
	private String taskName;
	@ApiModelProperty("已盘点")
	private Long isCheckNum;
	@ApiModelProperty("未盘点")
	private Long noCheckNum;
	@ApiModelProperty("盘盈总数")
	private Long py;
	@ApiModelProperty("已盘盈")
	private Long pys;
	@ApiModelProperty("盘亏总数")
	private Long pk;
	@ApiModelProperty("已盘亏")
	private Long pks;
	@ApiModelProperty("盘点人员id集合")
	private List<String> receiverIds;
	private String receiverName;
}
