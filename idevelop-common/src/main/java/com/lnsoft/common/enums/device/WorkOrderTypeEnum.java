package com.lnsoft.common.enums.device;

import com.alibaba.fastjson.JSONObject;
import com.lnsoft.common.enums.IEnum;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xuel
 * @CreateTime: 2024/2/19 16:17
 * @Description: 工单和设备编号生成枚举 OrderTypeEnum
 */
@Getter
public enum WorkOrderTypeEnum implements IEnum<JSONObject> {

	WH("WH", "仓库编码", ""),
	RM("RM", "机房编码", ""),
	GC("GC", "设备编码", ""),
	JD("JD", "设备建档", ""),
	RK("RK", "入库管理", ""),
	ZZ("ZZ", "设备转资", ""),
	SQ("SQ", "设备申请", ""),
	CK("CK", "设备出库", ""),
	TY("TY", "设备投运", ""),
	BG("BG", "设备变更", ""),
	BX("BX", "设备报修", ""),
	BF("BF", "设备报废", ""),
	TYD("TYD", "设备退运", ""),
	PD("PD", "盘点任务", ""),
	YDGJ("YDGJ", "异动告警", "warning"),
	ERP("ERP", "ERP编码", ""),
	RZ("ZR", "认证账号", ""),
	JK("JK", "接口申请", ""),
	FK("FK", "用户反馈", ""),
	BZXHK("BZXHK","标准型号库","");

	private String value;
	private String text;
	private String endpointValue;

	WorkOrderTypeEnum(String value, String text, String endpointValue) {
		this.value = value;
		this.text = text;
		this.endpointValue = endpointValue;
	}

	public static String getValue(String _status) {
		for (WorkOrderTypeEnum workOrderTypeEnum : WorkOrderTypeEnum.values()) {
			if (workOrderTypeEnum.getValue().equals(_status)) {
				return workOrderTypeEnum.getValue();
			}
		}
		return "";
	}

	public static String getText(String _name) {
		for (WorkOrderTypeEnum workOrderTypeEnum : WorkOrderTypeEnum.values()) {
			if (workOrderTypeEnum.getText().equals(_name)) {
				return workOrderTypeEnum.getText();
			}
		}
		return "";
	}

	@Override
	public List<JSONObject> getValueTextList() {
		List<JSONObject> returnList = new ArrayList<>();
		for (WorkOrderTypeEnum input : WorkOrderTypeEnum.values()) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("value", input.getValue());
			jsonObj.put("text", input.getText());
			jsonObj.put("endpointValue", input.getEndpointValue());
			returnList.add(jsonObj);
		}
		return returnList;
	}
}
