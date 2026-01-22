
package com.lnsoft.report;

import com.lnsoft.core.cloud.client.IdevelopCloudApplication;
import com.lnsoft.core.launch.IdevelopApplication;
import com.lnsoft.core.launch.constant.AppConstant;

/**
 * UReport启动器
 *
 * @author guozhao
 */
@IdevelopCloudApplication
public class ReportApplication {

	public static void main(String[] args) {
		IdevelopApplication.run(AppConstant.APPLICATION_REPORT_NAME, ReportApplication.class, args);
	}

}
