package com.lnsoft.device.api.i6000.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xyzadmin
 */

public enum I6000EnumEnum {
	CITYPE_ID("100007", "资源分类ID"),
	NETACC_TYPE("100018", "网口类型"),
	CYCLE_STATUS("400007", "设备状态"),
	HAVE("400009", "有无判定"),
	HARDDISK_TYPE("400010", "硬盘类型"),
	NETAPP_TYPE("400014", "应用类型"),
	NETWORK("400020", "所属网络"),
	YESORNO("400023", "是否判定"),
	MANAG_DEPTTYPE("400024", "设备管理部门"),
	OS_DATATYPE("400028", "操作系统类型"),
	PUR_MODE("400029", "采购方式"),
	RAIDMETHOD("400033", "RAID方式"),
	SAFE_BOUNDARY("400036", "所属安全边界"),
	PROP_STATUS("400040", "产权状态"),
	ASSET_ADD("400041", "设备增加方式"),
	ASSET_CHANGE("400042", "设备变动方式"),
	BEBER("400043", "工厂区域"),
	MADE_COUNTRY("400044", "制造国家和地区"),
	AUDIT_STATE("400045", "审核状态"),
	ENTIRE_BAK_PD_UNIT("400049", "完全备份周期单位"),
	ENABLED_STATUS("400099", "使用状态"),
	ELECTRIC_MODE("500001", "供电方式"),
	RAID_RDUDC_MODE("500002", "存储RAID冗余方式"),
	HIGH_AVAIL_TYPE("500003", "高可用类型"),
	AC_TYPE("500007", "空调类型"),
	MINI_COMPUTER_TYPE("500008", "小型机种类"),
	RUN_ENVIRONMENT("600005", "运行环境"),
	RUN_LEVEL("600012", "运行重要程度"),
	MASTER_FLAG("600015", "主备属性"),
	RUN_STATUS("600048", "运行状态"),
	RUN_GRADE("600054", "运维等级"),
	SOURCE("600056", "来源"),
	NETWORK_EFFECT_TYPE("600082", "网络设备用途类型"),
	HOST_EFFECT_TYPE("600083", "设备用途类型"),
	PRIORITY("600085", "优先级"),
	ERP_ASSET_STATE("600087", "ERP转资状态"),
	SCRAP_CAUSE("600088", "报废原因"),
	VPLATFORM_TYPE("600098", "虚拟化平台类型"),
	HALT_STATE("600099", "停机状态"),
	OS_RELEASE_VERSION("600104", "操作系统发行版本"),
	CLOUD_TYPE("600167", "云类型"),
	FUNCTION_DESCRIP("600171", "功能描述"),
	FAULT_COMPONENT("600173", "故障部件"),
	CPU_ARCHITEC("600176", "CPU架构"),
	SORT_ORDER("600181", "排放顺序");


	private String code;
	private String name;

	I6000EnumEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static List<Map<String, String>> toList() {
		List<Map<String, String>> list = new ArrayList<>();
		for (I6000EnumEnum item : I6000EnumEnum.values()) {
			Map<String, String> map = new HashMap<>();
			map.put("EXT_CODE", item.getCode());
			map.put("EXT_NAME", item.getName());
			list.add(map);
		}
		return list;
	}
}
