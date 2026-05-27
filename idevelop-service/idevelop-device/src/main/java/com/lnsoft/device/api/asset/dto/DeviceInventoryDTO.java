package com.lnsoft.device.api.asset.dto;

import com.lnsoft.device.api.asset.entity.DeviceInventory;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author xyzadmin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DeviceInventoryDTO extends DeviceInventory implements Serializable {
	private static final long serialVersionUID = -5666708997848771855L;
	/**
	 * 类型 金7天 本月  近一年
	 */
	private String type;
	/**
	 * 仓库出库量
	 */
	private String count;
	/**
	 * 单位全称
	 */
	private String fullName;
	/**
	 * 总条数
	 */
	private String total;
}
