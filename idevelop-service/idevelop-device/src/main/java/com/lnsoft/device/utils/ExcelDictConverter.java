package com.lnsoft.device.utils;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.lnsoft.core.tool.utils.SpringUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.annotation.ExcelDictExport;
import com.lnsoft.device.annotation.ExcelDictImport;
import com.lnsoft.system.feign.IDictClient;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * @ClassName: ExcelDictConverter
 * @description:
 * @author: zhangs
 * @create: 2024-02-29 14:54
 **/
@Slf4j
public class ExcelDictConverter implements Converter<String> {

	private IDictClient dictClient;


	{
		dictClient = SpringUtil.getBean(IDictClient.class);
	}


	@Override
	public Class supportJavaTypeKey() {
		return null;
	}

	@Override
	public CellDataTypeEnum supportExcelTypeKey() {
		return null;
	}

	@Override
	public String convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
		Field field = excelContentProperty.getField();
		ExcelDictImport excel = field.getAnnotation(ExcelDictImport.class);
		String dictType = excel.type();
		String value = cellData.getStringValue();
		if (StringUtil.isBlank(value)) {
			return value;
		}
		String key = null;
		try {
			key = dictClient.getKey(dictType, value).getData();
			if (StringUtil.isBlank(key)) {
				return value;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return key;
	}

	@Override
	public CellData convertToExcelData(String dictValue, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
		Field field = excelContentProperty.getField();
		ExcelDictExport excel = field.getAnnotation(ExcelDictExport.class);
		String dictType = excel.type();

		if (StringUtil.isBlank(dictValue)) {
			return new CellData(dictValue);
		}

		String value = dictClient.getValue(dictType, Integer.valueOf(dictValue)).getData();

		if (StringUtil.isBlank(value)) {
			return new CellData(dictValue);
		}

		return new CellData(value);
	}
}
