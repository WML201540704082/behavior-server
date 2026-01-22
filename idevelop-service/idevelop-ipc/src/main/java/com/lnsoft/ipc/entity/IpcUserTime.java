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

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 工控机管控--用户时间表实体类
 *
 * @author Idevelop
 * @since 2025-11-17
 */
@Data
@TableName("ipc_user_time")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "IpcUserTime对象", description = "工控机管控--用户时间表")
public class IpcUserTime extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
	@TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private String userId;

    /**
     * 起始可登录时间
     */
    @ApiModelProperty(value = "起始可登录时间")
    private LocalDateTime loginBeginTime;
    /**
     * 结束可登录时间
     */
    @ApiModelProperty(value = "结束可登录时间")
    private LocalDateTime loginEndTime;
	/**
	 * 是否需要同步
	 */
	@ApiModelProperty(value = "是否需要同步")
	private String isSync;
}
