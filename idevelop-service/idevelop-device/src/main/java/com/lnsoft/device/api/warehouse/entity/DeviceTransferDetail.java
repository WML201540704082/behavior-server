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
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 设备转资明细表实体类
 *
 * @author Idevelop
 * @since 2024-02-27
 */
@Data
@TableName("idevelop_device_transfer_detail")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceTransferDetail对象", description = "设备转资明细表")
public class DeviceTransferDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private String id;
	/**
	 * 转资工单ID
	 */
	@ApiModelProperty(value = "转资工单ID")
	private String transferId;
	/**
	 * 设备id
	 */
	@ApiModelProperty(value = "设备id")
	private Long deviceId;
	/**
	 * 设备uuid
	 */
	@ApiModelProperty(value = "设备uuid")
	private String deviceUuid;
	/**
	 * 设备编码
	 */
	@ApiModelProperty(value = "设备编码")
	private String deviceCode;
	/**
	 * ERP同步状态
	 */
	@ApiModelProperty(value = "ERP同步状态")
	private String erpStatus;
	/**
	 * erp资产编码
	 */
	@ApiModelProperty(value = "erp资产编码")
	private String erpAssetCode;
	/**
	 * erp编码状态
	 */
	@ApiModelProperty(value = "erp资产编码使用状态")
	private String erpAssetStatus;
	/**
	 * ERP台账编号
	 */
	@ApiModelProperty(value = "ERP台账编号")
	private String erpAccountCode;
	/**
	 * i6000同步状态
	 */
	@ApiModelProperty(value = "i6000同步状态")
	private String i6000Status;
	/**
	 * 资产信息
	 */
	@ApiModelProperty(value = "资产信息")
	private String deviceAssetInfo;
	/**
	 * 功能位置
	 */
	@ApiModelProperty(value = "功能位置")
	private String funLocation;
	/**
	 * 设备状态
	 */
	@ApiModelProperty(value = "设备状态")
	private String deviceStatus;
	/**
	 * 设备名称
	 */
	@ApiModelProperty(value = "设备名称")
	private String deviceName;
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
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;
	/**
	 * 创建部门
	 */
	@ApiModelProperty(value = "创建部门")
	private Long createDept;
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
	 * 设备增加方式
	 */
	@ApiModelProperty(value = "设备增加方式")
	private String deviceAddType;
	/**
	 * 设备变动方式
	 */
	@ApiModelProperty(value = "设备变动方式")
	private String deviceChangeType;

	/**
	 * 产权单位名称
	 */
	@TableField(exist = false)
	private String ownerUnitName;

	/**
	 * 區域
	 */
	@TableField(exist = false)
	private String regionCode;

	/**
	 * 实物ID
	 */
	private String swid;

	/**
	 * 电压等级
	 */
//	@NotBlank(message = "电压等级不能为空")
	@ApiModelProperty(value = "电压等级")
	private String zsb004;

	/**
	 * 制造商
	 */
//	@NotBlank(message = "制造商不能为空")
	@ApiModelProperty(value = "制造商")
	private String herst;

	/**
	 * 制造商设备型号
	 */
//	@NotBlank(message = "制造商设备型号不能为空")
	@ApiModelProperty(value = "制造商设备型号")
	private String typbz;

	/**
	 * 制造商设备铭牌号
	 */
//	@NotBlank(message = "设备铭牌号不能为空")
	@ApiModelProperty(value = "制造商设备铭牌号")
	private String serge;

	/**
	 * 制造（出厂、竣工）年份
	 */
//	@NotBlank(message = "制造（出厂、竣工）年份不能为空")
	@ApiModelProperty(value = "制造年份")
	private String baujj;

	/**
	 * 制造（出厂、竣工）月份
	 */
//	@NotBlank(message = "制造（出厂、竣工）月份不能为空")
	@ApiModelProperty(value = "制造月份")
	private String baumm;

	/**
	 * 维护工厂 = 维护工厂编码
	 */
//	@NotBlank(message = "维护工厂不能为空")
	@ApiModelProperty(value = "维护工厂")
	private String swerk;

	/**
	 * 设备存放地点 = 功能位置名称
	 */
//	@NotBlank(message = "设备存放地点不能为空")
	@ApiModelProperty(value = "设备存放地点，功能位置名称")
	private String zsb006;

	/**
	 * 功能位置 = 功能位置编码
	 */
//	@NotBlank(message = "功能位置不能为空")
	@ApiModelProperty(value = "功能位置编码")
	private String tplnr;

	/**
	 * ciId
	 */
	@ApiModelProperty(value = "ciId")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long ciId;

	private String sn;

	private String factoryDate;

	private String maintenanceCountry;

	private String supplierName;

	private String supplierTel;

	private String entityJson;

}
