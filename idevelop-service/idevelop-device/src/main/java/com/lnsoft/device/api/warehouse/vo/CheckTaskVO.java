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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lnsoft.device.api.warehouse.dto.CheckTaskDeviceDTO;
import com.lnsoft.device.api.warehouse.entity.CheckTask;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * 盘点任务视图实体类
 *
 * @author Idevelop
 * @since 2024-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CheckTaskVO对象", description = "盘点任务")
public class CheckTaskVO extends CheckTask {
	private static final long serialVersionUID = 1L;

	/**
	 * 发起人id集合
	 */
	@ApiModelProperty(value = "发起人id集合(传这个)")
//	@JsonSerialize(using = ToStringSerializer.class)
	private List<String> receiverIds;

	/**
	 * 部门范围id集合
	 */
	@ApiModelProperty(value = "部门范围id集合(传这个)")
//	@JsonSerialize(using = ToStringSerializer.class)
	private List<String> checkDeptIds;

	/**
	 * 设备分类id集合
	 */
	@ApiModelProperty(value = "设备分类id集合(传这个)")
//	@JsonSerialize(using = ToStringSerializer.class)
	private List<String> deviceCategoryIds;

	/**
	 * 设备类型id集合
	 */
	@ApiModelProperty(value = "设备类型id集合(传这个)")
//	@JsonSerialize(using = ToStringSerializer.class)
	private List<String> deviceTypeIds;
	/**
	 * 上期设备类型
	 */
	@ApiModelProperty(value = "上期设备(2:盘盈设备, 4:盘亏设备, 3:退运, 5:临时退网)")
	private List<String> lastDevice;

	/**
	 * 盘点进度
	 */
	@ApiModelProperty(value = "盘点进度")
	private String checkProgress;

	/**
	 * 已盘点数
	 */
	@ApiModelProperty(value = "已盘点")
	private Long isCheckNum;

	/**
	 * 未盘点数
	 */
	@ApiModelProperty(value = "未盘点")
	private Long noCheckNum;

	/**
	 * 盘盈进度
	 */
	@ApiModelProperty(value = "未盘盈")
	private Long wpy;
	/**
	 * 盘盈进度
	 */
	@ApiModelProperty(value = "盘盈进度")
	private Long pyProgress;
	/**
	 * 盘盈进度
	 */
	@ApiModelProperty(value = "未盘亏")
	private Long wpk;
	/**
	 * 盘亏进度
	 */
	@ApiModelProperty(value = "盘亏进度")
	private Long pkProgress;

	@ApiModelProperty(value = "是否按照历史盘点发起")
	private String isHistory;

	private List<CheckTaskDeviceVO> taskDeviceVOS;

}
