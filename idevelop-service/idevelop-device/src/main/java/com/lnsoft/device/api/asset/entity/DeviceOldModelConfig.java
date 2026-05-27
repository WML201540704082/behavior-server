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

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lnsoft.core.mp.base.BaseEntity;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 老旧设备打分模型配置表实体类
 *
 * @author Idevelop
 * @since 2024-06-19
 */
@Data
@TableName("idevelop_device_old_model_config")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceOldModelConfig对象", description = "老旧设备打分模型配置表")
public class DeviceOldModelConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 配置类型
     */
    @ApiModelProperty(value = "配置类型")
    private String configType;
    /**
     * 配置项
     */
    @ApiModelProperty(value = "配置项")
    private String configItem;
    /**
     * 取值
     */
    @ApiModelProperty(value = "取值")
    private String value;
    /**
     * 设备分类
     */
    @ApiModelProperty(value = "设备分类")
    private Long deviceCategory;
    /**
     * 设备类型
     */
    @ApiModelProperty(value = "设备类型")
    private Long deviceType;
    /**
     * 设备分类名称
     */
    @ApiModelProperty(value = "设备分类名称")
    private String deviceCategoryName;
    /**
     * 设备类型名称
     */
    @ApiModelProperty(value = "设备类型名称")
    private String deciceTypeName;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 配置类型代号
     */
    @ApiModelProperty(value = "配置类型代号")
    private String configTypeCode;


}
