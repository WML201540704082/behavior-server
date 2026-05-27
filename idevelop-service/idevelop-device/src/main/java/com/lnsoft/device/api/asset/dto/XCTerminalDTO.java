package com.lnsoft.device.api.asset.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lnsoft.device.annotation.ExcelValid;
import com.lnsoft.device.annotation.ExcelValidDate;
import com.lnsoft.device.constant.CommonConstant;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2024/5/28 14:31
 * @Description: XCTerminal
 */
@Data
public class XCTerminalDTO implements Serializable {
	private static final long serialVersionUID = -8993084197232798525L;

	@ExcelValid
	@ExcelProperty(index = 0, value = "单位(必填)")
	private String unit;

	@ExcelProperty(index = 1, value = "部门(必填)")
	private String dept;

	@ExcelValid
	@ExcelProperty(index = 2, value = "ERP资产编码(必填)")
	private String assetCodeErp;

	@ExcelValid
	@ExcelProperty(index = 3, value = "出厂序列号(必填)")
	private String sn;

	@ExcelValid
	@ExcelProperty(index = 4, value = "采购方式(必填)")
	private String procureTypeCode;

	@ExcelValid
	@ExcelProperty(index = 5, value = "服务商(必填)")
	private String service;

	@ExcelValid
	@ExcelValidDate(message = CommonConstant.DATE_FORMAT)
	@DateTimeFormat(CommonConstant.DATE_FORMAT)
	@ExcelProperty(index = 6, value = "售后服务到期时间(必填)")
	private String afterSaleExpDate;

	@ExcelValid
	@ExcelProperty(index = 7, value = "品牌(必填)")
	private String brand;

	@ExcelValid
	@ExcelProperty(index = 8, value = "系列(必填)")
	private String series;

	@ExcelValid
	@ExcelProperty(index = 9, value = "型号(必填)")
	private String deviceModel;

	@ExcelValid
	@ExcelProperty(index = 10, value = "设备类型(必填)")
	private String deviceType;

}
