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
import com.lnsoft.device.api.asset.dto.DeviceOldListDTO;
import com.lnsoft.device.api.asset.entity.DeviceOldList;
import com.lnsoft.device.api.asset.vo.DeviceOldListVO;
import com.lnsoft.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 老旧设备表	 服务类
 *
 * @author Idevelop
 * @since 2024-06-19
 */
public interface IDeviceOldListService extends BaseService<DeviceOldList> {

	/**
	 * 自定义分页
	 *
	 * @param query
	 * @param deviceOldList
	 * @return
	 */
	IPage<DeviceOldList> selectDeviceOldListPage(Query query, DeviceOldListVO deviceOldList);
	/**
	 * 修改自评 老旧设备表
	 * @param deviceOldList
	 */
	R change(List<DeviceOldList> deviceOldList);
	/**
	 * 批量新增设备 老旧设备表
	 * @param deviceOldList
	 */
	List<DeviceOldList> insert(List<DeviceOldList> deviceOldList);
	/**
	 * 详情
	 * @param deviceOldList
	 * @return
	 */
	DeviceOldListVO detail(DeviceOldListDTO deviceOldList);

	/**
	 * 分数计算
	 * @param deviceOldList
	 * @return
	 */
	R compute(List<DeviceOldList> deviceOldList);

	/**
	 * 获取设备列表（带参考年限）
	 * @param deviceOldListDTO
	 * @return
	 */
	R getDeviceList(DeviceOldListDTO deviceOldListDTO, Query query);

	/**
	 * 获取排名（本类设备）
	 * @param deviceOldListDTO
	 * @return
	 */
	Integer getRank(DeviceOldListDTO deviceOldListDTO);

	/**
	 * 补充材料
	 * @param deviceOldListDTOList
	 * @return
	 */
	R refine(List<DeviceOldListDTO> deviceOldListDTOList) throws Exception;

	/**
	 * 评审库审批
	 * @param deviceOldListDTOList
	 * @return
	 */
	R approval(List<DeviceOldList> deviceOldListDTOList);

	/**
	 * 评审库列表查询
	 * @param query
	 * @param deviceOldList
	 * @return
	 */
	IPage<DeviceOldList> getList(Query query, DeviceOldListVO deviceOldList);
}
