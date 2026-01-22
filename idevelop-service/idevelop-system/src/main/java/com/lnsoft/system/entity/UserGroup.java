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
package com.lnsoft.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 班组表实体类
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@Data
@TableName("idevelop_user_group")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserGroup对象", description = "班组表")
public class UserGroup extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 组名
     */
    @ApiModelProperty(value = "组名")
    private String groupName;
    /**
     * 部门名
     */
    @ApiModelProperty(value = "部门名")
    private String deptId;
	/**
	 * 部门名
	 */
	@ApiModelProperty(value = "部门名称")
	private String deptName;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;
    /**
     * 部门编码
     */
    @ApiModelProperty(value = "部门编码")
    private String groupCode;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;


}
