package com.lnsoft.device.api.asset.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2024/3/6 11:03
 * @Description: 导出项目 ProjectManagerExport
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectManagerExportDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 项目定义名称
	 */
	@ExcelProperty(value = "项目定义名称")
	private String projectDefine;

	/**
	 * wbs项目编码
	 */
	@ExcelProperty(value = "wbs项目编码")
	private String wbsCode;

	/**
	 * wbs项目
	 */
	@ExcelProperty(value = "wbs项目")
	private String wbsName;

	/**
	 * 所属单位名称
	 */
	@ExcelProperty(value = "所属单位名称")
	private String projectUnitName;
}
