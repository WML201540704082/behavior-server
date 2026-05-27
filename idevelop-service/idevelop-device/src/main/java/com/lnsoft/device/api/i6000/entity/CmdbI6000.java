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

import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 模型属性映射表(查询专用)实体类
 *
 * @author Idevelop
 * @since 2024-11-06
 */
@Data
@TableName("idevelop_cmdb_i6000")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CmdbI6000对象", description = "模型属性映射表(查询专用)")
public class CmdbI6000 extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private String id;
    /**
     * cmdb所属模型ID
     */
    @ApiModelProperty(value = "cmdb所属模型ID")
    private Long cmdbCiId;
    /**
     * cmdb属性ID
     */
    @ApiModelProperty(value = "cmdb属性ID")
    private Long cmdbAttrId;
    /**
     * cmdb属性英文名
     */
    @ApiModelProperty(value = "cmdb属性英文名")
    private String cmdbAttrName;
    /**
     * cmdb属性类型
     */
    @ApiModelProperty(value = "cmdb属性类型")
    private String cmdbAttrType;
    /**
     * i6000所属模型编码
     */
    @ApiModelProperty(value = "i6000所属模型编码")
    private String i6000CiId;
    /**
     * i6000属性id
     */
    @ApiModelProperty(value = "i6000属性id")
    private String i6000AttrId;
    /**
     * i6000属性英文名
     */
    @ApiModelProperty(value = "i6000属性英文名")
    private String i6000AttrName;
    /**
     * i6000属性类型
     */
    @ApiModelProperty(value = "i6000属性类型")
    private String i6000AttrType;


}
