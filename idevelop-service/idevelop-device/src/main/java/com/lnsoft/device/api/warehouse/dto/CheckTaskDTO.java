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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lnsoft.device.api.warehouse.entity.CheckTask;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 盘点任务数据传输对象实体类
 *
 * @author Idevelop
 * @since 2024-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CheckTaskDTO extends CheckTask {
	private static final long serialVersionUID = 1L;

	/**
	 * 发起人id集合
	 */
	@ApiModelProperty(value = "盘点人id集合(传这个)")
	@JsonSerialize(using = ToStringSerializer.class)
	private List<Long> receiverIds;

	/**
	 * 部门范围id集合
	 */
	@ApiModelProperty(value = "部门范围id集合(传这个)")
	@JsonSerialize(using = ToStringSerializer.class)
	private List<Long> checkDeptIds;

	/**
	 * 设备分类id集合
	 */
	@ApiModelProperty(value = "设备分类id集合(传这个)")
	@JsonSerialize(using = ToStringSerializer.class)
	private List<Long> deviceCategoryIds;

	/**
	 * 设备类型id集合
	 */
	@ApiModelProperty(value = "设备类型id集合(传这个)")
	@JsonSerialize(using = ToStringSerializer.class)
	private List<Long> deviceTypeIds;
	/**
	 * 上期设备类型
	 */
	@ApiModelProperty(value = "上期设备(2:盘盈设备, 4:盘亏设备, 3:退运, 5:临时退网)")
	private List<String> lastDevice;

	/**
	 * 盘点任务设备列表
	 */
	@ApiModelProperty(value = "盘点任务设备列表")
	private List<CheckTaskDeviceDTO> checkTaskDeviceDTOS;
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
	@ApiModelProperty(value = "工单编号，多个用英文逗号隔开(工作台)")
	private String orderNoList;

	/**
	 * 工单编号
	 */
	private List<String> filingNoList;

	private String ids;
}
