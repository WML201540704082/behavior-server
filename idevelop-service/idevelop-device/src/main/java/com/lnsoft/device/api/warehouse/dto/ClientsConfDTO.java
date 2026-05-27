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
public class ClientsConfDTO implements Serializable {
	private static final long serialVersionUID = -3706650723353369570L;

	private int clientId;

	@ApiModelProperty(value = "交换机IP、对应老平台的NASIP、本系统的sw_ip")
	private String ipaddr;

	@ApiModelProperty(value = "交换机密码，对应老平台的NASSECRET、本系统的sw_pass")
	private String secret;
}
