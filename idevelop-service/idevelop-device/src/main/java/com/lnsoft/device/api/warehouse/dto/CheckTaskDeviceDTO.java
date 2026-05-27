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

import com.lnsoft.device.api.warehouse.entity.CheckTaskDevice;
import com.lnsoft.device.api.warehouse.vo.CheckDeviceHandleVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * 盘点任务设备详情数据传输对象实体类
 *
 * @author Idevelop
 * @since 2024-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CheckTaskDeviceDTO extends CheckTaskDevice {
	private static final long serialVersionUID = 1L;

	/**
	 * cmdb对象字段
	 */
	@ApiModelProperty(value = "cmdbEntity")
	private Map<String, Object> entity;

	@ApiModelProperty(value = "处置类型(0: 异常处置， 1：盘盈处置， 2：盘亏处置) 处置传参")
	private String editType;

	@ApiModelProperty(value = "异常信息处置")
	private List<CheckDeviceHandleVo> handleVos;

	@ApiModelProperty(value = "盘点展示类型  0-是盘点任务设备   1-不是盘点任务设备   2-台账中不存在，盘盈设备")
	private String showType;

	@ApiModelProperty(value = "关联的工单编号")
	private String filingNo;

	@ApiModelProperty(value = "审批意见 0 同意 1 拒绝")
	private Integer workerStatus;
}
