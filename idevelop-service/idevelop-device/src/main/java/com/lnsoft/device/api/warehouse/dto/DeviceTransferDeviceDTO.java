package com.lnsoft.device.api.warehouse.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 设备转资设备
 *
 * @author hujia
 * @since 2024-03-04
 */
@Data
@ApiModel(value = "DeviceTransferDeviceDTO", description = "设备转资设备")
public class DeviceTransferDeviceDTO {

	@ExcelIgnore
	@ApiModelProperty(value = "序号")
	private Integer index;

	@ExcelProperty(value = "设备编码")
	@ApiModelProperty("设备编码")
	private String deviceCode;

	@ExcelProperty(value = "ERP资产编码")
	@ApiModelProperty("ERP资产编码")
	private String erpAssetCode;

	@ExcelProperty(value = "ERP台账编号")
	@ApiModelProperty("ERP台账编号")
	private String erpAccountCode;

	@ExcelIgnore
	@ApiModelProperty("ERP同步状态")
	private String erpStatus;

	@ExcelProperty(value = "设备名称")
	@ApiModelProperty("设备名称")
	private String deviceName;

	@ExcelProperty(value = "资产信息")
	@ApiModelProperty("资产信息")
	private String deviceAssetInfo;

	@ExcelProperty(value = "硬件配置")
	@ApiModelProperty("硬件配置")
	private String deviceHardwareConfig;

	@ExcelProperty(value = "设备状态")
	@ApiModelProperty("设备状态")
	private String deviceStatus;

	@ExcelProperty(value = "功能位置")
	@ApiModelProperty("功能位置")
	private String funLocation;
}
