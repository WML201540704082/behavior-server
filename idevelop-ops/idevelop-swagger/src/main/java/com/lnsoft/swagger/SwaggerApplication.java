
package com.lnsoft.swagger;

import com.lnsoft.core.launch.IdevelopApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * swagger聚合启动器
 *
 * @author guozhao
 */
@SpringBootApplication
public class SwaggerApplication {

	public static void main(String[] args) {
		IdevelopApplication.run("idevelop-swagger", SwaggerApplication.class, args);
	}

}
