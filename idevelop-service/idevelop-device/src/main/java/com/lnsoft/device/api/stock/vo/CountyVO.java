package com.lnsoft.device.api.stock.vo;

import lombok.Data;

/**
 * @author xyzadmin
 */
@Data
public class CountyVO {
	/**
	 *设备分类
	 */
	private String deviceType;
	/**
	 * 区域编码
	 */
	private String regionCode;
	/**
	 * 区域名称
	 */
	private String regionName;
	/**
	 * 已治理数量
	 */
	private String governanceYes;
	/**
	 * 未治理数量
	 */
	private String governanceNo;
	/**
	 * 总数
	 */
	private String allCount;
	/**
	 * 数量校对
	 */
	private Boolean isTrue;
	/**
	 * 治理率
	 */
	private String rate;

}
