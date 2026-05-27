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
package com.lnsoft.device.api.asset.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDate;

/**
 * 实体类
 *
 * @author Idevelop
 * @since 2024-05-09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("idevelop_generate_code")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "GenerateCode对象", description = "GenerateCode对象")
public class GenerateCode extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 唯一key
	 */
	@ApiModelProperty(value = "唯一key")
	@TableId("unique_key")
	private String uniqueKey;
	/**
	 * 序号
	 */
	@ApiModelProperty(value = "序号")
	private Long number;
	/**
	 * 时间
	 */
	@ApiModelProperty(value = "时间")
	private LocalDate time;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型")
	private String type;

}
