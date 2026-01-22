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
package com.lnsoft.system.vo;

import com.lnsoft.system.entity.Remind;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 通知表视图实体类
 *
 * @author Idevelop
 * @since 2024-09-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "RemindVO对象", description = "通知表")
public class RemindVO extends Remind {
	private static final long serialVersionUID = 1L;
	/**
	 * 是否查看
	 */
	@ApiModelProperty(value = "是否查看  0否 1是")
	private Integer isLook;
}
