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

import com.alibaba.excel.annotation.ExcelIgnore;
import com.lnsoft.device.api.warehouse.entity.DeviceScrapList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 设备报废列表数据传输对象实体类
 *
 * @author Idevelop
 * @since 2024-03-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceScrapListDTO extends DeviceScrapList {
	private static final long serialVersionUID = 1L;

	@ExcelIgnore
	@ApiModelProperty(value = "序号")
	private Integer index;

	/**
	 * cmdb对象字段
	 */
	@ApiModelProperty(value = "cmdbEntity")
	private Map<String,Object> entity;

}
