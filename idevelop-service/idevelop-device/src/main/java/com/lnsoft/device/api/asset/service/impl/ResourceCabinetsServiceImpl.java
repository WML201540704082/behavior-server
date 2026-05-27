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
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.asset.dto.DeviceCmdbDTO;
import com.lnsoft.device.api.asset.dto.ExportCabinets;
import com.lnsoft.device.api.asset.dto.ResourceCabinetsDTO;
import com.lnsoft.device.api.asset.entity.ResourceCabinets;
import com.lnsoft.device.api.asset.entity.ResourceCabinetsLs;
import com.lnsoft.device.api.asset.mapper.ResourceCabinetsMapper;
import com.lnsoft.device.api.asset.service.IResourceCabinetsService;
import com.alibaba.excel.EasyExcel;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.device.entity.CiCientitySearch;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.constant.CmdbCientityConstant;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.vo.CiCientitySearchVO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 空间资源管理机柜表 服务实现类
 *
 * @author Idevelop
 * @since 2024-02-21
 */
@Service
public class ResourceCabinetsServiceImpl extends BaseServiceImpl<ResourceCabinetsMapper, ResourceCabinetsLs> implements IResourceCabinetsService {
	@Resource
	private ICmdbService cmdbService;
	@Resource
	private CmdbCientityProperties cmdbCientityProperties;

	@Override
	public IPage<ResourceCabinetsLs> selectResourceCabinetsPage(IPage<ResourceCabinetsLs> page, ResourceCabinetsLs resourceCabinets) {
		return baseMapper.selectResourceCabinetsPage(page, resourceCabinets);
	}

	@Override
	public List<ResourceCabinets> selectResourceCabinetsListByRoomId(List<String> roomIds) {
		return baseMapper.selectResourceCabinetsListByRoomId(roomIds);
	}

	@Override
	public String selectCabinetsIdById(String id) {
		return baseMapper.selectCabinetsIdById(id);
	}

	@Override
	public List<ExportCabinets> selectCabinetsList(ResourceCabinetsDTO resourceCabinets) {
		return baseMapper.selectCabinetsList(resourceCabinets);
	}

	@Override
	public ExportCabinets selectCabinetsById(String id) {
		return baseMapper.selectCabinetsById(id);
	}

