package com.lnsoft.device.api.safeaccess.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: SafeaccessSwitcheSaveDTO
 * @description:
 * @author: zhangs
 * @create: 2024-03-19 15:52
 **/
@Data
public class SafeaccessSwitcheSaveDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "交换机标签")
	private String swName;
	@ApiModelProperty(value = "厂家代码")
	private String swFirm;
	@ApiModelProperty(value = "型号")
	private String swModel;
	@ApiModelProperty(value = "安装位置")
	private String swWhere;
	@ApiModelProperty(value = "用途")
	private String swPurpose;
	@ApiModelProperty(value = "所属单位")
	private String company;
	@ApiModelProperty(value = "交换机IP")
	private String swIp;
	@ApiModelProperty(value = "交换机密码")
	private String swPass;
	@ApiModelProperty(value = "配置文件备份")
	private String configBak;
	@ApiModelProperty(value = "isAdmin")
	private String isAdmin;
	@ApiModelProperty(value = "网络层次：核心/汇聚/接入")
	private String is3;
	@ApiModelProperty(value = "工作状态：停用/运行")
	private String swState;
	@ApiModelProperty(value = "端口数量")
	private String portsCount;
	@ApiModelProperty(value = "认证方式：802.1x、MAC")
	private String authConfig;
	@ApiModelProperty(value = "认证状态：0未认证1已认证")
	private String authState;
	@ApiModelProperty(value = "shortname")
	private String oldShortname;
	@ApiModelProperty(value = "工作vlans号")
	private String vlans;
	@ApiModelProperty(value = "管理ip")
	private String telIp;
	@ApiModelProperty(value = "管理用户")
	private String telUser;
	@ApiModelProperty(value = "管理密码")
	private String telPass;
	@ApiModelProperty(value = "配置密码")
	private String configPass;
	@ApiModelProperty(value = "SNMP查询密码")
	private String snmpQueryPass;
	@ApiModelProperty(value = "SNMP管理密码")
	private String snmpManagePass;
	@ApiModelProperty(value = "SNMP版本号")
	private String snmpVersion;
	@ApiModelProperty(value = "设备编码")
	private String deviceCode;
	@ApiModelProperty(value = "SNMP读字符串")
	private String snmpReadStr;
	@ApiModelProperty(value = "SNMP写字符串")
	private String snmpWriteStr;
	@ApiModelProperty(value = "录入人")
	private String fillMan;
	@ApiModelProperty(value = "录入时间")
	private String fillDate;
	@ApiModelProperty(value = "交换机是否认证 0否1是")
	private String isAccessSwitch;
	@ApiModelProperty(value = "备注")
	private String remark;
	@ApiModelProperty("子网id")
	private String subnetId;

}
