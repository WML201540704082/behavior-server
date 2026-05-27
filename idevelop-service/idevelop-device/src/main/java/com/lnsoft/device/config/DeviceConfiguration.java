
package com.lnsoft.device.config;


import com.lnsoft.device.props.DeviceProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * 配置feign、mybatis包名、properties
 *
 * @author xuejg
 */
@Configuration(proxyBeanMethods = false)
@EnableFeignClients({"com.lnsoft", "com.lnsoft"})
@MapperScan({"com.lnsoft.**.mapper.**", "com.lnsoft.**.mapper.**"})
@EnableConfigurationProperties(DeviceProperties.class)
public class DeviceConfiguration {

}
