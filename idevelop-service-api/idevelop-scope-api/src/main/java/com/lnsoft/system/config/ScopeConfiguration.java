
package com.lnsoft.system.config;


import lombok.AllArgsConstructor;
import com.lnsoft.system.handler.DataScopeModelHandler;
import com.lnsoft.core.datascope.handler.ScopeModelHandler;
import com.lnsoft.core.secure.config.RegistryConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 公共封装包配置类
 *
 * @author guozhao
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@AutoConfigureBefore(RegistryConfiguration.class)
public class ScopeConfiguration {

	@Bean
	public ScopeModelHandler scopeModelHandler() {
		return new DataScopeModelHandler();
	}

}
