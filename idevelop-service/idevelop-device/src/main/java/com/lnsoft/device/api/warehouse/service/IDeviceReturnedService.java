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
package com.lnsoft.device.api.warehouse.service;

import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.dto.DeviceReturnedDTO;
import com.lnsoft.device.dto.DeviceReturnedDetailDTO;
import com.lnsoft.device.entity.DeviceReturned;
import com.lnsoft.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.device.vo.DeviceReturnedVO;

import java.util.List;

/**
 * 设备退运 服务类
 *
 * @author Idevelop
 * @since 2024-03-25
 */
public interface IDeviceReturnedService extends BaseService<DeviceReturned> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param deviceReturned
	 * @return
	 */
	IPage<DeviceReturnedVO> selectDeviceReturnedPage(IPage<DeviceReturnedVO> page, DeviceReturnedVO deviceReturned);

	/**
	 * 查看设备退运详情
	 * @param dto
	 * @return
	 */
	R<DeviceReturnedVO> detail(DeviceReturnedDTO dto);

	/**
	 * 查看设备退运列表
	 * @param dto
	 * @param query
	 * @return
	 */
	IPage<DeviceReturned> returnedPage(DeviceReturnedDTO dto, Query query);

	/**
	 * 保存设备退运
	 * @param dto
	 * @return
	 */
	R<DeviceReturnedVO> saveReturned(DeviceReturnedDTO dto);

	/**
	 * 删除设备退运
	 * @param ids
	 * @return
	 */
	R removeReturned(String ids);

	/**
	 * 提交设备退运
	 * @param dto
	 * @return
	 */
	R<DeviceReturnedVO> deviceReturnedSubmit(DeviceReturnedDTO dto) throws Exception;

	/**
	 * 工作台查看设备退运
	 * @param dto
	 * @param query
	 * @return
	 */
	R<IPage<DeviceReturnedVO>> deskDeviceReturnedList(DeviceReturnedDTO dto, Query query);

	/**
	 * 更新设备退运状态
	 * @param dto
	 * @return
	 */
	R<Integer> deskDeviceReturnedStatus(DeviceReturnedDTO dto) throws Exception;

	/**
	 * 校验是否全部归还
	 * @param dto
	 * @return
	 */
	R checkIsAllReturn(DeviceReturnedDTO dto);

	/**
	 * 同步i6000信息
	 * @param deviceList
	 * @return
	 */
	boolean updateI6000Entity(List<DeviceReturnedDetailDTO> deviceList);
}
