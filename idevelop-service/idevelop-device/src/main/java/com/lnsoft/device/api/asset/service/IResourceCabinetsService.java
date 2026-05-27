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

import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.asset.dto.DeviceCmdbDTO;
import com.lnsoft.device.api.asset.dto.ExportCabinets;
import com.lnsoft.device.api.asset.dto.ResourceCabinetsDTO;
import com.lnsoft.device.api.asset.entity.ResourceCabinets;
import com.lnsoft.device.api.asset.entity.ResourceCabinetsLs;
import com.lnsoft.device.api.asset.vo.ResourceCabinetsVO;
import com.lnsoft.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 空间资源管理机柜表 服务类
 *
 * @author Idevelop
 * @since 2024-02-21
 */
public interface IResourceCabinetsService extends BaseService<ResourceCabinetsLs> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param resourceCabinets
	 * @return
	 */
	IPage<ResourceCabinetsLs> selectResourceCabinetsPage(IPage<ResourceCabinetsLs> page, ResourceCabinetsLs resourceCabinets);
	/**
	 * 根据room_id查询机柜信息
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
	 * 导出
	 *
	 * @param resourceCabinetsDTO
	 * @return
	 */
    void export(ResourceCabinetsDTO resourceCabinetsDTO, HttpServletResponse servletResponse);

	/**
	 * 根据roomid查询机柜列表
	 * @param id
	 * @return
	 */
	List<ResourceCabinets> findByRoomId(String id);

    void updateBatch(List<ResourceCabinets> list);

    Integer delete(List<String> toStrList);

	/**
	 * 机柜列表分页
	 * @param resourceCabinets
	 * @param query
	 * @return
	 */
	R getList(ResourceCabinetsDTO resourceCabinets, Query query);

	/**
	 * 移除机柜
	 * @param resourceCabinetsList
	 * @return
	 */
	R removeCabinets(List<ResourceCabinets> resourceCabinetsList);

	/**
	 * 机柜选择设备
	 * @param deviceCmdbDTO
	 * @param query
	 * @return
	 */
	R getDeviceList(DeviceCmdbDTO deviceCmdbDTO, Query query);

	/**
	 * U位校验
	 * @param resourceCabinetsDTO
	 * @return
	 */
	R check(ResourceCabinetsDTO resourceCabinetsDTO);

	/**
	 * 关联设备提交
	 * @param resourceCabinetsDTO
	 * @return
	 */
	R submit(ResourceCabinetsDTO resourceCabinetsDTO);

	/**
	 * 获取u位
	 * @param resourceCabinetsList
	 * @return
	 */
	R getPlace(ResourceCabinetsDTO resourceCabinetsList);

	/**
	 * 机柜移除设备
	 * @param resourceCabinetsList
	 * @return
	 */
	R removeDev(List<ResourceCabinets> resourceCabinetsList);

	/**
	 * 机柜下拉（根据机房查询）
	 * @param resourceCabinets
	 * @return
	 */
	FeignCiCientity getCabinets(ResourceCabinetsDTO resourceCabinets, Query query);
}
