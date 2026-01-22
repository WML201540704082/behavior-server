package com.lnsoft.gateway.filter;

import lombok.Data;

import java.util.Map;

/**
 * 解析请求参数的上下文
 * @author guozhao
 */
@Data
public class RequestParamContext {
	/**
	 * url 上的请求参数,不包含 请求 body 数据
	 */
	private Map<String, String> queryParams;
	/**
	 * 请求方法
	 */
	private String method;
	/**
	 * 请求类型
	 */
	private String contentType;

	/**
	 * body 参数
	 *
	 * 1.json:{a:b,c:d}
	 * 2.xxx-form: a=b&c=d
	 *
	 */
	private String body;

	/**
	 * 请求路径，除queryParams
	 */
	private String path;
}