	@Override
	public void export(ResourceCabinetsDTO resourceCabinetsDTO, HttpServletResponse servletResponse) {
		String ids = resourceCabinetsDTO.getIds();
		List<ExportCabinets> cabinetsList = new ArrayList<>();
		if ("".equals(ids)) {
			cabinetsList = selectCabinetsList(resourceCabinetsDTO);
		} else {
			List<String> idList = Func.toStrList(ids);
			for (String id : idList) {
				ExportCabinets exportCabinets = selectCabinetsById(id);
				cabinetsList.add(exportCabinets);
			}
		}
		try {
			servletResponse.setContentType("application/vnd.ms-excel");
			servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
			String fileName = URLEncoder.encode("机柜列表导出", StandardCharsets.UTF_8.name());
			servletResponse.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
			EasyExcel.write(servletResponse.getOutputStream(), ExportCabinets.class).sheet("机柜列表").doWrite(cabinetsList);
		} catch (IOException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public List<ResourceCabinets> findByRoomId(String roomId) {
		return baseMapper.findByRoomId(roomId);
	}

	@Override
	public void updateBatch(List<ResourceCabinets> list) {
		baseMapper.updateBatch(list);
	}

	@Override
	public Integer delete(List<String> toStrList) {
		return baseMapper.deleteBatchIds(toStrList);
	}

	@Override
	public R getList(ResourceCabinetsDTO resourceCabinets, Query query) {
		IdevelopUser user = SecureUtil.getUser();
		ResourceCabinets cabinets1 = new ResourceCabinets();
		BeanUtil.copyProperties(resourceCabinets,cabinets1);
		List<CiCientitySearchVO> ciCientitySearchVOS = CiCientitySearchVO.convertCiCientitySearchVO(cabinets1);
		if (StringUtil.isNotBlank(resourceCabinets.getFindType())){
			if (resourceCabinets.getFindType().equals("device")){
				String cabinets = cmdbCientityProperties.getCientityId(CmdbCientityConstant.T10603);
				CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_TYPE_CODE).attrValue(cabinets).expression(Expression.UNEQUAL).build();
				ciCientitySearchVOS.add(searchVO1);
			}
		}
		if ("1".equals(resourceCabinets.getNullMark())) {
			CiCientitySearchVO searchVO = new CiCientitySearchVO();
			searchVO.setAttrName(CmdbAttrConstant.CABINET_CODE);
			searchVO.setExpression(Expression.ISNULL);
			ciCientitySearchVOS.add(searchVO);
		}
		if (ObjectUtil.isNotEmpty(resourceCabinets.getOprtDateEnd()) && ObjectUtil.isNotEmpty(resourceCabinets.getOprtDateBegin())) {
			CiCientitySearchVO searchVO = new CiCientitySearchVO();
			searchVO.setAttrName(CmdbAttrConstant.OPRT_DATE);
			searchVO.setExpression(Expression.BETWEEN);
			searchVO.setAttrValue(resourceCabinets.getOprtDateBegin() + "~" + resourceCabinets.getOprtDateEnd());
			ciCientitySearchVOS.add(searchVO);
		}
		FeignCiCientity jsonObject = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
		return R.data(jsonObject);
	}

	@Override
	public R removeCabinets(List<ResourceCabinets> resourceCabinetsList) {
		Query query = new Query();
		Map<Long, Map<String, Object>> updateMap = new HashMap<>();
		for (ResourceCabinets cabinets : resourceCabinetsList) {
			Map<String, Object> map = BeanUtil.beanToMap(cabinets, false, true);
			//判断机柜下是否有设备
			List<CiCientitySearchVO> ciCientitySearchVOS = new ArrayList<>();
			CiCientitySearchVO searchVO = new CiCientitySearchVO();
			searchVO.setAttrName(CmdbAttrConstant.CABINET_CODE);
			searchVO.setExpression(Expression.EQUAL);
			searchVO.setAttrValue(cabinets.getId());
			ciCientitySearchVOS.add(searchVO);
			FeignCiCientity jsonObject = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
			if (jsonObject.getTotal()>0){
				return R.fail("机柜下存在设备，不可移除！");
			}
			map.put(CmdbAttrConstant.UUID, cabinets.getUuid());
			map.put(CmdbAttrConstant.CI_ID, cabinets.getCiId());
			map.put(CmdbAttrConstant.COMPUTER_ROOM, null);
			map.put(CmdbAttrConstant.COMPUTER_ROOM_CODE, null);
			updateMap.put(cabinets.getId(), map);
		}
		try {
			cmdbService.cientityBatchupdate(updateMap, TransactionActionType.UPDATE);
		} catch (Exception e) {
			return R.fail("移除失败");
		}
		return R.success("操作成功");
	}

	@Override
	public R getDeviceList(DeviceCmdbDTO deviceCmdbDTO, Query query) {
		List<CiCientitySearchVO> ciCientitySearchVOS = new ArrayList<>();
		//区域
		if (StringUtil.isNotBlank(deviceCmdbDTO.getArea())) {
			CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.AREA).attrValue(deviceCmdbDTO.getArea()).expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO1);
		}
		//mac
		if (StringUtil.isNotBlank(deviceCmdbDTO.getMAC())) {
			CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.MAC).attrValue(deviceCmdbDTO.getMAC()).expression(Expression.LIKE).build();
			ciCientitySearchVOS.add(searchVO1);
		}
		//ip
		if (StringUtil.isNotBlank(deviceCmdbDTO.getIP())) {
			CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.IP).attrValue(deviceCmdbDTO.getIP()).expression(Expression.LIKE).build();
			ciCientitySearchVOS.add(searchVO1);
		}
		//设备分类
		if (StringUtil.isNotBlank(deviceCmdbDTO.getDeviceCategoryCode())) {
			CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_CATEGORY_CODE).attrValue(deviceCmdbDTO.getDeviceCategoryCode()).expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO1);
		} else {
			String computer = cmdbCientityProperties.getCientityId(CmdbCientityConstant.T101);
			String save = cmdbCientityProperties.getCientityId(CmdbCientityConstant.T102);
			String net = cmdbCientityProperties.getCientityId(CmdbCientityConstant.T103);
			String safe = cmdbCientityProperties.getCientityId(CmdbCientityConstant.T104);
			CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_CATEGORY_CODE).attrValue(computer + "," + save + "," + net + "," + safe).expression(Expression.LIKE).build();
			ciCientitySearchVOS.add(searchVO1);
		}
		//设备类型
		if (StringUtil.isNotBlank(deviceCmdbDTO.getDeviceTypeCode())) {
			CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_TYPE_CODE).attrValue(deviceCmdbDTO.getDeviceTypeCode()).expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO1);
		}
		//设备来源
		if (StringUtil.isNotBlank(deviceCmdbDTO.getDeviceSourceCode())) {
			CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_SOURCE_CODE).attrValue(deviceCmdbDTO.getDeviceSourceCode()).expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO1);
		}
		//品牌
		if (StringUtil.isNotBlank(deviceCmdbDTO.getBrandCode())) {
			CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.BRAND_CODE).attrValue(deviceCmdbDTO.getBrandCode()).expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO1);
		}
		//系列
		if (StringUtil.isNotBlank(deviceCmdbDTO.getSeriesCode())) {
			CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.SERIES_CODE).attrValue(deviceCmdbDTO.getSeriesCode()).expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO1);
		}
		//型号
		if (StringUtil.isNotBlank(deviceCmdbDTO.getDeviceModelCode())) {
			CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_MODEL_CODE).attrValue(deviceCmdbDTO.getDeviceModelCode()).expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO1);
		}

		//设备编码
		if (StringUtil.isNotBlank(deviceCmdbDTO.getDeviceCode())) {
			CiCientitySearchVO searchVO3 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_CODE).attrValue(deviceCmdbDTO.getDeviceCode()).expression(Expression.LIKE).build();
			ciCientitySearchVOS.add(searchVO3);
		}
		//设备状态
		if (StringUtil.isNotBlank(deviceCmdbDTO.getDeviceStatusCode())) {
			CiCientitySearchVO searchVO3 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_STATUS_CODE).attrValue(deviceCmdbDTO.getDeviceStatusCode()).expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO3);
		}
		//设备高度
		if (StringUtil.isNotBlank(deviceCmdbDTO.getDeviceHeight())) {
			CiCientitySearchVO searchVO3 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_HEIGHT).attrValue(deviceCmdbDTO.getDeviceHeight()).expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO3);
		}
		//所属机房
		if (StringUtil.isNotBlank(deviceCmdbDTO.getComputerRoomCode())) {
			CiCientitySearchVO searchVO3 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.COMPUTER_ROOM_CODE).attrValue(deviceCmdbDTO.getComputerRoomCode()).expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO3);
		}
		//所属机柜
		if (StringUtil.isNotBlank(deviceCmdbDTO.getCabinetCode())) {
			CiCientitySearchVO searchVO3 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.CABINET_CODE).attrValue(deviceCmdbDTO.getCabinetCode()).expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO3);
		} else {
			String cabinets = deviceCmdbDTO.getCabinets();
			CiCientitySearchVO searchVO2 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.CABINET_CODE).expression(Expression.ISNULL).build();
			ciCientitySearchVOS.add(searchVO2);
		}
