package com.lnsoft.device.api.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: CustomDeviceStorageEventDTO
 * @description:
 * @author: zhangs
 * @create: 2024-03-05 16:18
 **/
@Data
@AllArgsConstructor
public class CustomDeviceStorageEventDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String serialNumber;

}
