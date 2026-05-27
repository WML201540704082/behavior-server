package com.lnsoft.device.api.asset.eums;

/**
 * @author xyzadmin
 */

public enum ResourceTreeTypeEnum {
	PREFECTURE("city", "地市"),

	REGION("county","地区"),

	ROOM("room", "机房"),

	CABINETS("cabinets", "机柜"),

	RACKS("racks", "机架"),

	WAREHOUSE("defaultWarehouse", "展示仓库"),

	CONSTANT_ROOM("defaultRoom","展示机房"),

	DEVICE("device","设备"),

	PROVINCIAL_CORPORATION("provinceCorporation","省公司"),

	MUNICIPAL_CORPORATION("municipalCorporation","市公司"),

	DISTRICT_CORPORATION("districtCorporation","区县公司"),

	TOP("top","最上级节点"),

	CORP("corp","公司");

	private String code;

	private String message;

	ResourceTreeTypeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}
}
