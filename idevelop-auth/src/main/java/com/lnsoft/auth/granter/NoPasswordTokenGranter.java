//package com.lnsoft.auth.granter;
//
//import com.lnsoft.auth.enums.IdevelopUserEnum;
//import com.lnsoft.auth.utils.TokenUtil;
//import com.lnsoft.common.cache.CacheNames;
//import com.lnsoft.core.log.exception.ServiceException;
//import com.lnsoft.core.pojo.Key;
//import com.lnsoft.core.secure.props.IdevelopTokenProperties;
//import com.lnsoft.core.tool.api.R;
//import com.lnsoft.core.tool.utils.DigestUtil;
//import com.lnsoft.core.tool.utils.Func;
//import com.lnsoft.core.tool.utils.RedisUtil;
//import com.lnsoft.core.tool.utils.WebUtil;
//import com.lnsoft.core.tool.utils.crypto.sm2.SM2Key;
//import com.lnsoft.core.tool.utils.crypto.sm2.SM2Util;
//import com.lnsoft.core.tool.utils.crypto.sm4.SM4Util;
//import com.lnsoft.system.user.entity.UserInfo;
//import com.lnsoft.system.user.feign.IUserClient;
//import javax.servlet.http.HttpServletRequest;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Component;
//
///**
// * @author guoz on 2024/3/6
// */
//@Component
//@AllArgsConstructor
//public class NoPasswordTokenGranter implements ITokenGranter {
//
//	public static final String GRANT_TYPE = "no-password";
//
//	private IUserClient userClient;
//	private RedisUtil redisUtil;
//	private IdevelopTokenProperties idevelopTokenProperties;
//
//	@Override
//	public UserInfo grant(TokenParameter tokenParameter) {
//
//		HttpServletRequest request = WebUtil.getRequest();
//		String kid = request.getHeader(TokenUtil.IDEVELOP_KID);
//		String enCpuk = request.getHeader(TokenUtil.IDEVELOP_ENCPUK);
//		String tenantId = tokenParameter.getArgs().getStr("tenantId");
//		String account = tokenParameter.getArgs().getStr("account");
//		// 获取服务端私钥
//		SM2Key sm2Key = (SM2Key) redisUtil.get(CacheNames.SM2_KEY + kid);
//		if(sm2Key == null) {
//			throw new ServiceException("登录状态过期, 请重新刷新页面");
//		}
//		// 用服务端私钥解密，获取客户端公钥，并用于加密产生的uid、ak、iv
//		String cpuk = SM2Util.decrypt(enCpuk, sm2Key.getSprk());
//
//		UserInfo userInfo = null;
//		if (Func.isNoneBlank(account)) {
//			// 获取用户类型
//			String userType = tokenParameter.getArgs().getStr("userType");
//			R<UserInfo> result;
//			// 根据不同用户类型调用对应的接口返回数据，用户可自行拓展
//			if (userType.equals(IdevelopUserEnum.WEB.getName())) {
//				result = userClient.userInfo(tenantId, account);
//			} else if (userType.equals(IdevelopUserEnum.APP.getName())) {
//				result = userClient.userInfo(tenantId, account);
//			} else {
//				result = userClient.userInfo(tenantId, account);
//			}
//			userInfo = result.isSuccess() ? result.getData() : null;
//		}
//		if(userInfo != null) {
//			Key cryptoKey = new Key();
//			cryptoKey.setSm4Key(SM4Util.generateKey());
//			cryptoKey.setSm4Iv(SM4Util.generateIv());
//			cryptoKey.setClientPubKey(cpuk);
//			cryptoKey.setServerPriKey(sm2Key.getSprk());
//			cryptoKey.setServerPubKey(sm2Key.getSpuk());
//			userInfo.setCryptoKey(cryptoKey);
//		}
//		return userInfo;
//	}
//}
