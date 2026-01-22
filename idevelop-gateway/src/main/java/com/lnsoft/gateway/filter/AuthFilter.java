
package com.lnsoft.gateway.filter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lnsoft.core.launch.constant.AppConstant;
import com.lnsoft.core.launch.props.IdevelopProperties;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.gateway.props.AuthProperties;
import com.lnsoft.gateway.provider.AuthProvider;
import com.lnsoft.gateway.provider.ResponseProvider;
import com.lnsoft.gateway.utils.*;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 鉴权认证
 *
 * @author guozhao
 */
@Slf4j
@Component
@AllArgsConstructor
public class AuthFilter implements WebFilter, GlobalFilter, Ordered {
	private final AuthProperties authProperties;
	private final ObjectMapper objectMapper;
	private final IdevelopProperties idevelopProperties;
	private final AntPathMatcher antPathMatcher = new AntPathMatcher();
	private final RedisUtil redisUtil;

	// 需要鉴权的swagger路径
	private static final String[] CHECK_PATH = new String[]{
		"/swagger-ui.html",
		"/swagger-resources/**",
		"/v2/api-docs/**",
		"/actuator/**"
	};




	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		ServerHttpRequest request = exchange.getRequest();
		ServerHttpRequest.Builder mutate = request.mutate();
		String path = request.getURI().getPath();
		String rawPath = exchange.getAttributes().get("RAWPATH").toString();
		// 增加对服务名的匹配机制
		if(StringUtils.isNotBlank(rawPath) && isSkip(rawPath)) {
			return chain.filter(exchange);
		}
		if (isSkip(path)) {
			return chain.filter(exchange);
		}
		ServerHttpResponse resp = exchange.getResponse();
		String headerToken = request.getHeaders().getFirst(AuthProvider.AUTH_KEY);
		String paramToken = request.getQueryParams().getFirst(AuthProvider.AUTH_KEY);
		if (StringUtils.isBlank(headerToken) && StringUtils.isBlank(paramToken)) {
			return unAuth(resp, "令牌不能为空");
		}


		String auth = StringUtils.isBlank(headerToken) ? paramToken : headerToken;
		String token = JwtUtil.getToken(auth);
		//校验 加密Token 合法性
		if (JwtUtil.isCrypto(auth)) {
			//token = JwtCrypto.decryptToString(token, idevelopProperties.getEnvironment().getProperty(IDEVELOP_CRYPTO_AES_KEY));
			IdevelopUser user = JwtUtil.getUser(request);
			token = SM4Util.decryptCBC(token, user.getKey().getSm4Key(), user.getKey().getSm4Iv());

		}
		Claims claims = JwtUtil.parseJWT(token);
		if (claims == null) {
			return unAuth(resp, "令牌已过期或者验证不正确");
		}
		// 校验用户是否登录
		boolean isLogin = redisUtil.hasKey(JwtUtil.getTokenKey(claims));
		if (!isLogin) {
			return unAuth(resp, "登录状态已过期");
		}

		String userKey = (String) claims.get(JwtUtil.LOGIN_USER_KEY);
		String account = (String) claims.get(JwtUtil.LOGIN_USERNAME_KEY);
		if(StringUtils.isEmpty(userKey) || StringUtils.isEmpty(account)) {
			return unAuth(resp, "令牌验证失败");
		}

		// 设置用户信息到请求
		addHeader(mutate, JwtUtil.LOGIN_USER_KEY, userKey);
		addHeader(mutate, JwtUtil.LOGIN_USERNAME_KEY, account);
		return chain.filter(exchange.mutate().request(mutate.build()).build());
	}

	private void addHeader(ServerHttpRequest.Builder mutate, String name, Object value) {
		if(value == null) {
			return;
		}
		String valueStr = value.toString();
		String valueEncode = ServletUtil.urlEncode(valueStr);
		mutate.header(name, valueEncode);
	}



	private boolean isSkip(String path) {
		return AuthProvider.getDefaultSkipUrl().stream().anyMatch(pattern -> antPathMatcher.match(pattern, path))
			|| authProperties.getSkipUrl().stream().anyMatch(pattern -> antPathMatcher.match(pattern, path));
	}

	private Mono<Void> unAuth(ServerHttpResponse resp, String msg) {
		resp.setStatusCode(HttpStatus.UNAUTHORIZED);
		resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
		String result = "";
		try {
			result = objectMapper.writeValueAsString(ResponseProvider.unAuth(msg));
		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
		}
		DataBuffer buffer = resp.bufferFactory().wrap(result.getBytes(StandardCharsets.UTF_8));
		return resp.writeWith(Flux.just(buffer));
	}

	@Override
	public int getOrder() {
		return -900;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange ctx, WebFilterChain chain) {
		ServerHttpRequest request = ctx.getRequest();
		ServerHttpResponse resp = ctx.getResponse();
		String url = request.getURI().getPath();
		String clientIp = IpUtil.getIP(request);
		PathMatcher pathMatcher = new AntPathMatcher();
		// PROD 生产环境默认为 * 不限制IP，但禁止访问CHECK_PATH中的路径
		if(AppConstant.PROD_CODE.equals(idevelopProperties.getEnv())) {
			// 为空默认为 “*” 任何访问
			if(StringUtils.isBlank(authProperties.getSkipIp()) || "*".equals(authProperties.getSkipIp())){
				// 如果访问的敏感路径不允许
				for (String path : CHECK_PATH) {
					if (pathMatcher.match(path, url)) {
						log.info("Access denied client-ip：{} to url:{}", clientIp, url);
						return unAuth(resp, "缺失令牌,鉴权失败");
					}
				}
				return chain.filter(ctx);
			}else {
				for (String path : CHECK_PATH) {
					if (pathMatcher.match(path, url)) {
						// 匹配成功判断请求ip是否在白名单
						if(ArrayUtils.contains(authProperties.getSkipIp().split(","), clientIp)){
							return chain.filter(ctx);
						}else {
							log.info("Access denied client-ip：{} to url:{}", clientIp, url);
							return unAuth(resp, "缺失令牌,鉴权失败");
						}
					}
				}
			}
		}
		if(AppConstant.DEV_CODE.equals(idevelopProperties.getEnv()) || AppConstant.TEST_CODE.equals(idevelopProperties.getEnv())) {
			for (String path : CHECK_PATH) {
				if (pathMatcher.match(path, url)) {
					// 匹配成功判断请求ip是否在白名单
					if(ArrayUtils.contains(authProperties.getSkipIp().split(","), clientIp)){
						return chain.filter(ctx);
					}else {
						log.info("Access denied client-ip：{} to url:{}", clientIp, url);
						return unAuth(resp, "缺失令牌,鉴权失败");
					}
				}
			}
		}
		for (String path : CHECK_PATH) {
			if (pathMatcher.match(path, url)) {
				log.info("Access denied client-ip：{} to url:{}", clientIp, url);
				return unAuth(resp, "缺失令牌,鉴权失败");
			}
		}
		return chain.filter(ctx);
	}
}
