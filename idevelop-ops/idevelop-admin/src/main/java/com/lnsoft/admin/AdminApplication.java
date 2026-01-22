
package com.lnsoft.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import com.lnsoft.core.cloud.client.IdevelopCloudApplication;
import com.lnsoft.core.launch.IdevelopApplication;
import com.lnsoft.core.launch.constant.AppConstant;

/**
 * admin启动器
 *
 * @author guozhao
 */
@EnableAdminServer
@IdevelopCloudApplication
public class AdminApplication {

	public static void main(String[] args) {
		IdevelopApplication.run(AppConstant.APPLICATION_ADMIN_NAME, AdminApplication.class, args);
	}

}
