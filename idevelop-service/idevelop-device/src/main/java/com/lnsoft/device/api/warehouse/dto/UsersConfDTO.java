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
public class UsersConfDTO implements Serializable {
	private static final long serialVersionUID = -7989820573697067674L;

	@ApiModelProperty(value = "认证账号")
	private String userId;

	@ApiModelProperty(value = "认证密码")
	private String cleartextPassword;

	@ApiModelProperty(value = "MAC地址")
	private String callingStationId;

	private String replyMessage;

	private int sessionTimeout;

	private String terminationAction;

	private String tunnelType;

	private String tunnelMediumType;

	@ApiModelProperty(value = "vlanID（子网管理里面的）")
	private String tunnelPrivateGroupID;
}
