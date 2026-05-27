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
package com.lnsoft.device.api.stock.vo;

import com.lnsoft.device.api.cmdb.entity.DeviceDataBasedTemplate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 设备-数据治理模板表视图实体类
 *
 * @author Idevelop
 * @since 2024-06-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceDataBasedTemplateVO对象", description = "设备-数据治理模板表")
public class DeviceDataBasedTemplateVO extends DeviceDataBasedTemplate {
	private static final long serialVersionUID = 1L;

}
