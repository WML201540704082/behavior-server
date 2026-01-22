package com.lnsoft.ipc.demo.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * DemoProperties
 *
 * @author guozhao
 */
@Data
@ConfigurationProperties(prefix = "demo")
public class DemoProperties {
	/**
	 * 名称
	 */
	private String name;
}
