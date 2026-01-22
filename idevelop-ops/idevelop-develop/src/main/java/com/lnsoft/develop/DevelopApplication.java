
package com.lnsoft.develop;

import com.lnsoft.core.cloud.client.IdevelopCloudApplication;
import com.lnsoft.core.launch.IdevelopApplication;
import com.lnsoft.core.launch.constant.AppConstant;

/**
 * Develop启动器
 *
 * @author guozhao
 */
@IdevelopCloudApplication
public class DevelopApplication {

	public static void main(String[] args) {
		IdevelopApplication.run(AppConstant.APPLICATION_DEVELOP_NAME, DevelopApplication.class, args);
	}

}

