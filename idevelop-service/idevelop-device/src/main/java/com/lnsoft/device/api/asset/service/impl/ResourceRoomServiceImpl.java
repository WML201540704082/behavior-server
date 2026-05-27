/**
 .
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lnsoft.device.api.asset.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.asset.dto.*;
import com.lnsoft.device.api.asset.entity.ResourceCabinets;
import com.lnsoft.device.api.asset.entity.ResourceRoom;
import com.lnsoft.device.api.asset.eums.ResourceTreeTypeEnum;
import com.lnsoft.device.api.asset.mapper.ResourceRoomMapper;
import com.lnsoft.device.api.asset.service.IResourceCabinetsService;
import com.lnsoft.device.api.asset.service.IResourceRoomService;
import com.lnsoft.device.api.asset.vo.ResourceRoomVO;
import com.lnsoft.device.api.asset.vo.ResourceTreeVO;
import com.lnsoft.device.api.asset.wrapper.HardwareBasicWrapper;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.res.service.IResourceRacksService;
import com.lnsoft.device.api.warehouse.dto.RoomWarehouseBatchDTO;
import com.lnsoft.device.api.warehouse.entity.Warehouse;
import com.lnsoft.device.api.warehouse.service.IWarehouseService;
import com.lnsoft.device.api.warehouse.vo.DeviceResourceVo;
import com.lnsoft.device.api.warehouse.vo.WarehouseVO;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.constant.CmdbCientityConstant;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.vo.CiCientitySearchVO;
import com.lnsoft.system.feign.IDeptClient;
import com.lnsoft.system.vo.DeptVO;
import com.lnsoft.system.vo.RegionVO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 空间资源管理机房表 服务实现类
 *
 * @author Idevelop
 * @since 2024-02-21
 */
@Service
public class ResourceRoomServiceImpl extends BaseServiceImpl<ResourceRoomMapper, ResourceRoom> implements IResourceRoomService {
	@Resource
	private IResourceCabinetsService resourceCabinetsService;
	@Resource
	private IResourceRacksService resourceRacksService;
	@Resource
	private IWarehouseService iWarehouseService;
	@Resource
	private ICmdbService cmdbService;
	@Resource
	private IDeptClient deptClient;
	@Resource
	private CmdbCientityProperties cmdbCientityProperties;

	@Override
	public IPage<ResourceRoomVO> selectResourceRoomPage(IPage<ResourceRoomVO> page, ResourceRoomVO resourceRoom) {
		return page.setRecords(baseMapper.selectResourceRoomPage(page, resourceRoom));
	}

	@Override
	public String selectRoomIdById(String uuid) {
		return baseMapper.selectRoomIdById(uuid);
	}

	@Override
	public ExportRoom selectRoomById(String id) {
		return baseMapper.selectRoomById(id);
	}

	@Override
	public List<ExportRoom> selectRoomList(ResourceRoomDTO resourceRoom) {
		return baseMapper.selectRoomList(resourceRoom);
	}

