package com.lnsoft.device.api.asset.eums;

/**
 * @author xyzadmin
 */

public enum OldDeviceScoreConfigEnum {
	CYCLE ("考核周期"),

	SOURCE("数据源"),

	CITY_ROOM("市公司核心机房"),

	COUNTY_ROOM( "分中心/县公司核心机房"),

	POWER_ROOM( "供电所机房"),

	FLOOR_ROOM( "楼层配线间"),

	INFORMATION_ROOM("信息机房"),

	COMMUNICATE_ROOM("信息通信机房"),

	OTHER_ROOM("与其他专业合用机房"),

	OVERAGE("超龄(T>0)"),

	NO_OVERAGE("未超龄(T≤0)"),

	FAULT_COUNT("近三年故障次数"),

	HIDDEN_COUNT("近三年隐患次数"),

	YES("有"),

	NO("无"),

	ALL_DIGITIZATION("运维部门和产权部门均为数字化管理部门"),

	PART_DIGITIZATION("产权部门非数字化管理部门"),

	NO_DIGITIZATION("均非数字化管理部门"),

	NEED("需要"),

	NO_NEED("不需要"),

	NO_CONTENTING("市公司核心机房/县公司核心机房，去除F为1的设备后，剩余设备不满足n-1"),

	CONTENTING_EQUALS_TWO("市公司核心机房/县公司核心机房，去除F为1的设备后，满足n-1但n=2"),

	CONTENTING_AFTER_TWO("市公司核心机房/县公司核心机房，去除F为1的设备后，满足n-1且n>2"),

	OTHER("其它");

	private String message;

	OldDeviceScoreConfigEnum(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
