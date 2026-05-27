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
package com.lnsoft.device.api.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.operation.entity.DeviceChangeLogs;
import com.lnsoft.device.api.operation.vo.DeviceChangeLogsVO;
import com.lnsoft.device.api.operation.mapper.DeviceChangeLogsMapper;
import com.lnsoft.device.api.operation.service.IDeviceChangeLogsService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 设备变更 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-07
 */
@Service
public class DeviceChangeLogsServiceImpl extends BaseServiceImpl<DeviceChangeLogsMapper, DeviceChangeLogs> implements IDeviceChangeLogsService {

	@Override
	public IPage<DeviceChangeLogsVO> selectDeviceChangeLogsPage(IPage<DeviceChangeLogsVO> page, DeviceChangeLogsVO deviceChangeLogs) {
		return page.setRecords(baseMapper.selectDeviceChangeLogsPage(page, deviceChangeLogs));
	}

	@Override
	public List<DeviceChangeLogs> getByChangeCode(DeviceChangeLogs deviceChangeLogs) {
		LambdaQueryWrapper<DeviceChangeLogs> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(DeviceChangeLogs::getDeviceCode,deviceChangeLogs.getDeviceCode());
		queryWrapper.eq(DeviceChangeLogs::getChangeId,deviceChangeLogs.getChangeId());
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public Integer delete(String changeId) {
		return baseMapper.deleteByDevOrChange(changeId);
	}

	@Override
	public void deleteOne(String id) {
		baseMapper.deleteOne(id);
	}

}
