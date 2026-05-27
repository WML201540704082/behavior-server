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

import com.lnsoft.device.api.warehouse.entity.DeviceApply;
import com.lnsoft.device.dto.DeviceOrderFileDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 设备申请表数据传输对象实体类
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceApplyDTO extends DeviceApply {
	private static final long serialVersionUID = 1L;

	/**
	 * 设备申请单详情信息
	 */
	@ApiModelProperty(value = "设备申请单详情信息")
	private List<DeviceApplyDetailDTO> deviceApplyDetailDTOList;

	/**
	 * 工单编号，多个用英文逗号隔开
	 */
	@ApiModelProperty(value = "工单编号，多个用英文逗号隔开")
	private String orderNoList;

	/**
	 * 设备工单附件
	 */
	@ApiModelProperty(value = "设备工单附件")
	private List<DeviceOrderFileDTO> deviceOrderFileDTOList;

	/**
	 * 工单流程节点标识
	 */
	@ApiModelProperty(value = "工单流程节点标识")
	private String taskDefinitionKey;
	/**
	 * 审核意见
	 */
	@ApiModelProperty(value = "审核意见")
	private String comment;
	/**
	 * 审批意见
	 */
	@ApiModelProperty(value = "审批意见 0 同意 1 拒绝")
	private Integer workerStatus;
	/**
	 * 审批角色
	 */
	@ApiModelProperty(value = "审批角色")
	private String examineRole;
	@ApiModelProperty(value = "操作标识 1 个人工作台 2 设备申请")
	private Integer examineType;

	@ApiModelProperty(value = "查看已办/待办标识 0 待办列表 1 已办列表")
	private Integer queryHandleFlag;
}
