package com.lnsoft.device.api.i6000.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xyzadmin
 */

public enum I6000ExternalEnum {
	EXT_101("EXT_101", "组织机构(单位)"),

	EXT_102("EXT_102", "部门"),

	EXT_103("EXT_103", "功能位置"),

	EXT_104("EXT_104", "维护工厂"),

	EXT_105("EXT_105", "WBS元素"),

	EXT_106("EXT_106", "成本中心"),

	EXT_107("EXT_107", "线站标识"),

	EXT_109("EXT_109", "组织单位"),

	EXT_110("EXT_110", "品牌"),

	EXT_111("EXT_111", "系列"),

	EXT_112("EXT_112", "型号"),

	EXT_113("EXT_113", "人员名称"),

	EXT_120("EXT_120", "操作系统类型"),

	EXT_121("EXT_121", "CPU品牌");


	private String code;
	private String name;

	I6000ExternalEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static List<Map<String,String>> toList(){
		List<Map<String, String>> list = new ArrayList<>();
		for (I6000ExternalEnum item : I6000ExternalEnum.values()) {
			Map<String, String> map = new HashMap<>();
			map.put("EXT_CODE",item.getCode());
			map.put("EXT_NAME",item.getName());
			list.add(map);
		}
		return list;
	}
}
