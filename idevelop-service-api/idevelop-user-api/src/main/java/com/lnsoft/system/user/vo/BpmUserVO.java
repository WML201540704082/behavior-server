package com.lnsoft.system.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BpmUserVO implements Serializable {
	private static final long serialVersionUID = 2787387932634541179L;

	@ApiModelProperty(value = "id")
	private Long id;

	@ApiModelProperty(value = "昵称")
	private String name;

	@ApiModelProperty(value = "真名")
	private String realName;

	@ApiModelProperty(value = "手机号")
	private String phone;
}
