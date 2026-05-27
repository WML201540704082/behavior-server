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
import com.google.protobuf.ServiceException;
import com.lnsoft.core.mp.base.BaseService;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.warehouse.dto.DeviceTransferDTO;
import com.lnsoft.device.api.warehouse.dto.DeviceTransferDetailDTO;
import com.lnsoft.device.api.warehouse.dto.ErpDeviceOrderDTO;
import com.lnsoft.device.api.warehouse.entity.DeviceTransfer;
import com.lnsoft.device.api.warehouse.vo.DeviceTransferVO;

import java.util.List;

/**
 * 设备转资 服务类
 *
 * @author Idevelop
 * @since 2024-02-27
 */
public interface IDeviceTransferService extends BaseService<DeviceTransfer> {

	/**
	 * 新增或修改 设备转资暂存
	 *
	 * @param deviceTransferDTO 转资信息
	 * @return R
	 */
	R<DeviceTransferVO> temporarily(DeviceTransferDTO deviceTransferDTO) throws ServiceException;

	/**
	 * 发起设备转资
	 *
	 * @param deviceTransferDTO 设备转资
	 * @return R
	 */
	R<DeviceTransferVO> insert(DeviceTransferDTO deviceTransferDTO) throws Exception;

	/**
	 * 删除 设备转资
	 *
	 * @param ids 设备转资id
	 * @return R
	 */
	R<Integer> deleteDeviceTransfer(String ids);

	/**
	 * 详情
	 *
	 * @param deviceTransferDTO 查询信息
	 * @return R
	 */
	R<DeviceTransferVO> detail(DeviceTransferDTO deviceTransferDTO);

	/**
	 * 分页 设备转资
	 *
	 * @param deviceTransferDTO 查询参数
	 * @param query             分页参数
	 * @return R
	 */
	R<IPage<DeviceTransferVO>> deviceTransferList(DeviceTransferDTO deviceTransferDTO, Query query);

	/**
	 * 获取当前登录人
	 *
	 * @return R
	 */
	R<String> getLoginUser();

	/**
	 * 保存 cmdb的接口
	 * @param deviceList
	 * @return
	 */
	boolean  updateCmdbEntity(List<DeviceTransferDetailDTO> deviceList, DeviceTransfer transfer);

	/**
	 * 更新设备转资工单状态
	 * @param dto
	 * @return
	 */
	R<Integer> deskDeviceTransferStatus(DeviceTransferDTO dto) throws Exception;

	/**
	 * 工作台获取设备转资列表
	 * @param dto
	 * @param query
	 * @return
	 */
	R<IPage<DeviceTransferVO>> deskDeviceTransferList(DeviceTransferDTO dto, Query query);

	/**
	 * erp审核回传接口
	 *
	 * @param erpDeviceOrderDTO ERP回调信息
	 * @return R
	 */
	R<Integer> erpDeviceTransfer(ErpDeviceOrderDTO erpDeviceOrderDTO) throws Exception;
}
