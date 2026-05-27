package com.lnsoft.device.api.asset.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: xuel
 * @CreateTime: 2024/7/5 19:26
 * @Description: DevicePatentOperatingVO
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "DevicePatentOperatingVO", description = "DevicePatentOperatingVO")
public class DevicePatentOperatingVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "数量")
	private Integer number;

	private List<SubOperating> subOperatingList;


	@Data
	public static class SubOperating {

		@ApiModelProperty(value = "名称")
		private String name;

		@ApiModelProperty(value = "数量")
		private Integer number;
	}

}
