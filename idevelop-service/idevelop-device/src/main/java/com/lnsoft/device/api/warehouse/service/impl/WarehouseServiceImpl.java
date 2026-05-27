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
package com.lnsoft.device.api.warehouse.service.impl;

import cn.hutool.core.convert.Convert;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.utils.DateUtil;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.warehouse.dto.ExportWarehouseDTO;
import com.lnsoft.device.api.warehouse.dto.RoomWarehouseBatchDTO;
import com.lnsoft.device.api.warehouse.dto.WarehouseDTO;
import com.lnsoft.device.api.warehouse.entity.Warehouse;
import com.lnsoft.device.api.warehouse.mapper.WarehouseMapper;
import com.lnsoft.device.api.warehouse.service.IWarehouseService;
import com.lnsoft.device.api.warehouse.vo.WarehouseDictVO;
import com.lnsoft.device.api.warehouse.vo.WarehouseVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 仓库管理表 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-05
 */
@Service
public class WarehouseServiceImpl extends BaseServiceImpl<WarehouseMapper, Warehouse> implements IWarehouseService {

	@Override
	public IPage<Warehouse> selectWarehousePage(Query query, Warehouse warehouse) {
		IdevelopUser user = SecureUtil.getUser();
		LambdaQueryWrapper<Warehouse> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.like(StringUtil.isNotBlank(warehouse.getRegionCode()), Warehouse::getRegionCode, warehouse.getRegionCode());
		queryWrapper.like(StringUtil.isNotBlank(warehouse.getWarehouseName()), Warehouse::getWarehouseName, warehouse.getWarehouseName());
		queryWrapper.like(StringUtil.isNotBlank(warehouse.getWarehouseId()), Warehouse::getWarehouseId, warehouse.getWarehouseId());
		queryWrapper.eq(StringUtil.isNotBlank(warehouse.getOwnerUnit()), Warehouse::getOwnerUnit, warehouse.getOwnerUnit());
		queryWrapper.eq(StringUtil.isNotBlank(warehouse.getOwnerUnitId()), Warehouse::getOwnerUnitId, warehouse.getOwnerUnitId());
		queryWrapper.orderByDesc(Warehouse::getUpdateTime);
		return baseMapper.selectPage(Condition.getPage(query), queryWrapper);

	}

	@Override
	public List<WarehouseDictVO> findByOwnerUnitId(String ownerUnitId) {
		List<WarehouseDictVO> list = baseMapper.findByOwnerUnitId(ownerUnitId);
		return list;
	}

	@Override
	public List<WarehouseVO> findByRegionCode(String regionCode) {
		return baseMapper.findByRegionCode(regionCode);
	}

	@Override
	public List<WarehouseVO> findByRegionCodeAndName(String regionCode, String name) {
		return baseMapper.findByRegionCodeAndName(regionCode, name);
	}

	@Override
	public List<WarehouseVO> findList(Warehouse warehouse) {
		return baseMapper.findList(warehouse);
	}

	@Override
	public Integer delete(List<String> idList) {
		return baseMapper.deleteBatchIds(idList);
	}

	@Override
	public List<WarehouseVO> selectPage(Query query, WarehouseDTO warehouse) {
		Integer startIndex = (query.getCurrent() - 1) * query.getSize();
		Integer size = query.getSize();
		return baseMapper.findPage(startIndex, size, warehouse);
	}

	@Override
	public void export(WarehouseDTO warehouseDTO, HttpServletResponse response) {
		List<WarehouseVO> warehouseList = new ArrayList<>();
		String ids = warehouseDTO.getIds();
		IdevelopUser user = SecureUtil.getUser();
		if ("".equals(ids)) {
			warehouseList = findByRegionCode(user.getRegionCode());
		} else {
			List<String> idList = Func.toStrList(ids);
			for (String id : idList) {
				Warehouse warehouse = this.getById(id);
				warehouseList.add(Convert.convert(WarehouseVO.class, warehouse));
			}
		}
		try {
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			String fileName = URLEncoder.encode("仓库列表导出", StandardCharsets.UTF_8.name());
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
			EasyExcel.write(response.getOutputStream(), ExportWarehouseDTO.class).sheet("仓库列表").doWrite(warehouseList);
		} catch (IOException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public boolean batchUpdate(RoomWarehouseBatchDTO roomWarehouseBatchDTO) {

		String ids = roomWarehouseBatchDTO.getRoomWarehouseIds();
		List<String> idList = Func.toStrList(ids);
		for (String id : idList) {
			Warehouse warehouse = new Warehouse();
			warehouse.setUuid(id);
			warehouse.setI6000Uuid(roomWarehouseBatchDTO.getI6000Uuid());
			warehouse.setI6000Name(roomWarehouseBatchDTO.getI6000Name());
			this.updateById(warehouse);
		}

		return true;
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
		String nowDay = DateUtil.formatDate(now);
		if ("7day".equals(type)) {
			Date nextDay = DateUtil.minusWeeks(now, 1);
			array = DateUtil.formatDate(nextDay) + '~' + nowDay;
		} else if ("month".equals(type)) {
			Date nextDay = new Date();
			nextDay.setDate(1);
			array = DateUtil.formatDate(nextDay) + '~' + nowDay;
		} else if ("year".equals(type)) {
			Date nextDay = DateUtil.minusYears(now, 1);
			array = DateUtil.formatDate(nextDay) + '~' + nowDay;
		}
		return array;
	}

}
