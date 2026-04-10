
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
 * 工控机管控--人脸识别登录日志表
 *
 * @author Idevelop
 * @since 2025-11-17
 */
@Data
@TableName("ipc_face_login_log")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "IpcFaceLoginLog对象", description = "工控机管控--人脸识别登录日志表")
public class IpcFaceLoginLog extends BaseEntity {

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
    private String username;

    /**
     * 起始可登录时间
     */
    @ApiModelProperty(value = "登录时间")
    private LocalDateTime loginTime;
    /**
     * 结束可登录时间
     */
    @ApiModelProperty(value = "登出时间")
    private LocalDateTime logoutTime;
	/**
	 * 部门
	 */
	@ApiModelProperty(value = "部门")
	private String department;

}
