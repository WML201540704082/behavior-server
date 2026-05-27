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
 * @author xyzadmin
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "DeviceCount", description = "首页台账概览统计返回类")
public class OldAgeVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 设备分类
	 */
	@ApiModelProperty(value = "设备分类")
	private String deviceType;
	/**
	 * 数值
	 */
	@ApiModelProperty(value = "数值")
	private List<Integer> valueList;

}
