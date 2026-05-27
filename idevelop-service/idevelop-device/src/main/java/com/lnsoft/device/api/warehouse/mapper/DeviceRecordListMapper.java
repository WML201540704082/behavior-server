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
import com.lnsoft.device.api.warehouse.dto.ErpDeviceDetailDTO;
import com.lnsoft.device.entity.DeviceRecordList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备建档-设备列表 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-02-21
 */
public interface DeviceRecordListMapper extends BaseMapper<DeviceRecordList> {

	/**
	 * 根据设备UUID批量更新设备信息
	 *
	 * @param deviceRecordListDTOList ERP回传设备信息
	 */
	void updateBatchDeviceId(@Param("list") List<ErpDeviceDetailDTO> deviceRecordListDTOList);
}