//		CiCientitySearchVO searchVO = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.AREA).attrValue(user.getRegionCode()).expression(Expression.EQUAL).build();
//		ciCientitySearchVOS.add(searchVO);
//		CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_HEIGHT).attrValue(deviceCmdbDTO.getDeviceHeightStart() + "~" + deviceCmdbDTO.getDeviceHeightOver()).expression(Expression.BETWEEN).build();
//		ciCientitySearchVOS.add(searchVO1);
		FeignCiCientity jsonObject = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
		return R.data(jsonObject);
	}

	@Override
	public R check(ResourceCabinetsDTO resourceCabinetsDTO) {
		Integer cabinetCapacity = resourceCabinetsDTO.getCabinetCapacity();
		List<Integer> cabinetCapacityList = IntStream.rangeClosed(1, cabinetCapacity).boxed().collect(Collectors.toList());
		//获取存量机柜
		Long cabinetsDTOId = resourceCabinetsDTO.getId();
		Query query = new Query();
		query.setCurrent(1);
		query.setSize(99);
		CiCientitySearchVO searchVO = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.CABINET_CODE).attrValue(cabinetsDTOId).expression(Expression.EQUAL).build();
		ArrayList<CiCientitySearchVO> ciCientitySearchVOS = new ArrayList<>();
		ciCientitySearchVOS.add(searchVO);
		FeignCiCientity jsonObject = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
		//删除存量机柜占用U位
		if (ObjectUtil.isNotEmpty(jsonObject.getData())) {
			for (Map<String, Object> device : jsonObject.getData()) {
				Integer deviceHeight = Integer.parseInt(String.valueOf(device.get(CmdbAttrConstant.DEVICE_HEIGHT)));
				Integer deviceHeightBegin = Integer.parseInt(String.valueOf(device.get(CmdbAttrConstant.DEVICE_HEIGHT_BEGIN)));
				List<Integer> integerList = getNumList(deviceHeightBegin, deviceHeight);
				cabinetCapacityList.removeAll(integerList);
			}
		}
		List<DeviceCmdbDTO> deviceList = resourceCabinetsDTO.getDeviceList();
		for (DeviceCmdbDTO deviceCmdbDTO : deviceList) {
			Integer deviceHeight = Integer.parseInt(deviceCmdbDTO.getDeviceHeight());
			Integer deviceHeightBegin = Integer.parseInt(deviceCmdbDTO.getDeviceHeightBegin());
			List<Integer> integerList = getNumList(deviceHeightBegin, deviceHeight);
			if (cabinetCapacityList.containsAll(integerList)) {
				cabinetCapacityList.removeAll(integerList);
				deviceCmdbDTO.setCheckMark(0);
				deviceCmdbDTO.setCheckPrompt("U位校验通过");
			} else {
				deviceCmdbDTO.setCheckMark(1);
				deviceCmdbDTO.setCheckPrompt("当前机柜无法容纳所选设备！");
			}
		}
		return R.data(deviceList);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R submit(ResourceCabinetsDTO resourceCabinetsDTO) {
		R check = check(resourceCabinetsDTO);
		List<DeviceCmdbDTO> deviceCmdbDTOList = (List<DeviceCmdbDTO>) check.getData();
		Integer mark = 0;
		for (DeviceCmdbDTO deviceCmdbDTO : deviceCmdbDTOList) {
			if (deviceCmdbDTO.getCheckMark() == 0) {
				continue;
			} else {
				mark = 1;
				break;
			}
		}
		if (mark == 1) {
			check.setMsg("提交异常，存在机柜放不下的设备");
			return check;
		}
		List<DeviceCmdbDTO> resourceCabinetsList = resourceCabinetsDTO.getDeviceList();
		Map<Long, Map<String, Object>> updateMap = new HashMap<>();
		CiCientitySearch ciCientitySearch = new CiCientitySearch();
		ciCientitySearch.setFilterCiEntityId(resourceCabinetsDTO.getId());
		ciCientitySearch.setFullField(Boolean.TRUE);
		ciCientitySearch.setQuery(new Query());
		FeignCiCientity jsonObject = cmdbService.getCiCientityListByCondition(ciCientitySearch);
		String computerRoom = String.valueOf(jsonObject.getData().get(0).get(CmdbAttrConstant.COMPUTER_ROOM));
		String computerRoomCode = String.valueOf(jsonObject.getData().get(0).get(CmdbAttrConstant.COMPUTER_ROOM_CODE));
		int useCapacity = (int) Double.parseDouble(String.valueOf(jsonObject.getData().get(0).get(CmdbAttrConstant.CABINET_USE_CAPACITY)));
		for (DeviceCmdbDTO device : resourceCabinetsList) {
			Map<String, Object> map = BeanUtil.beanToMap(device, false, true);
//			Map<String, Object> map = new HashMap<>();
			map.put(CmdbAttrConstant.UUID, device.getUuid());
			map.put(CmdbAttrConstant.CI_ID, device.getCiId());
			map.put(CmdbAttrConstant.CABINET, String.valueOf(jsonObject.getData().get(0).get(CmdbAttrConstant.DEVICE_NAME)));
			map.put(CmdbAttrConstant.CABINET_CODE, String.valueOf(jsonObject.getData().get(0).get(CmdbAttrConstant.ID)));
			map.put(CmdbAttrConstant.COMPUTER_ROOM, computerRoom);
			map.put(CmdbAttrConstant.COMPUTER_ROOM_CODE, computerRoomCode);
			map.put(CmdbAttrConstant.DEVICE_HEIGHT, device.getDeviceHeight());
			map.put(CmdbAttrConstant.DEVICE_HEIGHT_BEGIN, device.getDeviceHeightBegin());
			int height = Integer.parseInt(device.getDeviceHeight());
			int heightBegin = Integer.parseInt(device.getDeviceHeightBegin());
			int heightEnd = heightBegin + height - 1;
			map.put(CmdbAttrConstant.DEVICE_HEIGHT_END, String.valueOf(heightEnd));
			updateMap.put(device.getId(), map);
			useCapacity = useCapacity + height;
		}
		try {
			cmdbService.cientityBatchupdate(updateMap, TransactionActionType.UPDATE);
		} catch (Exception e) {
			return R.fail("关联失败");
		}
		//更新机柜容量
		Map<Long, Map<String, Object>> cabinetMap = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		map.put(CmdbAttrConstant.UUID, jsonObject.getData().get(0).get(CmdbAttrConstant.UUID));
		map.put(CmdbAttrConstant.CI_ID, jsonObject.getData().get(0).get(CmdbAttrConstant.CI_ID));
		cabinetMap.put(Long.valueOf(String.valueOf(jsonObject.getData().get(0).get(CmdbAttrConstant.ID))), map);
		try {
			cmdbService.cientityBatchupdate(cabinetMap, TransactionActionType.UPDATE);
		} catch (Exception e) {
			return R.fail("机柜使用容量更新失败");
		}
		List<DeviceCmdbDTO> list = new ArrayList<>();
		return R.data(list);

	}

	@Override
	public R getPlace(ResourceCabinetsDTO resourceCabinetsList) {
		Integer cabinetCapacity = resourceCabinetsList.getCabinetCapacity();
		List<Integer> cabinetCapacityList = IntStream.rangeClosed(1, cabinetCapacity).boxed().collect(Collectors.toList());
		//获取存量机柜
		String cabinetsUUID = resourceCabinetsList.getUuid();
		Query query = new Query();
		query.setCurrent(1);
		query.setSize(99);
		CiCientitySearchVO searchVO = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.CABINET_CODE).attrValue(cabinetsUUID).expression(Expression.EQUAL).build();
		ArrayList<CiCientitySearchVO> ciCientitySearchVOS = new ArrayList<>();
		ciCientitySearchVOS.add(searchVO);
		FeignCiCientity jsonObject = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
		//删除存量机柜占用U位
		if (ObjectUtil.isNotEmpty(jsonObject.getData())) {
			for (Map<String, Object> device : jsonObject.getData()) {
				Integer deviceHeight = Integer.parseInt(String.valueOf(device.get(CmdbAttrConstant.DEVICE_HEIGHT)));
				Integer deviceHeightBegin = Integer.parseInt(String.valueOf(device.get(CmdbAttrConstant.DEVICE_HEIGHT_BEGIN)));
				List<Integer> integerList = getNumList(deviceHeightBegin, deviceHeight);
				cabinetCapacityList.removeAll(integerList);
			}
		}
		List<DeviceCmdbDTO> deviceList = resourceCabinetsList.getDeviceList();
		for (DeviceCmdbDTO deviceCmdbDTO : deviceList) {
			Integer deviceHeight = Integer.parseInt(StringUtil.isNotBlank(deviceCmdbDTO.getDeviceHeight()) ? deviceCmdbDTO.getDeviceHeight() : "0");
			Integer deviceHeightBegin = Integer.parseInt(StringUtil.isNotBlank(deviceCmdbDTO.getDeviceHeightBegin()) ? deviceCmdbDTO.getDeviceHeightBegin() : "0");
			List<Integer> integers = new ArrayList<>();
			if(deviceHeightBegin>0){
				integers = getNumList(deviceHeightBegin, deviceHeight);
			}
			cabinetCapacityList.removeAll(integers);
		}
		return R.data(cabinetCapacityList);
	}

	@Override
	public R removeDev(List<ResourceCabinets> resourceCabinetsList) {
		String cabinetId = String.valueOf(resourceCabinetsList.get(0).getCabinetCode());
		CiCientitySearch ciCientitySearch = new CiCientitySearch();
		ciCientitySearch.setFilterCiEntityId(Long.valueOf(cabinetId));
		ciCientitySearch.setFullField(Boolean.TRUE);
		ciCientitySearch.setQuery(new Query());
		FeignCiCientity jsonObject = cmdbService.getCiCientityListByCondition(ciCientitySearch);
		int useCapacity = (int) Double.parseDouble(String.valueOf(jsonObject.getData().get(0).get(CmdbAttrConstant.CABINET_USE_CAPACITY)));
		Map<Long, Map<String, Object>> updateMap = new HashMap<>();
		for (ResourceCabinets cabinets : resourceCabinetsList) {
			Map<String, Object> map = new HashMap<>();
			map.put(CmdbAttrConstant.UUID, cabinets.getUuid());
			map.put(CmdbAttrConstant.CI_ID, cabinets.getCiId());
			map.put(CmdbAttrConstant.COMPUTER_ROOM, null);
			map.put(CmdbAttrConstant.COMPUTER_ROOM_CODE, null);
			map.put(CmdbAttrConstant.CABINET, null);
			map.put(CmdbAttrConstant.CABINET_CODE, null);
			map.put(CmdbAttrConstant.DEVICE_HEIGHT_BEGIN,null);
			map.put(CmdbAttrConstant.DEVICE_HEIGHT_END,null);
			updateMap.put(cabinets.getId(), map);
			Integer deviceHeight = cabinets.getDeviceHeight();
			useCapacity = useCapacity - deviceHeight;
		}
		try {
			cmdbService.cientityBatchupdate(updateMap, TransactionActionType.UPDATE);
		} catch (Exception e) {
			return R.fail("移除失败");
		}
		//更新机柜容量
		Map<Long, Map<String, Object>> cabinetMap = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		map.put(CmdbAttrConstant.UUID, jsonObject.getData().get(0).get(CmdbAttrConstant.UUID));
		map.put(CmdbAttrConstant.CI_ID, jsonObject.getData().get(0).get(CmdbAttrConstant.CI_ID));
		cabinetMap.put(Long.valueOf(String.valueOf(jsonObject.getData().get(0).get(CmdbAttrConstant.ID))), map);
		try {
			cmdbService.cientityBatchupdate(cabinetMap, TransactionActionType.UPDATE);
		} catch (Exception e) {
			return R.fail("机柜使用容量更新失败");
		}
		return R.success("操作成功");
	}

	@Override
	public FeignCiCientity getCabinets(ResourceCabinetsDTO resourceCabinets,Query query) {
		String computerRoomCode = resourceCabinets.getRoomId();
		ArrayList<CiCientitySearchVO> searchVOS = new ArrayList<>();
		CiCientitySearchVO ciCientitySearchVO = new CiCientitySearchVO();
		ciCientitySearchVO.setAttrName(CmdbAttrConstant.COMPUTER_ROOM_CODE);
		ciCientitySearchVO.setAttrValue(computerRoomCode);
		ciCientitySearchVO.setExpression(Expression.EQUAL);
		searchVOS.add(ciCientitySearchVO);
		String cabinets = cmdbCientityProperties.getCientityId(CmdbCientityConstant.T10603);
		CiCientitySearchVO ciCientitySearchVO1 = new CiCientitySearchVO();
		ciCientitySearchVO1.setAttrName(CmdbAttrConstant.DEVICE_TYPE_CODE);
		ciCientitySearchVO1.setAttrValue(cabinets);
		ciCientitySearchVO1.setExpression(Expression.EQUAL);
		searchVOS.add(ciCientitySearchVO1);
		if(StringUtil.isNotBlank(resourceCabinets.getCabinet())){
			CiCientitySearchVO ciCientitySearchVO2 = new CiCientitySearchVO();
			ciCientitySearchVO2.setAttrName(CmdbAttrConstant.FULL_NAME);
			ciCientitySearchVO2.setAttrValue(resourceCabinets.getCabinet());
			ciCientitySearchVO2.setExpression(Expression.EQUAL);
			searchVOS.add(ciCientitySearchVO2);
		}
		return cmdbService.getCiCientityListByClaccify(searchVOS, query);
	}

	private List<Integer> getNumList(Integer deviceHeightBegin, Integer deviceHeight) {
		List<Integer> integers = new ArrayList<>();
		for (int i = 0; i < deviceHeight; i++) {
			integers.add(deviceHeightBegin + i);
		}
		return integers;
	}


}
