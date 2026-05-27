package com.lnsoft.device.api.res.enums;

import com.alibaba.fastjson.JSONObject;
import com.lnsoft.common.enums.IEnum;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xuel
 * @CreateTime: 2024/3/1 12:19
 * @Description: AttrMappingType
 */
@Getter
public enum AttrMappingType implements IEnum<JSONObject> {

	ALL("ALL"),
	ATTRID("ATTRID");

	private final String value;

	AttrMappingType(String _value) {
		this.value = _value;
	}

	public static String getValue(String _value) {
		for (AttrMappingType s : AttrMappingType.values()) {
			if (s.getValue().equals(_value)) {
				return s.getValue();
			}
		}
		return null;
	}

	@Override
	public List<JSONObject> getValueTextList() {
		List<JSONObject> returnList = new ArrayList<>();
		for (AttrMappingType input : AttrMappingType.values()) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("value", input.getValue());
			returnList.add(jsonObj);
		}
		return returnList;
	}
}
