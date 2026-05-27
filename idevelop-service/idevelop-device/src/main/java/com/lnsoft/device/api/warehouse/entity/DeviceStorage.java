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
package com.lnsoft.device.api.warehouse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lnsoft.core.mp.base.BaseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 设备入库表实体类
 *
 * @author Idevelop
 * @since 2024-02-22
 */
@Data
@TableName("idevelop_device_storage")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceStorage对象", description = "设备入库表")
public class DeviceStorage extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private String id;
	/**
	 * 入库单号
	 */
	@ApiModelProperty(value = "入库单号")
	private String serialNumber;
	/**
	 * 设备来源；0统一纳管，1非统一纳管
	 */
	@ApiModelProperty(value = "设备来源；0统一纳管，1非统一纳管")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private String deviceSource;
	/**
	 * WBS项目
	 */
	@ApiModelProperty(value = "WBS项目")
	private String wbsProject;
	/**
	 * WBS元素
	 */
	@ApiModelProperty(value = "WBS元素")
	private String wbsElement;
	/**
	 * 设备分类
	 */
	@ApiModelProperty(value = "设备分类")
	private String deviceCategory;
	/**
	 * 设备类型
	 */
	@ApiModelProperty(value = "设备类型")
	private String deviceType;
	/**
	 * 所在仓库
	 */
	@ApiModelProperty(value = "所在仓库")
	private String warehouse;
	/**
	 * 入库数量
	 */
	@ApiModelProperty(value = "入库数量")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer deviceNum;
	/**
	 * 运行单位
	 */
	@ApiModelProperty(value = "运行单位")
	private String oprtDept;
	/**
	 * 受理人
	 */
	@ApiModelProperty(value = "受理人")
	private String receiver;
	/**
	 * 入库时间
	 */
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "入库时间")
	private LocalDateTime storageTime;
	/**
	 * 使用保管人
	 */
	@ApiModelProperty(value = "使用保管人")
	private String useKeepPerson;
	/**
	 * 电压等级
	 */
	@ApiModelProperty(value = "电压等级")
	private String voltageLevel;
	/**
	 * 采购日期
	 */
	@JsonFormat(
		pattern = "yyyy-MM-dd"
	)
	@ApiModelProperty(value = "采购日期")
	private LocalDate procureDate;
	/**
	 * 产权单位
	 */
	@ApiModelProperty(value = "产权单位")
	private String ownerUnit;
	private String ownerUnitName;
	/**
	 * 产权部门
	 */
	@ApiModelProperty(value = "产权部门")
	private String propertyDept;
	private String propertyDeptName;

	/**
	 * 是否同步I6000：0否1是
	 */
	@ApiModelProperty(value = "是否同步I6000：0否1是")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isToI6000;
	/**
	 * 是否同步ERP(是否立即转资)：0否1是
	 */
	@ApiModelProperty(value = "是否同步ERP(是否立即转资)：0否1是")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isToErp;
	/**
	 * 是否可用：0否1是
	 */
	@ApiModelProperty(value = "是否可用：0否1是")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isUsable;
	/**
	 * 是否暂存：0否1是
	 */
	@ApiModelProperty(value = "是否暂存：0否1是")
	@NotNull(message = "是否暂存状态值不能为空")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isTemp;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "创建部门")
	private String createDept;

	@ApiModelProperty(value = "i6000同步状态：0未同步1已同步")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer statusI6000;

	@ApiModelProperty(value = "区域编码")
	private String regionCode;

	@ApiModelProperty(value = "部门编码")
	private String deptCode;

	@ApiModelProperty(value = "部门名称")
	private String deptName;

	@ApiModelProperty(value = "运维单位")
	private String operationUnit;

	@ApiModelProperty(value = "运维单位编码")
	private String operationUnitCode;

}
