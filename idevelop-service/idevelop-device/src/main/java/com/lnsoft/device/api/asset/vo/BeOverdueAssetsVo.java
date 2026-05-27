package com.lnsoft.device.api.asset.vo;


import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 逾期资产 查询条件
 *
 * @author hujia
 * @since 2024-03-01
 */
@Data
@ApiModel(value = "beOverdue", description = "逾期资产查询条件")
public class BeOverdueAssetsVo extends BaseEntity {

	@ApiModelProperty(value = "每页大小")
	private Integer size;

	@ApiModelProperty(value = "当前页")
	private Integer current;

	@ApiModelProperty("所属领用单位")
	private String receiveUnit;

	@ApiModelProperty("所属领用部门")
	private String receiveDept;

	@ApiModelProperty("设备分类")
	private String deviceCategory;

	@ApiModelProperty("设备来源")
	private String deviceSource;

	@ApiModelProperty("设备类型")
	private String deviceType;

	@ApiModelProperty("售后状态")
	private String afterStatus;

	@ApiModelProperty("设备状态")
	private String deviceStatus;

	@ApiModelProperty("投运年限")
	private String useAge;

	@ApiModelProperty("设备编码")
	private String deviceCode;

	@ApiModelProperty("设备名称")
	private String deviceName;


	@ApiModelProperty("设备分类编码")
	private String deviceCategoryCode;

	@ApiModelProperty("设备类型编码")
	private String deviceTypeCode;

	@ApiModelProperty("老旧设备标识")
	private String oldMark;

	@ApiModelProperty("出厂序列号")
	private String sn;

	@ApiModelProperty("投运年限")
	private String age;
	/**
	 * 起始投运年限
	 */
	@ApiModelProperty(value = "起始投运年限")
	private Integer startUseAge;
	/**
	 * 终止投运年限
	 */
	@ApiModelProperty(value = "终止投运年限")
	private Integer endUseAge;


}
