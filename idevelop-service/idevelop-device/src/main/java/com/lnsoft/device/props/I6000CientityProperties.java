package com.lnsoft.device.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: xueli
 * @CreateTime: 2024/1/22 14:49
 * @Description: 映射配置文件中api实体
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "i6000-enum-id")
public class I6000CientityProperties {

	// 制造国家和地区_中国
	private String madeCountry1 = "40004407";


	// 云类型_阿里云
	private String cloudType1= "60016702";

}


