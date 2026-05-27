package com.lnsoft.device.api.i6000.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2024/5/14 9:55
 * @Description: 功能位置 I6000FuncDTO
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class I6000FuncDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * I6000唯一ID
	 */
	@ApiModelProperty(value = "I6000唯一ID" )
	private String objId;

	/**
	 * I6000唯一CODE == I6000唯一ID
	 */
	@ApiModelProperty(value = "I6000唯一CODE" )
	private String code;

	/**
	 * 功能位置名称
	 */
	@ApiModelProperty(value = "功能位置名称" )
	@NotNull(message = "功能位置名称不能为空")
	private String name;

	/**
	 * 维护工厂
	 */
	@ApiModelProperty(value = "维护工厂" )
	@NotNull(message = "维护工厂不能为空")
	private String maintenance;

	/**
	 * 工厂区域
	 */
	@ApiModelProperty(value = "工厂区域" )
	@NotNull(message = "工厂区域不能为空")
	private String domain;

	/**
	 * 功能位置编码
	 */
	@ApiModelProperty(value = "功能位置编码" )
	@NotNull(message = "功能位置编码不能为空")
	private String erpCode;

	/**
	 * 上级功能位置编码
	 */
	@ApiModelProperty(value = "上级功能位置编码" )
	@NotNull(message = "上级功能位置编码不能为空")
	private String upflcode;

	/**
	 * 电压等级编码
	 */
	@ApiModelProperty(value = "电压等级编码" )
	@NotNull(message = "电压等级编码不能为空")
	private String voltageClass;

	private String iwerk;
	private String synFlag;
	private String operFlag;
	private String odsFlag;
	private String errorInfo;
	private String corpCode;

	/**
	 * 操作标识 C创建 M修改 D删除
	 */
	@ApiModelProperty(value = "操作标识 C创建 M修改 D删除")
	private String operation;


}
