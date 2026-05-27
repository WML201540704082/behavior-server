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
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 设备转资实体类
 *
 * @author Idevelop
 * @since 2024-02-27
 */
@Data
@TableName("idevelop_device_transfer")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceTransfer对象", description = "设备转资")
public class DeviceTransfer extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private String id;
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
	 * 工单编号
	 */
	@ApiModelProperty(value = "工单编号")
	private String filingNo;
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
	 * ERP同步状态
	 */
	@ApiModelProperty(value = "ERP同步状态  0 未上报 1 转资中 2 已转资 3 转资失败")
	private String erpStatus;
	/**
	 * 是否同步i6000 0 不同步 1 同步
	 */
	@ApiModelProperty(value = "i6000同步状态 0 不同步 1 同步")
	private String i6000SyncStatus;
	/**
	 * 产权单位
	 */
	@ApiModelProperty(value = "产权单位")
	private String ownerUnit;
	/**
	 * 产权部门
	 */
	@ApiModelProperty(value = "产权部门")
	private String propertyDept;
	/**
	 * 产权单位
	 */
	@ApiModelProperty(value = "产权单位名称")
	private String ownerUnitName;
	/**
	 * 产权部门
	 */
	@ApiModelProperty(value = "产权部门")
	private String propertyDeptName;
	/**
	 * 使用保管人
	 */
	@ApiModelProperty(value = "使用保管人")
	private String useKeepPerson;
	/**
	 * 使用保管部门
	 */
	@ApiModelProperty(value = "使用保管部门")
	private String useKeepDept;
	/**
	 * 实物保管部门
	 */
	@ApiModelProperty(value = "实物保管部门")
	private String entityKeepDept;
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
	 * 维护工厂名称
	 */
	@ApiModelProperty(value = "维护工厂名称")
	private String maintenanceName;
	/**
	 * 线站标识
	 */
	@ApiModelProperty(value = "线站标识")
	private String lineStation;
	/**
	 * 安装地点
	 */
	@ApiModelProperty(value = "安装地点")
	private String installationSite;
	/**
	 * 工单发起人
	 */
	@ApiModelProperty(value = "工单发起人")
	private String receiver;
	/**
	 * 工单发起时间
	 */
	@ApiModelProperty(value = "工单发起时间")
	private Date receiverTime;
	/**
	 * 同步i6000状态 0 未同步 1 同步中 2 同步成功 3 同步失败
	 */
	@ApiModelProperty(value = "同步i6000状态 0 未同步 1 同步中 2 同步成功 3 同步失败")
	private String i6000Status;
	/**
	 * 创建部门
	 */
	@ApiModelProperty(value = "创建部门")
	private Long createDept;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

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
	 * 创建人所在区域
	 */
	@ApiModelProperty(value = "创建人所在区域")
	private String regionCode;

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
}
