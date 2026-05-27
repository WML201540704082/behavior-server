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

import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.entity.DeviceRepairList;
import com.lnsoft.device.vo.CiCientitySearchVO;
import com.lnsoft.device.dto.DeviceRepairDTO;
import com.lnsoft.device.entity.DeviceRepair;
import com.lnsoft.device.vo.DeviceRepairVO;
import com.lnsoft.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.device.api.operation.vo.HardwareBasicQueryRepairVO;
import com.lnsoft.device.api.warehouse.dto.OrderUpdateStatusDTO;

import java.util.List;

/**
 * 设备报修 服务类
 *
 * @author Idevelop
 * @since 2024-03-19
 */
public interface IDeviceRepairService extends BaseService<DeviceRepair> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param deviceRepair
	 * @return
	 */
	IPage<DeviceRepairVO> selectDeviceRepairPage(IPage<DeviceRepairVO> page, DeviceRepairVO deviceRepair);
	/**
	 * 删除
	 *
	 * @param ids
	 * @return
	 */
	void delete(String ids);

	/**
	 * 条件分页
	 * @param deviceRepair
	 * @param query
	 * @return
	 */
	IPage<DeviceRepair> deviceRepairPage(DeviceRepairVO deviceRepair, Query query);

	/**
	 * 提交暂存
	 * @param deviceRepairDTO
	 * @return
	 * @throws Exception
	 */
	R submit(DeviceRepairDTO deviceRepairDTO) throws Exception;

	/**
	 * 数据填充
	 * @return
	 */
	R load();
	/**
	 * 个人工作台审核更新工单状态，增加日志记录
	 * @param orderUpdateStatusDTO
	 * @return
	 */
	R<Integer> deskUpdateStatus(OrderUpdateStatusDTO orderUpdateStatusDTO) throws Exception;
	/**
	 * 工作台获取设备转资列表
	 * @param dto
	 * @param query
	 * @return
	 */
	R<IPage<DeviceRepairVO>> deskDeviceRepairList(DeviceRepairDTO dto, Query query);

	/**
	 * 参数组装
	 * @param hardwareBasicQueryVO
	 * @return
	 */
    List<CiCientitySearchVO> assemble(HardwareBasicQueryRepairVO hardwareBasicQueryVO);

	 /**
	  * 根据工单编号和状态查询设备列表
	  * @param repairId
	  * @return
	  */
	 List<DeviceRepairList> getDeviceByRepairIdAndStatus(String repairId);

	 /**
	  * 校验
	  * @param deviceRepairDTO
	  * @return
	  */
	 R check(DeviceRepairDTO deviceRepairDTO);
}
