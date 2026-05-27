package com.lnsoft.device.api.asset.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 转资到期 查询条件
 *
 * @author hujia
 * @since 2024-02-21
 */
@Data
@ApiModel(value = "becomeDueAssets", description = "转资到期查询条件")
public class BecomeDueAssetsVo extends BaseEntity {

	@ApiModelProperty(value = "每页大小")
	private Integer size;

	@ApiModelProperty(value = "当前页")
	private Integer current;

	@ApiModelProperty("设备分类")
	private String deviceCategory;
	@ApiModelProperty("设备类型")
	private String deviceType;
	@ApiModelProperty("设备状态")
	private String deviceStatus;
	@ApiModelProperty("设备编码")
	private String deviceCode;
	@ApiModelProperty("设备名称")
	private String deviceName;

	@ApiModelProperty("WBS元素")
	private String wbsElement;
	@ApiModelProperty("WBS项目")
	private String wbsElementName;

	@ApiModelProperty("模型ID")
	private String ciId;

	@ApiModelProperty("开始时间")
	@TableField(exist = false)
	private String minDate;
	@ApiModelProperty("结束时间")
	@TableField(exist = false)
	private String maxDate;


}
