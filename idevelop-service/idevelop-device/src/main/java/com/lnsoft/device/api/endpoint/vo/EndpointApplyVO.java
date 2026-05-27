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
package com.lnsoft.device.api.endpoint.vo;


import com.lnsoft.device.api.endpoint.entity.EndpointApply;
import com.lnsoft.device.api.endpoint.entity.EndpointPortVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据共享接口申请表视图实体类
 *
 * @author Idevelop
 * @since 2024-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "EndpointApplyVO对象", description = "数据共享接口申请表")
public class EndpointApplyVO extends EndpointApply {
	private static final long serialVersionUID = 1L;

	/**
	 * 接口清单id
	 */
	@ApiModelProperty(value = "接口清单id")
	private String portId;

	private EndpointPortVO endpointPortVOList;

}
