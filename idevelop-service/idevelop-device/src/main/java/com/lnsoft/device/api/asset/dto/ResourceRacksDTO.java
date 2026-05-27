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

import com.lnsoft.device.api.asset.entity.ResourceRacks;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 空间资源管理机架表数据传输对象实体类
 *
 * @author Idevelop
 * @since 2024-02-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceRacksDTO extends ResourceRacks {
	private static final long serialVersionUID = 1L;
	private String ids;

}
