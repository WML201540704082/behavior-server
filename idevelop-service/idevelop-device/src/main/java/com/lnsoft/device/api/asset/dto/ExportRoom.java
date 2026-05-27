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

/**
 * 空间资源管理机房表导出实体类
 *
 * @author xyz
 * @since 2024-03-01
 */
@Data
public class ExportRoom  {

    private static final long serialVersionUID = 1L;

    /**
     * 机房名称
     */
    @ApiModelProperty(value = "机房名称")
	@ExcelProperty("机房名称")
    private String roomName;
    /**
     * 机房编号
     */
    @ApiModelProperty(value = "机房编号")
	@ExcelProperty("机房编号")
    private String roomId;
    /**
     * 全局名称
     */
    @ApiModelProperty(value = "全局名称")
	@ExcelProperty("全局名称")
    private String globalName;

    /**
     * 机房位置
     */
    @ApiModelProperty(value = "机房位置")
	@ExcelProperty("机房位置")
    private String roomLocation;
    /**
     * 维护单位
     */
    @ApiModelProperty(value = "维护单位")
	@ExcelProperty("调管单位")
    private String maintenanceUnitName;

    /**
     * 机房类型
     */
    @ApiModelProperty(value = "机房类型")
	@ExcelProperty("机房类型")
    private String roomType;

	/**
	 * 关联I6000机房name
	 */
	@ApiModelProperty(value = "关联I6000机房name")
	@ExcelProperty("关联I6000机房")
	private String i6000Name;

}
