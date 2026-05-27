package com.lnsoft.device.api.asset.mapper;

import com.lnsoft.device.api.asset.dto.WarehouseDetailDTO;

import java.util.List;

/**
 * @author xyzadmin
 */
public interface HardwareBasicMapper {

	List<WarehouseDetailDTO> warehouse(String deviceCode);

	List<Object> apply(String deviceCode);

	List<Object> operation(String deviceCode);

	List<Object> change(String deviceCode);

	List<Object> repair(String deviceCode);
}
