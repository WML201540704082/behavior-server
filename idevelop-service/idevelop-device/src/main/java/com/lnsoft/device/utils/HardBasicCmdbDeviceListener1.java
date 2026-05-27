package com.lnsoft.device.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.PhoneUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Head;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.tool.utils.CollectionUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.annotation.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @ClassName: HardBasicCmdbDeviceListener
 * @description: cmdb数据 导入程序
 **/
@AllArgsConstructor
@Slf4j
public class HardBasicCmdbDeviceListener1 extends AnalysisEventListener {


	private List<Map<String, Object>> dataList;

	private Map<String, Integer> fieldColumn;

	private List<String> errAddr;


	@Override
	public void invoke(Object object, AnalysisContext analysisContext) {
		initColumnIndex(analysisContext);
		List<String> error = validate(object, analysisContext);
		Map<String, Object> map = BeanUtil.beanToMap(object, false, false);
		//导入的时候 获取 解析信息
		if (CollectionUtil.isNotEmpty(error)) {
			String errorStr = error.stream().collect(Collectors.joining("! ")).replace("*", "");
			map.put("exceptionField", errorStr + "! ");
		}
		dataList.add(map);
	}

	private void initColumnIndex(AnalysisContext context) {
		Map<Integer, Head> excelFileHead = context.readSheetHolder().excelReadHeadProperty().getHeadMap();
		for (Map.Entry<Integer, Head> entry : excelFileHead.entrySet()) {
			this.fieldColumn.put(entry.getValue().getFieldName(), entry.getKey());
		}
	}

	private List<String> validate(Object object, AnalysisContext context) {
		Integer rowIndex = context.readRowHolder().getRowIndex() + 1;
		List<String> errorList = new ArrayList<>();
		Field[] fields = object.getClass().getDeclaredFields();

		// 增加判断如果为非数字化设备,不需要必填校验.
		boolean isAccessEquipment = Arrays.stream(fields).filter(field -> StringUtils.equals("isAccessEquipment", field.getName()))
			.anyMatch(field -> {
				field.setAccessible(true);
				try {
					Object fieldValue = field.get(object);
					return !"是".equals(fieldValue);
				} catch (IllegalAccessException e) {

					return Boolean.TRUE;
				}
			});

		for (Field field : fields) {
			field.setAccessible(true);
			String fieldName = field.getName();
			Object fieldValue = null;
			Boolean flag = Boolean.FALSE;
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
				errorList.add(value[0] + ":不能为空");
				errAddr.add(getExcelColumn(rowIndex, fieldName, context));
				continue;
			}
			boolean isExcelValidNumber = field.isAnnotationPresent(ExcelValidNumber.class);
			if (isExcelValidNumber && !Validator.isNumber(String.valueOf(fieldValue))) {
				String[] value = property.value();
				errorList.add(value[0] + ":不为数值");
				flag = Boolean.TRUE;
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
					flag = Boolean.TRUE;
				}
			}
			boolean isExcelValidPhone = field.isAnnotationPresent(ExcelValidPhone.class);
			if (isExcelValidPhone && !PhoneUtil.isPhone(String.valueOf(fieldValue))) {
				String[] value = property.value();
				errorList.add(value[0] + ":格式异常");
				flag = Boolean.TRUE;
			}
			if (flag) {
				errAddr.add(getExcelColumn(rowIndex, fieldName, context));
			}
			// 2024-6-21 新增 导入时 中央和家用归为其他
			boolean isDataReplace = field.isAnnotationPresent(ExcelDataReplace.class);
			if (isDataReplace) {
				String fieldValueStr = String.valueOf(fieldValue).replace("null", "");
				if (StringUtil.isNotBlank(fieldValueStr)) {
					List<String> list = Arrays.asList("中央空调", "家用空调");
					if (list.contains(fieldValueStr)) {
						try {
							field.set(object, "其他");
						} catch (IllegalAccessException e) {
							throw new ServiceException("空调类型数据转换出现异常!");
						}
					}
				}
			}
		}
		return errorList;
	}

	private String getExcelColumn(Integer rowIndex, String fieldName, AnalysisContext context) {
		Integer columnIndex = getColumnIndex(fieldName, context);
		String excelColumn = EasyExcelUtil.convertToExcelColumn(columnIndex + 1);
		return excelColumn + rowIndex;
	}


	private Integer getColumnIndex(String field, AnalysisContext context) {
		if (CollectionUtil.isNotEmpty(this.fieldColumn)) {
			return this.fieldColumn.get(field);
		}
		Map<Integer, Head> excelFileHead = context.readSheetHolder().excelReadHeadProperty().getHeadMap();
		for (Map.Entry<Integer, Head> entry : excelFileHead.entrySet()) {
			this.fieldColumn.put(entry.getValue().getFieldName(), entry.getKey());
		}
		return this.fieldColumn.get(field);
	}


	private static boolean checkDate(String fieldValue, String format) {
		String regex = "\\d{4}-\\d{2}-\\d{2}";
		if (format.equalsIgnoreCase("yyyy/MM/dd")) {
			regex = "\\d{4}/\\d{2}/\\d{2}";
		}
		return Pattern.matches(regex, fieldValue);
	}


	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {

	}
}
