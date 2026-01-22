package com.lnsoft.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.gateway.props.AuthProperties;
import com.lnsoft.gateway.provider.AuthProvider;
import com.lnsoft.gateway.provider.ResponseProvider;
import com.lnsoft.gateway.utils.JwtUtil;
import com.lnsoft.gateway.utils.RedisUtil;
import com.lnsoft.gateway.utils.RequestContextUtil;
import com.lnsoft.gateway.utils.SM3Util;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 请求完整性校验过滤器
 * 使用sm3国密哈希 SM3Util.sm3Hash(param + ts + nonce + uid)后的值同sign做对比如果一致则通过，否则不通过
 * 需通过3次校验参数 1、时间浮动在4s内 2、随机数在30分钟内不能重复 3、整体参数做md5签名
 * 1和2是防止重放攻击和多次提交 3是防止数据篡改
 * GET 前后约定按字典key升序排序，否则会前后参数顺序不一致
 * POST 强制规定使用json提交表单。
 *
 * @author guozhao
 */
@Slf4j
// @Component
@AllArgsConstructor
public class ReqIntegrityFilter implements GlobalFilter, Ordered {


	private final ObjectMapper objectMapper;
	private final AuthProperties authProperties;
	private final AntPathMatcher antPathMatcher = new AntPathMatcher();
	private final RedisUtil redisUtil;

	private static final Long REQ_LIMITS = -60L;
	public static final String NONCE_CODE_KEY = "idevelop:nonceKey_codes:";
	/**
	 * nonce_client有效期（分钟）
	 */
	public static final Integer NONCE_EXPIRATION = 1;
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse resp = exchange.getResponse();
		ServerHttpRequest.Builder mutate = request.mutate();

		if(!authProperties.isReqIntegrity()) {
			return chain.filter(exchange.mutate().request(mutate.build()).build());
		}

		String path = request.getURI().getPath();
		String rawPath = exchange.getAttributes().get("RAWPATH").toString();
		// 增加对服务名的匹配机制
		if (StringUtils.isNotBlank(rawPath) && isSkip(rawPath)) {
			return chain.filter(exchange);
		}
		if (isSkip(path)) {
			return chain.filter(exchange);
		}
		HttpHeaders headers = request.getHeaders();
		MediaType contentType = headers.getContentType();
		// 上传请求不处理
//		if (contentType != null && MediaType.MULTIPART_FORM_DATA.getType().equals(contentType.getType())) {
//			return chain.filter(exchange);
//		}
		RequestParamContext context = new RequestParamContext();
		context.setMethod(request.getMethodValue());
		context.setPath(request.getPath().value());
		//仅获取 uri 上 query param
		//getQueryParams:
		//1.get 请求url参数
		//2.post 请求url上带的参数
		//3.获取不了body 请求参数
		Map<String, String> queryParams = request.getQueryParams().toSingleValueMap();
		// 对参数按key升序排序
		context.setQueryParams(sortMapByKey(queryParams));
		if (contentType != null) {
			//如果仅仅只有地址栏上有参数，则contentType为空
			context.setContentType(MediaType.toString(Arrays.asList(contentType)));
		}

		// application/json
		if (context.getMethod().equals(HttpMethod.POST.name()) && headers.getContentLength() > 0) {
			context.setBody(RequestContextUtil.getRequestBodyFormRequest(request));
		}
		String headerToken = request.getHeaders().getFirst(AuthProvider.AUTH_KEY);
		String paramToken = request.getQueryParams().getFirst(AuthProvider.AUTH_KEY);
		String auth = StringUtils.isBlank(headerToken) ? paramToken : headerToken;
		String token = JwtUtil.getToken(auth);
		Claims claims = JwtUtil.parseJWT(token);

		IdevelopUser user = JwtUtil.getUser(claims);
		String nonce = request.getHeaders().getFirst("nonce");
		String ts = request.getHeaders().getFirst("ts");
		String sign = request.getHeaders().getFirst("sign");
		String uid = user.getUid();

