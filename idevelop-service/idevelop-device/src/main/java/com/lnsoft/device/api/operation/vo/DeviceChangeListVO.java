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
package com.lnsoft.device.api.operation.vo;

import com.lnsoft.device.api.operation.entity.DeviceChangeList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 设备变更视图实体类
 *
 * @author Idevelop
 * @since 2024-03-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceChangeListVO对象", description = "设备变更")
public class DeviceChangeListVO extends DeviceChangeList {
	private static final long serialVersionUID = 1L;
	/**
	 * 模型id
	 */
	private String ciId;

	/**
	 * 认证用户
	 */
	@ApiModelProperty(value = "认证用户")
	private String approveuUser;
	/**
	 * 认证密码
	 */
	@ApiModelProperty(value = "认证密码")
	private String approveuPassword;

}
