
package com.lnsoft.auth.granter;

import lombok.AllArgsConstructor;
import com.lnsoft.core.secure.exception.SecureException;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.core.tool.utils.SpringUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TokenGranterBuilder
 *
 * @author guozhao
 */
@AllArgsConstructor
public class TokenGranterBuilder {

	/**
	 * TokenGranter缓存池
	 */
	private static final Map<String, ITokenGranter> GRANTER_POOL = new ConcurrentHashMap<>();

	static {
		GRANTER_POOL.put(PasswordTokenGranter.GRANT_TYPE, SpringUtil.getBean(PasswordTokenGranter.class));
		GRANTER_POOL.put(CaptchaTokenGranter.GRANT_TYPE, SpringUtil.getBean(CaptchaTokenGranter.class));
		GRANTER_POOL.put(RefreshTokenGranter.GRANT_TYPE, SpringUtil.getBean(RefreshTokenGranter.class));
		GRANTER_POOL.put(SocialTokenGranter.GRANT_TYPE, SpringUtil.getBean(SocialTokenGranter.class));
//		GRANTER_POOL.put(NoPasswordTokenGranter.GRANT_TYPE, SpringUtil.getBean(NoPasswordTokenGranter.class));
	}

	/**
	 * 获取TokenGranter
	 *
	 * @param grantType 授权类型
	 * @return ITokenGranter
	 */
	public static ITokenGranter getGranter(String grantType) {
		ITokenGranter tokenGranter = GRANTER_POOL.get(Func.toStr(grantType, PasswordTokenGranter.GRANT_TYPE));
		if (tokenGranter == null) {
			throw new SecureException("no grantType was found");
		} else {
			return tokenGranter;
		}
	}

}
