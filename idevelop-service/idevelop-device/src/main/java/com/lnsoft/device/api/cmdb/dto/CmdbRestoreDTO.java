package com.lnsoft.device.api.cmdb.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2024/10/31 11:29
 * @Description: 用于接受CMBD中ID/UUID/CiId CmdbRestoreUpdateDTO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CmdbRestoreDTO implements Serializable {

	@ApiModelProperty(value = "配置项ID")
	private Long id;

	@ApiModelProperty(value = "模型ID")
	private Long CiId;

	@ApiModelProperty(value = "配置项UUID")
	private String UUID;

	@ApiModelProperty(value = "设备编码串")
	private String deviceCode;


}
