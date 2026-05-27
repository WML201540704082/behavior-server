package com.lnsoft.device.api.i6000.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2024/5/19 10:09
 * @Description: I6000OriViewDTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class I6000OriViewDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// 外部视图ID
	@ApiModelProperty(value = "外部视图ID")
	private String oriViewId;

	@ApiModelProperty(value = "外部数据名称，作为筛选条件")
	private String extName;

	// 分页起始页，从1开始
	@ApiModelProperty(value = "分页起始页，从1开始")
	private String pageStart;
	// 分页每页显示条数据，从1开始
	@ApiModelProperty(value = "分页每页显示条数据，从1开始")
	private String pageSize;
}
