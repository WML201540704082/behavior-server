
package com.lnsoft.system.user.entity;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;


/**
 * 用户信息参数传递类
 *
 * @author xyzadmin
 */
@Data
@Builder
@ApiModel(description = "用户信息")
public class UserDTO extends User  {

	private static final long serialVersionUID = 1L;

	private Long userId;
	/**
	 * 旧密码
	 */
	private String oldPassword;
	/**
	 * 新密码
	 */
	private String newPassword;
	/**
	 * 新密码(确认密码)
	 */
	private String newPassword1;
	/**
	 * 修改用户id
	 */
	private Long updateId;
	/**
	 * 删除用户传参
	 */
	private String ids;
	/**
	 * 角色配置传参1/初始化密码传参 userId集合
	 */
	private String userIds;
	/**
	 * 角色配置传参2 roleId集合
	 */
	private String roleIds;

}
