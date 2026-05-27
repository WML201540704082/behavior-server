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
package com.lnsoft.device.api.operation.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lnsoft.core.mp.base.BaseEntity;
import com.lnsoft.device.api.operation.annotation.ForUpdate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备变更实体类
 *
 * @author Idevelop
 * @since 2024-03-07
 */
@Data
@TableName("idevelop_device_change_list")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceChangeList对象", description = "设备变更")
public class DeviceChangeList extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@TableId
	private String id;
	/**
	 * 设备id
	 */
	@ApiModelProperty(value = "设备id")
	private String deviceId;
	/**
	 * uuid
	 */
	@ApiModelProperty(value = "uuid")
	private String uuid;
	/**
	 * 变更编号
	 */
	@ApiModelProperty(value = "变更关联id")
	private String changeId;
	/**
	 * 设备编号
	 */
	@ApiModelProperty(value = "设备编号")
	private String deviceCode;
	/**
	 * ERP资产编码
	 */
	@ApiModelProperty(value = "ERP设备台账编码")
	private String deviceCodeErp;
	@ApiModelProperty(value = "erp资产编码")
	private String assetCodeErp;
	/**
	 * 设备名称
	 */
	@ApiModelProperty(value = "设备名称")
	@ForUpdate(fieldName = "设备名称")
	private String fullName;
	/**
	 * 出厂序列号
	 */
	@ApiModelProperty(value = "出厂序列号")
	@ForUpdate(fieldName = "出厂序列号")
	private String sn;
	/**
	 * 领用单位
	 */
	@ApiModelProperty(value = "领用单位")
	@ForUpdate(fieldName = "领用单位")
	private String receiveUnit;
	/**
	 * 领用部门
	 */
	@ApiModelProperty(value = "领用部门")
	@ForUpdate(fieldName = "领用部门")
	private String receiveDept;
	/**
	 * 领用责任人
	 */
	@ApiModelProperty(value = "责任人")
	@ForUpdate(fieldName = "责任人")
	private String receivingPerson;
	/**
	 * 领用责任人身份证
	 */
	@ApiModelProperty(value = "责任人身份证")
	@ForUpdate(fieldName = "责任人身份证")
	private String receivingIDCard;
	/**
	 * 领用责任人联系方式
	 */
	@ApiModelProperty(value = "责任人联系方式")
	@ForUpdate(fieldName = "责任人联系方式")
	private String receivingTel;
	/**
	 * 领用责任人ISC账号
	 */
	@ApiModelProperty(value = "责任人统一权限账号")
	@ForUpdate(fieldName = "责任人统一权限账号")
	private String receivePersonUnifiedAcc;
	/**
	 * erp同步状态
	 */
	@ApiModelProperty(value = "erp同步状态  0-未同步 1- 已同步 ")
	private Integer erpStatus;
	/**
	 * 使用人
	 */
	@ApiModelProperty(value = "使用人")
	@ForUpdate(fieldName = "使用人")
	private String user;
	/**
	 * 使用人联系方式
	 */
	@ApiModelProperty(value = "使用人联系方式")
	@ForUpdate(fieldName = "使用人联系方式")
	private String userTel;
	/**
	 * 使用人身份证
	 */
	@ApiModelProperty(value = "使用人身份证")
	@ForUpdate(fieldName = "使用人身份证")
	private String deviceUserIDCard;
	/**
	 * 安装地点
	 */
	@ApiModelProperty(value = "安装地点")
	@ForUpdate(fieldName = "安装地点")
	private String installationSite;
	/**
	 * 运维单位
	 */
	@ApiModelProperty(value = "运维单位")
	@ForUpdate(fieldName = "运维单位")
	private String operationUnit;
	/**
	 * 运维部门
	 */
	@ApiModelProperty(value = "运维部门")
	@ForUpdate(fieldName = "运维部门")
	private String operationDept;
	/**
	 * 运维责任人
	 */
	@ApiModelProperty(value = "运维责任人")
	@ForUpdate(fieldName = "运维责任人")
	private String operationPerson;
	/**
	 * 运维等级
	 */
	@ApiModelProperty(value = "运维等级")
	@ForUpdate(fieldName = "运维等级")
	private String operationLevel;
	/**
	 * 运维联系电话
	 */
	@ApiModelProperty(value = "运维联系电话")
	@ForUpdate(fieldName = "运维联系电话")
	private String operationTel;
	/**
	 * 认证方式
	 */
	@ApiModelProperty(value = "认证方式")
	@ForUpdate(fieldName = "认证方式")
	private String networkAccessMethod;
	/**
	 * 设备状态
	 */
	@ApiModelProperty(value = "设备状态")
	private String deviceStatus;
	/**
	 * 品牌
	 */
	@ApiModelProperty(value = "品牌")
	@ForUpdate(fieldName = "品牌")
	private String brand;
	/**
	 * 系列
	 */
	@ApiModelProperty(value = "系列")
	@ForUpdate(fieldName = "系列")
	private String series;
	/**
	 * 型号
	 */
	@ApiModelProperty(value = "型号")
	@ForUpdate(fieldName = "型号")
	private String deviceModel;
	/**
	 * 设备来源
	 */
	@ApiModelProperty(value = "设备来源")
	@ForUpdate(fieldName = "设备来源")
	private String deviceSource;
	/**
	 * MAC地址
	 */
	@ApiModelProperty(value = "MAC地址")
	@TableField(value = "mac")
	@JsonProperty("MAC")
	@ForUpdate(fieldName = "MAC地址")
	private String MAC;
	/**
	 * IP地址
	 */
	@ApiModelProperty(value = "IP地址")
	@TableField(value = "ip")
	@JsonProperty("IP")
	@ForUpdate(fieldName = "IP地址")
	private String IP;
	/**
	 * 所属子网
	 */
	@ApiModelProperty(value = "所属子网")
	@ForUpdate(fieldName = "所属子网ID")
	private String subnetId;
	/**
	 * 所属子网名称
	 */
	@ApiModelProperty(value = "所属子网名称")
	@ForUpdate(fieldName = "所属子网")
	private String subnetName;
	/**
	 * 模型id
	 */
	@ApiModelProperty(value = "模型id")
	private String ciId;
	/**
	 * 领用单位编码
	 */
	@ApiModelProperty(value = "领用单位编码")
	@ForUpdate(fieldName = "领用单位编码")
	private String receiveUnitCode;
	/**
	 * 领用部门编码
	 */
	@ApiModelProperty(value = "领用部门编码")
	@ForUpdate(fieldName = "领用部门编码")
	private String receiveDeptCode;
	/**
	 * 运维单位编码
	 */
	@ApiModelProperty(value = "运维单位编码")
	@ForUpdate(fieldName = "运维单位编码")
	private String operationUnitCode;
	/**
	 * 运维部门编码
	 */
	@ApiModelProperty(value = "运维部门编码")
	@ForUpdate(fieldName = "运维部门编码")
	private String operationDepCode;
	/**
	 * 运维责任人账号
	 */
	@ApiModelProperty(value = "运维责任人账号")
	@ForUpdate(fieldName = "运维责任人账号")
	private String operationChageAccount;
	/**
	 * 运维责任人账号名称
	 */
	@ApiModelProperty(value = "运维责任人账号名称")
	@ForUpdate(fieldName = "运维责任人账号名称")
	private String operationChageAccounName;
	/**
	 * 责任人班组
	 */
	@ApiModelProperty(value = "责任人班组")
	@ForUpdate(fieldName = "运维责任人账号名称")
	private String receivingGroup;
	/**
	 * 设备类型编码
	 */
	@ApiModelProperty(value = "设备类型编码")
	private String deviceTypeCode;
	/**
	 * 设备类型编码
	 */
	@ApiModelProperty(value = "设备类型名称")
	private String deviceType;
	/**
	 * 设备类型编码
	 */
	@ApiModelProperty(value = "设备分类名称")
	private String deviceCategory;
	/**
	 * 设备类型编码
	 */
	@ApiModelProperty(value = "设备分类编码")
	private String deviceCategoryCode;
	/**
	 * 硬盘总容量
	 */
	@ApiModelProperty(value = "硬盘总容量")
	@ForUpdate(fieldName = "硬盘总容量")
	private String hardDiskCapability;
	/**
	 * 内存大小
	 */
	@ApiModelProperty(value = "内存大小")
	@ForUpdate(fieldName = "内存大小")
	private String memSize;
	/**
	 * 操作系统版本号
	 */
	@ApiModelProperty(value = "操作系统版本号")
	@TableField(value = "os_version")
	@ForUpdate(fieldName = "操作系统版本号")
	private String OSVersion;
	/**
	 * 操作系统类型
	 */
	@ApiModelProperty(value = "操作系统类型")
	@TableField(value = "os_type_code")
	@ForUpdate(fieldName = "操作系统类型")
	private String OSTypeCode;
	/**
	 * 出厂日期
	 */
	@ApiModelProperty(value = "出厂日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ForUpdate(fieldName = "出厂日期")
	private Date factoryDate;

	@ApiModelProperty(value = "使用保管部门名称")
	@ForUpdate(fieldName = "使用保管部门名称")
	private String useKeepDeptName;

	@ApiModelProperty(value = "使用保管部门")
	@ForUpdate(fieldName = "使用保管部门")
	private String useKeepDept;

	@ApiModelProperty(value = "实物管理部门名称")
	@ForUpdate(fieldName = "实物管理部门名称")

	private String entityManagementDeptName;
	@ApiModelProperty(value = "实物管理部门")
	@ForUpdate(fieldName = "实物管理部门")
	private String realManageDept;

	@ApiModelProperty(value = "电压等级")
	@ForUpdate(fieldName = "电压等级")
	private String voltageLevel;
	@ApiModelProperty(value = "电压等级编码")
	private String voltageLevelCode;

	@ApiModelProperty(value = "设备增加方式")
	@ForUpdate(fieldName = "设备增加方式")
	private String deviceAddType;
	@ApiModelProperty(value = "设备增加方式编码")
	private String deviceAddTypeCode;

	@ApiModelProperty(value = "设备变动方式")
	@ForUpdate(fieldName = "设备变动方式")
	private String deviceChangeType;
	@ApiModelProperty(value = "设备变动方式编码")
	private String deviceChangeTypeCode;

	@ApiModelProperty(value = "工厂区域")
	@ForUpdate(fieldName = "工厂区域")
	private String factoryArea;
	@ApiModelProperty(value = "工厂区域编码")
	private String factoryAreaCode;

	@ApiModelProperty(value = "功能位置")
	@ForUpdate(fieldName = "功能位置")
	private String funLocation;
	@ApiModelProperty(value = "功能位置编码")
	@ForUpdate(fieldName = "功能位置编码")
	private String funLocationCode;

	@ApiModelProperty(value = "制造商")
	@ForUpdate(fieldName = "制造商")
	private String maker;
	@ApiModelProperty(value = "制造商编码")
	private String makerCode;
	@ApiModelProperty(value = "系列编码")
	private String seriesCode;
	@ApiModelProperty(value = "品牌编码")
	private String brandCode;
	@ApiModelProperty(value = "型号编码")
	private String deviceModelCode;

	private String measureUnit;
	@ApiModelProperty(value = "投运日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ForUpdate(fieldName = "投运日期")
	private Date oprtDate;

	@ApiModelProperty(value = "维护工厂")
	@ForUpdate(fieldName = "维护工厂")
	private String maintenanceFactory;
	@ApiModelProperty(value = "维护工厂编码")
	private String maintenanceFactoryCode;

	@ApiModelProperty(value = "所属网络编码")
	@ForUpdate(fieldName = "所属网络编码")
	private String netWorkCode;

	@TableField(exist = false)
	private String userAccessId;

	@ApiModelProperty(value = "cpu品牌")
	@ForUpdate(fieldName = "cpu品牌")
	private String cpuBrand;

	@ApiModelProperty(value = "cpu品牌编码")
	private String cpuBrandCode;

	@ApiModelProperty(value = "cpu型号")
	@ForUpdate(fieldName = "cpu型号")
	private String cpuModel;

	/**
	 * 网络设备用途类型
	 */
	@ApiModelProperty(value = "网络设备用途类型")
	@ForUpdate(fieldName = "网络设备用途类型")
	private String networkDeviceType;
	/**
	 * 管理IP
	 */
	@ApiModelProperty(value = "管理IP")
	@ForUpdate(fieldName = "管理IP")
	private String manageIp;
	/**
	 * 管理用户
	 */
	@ApiModelProperty(value = "管理用户")
	@ForUpdate(fieldName = "管理用户")
	private String manageUser;
	/**
	 * 管理密码
	 */
	@ApiModelProperty(value = "管理密码")
	@ForUpdate(fieldName = "管理密码")
	private String managePassword;
	/**
	 * 交换机ip
	 */
	@ApiModelProperty(value = "交换机ip")
	@ForUpdate(fieldName = "交换机ip")
	private String switchesIp;
	/**
	 * 交换机密码
	 */
	@ApiModelProperty(value = "交换机密码")
	@ForUpdate(fieldName = "交换机密码")
	private String switchesPassword;
	/**
	 * SNMP版本号
	 */
	@ApiModelProperty(value = "SNMP版本号")
	@ForUpdate(fieldName = "SNMP版本号")
	private String snmpVersion;
	/**
	 * SNMP读字符串
	 */
	@ApiModelProperty(value = "SNMP读字符串")
	@ForUpdate(fieldName = "SNMP读字符串")
	private String snmpRead;
	/**
	 * SNMP写字符串
	 */
	@ApiModelProperty(value = "SNMP写字符串")
	@ForUpdate(fieldName = "SNMP写字符串")
	private String snmpWrite;
	/**
	 * 工作Vlan
	 */
	@ApiModelProperty(value = "工作Vlan")
	@ForUpdate(fieldName = "工作Vlan")
	private String vlanNumber;
	/**
	 * 配置密码
	 */
	@ApiModelProperty(value = "配置密码")
	@ForUpdate(fieldName = "配置密码")
	private String allocationPassword;
	/**
	 * 用途
	 */
	@ApiModelProperty(value = "用途")
	@ForUpdate(fieldName = "用途")
	private String purpose;

	@ApiModelProperty(value = "端口数量")
	@ForUpdate(fieldName = "端口数量")
	private String portsCount;

}
