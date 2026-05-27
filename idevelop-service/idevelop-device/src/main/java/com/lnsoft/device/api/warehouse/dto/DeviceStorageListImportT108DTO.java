package com.lnsoft.device.api.warehouse.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lnsoft.device.annotation.*;
import com.lnsoft.device.constant.CommonConstant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: 备品备件
 * @description:
 * @author: zhangs
 * @create: 2024-03-06 10:18
 **/
@Data
@NoArgsConstructor
public class DeviceStorageListImportT108DTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelIgnore
	private String exceptionField;

	@ExcelValid(required = true)
	@ExcelProperty(index = 0, value = "出厂序列号(必填)")
	private String sn;

	@ExcelProperty(index = 1, value = "设备名称")
	private String deviceName;

	@ExcelValid(required = true)
	@ExcelSelected(ciId = "brand", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 2, value = "品牌(必填)")
	private String brand;

	@ExcelValid(required = true)
	@ExcelSelected(ciId = "series", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 3, value = "系列(必填)")
	private String series;

	@ExcelValid(required = true)
	@ExcelSelected(ciId = "model", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 4, value = "型号(必填)")
	private String deviceModel;

	@ExcelValid(required = true)
	@ExcelProperty(index = 5, value = "CPU型号(必填)")
	private String cpuModel;

	@ExcelValid(required = true)
	@ExcelValidDate(message = CommonConstant.DATE_FORMAT)
	@DateTimeFormat(CommonConstant.DATE_FORMAT)
	@ExcelProperty(index = 6, value = "出厂日期(必填)")
	private String factoryDate;

	@ExcelValid(required = true)
	@ExcelProperty(index = 7, value = "服务商(必填)")
	private String serviceName;

	@ExcelValid(required = true)
	@ExcelValidPhone
	@ExcelProperty(index = 8, value = "服务商联系电话(必填)")
	private String serviceTel;

	@ExcelValid(required = true)
	@ExcelProperty(index = 9, value = "服务商联系人(必填)")
	private String serviceContacts;

	@ExcelValid(required = true)
	@ExcelSelected(ciId = "service-level", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 10, value = "服务级别(必填)")
	private String serviceLevel;

	@ExcelValid(required = true)
	@ExcelValidDate(message = CommonConstant.DATE_FORMAT)
	@DateTimeFormat(CommonConstant.DATE_FORMAT)
	@ExcelProperty(index = 11, value = "服务开始日期(必填)")
	private String afterSaleBeginDate;

	@ExcelValid(required = true)
	@ExcelValidDate(message = CommonConstant.DATE_FORMAT)
	@DateTimeFormat(CommonConstant.DATE_FORMAT)
	@ExcelProperty(index = 12, value = "服务到期时间(必填)")
	private String serviceExpDate;

	@ExcelValid(required = true)
	@ExcelValidDate(message = CommonConstant.DATE_FORMAT)
	@DateTimeFormat(CommonConstant.DATE_FORMAT)
	@ExcelProperty(index = 13, value = "售后服务到期时间(必填)")
	private String afterSaleExpDate;

	@ExcelValid(required = true)
	@ExcelSelected(ciId = "spare-parts-type", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 14, value = "备品备件类型(必填)")
	private String sparePartsType;

	@ExcelProperty(index = 15, value = "备注")
	private String remark;

	@ExcelValid(required = true)
	@ExcelSelected(ciId = "maker", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 16, value = "制造商(必填)")
	private String maker;

	@ExcelValid(required = true)
	@ExcelProperty(index = 17, value = "标准全称(必填)")
	private String fullName;
}
