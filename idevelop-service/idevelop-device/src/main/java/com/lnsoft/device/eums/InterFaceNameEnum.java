package com.lnsoft.device.eums;

import com.alibaba.fastjson.JSONObject;
import com.lnsoft.common.enums.IEnum;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum InterFaceNameEnum implements IEnum<JSONObject> {

	GET_KOSTL("GET_KOSTL", "成本中心基础数据"),
	TRANS_TPLNR("TRANS_TPLNR", "功能位置主数据接口"),
	GET_WBS("GET_WBS", "获取WBS基础数据"),
	TRANS_EQUNR("TRANS_EQUNR", "设备台账主数据同步接口"),
	TRANS_ZCBF("TRANS_ZCBF", "资产报废集成接口"),
	GET_AUTH("GET_AUTH", "获取人员权限")
	;

	private final String value;
	private final String text;

	InterFaceNameEnum(String _value, String _text) {
		this.value = _value;
		this.text = _text;
	}

	public static String getValue(String _status) {
		for (InterFaceNameEnum s : InterFaceNameEnum.values()) {
			if (s.getValue().equals(_status)) {
				return s.getValue();
			}
		}
		return null;
	}

	public static String getText(String name) {
		for (InterFaceNameEnum s : InterFaceNameEnum.values()) {
			if (s.getValue().equals(name)) {
				return s.getText();
			}
		}
		return "";
	}

	@Override
	public List<JSONObject> getValueTextList() {
		List<JSONObject> returnList = new ArrayList<>();
		for (InterFaceNameEnum input : InterFaceNameEnum.values()) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("value", input.getValue());
			jsonObj.put("text", input.getText());
			returnList.add(jsonObj);
		}
		return returnList;
	}
}
