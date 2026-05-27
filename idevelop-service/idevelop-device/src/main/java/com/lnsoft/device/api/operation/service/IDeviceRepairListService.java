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

import com.lnsoft.core.mp.support.Query;
import com.lnsoft.device.entity.DeviceRepairList;
import com.lnsoft.device.vo.DeviceRepairListVO;
import com.lnsoft.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 设备报修详情表 服务类
 *
 * @author Idevelop
 * @since 2024-03-19
 */
public interface IDeviceRepairListService extends BaseService<DeviceRepairList> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param deviceRepairList
	 * @return
	 */
	IPage<DeviceRepairListVO> selectDeviceRepairListPage(IPage<DeviceRepairListVO> page, DeviceRepairListVO deviceRepairList);
	/**
	 * 删除
	 *
	 * @param repairId
	 * @return
	 */
	void deleteByRepairId(String  repairId);

	/**
	 * 按照报修工单id查询
	 * @param deviceRepairList
	 * @param query
	 * @return
	 */
	IPage<DeviceRepairList> selectByRepairId(DeviceRepairList deviceRepairList, Query query);
	/**
	 * 新增
	 * @param deviceRepairList
	 * @return
	 */
	Integer insertDevice(DeviceRepairList deviceRepairList);

	 /**
	  * 根据设备编码查询
	  * @return
	  */
     List<DeviceRepairList> getByDeviceCode(String deviceCode);

}
