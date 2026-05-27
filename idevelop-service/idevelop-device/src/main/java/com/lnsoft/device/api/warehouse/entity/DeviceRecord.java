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
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 设备建档实体类
 *
 * @author Idevelop
 * @since 2024-02-07
 */
@Data
@TableName("idevelop_device_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceRecord对象", description = "设备建档")
public class DeviceRecord extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "主键")
	private String id;

	/**
	 * WBS项目
	 */
	@NotBlank(message = "WBS项目不能为空")
	@ApiModelProperty(value = "WBS项目")
	private String wbsProject;
	/**
	 * WBS元素
	 */
	@NotBlank(message = "WBS元素不能为空")
	@ApiModelProperty(value = "WBS元素")
	private String wbsElement;
	/**
	 * 建档编号
	 */
	@ApiModelProperty(value = "建档编号")
	private String filingNo;
	/**
	 * 设备分类
	 */
	@NotBlank(message = "设备分类不能为空")
	@ApiModelProperty(value = "设备分类")
	private String deviceCategory;
	/**
	 * 设备分类
	 */
	@NotBlank(message = "设备分类名称不能为空")
	@ApiModelProperty(value = "设备分类名称")
	private String deviceCategoryName;
	/**
	 * 设备类型
	 */
	@NotBlank(message = "设备类型不能为空")
	@ApiModelProperty(value = "设备类型")
	private String deviceType;
	/**
	 * 设备分类名称
	 */
	@NotBlank(message = "设备分类名称不能为空")
	@ApiModelProperty(value = "设备分类名称")
	private String deviceTypeName;
	/**
	 * 设备数量
	 */
	@ApiModelProperty(value = "设备数量")
	@Min(value = 1, message = "设备数量需要大于0")
	@Max(value = 999, message = "设备数量不能大于999")
	@NotNull(message = "设备数量不能为空")
	private Integer deviceNum;
	/**
	 * 设备增加方式
	 */
	@NotBlank(message = "设备增加方式不能为空")
	@ApiModelProperty(value = "设备增加方式")
	private String deviceAddType;
	/**
	 * 设备变动方式
	 */
	@ApiModelProperty(value = "设备变动方式")
	private String deviceChangeType;
	/**
	 * 安装地点
	 */
	@ApiModelProperty(value = "安装地点")
	private String installationSite;
	/**
	 * 设备状态
	 */
	@ApiModelProperty(value = "设备状态")
	private String deviceStatus;
	/**
	 * 投运日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "投运日期")
	private Date oprtDate;
	/**
	 * 采购日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "采购日期")
	private Date procureDate;
	/**
	 * 设备名称
	 */
	@NotBlank(message = "设备名称不能为空")
	@ApiModelProperty(value = "设备名称")
	private String deviceName;
	/**
	 * 标准全称
	 */
	@NotBlank(message = "标准全称不能为空")
	@ApiModelProperty(value = "标准全称")
	private String fullName;
	/**
	 * 是否同步ERP 0 不同步 1 同步
	 */
	@NotNull(message = "是否同步ERP不能为空")
	@ApiModelProperty(value = "是否同步ERP 0 不同步 1 同步")
	private Integer isToErp;
	/**
	 * 品牌
	 */
	@NotBlank(message = "品牌不能为空")
	@ApiModelProperty(value = "品牌")
	private String brand;
	/**
	 * 系列
	 */
	@NotBlank(message = "系列不能为空")
	@ApiModelProperty(value = "系列")
	private String series;
	/**
	 * 型号
	 */
	@NotBlank(message = "型号不能为空")
	@ApiModelProperty(value = "型号")
	private String deviceModel;
	/**
	 * 铭牌号
	 */
	@NotBlank(message = "铭牌号不能为空")
	@ApiModelProperty(value = "铭牌号")
	private String nameplateNo;
	/**
	 * 制造商
	 */
	@NotBlank(message = "制造商不能为空")
	@ApiModelProperty(value = "制造商")
	private String manufacturer;
	/**
	 * 制造国家与地区
	 */
	@NotBlank(message = "制造国家与地区不能为空")
	@ApiModelProperty(value = "制造国家与地区")
	private String maintenanceCountry;
	/**
	 * 出厂日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "出厂日期不能为空")
	@ApiModelProperty(value = "出厂日期")
	private Date factoryDate;
	/**
	 * 产权单位
	 */
	@ApiModelProperty(value = "产权单位")
	private String ownerUnit;
	/**
	 * 产权单位名称
	 */
	@ApiModelProperty(value = "产权单位名称")
	private String ownerUnitName;
	/**
	 * 产权部门
	 */
	@ApiModelProperty(value = "产权部门")
	private String propertyDept;
	/**
	 * 产权部门名称
	 */
	@ApiModelProperty(value = "产权部门名称")
	private String propertyDeptName;
	/**
	 * 使用保管部门
	 */
	@ApiModelProperty(value = "使用保管部门")
	private String useKeepDept;
	/**
	 * 使用保管部门名称
	 */
	@ApiModelProperty(value = "使用保管部门名称")
	private String useKeepDeptName;
	/**
	 * 实物保管部门
	 */
	@ApiModelProperty(value = "实物保管部门")
	private String entityKeepDept;
	/**
	 * 实物保管部门名称
	 */
	@ApiModelProperty(value = "实物保管部门名称")
	private String entityKeepDeptName;
	/**
	 * 使用保管人
	 */
	@NotBlank(message = "使用保管人不能为空")
	@ApiModelProperty(value = "使用保管人")
	private String useKeepPerson;
	/**
	 * 功能位置
	 */
	@ApiModelProperty(value = "功能位置")
	private String funLocation;
	/**
	 * 工厂区域
	 */
	@ApiModelProperty(value = "工厂区域")
	private String factoryArea;
	/**
	 * 维护工厂
	 */
	@ApiModelProperty(value = "维护工厂")
	private String maintenanceFactory;
	/**
	 * 线站标识
	 */
	@NotBlank(message = "线站标识不能为空")
	@ApiModelProperty(value = "线站标识")
	private String lineStation;
	/**
	 * ERP同步状态   0 未上报 1 建档中 2 已建档 3 建档失败
	 */
	@ApiModelProperty(value = "ERP同步状态   0 未上报 1 建档中 2 已建档 3 建档失败")
	private String erpStatus;
	/**
	 * 受理人(工单发起人)
	 */
	@NotBlank(message = "受理人不能为空")
	@ApiModelProperty(value = "受理人(工单发起人)")
	private String receiver;
	/**
	 * 受理人姓名
	 */
	@NotBlank(message = "受理人姓名不能为空")
	@ApiModelProperty(value = "受理人姓名")
	private String receiverName;
	/**
	 * 受理时间
	 */
	@NotNull(message = "受理时间不能为空")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "受理时间")
	private Date receiverTime;
	/**
	 * 发起人
	 */
	@ApiModelProperty(value = "发起人")
	private String submitUser;
	/**
	 * 发起时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "发起时间")
	private Date submitTime;
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
	 * 流程实例ID
	 */
	@ApiModelProperty(value = "流程实例ID")
	private String processInsId;
	/**
	 * 流程状态
	 */
	@ApiModelProperty(value = "流程状态")
	private String processStatus;
	/**
	 * 计量单位
	 */
	@ApiModelProperty(value = "计量单位")
	private String unit;
	/**
	 * 计量单位名称
	 */
	@ApiModelProperty(value = "计量单位名称")
	private String unitName;
	/**
	 * 区域编码
	 */
	@ApiModelProperty(value = "区域编码")
	private String regionCode;
	/**
	 * 功能位置
	 */
	@ApiModelProperty(value = "功能位置编码")
	private String funLocationCode;
	/**
	 * 工厂区域
	 */
	@ApiModelProperty(value = "工厂区域编码")
	private String factoryAreaCode;
	/**
	 * 维护工厂
	 */
	@ApiModelProperty(value = "维护工厂编码")
	private String maintenanceFactoryCode;

	/**
	 * 是否已推送给ERP, 0未推送,已推送.
	 */
	@ApiModelProperty("是否已推送给ERP, 0未推送,1已推送.")
	private Integer isSendErp;

}
