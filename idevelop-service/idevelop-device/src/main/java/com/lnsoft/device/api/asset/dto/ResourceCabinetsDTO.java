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
package com.lnsoft.device.api.asset.dto;

import com.lnsoft.device.api.asset.entity.ResourceCabinets;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

/**
 * 空间资源管理机柜表数据传输对象实体类
 *
 * @author Idevelop
 * @since 2024-02-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceCabinetsDTO extends ResourceCabinets {
	private static final long serialVersionUID = 1L;

	private String ids;
	@ApiModelProperty(value = "查询起始日期")
	private String oprtDateBegin;
	@ApiModelProperty(value = "查询终止日期")
	private String oprtDateEnd;

	@ApiModelProperty(value = "设备集合")
	private List<DeviceCmdbDTO> deviceList;

	@ApiModelProperty(value = "空标识")
	private String nullMark;

	@ApiModelProperty(value = "查询类型  device-设备列表  cabinet-机柜列表")
	private String findType;

	private String roomId;


}
