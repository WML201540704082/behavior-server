package com.lnsoft.device.api.warehouse.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lnsoft.device.annotation.ExcelDynamicSelectImpl;
import com.lnsoft.device.annotation.ExcelSelected;
import com.lnsoft.device.annotation.ExcelValid;
import com.lnsoft.device.annotation.ExcelValidDate;
import com.lnsoft.device.constant.CommonConstant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: 辅助设备
 * @description:
 * @author: zhangs
 * @create: 2024-03-06 10:18
 **/
@Data
@NoArgsConstructor
public class DeviceStorageListImportT106DTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelIgnore
	private String exceptionField;


	@ExcelValid(required = true)
	@ExcelProperty(index = 0, value = "标准全称")
	private String fullName;

	@ExcelSelected(ciId = "device-add-type", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelValid(required = true)
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



	@ExcelValid(required = true)
	@ExcelValidDate(message = CommonConstant.DATE_FORMAT)
	@DateTimeFormat(CommonConstant.DATE_FORMAT)
	@ExcelProperty(index = 8, value = "出厂日期")
	private String factoryDate;


	@ExcelValid(required = true)
	@ExcelValidDate(message = CommonConstant.DATE_FORMAT)
	@DateTimeFormat(CommonConstant.DATE_FORMAT)
	@ExcelProperty(index = 9, value = "服务到期时间")
	private String serviceExpDate;

	@ExcelProperty(index = 10, value = "备注")
	private String remark;
}
