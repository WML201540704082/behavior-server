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

import com.lnsoft.device.api.asset.dto.ExportRoom;
import com.lnsoft.device.api.asset.dto.ResourceRoomDTO;
import com.lnsoft.device.api.asset.entity.ResourceRoom;
import com.lnsoft.device.api.asset.vo.ResourceRoomVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 空间资源管理机房表 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-02-21
 */
public interface ResourceRoomMapper extends BaseMapper<ResourceRoom> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param resourceRoom
	 * @return
	 */
	List<ResourceRoomVO> selectResourceRoomPage(IPage page, ResourceRoomVO resourceRoom);
	/**
	 * 查询RoomId
	 *
	 * @param uuid
	 * @return
	 */
    String selectRoomIdById(String uuid);
	/**
	 * 根据Id查询机房数据
	 *
	 * @param id
	 * @return
	 */
	ExportRoom selectRoomById(String id);
	/**
	 * 查询所有的机房数据
	 *
	 * @param resourceRoom
	 * @return
	 */
	List<ExportRoom> selectRoomList(@Param("resourceRoom") ResourceRoomDTO resourceRoom);


	/**
	 * 根据地区父id查询
	 * @param page
	 * @param resourceRoom
	 * @return
	 */
	List<ResourceRoomVO> selectRoomByAreaId(IPage page, ResourceRoomVO resourceRoom);

	/**
	 * 根据地区id查询
	 * @param id
	 * @return
	 */
    List<ResourceRoom> findByAreaId(String id);

	/**
	 * 根据部门id查询
	 * @param resourceRoomDTO
	 * @return
	 */
	List<ResourceRoom> findByDeptId(ResourceRoomDTO resourceRoomDTO);
	/**
	 * 获取列表（刷新全称)
	 * @return
	 */
	List<ResourceRoomVO> getAll();
}
