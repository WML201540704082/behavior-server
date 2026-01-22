
package com.lnsoft.ipc.demo.config;


import com.lnsoft.ipc.demo.props.DemoProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * 配置feign、mybatis包名、properties
 *
 * @author guozhao
 */
@Configuration(proxyBeanMethods = false)
@EnableFeignClients({"com.lnsoft", "com.example"})
@MapperScan({"com.lnsoft.**.mapper.**", "com.example.**.mapper.**"})
@EnableConfigurationProperties(DemoProperties.class)
public class DemoConfiguration {

}
