
package com.lnsoft.auth.controller;

import cn.hutool.core.util.IdUtil;
import com.google.common.collect.Maps;
import com.lnsoft.auth.domain.LoginParam;
import com.lnsoft.auth.granter.*;
import com.lnsoft.auth.utils.TokenUtil;
import com.lnsoft.common.cache.CacheNames;
import com.lnsoft.core.log.annotation.ApiLog;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.AuthInfo;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.support.Kv;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.core.tool.utils.RedisUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.core.tool.utils.WebUtil;
import com.lnsoft.core.tool.utils.crypto.sm2.SM2Key;
import com.lnsoft.core.tool.utils.crypto.sm2.SM2Util;
import com.lnsoft.system.user.entity.UserInfo;
import com.lnsoft.system.user.feign.IUserClient;

import com.wf.captcha.SpecCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 认证模块
 *
 * @author guozhao
 */
@Slf4j
@RestController
@Api(value = "用户授权认证", tags = "授权接口")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Resource
	private RedisUtil redisUtil;
//	private IIdentityService identityService = (IIdentityService) AdapterFactory.getIdentityService();
	@Resource
	private IUserClient userClient;

	@PostMapping("token")
	@ApiLog("系统登录接口")
	@ApiOperation(value = "获取认证token", notes = "传入租户ID:tenantId,账号:account,密码:password")
	public R<AuthInfo> token(@ApiParam(value = "授权类型", required = true) @RequestParam(defaultValue = "password", required = false) String grantType,
							 @ApiParam(value = "刷新令牌") @RequestParam(required = false) String refreshToken,
							 @ApiParam(value = "租户ID", required = true) @RequestParam(defaultValue = "000000", required = false) String tenantId,
							 @ApiParam(value = "账号") @RequestParam(required = false) String account,
							 @ApiParam(value = "密码") @RequestParam(required = false) String password) {

		HttpServletRequest request = WebUtil.getRequest();
		String key = request.getHeader(TokenUtil.CAPTCHA_HEADER_KEY);
		String code = request.getHeader(TokenUtil.CAPTCHA_HEADER_CODE);
		String userType = Func.toStr(request.getHeader(TokenUtil.USER_TYPE_HEADER_KEY), TokenUtil.DEFAULT_USER_TYPE);

		TokenParameter tokenParameter = new TokenParameter();
		tokenParameter.getArgs().set("tenantId", tenantId)
			.set("account", account)
			.set("password", password)
			.set("grantType", grantType)
			.set("refreshToken", refreshToken)
			.set("userType", userType)
			.set("iscCheck", Boolean.FALSE);

		// 检验验证码检验
		checkCaptcha(key, code);

		// 检验记录登录错误次数
		Object accountObj = redisUtil.get(CacheNames.CAPTCHA_USER + account);
		checkLoginNumber(accountObj);

		// ISC用户账号登录信息认证
		checkIscLogin(grantType, tenantId, account, password, tokenParameter);

		ITokenGranter granter = TokenGranterBuilder.getGranter(grantType);
		UserInfo userInfo = granter.grant(tokenParameter);

		if (userInfo == null || userInfo.getUser() == null || userInfo.getUser().getId() == null) {
			userErrorFound(account, accountObj);
			return R.fail(TokenUtil.USER_NOT_FOUND);
		}

		return R.data(TokenUtil.createAuthInfo(userInfo));
	}


	@PostMapping("token-igw")
	@ApiOperation(value = "获取认证token", notes = "传入租户ID:tenantId,账号:account,密码:password")
	public R<AuthInfo> token(@RequestBody LoginParam loginParam) {

		String userType = Func.toStr(WebUtil.getRequest().getHeader(TokenUtil.USER_TYPE_HEADER_KEY), TokenUtil.DEFAULT_USER_TYPE);

		TokenParameter tokenParameter = new TokenParameter();
		tokenParameter.getArgs().set("tenantId", loginParam.getTenantId())
			.set("account", loginParam.getAccount())
			.set("password", loginParam.getPassword())
			.set("grantType", loginParam.getGrantType())
			.set("refreshToken", loginParam.getRefreshToken())
			.set("userType", userType);

		ITokenGranter granter = TokenGranterBuilder.getGranter(loginParam.getGrantType());
		UserInfo userInfo = granter.grant(tokenParameter);

		if (userInfo == null || userInfo.getUser() == null || userInfo.getUser().getId() == null) {
			return R.fail(TokenUtil.USER_NOT_FOUND);
		}

		return R.data(TokenUtil.createAuthInfo(userInfo));
	}

