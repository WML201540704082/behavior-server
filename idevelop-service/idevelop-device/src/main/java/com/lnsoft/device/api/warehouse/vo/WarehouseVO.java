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

import com.alibaba.excel.annotation.ExcelIgnore;
import com.lnsoft.device.api.warehouse.entity.Warehouse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 仓库管理表视图实体类
 *
 * @author Idevelop
 * @since 2024-03-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "WarehouseVO对象", description = "仓库管理表")
public class WarehouseVO extends Warehouse {
	private static final long serialVersionUID = 1L;

	@ExcelIgnore
	private String fullName;

}
