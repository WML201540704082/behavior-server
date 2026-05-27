package com.lnsoft.device.api.asset.dto;

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
 * @Description: WorkBenchFinishDTO
 */
@Data
@ApiModel(value = "WorkBenchFinishDTO对象", description = "个人工作台：IP资源")
public class WorkBenchFinishDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "当前页")
	private Integer current;

	@ApiModelProperty(value = "每页的数量")
	private Integer size;

	@ApiModelProperty(value = "发起人员")
	private Long createUser;
}
