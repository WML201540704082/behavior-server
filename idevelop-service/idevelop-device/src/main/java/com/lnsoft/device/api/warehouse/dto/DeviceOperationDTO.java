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

import com.lnsoft.device.api.warehouse.entity.DeviceOperation;
import com.lnsoft.device.dto.DeviceOrderFileDTO;
import com.lnsoft.device.entity.SafeaccessSwitche;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

/**
 * 设备投运表数据传输对象实体类
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceOperationDTO extends DeviceOperation {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "投运详情信息")
	private List<DeviceOperationDetailDTO> deviceOperationDetailDTOList;

	@ApiModelProperty(value = "设备工单附件")
	private List<DeviceOrderFileDTO> deviceOrderFileDTOList;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "查询开始时间")
	private LocalDate startTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "查询结束时间")
	private LocalDate endTime;

	@ApiModelProperty(value = "设备投运单号")
	private String orderNoList;

	@ApiModelProperty(value = "工单流程节点标识")
	private String taskDefinitionKey;

	@ApiModelProperty(value = "审核意见")
	private String comment;

	@ApiModelProperty(value = "审批意见 0 同意 1 拒绝")
	private Integer workerStatus;

	@ApiModelProperty(value = "审批角色")
	private String examineRole;

	@ApiModelProperty(value = "查看已办/待办标识 0 待办列表 1 已办列表")
	private Integer queryHandleFlag;

	@ApiModelProperty(value = "设备编码")
	private String deviceCode;

	private List<SafeaccessSwitche> switchesList;
}
