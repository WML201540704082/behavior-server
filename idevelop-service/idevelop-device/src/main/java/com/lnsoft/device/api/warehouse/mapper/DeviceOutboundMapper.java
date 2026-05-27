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
import com.lnsoft.device.api.asset.dto.DeviceInventoryDTO;
import com.lnsoft.device.api.warehouse.dto.DeviceOutboundDTO;
import com.lnsoft.device.api.warehouse.entity.DeviceOutbound;
import com.lnsoft.device.api.warehouse.vo.DeviceOutboundVO;
import org.apache.ibatis.annotations.Param;

/**
 * 设备出库表 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-03-06
 */
public interface DeviceOutboundMapper extends BaseMapper<DeviceOutbound> {
	/**
	 * 根据日期查询出库数量
	 * @param  startDate
	 * @return R
	 */
	DeviceInventoryDTO getOutWarehouse(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("warehouse") String warehouse);

	IPage<DeviceOutboundVO> getPage(IPage<DeviceOutbound> page, DeviceOutboundDTO deviceOutboundDTO);
}
