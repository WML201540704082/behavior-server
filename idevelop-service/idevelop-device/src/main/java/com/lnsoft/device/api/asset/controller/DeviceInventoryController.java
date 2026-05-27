package com.lnsoft.device.api.asset.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.asset.dto.DeviceInventoryDTO;
import com.lnsoft.device.api.asset.dto.DeviceInventoryPageDTO;
import com.lnsoft.device.api.asset.entity.DeviceAssetCaching;
import com.lnsoft.device.api.asset.entity.DeviceInventory;
import com.lnsoft.device.api.asset.service.IDeviceAssetCachingService;
import com.lnsoft.device.api.asset.service.IDeviceInventoryService;
import com.lnsoft.device.api.asset.task.DeviceAssetTask;
import com.lnsoft.device.api.warehouse.dto.WarehouseDTO;
import com.lnsoft.device.api.warehouse.entity.Warehouse;
import com.lnsoft.device.api.warehouse.service.IWarehouseService;
import com.lnsoft.device.api.warehouse.vo.WarehouseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  控制器
 *
 * @author xyz
 * @since 2024-04-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/inventory")
@Api(value = "库存统计接口", tags = "接口")
public class DeviceInventoryController extends IdevelopController {
	private IDeviceInventoryService deviceInventoryService;
	/**
	 * 获取在库设备统计
	 */
	@GetMapping("/inWarehouse")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取在库设备统计", notes = "传入仓库uuid-deviceInventory")
	public R<List<DeviceInventory>> list(DeviceInventory deviceInventory) {
		return R.data(deviceInventoryService.getDeviceCountByWarehouse(deviceInventory));
	}
	/**
	 * 获取出库数量统计
	 */
	@GetMapping("/outWarehouse")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "获取在库设备统计", notes = "传入仓库uuid-deviceInventory")
	public R<List<DeviceInventory>> count(DeviceInventoryDTO deviceInventory) {
		return R.data(deviceInventoryService.getOutWarehouse(deviceInventory));
	}
	/**
	 * 仓库统计
	 */
	@GetMapping("/warehouse")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "仓库统计", notes = "")
	public R deviceStatistics(WarehouseDTO warehouse) {
		IdevelopUser sysUser = SecureUtil.getUser();
		warehouse.setOwnerUnitId(sysUser.getCorpId());
		List<WarehouseVO> list = deviceInventoryService.deviceStatistics(warehouse);
		return R.data(list);
	}
	/**
	 * 查询结果列表(分页）
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "仓库列表", notes = "")
	public R warehouseStatistics(WarehouseDTO warehouse, Query query) {
		List<WarehouseVO> list = deviceInventoryService.warehouseStatistics(warehouse,query);
		return R.data(list);
	}
	/**
	 * 查询结果列表(分页）
	 */
	@GetMapping("/wareList")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "仓库列表", notes = "")
	public R warehouse(DeviceInventory inventory, Query query) {
		List<DeviceInventoryDTO> list = deviceInventoryService.findList(inventory,query);
		DeviceInventoryPageDTO dto = new DeviceInventoryPageDTO();
		dto.setList(list);
		dto.setTotal(deviceInventoryService.getTotal(inventory));
		return R.data(dto);
	}
}
