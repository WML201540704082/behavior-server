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
package com.lnsoft.device.api.warehouse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 各地市同步sdn和radius控制表实体类
 *
 * @author Idevelop
 * @since 2026-01-27
 */
@Data
@TableName("idevelop_sync_sdn")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SyncSdn对象", description = "各地市同步sdn和radius控制表")
public class SyncSdn extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
    /**
     * 区域code
     */
    @ApiModelProperty(value = "区域code")
    private String regionCode;
    /**
     * 类型: 0 设备投运 1 设备退运 2 子网管理（新增、修改） 3 子网管理（删除） 4 设备变更
     */
    @ApiModelProperty(value = "类型: 0 设备投运 1 设备退运 2 子网管理（新增、修改） 3 子网管理（删除） 4 设备变更")
    private String switcherType;
    /**
     * 请求类型: Sync/Sdn
     */
    @ApiModelProperty(value = "请求类型: Sync/Sdn")
    private String requestType;
    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    private String request;


}
