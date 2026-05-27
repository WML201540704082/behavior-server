package com.lnsoft.device.api.asset.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2024/7/22 9:15
 * @Description: WorkBenchIpAssignNumberVO
 */

@Data
@ApiModel(value = "WorkBenchIpAssignNumberVO对象", description = "个人工作台：IP资源")
public class WorkBenchIpAssignNumberVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "显示类型")
	private String networkType;

	@ApiModelProperty(value = "是否分配: 0:未分配 1:已分配")
	private String isUsed;

	@ApiModelProperty(value = "数量")
	private Integer number;

}
