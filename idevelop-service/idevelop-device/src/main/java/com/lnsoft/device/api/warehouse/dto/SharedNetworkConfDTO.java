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
public class SharedNetworkConfDTO implements Serializable {
	private static final long serialVersionUID = 868065631225320822L;

	private int sharedNetwork;

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
	private int defaultLeaseTime;

	@ApiModelProperty(value = "DHCP生成周期(秒) 最大 默认24小时 86400")
	private int maxLeaseTime;
}
