package com.lnsoft.device.api.asset.eums;

/**
 * @author xyzadmin
 */

public enum OldTrendEnum {
	TIME_2024("2024", "2024-12-31"),

	TIME_2025("2025", "2025-12-31"),

	TIME_2026("2026", "2026-12-31"),

	TIME_2027("2027", "2027-12-31"),

	TIME_2028("2028", "2028-12-31");


	private String code;

	private String message;

	OldTrendEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
