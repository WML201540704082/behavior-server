package com.lnsoft.device.api.warehouse.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: DeviceStorageListImportVO
 * @description:
 * @author: zhangs
 * @create: 2024-03-04 16:49
 **/
@Data
public class DeviceStorageListImportVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String exceptionField;

	private List<Map<String, Object>> list;

}
