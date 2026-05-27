package com.lnsoft.device.api.i6000.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2024/12/30 20:28
 * @Description: I6000SyncDetail
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class I6000SyncDetailDTO implements Serializable {

	@ApiModelProperty(value = "主键ID")
	private String UUID;

	@ApiModelProperty(value = "设备编码")
	private String deviceCode;

	@ApiModelProperty(value = "设备类型")
	private String deviceType;

	@ApiModelProperty(value = "i6000主键ID")
	@JsonProperty("CI_ID")
	private String CI_ID;

	@ApiModelProperty(value = "I6000设备分类")
	@JsonProperty("CITYPE_ID")
	private String CITYPE_ID;

	@ApiModelProperty(value = "是否进行I6000数据新增")
	private Boolean isAddI6000Detail = Boolean.FALSE;

}
