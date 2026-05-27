package com.lnsoft.device.eums;

import com.alibaba.fastjson.JSONObject;
import com.lnsoft.common.enums.IEnum;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum ModeType implements IEnum<JSONObject> {

	PAGE("page", "分页"),
	DIALOG("dialog", "查看详情");

	private final String value;
	private final String text;

	ModeType(String _value, String _text) {
		this.value = _value;
		this.text = _text;
	}

	public static String getValue(String _status) {
		for (ModeType s : ModeType.values()) {
			if (s.getValue().equals(_status)) {
				return s.getValue();
			}
		}
		return null;
	}

	public static String getText(String name) {
		for (ModeType s : ModeType.values()) {
			if (s.getValue().equals(name)) {
				return s.getText();
			}
		}
		return "";
	}

	@Override
	public List<JSONObject> getValueTextList() {
		List<JSONObject> returnList = new ArrayList<>();
		for (ModeType input : ModeType.values()) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("value", input.getValue());
			jsonObj.put("text", input.getText());
			returnList.add(jsonObj);
		}
		return returnList;
	}

}