		if (StrUtil.isAllEmpty(nonce, ts, sign)) {
			log.error("完整性校验-请求参数缺失:url{}\nnonce: {}\nts: {}\nsign: {},", rawPath, nonce, ts, sign);
			return unAuth(resp, "请求完整性校验失败");
		}

		// 先校验请求时间，控制请求和服务时间不超过xxx时间
		long currentTimeStamp = System.currentTimeMillis() / 1000L;
		long limit = currentTimeStamp - Long.parseLong(ts) / 1000L;
		//考虑客户端与服务器时间、以及网络问题。总体上下浮动4s时间 改为浮动3分钟
		if (limit < REQ_LIMITS || limit > Math.abs(REQ_LIMITS)) {
			log.error("完整性校验-客户端请求时间和服务器处理时间相差:url:{}\n limit: {} ", rawPath, limit);
			return unAuth(resp, "请求完整性校验失败");
		}

		// 验证随机数
		String nonceKey = NONCE_CODE_KEY + nonce;
		if (redisUtil.hasKey(nonceKey)) {
			log.error("完整性校验-重复随机数:url:{}\n nonce:{} ", rawPath, nonceKey);
			return unAuth(resp, "请求完整性校验失败");
		} else {
			// 存入随机数，有效时间默认1分钟
			redisUtil.set(nonceKey, nonce, NONCE_EXPIRATION, TimeUnit.MINUTES);
		}
		String params = "";
		if (context.getMethod().equals(HttpMethod.POST.name()) && headers.getContentLength() > 0) {
			params = context.getBody();
		}else {
			params = JSONObject.toJSONString(context.getQueryParams());
		}
		// 验证签名
		String fullStr = ("{}".equals(params) ? "" : params) + ts + nonce + uid;
		String resign = SM3Util.sm3Hash(fullStr);
		if (!resign.equals(sign)) {
			log.info("完整性校验-请求签名不一致:url:{}\nsign: {}\nresign:{}", rawPath, sign, resign);
			return unAuth(resp, "请求完整性校验失败");
		}
		return chain.filter(exchange.mutate().request(mutate.build()).build());
	}

	public Map<String, String> sortMapByKey(Map<String, String> map) {
		LinkedHashMap<String, String> sortMap = new LinkedHashMap<>();
		// 开始排序
		List<String> keyList = new ArrayList<>();
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			keyList.add(it.next());
		}
		Collections.sort(keyList);

		Iterator<String> itList = keyList.iterator();

		while (itList.hasNext()) {
			String key = itList.next();
			sortMap.put(key, map.get(key));
		}
		return sortMap;
	}

	public Mono<Void> cacheBody(ServerWebExchange exchange, GatewayFilterChain chain, RequestParamContext context) {
		return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(dataBuffer -> {
			//引用+1
			DataBufferUtils.retain(dataBuffer);
			DataBuffer tmpDataBuffer = dataBuffer.slice(0, dataBuffer.readableByteCount());
			byte[] bytes = new byte[tmpDataBuffer.readableByteCount()];
			tmpDataBuffer.read(bytes);
			// 将复制出来的tmpDataBuffer的计数器减少2，这个的同时也会将原始ataBuffer减少2
			String body = new String(bytes, StandardCharsets.UTF_8);
			DataBufferUtils.release(tmpDataBuffer);
			context.setBody(body);
			return chain.filter(exchange.mutate().request(decorateRequest(exchange, dataBuffer)).build());
		});
	}

	private ServerHttpRequest decorateRequest(ServerWebExchange exchange, DataBuffer dataBuffer) {
		return new ServerHttpRequestDecorator(exchange.getRequest()) {
			@Override
			public Flux<DataBuffer> getBody() {
				return Flux.just(dataBuffer);
			}
		};
	}


	@Override
	public int getOrder() {
		return -600;
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
			result = objectMapper.writeValueAsString(ResponseProvider.unReqIntergrity(msg));
		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
		}
		DataBuffer buffer = resp.bufferFactory().wrap(result.getBytes(StandardCharsets.UTF_8));
		return resp.writeWith(Flux.just(buffer));
	}
}
