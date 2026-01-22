
package com.lnsoft.auth;


import com.lnsoft.core.cloud.client.IdevelopCloudApplication;
import com.lnsoft.core.launch.IdevelopApplication;
import com.lnsoft.core.launch.constant.AppConstant;

/**
 * 用户认证服务器
 *
 * @author guozhao
 */
@IdevelopCloudApplication
public class AuthApplication {

	public static void main(String[] args) {
		IdevelopApplication.run(AppConstant.APPLICATION_AUTH_NAME, AuthApplication.class, args);
	}

}
