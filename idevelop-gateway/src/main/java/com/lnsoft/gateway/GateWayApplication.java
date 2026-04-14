
package com.lnsoft.gateway;

import com.lnsoft.core.launch.IdevelopApplication;
import com.lnsoft.core.launch.config.IpFilter;
import com.lnsoft.core.launch.config.IpFilterConfiguration;
import com.lnsoft.core.launch.constant.AppConstant;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 项目启动
 *
 * @author guozhao
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = {IpFilterConfiguration.class})
public class GateWayApplication {

	public static void main(String[] args) {
		IdevelopApplication.run(AppConstant.APPLICATION_GATEWAY_NAME, GateWayApplication.class, args);
	}

}
