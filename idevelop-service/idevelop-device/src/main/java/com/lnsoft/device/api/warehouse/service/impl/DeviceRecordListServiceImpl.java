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
package com.lnsoft.device.api.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.api.ResultCode;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.dto.DeviceRecordListDTO;
import com.lnsoft.device.api.warehouse.dto.ErpDeviceDetailDTO;
import com.lnsoft.device.api.warehouse.entity.DeviceRecord;
import com.lnsoft.device.entity.DeviceRecordList;
import com.lnsoft.common.enums.hussar.DeviceRecordBpmNodeEnum;
import com.lnsoft.device.api.warehouse.mapper.DeviceRecordListMapper;
import com.lnsoft.device.api.warehouse.mapper.DeviceRecordMapper;
import com.lnsoft.device.api.warehouse.service.IDeviceRecordListService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 设备建档-设备列表 服务实现类
 *
 * @author Idevelop
 * @since 2024-02-21
 */
@Service
@AllArgsConstructor
public class DeviceRecordListServiceImpl extends BaseServiceImpl<DeviceRecordListMapper, DeviceRecordList> implements IDeviceRecordListService {

	@Resource
	private DeviceRecordMapper deviceRecordMapper;

	/**
	 * 批量修改设备建档设备的ERP资产编码使用状态
	 *
	 * @param deviceRecordListDTO 修改参数
	 * @return R
	 */
	@Override
	public R<Integer> batchUpdateDeviceRecordErpAssetStatus(DeviceRecordListDTO deviceRecordListDTO) {
		IdevelopUser user = SecureUtil.getUser();
		if (baseMapper.update(new LambdaUpdateWrapper<DeviceRecordList>()
			.eq(DeviceRecordList::getErpAssetCode, deviceRecordListDTO.getErpAssetCode())
			.set(StringUtil.isNotBlank(deviceRecordListDTO.getErpAssetStatus()), DeviceRecordList::getErpAssetStatus, deviceRecordListDTO.getErpAssetStatus())
			.set(StringUtil.isNotBlank(deviceRecordListDTO.getErpStatus()), DeviceRecordList::getErpStatus, deviceRecordListDTO.getErpStatus())
			.set(StringUtil.isNotBlank(deviceRecordListDTO.getI6000Status()), DeviceRecordList::getI6000Status, deviceRecordListDTO.getI6000Status())
			.set(DeviceRecordList::getUpdateTime, new Date())
			.set(DeviceRecordList::getUpdateUser, user.getUserId())) != 1) {
			return R.fail("修改ERP资产编码使用状态失败");
		} else {
			return R.success(ResultCode.SUCCESS);
		}
	}

	/**
	 * 删除 设备建档-设备列表
	 *
	 * @param ids 设备列表
	 * @return R
	 */
	@Override
	public R<Integer> removeDeviceDetail(String ids) {
		List<DeviceRecordList> deviceRecordListList = baseMapper.selectList(new LambdaQueryWrapper<DeviceRecordList>().in(DeviceRecordList::getId, ids));
		deviceRecordListList.forEach(item -> {
			Long count = deviceRecordMapper.selectCount(new LambdaQueryWrapper<DeviceRecord>().eq(DeviceRecord::getId, item.getRecordId())
				.eq(DeviceRecord::getProcessStatus, DeviceRecordBpmNodeEnum.DEVICE_RECORD_FINISH.getNode()));
//			if (count > 0) {
//				// todo 设备的删除需要推送ERP
//				// todo 是否需要同步CMDB？？？
//			}
		});
		baseMapper.deleteBatchIds(Arrays.asList(ids.split(",")));
		return R.success(ResultCode.SUCCESS);
	}

	/**
	 * 根据设备UUID批量更新设备信息
	 *
	 * @param deviceRecordListDTOList ERP回传设备信息
	 */
	@Override
	public void updateBatchDeviceId(List<ErpDeviceDetailDTO> deviceRecordListDTOList) {
		baseMapper.updateBatchDeviceId(deviceRecordListDTOList);
	}
}
