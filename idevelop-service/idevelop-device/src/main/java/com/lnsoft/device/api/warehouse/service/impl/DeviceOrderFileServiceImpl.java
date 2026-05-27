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

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.tool.utils.CollectionUtil;
import com.lnsoft.device.entity.DeviceOrderFile;
import com.lnsoft.common.enums.hussar.OrderFileTypeEnum;
import com.lnsoft.device.api.warehouse.mapper.DeviceOrderFileMapper;
import com.lnsoft.device.api.warehouse.service.IDeviceOrderFileService;
import com.lnsoft.device.api.warehouse.vo.DeviceOrderFileVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备工单附件 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@Service
public class DeviceOrderFileServiceImpl extends BaseServiceImpl<DeviceOrderFileMapper, DeviceOrderFile> implements IDeviceOrderFileService {

	/**
	 * 获取设备信息
	 *
	 * @param deviceOrderFileVOList 设备信息
	 * @param id                    工单id
	 * @param orderType             工地类型
	 */
	public void getOrderFileList(List<DeviceOrderFileVO> deviceOrderFileVOList, String id, String orderType) {
		List<DeviceOrderFile> deviceOrderFileListApply = baseMapper.selectList(new LambdaQueryWrapper<DeviceOrderFile>().eq(DeviceOrderFile::getOrderId, id)
			.eq(DeviceOrderFile::getOrderType, orderType)
			.eq(DeviceOrderFile::getOrderFileType, OrderFileTypeEnum.getFileType(orderType)));
		if (CollectionUtil.isNotEmpty(deviceOrderFileListApply)) {
			deviceOrderFileVOList.addAll(Convert.convert(new TypeReference<List<DeviceOrderFileVO>>() {
			}, deviceOrderFileListApply));
		}
	}
}
