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
 * @Description: WorkBenchFinishVO
 */
@Data
@ApiModel(value = "WorkBenchIpAssignNumberVO对象", description = "个人工作台：IP资源")
public class WorkBenchFinishVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "工单ID")
	private String id;

	@ApiModelProperty(value = "单号")
	private String businessId;

	@ApiModelProperty(value = "工单类型")
	private String processName;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "处理时间")
	private LocalDateTime processing;

	@ApiModelProperty(value = "处理结果")
	private String processResult;

}
