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
package com.lnsoft.device.api.erp.vo;

import com.lnsoft.device.api.erp.entity.ErpTranstplnr;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * ERP功能位置视图实体类
 *
 * @author Idevelop
 * @since 2024-03-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ErpTranstplnrVO对象", description = "ERP功能位置")
public class ErpTranstplnrVO extends ErpTranstplnr {
	private static final long serialVersionUID = 1L;

}
