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
 * i6000模型属性实体类
 *
 * @author Idevelop
 * @since 2024-03-19
 */
@Data
@TableName("idevelop_i6000_ci_attr")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "I6000CiAttr对象", description = "i6000模型属性")
public class I6000CiAttr extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty(value = "id" )
	@TableId(value = "id", type = IdType.ASSIGN_UUID)
	private String id;

	/**
	 * 模型code
	 */
	@ApiModelProperty(value = "模型code")
	private String ciCode;

	/**
	 * 属性编码
	 */
	@ApiModelProperty(value = "属性编码")
	private String attrCode;
	/**
	 * 属性名称
	 */
	@ApiModelProperty(value = "属性名称")
	private String attrName;
	/**
	 * 属性数据类型
	 */
	@ApiModelProperty(value = "属性数据类型")
	private String datatypeName;
	/**
	 * 属性长度
	 */
	@ApiModelProperty(value = "属性长度")
	private String attrDataLen;
	/**
	 * 是否可见
	 */
	@ApiModelProperty(value = "是否可见")
	private String viewFlag;
	/**
	 * 是否标准集 Y:是 N:否
	 */
	@ApiModelProperty(value = "是否标准集 Y:是 N:否")
	private String standardFlag;
	/**
	 * 是否一对多，Y-是，N-否
	 */
	@ApiModelProperty(value = "是否一对多，Y-是，N-否")
	private String otnFlag;
	/**
	 * 属性值源类型：1-手工录入，2-枚举数据，3-配置类型，4-外部数据
	 */
	@ApiModelProperty(value = "属性值源类型：1-手工录入，2-枚举数据，3-配置类型，4-外部数据")
	private String oriType;
	/**
	 * 属性值源数据：当值源类型为2或者4时，该值代表对应的枚举或者外部数据，当值源类型为3也就是配置类型时，该值代表模型关联关系的ID
	 */
	@ApiModelProperty(value = "属性值源数据：当值源类型为2或者4时，该值代表对应的枚举或者外部数据，当值源类型为3也就是配置类型时，该值代表模型关联关系的ID")
	private String origin;
	/**
	 * 是否可采集
	 */
	@ApiModelProperty(value = "是否可采集")
	private String collectFlag;
	/**
	 * 视图
	 */
	@ApiModelProperty(value = "视图")
	private String viewUnit;
	private String unit;
	/**
	 * 是否只读
	 */
	@ApiModelProperty(value = "是否只读")
	private String readonly;
	/**
	 * 是否可为空
	 */
	@ApiModelProperty(value = "是否可为空")
	private String nullFlag;
	/**
	 * 是否可输入
	 */
	@ApiModelProperty(value = "是否可输入")
	private String inputFlag;
	/**
	 * 所属单位编码
	 */
	@ApiModelProperty(value = "所属单位编码")
	private String corpCode;
	/**
	 * 当值源类型为3也就是配置类型时，该值代表模型关联关系的ID
	 */
	@ApiModelProperty(value = "当值源类型为3也就是配置类型时，该值代表模型关联关系的ID")
	private String asctCitypeId;

	/**
	 * 是否与CMDB映射 0否, 1是
	 */
	@ApiModelProperty("是否与CMDB映射 0否, 1是")
	private Integer isMapping;
	/**
	 * 是否需要手动映射 0否, 1是
	 */
	@ApiModelProperty("是否需要手动映射 0否, 1是")
	private Integer isNeed;

}
