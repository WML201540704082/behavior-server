
package com.lnsoft.device;

import com.lnsoft.core.cloud.client.IdevelopCloudApplication;
import com.lnsoft.core.launch.IdevelopApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Demo启动器
 *
 * @author guozhao
 */
@EnableAsync
@ComponentScans(value = {@ComponentScan("com.lnsoft.hussar")})
@EnableFeignClients({"com.lnsoft"})
@IdevelopCloudApplication
public class DeviceApplication {

	public static void main(String[] args) {
		IdevelopApplication.run("idevelop-device", DeviceApplication.class, args);
	}

}

