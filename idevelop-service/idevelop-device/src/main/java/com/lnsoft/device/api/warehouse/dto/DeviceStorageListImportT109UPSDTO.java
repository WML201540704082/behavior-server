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
 * @ClassName: 基础设施
 * @description:
 * @author: zhangs
 * @create: 2024-03-06 10:18
 **/
@Data
@NoArgsConstructor
public class DeviceStorageListImportT109UPSDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelIgnore
	private String exceptionField;


	@ExcelValid(required = true)
	@ExcelProperty(index = 0, value = "标准全称")
	private String fullName;

	@ExcelValid(required = true)
	@ExcelSelected(ciId = "device-add-type", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 1, value = "设备增加方式")
	private String deviceAddType;


	@ExcelValid(required = true)
	@ExcelSelected(ciId = "procure-type-code", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 2, value = "采购方式")
	private String procureType;


	@ExcelValid(required = true)
	@ExcelProperty(index = 3, value = "出厂序列号")
	private String sn;

	@ExcelValid(required = true)
	@ExcelSelected(ciId = "maker", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 4, value = "制造商")
	private String maker;

	@ExcelValid(required = true)
	@ExcelSelected(ciId = "brand", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 5, value = "品牌")
	private String brand;


	@ExcelValid(required = true)
	@ExcelSelected(ciId = "series", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 6, value = "系列")
	private String series;


	@ExcelValid(required = true)
	@ExcelSelected(ciId = "model", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 7, value = "型号")
	private String deviceModel;


	@ExcelCorpSelected
	@ExcelValid(required = true)
	@ExcelProperty(index = 8, value = "产权单位")
	private String ownerUnit;


	@ExcelDeptSelected
	@ExcelValid(required = true)
	@ExcelProperty(index = 9, value = "产权部门")
	private String propertyDept;


	@ExcelValid(required = true)
	@ExcelValidDate(message = CommonConstant.DATE_FORMAT)
	@DateTimeFormat(CommonConstant.DATE_FORMAT)
	@ExcelProperty(index = 10, value = "出厂日期")
	private String factoryDate;


	@ExcelValid(required = true)
	@ExcelValidDate(message = CommonConstant.DATE_FORMAT)
	@DateTimeFormat(CommonConstant.DATE_FORMAT)
	@ExcelProperty(index = 11, value = "服务到期日期")
	private String serviceExpDate;


	@ExcelValid(required = true)
	@ExcelProperty(index = 12, value = "UPS容量(KVA)")
	private String upsCapacity;


	@ExcelValid(required = true)
	@ExcelProperty(index = 13, value = "电源负载(VA)")
	private String powerLoad;


	@ExcelProperty(index = 14, value = "备注")
	private String remark;
}
