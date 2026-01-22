
package com.lnsoft.seata.storage;

import com.lnsoft.core.launch.IdevelopApplication;
import com.lnsoft.core.launch.constant.AppConstant;
import com.lnsoft.core.transaction.annotation.SeataCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Storage启动器
 *
 * @author guozhao
 */
@SeataCloudApplication
@EnableFeignClients(AppConstant.BASE_PACKAGES)
public class SeataStorageApplication {

	public static void main(String[] args) {
		IdevelopApplication.run("idevelop-seata-storage", SeataStorageApplication.class, args);
	}

}

