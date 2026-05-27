package com.lnsoft.device.api.asset.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseService;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.device.api.asset.dto.DeviceInventoryDTO;
import com.lnsoft.device.api.asset.entity.DeviceInventory;
import com.lnsoft.device.api.warehouse.dto.WarehouseDTO;
import com.lnsoft.device.api.warehouse.entity.Warehouse;
import com.lnsoft.device.api.warehouse.vo.WarehouseVO;

import java.util.List;

import java.util.List;

public interface IDeviceInventoryService extends BaseService<DeviceInventory> {

	/**
	 * 批量更新设备库存
	 *
	 * @param updateDeviceInventory 更新库存信息
	 */
	void batchUpdateInventory(List<DeviceInventory> updateDeviceInventory);

	/**
	 * 获取在库设备统计数据
	 * @param deviceInventory
	 * @return
	 */
	List<DeviceInventory> getDeviceCountByWarehouse(DeviceInventory deviceInventory);

	/**
	 * 获取出库数量数据
	 * @param deviceInventory
	 * @return
	 */
	List<DeviceInventory> getOutWarehouse(DeviceInventoryDTO deviceInventory);

	/**
	 * 仓库统计
	 * @param warehouse
	 * @return
	 */
	List<WarehouseVO> deviceStatistics(WarehouseDTO warehouse);
	/**
	 * 仓库列表
	 * @param warehouse
	 * @return
	 */
	List<WarehouseVO> warehouseStatistics(WarehouseDTO warehouse, Query query);
	/**
	 * 仓库分类统计列表
	 * @param inventory
	 * @return
	 */
	List<DeviceInventoryDTO> findList(DeviceInventory inventory, Query query);
	String getTotal(DeviceInventory inventory);
}
