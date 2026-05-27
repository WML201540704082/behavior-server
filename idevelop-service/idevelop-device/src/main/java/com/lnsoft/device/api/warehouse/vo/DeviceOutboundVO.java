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

import com.lnsoft.device.api.warehouse.entity.DeviceOutbound;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 设备出库表视图实体类
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceOutboundVO对象", description = "设备出库表")
public class DeviceOutboundVO extends DeviceOutbound {
	private static final long serialVersionUID = 1L;

	/**
	 * 设备详情信息
	 */
	@ApiModelProperty(value = "设备详情信息")
	private List<DeviceOutboundDetailVO> deviceOutboundDetailVOList;

	/**
	 * 工单附件信息
	 */
	@ApiModelProperty(value = "工单附件信息")
	private List<DeviceOrderFileVO> deviceOrderFileVOList;
}
