package com.lnsoft.device.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: xueli
 * @CreateTime: 2024/4/19 14:49
 * @Description: 映射配置文件中api实体
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "file-import")
public class FileImportProperties {

	/**
	 * 是否使用带有级联关系模板处理
	 */
	private boolean allow;


	@Override
	public String toString() {
		return "FileImportProperties{" +
			"isAllow=" + allow +
			'}';
	}
}


