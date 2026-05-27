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

import com.lnsoft.device.api.asset.entity.DeviceAssetCaching;
import com.lnsoft.core.mp.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 *  服务类
 *
 * @author Idevelop
 * @since 2024-03-30
 */
public interface IDeviceAssetCachingService extends BaseService<DeviceAssetCaching> {

	/**
	 * 获取分类资产总值列表
	 * @param deviceAssetCaching
	 * @return
	 */
	List<Map<String,Object>> findAssetList(DeviceAssetCaching deviceAssetCaching);

	/**
	 * 根据分类，类型，单位，获取
	 * @param deviceAssetCaching
	 * @return
	 */
    DeviceAssetCaching getOneData(DeviceAssetCaching deviceAssetCaching);
}
