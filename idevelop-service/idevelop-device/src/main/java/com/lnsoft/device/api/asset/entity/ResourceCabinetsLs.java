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
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;

import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * 空间资源管理机柜表实体类
 *
 * @author Idevelop
 * @since 2024-02-21
 */
@Data
@TableName("idevelop_resource_cabinets")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ResourceCabinets对象", description = "空间资源管理机柜表")
public class ResourceCabinetsLs extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private String id;
	/**
	 * 机柜名称
	 */
	@ApiModelProperty(value = "机柜名称")
	private String cabinetsName;
	/**
	 * 机柜编号
	 */
	@ApiModelProperty(value = "机柜编号")
	private String cabinetsId;
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
	 * 所属机房
	 */
	@ApiModelProperty(value = "所属机房")
	private String belongRoom;
	/**
	 * 所属模块
	 */
	@ApiModelProperty(value = "所属模块")
	private String module;
	/**
	 * 机柜类型
	 */
	@ApiModelProperty(value = "机柜类型")
	private String cabinetsType;
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
	 * 机柜宽度（mm）
	 */

	@ApiModelProperty(value = "机柜宽度（mm）")
	private Integer cabinetsWidth;
	/**
	 * 机柜高度（mm）
	 */
	@ApiModelProperty(value = "机柜高度（mm）")
	private Integer cabinetsHeight;
	/**
	 * 是否正序
	 */
	@ApiModelProperty(value = "是否正序")
	private String isSort;
	/**
	 * 维护单位
	 */
	@ApiModelProperty(value = "维护单位")
	private String maintenanceUnit;
	/**
	 * 维护单位名称
	 */
	@ApiModelProperty(value = "维护单位名称")
	private String maintenanceUnitName;
	/**
	 * 容量（U）
	 */
	@ApiModelProperty(value = "容量（U）")
	private String capacity;
	/**
	 * 设备型号
	 */
	@ApiModelProperty(value = "设备型号")
	private String deviceModel;
	/**
	 * 机柜深度（mm）
	 */
	@ApiModelProperty(value = "机柜深度（mm）")
	private Integer cabinetsDepth;
	/**
	 * 退运日期
	 */
	@ApiModelProperty(value = "退运日期")
	private LocalDate returnDate;
	/**
	 * 维护人
	 */
	@ApiModelProperty(value = "维护人")
	private String maintenanceUser;
	/**
	 * 机房id
	 */
	@ApiModelProperty(value = "机房id")
	private String roomId;
	/**
	 * 数据所属类型
	 */
	@ApiModelProperty(value = "数据所属类型 ")
	private String type;
	/**
	 * 创建部门
	 */
	@ApiModelProperty(value = "创建部门")
	private String createDept;
	/**
	 * 是否同步i6000
	 */
	@ApiModelProperty(value = "是否同步i6000 否-0 是-1")
	@ExcelIgnore
	private String isI6000;
}