//	@PostMapping("/ssoIscLogin")
//	@ApiOperation(value = "ISC-CAS单点登录票据验证接口", notes = "CAS票据:ticket")
//	public R<AuthInfo> ssoIscLogin(@ApiParam(value = "CAS票据", required = true) @RequestParam("ticket") String ticket,
//								   @ApiParam(value = "租户ID", required = true) @RequestParam(defaultValue = "000000", required = false) String tenantId) {
//		// 验证票据
//		ServiceTicketValidator sv = new ServiceTicketValidator();
//		ServiceResponse sr = new ServiceResponse();
//		User user = null;
//		try {
//			CommonUtil.casValidate(sv, sr, ticket);
//			if (sv.isAuthenticationSuccesful()) {
//				sr.read(sv.getResponse());
//				IscSSOUserBean iscSsoUserBean = null;
//				Map<String, Object> userMap = sr.getUserMap();
//				if (userMap != null) {
//					iscSsoUserBean = new IscSSOUserBean();
//					BeanUtils.populate(iscSsoUserBean, userMap);
//					String[] userId = {iscSsoUserBean.getIscUserId()};
//					List<User> userList = identityService.getUserByIds(userId);
//					if (null != userList.get(0)) {
//						user = userList.get(0);
//						// 账号
//						String iscUserName = user.getUserName();
//						log.info("ISC账号：{}", iscUserName);
//						String userType = Func.toStr(WebUtil.getRequest().getHeader(TokenUtil.USER_TYPE_HEADER_KEY), TokenUtil.DEFAULT_USER_TYPE);
//						String userName = user.getResExt().get("namecode").toString();
//						log.info("ISC账号【namecode】：{}", userName);
//
//						TokenParameter tokenParameter = new TokenParameter();
//						tokenParameter.getArgs().set("tenantId", tenantId)
//							.set("account", userName)
//							.set("grantType", "no-password")
//							.set("userType", userType);
//						ITokenGranter granter = TokenGranterBuilder.getGranter("no-password");
//						UserInfo userInfo = granter.grant(tokenParameter);
//						if (userInfo == null || userInfo.getUser() == null || userInfo.getUser().getId() == null) {
//							return R.fail(TokenUtil.USER_NOT_FOUND);
//						}
//						return R.data(TokenUtil.createAuthInfo(userInfo));
//					}
//				}
//			} else {
//				return R.fail("票据验证失败");
//			}
//		} catch (Exception e) {
//			log.error("票据验证失败：{}", e.getMessage(), e);
//			throw new ServiceException("票据验证失败");
//		}
//		return R.data(null);
//	}

	@GetMapping("/captcha")
	@ApiOperation(value = "获取验证码")
	public R<Kv> captcha() {
		SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
		String verCode = specCaptcha.text().toLowerCase();
		String key = UUID.randomUUID().toString();
		// 存入redis并设置过期时间为30分钟
		redisUtil.set(CacheNames.CAPTCHA_KEY + key, verCode, 30L, TimeUnit.MINUTES);
		// 将key和base64返回给前端
		return R.data(Kv.init().set("key", key).set("image", specCaptcha.toBase64()));
	}

	/**
	 * 用户首次访问系统所请求的接口
	 * 生成与用户会话相关的公私钥
	 *
	 * @return
	 */
	@GetMapping("/k")
	public R<Map<String, Object>> k() {
		// 创建公私钥并存入缓存，有效期与用户会话一致
		SM2Key sm2Key = SM2Util.createSm2Key();
		String kid = IdUtil.simpleUUID();
		redisUtil.set(CacheNames.SM2_KEY + kid, sm2Key, 1, TimeUnit.MINUTES);
		Map<String, Object> result = Maps.newHashMap();
		result.put(TokenUtil.IDEVELOP_KID, kid);
		result.put(TokenUtil.IDEVELOP_SPUK, sm2Key.getSpuk());
		return R.data(result);
	}

	/**
	 * 注销
	 */
	@PostMapping("logout")
	public R logout() {
		IdevelopUser idevelopUser = SecureUtil.getUser();
		if (idevelopUser != null) {
			SecureUtil.delLoginUser(idevelopUser.getAccount(), idevelopUser.getToken());
			// TODO 增加登录日志
			// LoginLogPublisher.publishEvent(loginUser.getSysUser().getUserName(), Constants.LOGOUT, "退出成功");
		}
		return R.success("退出成功");
	}


	/**
	 * ISC用户账号登录信息认证
	 *
	 * @param account
	 * @param password
	 * @return
	 */
	private void userLoginAuth(TokenParameter tokenParameter, String account, String password) {
		try {
			HttpServletRequest request = WebUtil.getRequest();
			String kid = request.getHeader(TokenUtil.IDEVELOP_KID);
			// 获取服务端私钥
			SM2Key sm2Key = (SM2Key) redisUtil.get(CacheNames.SM2_KEY + kid);
			if (sm2Key == null) {
				throw new ServiceException("登录状态过期, 请重新刷新页面");
			}
			String dePassword = SM2Util.decrypt(password, sm2Key.getSprk());
//			User user = identityService.userLoginAuth(account, dePassword);
//			if (ObjectUtils.isEmpty(user)) {
//				tokenParameter.getArgs().set("iscCheck", Boolean.FALSE);
//			}
			tokenParameter.getArgs().set("iscCheck", Boolean.TRUE);
		} catch (Exception e) {
			logger.error("ISC用户账号登录信息认证失败: {}", e.getMessage());
			tokenParameter.getArgs().set("iscCheck", Boolean.FALSE);
		}
	}


	/**
	 * 检验记录登录错误次数
	 *
	 * @param accountObj
	 */
	private void checkLoginNumber(Object accountObj) {
		if (ObjectUtils.isNotEmpty(accountObj)) {
			int accountInt = (int) accountObj;
			if (accountInt > 5) {
				throw new ServiceException(TokenUtil.USER_ERROR_FOUND);
			}
		}
	}

	/**
	 * 验证码检验
	 *
	 * @param key
	 * @param code
	 */
	private void checkCaptcha(String key, String code) {
		if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(code)) {
			// 获取验证码
			Object rediskey = redisUtil.get(CacheNames.CAPTCHA_KEY + key);
			// 删除验证码
			redisUtil.del(CacheNames.CAPTCHA_KEY + key);
			String redisCode = String.valueOf(rediskey);
			// 判断验证码
			if (!StringUtil.equalsIgnoreCase(redisCode, code)) {
				throw new ServiceException(TokenUtil.CAPTCHA_NOT_CORRECT);
			}
		}
	}

	/**
	 * 记录登录错误次数
	 *
	 * @param account
	 * @param accountObj
	 */
	private void userErrorFound(String account, Object accountObj) {
		if (ObjectUtils.isNotEmpty(accountObj)) {
			int accountInt = (int) accountObj;
			redisUtil.set(CacheNames.CAPTCHA_USER + account, accountInt + 1, 30L, TimeUnit.MINUTES);
		} else {
			redisUtil.set(CacheNames.CAPTCHA_USER + account, 1, 30L, TimeUnit.MINUTES);
		}
	}

	/**
	 * ISC用户账号登录信息认证
	 *
	 * @param grantType
	 * @param tenantId
	 * @param account
	 * @param password
	 * @param tokenParameter
	 */
	private void checkIscLogin(String grantType, String tenantId, String account, String password, TokenParameter tokenParameter) {
		if (StringUtils.equalsAny(grantType, CaptchaTokenGranter.GRANT_TYPE, PasswordTokenGranter.GRANT_TYPE)) {
			R<UserInfo> result = userClient.userInfo(tenantId, account);
			if (result.isSuccess()) {
				String userType1 = result.getData().getUser().getUserType();
				if (StringUtils.equals("2", userType1)) {
					userLoginAuth(tokenParameter, account, password);
				}
			}
		}
	}

}
