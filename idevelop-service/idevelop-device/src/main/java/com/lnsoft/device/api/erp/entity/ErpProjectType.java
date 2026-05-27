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
 * 实体类
 *
 * @author Idevelop
 * @since 2024-03-28
 */
@Data
@TableName("idevelop_erp_project_type")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ErpProjectType对象", description = "ErpProjectType对象")
public class ErpProjectType extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * erp项目类型代码
	 */
	@ApiModelProperty(value = "erp项目类型代码")
	@TableId("project_type")
	private String projectType;

	/**
	 * erp项目类型描述
	 */
	@ApiModelProperty(value = "erp项目类型描述")
	private String projectDesc;


}
