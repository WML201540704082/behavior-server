package com.lnsoft.device.api.asset.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2024/7/5 12:18
 * @Description: DevicePatentOnlineDTO
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "DevicePatentOnlineDTO", description = "DevicePatentOnlineDTO")
public class DevicePatentOnlineDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "区域编码")
	private String regionCode;

	@ApiModelProperty(value = "单位名称")
	private String regionName;
}
