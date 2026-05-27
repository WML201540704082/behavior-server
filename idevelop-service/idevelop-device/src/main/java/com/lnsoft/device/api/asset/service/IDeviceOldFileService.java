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

import com.lnsoft.device.api.asset.entity.DeviceOldFile;
import com.lnsoft.device.api.asset.vo.DeviceOldFileVO;
import com.lnsoft.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 设备工单附件 服务类
 *
 * @author Idevelop
 * @since 2024-06-25
 */
public interface IDeviceOldFileService extends BaseService<DeviceOldFile> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param deviceOldFile
	 * @return
	 */
	IPage<DeviceOldFileVO> selectDeviceOldFilePage(IPage<DeviceOldFileVO> page, DeviceOldFileVO deviceOldFile);

	/**
	 * 按照设备编码删除
	 * @param deviceCode
	 * @return
	 */
	Integer delete(String deviceCode);
}
