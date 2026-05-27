package com.lnsoft.device.eums;

import com.alibaba.fastjson.JSONObject;
import com.lnsoft.common.enums.IEnum;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum TripleTypeEnum implements IEnum<JSONObject> {

	CMDB("CMDB", "CMDB系统"),
	I6000("I6000", "I6000系统"),
	ERP("ERP", "ERP系统"),
	UNKNOWN("未知三方系统", "未知三方系统")
	;

	private final String value;
	private final String text;

	TripleTypeEnum(String _value, String _text) {
		this.value = _value;
		this.text = _text;
	}

	public static String getValue(String _status) {
		for (TripleTypeEnum s : TripleTypeEnum.values()) {
			if (s.getValue().equals(_status)) {
				return s.getValue();
			}
		}
		return null;
	}

	public static String getText(String name) {
		for (TripleTypeEnum s : TripleTypeEnum.values()) {
			if (s.getValue().equals(name)) {
				return s.getText();
			}
		}
		return "";
	}

	@Override
	public List<JSONObject> getValueTextList() {
		List<JSONObject> returnList = new ArrayList<>();
		for (TripleTypeEnum input : TripleTypeEnum.values()) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("value", input.getValue());
			jsonObj.put("text", input.getText());
			returnList.add(jsonObj);
		}
		return returnList;
	}
}
