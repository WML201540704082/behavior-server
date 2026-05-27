package com.lnsoft.device.api.warehouse.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SwitcherDeviceListDTO implements Serializable {
	private static final long serialVersionUID = 9152468444628750056L;

	@ApiModelProperty(value = "设备类型")
	private String deviceCategory;

	@ApiModelProperty(value = "设备类型")
	private String deviceType;

	@ApiModelProperty(value = "设备ip")
	private String deviceIp;

	@ApiModelProperty(value = "设备Mac")
	private String deviceMac;

	@ApiModelProperty(value = "认证账号")
	private String authAccount;

	@ApiModelProperty(value = "认证账号")
	private String authPassword;

	@ApiModelProperty(value = "所属子网id")
	private String deviceSubnet;

	@ApiModelProperty(value = "交换机ip")
	private String switchesIp;

	@ApiModelProperty(value = "交换机密码")
	private String switchesPassword;

	@ApiModelProperty(value = "设备编码")
	private String deviceCode;

	@ApiModelProperty(value = "子网地址")
	private String subnet;

	@ApiModelProperty(value = "子网掩码")
	private String netmask;

	@ApiModelProperty(value = "广播地址")
	private String optionBroadcastAddress;

	@ApiModelProperty(value = "子网网关")
	private String optionRouters;

	@ApiModelProperty(value = "子网掩码")
	private String optionSubnetMask;

	@ApiModelProperty(value = "首选DNS服务器，多个逗号拼接")
	private String optionDomainNameServers;

	@ApiModelProperty(value = "DHCP生成周期(秒)（默认1小时 3600）")
	private Integer defaultLeaseTime;

	@ApiModelProperty(value = "DHCP生成周期(秒) 最大 默认24小时 86400")
	private int maxLeaseTime;

	@ApiModelProperty(value = "认证方式: 是否启用802.1X接入认证（0 不认证，1 802.1X，2 mac）")
	private String is802;
}
