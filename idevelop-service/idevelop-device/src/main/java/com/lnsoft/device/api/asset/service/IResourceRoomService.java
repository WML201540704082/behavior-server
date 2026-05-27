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
package com.lnsoft.device.api.asset.service;

	import com.lnsoft.core.mp.support.Query;
	import com.lnsoft.core.tool.api.R;
	import com.lnsoft.device.api.asset.dto.DeviceCmdbDTO;
	import com.lnsoft.device.api.asset.dto.ExportRoom;
	import com.lnsoft.device.api.asset.dto.ResourceQuery;
	import com.lnsoft.device.api.asset.dto.ResourceRoomDTO;
	import com.lnsoft.device.api.asset.entity.ResourceRoom;
	import com.lnsoft.device.api.asset.vo.ResourceRoomVO;
	import com.lnsoft.core.mp.base.BaseService;
	import com.baomidou.mybatisplus.core.metadata.IPage;
	import com.lnsoft.device.api.asset.vo.ResourceTreeVO;
	import com.lnsoft.device.api.warehouse.dto.RoomWarehouseBatchDTO;
	import org.springframework.web.bind.annotation.RequestBody;

	import javax.servlet.http.HttpServletResponse;
	import java.util.List;

/**
 * 空间资源管理机房表 服务类
 *
 * @author Idevelop
 * @since 2024-02-21
 */
public interface IResourceRoomService extends BaseService<ResourceRoom> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param resourceRoom
	 * @return
	 */
	IPage<ResourceRoomVO> selectResourceRoomPage(IPage<ResourceRoomVO> page, ResourceRoomVO resourceRoom);

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
	List<ExportRoom> selectRoomList(ResourceRoomDTO resourceRoom);

	/**
	 * 导出机房列表
	 *
	 * @param resourceRoomDTO
	 * @param response
	 */
	void export(ResourceRoomDTO resourceRoomDTO, HttpServletResponse response);


	/**
	 * 根据地区父id查询
	 *
	 * @param page
	 * @param resourceRoom
	 * @return
	 */
	IPage<ResourceRoomVO> selectRoomByAreaId(IPage<ResourceRoomVO> page, ResourceRoomVO resourceRoom);

	/**
	 * 树形结构 区域
	 *
	 * @param resourceQuery
	 * @return
	 */
	List<ResourceTreeVO> lazyTree(ResourceQuery resourceQuery);

	/**
	 * 树形结构 部门
	 *
	 * @param resourceQuery
	 * @return
	 */
	List<ResourceTreeVO> deptTree(ResourceQuery resourceQuery);

	Integer delete(List<String> idList);

	/**
	 * 机房选择设备
	 *
	 * @param deviceCmdbDTO
	 * @param query
	 * @return
	 */
	R getDeviceList(DeviceCmdbDTO deviceCmdbDTO, Query query);

	/**
	 * 关联设备保存
	 *
	 * @param resourceCabinetsList
	 * @return
	 */
	R submit(List<DeviceCmdbDTO> resourceCabinetsList);

	/**
	 * 批量修改关联机房
	 *
	 * @param roomWarehouseBatchDTO
	 * @return
	 */
	boolean batchUpdate(RoomWarehouseBatchDTO roomWarehouseBatchDTO);
}
