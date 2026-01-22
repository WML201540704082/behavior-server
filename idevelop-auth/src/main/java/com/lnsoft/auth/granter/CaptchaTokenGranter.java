
package com.lnsoft.auth.granter;

import com.lnsoft.auth.enums.IdevelopUserEnum;
import com.lnsoft.auth.utils.TokenUtil;
import com.lnsoft.common.cache.CacheNames;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.pojo.Key;
import com.lnsoft.core.secure.props.IdevelopTokenProperties;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.*;
import com.lnsoft.core.tool.utils.crypto.sm2.SM2Key;
import com.lnsoft.core.tool.utils.crypto.sm2.SM2Util;
import com.lnsoft.core.tool.utils.crypto.sm4.SM4Util;
import com.lnsoft.system.user.entity.UserInfo;
import com.lnsoft.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证码TokenGranter
 *
 * @author guozhao
 */
@Component
@AllArgsConstructor
public class CaptchaTokenGranter implements ITokenGranter {

	public static final String GRANT_TYPE = "captcha";

	private IUserClient userClient;
	private RedisUtil redisUtil;
	private IdevelopTokenProperties idevelopTokenProperties;

	@Override
	public UserInfo grant(TokenParameter tokenParameter) {
		HttpServletRequest request = WebUtil.getRequest();

		// String key = request.getHeader(TokenUtil.CAPTCHA_HEADER_KEY);
		// String code = request.getHeader(TokenUtil.CAPTCHA_HEADER_CODE);
		String kid = request.getHeader(TokenUtil.IDEVELOP_KID);
		String enCpuk = request.getHeader(TokenUtil.IDEVELOP_ENCPUK);
		String tenantId = tokenParameter.getArgs().getStr("tenantId");
		String account = tokenParameter.getArgs().getStr("account");
		String password = tokenParameter.getArgs().getStr("password");

		// // 获取验证码
		// Object rediskey = redisUtil.get(CacheNames.CAPTCHA_KEY + key);
		// String redisCode = String.valueOf(rediskey);
		// // 判断验证码
		// if (code == null || !StringUtil.equalsIgnoreCase(redisCode, code)) {
		// 	throw new ServiceException(TokenUtil.CAPTCHA_NOT_CORRECT);
		// }
		// // 删除验证码
		// redisUtil.del(CacheNames.CAPTCHA_KEY + key);
		// 获取服务端私钥
		SM2Key sm2Key = (SM2Key) redisUtil.get(CacheNames.SM2_KEY + kid);
		if(sm2Key == null) {
			throw new ServiceException("登录状态过期, 请重新刷新页面");
		}
		String dePassword = SM2Util.decrypt(password, sm2Key.getSprk());
		// 用服务端私钥解密，获取客户端公钥，并用于加密产生的uid、ak、iv
		String cpuk = SM2Util.decrypt(enCpuk, sm2Key.getSprk());

		UserInfo userInfo = null;

		if (Func.isNoneBlank(account, dePassword)) {
			// 获取用户类型
			String userType = tokenParameter.getArgs().getStr("userType");
			Boolean iscCheck = tokenParameter.getArgs().getBool("iscCheck");
			R<UserInfo> result;
			// 根据不同用户类型调用对应的接口返回数据，用户可自行拓展
			if (userType.equals(IdevelopUserEnum.WEB.getName())) {
				if (iscCheck) {
					result = userClient.userInfo(tenantId, account);
				} else {
					result = userClient.userInfo(tenantId, account, DigestUtil.encrypt(dePassword));
				}
			} else if (userType.equals(IdevelopUserEnum.APP.getName())) {
				if (iscCheck) {
					result = userClient.userInfo(tenantId, account);
				} else {
					result = userClient.userInfo(tenantId, account, DigestUtil.encrypt(dePassword));
				}
			} else {
				if (iscCheck) {
					result = userClient.userInfo(tenantId, account);
				} else {
					result = userClient.userInfo(tenantId, account, DigestUtil.encrypt(dePassword));
				}
			}
			userInfo = result.isSuccess() ? result.getData() : null;
		}
		if(userInfo != null) {
			Key cryptoKey = new Key();
			cryptoKey.setSm4Key(SM4Util.generateKey());
			cryptoKey.setSm4Iv(SM4Util.generateIv());
			cryptoKey.setClientPubKey(cpuk);
			cryptoKey.setServerPriKey(sm2Key.getSprk());
			cryptoKey.setServerPubKey(sm2Key.getSpuk());
			userInfo.setCryptoKey(cryptoKey);
		}

		return userInfo;
	}

}
