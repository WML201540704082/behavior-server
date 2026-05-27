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
package com.lnsoft.device.api.asset.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 空间资源管理机房表实体类
 *
 * @author Idevelop
 * @since 2024-02-21
 */
@Data
@TableName("idevelop_resource_room")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ResourceRoom对象", description = "空间资源管理机房表")
public class ResourceRoom extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "uuid", type = IdType.ASSIGN_UUID)
	@ExcelIgnore
  	private String uuid;
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
     * 简称
     */
    @ApiModelProperty(value = "简称")
	@ExcelIgnore
    private String abbreviation;
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
	private String maintenanceUnit;
    /**
     * 维护单位名称
     */
    @ApiModelProperty(value = "维护单位")
	@ExcelProperty("调管单位")
    private String maintenanceUnitName;
    /**
     * 机房面积（m²）
     */
    @ApiModelProperty(value = "机房面积（m²）")
	@ExcelIgnore
    private Double roomArea;
    /**
     * 机房高度（m）
     */
    @ApiModelProperty(value = "机房高度（m）")
	@ExcelIgnore
    private Double roomHeight;
    /**
     * 机房宽度（m）
     */
    @ApiModelProperty(value = "机房宽度（m）")
	@ExcelIgnore
    private Double roomWidth;
    /**
     * 机房类型
     */
    @ApiModelProperty(value = "机房类型")
	@ExcelProperty("机房类型")
    private String roomType;
    /**
     * 机房功能
     */
    @ApiModelProperty(value = "机房功能")
	@ExcelProperty("机房功能")
    private String roomFunction;
    /**
     * 是否监控
     */
    @ApiModelProperty(value = "是否监控")
	@ExcelIgnore
    private String isMonitor;
    /**
     * 房间承重
     */
    @ApiModelProperty(value = "房间承重")
	@ExcelIgnore
    private String roomBearing;
    /**
     * 房间进深（m）
     */
    @ApiModelProperty(value = "房间进深（m）")
	@ExcelIgnore
    private Double roomDepth;
    /**
     * 取暖方式
     */
    @ApiModelProperty(value = "取暖方式")
	@ExcelIgnore
    private String heatingMethod;
    /**
     * 空调方式
     */
    @ApiModelProperty(value = "空调方式")
	@ExcelIgnore
    private String airMethod;
    /**
     * 走线方式
     */
    @ApiModelProperty(value = "走线方式")
	@ExcelIgnore
    private String routingMethod;
    /**
     * 维护人
     */
    @ApiModelProperty(value = "维护人")
	@ExcelIgnore
    private String maintenanceUser;
    /**
     * 值班电话
     */
    @ApiModelProperty(value = "值班电话")
	@ExcelIgnore
    private String dutyPhone;
    /**
     * 地区id
     */
    @ApiModelProperty(value = "所属地区id")
	@ExcelIgnore
    private String regionCode;
	/**
	 * 所属地区名称
	 */
	@ApiModelProperty(value = "所属地区名称")
	@ExcelIgnore
	private String regionName;
	/**
	 * 地区父id
	 */
	@ApiModelProperty(value = "地区父id")
	@ExcelIgnore
	private String areaParentId;
	/**
	 * 数据所属类型
	 */
	@ApiModelProperty(value = "数据所属类型")
	@ExcelIgnore
	private String type;
	/**
	 * 创建部门
	 */
	@ApiModelProperty(value = "创建部门")
	@ExcelIgnore
	private String createDept;
	/**
	 * 是否同步i6000
	 */
	@ApiModelProperty(value = "是否同步i6000 否-0 是-1")
	@ExcelIgnore
	private String isI6000;

	/**
	 * 关联I6000机房uuid
	 */
	@ApiModelProperty(value = "关联I6000机房uuid")
	@ExcelIgnore
	private String i6000Uuid;

	/**
	 * 关联I6000机房name
	 */
	@ApiModelProperty(value = "关联I6000机房name")
	@ExcelProperty("关联I6000机房")
	private String i6000Name;

}
