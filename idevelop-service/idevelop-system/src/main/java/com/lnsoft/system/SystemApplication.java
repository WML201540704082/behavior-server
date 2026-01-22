
package com.lnsoft.system;

import com.lnsoft.core.cloud.client.IdevelopCloudApplication;
import com.lnsoft.core.launch.IdevelopApplication;
import com.lnsoft.core.launch.constant.AppConstant;

/**
 * 系统模块启动器
 * @author guozhao
 */
@IdevelopCloudApplication
public class SystemApplication {

	public static void main(String[] args) {
		IdevelopApplication.run(AppConstant.APPLICATION_SYSTEM_NAME, SystemApplication.class, args);
	}

}

