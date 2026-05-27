/**
 .
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lnsoft.device.api.asset.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * 空间资源管理机柜表导出实体类
 *
 * @author Idevelop
 * @since 2024-02-21
 */
@Data
public class ExportCabinets {

	private static final long serialVersionUID = 1L;

	/**
	 * 机柜名称
	 */
	@ApiModelProperty(value = "机柜名称")
	@ExcelProperty("机柜名称")
	private String cabinetsName;
	/**
	 * 全局名称
	 */
	@ApiModelProperty(value = "全局名称")
	@ExcelProperty("全局名称")
	private String globalName;
	/**
	 * 简称
	 */
	@ApiModelProperty(value = "简称")
	@ExcelProperty("简称")
	private String abbreviation;
	/**
	 * 机柜编号
	 */
	@ApiModelProperty(value = "机柜编号")
	@ExcelProperty("机柜编号")
	private String cabinetsId;
	/**
	 * 所属机房
	 */
	@ApiModelProperty(value = "所属机房")
	@ExcelProperty("所属机房")
	private String belongRoom;
	/**
	 * 机柜类型
	 */
	@ApiModelProperty(value = "机柜类型")
	@ExcelProperty("机柜类型")
	private String cabinetsType;
	/**
	 * 生产厂家
	 */
	@ApiModelProperty(value = "生产厂家")
	@ExcelProperty("生产厂家")
	private String produceFactory;
	/**
	 * 投运日期
	 */
	@ApiModelProperty(value = "投运日期")
	@ExcelProperty("投运日期")
	private String useDate;
	/**
	 * 维护单位
	 */
	@ApiModelProperty(value = "维护单位")
	@ExcelProperty("维护单位")
	private String maintenanceUnitName;
	/**
	 * 容量（U）
	 */
	@ApiModelProperty(value = "容量（U）")
	@ExcelProperty("容量（U）")
	private String capacity;
	/**
	 * 设备型号
	 */
	@ApiModelProperty(value = "设备型号")
	@ExcelProperty("设备型号")
	private String deviceModel;
	/**
	 * 是否正序
	 */
	@ApiModelProperty(value = "是否正序")
	@ExcelProperty("是否正序")
	private String isSort;
	/**
	 * 机柜宽度（mm）
	 */
	@ApiModelProperty(value = "机柜宽度（mm）")
	@ExcelProperty("机柜宽度（mm）")
	private Integer cabinetsWidth;
	/**
	 * 机柜高度（mm）
	 */
	@ApiModelProperty(value = "机柜高度（mm）")
	@ExcelProperty("机柜高度（mm）")
	private Integer cabinetsHeight;
	/**
	 * 维护人
	 */
	@ApiModelProperty(value = "维护人")
	@ExcelProperty("维护人")
	private String maintenanceUser;
	/**
	 * 机柜深度（mm）
	 */
	@ApiModelProperty(value = "机柜深度（mm）")
	@ExcelProperty("机柜深度（mm）")
	private Integer cabinetsDepth;
	/**
	 * 退运日期
	 */
	@ApiModelProperty(value = "退运日期")
	@ExcelProperty("退运日期")
	private String returnDate;


}
