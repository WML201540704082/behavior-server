package com.lnsoft.device.props;

import com.lnsoft.core.log.exception.ServiceException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.lnsoft.device.constant.CmdbAttrConstant.*;

/**
 * @Author: xueli
 * @CreateTime: 2024/4/19 14:49
 * @Description: 映射配置文件中api实体
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "cmdb-attr")
public class CmdbAttrProperties {

	// 属性-设备状态
	private Long deviceStatusCode;

	// 属性-设备类型
	private Long deviceTypeCode;

	// 属性-设备来源
	private Long deviceSourceCode;

	// 属性-网络设备用途
	private Long networkDeviceType;

	// 属性-是否信创设备
	private Long isITAICode;

	// 属性-计量单位
	private Long measureUnit;

	// 属性-资产台账来源系统
	private Long sourceSystem;

	// 属性-I6000主键UUID
	private Long i6000CiId;

	/**
	 * 获取模型属性ID
	 */
	public Long getCmdbAttrId(String constant) {
		if (StringUtils.equals(constant, DEVICE_STATUS_CODE)) {
			return this.deviceStatusCode;
		} else if (StringUtils.equals(constant, DEVICE_TYPE_CODE)) {
			return this.deviceTypeCode;
		} else if (StringUtils.equals(constant, DEVICE_SOURCE_CODE)) {
			return this.deviceSourceCode;
		} else if (StringUtils.equals(constant, NETWORK_DEVICE_TYPE)) {
			return this.networkDeviceType;
		} else if (StringUtils.equals(constant, IS_IT_AI_CODE)) {
			return this.isITAICode;
		} else if (StringUtils.equals(constant, MEASURE_UNIT)) {
			return this.measureUnit;
		} else {
			throw new ServiceException("未查询到需要的模型属性ID");
		}
	}
}


