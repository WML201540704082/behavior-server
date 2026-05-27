package com.lnsoft.device.config;

import com.lnsoft.device.props.CmdbDictProperties;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ExcelDeviceTemplateConfiguration
 * @description:
 * @author: zhangs
 * @create: 2024-04-19 11:02
 **/
@Component
@AllArgsConstructor
public class ExcelDeviceTemplateConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(ExcelDeviceTemplateConfiguration.class);
	private static final Map<String, Long> EXCEL_CI_ID = new HashMap<>();
	private CmdbDictProperties cmdbDictProperties;

	@PostConstruct
	public void init() {
		try {
			// 制造商
			EXCEL_CI_ID.put("maker", cmdbDictProperties.getMaker());
			// 品牌
			EXCEL_CI_ID.put("brand", cmdbDictProperties.getBrand());
			// 系列
			EXCEL_CI_ID.put("series", cmdbDictProperties.getSeries());
			// 型号
			EXCEL_CI_ID.put("model", cmdbDictProperties.getModel());
			// 是否
			EXCEL_CI_ID.put("yes-no", cmdbDictProperties.getYesNo());
			// CPU品牌
			EXCEL_CI_ID.put("cpu-brand", cmdbDictProperties.getCpuBrand());
			// CPU架构
			EXCEL_CI_ID.put("cpu-arch-code", cmdbDictProperties.getCpuArchCode());
			// 硬盘类型
			EXCEL_CI_ID.put("hard-disk-type-code", cmdbDictProperties.getHardDiskTypeCode());
			// 存储RAID冗余方式
			EXCEL_CI_ID.put("raid-storage-type", cmdbDictProperties.getRaidStorageType());
			// 网口类型
			EXCEL_CI_ID.put("net-port-type", cmdbDictProperties.getNetPortType());
			// 采购方式
			EXCEL_CI_ID.put("procure-type-code", cmdbDictProperties.getProcureTypeCode());
			// 网络设备用途类型
			EXCEL_CI_ID.put("network-device-type", cmdbDictProperties.getNetworkDeviceType());
			// 操作系统类型
			EXCEL_CI_ID.put("OSType-code", cmdbDictProperties.getOSTypeCode());
			//操作系统发行版本
			EXCEL_CI_ID.put("OSIssue-version", cmdbDictProperties.getOSIssueVersion());
			// 备品备件类型
			EXCEL_CI_ID.put("spare-parts-type", cmdbDictProperties.getSparePartsType());
			// 服务级别
			EXCEL_CI_ID.put("service-level", cmdbDictProperties.getServiceLevel());
			// 设备类型
			EXCEL_CI_ID.put("device-type", cmdbDictProperties.getDeviceType());
			// 设备分类
			EXCEL_CI_ID.put("device-claccify", cmdbDictProperties.getDeviceClaccify());
			// 设备状态
			EXCEL_CI_ID.put("device-status", cmdbDictProperties.getDeviceStatus());
			// 设备来源
			EXCEL_CI_ID.put("device-source", cmdbDictProperties.getDeviceSource());
			// 所属网络
			EXCEL_CI_ID.put("net-work-code", cmdbDictProperties.getNetWorkCode());
			// 报废原因
			EXCEL_CI_ID.put("scrap-cause", cmdbDictProperties.getScrapCause());
			//空调类型
			EXCEL_CI_ID.put("air-condition-type", cmdbDictProperties.getAirConditionType());
			//设备数据操作类型
			EXCEL_CI_ID.put("data-opt", cmdbDictProperties.getDataOpt());
			//设备增加方式
			EXCEL_CI_ID.put("device-add-type", cmdbDictProperties.getDeviceAddType());
			//设备变动方式
			EXCEL_CI_ID.put("device-change-type", cmdbDictProperties.getDeviceChangeType());
			//计量单位
			EXCEL_CI_ID.put("unit", cmdbDictProperties.getUnifiedCode());
			//电压
			EXCEL_CI_ID.put("power-level", cmdbDictProperties.getPowerLevel());
			// 工厂区域
			EXCEL_CI_ID.put("factory-area-code", cmdbDictProperties.getFactoryAreaCode());
			// 所属安全边界
			EXCEL_CI_ID.put("security-boundary", cmdbDictProperties.getSecurityBoundary());
			// 主备属性
			EXCEL_CI_ID.put("standby-attr", cmdbDictProperties.getStandbyAttr());
			// 设备用途类型
			EXCEL_CI_ID.put("server-use-to-type", cmdbDictProperties.getServerUseToType());
			// 字典-云类型
			EXCEL_CI_ID.put("cloud-type", cmdbDictProperties.getCloudType());
		} catch (Exception e) {
			logger.info("初始化cmdb模型id失败:{}", e);
		}
	}

	public static Long getCmdbCiIdByExcel(String key) {
		return EXCEL_CI_ID.get(key);
	}

}
