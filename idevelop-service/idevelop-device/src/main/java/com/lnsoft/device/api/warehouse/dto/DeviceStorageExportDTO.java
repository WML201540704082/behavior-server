package com.lnsoft.device.api.warehouse.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author: xuel
 * @CreateTime: 2025/9/16 15:49
 * @Description: DeviceStorageExport
 */

@Data
public class DeviceStorageExportDTO implements Serializable {

	@ApiModelProperty(value = "设备分类")
	private String deviceCategory;

	@ApiModelProperty(value = "设备类型")
	private String deviceType;

	@ApiModelProperty(value = "数据列表")
	private List<Map<String, Object>> records;
}
