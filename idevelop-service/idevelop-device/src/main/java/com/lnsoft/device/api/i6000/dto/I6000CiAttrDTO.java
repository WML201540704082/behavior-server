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
package com.lnsoft.device.api.i6000.dto;

import com.lnsoft.device.api.i6000.entity.I6000CiAttr;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * i6000模型属性数据传输对象实体类
 *
 * @author Idevelop
 * @since 2024-03-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class I6000CiAttrDTO extends I6000CiAttr {
	private static final long serialVersionUID = 1L;

}
