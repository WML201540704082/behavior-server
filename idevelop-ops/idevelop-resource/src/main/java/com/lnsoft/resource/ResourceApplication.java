
package com.lnsoft.resource;

import com.lnsoft.core.cloud.client.IdevelopCloudApplication;
import com.lnsoft.core.launch.IdevelopApplication;
import com.lnsoft.core.launch.constant.AppConstant;

/**
 * 资源启动器
 *
 * @author guozhao
 */
@IdevelopCloudApplication
public class ResourceApplication {

	public static void main(String[] args) {
		IdevelopApplication.run(AppConstant.APPLICATION_RESOURCE_NAME, ResourceApplication.class, args);
	}

}

