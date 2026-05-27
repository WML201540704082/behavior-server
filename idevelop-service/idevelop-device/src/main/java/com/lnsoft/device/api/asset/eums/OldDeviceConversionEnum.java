package com.lnsoft.device.api.asset.eums;

/**
 * @author xyzadmin
 */

public enum OldDeviceConversionEnum {
	CITY_ROOM("0", "市公司核心机房"),

	COUNTY_ROOM("1","分中心/县公司核心机房"),

	POWER_ROOM("2", "供电所机房"),

	FLOOR_ROOM("3", "楼层配线间"),

	INFORMATION_ROOM("4", "信息机房"),

	COMMUNICATE_ROOM("5", "信息通信机房"),

	OTHER_ROOM("6","与其他专业合用机房"),

	SPARE("1104999917879296","库存备用"),

	OPERATION("1105089449492480","在运"),

	WAREHOUSE("1105092544888833","退运在库"),

	SCRAPPED("1105089734705152","待报废"),

	YES("7","有"),

	NO("8","无"),

	NEED("9","需要"),

	NO_NEED("10","不需要");

	private String code;

	private String message;

	OldDeviceConversionEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	public static String getMessage(String code) {
		OldDeviceConversionEnum[] values = OldDeviceConversionEnum.values();
		for (OldDeviceConversionEnum oldDeviceConversionEnum : values) {
			if (oldDeviceConversionEnum.code.equals(code)) {
				return oldDeviceConversionEnum.message;
			}
		}
		return null;
	}
}
