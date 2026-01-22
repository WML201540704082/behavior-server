
package com.lnsoft.gateway.utils;

import com.lnsoft.core.launch.constant.TokenConstant;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.gateway.props.JwtProperties;
import com.lnsoft.gateway.provider.AuthProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Locale;

/**
 * JwtUtil
 *
 * @author guozhao
 */
public class JwtUtil {


	public static String BEARER = TokenConstant.BEARER;
	public static String CRYPTO = TokenConstant.CRYPTO;
	public static Integer AUTH_LENGTH = 7;
	/**
	 * 登录用户 redis key
	 */
	public static String LOGIN_TOKEN_KEY = "idevelop:login_tokens:";

	/**
	 * 令牌前缀
	 */
	public static String LOGIN_USER_KEY = "login_user_key";

	/**
	 * 令牌前缀
	 */
	public static String LOGIN_USERNAME_KEY = "login_username_key";

	/**
	 * jwt配置
	 */
	@Getter
	private static JwtProperties jwtProperties;


	public static void setJwtProperties(JwtProperties properties) {
		if (JwtUtil.jwtProperties == null) {
			JwtUtil.jwtProperties = properties;
		}
	}


	@Getter
	private static RedisUtil redisUtil;

	public static void setRedisUtil(RedisUtil redisUtil) {
		if (JwtUtil.redisUtil == null) {
			JwtUtil.redisUtil = redisUtil;
		}
	}


	/**
	 * 签名加密
	 */
	public static String getBase64Security() {
		return Base64.getEncoder().encodeToString(getJwtProperties().getSignKey().getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * 获取请求传递的token串
	 *
	 * @param auth token
	 * @return String
	 */
	public static String getToken(String auth) {
		if (isBearer(auth) || isCrypto(auth)) {
			return auth.substring(AUTH_LENGTH);
		}
		return null;
	}

	/**
	 * 判断token类型为bearer
	 *
	 * @param auth token
	 * @return String
	 */
	public static Boolean isBearer(String auth) {
		Locale.setDefault(Locale.ENGLISH);
		if ((auth != null) && (auth.length() > AUTH_LENGTH)) {
			String headStr = auth.substring(0, 6).toLowerCase();
			return headStr.compareTo(BEARER) == 0;
		}
		return false;
	}

	/**
	 * 判断token类型为crypto
	 *
	 * @param auth token
	 * @return String
	 */
	public static Boolean isCrypto(String auth) {
		Locale.setDefault(Locale.ENGLISH);
		if ((auth != null) && (auth.length() > AUTH_LENGTH)) {
			String headStr = auth.substring(0, 6).toLowerCase();
			return headStr.compareTo(CRYPTO) == 0;
		}
		return false;
	}

	/**
	 * 解析jsonWebToken
	 *
	 * @param jsonWebToken token串
	 * @return Claims
	 */
	public static Claims parseJWT(String jsonWebToken) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(Base64.getDecoder().decode(getBase64Security())).build()
				.parseClaimsJws(jsonWebToken).getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		} catch (Exception ex) {
			return null;
		}
	}


	public static String getTokenKey(Claims claims) {
		String token = (String) claims.get(LOGIN_USER_KEY);
		String account = (String) claims.get(LOGIN_USERNAME_KEY);
		// 上线要求 同一用户只允许登录一个
		// Constants.LOGIN_TOKEN_KEY + username + "@" + uuid;
		if (jwtProperties.getSingle()) {
			return LOGIN_TOKEN_KEY + account;
		}
		return LOGIN_TOKEN_KEY + account + "@" + token;
	}

	public static IdevelopUser getUser(Claims claims) {
		// 优先从request中获取

		String userKey = getTokenKey(claims);
		// 获取登录用户信息
		try {
			return (IdevelopUser) redisUtil.get(userKey);
		} catch (Exception e) {
			return null;
		}
	}

	public static IdevelopUser getUser(ServerHttpRequest request) {
		String headerToken = request.getHeaders().getFirst(AuthProvider.AUTH_KEY);
		String paramToken = request.getQueryParams().getFirst(AuthProvider.AUTH_KEY);
		String auth = StringUtils.isBlank(headerToken) ? paramToken : headerToken;
		String token = JwtUtil.getToken(auth);
		Claims claims = JwtUtil.parseJWT(token);
		return getUser(claims);
	}
}
