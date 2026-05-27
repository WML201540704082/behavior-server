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
package com.lnsoft.device.api.asset.mapper;

import com.lnsoft.device.api.asset.entity.DeviceAssetCaching;
import com.lnsoft.device.api.asset.vo.DeviceAssetCachingVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 *  Mapper 接口
 *
 * @author Idevelop
 * @since 2024-03-30
 */
public interface DeviceAssetCachingMapper extends BaseMapper<DeviceAssetCaching> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param deviceAssetCaching
	 * @return
	 */
	List<DeviceAssetCachingVO> selectDeviceAssetCachingPage(IPage page, DeviceAssetCachingVO deviceAssetCaching);
	/**
	 * 根据分类，类型，单位，获取
	 * @param deviceAssetCaching
	 * @return
	 */
    DeviceAssetCaching getOneData(DeviceAssetCaching deviceAssetCaching);

}
