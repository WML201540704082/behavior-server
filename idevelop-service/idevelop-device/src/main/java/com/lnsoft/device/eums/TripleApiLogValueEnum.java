package com.lnsoft.device.eums;

import com.alibaba.fastjson.JSONObject;
import com.lnsoft.common.enums.IEnum;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
public enum TripleApiLogValueEnum implements IEnum<JSONObject> {

	CMDB_INSERT_DATA("CMDB_INSERT_DATA", "CMDB-新增配置项"),
	CMDB_UPDATE_DATA("CMDB_UPDATE_DATA", "CMDB-修改配置项"),
	CMDB_DICT_INSERT_DATA("CMDB_DICT_INSERT_DATA", "CMDB-新增枚举 配置项"),
	CMDB_DICT_UPDATE_DATA("CMDB_DICT_UPDATE_DATA", "CMDB-修改枚举 配置项"),
	CMDB_DELETE_DATA("CMDB_DELETE_DATA", "CMDB-删除配置项"),
	CMDB_DELETE_BATCH_DATA("CMDB_DELETE_BATCH_DATA", "CMDB-批量删除配置项"),
	CMDB_STOCK_BATCH_DATA("CMDB_STOCK_BATCH_DATA", "CMDB-批量数据治理"),

	ERP_INSERT_UPDATE_DATA("ERP_INSERT_UPDATE_DATA", "ERP-新增或修改主数据"),
	ERP_XTYTH_CALLBACK("ERP_XTYTH_CALLBACK", "ERP-建档/转资回调接口"),
	ERP_TRANS_TPLNR("ERP_TRANS_TPLNR", "ERP-功能位置主数据接口"),
	ERP_TRANS_ZCBF("ERP_TRANS_ZCBF", "ERP-资产报废集成接口"),
	ERP_UPDATE_ANLNR("ERP_UPDATE_ANLNR", "ERP-修改ERP资产编码接口"),

	I6000_INSERT_DATA("I6000_INSERT_DATA", "i6000-新增配置项"),
	I6000_UPDATE_DATA("I6000_UPDATE_DATA", "i6000-修改配置项"),
	I6000_UPDATE_DATA_TPLNR("I6000_UPDATE_DATA_TPLNR", "i6000-新增/修改功能位置主数据接口"),

	UNKNOWN("UNKNOWN", "未知")
	;

	private final String value;
	private final String text;

	TripleApiLogValueEnum(String _value, String _text) {
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
