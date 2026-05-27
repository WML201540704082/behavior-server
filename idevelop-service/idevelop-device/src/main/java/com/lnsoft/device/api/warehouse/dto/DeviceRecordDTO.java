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

import com.lnsoft.device.api.warehouse.entity.DeviceRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 设备建档数据传输对象实体类
 *
 * @author Idevelop
 * @since 2024-02-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceRecordDTO extends DeviceRecord {
	private static final long serialVersionUID = 1L;

	/**
	 * 查询开始时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "查询开始时间")
	private Date startDate;
	/**
	 * 查询结束时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "查询结束时间")
	private Date endDate;
	/**
	 * 工单编号，多个用英文逗号隔开
	 */
	@ApiModelProperty(value = "工单编号，多个用英文逗号隔开")
	private String orderNoList;
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
	 * 工单编号
	 */
	private List<String> filingNoList;
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
}
