package com.lnsoft.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.gateway.props.AuthProperties;
import com.lnsoft.gateway.utils.JwtUtil;
import com.lnsoft.gateway.utils.SM4Util;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;


/**
 * 请求参数解密与响应加密
 * 需配合前端
 *
 * @author guozhao
 */
@Slf4j
// @Component
@AllArgsConstructor
public class ApiCryptoFilter implements GlobalFilter, Ordered {


	private final AuthProperties authProperties;
	private static final List<HttpMethod> NEED_FILTER_METHOD_LIST = Arrays.asList(HttpMethod.GET, HttpMethod.POST);
	private static final List<MediaType> NEED_FILTER_MEDIA_TYPE_LIST = Arrays.asList(MediaType.APPLICATION_JSON);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();
		ServerHttpRequest.Builder mutate = request.mutate();
		HttpHeaders headers = request.getHeaders();
		HttpMethod method = request.getMethod();
		boolean isExport = Boolean.parseBoolean(request.getHeaders().getFirst("isExport"));
		boolean isImport = Boolean.parseBoolean(request.getHeaders().getFirst("isImport"));
		// 满足条件，进行过滤
		if (isNeedFilterMethod(method) && isNeedFilterContentType(headers.getContentType()) && !isExport && !isImport) {
			return DataBufferUtils.join(request.getBody())
				.flatMap(dataBuffer -> {
					try {
						// 获取请求参数
						String originaRequestBody = getOriginalRequestBody(dataBuffer);
						// 解密请求参数
						String decrypted = decryptRequest(originaRequestBody, request);
						// 装饰新的请求体
						ServerHttpRequestDecorator serverHttpRequestDecorator = serverHttpRequestDecorator(request, decrypted);
						// 装饰新的响应体
						ServerHttpResponseDecorator serverHttpResponseDecorator = serverHttpResponseDecorator(request, response);
						// 使用新的请求体和响应体转发
						ServerWebExchange serverWebExchange = exchange.mutate().request(serverHttpRequestDecorator).response(serverHttpResponseDecorator).build();
						return chain.filter(serverWebExchange);
					} catch (Exception e) {
						log.error("密文过滤器加解密异常", e);
						return Mono.empty();
					} finally {
						DataBufferUtils.release(dataBuffer);
					}
				});

		}
		return chain.filter(exchange);
	}


	/**
	 * 获取原始的请求参数
	 *
	 * @param dataBuffer
	 * @return
	 */
	private String getOriginalRequestBody(DataBuffer dataBuffer) {
		byte[] bytes = new byte[dataBuffer.readableByteCount()];
		dataBuffer.read(bytes);
		return new String(bytes, StandardCharsets.UTF_8);
	}

	private ServerHttpRequestDecorator serverHttpRequestDecorator(ServerHttpRequest originalRequest, String decrypteRequestBody) {
		return new ServerHttpRequestDecorator(originalRequest) {
			@Override
			public HttpHeaders getHeaders() {
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.putAll(super.getHeaders());
				httpHeaders.remove(HttpHeaders.CONTENT_LENGTH);
				httpHeaders.setContentLength(decrypteRequestBody.getBytes().length);
				httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
				return httpHeaders;
			}

			@Override
			public Flux<DataBuffer> getBody() {
				byte[] bytes = decrypteRequestBody.getBytes(StandardCharsets.UTF_8);
				return Flux.just(new DefaultDataBufferFactory().wrap(bytes));
			}
		};
	}

	private ServerHttpResponseDecorator serverHttpResponseDecorator(ServerHttpRequest originalRequest, ServerHttpResponse originalResponse) {
		DataBufferFactory dataBufferFactory = originalResponse.bufferFactory();
		return new ServerHttpResponseDecorator(originalResponse) {
			@Override
			public HttpHeaders getHeaders() {
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.addAll(originalResponse.getHeaders());
				return httpHeaders;
			}

			@Override
			public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
				if(body instanceof Flux) {
					Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
					return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
						DataBuffer join = dataBufferFactory.join(dataBuffers);
						byte[] byteArray = new byte[join.readableByteCount()];
						join.read(byteArray);
						DataBufferUtils.release(join);
						String originalResponseBody = new String(byteArray, StandardCharsets.UTF_8);
						// 加密
						String encrypted = encryptResponse(originalResponseBody, originalRequest);
						byte[] encryptedByteArray = encrypted.getBytes(StandardCharsets.UTF_8);
						originalResponse.getHeaders().setContentLength(encryptedByteArray.length);
						originalResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
						return dataBufferFactory.wrap(encryptedByteArray);
					}));
				}
				return super.writeWith(body);
			}

			@Override
			public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
				return writeWith(Flux.from(body).flatMapSequential(p -> p));

			}
		};
	}

	private String decryptRequest(String originaRequestBody, ServerHttpRequest request) {
		if (!authProperties.isEnableDecryptRequestParam()) {
			return originaRequestBody;
		}
		IdevelopUser user = JwtUtil.getUser(request);
		log.info("请求参数解密,原文:{}", originaRequestBody);
		JSONObject jsonObject = JSON.parseObject(originaRequestBody);
		String decrypted = SM4Util.decryptCBC(jsonObject.getString("encrypted"), user.getKey().getSm4Key(), user.getKey().getSm4Iv());
		log.info("请求参数解密,明文:{}", decrypted);
		return decrypted;
	}

	private String encryptResponse(String originaRequestBody, ServerHttpRequest request) {
		if (!authProperties.isEnableEncryptResponseParam()) {
			return originaRequestBody;
		}
		IdevelopUser user = JwtUtil.getUser(request);
		log.info("响应结果加密,原文:{}", originaRequestBody);
		String encrypted = SM4Util.encryptCBC(originaRequestBody, user.getKey().getSm4Key(), user.getKey().getSm4Iv());
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("encrypted", encrypted);
		log.info("响应结果加密,密文:{}", encrypted);
		return jsonObject.toJSONString();
	}


	private boolean isNeedFilterMethod(HttpMethod method) {
		return NEED_FILTER_METHOD_LIST.contains(method);
	}

	private boolean isNeedFilterContentType(MediaType mediaType) {
		if (mediaType == null) {
			return false;
		}
		return NEED_FILTER_MEDIA_TYPE_LIST.contains(mediaType) || "json".equals(mediaType.getSubtype());
	}


	@Override
	public int getOrder() {
		return -800;
	}
}
