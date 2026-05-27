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
package com.lnsoft.device.api.i6000.vo;

import com.lnsoft.device.api.i6000.entity.I6000ExternalAdd;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 外部数据表视图实体类
 *
 * @author Idevelop
 * @since 2024-06-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "I6000ExternalAddVO对象", description = "外部数据表")
public class I6000ExternalAddVO extends I6000ExternalAdd {
	private static final long serialVersionUID = 1L;

}
