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

import com.lnsoft.device.api.warehouse.entity.DeviceTransfer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 设备转资数据传输对象实体类
 *
 * @author Idevelop
 * @since 2024-02-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceTransferDTO extends DeviceTransfer {
	private static final long serialVersionUID = 1L;

	/**
	 * 设备转资设备列表信息
	 */
	@ApiModelProperty(value = "设备转资设备列表信息")
	private List<DeviceTransferDetailDTO> deviceTransferDetailDTOList;
	/**
	 * 设备编码
	 */
	@ApiModelProperty(value = "设备编码")
	private String deviceCode;
	/**
	 * 设备名称
	 */
	@ApiModelProperty(value = "设备名称")
	private String deviceName;
	/**
	 * 所在仓库
	 */
	@ApiModelProperty(value = "所在仓库")
	private String warehouse;

	/**
	 * 工单流程taskId
	 */
	@ApiModelProperty(value = "工单流程taskId")
	private String taskId;
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
	 * 审批状态
	 */
	@ApiModelProperty(value = "审批状态")
	private Integer workerStatus;
	/**
	 * 审批角色
	 */
	@ApiModelProperty(value = "审批角色")
	private String examineRole;

	/**
	 * 工单编号，多个用英文逗号隔开
	 */
	@ApiModelProperty(value = "工单编号，多个用英文逗号隔开")
	private String orderNoList;

	/**
	 * 工单编号
	 */
	private List<String> filingNoList;
	/**
	 * 查询开始时间
	 */
	private String minDate;
	/**
	 * 查询结束时间
	 */
	private String maxDate;

	private String entityKeepDeptName;
	private String pltxt;
	private String useKeepDeptName;
}
