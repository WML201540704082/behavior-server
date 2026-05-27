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

import com.lnsoft.device.api.warehouse.entity.DeviceTransfer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 设备转资视图实体类
 *
 * @author Idevelop
 * @since 2024-02-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceTransferVO对象", description = "设备转资")
public class DeviceTransferVO extends DeviceTransfer {
	private static final long serialVersionUID = 1L;

	/**
	 * 设备转资设备列表信息
	 */
	@ApiModelProperty(value = "设备转资设备列表信息")
	private List<DeviceTransferDetailVO> deviceTransferDetailVOList;

	/**
	 * 发起人姓名
	 */
	@ApiModelProperty(value = "发起人姓名")
	private String realName;

	private String processType;

	private Integer deviceNum;

}
