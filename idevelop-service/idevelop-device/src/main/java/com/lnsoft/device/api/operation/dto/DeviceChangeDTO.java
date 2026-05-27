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
package com.lnsoft.device.api.operation.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lnsoft.device.api.operation.entity.DeviceChange;
import com.lnsoft.device.api.operation.entity.DeviceChangeList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 设备变更数据传输对象实体类
 *
 * @author Idevelop
 * @since 2024-03-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceChangeDTO extends DeviceChange {
	private static final long serialVersionUID = 1L;
	/**
	 * 查询开始时间
	 */
	@ApiModelProperty(value = "查询开始时间")
	private String startDate;
	/**
	 * 查询结束时间
	 */
	@ApiModelProperty(value = "查询结束时间")
	private String endDate;
	/**
	 * 更改后的设备列表数据
	 */
	private List<DeviceChangeList> newChangeDeviceDTOList;
	/**
	 * 更改前的设备列表数据
	 */
	private List<DeviceChangeList> oldChangeDeviceDTOList;
	/**
	 * 提交或暂存判断标识
	 */
	private String submitStatus;
	/**
	 * 工单编号
	 */
	private List<String> filingNoList;
	/**
	 * 工单编号，多个用英文逗号隔开
	 */
	@ApiModelProperty(value = "工单编号，多个用英文逗号隔开")
	private String orderNoList;
	/**
	 * 设备编码
	 */
	private String deviceCode;
	/**
	 * 区域编码
	 */
	private String regionCode;

}
