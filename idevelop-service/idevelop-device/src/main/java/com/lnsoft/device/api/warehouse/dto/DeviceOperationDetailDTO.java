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
package com.lnsoft.device.api.warehouse.dto;

import com.lnsoft.device.api.warehouse.entity.DeviceOperationDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备投运单设备详情数据传输对象实体类
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceOperationDetailDTO extends DeviceOperationDetail {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "是否是第一次投运 0 是 1 否")
	private Integer firstReceiveFlag;

	@ApiModelProperty(value = "是否是接入层交换机 0 是 1 否")
	private Integer switchesType;

	@ApiModelProperty(value = "仓库编码")
	private String returnWarehouseCode;
}
