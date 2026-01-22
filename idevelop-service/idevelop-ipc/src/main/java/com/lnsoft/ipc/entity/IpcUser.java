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
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 工控机管控--用户表实体类
 *
 * @author Idevelop
 * @since 2025-11-17
 */
@Data
@TableName("ipc_user")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "IpcUser对象", description = "工控机管控--用户表")
public class IpcUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
	@TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 所属终端
     */
    @ApiModelProperty(value = "所属终端")
    private String terminal;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String name;
    /**
     * 用户类型
     */
    @ApiModelProperty(value = "用户类型")
    private String userType;

    /**
     * 人脸照片
     */
    @ApiModelProperty(value = "人脸照片")
    private String face;

	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	private String password;
	/**
	 * 部门
	 */
	@ApiModelProperty(value = "部门")
	private String department;
	/**
	 * 是否需要同步
	 */
	@ApiModelProperty(value = "是否需要同步  0-否 1-是")
	private String isSync;
	@TableField(exist = false)
	private List<IpcUserTime> timeList;

}
