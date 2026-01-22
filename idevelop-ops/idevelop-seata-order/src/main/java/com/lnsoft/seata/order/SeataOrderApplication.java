
package com.lnsoft.seata.order;

import com.lnsoft.core.launch.IdevelopApplication;
import com.lnsoft.core.launch.constant.AppConstant;
import com.lnsoft.core.transaction.annotation.SeataCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Order启动器
 *
 * @author guozhao
 */
@SeataCloudApplication
@EnableFeignClients(AppConstant.BASE_PACKAGES)
public class SeataOrderApplication {

	public static void main(String[] args) {
		IdevelopApplication.run("idevelop-seata-order", SeataOrderApplication.class, args);
	}

}

