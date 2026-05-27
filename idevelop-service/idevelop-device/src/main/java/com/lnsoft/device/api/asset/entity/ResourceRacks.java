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

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;

import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 空间资源管理机架表实体类
 *
 * @author Idevelop
 * @since 2024-02-21
 */
@Data
@TableName("idevelop_resource_racks")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ResourceRacks对象", description = "空间资源管理机架表")
public class ResourceRacks extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
  private String id;
    /**
     * 机架名称
     */
    @ApiModelProperty(value = "机架名称")
    private String racksName;
    /**
     * 全局名称
     */
    @ApiModelProperty(value = "全局名称")
    private String globalName;
    /**
     * 简称
     */
    @ApiModelProperty(value = "简称")
    private String abbreviation;
    /**
     * 所属机柜
     */
    @ApiModelProperty(value = "所属机柜")
    private String belongRacks;
    /**
     * 生产厂家
     */
    @ApiModelProperty(value = "生产厂家")
    private String produceFactory;
    /**
     * 投运日期
     */
    @ApiModelProperty(value = "投运日期")
    private LocalDate useDate;
    /**
     * 机架序号
     */
    @ApiModelProperty(value = "机架序号")
    private String racksNum;
    /**
     * 机架占用
     */
    @ApiModelProperty(value = "机架占用")
    private String racksOccupancy;
    /**
     * 运行状态
     */
    @ApiModelProperty(value = "运行状态")
    private String runningStatus;
    /**
     * 机架位置
     */
    @ApiModelProperty(value = "机架位置")
    private String racksLocation;
    /**
     * 退运日期
     */
    @ApiModelProperty(value = "退运日期")
    private LocalDate returnDate;
    /**
     * 维护单位
     */
    @ApiModelProperty(value = "维护单位")
    private String maintenanceUnit;
	/**
	 * 维护单位
	 */
	@ApiModelProperty(value = "维护单位")
	private String maintenanceUnitName;
    /**
     * 维护人
     */
    @ApiModelProperty(value = "维护人")
    private String maintenanceUser;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 机柜id
     */
    @ApiModelProperty(value = "机柜id")
    private String cabinetsId;
	/**
	 * 数据所属类型
	 */
	@ApiModelProperty(value = "数据所属类型")
	private String type;
	/**
	 * 创建部门
	 */
	@ApiModelProperty(value = "创建部门")
	private String createDept;

}
