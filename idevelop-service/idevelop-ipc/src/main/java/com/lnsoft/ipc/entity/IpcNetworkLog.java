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
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 工控机管控-网络访问记录表实体类
 *
 * @author Idevelop
 * @since 2025-11-17
 */
@Data
@TableName("ipc_network_log")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "IpcNetworkLog对象", description = "工控机管控-网络访问记录表")
public class IpcNetworkLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 登录用户id
     */
    @ApiModelProperty(value = "登录用户id")
    private String userId;
    /**
     * 登录用户名
     */
    @ApiModelProperty(value = "登录用户名")
    private String userName;
    /**
     * 用户部门
     */
    @ApiModelProperty(value = "用户部门")
    private String userDept;
    /**
     * 终端ip
     */
    @ApiModelProperty(value = "终端ip")
    private String ip;
    /**
     * url地址
     */
    @ApiModelProperty(value = "url地址")
    private String url;
	/**
     * 业务系统名称
     */
    @ApiModelProperty(value = "业务系统名称")
    private String businessName;
    /**
     * 访问开始时间
     */
    @ApiModelProperty(value = "访问开始时间")
    private LocalDateTime startTime;
    /**
     * 访问结束时间
     */
    @ApiModelProperty(value = "访问结束时间")
    private LocalDateTime endTime;
    /**
     * 访问时长
     */
    @ApiModelProperty(value = "访问时长")
    private Long accessLength;
    /**
     * 活动时长
     */
    @ApiModelProperty(value = "活动时长")
    private Integer activityLength;
	@TableField(exist = false)
	private List<String> ips;

}
