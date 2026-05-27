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
package com.lnsoft.device.api.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 信通一体化平台导入文件结果下载实体类
 *
 * @author Idevelop
 * @since 2026-03-01
 */
@Data
@TableName("idevelop_import_result")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ImportResult对象", description = "信通一体化平台导入文件结果下载")
public class ImportResult extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 本次导入文件名称
     */
    @ApiModelProperty(value = "本次导入文件名称")
    private String importName;
    /**
     * 本次导入UUID
     */
    @ApiModelProperty(value = "本次导入UUID")
    private String importUuid;
    /**
     * 本次导入文件内容数量
     */
    @ApiModelProperty(value = "本次导入文件内容数量")
    private Integer importNumber;
    /**
     * 本次导入文件是否完成
     */
    @ApiModelProperty(value = "本次导入文件是否完成")
    private Integer importStatus;
    /**
     * 文件导入模块
     */
    @ApiModelProperty(value = "文件导入模块")
    private String importType;


}
