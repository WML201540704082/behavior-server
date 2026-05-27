package com.lnsoft.device.config;

import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.device.constant.I6000Constant;
import com.lnsoft.device.props.CmdbDictProperties;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xuel
 * @CreateTime: 2024/4/15 16:42
 * @Description: CmdbI6000Configuration
 */

@Component
@AllArgsConstructor
public class CmdbI6000Configuration {

	private static final Logger LOGGER = LoggerFactory.getLogger(CmdbI6000Configuration.class);

	private static final Map<String, Long> I6000_EUNUM_DICT = new HashMap<>();
	private static final Map<String, Long> I6000_EXTERNAL_DICT_MAP = new HashMap<>();

	private CmdbDictProperties cmdbDictProperties;

	@PostConstruct
	public void init() {
		try {
			I6000_EUNUM_DICT.put("AC_TYPE", cmdbDictProperties.getApplicationType());
			I6000_EUNUM_DICT.put("ASSET_ADD", cmdbDictProperties.getDeviceAdd());
			I6000_EUNUM_DICT.put("ASSET_CHANGE", cmdbDictProperties.getDeviceChangeType());
			I6000_EUNUM_DICT.put("AUDIT_STATE", cmdbDictProperties.getAfterStatus());
			I6000_EUNUM_DICT.put("BAK_RTPD_UNIT", cmdbDictProperties.getBackTermUnit());
			I6000_EUNUM_DICT.put("BATCH_BUG", cmdbDictProperties.getModeBatchHiddenTrouble());
			I6000_EUNUM_DICT.put("BEBER", cmdbDictProperties.getFactoryAreaCode());
			I6000_EUNUM_DICT.put("CAN_SCRAP_FLAG", cmdbDictProperties.getYesNo());
			I6000_EUNUM_DICT.put("CITYPE", cmdbDictProperties.getRaidStorageType());
			I6000_EUNUM_DICT.put("CITYPE_ID", cmdbDictProperties.getResourceCategoryId());
			I6000_EUNUM_DICT.put("CLOUD_TYPE", cmdbDictProperties.getCloudType());
			I6000_EUNUM_DICT.put("CPU_ARCHITEC", cmdbDictProperties.getCpuArchCode());
			I6000_EUNUM_DICT.put("CYCLE_STATUS", cmdbDictProperties.getDeviceStatus());
			I6000_EUNUM_DICT.put("ELECTRIC_MODE", cmdbDictProperties.getPowerSupplyModel());
			I6000_EUNUM_DICT.put("ENABLED_STATUS", cmdbDictProperties.getUseStatus());
			I6000_EUNUM_DICT.put("ENTIRE_BAK_MODE", cmdbDictProperties.getFunBackup());
			I6000_EUNUM_DICT.put("ENTIRE_BAK_PD_UNIT", cmdbDictProperties.getFunBackupPeriodUnit());
			I6000_EUNUM_DICT.put("ERP_ASSET_STATE", cmdbDictProperties.getErpTransferStatus());
			I6000_EUNUM_DICT.put("FAULT_COMPONENT", cmdbDictProperties.getFaultParts());
			I6000_EUNUM_DICT.put("FUNCTION_DESCRIP", cmdbDictProperties.getFunctionDescrip());
			I6000_EUNUM_DICT.put("GROW_BAK_MODE", cmdbDictProperties.getAddBackupType());
			I6000_EUNUM_DICT.put("HALT_STATE", cmdbDictProperties.getDownStatus());
			I6000_EUNUM_DICT.put("HARDDISK_TYPE", cmdbDictProperties.getHardDiskTypeCode());
			I6000_EUNUM_DICT.put("HIGH_AVAIL_TYPE", cmdbDictProperties.getHAType());
			I6000_EUNUM_DICT.put("HOST_EFFECT_TYPE", cmdbDictProperties.getUseToType());
			I6000_EUNUM_DICT.put("HOT_BAK", cmdbDictProperties.getYesNo());
			I6000_EUNUM_DICT.put("INFO_INTEGRATED", cmdbDictProperties.getYesNo());
			I6000_EUNUM_DICT.put("INTEND_SCRAP_FLAG", cmdbDictProperties.getYesNo());
			I6000_EUNUM_DICT.put("isCloudManage", cmdbDictProperties.getYesNo());
			I6000_EUNUM_DICT.put("IS_FIREWALL", cmdbDictProperties.getYesNo());
			I6000_EUNUM_DICT.put("IS_TIME_SYNC", cmdbDictProperties.getYesNo());
			// I6000_EUNUM_DICT.put("IS_WABN_DEVICE", );
			I6000_EUNUM_DICT.put("LINK_AGG_FLAG", cmdbDictProperties.getYesNo());
			I6000_EUNUM_DICT.put("LOG_DUMP", cmdbDictProperties.getYesNo());
			I6000_EUNUM_DICT.put("MADE_COUNTRY", cmdbDictProperties.getCountryArea());
			I6000_EUNUM_DICT.put("MANAG_DEPTTYPE", cmdbDictProperties.getManagementDept());
			I6000_EUNUM_DICT.put("MASTER_FLAG", cmdbDictProperties.getStandbyAttr());
			I6000_EUNUM_DICT.put("MEASURE_ANIMAL", cmdbDictProperties.getAnimalProtection());
			I6000_EUNUM_DICT.put("MEASURE_LAND", cmdbDictProperties.getEarthingProtection());
			I6000_EUNUM_DICT.put("MEASURE_PLUG", cmdbDictProperties.getPluggingProtection());
			I6000_EUNUM_DICT.put("MEASURE_SHIELD", cmdbDictProperties.getShueldingProtection());
			I6000_EUNUM_DICT.put("MEASURE_THUNDER", cmdbDictProperties.getLightningProtection());
			// I6000_EUNUM_DICT.put("MINI_COMPUTER_TYPE", );
			I6000_EUNUM_DICT.put("NETACC_TYPE", cmdbDictProperties.getNetPortType());
			I6000_EUNUM_DICT.put("NETAPP_TYPE", cmdbDictProperties.getApplicationType());
			I6000_EUNUM_DICT.put("NETWORK", cmdbDictProperties.getNetWorkCode());
			I6000_EUNUM_DICT.put("NETWORK_EFFECT_TYPE", cmdbDictProperties.getNetworkDeviceType());
			I6000_EUNUM_DICT.put("OS_DATATYPE", cmdbDictProperties.getOSTypeCode());
			I6000_EUNUM_DICT.put("OS_RELEASE_VERSION", cmdbDictProperties.getOSIssueVersion());
			I6000_EUNUM_DICT.put("OTHER_CORP_FLAG", cmdbDictProperties.getYesNo());
			// I6000_EUNUM_DICT.put("PRIORITY", );
			I6000_EUNUM_DICT.put("PROP_STATUS", cmdbDictProperties.getOwnerStatus());
			I6000_EUNUM_DICT.put("PUR_MODE", cmdbDictProperties.getProcureTypeCode());
			I6000_EUNUM_DICT.put("RAIDMETHOD", cmdbDictProperties.getRaidType());
			I6000_EUNUM_DICT.put("RAID_RDUDC_MODE", cmdbDictProperties.getRaidStorageType());
			I6000_EUNUM_DICT.put("REPAIR_STATE", cmdbDictProperties.getOverhaulStatus());
			// I6000_EUNUM_DICT.put("RUN_ENVIRONMENT", );
			I6000_EUNUM_DICT.put("RUN_GRADE", cmdbDictProperties.getOperationLevel());
			I6000_EUNUM_DICT.put("RUN_LEVEL", cmdbDictProperties.getOprtImportance());
			I6000_EUNUM_DICT.put("RUN_STATUS", cmdbDictProperties.getOprtStatus());
			I6000_EUNUM_DICT.put("SAFE_BOUNDARY", cmdbDictProperties.getSecurityBoundary());
			I6000_EUNUM_DICT.put("SAFE_CHECK", cmdbDictProperties.getYesNo());
			I6000_EUNUM_DICT.put("SAFE_FASTEN", cmdbDictProperties.getYesNo());
			I6000_EUNUM_DICT.put("SCAN_FLAG", cmdbDictProperties.getYesNo());
			I6000_EUNUM_DICT.put("SCRAP_CAUSE", cmdbDictProperties.getScrapCause());
			// I6000_EUNUM_DICT.put("SORT_ORDER", );
			I6000_EUNUM_DICT.put("SOURCE", cmdbDictProperties.getSource());
			I6000_EUNUM_DICT.put("STGY_FLAG", cmdbDictProperties.getYesNo());
			I6000_EUNUM_DICT.put("SWITCH_TEST", cmdbDictProperties.getSwitchTest());
			I6000_EUNUM_DICT.put("SYNC_ERP_FLAG", cmdbDictProperties.getYesNo());
			I6000_EUNUM_DICT.put("TERM_OFFICE", cmdbDictProperties.getYesNo());
			I6000_EUNUM_DICT.put("USABLE_FLAG", cmdbDictProperties.getYesNo());
			I6000_EUNUM_DICT.put("VPLATFORM_TYPE", cmdbDictProperties.getVirtualizationPlayformTyp());

			// **外部数据**
			// BDZ 线站标识
			I6000_EXTERNAL_DICT_MAP.put("BDZ", cmdbDictProperties.getLineStation());
			// BRAND 品牌
			I6000_EXTERNAL_DICT_MAP.put("BRAND", cmdbDictProperties.getBrand());
			// CPU_BRAND CPU品牌
			I6000_EXTERNAL_DICT_MAP.put("CPU_BRAND", cmdbDictProperties.getCpuBrand());
			// MANUFACTURER	制造商
			I6000_EXTERNAL_DICT_MAP.put("MANUFACTURER", cmdbDictProperties.getMaker());
			// MFR 开发商
			I6000_EXTERNAL_DICT_MAP.put("MFR", cmdbDictProperties.getMaker());
			// MODEL 型号
			I6000_EXTERNAL_DICT_MAP.put("MODEL", cmdbDictProperties.getModel());
			// OS_DATATYPE 操作系统类型
			I6000_EXTERNAL_DICT_MAP.put("OS_DATATYPE", cmdbDictProperties.getOSTypeCode());
			// SERIES 系列
			I6000_EXTERNAL_DICT_MAP.put("SERIES", cmdbDictProperties.getSeries());

		} catch (Exception e) {
			LOGGER.info("初始化CmdbI6000映射字段失败: " + e.getMessage());
		}
	}

	/**
	 * 获取i6000 枚举值 和 外部数据
	 *
	 * @param attrCode
	 * @return
	 */
	public static Long getCmdbCiIdByI6000(String attrCode, String constant) {
		if (StringUtils.pathEquals(I6000Constant.TYPE_ENUM , constant)) {
			return I6000_EUNUM_DICT.get(attrCode);
		} else if (StringUtils.pathEquals(I6000Constant.TYPE_EXTERNAL , constant)){
			return I6000_EXTERNAL_DICT_MAP.get(attrCode);
		} else {
			throw new ServiceException("未查询到对应的属性映射关系,请求联系运维人员处理! attrCode = " + attrCode);
		}
	}

}
