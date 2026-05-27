package com.lnsoft.device.api.asset.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 老旧设备查询条件类
 * @author xyzadmin
 */
@Data
public class OverdueAssetSearchDTO {
	/**
	 * 设备分类
	 */
	@ApiModelProperty(value = "设备分类")
	private String deviceCategoryCode;
	/**
	 * 投运年限
	 */
	@ApiModelProperty(value = "投运年限")
	private String useAge;
	/**
	 * 设备类型
	 */
	@ApiModelProperty(value = "设备类型")
	private String deviceTypeCode;

}
