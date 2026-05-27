package com.lnsoft.device.api.res.constants;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class Constants implements Serializable {
	private static final long serialVersionUID = 2213372076729629743L;

	/**
	 * 产生随机数
	 */
	public static final String SECURE_RANDOM = "SHA1PRNG";

	/**
	 * 日期格式转换
	 */
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat DATE_FORMAT_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat DATE_FORMAT_NO_UNDERLINE = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat DATE_FORMAT_NO_UNDERLINE_TIME = new SimpleDateFormat("yyyyMMddHHmm");

	/**
	 * ERP审核角色
	 */
	public static final String ERP_ROLE = "ERP审核角色";

	/**
	 * 库存备用状态
	 */
	public static final String DEVICE_STATUS_RESERVE = "0";
	public static final String DEVICE_SOURCE = "统一纳管";
	public static final String NO_DEVICE_SOURCE = "非统一纳管";
	public static final String IN_OPERATION = "在运";
	public static final String IN_WAREHOUSE = "退运在库";
	public static final String WAIT_SCARP = "待报废";

	/**
	 * 设备类型（交换机）（网络）交换机、存储交换机、光纤交换机
	 */
	public static final String SWITCHES_TYPE = "switches";
	/**
	 * 笔记本电脑、台式机、分拣装置、动力环境监测设备（无）、电能量采集终端（无）
	 * 自助缴费终端、日志审计、标准化抢修库房管理系统、移动工作站、其他前置机（无）
	 * 电能质量前置机（无）、油色谱前置机（无）、雷电监测前置机（无）、IAD终端、其他终端
	 * 计量终端、排队机、查询机、视频会议MCU（无）、视频会议终端
	 * 工作站、考勤机、计量周转柜、图像监控、POS终端
	 * 云终端、IP电话、刀片机、小型机、PC服务器
	 * 数据传输通道接口（无）、交换机(目前只记录接入层（网络）交换机)、路由器、屏幕设备、复印机
	 * 影印一体机、拼接屏系统（无）、大屏显示系统（无）、媒体控制器、门禁设备
	 * 图形采集设备、kvm、温湿度传感器、UPS、机房空调
	 * 入侵监测设备、其他安全设备、入侵防御设备、防病毒网关设备、网络隔离设备
	 * 防火墙、智能钥匙柜、人脸识别辅机（无）、人脸识别主机（无）、漏洞扫描设备
	 * 流量监测设备、存储交换机、光纤交换机
	 */
	public static final String DEVICE_SAFE_ACCESS_TYPE = "safeAccess";

	/**
	 * 设备投运更新地址池表前缀
	 */
	public static final String IP_POOL = "idevelop_safeaccess_ippool";

	/**
	 * DHCP生成周期（秒） 默认1小时 3600 默认最大24小时 86400
	 */
	public static final Integer DEFAULT_LEASE_TIME = 3600;
	public static final Integer MAX_LEASE_TIME = 86400;

	/**
	 * 建档ERP回调测试使用
	 */
	public static final String RECORD_ERP_TEST = "ERP";
	public static final String RECORD_ERP_ACC_TEST = "ERPACC";

	/**
	 * ERP推送电压等级
	 */
	public static final String ERP_ZSB004 = "07";
	/**
	 * 工厂区域
	 */
	public static final String COUNTY_FACTORY_AREA = "县公司";
	public static final String CITY_FACTORY_AREA = "省/市公司";

	/**
	 * 认证方式
	 */
	public static final String NO_AUTHENTICATION = "不认证";
	public static final String I802_AUTHENTICATION = "802.1x";
	public static final String MAC_AUTHENTICATION = "MAC认证";
	public static final String ELSE_AUTHENTICATION = "例外";
}
