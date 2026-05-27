package com.lnsoft.device.api.feign;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.api.ResultCode;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.asset.entity.ResourceCabinets;
import com.lnsoft.device.api.asset.entity.ResourceCabinetsLs;
import com.lnsoft.device.api.asset.entity.ResourceRoom;
import com.lnsoft.device.api.asset.mapper.ResourceCabinetsMapper;
import com.lnsoft.device.api.asset.mapper.ResourceRoomMapper;
import com.lnsoft.device.api.asset.service.IResourceCabinetsService;
import com.lnsoft.device.api.asset.service.IResourceRoomService;
import com.lnsoft.device.api.asset.task.DeviceAssetTask;
import com.lnsoft.device.api.asset.vo.ResourceCabinetsVO;
import com.lnsoft.device.api.asset.vo.ResourceRoomVO;
import com.lnsoft.device.api.warehouse.entity.Warehouse;
import com.lnsoft.device.api.warehouse.mapper.WarehouseMapper;
import com.lnsoft.device.api.warehouse.service.IWarehouseService;
import com.lnsoft.device.api.warehouse.vo.WarehouseVO;
import com.lnsoft.device.feign.IOldDeviceAssetClient;
import com.lnsoft.device.task.CmdbTask;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@ApiIgnore
@RestController
public class OldDeviceAssetClient implements IOldDeviceAssetClient {

	@Resource
	private DeviceAssetTask deviceAssetTask;
	@Resource
	private CmdbTask cmdbTask;
	@Resource
	private ResourceRoomMapper roomMapper;
	@Resource
	private IResourceRoomService resourceRoomService;
	@Resource
	private IResourceCabinetsService resourceCabinetsService;
	@Resource
	private ResourceCabinetsMapper cabinetsMapper;
	@Resource
	private IWarehouseService warehouseService;
	@Resource
	private WarehouseMapper warehouseMapper;

	/**
	 * 定时同步老旧设备资产统计缓存表
	 */
	@Override
	@PostMapping(API_PREFIX + "/device/asset")
	public R<Integer> oldDeviceAsset() {
		deviceAssetTask.updateAsset();
		return R.data(ResultCode.SUCCESS.getCode());
	}

	@Override
	@PostMapping(API_PREFIX + "/device/refresh/age")
	public R refreshUseAge() {
		cmdbTask.refreshUseAge();
		return R.data(ResultCode.SUCCESS.getCode());
	}

	@Override
	@PostMapping(API_PREFIX + "/device/refresh/room")
	public R refreshRoom() {
		//更新机房全称
		List<ResourceRoomVO> all = roomMapper.getAll();
		List<ResourceRoom> roomList = all.stream().map(room -> {
			ResourceRoom resourceRoom = new ResourceRoom();
			resourceRoom.setUuid(room.getUuid());
			resourceRoom.setMaintenanceUnitName(StringUtil.isNotBlank(room.getFullName())? room.getFullName():"");
			return resourceRoom;
		}).collect(Collectors.toList());
		boolean b1 = resourceRoomService.updateBatchById(roomList);
		if (!b1){
			return R.fail("机房更新失败");
		}
		//机柜
		List<ResourceCabinetsVO> all1 = cabinetsMapper.getAll();
		List<ResourceCabinetsLs> cabinetsList = all1.stream().map(cabinets -> {
			ResourceCabinetsLs resourceCabinets = new ResourceCabinetsLs();
			resourceCabinets.setId(String.valueOf(cabinets.getId()));
			resourceCabinets.setMaintenanceUnitName(StringUtil.isNotBlank(cabinets.getFullName())? cabinets.getFullName():"");
			return resourceCabinets;
		}).collect(Collectors.toList());
		boolean b = resourceCabinetsService.updateBatchById(cabinetsList);
		if (!b){
			return R.fail("机柜更新失败");
		}
		//仓库
		List<WarehouseVO> all2 = warehouseMapper.getAll();
		List<Warehouse> warehouseList = all2.stream().map(warehouse -> {
			Warehouse warehouse00 = new Warehouse();
			warehouse00.setUuid(warehouse.getUuid());
			warehouse00.setOwnerUnit(StringUtil.isNotBlank(warehouse.getFullName())? warehouse.getFullName():"");
			return warehouse00;
		}).collect(Collectors.toList());
		boolean b2 = warehouseService.updateBatchById(warehouseList);
		if (!b2){
			return R.fail("仓库更新失败");
		}
		return R.success(ResultCode.SUCCESS);
	}
}
