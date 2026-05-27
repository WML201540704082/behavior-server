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
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.warehouse.dto.DeviceOperationDTO;
import com.lnsoft.device.api.warehouse.entity.DeviceOperation;
import com.lnsoft.device.api.warehouse.vo.DeviceOperationVO;

/**
 * 设备投运表 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-03-06
 */
public interface DeviceOperationMapper extends BaseMapper<DeviceOperation> {

    IPage<DeviceOperationVO> getPage(IPage<DeviceOperation> page, DeviceOperationDTO deviceOperationDTO);
}
