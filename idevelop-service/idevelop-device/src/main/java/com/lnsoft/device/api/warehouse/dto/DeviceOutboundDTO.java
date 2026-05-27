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

import com.lnsoft.device.api.warehouse.entity.DeviceOutbound;
import com.lnsoft.device.dto.DeviceOrderFileDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 设备出库表数据传输对象实体类
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceOutboundDTO extends DeviceOutbound {
	private static final long serialVersionUID = 1L;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "申请日期开始时间")
	private Date beginTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "申请日期结束时间")
	private Date endTime;

	@ApiModelProperty(value = "个人工作台工单编号")
	private String orderNoList;

	@ApiModelProperty(value = "工单流程节点标识")
	private String taskDefinitionKey;

	@ApiModelProperty(value = "审核意见")
	private String comment;

	@ApiModelProperty(value = "审批意见 0 同意 1 拒绝")
	private Integer workerStatus;

	@ApiModelProperty(value = "审批角色")
	private String examineRole;

	@ApiModelProperty(value = "审核信息组装 如果是立即投运，需要组装 operation = 0,否则operation = 1")
	private Map<String, Object> variable;

	@ApiModelProperty(value = "设备详情信息")
	private List<DeviceOutboundDetailDTO> deviceOutboundDetailDTOList;

	@ApiModelProperty(value = "设备详情信息")
	private List<DeviceOperationDetailDTO> deviceOperationDetailDTOList;

	@ApiModelProperty(value = "设备工单附件")
	private List<DeviceOrderFileDTO> deviceOrderFileDTOList;

	@ApiModelProperty(value = "查看已办/待办标识 0 待办列表 1 已办列表")
	private Integer queryHandleFlag;
	@ApiModelProperty(value = "设备编码")
	private String deviceCode;
}
