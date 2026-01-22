
package com.lnsoft.system.user.entity;

import com.lnsoft.core.pojo.Key;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息
 *
 * @author guozhao
 */
@Data
@ApiModel(description = "用户信息")
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户基础信息
	 */
	@ApiModelProperty(value = "用户")
	private User user;
	/**
	 * 用户会话密钥
	 */
	@ApiModelProperty(value = "用户")
	private Key cryptoKey;
	/**
	 * 权限标识集合
	 */
	@ApiModelProperty(value = "权限集合")
	private List<String> permissions;

	/**
	 * 角色集合
	 */
	@ApiModelProperty(value = "角色集合")
	private List<String> roles;

	/**
	 * 角色集合名称
	 */
	@ApiModelProperty(value = "角色集合名称")
	private List<String> roleName;

	/**
	 * 第三方授权id
	 */
	@ApiModelProperty(value = "第三方授权id")
	private String oauthId;

}
