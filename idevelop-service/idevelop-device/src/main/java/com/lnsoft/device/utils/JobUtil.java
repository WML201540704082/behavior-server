package com.lnsoft.device.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;


/**
 * @author 中间表工具类
 *
 */
@Slf4j
public class JobUtil {

	/**
	 * 有中间表的城市编码
	 */
//	public static String cityCodeArray = "0504,0511,";
	public static String cityCodeArray = PropertiesUtil.getValue("cityCodeArray");

	/**获取配置的城市信息
	 * @return
	 */
	public static String getCityCodeArray(){
		String value = "";
		try {
//			Properties properties = new Properties();
//			InputStream inputStream = JobUtil.class.getResourceAsStream("DBManager.properties");
//
//			properties.load(inputStream);
//			//获取上传图片的配置
//			Object uploadConfig = properties.get("cityCodeArray");
//			value = uploadConfig == null ? "" : uploadConfig.toString();
			value = PropertiesLoaderUtils.loadProperties(new EncodedResource(new ClassPathResource("application.properties"), "utf8")).getProperty("cityCodeArray");
			value = value == null ? "" : value;
		} catch (Exception e) {
			log.warn(e.getMessage());;
		}
		return value;
	}
}
