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
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.desk.vo.DictValueVO;
import com.lnsoft.device.api.warehouse.dto.DeviceOperationDTO;
import com.lnsoft.device.api.warehouse.entity.DeviceOperation;
import com.lnsoft.device.api.warehouse.vo.DeviceOperationDetailVO;
import com.lnsoft.device.api.warehouse.vo.DeviceOperationVO;

import java.util.List;

/**
 * 设备投运表 服务类
 *
 * @author Idevelop
 * @since 2024-03-06
 */
public interface IDeviceOperationService extends BaseService<DeviceOperation> {

	/**
	 * 新增/修改设备投运
	 *
	 * @param deviceOperationDTO 投运信息
	 * @return R
	 */
	R<DeviceOperationVO> insertOperation(DeviceOperationDTO deviceOperationDTO);

	/**
	 * 提交设备投运
	 *
	 * @param deviceOperationDTO 投运信息
	 * @return R
	 */
	R<DeviceOperationVO> submitOperation(DeviceOperationDTO deviceOperationDTO) throws Exception;

	/**
	 * 提交设备投运
	 *
	 * @param deviceOperationDTO 投运信息
	 * @param user               登录用户信息
	 * @return R
	 */
	R<DeviceOperationVO> submitOperationData(DeviceOperationDTO deviceOperationDTO, IdevelopUser user) throws Exception;

	/**
	 * 删除设备投运
	 *
	 * @param ids 投运工单id
	 * @return R
	 */
	R<Integer> removeOperation(String ids);

	/**
	 * 设备投运分页
	 *
	 * @param page               分页条件
	 * @param deviceOperationDTO 查询参数
	 * @return R
	 */
	R<IPage<DeviceOperationVO>> selectOperationList(IPage<DeviceOperation> page, DeviceOperationDTO deviceOperationDTO);

	/**
	 * 设备投运详情
	 *
	 * @param deviceOperationDTO 查询条件
	 * @return R
	 */
	R<DeviceOperationVO> detailOperation(DeviceOperationDTO deviceOperationDTO);

	/**
	 * 获取设备投运工单状态字典
	 *
	 * @return R
	 */
	R<List<DictValueVO>> deviceOperationDict();

	/**
	 * 个人工作台查询投运工单
	 *
	 * @param deviceOperationDTO 查询条件
	 * @param query              分页条件
	 * @return R
	 */
	R<IPage<DeviceOperationVO>> deskList(DeviceOperationDTO deviceOperationDTO, Query query);

	/**
	 * 个人工作台审核更新工单
	 *
	 * @param deviceOperationDTO 审核工单信息
	 * @return R
	 */
	R<List<DeviceOperationDetailVO>> deskUpdateStatus(DeviceOperationDTO deviceOperationDTO) throws Exception;

	/**
	 * 工单审核之前校验设备信息
	 *
	 * @param deviceOperationDTO 投运信息
	 * @return R
	 */
	R<Integer> checkDeviceOperation(DeviceOperationDTO deviceOperationDTO);

	/**
	 * 生成标准全称
	 *
	 * @param deviceType 设备类型
	 * @return R
	 */
	R<String> createFullName(String deviceType);
}
