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
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.device.api.asset.entity.DeviceOldModelConfig;
import com.lnsoft.device.api.asset.vo.DeviceOldModelConfigVO;
import com.lnsoft.device.api.asset.mapper.DeviceOldModelConfigMapper;
import com.lnsoft.device.api.asset.service.IDeviceOldModelConfigService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 老旧设备打分模型配置表 服务实现类
 *
 * @author Idevelop
 * @since 2024-06-19
 */
@Service
public class DeviceOldModelConfigServiceImpl extends BaseServiceImpl<DeviceOldModelConfigMapper, DeviceOldModelConfig> implements IDeviceOldModelConfigService {

	@Override
	public IPage<DeviceOldModelConfigVO> selectDeviceOldModelConfigPage(IPage<DeviceOldModelConfigVO> page, DeviceOldModelConfigVO deviceOldModelConfig) {
		return page.setRecords(baseMapper.selectDeviceOldModelConfigPage(page, deviceOldModelConfig));
	}

	@Override
	public DeviceOldModelConfigVO getValue(String configItem) {
		return baseMapper.getValue(configItem);

	}

	@Override
	public List<DeviceOldModelConfig> findList(DeviceOldModelConfig deviceOldModelConfig) {
		LambdaQueryWrapper<DeviceOldModelConfig> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(DeviceOldModelConfig::getConfigTypeCode,deviceOldModelConfig.getConfigTypeCode()).eq(DeviceOldModelConfig::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public DeviceOldModelConfigVO getValueOfType(String configItem, String deviceTypeCode) {
		return baseMapper.getValueOfType(configItem,deviceTypeCode);
	}


}
