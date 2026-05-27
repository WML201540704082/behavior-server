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
package com.lnsoft.device.api.warehouse.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseService;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.device.api.warehouse.dto.RoomWarehouseBatchDTO;
import com.lnsoft.device.api.warehouse.dto.WarehouseDTO;
import com.lnsoft.device.api.warehouse.entity.Warehouse;
import com.lnsoft.device.api.warehouse.vo.WarehouseDictVO;
import com.lnsoft.device.api.warehouse.vo.WarehouseVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 仓库管理表 服务类
 *
 * @author Idevelop
 * @since 2024-03-05
 */
public interface IWarehouseService extends BaseService<Warehouse> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param warehouse
	 * @return
	 */
	IPage<Warehouse> selectWarehousePage(Query page, Warehouse warehouse);

	/**
	 * 根据所属单位查仓库
	 *
	 * @param ownerUnitId
	 * @return
	 */
	List<WarehouseDictVO> findByOwnerUnitId(String ownerUnitId);

//	/**
//	 * 在库设备统计
//	 * @return
//	 */
//	List<Warehouse> outStockedStatistics(WarehouseDTO warehouse);

//	/**
//	 * 仓库库存统计
//	 * @return
//	 */
//	List<Warehouse> deviceStatisticsStocked(WarehouseDTO warehouse);

	/**
	 * 根据区域查询仓库列表
	 *
	 * @return
	 */
	List<WarehouseVO> findByRegionCode(String regionCode);

	/**
	 * 根据区域和仓库名称查询仓库列表
	 *
	 * @param regionCode
	 * @param name
	 * @return
	 */
	List<WarehouseVO> findByRegionCodeAndName(String regionCode, String name);

	/**
	 * 根据单位查询仓库列表
	 *
	 * @return
	 */
	List<WarehouseVO> findList(Warehouse warehouse);

	/**
	 * 删除
	 *
	 * @return
	 */
	Integer delete(List<String> idList);

	/**
	 * 分页查询
	 *
	 * @return
	 */
	List<WarehouseVO> selectPage(Query query, WarehouseDTO warehouse);

	/**
	 * 仓库导出
	 *
	 * @param warehouseDTO
	 * @param response
	 */
	void export(WarehouseDTO warehouseDTO, HttpServletResponse response);

	/**
	 * 批量修改关联仓库
	 *
	 * @param roomWarehouseBatchDTO
	 * @return
	 */
	boolean batchUpdate(RoomWarehouseBatchDTO roomWarehouseBatchDTO);

}
