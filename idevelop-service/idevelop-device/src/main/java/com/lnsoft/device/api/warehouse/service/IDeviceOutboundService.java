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
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.asset.dto.DeviceInventoryDTO;
import com.lnsoft.device.api.desk.vo.DictValueVO;
import com.lnsoft.device.api.warehouse.dto.DeviceOutboundDTO;
import com.lnsoft.device.api.warehouse.entity.DeviceOutbound;
import com.lnsoft.device.api.warehouse.vo.DeviceOutboundVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备出库表 服务类
 *
 * @author Idevelop
 * @since 2024-03-06
 */
public interface IDeviceOutboundService extends BaseService<DeviceOutbound> {

	/**
	 * 分页 设备出库表
	 *
	 * @param page              分页参数
	 * @param deviceOutboundDTO 查询条件
	 * @return R
	 */
	R<IPage<DeviceOutboundVO>> deviceOutboundList(IPage<DeviceOutbound> page, DeviceOutboundDTO deviceOutboundDTO);

	/**
	 * 个人工作台查询出库工单
	 *
	 * @param deviceOutboundDTO 查询条件
	 * @param query             分页条件
	 * @return R
	 */
	R<IPage<DeviceOutboundVO>> deskList(DeviceOutboundDTO deviceOutboundDTO, Query query);

	/**
	 * 个人工作台审核更新工单
	 *
	 * @param deviceOutboundDTO 工单信息
	 * @return R
	 */
	R<Integer> deskUpdateStatus(DeviceOutboundDTO deviceOutboundDTO) throws Exception;

	/**
	 * 获取设备出库工单状态字典
	 *
	 * @return List
	 */
	List<DictValueVO> deviceOutboundDict();

	/**
	 * 工单审核之前校验设备信息
	 *
	 * @param deviceOutboundDTO 工单信息
	 * @return R
	 */
	R<Integer> checkDeviceOperation(DeviceOutboundDTO deviceOutboundDTO);

	/**
	 * 详情
	 *
	 * @param deviceOutboundDTO 出库信息
	 * @return R
	 */
	R<DeviceOutboundVO> detail(DeviceOutboundDTO deviceOutboundDTO);
	/**
	 * 根据日期查询出库数量
	 * @param  startDate
	 * @return R
	 */
	DeviceInventoryDTO getOutWarehouse(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("warehouse") String warehouse);


}
