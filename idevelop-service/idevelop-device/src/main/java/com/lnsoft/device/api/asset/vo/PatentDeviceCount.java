package com.lnsoft.device.api.asset.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xyzadmin
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "PatentDeviceCount", description = "首页信创设备统计返回类")
public class PatentDeviceCount implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 分发进度
	 */
	@ApiModelProperty(value = "分发进度")
	private Double distributionProgress;
	/**
	 * 信创设备总数
	 */
	@ApiModelProperty(value = "信创设备总数")
	private Integer patentCount;
	/**
	 * 已分发数
	 */
	@ApiModelProperty(value = "已分发数")
	private Integer distributeCount;
	/**
	 * 信创替代率
	 */
	@ApiModelProperty(value = "信创替代率")
	private Double patentProbability;

}
