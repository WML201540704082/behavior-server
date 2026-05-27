package com.lnsoft.device.api.asset.dto;

import lombok.Data;

/**
 * @author xyzadmin
 */
@Data
public class WarehouseDetailDTO {
	private static final long serialVersionUID = 1L;

	/**
	 * 工单编号
	 */
	private String ticketCode;
	/**
	 * 工单类型
	 */
	private String type;
	/**
	 * 所属单位
	 */
	private String unit;
	/**
	 * 所属仓库
	 */
	private String warehouse;
	/**
	 * 设备数量
	 */
	private String number;
	/**
	 * 工单时间
	 */
	private String time;
	/**
	 * 工单状态
	 */
	private String status;
	/**
	 * 操作人
	 */
	private String user;

}
