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
package com.lnsoft.device.api.operation.service;

import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.operation.entity.DeviceChangeLogs;
import com.lnsoft.device.api.operation.vo.DeviceChangeLogsVO;
import com.lnsoft.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 设备变更 服务类
 *
 * @author Idevelop
 * @since 2024-03-07
 */
public interface IDeviceChangeLogsService extends BaseService<DeviceChangeLogs> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param deviceChangeLogs
	 * @return
	 */
	IPage<DeviceChangeLogsVO> selectDeviceChangeLogsPage(IPage<DeviceChangeLogsVO> page, DeviceChangeLogsVO deviceChangeLogs);

	/**
	 * 根据变更编码查询列表
	 * @param deviceChangeLogs
	 * @return
	 */
    List<DeviceChangeLogs> getByChangeCode(DeviceChangeLogs deviceChangeLogs);
	/**
	 * 根据设备id和关联工单id删除记录
	 * @param changeId
	 * @return
	 */
	 Integer delete(String changeId);

	void deleteOne(String id);
}
