package com.lnsoft.device.api.asset.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2024/3/4 16:53
 * @Description: ProjectManagerErpVO
 */
@Data
@ApiModel(value = "ProjectManagerErpVO对象", description = "项目管理")
public class ProjectManagerErpVO implements Serializable {

	@ApiModelProperty("ERP台账编码")
	private String erpAccountCode;

	@ApiModelProperty("设备名称")
	private String deviceName;

	@ApiModelProperty("设备类型")
	private String deviceType;

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

}
