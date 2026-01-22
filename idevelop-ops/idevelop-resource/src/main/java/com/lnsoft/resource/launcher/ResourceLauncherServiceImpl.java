
package com.lnsoft.resource.launcher;

import com.lnsoft.core.launch.constant.AppConstant;
import com.lnsoft.core.launch.constant.NacosConstant;
import com.lnsoft.core.launch.service.LauncherService;
import com.lnsoft.core.launch.utils.PropsUtil;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Properties;

/**
 * 启动参数拓展
 *
 * @author guozhao
 */
public class ResourceLauncherServiceImpl implements LauncherService {

	@Override
	public void launcher(SpringApplicationBuilder builder, String appName, String profile) {
		Properties props = System.getProperties();
		PropsUtil.setProperty(props, "spring.cloud.nacos.config.ext-config[0].data-id", NacosConstant.dataId(AppConstant.APPLICATION_RESOURCE_NAME, profile));
		PropsUtil.setProperty(props, "spring.cloud.nacos.config.ext-config[0].group", NacosConstant.NACOS_CONFIG_GROUP);
		PropsUtil.setProperty(props, "spring.cloud.nacos.config.ext-config[0].refresh", NacosConstant.NACOS_CONFIG_REFRESH);
		// 自定义命名空间
		// PropsUtil.setProperty(props, "spring.cloud.nacos.config.namespace", LauncherConstant.NACOS_NAMESPACE);
		// PropsUtil.setProperty(props, "spring.cloud.nacos.discovery.namespace", LauncherConstant.NACOS_NAMESPACE);
	}

	@Override
	public int getOrder() {
		return 20;
	}
}
