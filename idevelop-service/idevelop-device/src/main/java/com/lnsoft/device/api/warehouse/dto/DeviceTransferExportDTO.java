package com.lnsoft.device.api.warehouse.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceTransferExportDTO implements Serializable {

	/**
	 * 设备编码
	 */
	@ExcelProperty("设备编码")
	private String deviceCode;
	/**
	 * erp资产编码
	 */
	@ExcelProperty("erp资产编码")
	private String erpAssetCode;
	/**
	 * erp编码状态
	 */
	@ExcelProperty("erp编码状态")
	private String erpAssetStatus;
	/**
	 * 资产信息
	 */
	@ExcelProperty("资产信息")
	private String deviceAssetInfo;
	/**
	 * 功能位置
	 */
	@ExcelProperty("功能位置")
	private String funLocation;
	/**
	 * 设备状态
	 */
	@ExcelProperty("设备状态")
	private Integer deviceStatus;
	/**
	 * 设备名称
	 */
	@ExcelProperty("设备名称")
	private String deviceName;
	/**
	 * 设备分类
	 */
	@ExcelProperty("设备分类")
	private String deviceCategory;
	/**
	 * 设备类型
	 */
	@ExcelProperty("设备类型")
	private String deviceType;
	/**
	 * 所在仓库
	 */
	@ExcelProperty("所在仓库")
	private String warehouse;
	/**
	 * 备注
	 */
	@ExcelProperty("备注")
	private String remark;
}
