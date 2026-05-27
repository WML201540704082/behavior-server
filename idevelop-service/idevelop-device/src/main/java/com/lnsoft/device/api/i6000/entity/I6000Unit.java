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

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * I6000单位实体类
 *
 * @author Idevelop
 * @since 2024-04-12
 */
@Data
@TableName("idevelop_i6000_unit")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "I6000Unit对象", description = "I6000单位")
public class I6000Unit extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * i6000单位ID
     */
    @ApiModelProperty(value = "i6000单位ID")
    @TableId("i6000_unit_id")
  private String i6000UnitId;
    /**
     * i6000单位Name
     */
    @ApiModelProperty(value = "i6000单位Name")
    private String i6000UnitName;


}
