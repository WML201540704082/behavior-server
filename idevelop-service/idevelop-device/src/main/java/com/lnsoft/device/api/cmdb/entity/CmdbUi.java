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
package com.lnsoft.device.api.cmdb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 前端属性/配置项配置表实体类
 *
 * @author Idevelop
 * @since 2024-06-24
 */
@Data
@TableName("idevelop_cmdb_ui")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CmdbUi对象", description = "前端属性/配置项配置表")
public class CmdbUi extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键ID")
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 属性/配置项key
	 */
	@ApiModelProperty(value = "属性/配置项key")
	private String name;

	/**
	 * 属性/配置项value
	 */
	@ApiModelProperty(value = "属性/配置项value")
	private Long value;

	/**
	 * 说明
	 */
	@ApiModelProperty(value = "说明")
	private String remark;

	/**
	 * 类型 0: 属性key, 1: 配置id
	 */
	@ApiModelProperty(value = "类型 0: 属性id, 1: 配置id")
	private Integer type;


}
