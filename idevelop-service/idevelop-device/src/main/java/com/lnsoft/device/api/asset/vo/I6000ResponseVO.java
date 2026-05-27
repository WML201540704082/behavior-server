package com.lnsoft.device.api.asset.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2024/5/29 15:56
 * @Description: I6000ResponseDTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class I6000ResponseVO implements Serializable {
	private static final long serialVersionUID = 3899062293875690004L;

	@ApiModelProperty(value = "ERP资产编码")
	private String assetCodeErp;

	@ApiModelProperty(value = "ERP设备台账编码")
	private String deviceCodeErp;

	@ApiModelProperty(value = "WBS元素项目显示名称")
	private String wbsElementName;

	@ApiModelProperty(value = "WBS元素项目")
	private String wbsElement;

	@ApiModelProperty(value = "项目编号")
	private String projectCode;

	@ApiModelProperty(value = "项目名称")
	private String projectName;

	@ApiModelProperty(value = "功能位置")
	private String funLocation;

	@ApiModelProperty(value = "功能位置编码")
	private String funLocationCode;

	@ApiModelProperty(value = "维护工厂")
	private String maintenanceFactory;

	@ApiModelProperty(value = "维护工厂编码")
	private String maintenanceFactoryCode;

	@ApiModelProperty(value = "工厂区域")
	private String factoryArea;

	@ApiModelProperty(value = "工厂区域编码")
	private String factoryAreaCode;

	@ApiModelProperty(value = "资产原值")
	private String assetOriginal;

	@ApiModelProperty(value = "使用保管部门(成本中心)")
	private String useKeepDept;

	@ApiModelProperty(value = "使用保管部门(成本中心)名称")
	private String useKeepDeptName;

	@ApiModelProperty(value = "实物保管部门")
	private String realManageDept;

	@ApiModelProperty(value = "实物保管部门名称")
	private String entityManagementDeptName;
}
