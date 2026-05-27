package com.lnsoft.device.api.asset.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 老旧设备查询列表条件类
 * @author xyzadmin
 */
@Data
public class OverdueAssetSearchListDTO {
	/**
	 * 设备分类
	 */
	@ApiModelProperty(value = "设备分类")
	private String deviceCategoryCode;
	/**
	 * 设备类型
	 */
	@ApiModelProperty(value = "设备类型")
	private String deviceTypeCode;
	/**
	 * 设备来源
	 */
	@ApiModelProperty(value = "设备来源")
	private String deviceSourceCode;
	/**
	 * 设备状态
	 */
	@ApiModelProperty(value = "设备状态")
	private String deviceStatusCode;
	/**
	 * 所属单位（领用单位）
	 */
	@ApiModelProperty(value = "所属单位（领用单位）")
	private String receiveUnitCode;
	/**
	 * 出厂序列号
	 */
	@ApiModelProperty(value = "出厂序列号")
	private String sn;
	/**
	 * 投运年限
	 */
	@ApiModelProperty(value = "投运年限")
	private String useAge;


}
