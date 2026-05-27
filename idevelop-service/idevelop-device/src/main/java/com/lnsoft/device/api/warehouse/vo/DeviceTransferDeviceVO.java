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
package com.lnsoft.device.api.warehouse.vo;

import com.lnsoft.device.api.warehouse.dto.DeviceTransferDeviceDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 设备转资设备实体类
 *
 * @author Idevelop
 * @since 2024-02-27
 */
@Data
@ApiModel(value = "DeviceTransferDeviceVO", description = "设备转资设备")
public class DeviceTransferDeviceVO {
	private static final long serialVersionUID = 1L;

	/**
	 * 转资设备列表信息
	 */
	@ApiModelProperty(value = "转资设备列表信息")
	private	List<DeviceTransferDeviceDTO> list;



}
