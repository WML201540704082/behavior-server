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

import com.lnsoft.device.api.warehouse.entity.DeviceTransferDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 设备转资明细表数据传输对象实体类
 *
 * @author Idevelop
 * @since 2024-02-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceTransferDetailDTO extends DeviceTransferDetail {
	private static final long serialVersionUID = 1L;

	/**
	 * cmdb对象字段
	 */
	@ApiModelProperty(value = "cmdbEntity")
	private Map<String,Object> entity;

	private String deviceTypeCode;

	private String deviceStatusCode;

	private String deviceCategoryCode;

	private String deviceAddTypeCode;

	private String inWarehouseCode;

	// 维护工厂编码
	private String swerkName;

}
