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
package com.lnsoft.device.api.operation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseService;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.operation.dto.DeviceChangeDTO;
import com.lnsoft.device.api.operation.entity.DeviceChange;
import com.lnsoft.device.api.operation.entity.DeviceChangeList;
import com.lnsoft.device.api.operation.vo.DeviceChangeVO;
import com.lnsoft.device.api.warehouse.dto.OrderUpdateStatusDTO;

import java.util.List;

/**
 * 设备变更 服务类
 *
 * @author Idevelop
 * @since 2024-03-07
 */
public interface IDeviceChangeService extends BaseService<DeviceChange> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param deviceChange
	 * @return
	 */
	IPage<DeviceChangeVO> selectDeviceChangePage(IPage<DeviceChangeVO> page, DeviceChangeVO deviceChange);
	/**
	 * 分页查询设备变更列表
	 *
	 * @param query
	 * @param deviceChange
	 * @return
	 */
	R<IPage<DeviceChange>> deviceChangeList(DeviceChangeDTO deviceChange, Query query);
	/**
	 * 变更提交
	 * @param deviceChange
	 * @return
	 */
	R<DeviceChangeVO> add(DeviceChangeDTO deviceChange) throws Exception;
	/**
	 * 变更工单及设备删除
	 * @param ids
	 * @return
	 */
    R delete(String ids);
	/**
	 * 个人工作台审核更新工单状态，增加日志记录
	 * @param orderUpdateStatusDTO
	 * @return
	 */
	R<Integer> deskUpdateStatus(OrderUpdateStatusDTO orderUpdateStatusDTO) throws Exception;
	/**
	 * 同步erp
	 * @param deviceChangeLists
	 * @return
	 */
    R erpSync(List<DeviceChangeList> deviceChangeLists);

	/**
	 * 工作台获取设备变更列表
	 * @param query
	 * @param deviceChange
	 * @return
	 */
	R<IPage<DeviceChangeVO>> deskDeviceChangeList(Query query, DeviceChangeDTO deviceChange);
	/**
	 * 数据填充
	 */
	R load();
	/**
	 * 数据校验
	 */
	R check(DeviceChangeDTO deviceChangeDTO);
}
