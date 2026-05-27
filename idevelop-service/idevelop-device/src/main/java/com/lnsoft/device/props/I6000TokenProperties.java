package com.lnsoft.device.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: xueli
 * @CreateTime: 2024/1/22 14:49
 * @Description: 映射配置文件中api实体
 */
@Configuration
@ConfigurationProperties(prefix = "i6000")
@Data
public class I6000TokenProperties {

	// accessToken
	private String accessToken;

	// publicKey
	private String publicKey;

	// address
	private String address;

	private String field;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "I6000TokenProperties{" +
			"accessToken='" + accessToken + '\'' +
			", publicKey='" + publicKey + '\'' +
			", address='" + address + '\'' +
			'}';
	}
}


