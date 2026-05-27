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
package com.lnsoft.device.api.warehouse.mapper;

import com.lnsoft.device.dto.DeviceReturnedDTO;
import com.lnsoft.device.entity.DeviceReturned;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.device.vo.DeviceReturnedVO;

import java.util.List;

/**
 * 设备退运 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-03-25
 */
public interface DeviceReturnedMapper extends BaseMapper<DeviceReturned> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param deviceReturned
	 * @return
	 */
	List<DeviceReturnedVO> selectDeviceReturnedPage(IPage page, DeviceReturnedVO deviceReturned);

	/**
	 * 工作台查看设备退运
	 * @param dto
	 * @param page
	 * @return
	 */
	IPage<DeviceReturnedVO> deskDeviceReturnedList(DeviceReturnedDTO dto, IPage<DeviceReturned> page);

    IPage<DeviceReturned> getPage(IPage<Object> page, DeviceReturnedDTO deviceReturnedDTO);
}
