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
package com.lnsoft.device.api.warehouse.mapper;

import com.lnsoft.core.mp.support.Query;
import com.lnsoft.device.api.warehouse.dto.WarehouseDTO;
import com.lnsoft.device.api.warehouse.entity.Warehouse;
import com.lnsoft.device.api.warehouse.vo.WarehouseDictVO;
import com.lnsoft.device.api.warehouse.vo.WarehouseVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 仓库管理表 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-03-05
 */
public interface WarehouseMapper extends BaseMapper<Warehouse> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param warehouse
	 * @return
	 */
	List<WarehouseVO> selectWarehousePage(IPage page, WarehouseVO warehouse);
	/**
	 * 根据所属单位查仓库
	 * @param ownerUnitId
	 * @return
	 */
    List<WarehouseDictVO> findByOwnerUnitId(String ownerUnitId);

	/**
	 * 在库设备统计
	 * @param param
	 * @return
	 */
	List<Warehouse> deviceStatistics(Map<String, Object> param);
	/**
	 * 根据区域查询仓库列表
	 * @return
	 */
	List<WarehouseVO> findByRegionCode(String regionCode);

	/**
	 * 根据单位查询仓库列表
	 * @param warehouse
	 * @return
	 */
	List<WarehouseVO> findList(Warehouse warehouse);

	List<WarehouseVO> findPage(@Param("startIndex") Integer startIndex,
							   @Param("size") Integer size,
							   @Param("warehouse") WarehouseDTO warehouse);

	List<WarehouseVO> findByRegionCodeAndName(String regionCode, String name);



	List<WarehouseVO> getAll();
}
