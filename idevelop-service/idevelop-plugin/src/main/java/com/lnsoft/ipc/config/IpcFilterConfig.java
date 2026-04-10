package com.lnsoft.ipc.config;

import com.lnsoft.ipc.filter.IpcDataCryptoFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class IpcFilterConfig {

    @Bean
    public FilterRegistrationBean<IpcDataCryptoFilter> ipcCryptoFilterRegistration() {
        FilterRegistrationBean<IpcDataCryptoFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new IpcDataCryptoFilter());
        registrationBean.addUrlPatterns("/face/*"); // 拦截ipc-service所有请求
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE); // 确保优先级最高，避免流被提前读取
        registrationBean.setName("ipcDataCryptoFilter");
        return registrationBean;
    }
}
