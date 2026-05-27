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

import java.util.Date;

/**
 * 设备投运单设备详情实体类
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@Data
@TableName("idevelop_device_operation_detail")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceOperationDetail对象", description = "设备投运单设备详情")
public class DeviceOperationDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private String id;
	/**
	 * 设备投运单id
	 */
	@ApiModelProperty(value = "设备投运单id")
	private String operationId;
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
	 * 设备分类名称
	 */
	@ApiModelProperty(value = "设备分类名称")
	private String deviceCategoryName;
	/**
	 * 设备类型名称
	 */
	@ApiModelProperty(value = "设备类型名称")
	private String deviceTypeName;
	/**
	 * 设备CMDB的id
	 */
	@ApiModelProperty(value = "设备CMDB的id")
	private String deviceId;
	/**
	 * 设备CMDB的Cid
	 */
	@ApiModelProperty(value = "设备CMDB的Cid")
	private String deviceCid;
	/**
	 * 设备CMDB的UUid
	 */
	@ApiModelProperty(value = "设备CMDB的UUid")
	private String deviceUuid;
	/**
	 * 使用人
	 */
	@ApiModelProperty(value = "使用人")
	private String userName;
	/**
	 * 使用人联系方式
	 */
	@ApiModelProperty(value = "使用人联系方式")
	private String userPhone;
	/**
	 * 使用人身份证号
	 */
	@ApiModelProperty(value = "使用人身份证号")
	private String userCard;
	/**
	 * 设备编码
	 */
	@ApiModelProperty(value = "设备编码")
	private String deviceCode;
	/**
	 * 设备名称
	 */
	@ApiModelProperty(value = "设备名称")
	private String deviceName;
	/**
	 * 设备状态
	 */
	@ApiModelProperty(value = "设备状态")
	private String deviceStatus;
	/**
	 * ERP资产编号
	 */
	@ApiModelProperty(value = "ERP资产编码")
	private String erpAssetCode;
	/**
	 * 出厂序列号
	 */
	@ApiModelProperty(value = "出厂序列号")
	private String factoryNumber;
	/**
	 * 旧设备CMDB的id
	 */
	@ApiModelProperty(value = "旧设备CMDB的id")
	private String oldDeviceId;
	/**
	 * 旧设备CMDB的Cid
	 */
	@ApiModelProperty(value = "旧设备CMDB的Cid")
	private String oldDeviceCid;
	/**
	 * 旧设备CMDB的UUid
	 */
	@ApiModelProperty(value = "旧设备CMDB的UUid")
	private String oldDeviceUuid;
	/**
	 * 旧设备设备编码
	 */
	@ApiModelProperty(value = "旧设备设备编码")
	private String oldDeviceCode;
	/**
	 * 旧设备设备名称
	 */
	@ApiModelProperty(value = "旧设备设备名称")
	private String oldDeviceName;
	/**
	 * 旧设备ip
	 */
	@ApiModelProperty(value = "旧设备ip")
	private String oldDeviceIp;
	/**
	 * 旧设备使用人
	 */
	@ApiModelProperty(value = "旧设备使用人")
	private String oldDeviceUser;
	/**
	 * 旧设备使用人联系方式
	 */
	@ApiModelProperty(value = "旧设备使用人联系方式")
	private String oldUserPhone;
	/**
	 * 旧设备使用人身份证号
	 */
	@ApiModelProperty(value = "旧设备使用人身份证号")
	private String oldUserCard;
	/**
	 * 旧设备安装地点
	 */
	@ApiModelProperty(value = "旧设备安装地点")
	private String oldAddress;
	/**
	 * 旧设备是否公用 0 公用 1 个人
	 */
	@ApiModelProperty(value = "旧设备是否公用 0 公用 1 个人")
	private Integer oldUserType;
	/**
	 * 旧设备责任人
	 */
	@ApiModelProperty(value = "旧设备责任人")
	private String oldDeviceReceiveUse;
	/**
	 * 领用时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "领用时间")
	private Date userTime;
	/**
	 * 是否公用 0 公用 1 个人
	 */
	@ApiModelProperty(value = "是否公用 0 公用 1 个人")
	private Integer userType;
	/**
	 * 安装地点
	 */
	@ApiModelProperty(value = "安装地点")
	private String address;
	/**
	 * 所属子网id
	 */
	@ApiModelProperty(value = "所属子网id")
	private String deviceSubnet;
	/**
	 * 所属子网名称
	 */
	@ApiModelProperty(value = "所属子网名称")
	private String deviceSubnetName;
	/**
	 * 所属网络
	 */
	@ApiModelProperty(value = "所属网络")
	private String networkType;
	/**
	 * 所属网络名称
	 */
	@ApiModelProperty(value = "所属网络名称")
	private String networkTypeName;
	/**
	 * IP地址
	 */
	@ApiModelProperty(value = "IP地址")
	private String deviceIp;
	/**
	 * 旧MAC地址
	 */
	@ApiModelProperty(value = "旧MAC地址")
	private String deviceOldMac;
	/**
	 * MAC地址
	 */
	@ApiModelProperty(value = "MAC地址")
	private String deviceMac;
	/**
	 * 入网方式
	 */
	@ApiModelProperty(value = "入网方式")
	private String deviceNetworkType;
	/**
	 * 认证账号
	 */
	@ApiModelProperty(value = "认证账号")
	private String authAccount;
	/**
	 * 认证密码
	 */
	@ApiModelProperty(value = "认证密码")
	private String authPassword;
	/**
	 * 运维单位
	 */
	@ApiModelProperty(value = "运维单位")
	private String operationUnit;
	/**
	 * 运维单位名称
	 */
	@ApiModelProperty(value = "运维单位名称")
	private String operationUnitName;
	/**
	 * 运维部门
	 */
	@ApiModelProperty(value = "运维部门")
	private String operationDept;
	/**
	 * 运维部门名称
	 */
	@ApiModelProperty(value = "运维部门名称")
	private String operationDeptName;
	/**
	 * 运维责任人
	 */
	@ApiModelProperty(value = "运维责任人")
	private String operationUse;
	/**
	 * 运维等级
	 */
	@ApiModelProperty(value = "运维等级")
	private String operationGrade;
	/**
	 * 运维等级编码
	 */
	@ApiModelProperty(value = "运维等级编码")
	private String operationGradeCode;
	/**
	 * 运维联系电话
	 */
	@ApiModelProperty(value = "运维联系电话")
	private String operationPhone;
	/**
	 * 设备来源；0统一纳管，1非统一纳管
	 */
	@ApiModelProperty(value = "设备来源")
	private String deviceSource;
	/**
	 * 是否启用802.1x接入认证
	 */
	@ApiModelProperty(value = "是否启用802.1x接入认证")
	private String is802;
	/**
	 * 是否认证
	 */
	@ApiModelProperty(value = "是否认证")
	private String isAccess;
	/**
	 * 更换原因
	 */
	@ApiModelProperty(value = "更换原因")
	private String changePerson;
	/**
	 * 标准全称
	 */
	@ApiModelProperty(value = "标准全称")
	private String fullName;
	/**
	 * 售后服务到期时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "售后服务到期时间")
	private Date afterSaleExpDate;
	/**
	 * 品牌
	 */
	@ApiModelProperty(value = "品牌")
	private String brand;
	/**
	 * 品牌名称
	 */
	@ApiModelProperty(value = "品牌名称")
	private String brandName;
	/**
	 * 系列
	 */
	@ApiModelProperty(value = "系列")
	private String series;
	/**
	 * 系列名称
	 */
	@ApiModelProperty(value = "系列名称")
	private String seriesName;
	/**
	 * 型号
	 */
	@ApiModelProperty(value = "型号")
	private String deviceModel;
	/**
	 * 型号名称
	 */
	@ApiModelProperty(value = "型号名称")
	private String deviceModelName;
	/**
	 * 是否信创设备 0 是 1 否
	 */
	@ApiModelProperty(value = "是否信创设备 0 是 1 否")
	private String isItal;
	/**
	 * CPU品牌
	 */
	@ApiModelProperty(value = "CPU品牌")
	private String cpuBrand;
	/**
	 * CPU品牌编码
	 */
	@ApiModelProperty(value = "CPU品牌编码")
	private String cpuBrandCode;
	/**
	 * 操作系统类型
	 */
	@ApiModelProperty(value = "操作系统类型")
	private String osType;
	/**
	 * 操作系统类型编码
	 */
	@ApiModelProperty(value = "操作系统类型编码")
	private String osTypeCode;
	/**
	 * 运行单位
	 */
	@ApiModelProperty(value = "运行单位")
	private String oprtDept;
	/**
	 * 运行单位名称
	 */
	@ApiModelProperty(value = "运行单位名称")
	private String oprtDeptName;
	/**
	 * 领用单位
	 */
	@ApiModelProperty(value = "领用单位")
	private String receiveUnit;
	/**
	 * 领用单位名称
	 */
	@ApiModelProperty(value = "领用单位名称")
	private String receiveUnitName;
	/**
	 * 领用部门
	 */
	@ApiModelProperty(value = "领用部门")
	private String receiveDutyDept;
	/**
	 * 领用部门名称
	 */
	@ApiModelProperty(value = "领用部门名称")
	private String receiveDutyDeptName;
	/**
	 * 领用责任人班组
	 */
	@ApiModelProperty(value = "领用责任人班组")
	private String receiveDutyGroup;
	/**
	 * 领用责任人班组名称
	 */
	@ApiModelProperty(value = "领用责任人班组名称")
	private String receiveDutyGroupName;
	/**
	 * 领用责任人
	 */
	@ApiModelProperty(value = "领用责任人")
	private String receiveUseName;
	/**
	 * 领用责任人身份证号
	 */
	@ApiModelProperty(value = "领用责任人身份证号")
	private String receiveUseCard;
	/**
	 * 领用责任人联系方式
	 */
	@ApiModelProperty(value = "领用责任人联系方式")
	private String receiveUsePhone;
	/**
	 * 是否临时使用 0 是 1 否
	 */
	@ApiModelProperty(value = "是否临时使用 0 是 1 否")
	private Integer temporaryType;
	/**
	 * 临时使用开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "临时使用开始时间")
	private Date temporaryStartTime;
	/**
	 * 临时使用结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "临时使用结束时间")
	private Date temporaryEndTime;
	/**
	 * 机房id
	 */
	@ApiModelProperty(value = "传机房编号")
	private String roomId;
	/**
	 * 机房名称
	 */
	@ApiModelProperty(value = "机房名称")
	private String roomName;
	/**
	 * 机柜id
	 */
	@ApiModelProperty(value = "传机柜编号")
	private String cabinetsId;
	/**
	 * 机柜名称
	 */
	@ApiModelProperty(value = "机柜名称")
	private String cabinetsName;
	/**
	 * 设备高度
	 */
	@ApiModelProperty(value = "设备高度")
	private String deviceHeight;
	/**
	 * 设备起始高度
	 */
	@ApiModelProperty(value = "设备起始高度")
	private String deviceStartHeight;
	/**
	 * 设备终止高度
	 */
	@ApiModelProperty(value = "设备终止高度")
	private String deviceEndHeight;
	/**
	 * 网络设备用途类型
	 */
	@ApiModelProperty(value = "网络设备用途类型")
	private String networkDeviceType;
	/**
	 * 管理IP
	 */
	@ApiModelProperty(value = "管理IP")
	private String manageIp;
	/**
	 * 管理用户
	 */
	@ApiModelProperty(value = "管理用户")
	private String manageUser;
	/**
	 * 管理密码
	 */
	@ApiModelProperty(value = "管理密码")
	private String managePassword;
	/**
	 * 交换机ip
	 */
	@ApiModelProperty(value = "交换机ip")
	private String switchesIp;
	/**
	 * 交换机密码
	 */
	@ApiModelProperty(value = "交换机密码")
	private String switchesPassword;
	/**
	 * SNMP版本号
	 */
	@ApiModelProperty(value = "SNMP版本号")
	private String snmpVersion;
	/**
	 * SNMP读字符串
	 */
	@ApiModelProperty(value = "SNMP读字符串")
	private String snmpRead;
	/**
	 * SNMP写字符串
	 */
	@ApiModelProperty(value = "SNMP写字符串")
	private String snmpWrite;
	/**
	 * 工作Vlan
	 */
	@ApiModelProperty(value = "工作Vlan")
	private String vlanNumber;
	/**
	 * 配置密码
	 */
	@ApiModelProperty(value = "配置密码")
	private String allocationPassword;
	/**
	 * 用途
	 */
	@ApiModelProperty(value = "用途")
	private String purpose;
	/**
	 * 旧设备归还状态
	 */
	@ApiModelProperty(value = "旧设备归还状态")
	private String returnDeviceStatus;
	/**
	 * 旧设备归还仓库
	 */
	@ApiModelProperty(value = "旧设备归还仓库")
	private String returnWarehouse;
	/**
	 * 旧设备归还仓库名称
	 */
	@ApiModelProperty(value = "旧设备归还仓库名称")
	private String returnWarehouseName;
	/**
	 * 旧设备归还位置
	 */
	@ApiModelProperty(value = "旧设备归还位置")
	private String returnAddress;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "制造商")
	private String maker;

	@ApiModelProperty(value = "制造商名称")
	private String makerName;

	@ApiModelProperty(value = "端口数量")
	private String portsCount;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "出厂日期")
	private Date factoryDate;

	@ApiModelProperty(value = "采购方式")
	private String procureTypeCode;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "投运日期")
	private Date oprtDate;

	@ApiModelProperty(value = "责任人ISC账号字段")
	private String receiveDutyIscAccount;

	@ApiModelProperty(value = "产权单位")
	private String ownerUnit;

	@ApiModelProperty(value = "产权单位编码")
	private String ownerUnitCode;

	@ApiModelProperty(value = "产权部门")
	private String propertyDept;

	@ApiModelProperty(value = "产权部门编码")
	private String propertyDeptCode;

	@ApiModelProperty(value = "电压等级名称")
	private String voltageLevel;

	@ApiModelProperty(value = "电压等级编码")
	private String voltageLevelCode;

	@ApiModelProperty(value = "操作系统版本号")
	private String osVersion;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "首次投运日期")
	private Date oprtDateFirst;

	@ApiModelProperty(value = "硬盘类型")
	private String hardDiskType;

	@ApiModelProperty(value = "主备属性")
	private String standbyAttr;

	@ApiModelProperty(value = "所属安全边界")
	private String securityBoundary;

	@ApiModelProperty(value = "内存大小")
	private String memSize;

	@ApiModelProperty(value = "额定功率")
	private String ratedPower;

	@ApiModelProperty(value = "电源模块")
	private String powerModel;

	@ApiModelProperty(value = "是否纳入云管")
	private String isCloudMange;

	@ApiModelProperty(value = "主机设备用途类型")
	private String serverUseToType;

	@ApiModelProperty(value = "硬盘容量")
	private String hardDiskCapability;

	@ApiModelProperty(value = "pdu额定功率")
	private String pduRatedPower;

	@ApiModelProperty(value = "pdu运行功率")
	private String pduOperatePower;

	@ApiModelProperty(value = "操作系统发行版本")
	private String osIssueVersion;

}
