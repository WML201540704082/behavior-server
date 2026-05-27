package com.lnsoft.device.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.Properties;

@Slf4j
public class PropertiesUtil {
	public static String getValue(String key){
		String value = null;
		try {
			Properties properties = PropertiesLoaderUtils.loadProperties(new EncodedResource(new ClassPathResource("application.properties"), "utf8"));
			value = properties.getProperty(key);
		} catch (Exception e) {
			log.warn(e.getMessage());;
		}
		return value==null ? "" : value;
	}

}

