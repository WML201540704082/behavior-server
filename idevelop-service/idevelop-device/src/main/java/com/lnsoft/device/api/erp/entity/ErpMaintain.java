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
package com.lnsoft.device.api.erp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * erp维护工厂(对应单位)实体类
 *
 * @author Idevelop
 * @since 2024-03-22
 */
@Data
@TableName("idevelop_erp_maintain")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ErpMaintain对象", description = "erp维护工厂(对应单位)")
public class ErpMaintain extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 维护工厂编码
     */
    @ApiModelProperty(value = "维护工厂编码")
    private String code;
    /**
     * 维护工厂名称
     */
    @ApiModelProperty(value = "维护工厂名称")
    private String name;

	/**
     * 维护工厂全称
     */
    @ApiModelProperty(value = "维护工厂全称")
    private String fullName;


}
