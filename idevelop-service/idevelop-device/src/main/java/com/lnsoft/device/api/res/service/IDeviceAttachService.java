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
package com.lnsoft.device.api.res.service;

import com.lnsoft.device.api.res.entity.DeviceAttach;
import com.lnsoft.device.api.res.vo.DeviceAttachVO;
import com.lnsoft.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 设备-附件表 服务类
 *
 * @author Idevelop
 * @since 2024-02-23
 */
public interface IDeviceAttachService extends BaseService<DeviceAttach> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param deviceAttach
	 * @return
	 */
	IPage<DeviceAttachVO> selectDeviceAttachPage(IPage<DeviceAttachVO> page, DeviceAttachVO deviceAttach);

	/**
	 * 根据入库单号和附件id获取附件信息
	 *
	 * @param storageId
	 * @return
	 */
	List<DeviceAttach> getAttach(String storageId);

	/**
	 * 根据入库单号删除附件
	 *
	 * @param storageId
	 */
	void deleteByStorageId(String storageId);
}
