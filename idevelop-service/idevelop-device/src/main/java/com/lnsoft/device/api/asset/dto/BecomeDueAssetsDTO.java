package com.lnsoft.device.api.asset.dto;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 转资到期 导出实体
 *
 * @author hujia
 * @since 2024-03-01
 */
@Data
@ApiModel(value = "becomeDueAssets", description = "转资到期导出实体")
public class BecomeDueAssetsDTO  {

	@ExcelIgnore
	@ApiModelProperty(value = "每页大小")
	private Integer size;

	@ExcelIgnore
	@ApiModelProperty(value = "当前页")
	private Integer current;

	@ExcelProperty(value = "WBS项目")
	@ApiModelProperty("WBS项目")
	private String wbsElementName;

	@ExcelProperty(value = "WBS元素")
	@ApiModelProperty("WBS元素")
	private String wbsElement;

	@ExcelProperty(value = "设备编码")
	@ApiModelProperty("设备编码")
	private String deviceCode;

	@ExcelProperty(value = "设备名称")
	@ApiModelProperty("设备名称")
	private String deviceName;

	@ExcelProperty(value = "设备分类")
	@ApiModelProperty("设备分类")
	private String deviceCategoryName;

	@ExcelProperty(value = "设备类型")
	@ApiModelProperty("设备类型")
	private String deviceTypeName;

	@ExcelProperty(value = "入库时间")
	@ApiModelProperty("入库时间")
	private String createTimes;

	@ExcelProperty(value = "剩余转资操作天数")
	@ApiModelProperty("剩余转资操作天数")
	private String afterSaleExpDate;


}
