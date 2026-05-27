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

import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.device.api.warehouse.entity.DeviceScrapList;
import com.lnsoft.device.api.warehouse.mapper.DeviceScrapListMapper;
import com.lnsoft.device.api.warehouse.service.IDeviceScrapListService;
import com.lnsoft.device.api.warehouse.vo.DeviceScrapListVO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 设备报废列表 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-18
 */
@Service
public class DeviceScrapListServiceImpl extends BaseServiceImpl<DeviceScrapListMapper, DeviceScrapList> implements IDeviceScrapListService {

	@Override
	public IPage<DeviceScrapListVO> selectDeviceScrapListPage(IPage<DeviceScrapListVO> page, DeviceScrapListVO deviceScrapList) {
		return page.setRecords(baseMapper.selectDeviceScrapListPage(page, deviceScrapList));
	}

	@Override
	public List<DeviceScrapList> getByDeviceCode(String deviceCode) {
		return baseMapper.getByDeviceCode(deviceCode);
	}

}
