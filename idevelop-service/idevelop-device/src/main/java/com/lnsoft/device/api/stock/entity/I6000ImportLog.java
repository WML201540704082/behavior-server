/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
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
package com.lnsoft.device.api.stock.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 管理员批量导入I6000数据记录表实体类
 *
 * @author Idevelop
 * @since 2025-11-02
 */
@Data
@TableName("idevelop_i6000_import_log")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "I6000ImportLog对象", description = "管理员批量导入I6000数据记录表")
public class I6000ImportLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    private String id;
    /**
     * 本次导入UUID
     */
    @ApiModelProperty(value = "本次导入UUID")
    private String importUuid;
    /**
     * 设备编码
     */
    @ApiModelProperty(value = "设备编码")
    private String deviceCode;
    /**
     * 导入情况
     */
    @ApiModelProperty(value = "导入情况")
    private String deviceInfo;


}
