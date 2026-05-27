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
package com.lnsoft.device.api.cmdb.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 模型属性映射表(编辑)视图实体类
 *
 * @author Idevelop
 * @since 2024-03-14
 */
@Data
@ApiModel(value = "CmdbCiAttrGradeConvertVO对象", description = "模型属性映射表(编辑)")
public class CmdbCiAttrGradeConvertVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 模型ID_属性ID
	 */
	@ApiModelProperty(value = "模型ID_属性ID")
	private String attrCiId;
	/**
	 * 模型ID
	 */
	@ApiModelProperty(value = "模型ID")
	private Long ciId;
	/**
	 * 属性ID
	 */
	@ApiModelProperty(value = "属性ID")
	private Long id;
	/**
	 * 模型英文名
	 */
	@ApiModelProperty(value = "模型英文名")
	private String ciName;
	/**
	 * 模型中文名
	 */
	@ApiModelProperty(value = "模型中文名")
	private String ciLabel;
	/**
	 * 属性中文名
	 */
	@ApiModelProperty(value = "属性中文名")
	private String label;
	/**
	 * 属性英文名
	 */
	@ApiModelProperty(value = "属性英文名")
	private String name;
	/**
	 * 属性类型
	 */
	@ApiModelProperty(value = "属性类型")
	private String type;
	/**
	 * 属性类型文本
	 */
	@ApiModelProperty(value = "属性类型文本")
	private String typeText;
	/**
	 * 是否允许编辑
	 */
	@ApiModelProperty(value = "是否允许编辑")
	private Integer allowEdit;
	/**
	 * 属性级别
	 */
	@ApiModelProperty(value = "属性级别")
	private String attrGrade;
	/**
	 * 是否支持导入
	 */
	@ApiModelProperty(value = "是否支持导入")
	private String canImport;
	/**
	 * 是否支持输入
	 */
	@ApiModelProperty(value = "是否支持输入")
	private String canInput;
	/**
	 * 是否支持搜索
	 */
	@ApiModelProperty(value = "是否支持搜索")
	private String canSearch;
	/**
	 * 是否有额外配置
	 */
	@ApiModelProperty(value = "是否有额外配置")
	private String needConfig;
	/**
	 * 是否需要关联目标模型
	 */
	@ApiModelProperty(value = "是否需要关联目标模型")
	private String needTargetCi;
	/**
	 * 是否需要一整行显示编辑组件
	 */
	@ApiModelProperty(value = "是否需要一整行显示编辑组件")
	private String needWholeRow;
	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String description;
	/**
	 * 分组名称
	 */
	@ApiModelProperty(value = "分组名称")
	private String groupName;
	/**
	 * 属性录入方式
	 */
	@ApiModelProperty(value = "属性录入方式")
	private String inputType;
	/**
	 * 录入方式
	 */
	@ApiModelProperty(value = "录入方式")
	private String inputTypeText;
	/**
	 * 是否模型唯一属性成员
	 */
	@ApiModelProperty(value = "是否模型唯一属性成员")
	private Integer isCiUnique;
	/**
	 * 是否必填
	 */
	@ApiModelProperty(value = "是否必填")
	private Integer isRequired;
	/**
	 * 是否继承属性
	 */
	@ApiModelProperty(value = "是否继承属性")
	private Integer isExtended;
	/**
	 * 是否私有属性
	 */
	@ApiModelProperty(value = "是否私有属性")
	private Integer isPrivate;
	/**
	 * 是否允许搜索
	 */
	@ApiModelProperty(value = "是否允许搜索")
	private Integer isSearchAble;
	/**
	 * 是否唯一
	 */
	@ApiModelProperty(value = "是否唯一")
	private Integer isUnique;

	// /**
	//  * 排序
	//  */
	// @ApiModelProperty(value = "排序")
	// private Integer sort;

	/**
	 * 开始页数
	 */
	@ApiModelProperty(value = "开始页数")
	private Integer startPage;

}
