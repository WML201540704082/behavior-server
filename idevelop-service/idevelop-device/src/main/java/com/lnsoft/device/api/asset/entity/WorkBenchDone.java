package com.lnsoft.device.api.asset.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: xuel
 * @CreateTime: 2024/7/24 14:35
 * @Description: WorkBenchDone
 */

@Data
@ApiModel(value = "WorkBenchDone对象", description = "WorkBenchDone映射实体")
public class WorkBenchDone implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键ID")
	private String id;

	@ApiModelProperty(value = "单号")
	private String businessId;

	@ApiModelProperty(value = "工单类型")
	private String processName;

	@ApiModelProperty(value = "工单类型")
	private String processCode;

	@ApiModelProperty(value = "申请业务说明")
	private String description;

	@ApiModelProperty(value = "流程实例ID")
	private String processId;

	@ApiModelProperty(value = "流程状态")
	private String processStatus;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "发起时间")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "发起人员")
	private Long createUser;

}
