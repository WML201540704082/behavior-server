package com.lnsoft.device.api.i6000.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: xuel
 * @CreateTime: 2024/5/15 11:09
 * @Description: I6000CiCientity
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class I6000CiCientityDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// 待查询的配置项数据属性编码，多个属性时中间以英文逗号分隔。
	private String attrCode;

	// 查询请求条件
	private List<Conditions> conditions;

	// 分页起始页，从1开始
	private String pageStart;
	// 分页每页显示条数据，从1开始
	private String pageSize;


	@Data
	public static class Conditions {

		// 待查询的配置项数据属性编码
		private String attrCode;
		// 查询的条件（例子：in ,=,<>）
		private String operator;
		// 属性值
		private String value;
		private List<OrCondition> orCondition;
	}

	@Data
	public static class OrCondition {

		// 待查询的配置项数据属性编码
		private String attrCode;
		// 查询的条件（例子：in ,=,<>）
		private String operator;
		// 属性值
		private String value;
	}


}
