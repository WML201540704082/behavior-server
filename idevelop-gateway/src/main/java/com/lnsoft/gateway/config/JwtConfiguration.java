

package com.lnsoft.gateway.config;

import com.lnsoft.gateway.utils.RedisUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lnsoft.gateway.props.JwtProperties;
import com.lnsoft.gateway.utils.JwtUtil;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * JWT配置信息
 *
 * @author guozhao
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@EnableConfigurationProperties({JwtProperties.class})
public class JwtConfiguration implements SmartInitializingSingleton {

	private final JwtProperties properties;
	private final RedisUtil redisUtil;

	@Override
	public void afterSingletonsInstantiated() {
		JwtUtil.setJwtProperties(properties);
		JwtUtil.setRedisUtil(redisUtil);
	}


}
