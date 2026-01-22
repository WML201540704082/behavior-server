
package com.lnsoft.gateway.props;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限过滤
 *
 * @author guozhao
 */
@Data
@RefreshScope
@ConfigurationProperties("idevelop.secure")
public class AuthProperties {

	/**
	 * 放行API集合
	 */
	private final List<String> skipUrl = new ArrayList<>();

	/**
	 * IP白名单
	 */
	private String skipIp = "";

	/**
	 * 请求完整性校验
	 */
	private boolean reqIntegrity;

	/**
	 * 请求防重放校验
	 */
	private boolean replay;

	/**
	 * 请求解密
	 */
	private boolean enableDecryptRequestParam;

	/**
	 * 响应加密
	 */
	private boolean enableEncryptResponseParam;
	/**
	 * 跨站伪造请求校验
	 */
	private CsrfProperties csrf = new CsrfProperties();

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public class CsrfProperties{
		private boolean enabled;
		private List<String> refererUrl;
	}
}
