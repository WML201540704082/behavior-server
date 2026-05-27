package com.lnsoft.device.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: xueli
 * @CreateTime: 2024/1/22 14:49
 * @Description: 映射配置文件中api实体
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "device.check")
public class CheckProperties implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<String> invalidKeywords;

	private List<String> invalidCharacters;

	private List<String> invalidEndings;

	private List<String> invalidEqualWords;
}


