
package com.lnsoft.auth.utils;

import com.lnsoft.core.launch.constant.TokenConstant;
import com.lnsoft.core.secure.AuthInfo;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.TokenInfo;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.core.tool.utils.crypto.sm2.SM2Util;
import com.lnsoft.system.user.entity.User;
import com.lnsoft.system.user.entity.UserInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证工具类
 *
 * @author guozhao
 */
public class TokenUtil {

	public final static String CAPTCHA_HEADER_KEY = "Captcha-Key";
	public final static String CAPTCHA_HEADER_CODE = "Captcha-Code";
	public final static String IDEVELOP_KID = "idevelop_kid";
	public final static String IDEVELOP_ENCPUK = "idevelop_enCpuk";
	public final static String IDEVELOP_SPUK = "idevelop_spuk";
	public final static String CAPTCHA_NOT_CORRECT = "验证码不正确";
	public final static String TENANT_HEADER_KEY = "Tenant-Id";
	public final static String DEFAULT_TENANT_ID = "000000";
	public final static String USER_TYPE_HEADER_KEY = "User-Type";
	public final static String DEFAULT_USER_TYPE = "web";
	public final static String USER_NOT_FOUND = "用户认证信息错误，请重新输入";
	public final static String USER_ERROR_FOUND = "登录次数超过5次，账号锁定30分钟";
	public final static String HEADER_KEY = "Authorization";
	public final static String HEADER_PREFIX = "Basic ";


	/**
	 * 创建认证token
	 *
	 * @param userInfo 用户信息
	 * @return token
	 */
	public static AuthInfo createAuthInfo(UserInfo userInfo) {
		User user = userInfo.getUser();
		//设置jwt参数
		Map<String, Object> ext = new HashMap<>(16);
		IdevelopUser idevelopUser = new IdevelopUser();
		idevelopUser.setToken(TokenConstant.ACCESS_TOKEN);
		idevelopUser.setTenantId(user.getTenantId());
		idevelopUser.setUserId(Func.toLong(user.getId()));
		idevelopUser.setRoleId(user.getRoleId());
		idevelopUser.setDeptId(user.getDeptId());
		idevelopUser.setDeptName(user.getDeptName());
		idevelopUser.setCorpId(user.getCorpId());
		idevelopUser.setCorpName(user.getCorpName());
		idevelopUser.setAccount(user.getAccount());
		idevelopUser.setAvatar(user.getAvatar());
		idevelopUser.setRealName(user.getRealName());
		idevelopUser.setRoleName(Func.join(userInfo.getRoleName()));
		idevelopUser.setRoleCode(Func.join(userInfo.getRoles()));
		idevelopUser.setRegionCode(user.getRegionCode());
		idevelopUser.setRegionName(user.getRegionName());
		idevelopUser.setRegionFullName(user.getRegionFullName().replace("/", "_"));
		idevelopUser.setUserType(user.getUserType());
		idevelopUser.setErpDept(user.getErpDept());
		idevelopUser.setErpDeptCode(user.getErpDeptCode());
		idevelopUser.setErpUnit(user.getErpUnit());
		idevelopUser.setErpUnitCode(user.getErpUnitCode());
		idevelopUser.setI6000Dept(user.getI6000Dept());
		idevelopUser.setI6000DeptCode(user.getI6000DeptCode());
		idevelopUser.setI6000Unit(user.getI6000Unit());
		idevelopUser.setI6000UnitCode(user.getI6000UnitCode());
		idevelopUser.setKey(userInfo.getCryptoKey());
		idevelopUser.setUserName(user.getName());
		ext.put("tag", user.getTag());
		ext.put("idcard", user.getIdcard());
		ext.put("phone", user.getPhone());
		ext.put("groupId", user.getGroupId());
		ext.put("groupName", user.getGroupName());
		ext.put("corpFullName", user.getCorpFullName());
		ext.put("isLogin", user.getIsLogin());
		ext.put("permissions", userInfo.getPermissions());
		idevelopUser.setExt(ext);

		TokenInfo accessToken = SecureUtil.createAccessToken(idevelopUser);
		AuthInfo authInfo = new AuthInfo();
		authInfo.setUserId(user.getId());
		authInfo.setTenantId(user.getTenantId());
		authInfo.setOauthId(userInfo.getOauthId());
		authInfo.setAccount(user.getAccount());
		authInfo.setAvatar(user.getAvatar());
		authInfo.setUserName(user.getRealName());
		authInfo.setAuthority(Func.join(userInfo.getRoles()));
		authInfo.setAccessToken(accessToken.getToken());
		authInfo.setExpiresIn(accessToken.getExpire());
		authInfo.setRefreshToken(createRefreshToken(userInfo).getToken());
		authInfo.setTokenType(TokenConstant.BEARER);
		authInfo.setLicense(TokenConstant.LICENSE_NAME);
		// 自定义扩展属性
		Map<String, Object> extparam = new HashMap<>();
		// 设置登录时间
		extparam.put("loginTime", idevelopUser.getLoginTime());
		// 给客户端交换SM4秘钥、UID
		if(userInfo.getCryptoKey() != null) {
			extparam.put("k", SM2Util.encrypt(userInfo.getCryptoKey().getSm4Key(), userInfo.getCryptoKey().getClientPubKey()));
			extparam.put("i", SM2Util.encrypt(userInfo.getCryptoKey().getSm4Iv(), userInfo.getCryptoKey().getClientPubKey()));
			extparam.put("uid", SM2Util.encrypt(idevelopUser.getUid(), userInfo.getCryptoKey().getClientPubKey()));
			authInfo.setExt(extparam);
		}
		return authInfo;
	}

	/**
	 * 创建refreshToken
	 *
	 * @param userInfo 用户信息
	 * @return refreshToken
	 */
	private static TokenInfo createRefreshToken(UserInfo userInfo) {
		User user = userInfo.getUser();
		Map<String, String> param = new HashMap<>(16);
		param.put(TokenConstant.TOKEN_TYPE, TokenConstant.REFRESH_TOKEN);
		param.put(TokenConstant.USER_ID, Func.toStr(user.getId()));
		return SecureUtil.createJWT(param, "audience", "issuser", TokenConstant.REFRESH_TOKEN);
	}

}
