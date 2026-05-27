package com.lnsoft.device.api.asset.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2024/7/5 12:18
 * @Description: DevicePatentOnline
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "DevicePatentOnline", description = "DevicePatentOnline")
public class DevicePatentOnlineVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "地市/部门名称")
	private String name;

	@ApiModelProperty(value = "台式机数量")
	private Integer desktopNumber;

	@ApiModelProperty(value = "笔记本数量")
	private Integer notebookNumber;

	@ApiModelProperty(value = "总数量")
	private Integer number;

	@ApiModelProperty(value = "排序")
	private Integer sort;
}
