package com.lnsoft.device.api.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.cmdb.feign.ICmdbClient;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.DateUtil;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.asset.dto.DeviceInventoryDTO;
import com.lnsoft.device.api.asset.entity.DeviceInventory;
import com.lnsoft.device.api.asset.mapper.DeviceInventoryMapper;
import com.lnsoft.device.api.asset.service.IDeviceInventoryService;
import com.lnsoft.device.api.warehouse.dto.WarehouseDTO;
import com.lnsoft.device.api.warehouse.entity.Warehouse;
import com.lnsoft.device.api.warehouse.mapper.DeviceOutboundMapper;
import com.lnsoft.device.api.warehouse.mapper.WarehouseMapper;
import com.lnsoft.device.api.warehouse.service.IDeviceOperationDetailService;
import com.lnsoft.device.api.warehouse.service.IDeviceOutboundService;
import com.lnsoft.device.api.warehouse.service.IWarehouseService;
import com.lnsoft.device.api.warehouse.vo.WarehouseVO;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @author xyzadmin
 */
@Service
public class DeviceInventoryServiceImpl extends BaseServiceImpl<DeviceInventoryMapper, DeviceInventory> implements IDeviceInventoryService {


	/**
	 * 批量更新设备库存
	 *
	 * @param updateDeviceInventory 更新库存信息
	 */
	@Override
	public void batchUpdateInventory(List<DeviceInventory> updateDeviceInventory) {
		baseMapper.batchUpdateInventory(updateDeviceInventory);
	}
	@Resource
	private ICmdbClient cmdbClient;
	@Resource
	private DeviceOutboundMapper deviceOutboundMapper;
	@Resource
	private IWarehouseService warehouseService;
	@Resource
	private WarehouseMapper warehouseMapper;

	@Override
	public List<DeviceInventory> getDeviceCountByWarehouse(DeviceInventory deviceInventory) {
		IdevelopUser user = SecureUtil.getUser();
		ArrayList<DeviceInventory> deviceInventories = new ArrayList<>();
		R<List<Map<String, Object>>> categoryList = cmdbClient.feignGetCiCientityDictList(1097745625841664L);
		List<Map<String, Object>> categoryListData = categoryList.getData();
		Warehouse warehouse = new Warehouse();
		warehouse.setOwnerUnitId(user.getCorpId());
		List<WarehouseVO> warehouseVOS = warehouseService.findList(warehouse);
		WarehouseVO ware = warehouseVOS.get(0);
		String uuid = ware.getUuid();
		for (Map<String, Object> category : categoryListData) {
			LambdaQueryWrapper<DeviceInventory> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(DeviceInventory::getDeviceCategory, String.valueOf(category.get("dictKey")));
			if (ObjectUtil.isNotEmpty(deviceInventory.getWarehouse())) {
				uuid =  deviceInventory.getWarehouse();
			}
			DeviceInventory inventorys = new DeviceInventory();
			DeviceInventory inventory = baseMapper.selectDeviceCount(uuid,String.valueOf(category.get("dictKey")));
			if (inventory != null) {
				inventorys.setInventoryNum(inventory.getInventoryNum());
			} else {
				inventorys.setInventoryNum(0);
			}
			inventorys.setDeviceCategory(String.valueOf(category.get("dictKey")));
			inventorys.setDeviceCategoryName(String.valueOf(category.get("dictValue")));
			deviceInventories.add(inventorys);
		}
		return deviceInventories;
	}

	@Override
	public List<DeviceInventory> getOutWarehouse(DeviceInventoryDTO deviceInventory) {
		IdevelopUser user = SecureUtil.getUser();
		Warehouse ware = new Warehouse();
		ware.setOwnerUnitId(user.getCorpId());
		ArrayList<DeviceInventory> inventoryArrayList = new ArrayList<>();
		List<WarehouseVO> warehouseVOList = warehouseService.findList(ware);
		for (WarehouseVO warehouse : warehouseVOList) {
			Date date = new Date();
			String nowDay = DateUtil.formatDate(date);
			String wareDateStr = getWareDateStr(deviceInventory.getType());
			String endDate = nowDay;
			String startDate = wareDateStr;
			String warehouseId = warehouse.getUuid();
			DeviceInventoryDTO dto = deviceOutboundMapper.getOutWarehouse(startDate, endDate, warehouseId);
			dto.setWarehouseName(warehouse.getWarehouseName());
			dto.setWarehouse(warehouse.getUuid());
			inventoryArrayList.add(dto);
		}
		return inventoryArrayList;
	}

	@Override
	public List<WarehouseVO> deviceStatistics(WarehouseDTO warehouse) {
		return warehouseService.findList(warehouse);
	}

	@Override
	public List<WarehouseVO> warehouseStatistics(WarehouseDTO warehouse, Query query) {
		IdevelopUser user = SecureUtil.getUser();
		warehouse.setOwnerUnitId(user.getCorpId());
		List<WarehouseVO> list = warehouseService.selectPage(query, warehouse);
		for (Warehouse ware : list) {
			DeviceInventory deviceInventory = new DeviceInventory();
			deviceInventory.setWarehouse(ware.getUuid());
			LambdaQueryWrapper<DeviceInventory> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(DeviceInventory::getWarehouse, ware.getUuid());
			List<DeviceInventory> deviceInventories = baseMapper.selectList(queryWrapper);
			Integer devSize = 0;
			for (DeviceInventory inventory : deviceInventories) {
				Integer inventoryNum = inventory.getInventoryNum();
				devSize += inventoryNum;
			}
			ware.setWareSize(devSize);
//			DeviceInventoryDTO outWarehouse = deviceOutboundMapper.getOutWarehouse(null, null, ware.getUuid());
//			ware.setOutSize(Integer.parseInt(outWarehouse.getCount()));
//			ware.setDevSize(devSize + Integer.parseInt(outWarehouse.getCount()));
			ware.setDevSize(devSize);
		}
		return list;
	}

	@Override
	public List<DeviceInventoryDTO> findList(DeviceInventory inventory, Query query) {
		IdevelopUser user = SecureUtil.getUser();
		Integer current = query.getCurrent();
		Integer size = query.getSize();
		Integer sizeIndex = (current-1)*size;
		String regionCode = user.getRegionCode();
		inventory.setRegionCode(regionCode);
		List<DeviceInventoryDTO> list = baseMapper.findListPage(inventory,sizeIndex,size);
		return list;
	}

	@Override
	public String getTotal(DeviceInventory inventory) {
		return baseMapper.getTotal(inventory);
	}

	/**
	 * 获取日期字符串
	 *
	 * @param type
	 * @return
	 */
	private String getWareDateStr(String type) {
		String array = new String();
		Date now = new Date();
		if ("7day".equals(type)) {
			Date nextDay = DateUtil.minusWeeks(now, 1);
			array = DateUtil.formatDate(nextDay);
		} else if ("month".equals(type)) {
			Date nextDay = new Date();
			nextDay.setDate(1);
			array = DateUtil.formatDate(nextDay);
		} else if ("year".equals(type)) {
			Date nextDay = DateUtil.minusYears(now, 1);
			array = DateUtil.formatDate(nextDay);
		}
		return array;
	}
}
