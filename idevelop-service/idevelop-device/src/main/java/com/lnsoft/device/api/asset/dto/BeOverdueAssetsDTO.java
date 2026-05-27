package com.lnsoft.device.api.asset.dto;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 逾期资产 导出实体
 *
 * @author hujia
 * @since 2024-03-01
 */
@Data
@ApiModel(value = "beOverdue", description = "逾期资产导出实体")
public class BeOverdueAssetsDTO {

	@ExcelIgnore
	@ApiModelProperty(value = "每页大小")
	private Integer size;

	@ExcelIgnore
	@ApiModelProperty(value = "当前页")
	private Integer current;

	@ExcelProperty(value = "设备编码")
	@ApiModelProperty("设备编码")
	private String deviceCode;

	@ExcelProperty(value = "设备名称")
	@ApiModelProperty("设备名称")
	private String deviceName;

	@ExcelProperty(value = "设备类型")
	@ApiModelProperty("设备类型")
	private String deviceTypeName;

	@ExcelProperty(value = "首次投运日期")
	@ApiModelProperty("首次投运日期")
	private String oprtDateFirst;

	@ExcelProperty(value = "投运年限")
	@ApiModelProperty("投运年限")
	private String useAge;

	@ExcelProperty(value = "设备状态")
	@ApiModelProperty("设备状态")
	private String deviceStatus;

	@ExcelProperty(value = "售后服务到期日期")
	@ApiModelProperty("售后服务到期日期")
	private String afterSaleExpDate;

	@ExcelProperty(value = "售后状态")
	@ApiModelProperty("售后状态")
	private String afterStatus;

	@ExcelProperty(value = "设备来源")
	@ApiModelProperty("设备来源")
	private String deviceSource;

	@ExcelProperty(value = "领用单位")
	@ApiModelProperty("领用单位")
	private String receiveUnitName;

	@ExcelProperty(value = "领用部门")
	@ApiModelProperty("领用部门")
	private String receiveDeptName;


}
