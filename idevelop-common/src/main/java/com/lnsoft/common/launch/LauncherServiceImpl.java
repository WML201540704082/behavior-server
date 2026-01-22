
package com.lnsoft.common.launch;

import com.lnsoft.common.constant.LauncherConstant;
import com.lnsoft.core.launch.service.LauncherService;
import com.lnsoft.core.launch.utils.PropsUtil;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Properties;

/**
 * 启动参数拓展
 *
 * @author idevelop
 */
public class LauncherServiceImpl implements LauncherService {

	@Override
	public void launcher(SpringApplicationBuilder builder, String appName, String profile) {
		Properties props = System.getProperties();
		PropsUtil.setProperty(props, "spring.cloud.nacos.discovery.server-addr", LauncherConstant.nacosAddr(profile));
		PropsUtil.setProperty(props, "spring.cloud.nacos.config.server-addr", LauncherConstant.nacosAddr(profile));

		PropsUtil.setProperty(props, "spring.cloud.sentinel.transport.dashboard", LauncherConstant.sentinelAddr(profile));
		PropsUtil.setProperty(props, "spring.zipkin.base-url", LauncherConstant.zipkinAddr(profile));

		// PropsUtil.setProperty(props, "spring.cloud.nacos.discovery.group", LauncherConstant.nacosGroup(profile));
		// PropsUtil.setProperty(props, "spring.cloud.nacos.config.group", LauncherConstant.nacosGroup(profile));
		//
		// PropsUtil.setProperty(props, "spring.cloud.nacos.discovery.namespace", LauncherConstant.nacosNamespace(profile));
		// PropsUtil.setProperty(props, "spring.cloud.nacos.config.namespace", LauncherConstant.nacosNamespace(profile));
	}

}
