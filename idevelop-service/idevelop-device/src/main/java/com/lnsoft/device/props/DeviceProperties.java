package com.lnsoft.device.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * DeviceProperties
 *
 * @author xuejg
 */
@Data
@ConfigurationProperties(prefix = "demo")
public class DeviceProperties {
	/**
	 * 名称
	 */
	private String name;
}
