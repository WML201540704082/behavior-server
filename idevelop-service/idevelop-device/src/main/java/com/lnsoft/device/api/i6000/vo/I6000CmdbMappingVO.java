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

import com.lnsoft.device.api.i6000.entity.I6000CmdbMapping;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * cmdb和i6000的映射关系表视图实体类
 *
 * @author Idevelop
 * @since 2024-03-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "I6000CmdbMappingVO对象", description = "cmdb和i6000的映射关系表")
public class I6000CmdbMappingVO extends I6000CmdbMapping {
	private static final long serialVersionUID = 1L;

	private Integer count;

	private String i6000CiName;

}
