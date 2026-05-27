package com.lnsoft.device.api.asset.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 老旧设备数据返回类
 * @author xyzadmin
 */
@Data
public class OverdueAssetVO {
	/**
	 * 总数量
	 */
	@ApiModelProperty(value = "总数量")
	private Integer devSize;
	/**
	 * 设备分类
	 */
	@ApiModelProperty(value = "设备分类")
	private String deviceCategory;
	/**
	 * 设备分类编码
	 */
	@ApiModelProperty(value = "设备分类编码")
	private String deviceCategoryCode;
	/**
	 * 设备类型
	 */
	@ApiModelProperty(value = "设备分类")
	private String deviceType;
	/**
	 * 设备类型
	 */
	@ApiModelProperty(value = "设备类型")
	private String deviceTypeCode;
	/**
	 * 老旧设备标识
	 */
	@ApiModelProperty(value = "老旧设备标识")
	private String oldMark;
	/**
	 * 区域
	 */
	@ApiModelProperty(value = "区域")
	private String regionCode;
	/**
	 * 单位
	 */
	@ApiModelProperty(value = "单位")
	private String dept;
	/**
	 * 投运年限
	 */
	@ApiModelProperty(value = "投运年限")
	private String useAge;
	/**
	 * 最后统计时间
	 */
	@ApiModelProperty(value = "最后统计时间")
	private Date lastUpdateTime;

}
