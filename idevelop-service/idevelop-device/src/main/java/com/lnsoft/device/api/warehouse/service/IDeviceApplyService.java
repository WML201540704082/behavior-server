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
import com.lnsoft.device.api.desk.vo.DictValueVO;
import com.lnsoft.device.api.warehouse.dto.DeviceApplyDTO;
import com.lnsoft.device.api.warehouse.entity.DeviceApply;
import com.lnsoft.device.api.warehouse.vo.DeviceApplyVO;
import com.lnsoft.device.api.warehouse.vo.DeviceOperationDetailVO;

import java.util.List;

/**
 * 设备申请表 服务类
 *
 * @author Idevelop
 * @since 2024-03-06
 */
public interface IDeviceApplyService extends BaseService<DeviceApply> {

	/**
	 * 新增/暂存设备申请表
	 *
	 * @param deviceApplyDTO 新增/暂存设备申请单
	 * @return R
	 */
	R<Integer> insertDeviceApply(DeviceApplyDTO deviceApplyDTO);

	/**
	 * 设备申请
	 *
	 * @param deviceApplyDTO 设备申请
	 * @return R
	 */
	R<DeviceApplyVO> submitDeviceApply(DeviceApplyDTO deviceApplyDTO) throws Exception;

	/**
	 * 删除 设备申请表
	 *
	 * @param ids 删除工单id
	 * @return R
	 */
	R<Integer> removeDeviceApply(String ids);

	/**
	 * 详情
	 *
	 * @param deviceApply 查询参数
	 * @return R
	 */
	R<DeviceApplyVO> deviceApply(DeviceApply deviceApply);

	/**
	 * 分页 设备申请表
	 *
	 * @param deviceApplyDTO 查询条件
	 * @param query          分页信息
	 * @return R
	 */
	R<IPage<DeviceApplyVO>> selectDeviceApplyList(DeviceApplyDTO deviceApplyDTO, Query query);

	/**
	 * 个人工作台查询申请单工单
	 *
	 * @param deviceApplyDTO 查询条件
	 * @param query          分页条件
	 * @return R
	 */
	R<IPage<DeviceApplyVO>> deskList(DeviceApplyDTO deviceApplyDTO, Query query);

	/**
	 * 个人工作台审核更新工单状态，增加日志记录
	 *
	 * @param deviceApplyDTO 更新工单信息
	 * @return R
	 */
	R<Integer> deskUpdateStatus(DeviceApplyDTO deviceApplyDTO) throws Exception;

	/**
	 * 获取设备申请工单状态字典
	 *
	 * @return List
	 */
	List<DictValueVO> deviceApplyDict();

	/**
	 * 根据设备id查询旧设备的使用类型
	 *
	 * @param deviceId 设备id
	 * @return DeviceOperationDetailVO 查询设备信息
	 */
	DeviceOperationDetailVO queryUserType(String deviceId);
}
