package com.lnsoft.device.api.cmdb.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author: xuel
 * @CreateTime: 2024/4/25 16:30
 * @Description: CmdbStockDTO
 */
@Data
public class CmdbStockDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	// 设备编码
	private String deviceCode;

	// 设备类型
	private String deviceTypeCode;

	// 查询配置项ID
	private Long filterCiEntityId;

	// 治理数据
	private Map<String, Object> stockMap;
}
