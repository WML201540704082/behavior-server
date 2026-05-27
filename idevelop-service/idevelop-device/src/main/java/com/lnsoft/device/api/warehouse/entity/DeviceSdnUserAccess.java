package com.lnsoft.device.api.warehouse.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceSdnUserAccess implements Serializable {
	private static final long serialVersionUID = 413102691569977782L;

	/**
	 * ID
	 */
	private String id;

	/**
	 * 公司
	 */
	private String company;

	/**
	 * 部门
	 */
	private String department;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 联系电话
	 */
	private String phone;

	/**
	 * 设备类型
	 */
	private String deviceId;

	/**
	 * 所属子网ID
	 */
	private String subnetId;

	/**
	 * 认证用户
	 */
	private String authUser;

	/**
	 * 认证密码
	 */
	private String authPassword;

	/**
	 * mac地址
	 */
	private String macAddress;

	/**
	 * ip地址
	 */
	private String ipAddress;

	/**
	 * 原ip地址 ip地址
	 */
	private String oldIpAddress;

	/**
	 * 设备编码
	 */
	private String sbbm;

	/**
	 * 入网开始时间
	 */
	private String startTime;

	/**
	 * 允许入网时间
	 */
	private String allowDays;

	/**
	 * 是否启用802.1X接入认证（0 不认证，1 802.1X，2 mac）
	 */
	private String is802;

	/**
	 * 用户全名
	 */
	private String fullUserName;

	/**
	 * 是否认证成功（0 未认证，1 已认证）
	 */
	private String isAccess;

	/**
	 * 同步时间（数据写入此表的时间）
	 */
	private String syncTime;

	/**
	 * 同步标识（A 新增，U 更新，D删除，C 变更）
	 */
	private String syncSign;

	/**
	 * 数据读取状态（0 未读，1 已读）
	 */
	private String readState;

	/**
	 * 数据来源（0 一体化平台，1 SDN第三方）
	 */
	private String dataFrom;

	/**
	 * 子网对应的vlanid号
	 */
	private String vlanId;

	/**
	 * 设备来源，01：单位自购，02：公司配发
	 */
	private String sourcr;

	/**
	 * 区域编码（到市）
	 */
	private String region;

	/**
	 * 标准全称
	 */
	private String deviceName;

	/**
	 * 序列号
	 */
	private String factoryNumber;

	/**
	 * 制造商
	 */
	private String makerName;

	/**
	 * 品牌名称
	 */
	private String brandName;

	/**
	 * 系列名称
	 */
	private String seriesName;

	/**
	 * 型号名称
	 */
	private String deviceModelName;

}
