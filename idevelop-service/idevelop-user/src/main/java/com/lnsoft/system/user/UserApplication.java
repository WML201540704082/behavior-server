
package com.lnsoft.system.user;

import com.lnsoft.core.cloud.client.IdevelopCloudApplication;
import com.lnsoft.core.launch.IdevelopApplication;
import com.lnsoft.core.launch.constant.AppConstant;

/**
 * 用户启动器
 *
 * @author guozhao
 */
@IdevelopCloudApplication
public class UserApplication {

	public static void main(String[] args) {
		IdevelopApplication.run(AppConstant.APPLICATION_USER_NAME, UserApplication.class, args);
	}

}
