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
public class HostConfDTO implements Serializable {
	private static final long serialVersionUID = -897933087556668312L;

	private int hostId;

	@ApiModelProperty(value = "MAC地址")
	private String hardwareEthernet;

	@ApiModelProperty(value = "IP")
	private String fixedAddress;
}
