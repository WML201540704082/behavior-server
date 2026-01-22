
package com.lnsoft.core.log;

import com.lnsoft.core.cloud.client.IdevelopCloudApplication;
import com.lnsoft.core.launch.IdevelopApplication;
import com.lnsoft.core.launch.constant.AppConstant;

/**
 * 日志服务
 *
 * @author guozhao
 */
@IdevelopCloudApplication
public class LogApplication {

	public static void main(String[] args) {
		IdevelopApplication.run(AppConstant.APPLICATION_LOG_NAME, LogApplication.class, args);
	}

}
