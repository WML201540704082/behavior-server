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
package com.lnsoft.device.api.operation.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 设备变更实体类
 *
 * @author Idevelop
 * @since 2024-03-07
 */
@Data
@TableName("idevelop_device_change_logs")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceChangeLogs对象", description = "设备变更")
public class DeviceChangeLogs extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
	@TableId
    private String id;
    /**
     * 属性名称
     */
    @ApiModelProperty(value = "属性名称")
    private String attributeName;
    /**
     * 修改前
     */
    @ApiModelProperty(value = "修改前")
    private String changeBefore;
    /**
     * 修改后
     */
    @ApiModelProperty(value = "修改后")
    private String changeAfter;
    /**
     * 变更编号
     */
    @ApiModelProperty(value = "变更关联id")
    private String changeId;
    /**
     * 设备编码
     */
    @ApiModelProperty(value = "设备编码")
    private String deviceCode;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 属性编码
     */
    @ApiModelProperty(value = "属性编码")
    private String attributeCode;


}
