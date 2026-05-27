package com.lnsoft.device.utils;


import com.lnsoft.device.api.operation.annotation.ForUpdate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author xyzadmin
 */
@Slf4j
public class ChangeUtils {
	public static <T> HashMap<String, List<Object>> getChangedFields(T oldBean,T newBean){
		Field[] fields = newBean.getClass().getDeclaredFields();
		HashMap<String, List<Object>> map = new HashMap<>();
		for (Field field : fields) {
			field.setAccessible(true);
			if (!field.isAnnotationPresent(ForUpdate.class)){
				continue;
			}
			try {
				Object oldValue = field.get(oldBean);
				Object newValue = field.get(newBean);
				if (oldValue==null){
					oldValue = "";
				}
				if (newValue==null){
					newValue = "";
				}
				String fieldName = field.getAnnotation(ForUpdate.class).fieldName();
				if (!Objects.equals(newValue,oldValue) || StringUtils.equals("IP地址" ,fieldName)) {
					ArrayList<Object> list = new ArrayList<>();
					list.add(oldValue.toString());
					list.add(newValue.toString());
					map.put(fieldName,list);
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}

		}
		return map;
	}
}
