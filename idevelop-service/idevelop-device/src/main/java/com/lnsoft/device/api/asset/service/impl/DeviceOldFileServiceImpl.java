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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lnsoft.device.api.asset.entity.DeviceOldFile;
import com.lnsoft.device.api.asset.vo.DeviceOldFileVO;
import com.lnsoft.device.api.asset.mapper.DeviceOldFileMapper;
import com.lnsoft.device.api.asset.service.IDeviceOldFileService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 设备工单附件 服务实现类
 *
 * @author Idevelop
 * @since 2024-06-25
 */
@Service
public class DeviceOldFileServiceImpl extends BaseServiceImpl<DeviceOldFileMapper, DeviceOldFile> implements IDeviceOldFileService {

	@Override
	public IPage<DeviceOldFileVO> selectDeviceOldFilePage(IPage<DeviceOldFileVO> page, DeviceOldFileVO deviceOldFile) {
		return page.setRecords(baseMapper.selectDeviceOldFilePage(page, deviceOldFile));
	}

	@Override
	public Integer delete(String deviceCode) {
		LambdaQueryWrapper<DeviceOldFile> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(DeviceOldFile::getDeviceCode,deviceCode);
		return baseMapper.delete(queryWrapper);
	}

}
