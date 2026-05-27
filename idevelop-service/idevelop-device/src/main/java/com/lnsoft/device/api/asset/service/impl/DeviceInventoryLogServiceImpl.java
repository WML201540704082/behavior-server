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
package com.lnsoft.device.api.asset.service.impl;

import com.lnsoft.device.api.asset.entity.DeviceInventoryLog;
import com.lnsoft.device.api.asset.vo.DeviceInventoryLogVO;
import com.lnsoft.device.api.asset.mapper.DeviceInventoryLogMapper;
import com.lnsoft.device.api.asset.service.IDeviceInventoryLogService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 设备库存日志表 服务实现类
 *
 * @author Idevelop
 * @since 2024-04-29
 */
@Service
public class DeviceInventoryLogServiceImpl extends BaseServiceImpl<DeviceInventoryLogMapper, DeviceInventoryLog> implements IDeviceInventoryLogService {

	@Override
	public IPage<DeviceInventoryLogVO> selectDeviceInventoryLogPage(IPage<DeviceInventoryLogVO> page, DeviceInventoryLogVO deviceInventoryLog) {
		return page.setRecords(baseMapper.selectDeviceInventoryLogPage(page, deviceInventoryLog));
	}

}
