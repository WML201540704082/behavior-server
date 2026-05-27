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
package com.lnsoft.device.api.asset.mapper;

import com.lnsoft.device.api.asset.dto.ExportRacks;
import com.lnsoft.device.api.asset.dto.ResourceRacksDTO;
import com.lnsoft.device.api.asset.entity.ResourceRacks;
import com.lnsoft.device.api.asset.vo.ResourceRacksVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 空间资源管理机架表 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-02-21
 */
public interface ResourceRacksMapper extends BaseMapper<ResourceRacks> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param resourceRacks
	 * @return
	 */
	List<ResourceRacksVO> selectResourceRacksPage(IPage page, ResourceRacksVO resourceRacks);
	/**
	 * 根据cabinetsId查询机架
	 *
	 * @param cabinetsIds
	 * @return
	 */
	List<ResourceRacks> selectResourceRacksByCabinetsId(List<String> cabinetsIds);
	/**
	 * 查询机架列表
	 *
	 * @param resourceRacks
	 * @return
	 */
    List<ExportRacks> selectRacksList(ResourceRacksDTO resourceRacks);
	/**
	 * 根据id查询机架
	 *
	 * @param id
	 * @return
	 */
	ExportRacks selectRacksById(String id);
	/**
	 * 根据cabinetsId查询机架列表
	 * @param cabinetsId
	 * @return
	 */
    List<ResourceRacks> findByCabinetsId(String cabinetsId);
}
