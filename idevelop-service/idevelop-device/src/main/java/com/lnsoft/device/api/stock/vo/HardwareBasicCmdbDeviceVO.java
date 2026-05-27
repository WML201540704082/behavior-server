package com.lnsoft.device.api.stock.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * cmdb数据导入文件数据回显
 */
@Data
public class HardwareBasicCmdbDeviceVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 错误信息
	 */
	private String msg;

	/**
	 * 状态
	 */
	private String code;

	/**
	 * 总数
	 */
	private int total;
	/**
	 * 数据列表
	 */
	private List<Map<String, Object>> records;

	/**
	 * 异常数据单元格位置 A2
	 */
	private List<String> errAddr;


	/**
	 * 设备分类
	 */
	private String deviceCategory;


}
