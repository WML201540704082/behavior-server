
package com.lnsoft.auth.granter;


import com.lnsoft.system.user.entity.UserInfo;

/**
 * 授权认证统一接口.
 *
 * @author guozhao
 */
public interface ITokenGranter {

	/**
	 * 获取用户信息
	 *
	 * @param tokenParameter 授权参数
	 * @return UserInfo
	 */
	UserInfo grant(TokenParameter tokenParameter);

}
