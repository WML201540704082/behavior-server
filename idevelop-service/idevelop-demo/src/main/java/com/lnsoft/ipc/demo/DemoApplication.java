
package com.lnsoft.ipc.demo;

import com.lnsoft.core.cloud.client.IdevelopCloudApplication;
import com.lnsoft.core.launch.IdevelopApplication;

/**
 * Demo启动器
 *
 * @author guozhao
 */
@IdevelopCloudApplication
public class DemoApplication {

	public static void main(String[] args) {
		IdevelopApplication.run("idevelop-demo", DemoApplication.class, args);
	}

}

