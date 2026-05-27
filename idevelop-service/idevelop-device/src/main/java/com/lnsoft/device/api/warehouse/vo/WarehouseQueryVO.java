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

import com.lnsoft.device.api.warehouse.entity.Warehouse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 仓库管理搜索类
 *
 * @author xyz
 * @since 2024-03-06
 */
@Data
public class WarehouseQueryVO  {
	@ApiModelProperty(value = "每页大小")
	private Integer size;

	@ApiModelProperty(value = "当前页")
	private Integer current;

	@ApiModelProperty(value = "所在仓库编码")
	private String inWarehouseCode;

}
