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
@ApiModel(value = "OnlineVO", description = "OnlineVO")
public class OnlineVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "时间")
	private List<String> time;

	@ApiModelProperty(value = "数量")
	private List<Integer> number;



}
