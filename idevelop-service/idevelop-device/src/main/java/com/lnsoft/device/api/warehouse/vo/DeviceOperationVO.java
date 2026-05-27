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

import com.lnsoft.device.api.warehouse.entity.DeviceOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 设备投运表视图实体类
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceOperationVO对象", description = "设备投运表")
public class DeviceOperationVO extends DeviceOperation {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "设备投运工单详情信息")
	private List<DeviceOperationDetailVO> deviceOperationDetailVOList;

	@ApiModelProperty(value = "申请信息")
	private DeviceApplyVO deviceApplyVO;

	@ApiModelProperty(value = "出库信息")
	private DeviceOutboundVO deviceOutboundVO;

	@ApiModelProperty(value = "工单附件信息")
	private List<DeviceOrderFileVO> deviceOrderFileVOList;

}
