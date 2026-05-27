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
package com.lnsoft.device.api.i6000.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * cmdb和i6000的映射关系表实体类
 *
 * @author Idevelop
 * @since 2024-03-24
 */
@Data
@TableName("idevelop_cmdb_i6000_mapping")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "I6000CmdbMapping对象", description = "cmdb和i6000的映射关系表")
public class I6000CmdbMapping extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	@ApiModelProperty(value = "id")
	@TableId(value = "id", type = IdType.ASSIGN_UUID)
	private String id;
	/**
	 * cmdb模型ID
	 */
	@ApiModelProperty(value = "cmdb模型ID")
	private Long cmdbCiId;
	/**
	 * cmdb模型名称
	 */
	@ApiModelProperty(value = "cmdb模型名称")
	private String cmdbCiName;
	/**
	 * cmdb属性
	 */
	@ApiModelProperty(value = "cmdb属性")
	private String cmdbAttrCode;
	/**
	 * cmdb属性中文
	 */
	@ApiModelProperty(value = "cmdb属性中文")
	private String cmdbAttrLabel;
	/**
	 * cmdb属性类型
	 */
	@ApiModelProperty(value = "cmdb属性类型")
	private String cmdbAttrType;
	/**
	 * i6000模型编码
	 */
	@ApiModelProperty(value = "i6000模型编码")
	private String i6000CiId;
	/**
	 * i6000属性编码
	 */
	@ApiModelProperty(value = "i6000属性编码")
	private String i6000AttrCode;
	/**
	 * i6000属性数据类型
	 */
	@ApiModelProperty(value = "i6000属性数据类型")
	private String i6000Datatype;
	/**
	 * i6000属性值源类型：1-手工录入，2-枚举数据，3-配置类型，4-外部数据
	 */
	@ApiModelProperty(value = "i6000属性值源类型：1-手工录入，2-枚举数据，3-配置类型，4-外部数据")
	private String i6000OriType;
	/**
	 * i6000属性值源数据：当值源类型为2或者4时，该值代表对应的枚举或者外部数据，当值源类型为3也就是配置类型时，该值代表模型关联关系的ID
	 */
	@ApiModelProperty(value = "i6000属性值源数据：当值源类型为2或者4时，该值代表对应的枚举或者外部数据，当值源类型为3也就是配置类型时，该值代表模型关联关系的ID")
	private String i6000Origin;
	/**
	 * i6000属性值源数据文本
	 */
	@ApiModelProperty(value = "i6000属性值源数据文本")
	private String i6000Expan;

}
