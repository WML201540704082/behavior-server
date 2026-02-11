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
package com.lnsoft.ipc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 工控机管控--终端运行状态监控表实体类
 *
 * @author Idevelop
 * @since 2025-11-17
 */
@Data
@TableName("ipc_terminal_monitoring_bak")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "IpcTerminalMonitoring对象", description = "工控机管控--终端运行状态监控表")
public class IpcTerminalMonitoringBak extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private String userId;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;
    /**
     * 终端ip
     */
    @ApiModelProperty(value = "终端ip")
    private String ip;
    /**
     * 开机时间
     */
    @ApiModelProperty(value = "开机时间")
    private LocalDateTime openTime;
    /**
     * 关机时间
     */
    @ApiModelProperty(value = "关机时间")
    private LocalDateTime showdownTime;
    /**
     * 在线时长
     */
    @ApiModelProperty(value = "在线时长")
    private Long onlineLength;
    /**
     * 活动时长
     */
    @ApiModelProperty(value = "活动时长")
    private Long activityLength;
	@TableField(exist = false)
	private String dept;

}
