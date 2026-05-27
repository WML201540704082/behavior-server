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
 * @CreateTime: 2024/7/23 19:25
 * @Description: 代办任务: 我发起(已完成) WorkBenchCompleteVO
 */

@Data
@ApiModel(value = "WorkBenchUnderwayVO对象", description = "代办任务: 我发起(已完成)")
public class WorkBenchCompleteVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键ID")
	private String id;

	@ApiModelProperty(value = "单号")
	private String businessId;

	@ApiModelProperty(value = "工单类型")
	private String processName;

	@ApiModelProperty(value = "申请业务说明")
	private String explain;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "发起时间")
	private LocalDateTime sponsorTime;

}
