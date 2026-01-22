package com.lnsoft.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lnsoft.gateway.props.AuthProperties;
import com.lnsoft.gateway.provider.ResponseProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 跨站伪造请求校验
 * @author guozhao
 */
@Slf4j
// @Component
@AllArgsConstructor
public class CsrfFilter implements GlobalFilter, Ordered {

	private final AuthProperties authProperties;
	private final ObjectMapper objectMapper;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse resp = exchange.getResponse();
		ServerHttpRequest.Builder mutate = request.mutate();
		if (!authProperties.getCsrf().isEnabled()) {
			return chain.filter(exchange.mutate().request(mutate.build()).build());
		}
		String path = request.getURI().getPath();
		String rawPath = exchange.getAttributes().get("RAWPATH").toString();
		// 获取请求头中的referer和origin
		String origin = request.getHeaders().getFirst("origin");
		String referer = request.getHeaders().getFirst("referer");
		if (StrUtil.isNotBlank(referer) && !existReferer(referer)) {
			log.error("跨站伪造请求校验-referer校验失败:url{}\nreferer: {},", rawPath, referer);
			return unAuth(resp, "跨站伪造请求校验失败");
		}

		if (StrUtil.isNotBlank(origin) && !existReferer(origin)) {
			log.error("跨站伪造请求校验-origin校验失败:url{}\norigin: {},", rawPath, origin);
			return unAuth(resp, "跨站伪造请求校验失败");
		}
		return chain.filter(exchange.mutate().request(mutate.build()).build());
	}

	private boolean existReferer(String referer) {
		referer = getIpPortByUrl(referer);
		for (String item : authProperties.getCsrf().getRefererUrl()) {
			if (item.equals(referer)) {
				return true;
			}
		}
		return false;
	}

	public String getIpPortByUrl(String url) {
		//使用正则表达式过滤，
		String re = "((http|ftp|https)://)(([a-zA-Z0-9._-]+)|([0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}))(([a-zA-Z]{2,6})|(:[0-9]{1,5})?)";
		String str = "";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(re);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(url);

		if (matcher.matches()) {
			str = url;
		} else {
			String[] split2 = url.split(re);
			if (split2.length > 1) {
				String substring = url.substring(0, url.length() - split2[1].length());
				str = substring;
			} else {
				str = split2[0];
			}
		}
		return str;
	}

	private Mono<Void> unAuth(ServerHttpResponse resp, String msg) {
		resp.setStatusCode(HttpStatus.UNAUTHORIZED);
		resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
		String result = "";
		try {
			result = objectMapper.writeValueAsString(ResponseProvider.unCsrf(msg));
		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
		}
		DataBuffer buffer = resp.bufferFactory().wrap(result.getBytes(StandardCharsets.UTF_8));
		return resp.writeWith(Flux.just(buffer));
	}

	@Override
	public int getOrder() {
		return -500;
	}
}
