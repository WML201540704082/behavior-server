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
package com.lnsoft.device.api.erp.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * erp成本中心实体类
 *
 * @author Idevelop
 * @since 2024-04-02
 */
@Data
@TableName("idevelop_erp_kostl")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ErpKostl对象", description = "erp成本中心")
public class ErpKostl extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 成本中心编码
	 */
	@ApiModelProperty(value = "成本中心编码")
	@TableId("kostl")
	private String kostl;
	/**
	 * 成本中心描述
	 */
	@ApiModelProperty(value = "成本中心描述")
	private String kostlT;

	/**
	 * 成本中心全描述
	 */
	@ApiModelProperty(value = "成本中心全描述")
	private String kostlLt;
	/**
	 * 成本中心状态
	 */
	@ApiModelProperty(value = "成本中心状态")
	private Integer koslStatus;
	/**
	 * 维护工厂
	 */
	@ApiModelProperty(value = "维护工厂")
	private String swerk;


}
