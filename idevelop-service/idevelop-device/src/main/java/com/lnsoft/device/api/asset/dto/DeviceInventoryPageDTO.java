package com.lnsoft.device.api.asset.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author xyzadmin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceInventoryPageDTO implements Serializable {
	private List<DeviceInventoryDTO> list;
	private String total;
}
