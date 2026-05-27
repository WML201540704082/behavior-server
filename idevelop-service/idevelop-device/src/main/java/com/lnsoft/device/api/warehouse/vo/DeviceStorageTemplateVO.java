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

import com.lnsoft.device.api.warehouse.entity.DeviceStorageTemplate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 设备入库导入模板表视图实体类
 *
 * @author Idevelop
 * @since 2024-02-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceStorageTemplateVO对象", description = "设备入库导入模板表")
public class DeviceStorageTemplateVO extends DeviceStorageTemplate {
	private static final long serialVersionUID = 1L;

}
