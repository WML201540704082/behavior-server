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

import com.lnsoft.device.api.asset.dto.ExportCabinets;
import com.lnsoft.device.api.asset.dto.ResourceCabinetsDTO;
import com.lnsoft.device.api.asset.entity.ResourceCabinets;
import com.lnsoft.device.api.asset.entity.ResourceCabinetsLs;
import com.lnsoft.device.api.asset.vo.ResourceCabinetsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 空间资源管理机柜表 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-02-21
 */
public interface ResourceCabinetsMapper extends BaseMapper<ResourceCabinetsLs> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param resourceCabinets
	 * @return
	 */
	IPage<ResourceCabinetsLs> selectResourceCabinetsPage(IPage page, ResourceCabinetsLs resourceCabinets);

	/**
	 * 根据roomId查询机柜信息
	 *
	 * @param roomIds
	 * @return
	 */
	List<ResourceCabinets> selectResourceCabinetsListByRoomId(List<String> roomIds);
	/**
	 * 根据id查询机柜id
	 *
	 * @param id
	 * @return
	 */
	String selectCabinetsIdById(String id);
	/**
	 * 查询机柜列表
	 *
	 * @param resourceCabinets
	 * @return
	 */
    List<ExportCabinets> selectCabinetsList(ResourceCabinetsDTO resourceCabinets);
	/**
	 * 根据id查询机柜
	 *
	 * @param id
	 * @return
	 */
	ExportCabinets selectCabinetsById(String id);
	/**
	 * 根据roomId查询机柜
	 *
	 * @param roomId
	 * @return
	 */
    List<ResourceCabinets> findByRoomId(String roomId);

    void updateBatch(List<ResourceCabinets> list);

	/**
	 * 获取数据临时
	 *
	 * @return
	 */
	List<ResourceCabinetsVO> getAll();

}
