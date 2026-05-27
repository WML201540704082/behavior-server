package com.lnsoft.device.eums;

import com.alibaba.fastjson.JSONObject;
import com.lnsoft.common.enums.IEnum;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xuel
 * @CreateTime: 2024/3/30 16:43
 * @Description: ErpOperationEnum  ERP标记位
 */
@Getter
public enum ErpOperationEnum implements IEnum<JSONObject> {

	C("C", "创建"),
	M("M", "更新"),
	D("D", "删除");

	private final String value;
	private final String text;

	ErpOperationEnum(String _value, String _text) {
		this.value = _value;
		this.text = _text;
	}

	public static String getValue(String _status) {
		for (ErpOperationEnum s : ErpOperationEnum.values()) {
			if (s.getValue().equals(_status)) {
				return s.getValue();
			}
		}
		return null;
	}

	public static String getText(String name) {
		for (ErpOperationEnum s : ErpOperationEnum.values()) {
			if (s.getValue().equals(name)) {
				return s.getText();
			}
		}
		return "";
	}

	@Override
	public List<JSONObject> getValueTextList() {
		List<JSONObject> returnList = new ArrayList<>();
		for (ErpOperationEnum input : ErpOperationEnum.values()) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("value", input.getValue());
			jsonObj.put("text", input.getText());
			returnList.add(jsonObj);
		}
		return returnList;
	}
}
