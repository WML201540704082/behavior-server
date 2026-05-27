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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.device.api.warehouse.entity.DeviceOperationDetail;
import com.lnsoft.device.api.warehouse.mapper.DeviceOperationDetailMapper;
import com.lnsoft.device.api.warehouse.service.IDeviceOperationDetailService;
import com.lnsoft.device.api.warehouse.vo.DeviceOperationDetailVO;
import org.springframework.stereotype.Service;

/**
 * 设备投运单设备详情 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@Service
public class DeviceOperationDetailServiceImpl extends BaseServiceImpl<DeviceOperationDetailMapper, DeviceOperationDetail> implements IDeviceOperationDetailService {

	@Override
	public IPage<DeviceOperationDetailVO> selectDeviceOperationDetailPage(IPage<DeviceOperationDetailVO> page, DeviceOperationDetailVO deviceOperationDetail) {
		return page.setRecords(baseMapper.selectDeviceOperationDetailPage(page, deviceOperationDetail));
	}

}
