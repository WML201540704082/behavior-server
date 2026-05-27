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
import com.lnsoft.device.api.warehouse.dto.DeviceRecordDTO;
import com.lnsoft.device.api.warehouse.dto.ErpDeviceOrderDTO;
import com.lnsoft.device.api.warehouse.entity.DeviceRecord;
import com.lnsoft.device.api.warehouse.vo.DeviceRecordVO;

/**
 * 设备建档 服务类
 *
 * @author Idevelop
 * @since 2024-02-07
 */
public interface IDeviceRecordService extends BaseService<DeviceRecord> {

	/**
	 * 获取 新增设备建档时填充数据
	 *
	 * @return
	 */
	DeviceRecordVO deviceRecordGet();

	/**
	 * 新增或修改 设备建档
	 *
	 * @param deviceRecordDTO 新增修改参数
	 * @return R
	 */
	R<DeviceRecordVO> deviceRecordSaveOrUpdate(DeviceRecordDTO deviceRecordDTO);

	/**
	 * 提交建档
	 *
	 * @param deviceRecordDTO 建档参数
	 * @return R
	 */
	R<DeviceRecordVO> deviceRecordSubmit(DeviceRecordDTO deviceRecordDTO) throws Exception;

	/**
	 * 删除设备建档
	 *
	 * @param ids 删除id
	 * @return R<Integer>
	 */
	R<Integer> removeDeviceRecord(String ids);

	/**
	 * 设备建档列表查询
	 *
	 * @param deviceRecordDTO 查询条件
	 * @param query           分页参数
	 * @return R
	 */
	R<IPage<DeviceRecord>> deviceRecordList(DeviceRecordDTO deviceRecordDTO, Query query);

	/**
	 * 工作台获取设备建档列表
	 *
	 * @param deviceRecordDTO 查询条件
	 * @param query           分页
	 * @return R
	 */
	R<IPage<DeviceRecordVO>> deskDeviceRecodeList(DeviceRecordDTO deviceRecordDTO, Query query);

	/**
	 * 更新设备建档工单状态
	 *
	 * @param deviceRecordDTO 更新参数
	 * @return R
	 */
	R<Integer> deskDeviceRecodeStatus(DeviceRecordDTO deviceRecordDTO) throws Exception;

	/**
	 * ERP审核回传接口
	 *
	 * @param erpDeviceOrderDTO 审核参数
	 * @return R
	 */
	R<Integer> erpDeviceRecord(ErpDeviceOrderDTO erpDeviceOrderDTO) throws Exception;
}
