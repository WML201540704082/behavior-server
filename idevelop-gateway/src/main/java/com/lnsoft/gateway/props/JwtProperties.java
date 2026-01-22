
package com.lnsoft.gateway.props;

import io.jsonwebtoken.JwtException;
import lombok.Data;
import com.lnsoft.core.launch.constant.TokenConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JWT配置
 *
 * @author guozhao
 */
@Data
@ConfigurationProperties("idevelop.token")
public class JwtProperties {

	/**
	 * token是否有状态
	 */
	private Boolean state = Boolean.FALSE;

	/**
	 * 是否只可同时在线一人
	 */
	private Boolean single = Boolean.FALSE;

	/**
	 * token签名
	 */
	private String signKey = "";



	/**
	 * 获取签名规则
	 */
	public String getSignKey() {
		if (this.signKey.length() < TokenConstant.SIGN_KEY_LENGTH) {
			throw new JwtException("请配置 idevelop.token.sign-key 的值, 长度32位以上");
		}
		return this.signKey;
	}

}
