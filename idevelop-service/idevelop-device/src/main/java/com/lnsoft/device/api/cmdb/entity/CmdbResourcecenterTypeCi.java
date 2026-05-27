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

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * IT设备模型ID管理(唯一数据)实体类
 *
 * @author xuel
 * @since 2024-03-01
 */
@Data
@TableName("idevelop_cmdb_resourcecenter_type_ci")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CmdbResourcecenterTypeCi对象", description = "IT设备模型ID管理(唯一数据)")
public class CmdbResourcecenterTypeCi extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 模型id
	 */
	@ApiModelProperty(value = "模型id")
	@TableId("ci_id")
	private Long ciId;


}
