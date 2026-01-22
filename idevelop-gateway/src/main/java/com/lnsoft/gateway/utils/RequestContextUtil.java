package com.lnsoft.gateway.utils;

import com.alibaba.fastjson.JSON;
import com.lnsoft.gateway.filter.ReqIntegrityFilter;
import com.lnsoft.gateway.filter.RequestParamContext;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class RequestContextUtil {


	/**
	 * 从requestBody中取出json或者form-data数据
	 */
	public static String getRequestBodyFormRequest(ServerHttpRequest request) {
		Flux<DataBuffer> body = request.getBody();
		StringBuilder sb = new StringBuilder();
		body.subscribe(buffer -> {
			byte[] bytes = new byte[buffer.readableByteCount()];
			buffer.read(bytes);
			String bodyString = new String(bytes, StandardCharsets.UTF_8);
			sb.append(bodyString);
		});
		if (MediaType.MULTIPART_FORM_DATA.getType().equals(request.getHeaders().getContentType().getType())) {
			return handleFormData(sb.toString(), request);
		}
		return sb.toString();
	}

	/**
	 * 解析从requestBody中获取的form-data数据，转换为json格式
	 */
	private static String handleFormData(String str, ServerHttpRequest request) {
		String contentType = request.getHeaders().getContentType().toString();
		String sep = "--" + contentType.replace("multipart/form-data;boundary=", "");
		String[] strs = str.split("\r\n");
		boolean bankRow = false;// 空行
		boolean keyRow = true;// name=xxx行
		boolean append = false;// 内容是否拼接换行符
		Map<String, String> params = new LinkedHashMap<>();// 这里保证接收顺序，所以用linkedhashmap
		String s = null, key = null;
		StringBuffer sb = new StringBuffer();
		for (int i = 1, len = strs.length - 1; i < len; i++) {
			s = strs[i];
			if (keyRow) {
				key = s.replace("Content-Disposition: form-data; name=", "");
				key = key.substring(1, key.length() - 1);
				keyRow = false;
				bankRow = true;
				sb = new StringBuffer();
				append = false;
				continue;
			}
			if (sep.equals(s)) {
				keyRow = true;
				if (null != key && !key.contains("file")) {
					params.put(key, sb.toString());
				}
				append = false;
				continue;
			}
			if (bankRow) {
				bankRow = false;
				append = false;
				continue;
			}
			if (append) {
				sb.append("\r\n");
			}
			sb.append(s);
			append = true;
		}
		if (null != key && !key.contains("file")) {
			params.put(key, sb.toString());
		}
		return JSON.toJSONString(sortMapByKey(params));// 这里是alibaba的fastjson，需要引入依赖
	}
	public static Map<String, String> sortMapByKey(Map<String, String> map) {
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


}
