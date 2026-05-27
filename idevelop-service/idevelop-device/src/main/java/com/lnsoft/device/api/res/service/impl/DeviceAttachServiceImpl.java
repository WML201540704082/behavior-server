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
package com.lnsoft.device.api.res.service.impl;

import com.lnsoft.device.api.res.entity.DeviceAttach;
import com.lnsoft.device.api.res.vo.DeviceAttachVO;
import com.lnsoft.device.api.res.mapper.DeviceAttachMapper;
import com.lnsoft.device.api.res.service.IDeviceAttachService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 设备-附件表 服务实现类
 *
 * @author Idevelop
 * @since 2024-02-23
 */
@Service
public class DeviceAttachServiceImpl extends BaseServiceImpl<DeviceAttachMapper, DeviceAttach> implements IDeviceAttachService {

	@Override
	public IPage<DeviceAttachVO> selectDeviceAttachPage(IPage<DeviceAttachVO> page, DeviceAttachVO deviceAttach) {
		return page.setRecords(baseMapper.selectDeviceAttachPage(page, deviceAttach));
	}

	@Override
	public List<DeviceAttach> getAttach(String storageId) {
		return baseMapper.getByFilingNoAndId(storageId);
	}

	@Override
	public void deleteByStorageId(String storageId) {
		baseMapper.deleteByStorageId(storageId);
	}

}
