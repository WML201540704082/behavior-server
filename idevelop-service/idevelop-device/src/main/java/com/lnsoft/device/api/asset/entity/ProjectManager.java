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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 项目管理实体类
 *
 * @author xuel
 * @since 2024-03-04
 */
@Data
@TableName("idevelop_project_manager")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ProjectManager对象", description = "项目管理")
public class ProjectManager extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * wbs项目编码
	 */
	@ApiModelProperty(value = "wbs项目编码")
	@TableId("wbs_code")
	private String wbsCode;
	/**
	 * wbs项目
	 */
	@ApiModelProperty(value = "wbs项目")
	private String wbsName;
	/**
	 * 项目类型code
	 */
	@ApiModelProperty(value = "项目类型code")
	private String projectTypeCode;
	/**
	 * 项目类型名称
	 */
	@ApiModelProperty(value = "项目类型名称")
	private String projectType;
	/**
	 * 项目定义编码
	 */
	@ApiModelProperty(value = "项目定义编码")
	private String projectDefineCode;
	/**
	 * 项目定义名称
	 */
	@ApiModelProperty(value = "项目定义名称")
	private String projectDefine;
	/**
	 * 维护工厂编码
	 */
	@ApiModelProperty(value = "维护工厂编码")
	private String projectUnitCode;
	/**
	 * 维护工厂名称
	 */
	@ApiModelProperty(value = "维护工厂名称")
	private String projectUnitName;
	/**
	 * 删除标记, Y存在,X删除
	 */
	@ApiModelProperty(value = "删除标记, Y存在,X删除")
	private String loevm;

	/**
	 * 项目创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "项目创建时间")
	private LocalDateTime projectCreateTime;


}
