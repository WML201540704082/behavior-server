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
public class ServerInfoDTO implements Serializable {

	private static final long serialVersionUID = -572151801410794618L;
	private String url;

	@ApiModelProperty(value = "省市区编码")
	private String code;

	@ApiModelProperty(value = "1省 2市 3县")
	private String type;
}
