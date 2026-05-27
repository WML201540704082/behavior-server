package com.lnsoft.device.api.warehouse.vo;

import lombok.Data;

@Data
public class DeviceStorageSdnVO {
	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 设备编码
	 */
	private String deviceCode;
	/**
	 * 设备名称
	 */
	private String deviceName;
	/**
	 * 设备分类
	 */
	private String deviceCategory;
	/**
	 * 设备类型
	 */
	private String deviceType;
	/**
	 * 单位
	 */
	private String ownerUnit;
	/**
	 * 部门
	 */
	private String dept;
	/**
	 * 申请人
	 */
	private String createUser;
	/**
	 * 入库时间
	 */
	private String storageTime;
	/**
	 * 创建时间
	 */
	private String createTime;

	private String syncSign;
	/**
	 * 制造商
	 */
	private String maker;
	/**
	 * 品牌
	 */
	private String brand;
	/**
	 * 系列
	 */
	private String series;
	/**
	 * 型号
	 */
	private String deviceModel;
	/**
	 * 出厂编号
	 */
	private String sn;
	/**
	 * 出库时间
	 */
	private String outboundTime;
	/**
	 * 用户账号
	 */
	private String account;

	/**
	 * wbs项目
	 */
	private String wbsProject;

	/**
	 * wbs元素
	 */
	private String wbsElement;

}
