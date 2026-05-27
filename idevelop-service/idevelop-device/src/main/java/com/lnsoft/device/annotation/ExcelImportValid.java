package com.lnsoft.device.annotation;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.PhoneUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @ClassName: ExcelImportVaild
 * @description:
 * @author: zhangs
 * @create: 2024-03-22 09:48
 **/
@Slf4j
public class ExcelImportValid {

	public static List<String> valid(Object object) {
		List<String> errorList = new ArrayList<>();
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Object fieldValue = null;
			try {
				fieldValue = field.get(object);

			} catch (IllegalAccessException e) {
				log.error(e.getMessage());
			}
			ExcelProperty property = field.getAnnotation(ExcelProperty.class);
			// 校验 必填字段
			ExcelValid excelValid = field.getAnnotation(ExcelValid.class);
			boolean isExcelValid = field.isAnnotationPresent(ExcelValid.class);
			if (isExcelValid && Objects.isNull(fieldValue) && excelValid.required()) {
				String[] value = property.value();
				log.error("ExcelImportValid-property-value：{}", value);
				errorList.add(value[0] + ":不能为空");
				continue;
			}
			boolean isExcelValidNumber = field.isAnnotationPresent(ExcelValidNumber.class);
			if (isExcelValidNumber && !Validator.isNumber(String.valueOf(fieldValue))) {
				String[] value = property.value();
				errorList.add(value[0] + ":不为数值");
			}
			ExcelValidDate excelValidDate = field.getAnnotation(ExcelValidDate.class);
			if (excelValidDate != null) {
				String format = excelValidDate.message();
				String[] value = property.value();
				if (Objects.isNull(fieldValue) && excelValidDate.required()) {
					errorList.add(value[0] + ":内容不能为空");
				}
				if (!Objects.isNull(fieldValue) && !checkDate(String.valueOf(fieldValue), format)) {
					errorList.add(value[0] + ":格式异常(参考格式:xxxx-xx-xx)");
				}
			}
			boolean isExcelValidPhone = field.isAnnotationPresent(ExcelValidPhone.class);
			if (isExcelValidPhone && !PhoneUtil.isPhone(String.valueOf(fieldValue))) {
				String[] value = property.value();
				errorList.add(value[0] + ":格式异常");
			}

		}
		return errorList;
	}

	private static boolean checkDate(String fieldValue, String format) {
		String regex = "\\d{4}-\\d{2}-\\d{2}";
		if (format.equalsIgnoreCase("yyyy/MM/dd")) {
			regex = "\\d{4}/\\d{2}/\\d{2}";
		}
		return Pattern.matches(regex, fieldValue);
	}

}
