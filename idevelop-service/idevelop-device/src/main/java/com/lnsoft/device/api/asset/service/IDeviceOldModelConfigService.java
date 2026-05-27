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
package com.lnsoft.device.api.asset.service;

import com.lnsoft.device.api.asset.entity.DeviceOldModelConfig;
import com.lnsoft.device.api.asset.vo.DeviceOldModelConfigVO;
import com.lnsoft.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 老旧设备打分模型配置表 服务类
 *
 * @author Idevelop
 * @since 2024-06-19
 */
public interface IDeviceOldModelConfigService extends BaseService<DeviceOldModelConfig> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param deviceOldModelConfig
	 * @return
	 */
	IPage<DeviceOldModelConfigVO> selectDeviceOldModelConfigPage(IPage<DeviceOldModelConfigVO> page, DeviceOldModelConfigVO deviceOldModelConfig);
	/**
	 * 根据配置项获取配置值
	 * @param configItem
	 * @return
	 */
	DeviceOldModelConfigVO getValue(String configItem);

	/**
	 * 评审库列表查询
	 * @param deviceOldModelConfig
	 * @return
	 */
	List<DeviceOldModelConfig> findList(DeviceOldModelConfig deviceOldModelConfig);
	/**
	 * 根据配置项获取配置值(带分类)
	 * @param configItem,deviceTypeCode
	 * @return
	 */
	DeviceOldModelConfigVO getValueOfType(String configItem,String deviceTypeCode);
}
