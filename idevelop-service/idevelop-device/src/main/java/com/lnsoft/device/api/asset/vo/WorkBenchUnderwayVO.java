package com.lnsoft.device.api.asset.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: xuel
 * @CreateTime: 2024/7/22 15:38
 * @Description: WorkBenchUnderwayVO
 */
@Data
@ApiModel(value = "WorkBenchUnderwayVO对象", description = "代办任务：我发起的(进行中)")
public class WorkBenchUnderwayVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "工单ID")
	private String id;

	@ApiModelProperty(value = "单号")
	private String businessId;

	@ApiModelProperty(value = "工单类型")
	private String processName;

	@ApiModelProperty(value = "工单类型编号")
	private String processCode;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "发起时间")
	private LocalDateTime sponsorTime;

	@ApiModelProperty(value = "当前流程节点")
	private String nodeCurrent;

	@ApiModelProperty(value = "当前处理人")
	private String currentProcess;

	@ApiModelProperty(value = "当前处理人ID")
	private String currentProcessId;

	@ApiModelProperty(value = "流程状态")
	private String processStatus;

}
