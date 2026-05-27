package com.lnsoft.device.api.asset.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author xyzadmin
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "OldTrend", description = "设备老旧趋势图统计返回类")
public class OldTrend implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 设备分类
	 */
	@ApiModelProperty(value = "设备分类")
	private Map<String,List<Integer>> dataMap;
	/**
	 * 统计年份
	 */
	@ApiModelProperty(value = "统计年份")
	private List<String> year;

}
