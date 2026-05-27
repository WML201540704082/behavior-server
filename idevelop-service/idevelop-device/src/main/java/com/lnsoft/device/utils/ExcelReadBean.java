package com.lnsoft.device.utils;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExcelReadBean {

	@ExcelProperty(index = 0,value = "name")
	private String name;

	@ExcelProperty(index = 1,value = "check")
	private String check;

	@ExcelProperty(index = 2,value = "key")
	private String key;

	@ExcelProperty(index = 3,value = "isRequired")
	private String isRequired;
}
