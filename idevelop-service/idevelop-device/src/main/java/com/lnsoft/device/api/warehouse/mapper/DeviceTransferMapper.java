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

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.device.api.warehouse.dto.DeviceTransferDTO;
import com.lnsoft.device.api.warehouse.entity.DeviceTransfer;
import com.lnsoft.device.api.warehouse.vo.DeviceTransferVO;
import org.apache.ibatis.annotations.Param;

/**
 * 设备转资 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-02-27
 */
public interface DeviceTransferMapper extends BaseMapper<DeviceTransfer> {

	/**
	 * 分页 设备转资
	 *
	 * @param deviceTransferDTO 查询参数
	 * @param page              分页参数
	 * @return R
	 */
	IPage<DeviceTransferVO> deviceTransferList(IPage<DeviceTransfer> page, @Param("deviceTransferDTO") DeviceTransferDTO deviceTransferDTO);

	IPage<DeviceTransferVO> deskDeviceTransferList(DeviceTransferDTO dto, IPage<Object> page);
}
