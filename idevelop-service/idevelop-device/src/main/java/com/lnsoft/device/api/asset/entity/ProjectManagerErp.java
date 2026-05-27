package com.lnsoft.device.api.asset.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2024/3/4 17:00
 * @Description: ProjectManagerErp
 */

@Data
@ApiModel(value = "ProjectManagerErp对象", description = "项目管理")
public class ProjectManagerErp implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("当前页")
	private Integer current;

	@ApiModelProperty("每页的数量")
	private Integer size;

	@ApiModelProperty("ERP资产编码")
	private String erpAssetCode;

	@ApiModelProperty("ERP资产编码使用状态")
	private String erpAssetStatus;

	@ApiModelProperty("ERP同步状态")
	private String erpStatus;

	@ApiModelProperty("i6000同步状态")
	private String i6000Status;

	@ApiModelProperty("设备编码")
	private String deviceCode;

	@ApiModelProperty("WBS项目")
	private String wbsProject;

	@ApiModelProperty("WBS元素")
	private String wbsElement;
}
