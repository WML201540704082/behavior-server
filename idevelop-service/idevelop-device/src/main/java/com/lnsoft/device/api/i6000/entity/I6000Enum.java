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
 * i6000枚举数据表实体类
 *
 * @author Idevelop
 * @since 2024-08-02
 */
@Data
@TableName("idevelop_i6000_enum")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "I6000Enum对象", description = "i6000枚举数据表")
public class I6000Enum extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 枚举数据名称
     */
    @ApiModelProperty(value = "枚举数据名称")
    private String enumName;
    /**
     * 枚举数据编码
     */
    @ApiModelProperty(value = "枚举数据编码")
    private String enumId;
    /**
     * 枚举项编码
     */
    @ApiModelProperty(value = "枚举项编码")
    private String enumvalCode;
    /**
     * 枚举项名称
     */
    @ApiModelProperty(value = "枚举项名称")
    private String enumvalName;


}
