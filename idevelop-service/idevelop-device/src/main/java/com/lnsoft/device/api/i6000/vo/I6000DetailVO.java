package com.lnsoft.device.api.i6000.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author: xuel
 * @CreateTime: 2024/12/30 19:44
 * @Description: I6000DetailVO
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class I6000DetailVO implements Serializable {

	@ApiModelProperty(value = "I6000是否能返回值, 是: true, 否: false")
	private Boolean code;

	@ApiModelProperty(value = "参数code=true, 则输出这个")
	private String remake;

	@ApiModelProperty(value = "是否同步过I6000数据")
	private String isToI6000;

	@ApiModelProperty(value = "I6000主键UUID")
	private String i6000CiId;

	@ApiModelProperty(value = "信通一体化设备编码")
	private String deviceCode;

	@ApiModelProperty(value = "是否进行I6000数据新增")
	private Boolean isAddI6000Detail;

	@ApiModelProperty(value = "I6000返回值")
	private List<Map<String, Object>> i6000ResultMap;

}
