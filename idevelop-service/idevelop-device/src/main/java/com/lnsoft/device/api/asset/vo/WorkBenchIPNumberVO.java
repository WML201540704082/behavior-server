package com.lnsoft.device.api.asset.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2024/7/20 19:06
 * @Description: 个人工作台：IP资源 WorkBenchIPNumberVO
 */

@Data
@ApiModel(value = "WorkBenchIPNumberVO对象", description = "个人工作台：IP资源")
public class WorkBenchIPNumberVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "显示类型 内网: intranet 外网: outernet")
	private String type;

	@ApiModelProperty(value = "已分配")
	private Integer assignNumber;

	@ApiModelProperty(value = "未分配")
	private Integer undistributedNumber;

	@ApiModelProperty(value = "IP使用率/使用率")
	private Double usageRate;

}