	@Override
	public void export(@RequestBody ResourceRoomDTO resourceRoomDTO, HttpServletResponse response) {
		List<ExportRoom> roomList = new ArrayList<>();
		String ids = resourceRoomDTO.getIds();
		IdevelopUser user = SecureUtil.getUser();
		if ("".equals(ids)) {
			resourceRoomDTO.setRegionCode(user.getRegionCode());
			roomList = selectRoomList(resourceRoomDTO);
		} else {
			List<String> idList = Func.toStrList(ids);
			for (String id : idList) {
				ExportRoom exportRoom = selectRoomById(id);
				roomList.add(exportRoom);
			}
		}
		try {
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			String fileName = URLEncoder.encode("机房列表导出", StandardCharsets.UTF_8.name());
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
			EasyExcel.write(response.getOutputStream(), ExportRoom.class).sheet("机房列表").doWrite(roomList);
		} catch (IOException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public IPage<ResourceRoomVO> selectRoomByAreaId(IPage<ResourceRoomVO> page, ResourceRoomVO resourceRoom) {
		return page.setRecords(baseMapper.selectRoomByAreaId(page, resourceRoom));
	}

	@Override
	public List<ResourceTreeVO> lazyTree(ResourceQuery resourceQuery) {
		//区域树
		String type = resourceQuery.getType();
		ArrayList<ResourceTreeVO> resourceTreeVOArrayList = new ArrayList<>();
		String id = resourceQuery.getId();
		if (StringUtil.isBlank(type)) {
			List<RegionVO> lazyTree = HardwareBasicWrapper.build().lazyTree(id);
			for (RegionVO regionVO : lazyTree) {
				ResourceTreeVO resourceTreeVO = new ResourceTreeVO();
				resourceTreeVO.setId(String.valueOf(regionVO.getId()));
				resourceTreeVO.setType(ResourceTreeTypeEnum.PREFECTURE.getCode());
				resourceTreeVO.setName(regionVO.getName());
				resourceTreeVOArrayList.add(resourceTreeVO);
			}
		}
		if (StringUtil.equals(ResourceTreeTypeEnum.PREFECTURE.getCode(), type)) {
			List<RegionVO> lazyTree = HardwareBasicWrapper.build().lazyTree(id);
			for (RegionVO regionVO : lazyTree) {
				ResourceTreeVO resourceTreeVO = new ResourceTreeVO();
				resourceTreeVO.setId(String.valueOf(regionVO.getId()));
				resourceTreeVO.setType(ResourceTreeTypeEnum.REGION.getCode());
				resourceTreeVO.setName(regionVO.getName());
				resourceTreeVOArrayList.add(resourceTreeVO);
			}
		}
		if (StringUtil.equals(ResourceTreeTypeEnum.REGION.getCode(), type)) {
			ResourceTreeVO treeVO01 = new ResourceTreeVO();
			treeVO01.setName("机房");
			treeVO01.setType(ResourceTreeTypeEnum.CONSTANT_ROOM.getCode());
			treeVO01.setId("1");
			ResourceTreeVO treeVO02 = new ResourceTreeVO();
			treeVO02.setName("仓库");
			treeVO02.setType(ResourceTreeTypeEnum.WAREHOUSE.getCode());
			treeVO02.setId("2");
			resourceTreeVOArrayList.add(treeVO01);
			resourceTreeVOArrayList.add(treeVO02);
		}
		if (StringUtil.equals(ResourceTreeTypeEnum.CONSTANT_ROOM.getCode(), type)) {
			List<ResourceRoom> roomList = baseMapper.findByAreaId(id);
			roomList.forEach((room) -> {
				ResourceTreeVO resourceTreeVO = new ResourceTreeVO();
				resourceTreeVO.setId(room.getUuid());
				resourceTreeVO.setName(room.getRoomName());
				resourceTreeVO.setType(ResourceTreeTypeEnum.ROOM.getCode());
				resourceTreeVOArrayList.add(resourceTreeVO);
			});
		}
		if (StringUtil.equals(ResourceTreeTypeEnum.ROOM.getCode(), type)) {
			List<ResourceCabinets> cabinetsList = resourceCabinetsService.findByRoomId(id);
			cabinetsList.forEach((cabinets) -> {
				ResourceTreeVO resourceTreeVO = new ResourceTreeVO();
				resourceTreeVO.setId(cabinets.getUuid());
				resourceTreeVO.setType(ResourceTreeTypeEnum.CABINETS.getCode());
				resourceTreeVO.setName(cabinets.getFullName());
				resourceTreeVOArrayList.add(resourceTreeVO);
			});
		}
		if (StringUtil.equals(ResourceTreeTypeEnum.CABINETS.getCode(), type)) {
			Query query = new Query();
			query.setCurrent(1);
			query.setSize(999);
			DeviceResourceVo deviceResourceVo = new DeviceResourceVo();
			deviceResourceVo.setCurrent(1);
			deviceResourceVo.setSize(999);
			deviceResourceVo.setCabinetCode(id);
			List<CiCientitySearchVO> ciCientitySearchVOS = CiCientitySearchVO.convertCiCientitySearchVO(deviceResourceVo);
			FeignCiCientity jsonObject = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
			List<Map<String, Object>> data = jsonObject.getData();
			data.forEach((device) -> {
				ResourceTreeVO resourceTreeVO = new ResourceTreeVO();
				resourceTreeVO.setId(String.valueOf(device.get("id")));
				resourceTreeVO.setCiId(String.valueOf(device.get("ciId")));
				resourceTreeVO.setName(String.valueOf(device.get("deviceName")));
				resourceTreeVO.setType(ResourceTreeTypeEnum.DEVICE.getCode());
				resourceTreeVOArrayList.add(resourceTreeVO);
			});
		}
		if (StringUtil.equals(ResourceTreeTypeEnum.WAREHOUSE.getCode(), type)) {
			List<WarehouseVO> warehouseVOList = iWarehouseService.findByRegionCode(id);
			for (WarehouseVO warehouseVO : warehouseVOList) {
				ResourceTreeVO resourceTreeVO = new ResourceTreeVO();
				resourceTreeVO.setId(warehouseVO.getUuid());
				resourceTreeVO.setType("warehouse");
				resourceTreeVO.setName(warehouseVO.getWarehouseName());
				resourceTreeVOArrayList.add(resourceTreeVO);
			}
		}
		return resourceTreeVOArrayList;
	}

	@Override
	public List<ResourceTreeVO> deptTree(ResourceQuery resourceQuery) {
		//部门树
		String type = resourceQuery.getType();
		ArrayList<ResourceTreeVO> resourceTreeVOArrayList = new ArrayList<>();
		String id = resourceQuery.getId();
		if (StringUtil.equals(ResourceTreeTypeEnum.TOP.getCode(), type)) {
			List<DeptVO> treeList = HardwareBasicWrapper.build().getTreeList(id);
			treeList.forEach((dept) -> {
				ResourceTreeVO resourceTreeVO = new ResourceTreeVO();
				resourceTreeVO.setName(dept.getFullName());
				resourceTreeVO.setId(String.valueOf(dept.getId()));
				resourceTreeVO.setType(ResourceTreeTypeEnum.CORP.getCode());
				resourceTreeVO.setRegionCode(dept.getRegionCode());
				resourceTreeVOArrayList.add(resourceTreeVO);
			});
		}
		if (StringUtil.equals(ResourceTreeTypeEnum.CORP.getCode(), type)) {
			ResourceTreeVO treeVO01 = new ResourceTreeVO();
			treeVO01.setName("机房");
			treeVO01.setType(ResourceTreeTypeEnum.CONSTANT_ROOM.getCode());
			treeVO01.setId(String.valueOf(UUID.randomUUID()));
			ResourceTreeVO treeVO02 = new ResourceTreeVO();
			treeVO02.setName("仓库");
			treeVO02.setType(ResourceTreeTypeEnum.WAREHOUSE.getCode());
			treeVO02.setId(String.valueOf(UUID.randomUUID()));
			resourceTreeVOArrayList.add(treeVO01);
			resourceTreeVOArrayList.add(treeVO02);
			List<DeptVO> treeList = HardwareBasicWrapper.build().getTreeList(id);
			treeList.forEach((dept) -> {
				ResourceTreeVO resourceTreeVO = new ResourceTreeVO();
				resourceTreeVO.setName(dept.getFullName());
				resourceTreeVO.setId(String.valueOf(dept.getId()));
				resourceTreeVO.setType(ResourceTreeTypeEnum.CORP.getCode());
				resourceTreeVO.setRegionCode(dept.getRegionCode());
				resourceTreeVOArrayList.add(resourceTreeVO);
			});

		}
		if (StringUtil.equals(ResourceTreeTypeEnum.CONSTANT_ROOM.getCode(), type)) {
			ResourceRoomDTO resourceRoomDTO = new ResourceRoomDTO();
			resourceRoomDTO.setMaintenanceUnit(resourceQuery.getId());
			resourceRoomDTO.setRegionCode(resourceQuery.getRegionCode());
			List<ResourceRoom> roomList = baseMapper.findByDeptId(resourceRoomDTO);
			roomList.forEach((room) -> {
				ResourceTreeVO resourceTreeVO = new ResourceTreeVO();
				resourceTreeVO.setId(room.getUuid());
				resourceTreeVO.setName(room.getRoomName());
				resourceTreeVO.setType(ResourceTreeTypeEnum.ROOM.getCode());
				resourceTreeVOArrayList.add(resourceTreeVO);
			});
		}
		if (StringUtil.equals(ResourceTreeTypeEnum.ROOM.getCode(), type)) {
			Query query = new Query();
			query.setCurrent(1);
			query.setSize(999);
			ResourceCabinetsDTO cabinetsDTO = new ResourceCabinetsDTO();
			cabinetsDTO.setComputerRoomCode(id);
			cabinetsDTO.setDeviceTypeCode("1135308277350478");
			R list = resourceCabinetsService.getList(cabinetsDTO, query);
			FeignCiCientity data = (FeignCiCientity) list.getData();
			List<Map<String, Object>> mapList = data.getData();
			mapList.forEach((cabinets) -> {
				ResourceTreeVO resourceTreeVO = new ResourceTreeVO();
				resourceTreeVO.setId(String.valueOf(cabinets.get(CmdbAttrConstant.ID)));
				resourceTreeVO.setType(ResourceTreeTypeEnum.CABINETS.getCode());
				resourceTreeVO.setName(String.valueOf(cabinets.get(CmdbAttrConstant.FULL_NAME)));
				resourceTreeVO.setCiId(String.valueOf(cabinets.get(CmdbAttrConstant.CI_ID)));
				resourceTreeVO.setCiEntityId(Long.valueOf(String.valueOf(cabinets.get(CmdbAttrConstant.ID))));
				resourceTreeVO.setCabinetCapacity(ObjectUtil.isNotEmpty(cabinets.get(CmdbAttrConstant.CABINET_CAPACITY)) ? (int) Double.parseDouble(String.valueOf(cabinets.get(CmdbAttrConstant.CABINET_CAPACITY))) : 0);
				resourceTreeVOArrayList.add(resourceTreeVO);
			});
		}
		if (StringUtil.equals(ResourceTreeTypeEnum.CABINETS.getCode(), type)) {
			Query query = new Query();
			query.setCurrent(1);
			query.setSize(999);
			DeviceResourceVo deviceResourceVo = new DeviceResourceVo();
			deviceResourceVo.setCurrent(1);
			deviceResourceVo.setSize(999);
			deviceResourceVo.setCabinetCode(id);
			List<CiCientitySearchVO> ciCientitySearchVOS = CiCientitySearchVO.convertCiCientitySearchVO(deviceResourceVo);
			FeignCiCientity jsonObject = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
			List<Map<String, Object>> data = jsonObject.getData();
			data.forEach((device) -> {
				ResourceTreeVO resourceTreeVO = new ResourceTreeVO();
				resourceTreeVO.setId(String.valueOf(device.get("id")));
				resourceTreeVO.setCiId(String.valueOf(device.get("ciId")));
				resourceTreeVO.setName(String.valueOf(device.get("deviceName")));
				resourceTreeVO.setType(ResourceTreeTypeEnum.DEVICE.getCode());
				resourceTreeVOArrayList.add(resourceTreeVO);
			});
		}
		if (StringUtil.equals(ResourceTreeTypeEnum.WAREHOUSE.getCode(), type)) {
			Warehouse warehouse = new Warehouse();
			warehouse.setOwnerUnitId(id);
			List<WarehouseVO> warehouseVOList = iWarehouseService.findList(warehouse);
			for (WarehouseVO warehouseVO : warehouseVOList) {
				ResourceTreeVO resourceTreeVO = new ResourceTreeVO();
				resourceTreeVO.setId(warehouseVO.getUuid());
				resourceTreeVO.setType("warehouse");
				resourceTreeVO.setName(warehouseVO.getWarehouseName());
				resourceTreeVOArrayList.add(resourceTreeVO);
			}
		}
		return resourceTreeVOArrayList;
	}

	@Override
	public Integer delete(List<String> idList) {
		return baseMapper.deleteBatchIds(idList);
	}

	@Override
	public R getDeviceList(DeviceCmdbDTO deviceCmdbDTO, Query query) {
		IdevelopUser user = SecureUtil.getUser();
		List<CiCientitySearchVO> ciCientitySearchVOS = CiCientitySearchVO.convertCiCientitySearchVO(deviceCmdbDTO);
		String cabinets = cmdbCientityProperties.getCientityId(CmdbCientityConstant.T10603);
		if (0 == deviceCmdbDTO.getIsCabinetsMark()) {
			//设备列表选择设备
			CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_TYPE_CODE).attrValue(cabinets).expression(Expression.UNEQUAL).build();
			ciCientitySearchVOS.add(searchVO1);
			CiCientitySearchVO searchVO3 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.COMPUTER_ROOM_CODE).expression(Expression.ISNULL).build();
			ciCientitySearchVOS.add(searchVO3);
		} else {
			//机柜列表选择设备
			CiCientitySearchVO searchVO3 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.COMPUTER_ROOM_CODE).expression(Expression.ISNULL).build();
			ciCientitySearchVOS.add(searchVO3);
		}
		if (StringUtil.isBlank(deviceCmdbDTO.getDeviceCategoryCode())) {
			String assist = cmdbCientityProperties.getCientityId(CmdbCientityConstant.T106);
			String foundations = cmdbCientityProperties.getCientityId(CmdbCientityConstant.T109);
			CiCientitySearchVO searchVO4 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_CATEGORY_CODE).attrValue(assist + "," + foundations).expression(Expression.LIKE).build();
			ciCientitySearchVOS.add(searchVO4);
		}
		FeignCiCientity jsonObject = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
		return R.data(jsonObject);
	}

	@Override
	public R submit(List<DeviceCmdbDTO> resourceCabinetsList) {
		Map<Long, Map<String, Object>> updateMap = new HashMap<>();
		for (DeviceCmdbDTO device : resourceCabinetsList) {
			Map<String, Object> toMap = BeanUtil.beanToMap(device, false, true);
//			Map<String, Object> map = new HashMap<>();
			toMap.put(CmdbAttrConstant.UUID, device.getUuid());
			toMap.put(CmdbAttrConstant.CI_ID, device.getCiId());
			toMap.put(CmdbAttrConstant.COMPUTER_ROOM, device.getComputerRoom());
			toMap.put(CmdbAttrConstant.COMPUTER_ROOM_CODE, device.getComputerRoomCode());
			updateMap.put(device.getId(), toMap);
		}
		try {
			cmdbService.cientityBatchupdate(updateMap, TransactionActionType.UPDATE);
		} catch (Exception e) {
			return R.fail("关联失败");
		}
		return R.success("设备关联成功");
	}

	@Override
	public boolean batchUpdate(RoomWarehouseBatchDTO roomWarehouseBatchDTO) {

		String ids = roomWarehouseBatchDTO.getRoomWarehouseIds();
		List<String> idList = Func.toStrList(ids);
		for (String id : idList) {
			ResourceRoom resourceRoom = new ResourceRoom();
			resourceRoom.setUuid(id);
			resourceRoom.setI6000Uuid(roomWarehouseBatchDTO.getI6000Uuid());
			resourceRoom.setI6000Name(roomWarehouseBatchDTO.getI6000Name());
			this.updateById(resourceRoom);
		}

		return true;
	}

}
