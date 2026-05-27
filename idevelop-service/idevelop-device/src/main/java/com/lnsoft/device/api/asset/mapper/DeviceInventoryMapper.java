package com.lnsoft.device.api.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lnsoft.device.api.asset.dto.DeviceInventoryDTO;
import com.lnsoft.device.api.asset.entity.DeviceInventory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DeviceInventoryMapper extends BaseMapper<DeviceInventory> {

	@Select("SELECT warehouse_name FROM `idevelop_warehouse` WHERE is_deleted = 0 AND uuid = #{warehouse}")
	String getWarehouseName(String warehouse);

	void saveOrUpdateDatabase(DeviceInventory source);

    List<DeviceInventoryDTO> findListPage(@Param("inventory") DeviceInventory inventory,
										  @Param("sizeIndex")Integer sizeIndex,
										  @Param("size")Integer size);

	DeviceInventory selectDeviceCount(@Param("warehouse") String uuid,@Param("deviceCategory") String dictKey);

	/**
	 * 批量更新设备库存
	 *
	 * @param updateDeviceInventory 更新库存信息
	 */
	void batchUpdateInventory(@Param("deviceInventory") List<DeviceInventory> updateDeviceInventory);

	String getTotal(@Param("inventory")DeviceInventory inventory);
}
