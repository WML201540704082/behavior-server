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
 * 空间资源管理机架表导出实体类
 *
 * @author xyz
 * @since 2024-02-21
 */
@Data
public class ExportRacks  {

    private static final long serialVersionUID = 1L;

    /**
     * 机架名称
     */
    @ApiModelProperty(value = "机架名称")
	@ExcelProperty("机架名称")
    private String racksName;
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
     * 所属机柜
     */
    @ApiModelProperty(value = "所属机柜")
	@ExcelProperty("所属机柜")
	private String belongRacks;
    /**
     * 投运日期
     */
    @ApiModelProperty(value = "投运日期")
	@ExcelProperty("投运日期")
    private String useDate;
	/**
	 * 生产厂家
	 */
	@ApiModelProperty(value = "生产厂家")
	@ExcelProperty("生产厂家")
	private String produceFactory;
    /**
     * 机架序号
     */
    @ApiModelProperty(value = "机架序号")
	@ExcelProperty("机架序号")
    private String racksNum;
	/**
	 * 机架占用
	 */
	@ApiModelProperty(value = "机架占用")
	@ExcelProperty("机架占用")
	private String racksOccupancy;
	/**
	 * 退运日期
	 */
	@ApiModelProperty(value = "退运日期")
	@ExcelProperty("退运日期")
	private String returnDate;
	/**
	 * 运行状态
	 */
	@ApiModelProperty(value = "运行状态")
	@ExcelProperty("运行状态")
	private String runningStatus;
	/**
	 * 机架位置
	 */
	@ApiModelProperty(value = "机架位置")
	@ExcelProperty("机架位置")
	private String racksLocation;
	/**
	 * 维护单位
	 */
	@ApiModelProperty(value = "维护单位")
	@ExcelProperty("维护单位")
	private String maintenanceUnitName;
    /**
     * 维护人
     */
    @ApiModelProperty(value = "维护人")
	@ExcelProperty("维护人")
    private String maintenanceUser;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	@ExcelProperty("备注")
	private String remark;


}
