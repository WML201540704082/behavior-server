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
@ConfigurationProperties(prefix = "erp-massage")
public class ErpMassageProperties {

	// 账号
	private String userName;

	// 密码
	private String passWord;

	// ERP在信通一体化中的审核人信息: 人员ID
	private Long userId;

}


