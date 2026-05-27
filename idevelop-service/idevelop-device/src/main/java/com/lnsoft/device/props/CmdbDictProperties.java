package com.lnsoft.device.props;

import com.lnsoft.device.api.cmdb.wrapper.CmdbCiAttrWrapper;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: xueli
 * @CreateTime: 2024/1/22 14:49
 * @Description: 映射配置文件中api实体
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "cmdb-dict")
public class CmdbDictProperties implements Serializable {
	private static final long serialVersionUID = 1L;

	// 字典-设备分类
	private Long deviceClaccify;

	// 字典-设备类型
	private Long deviceType;

	// 字典-设备来源
	private Long deviceSource;

	// 字典-品牌
	private Long brand;

	// 字典-系列
	private Long series;

	// 字典-型号
	private Long model;

	// 字典-售后状态
	private Long afterStatus;

	// 字典-设备状态
	private Long deviceStatus;

	// 字典-电压等级
	private Long powerLevel;

	// 字典-设备增加方式
	private Long deviceAdd;

	// 字典-制造国家与地区(国家与区域)
	private Long countryArea;

	// 字典-计量单位
	private Long unifiedCode;

	// 字典-工厂区域
	private Long factoryAreaCode;

	// 字典-报废原因
	private Long scrapCause;

	// 字典-CPU品牌
	private Long cpuBrand;

	// 字典-备品备件类型
	private Long sparePartsType;

	// 字典-制造商
	private Long maker;

	// 字典-采购方式
	private Long procureTypeCode;

	// 字典-设备变动方式
	private Long deviceChangeType;

	// 字典-服务级别
	private Long serviceLevel;

	// 字典-电压等级
	private Long voltageLevel;

	// 字典-是否
	private Long yesNo;

	// 字典-所属网络
	private Long netWorkCode;

	// 字典-网口类型
	private Long netPortType;

	// 字典-硬盘类型
	private Long hardDiskTypeCode;

	// 字典-内存代次
	private Long memoryLevel;

	// 字典-CPU架构
	private Long cpuArchCode;

	// 字典-操作系统类型
	private Long OSTypeCode;

	// 字典-RAID方式
	private Long raidType;

	// 字典-停机状态
	private Long downStatus;

	// 字典-购买类型
	private Long purchaseMethod;

	// 字典-运维等级
	private Long operationLevel;

	// 字典-ERP转资状态
	private Long erpTransferStatus;

	// 字典-空调类型
	private Long airConditionType;

	// 字典-审核状态
	private Long examineStatus;

	// 字典-备份保留期限单位
	private Long backTermUnit;

	// 字典-型号批次隐患缺陷
	private Long modeBatchHiddenTrouble;

	// 字典-资源类型
	private Long resourceType;

	// 字典-资源分类ID
	private Long resourceCategoryId;

	// 字典-云类型
	private Long cloudType;

	// 字典-供电方式
	private Long powerSupplyModel;

	// 字典-使用状态
	private Long useStatus;

	// 字典-完全备份方式
	private Long funBackup;

	// 字典-完全备份周期单位
	private Long funBackupPeriodUnit;

	// 字典-故障部件
	private Long faultParts;

	// 字典-功能描述
	private Long functionDescrip;

	// 字典-增量备份方式
	private Long addBackupType;

	// 字典-高可用类型
	private Long HAType;

	// 字典-设备用途类型
	private Long useToType;

	// 字典-设备管理部门
	private Long managementDept;

	// 字典-主备属性
	private Long standbyAttr;

	// 字典-防小动物措施
	private Long animalProtection;

	// 字典-接地措施
	private Long earthingProtection;

	// 字典-封堵措施
	private Long pluggingProtection;

	// 字典-屏蔽措施
	private Long shueldingProtection;

	// 字典-防雷措施
	private Long lightningProtection;

	// 字典-应用类型
	private Long applicationType;

	// 字典-网络设备用途类型
	private Long networkDeviceType;

	// 字典-操作系统发行版本
	private Long OSIssueVersion;

	// 字典-产权状态
	private Long ownerStatus;

	// 字典-存储RAID冗余方式
	private Long raidStorageType;

	// 字典-检修状态
	private Long overhaulStatus;

	// 字典-运行重要程度
	private Long oprtImportance;

	// 字典-运行状态
	private Long oprtStatus;

	// 字典-所属安全边界
	private Long securityBoundary;

	// 字典-来源
	private Long source;

	// 字典-虚拟化平台类型
	private Long virtualizationPlayformTyp;

	// 字典-切换测试开展
	private Long switchTest;

	// 字典-线站标识
	private Long lineStation;

	// 是否治理 - 否 用于导出数据
	private Long governNo;

	// 数据治理 - 操作类型
	private Long dataOpt;

	// 字典-设备增加方式
	private Long deviceAddType;

	// 设备用途类型
	private Long serverUseToType;

	// 设备用途类型
	private Long sourceSystem;

	/**
	 * 转换方法 CMDB
	 *
	 * @param ciId cmdb模型ID
	 * @return
	 */
	public Map<Object, Object> getDictMapByCiId(Long ciId) {
		List<Map<String, Object>> data = CmdbCiAttrWrapper.build().getCiCientityDictList(ciId).getData();
		return data.stream()
			.collect(Collectors.toMap(map -> map.get("dictKey"), map1 -> map1.get("dictValue")));
	}


	/**
	 * 转换方法 CMDB
	 *
	 * @param ciId cmdb模型ID
	 * @return
	 */
	public List<Map<String, Object>> getDictListByCiId(Long ciId) {
		return CmdbCiAttrWrapper.build().getCiCientityDictList(ciId).getData();
	}

	/**
	 * 转换方法 Erp
	 *
	 * @param ciId cmdb模型ID
	 * @return
	 */
	public Map<Object, Object> getDictErpMapByCiId(Long ciId) {
		List<Map<String, Object>> data = CmdbCiAttrWrapper.build().getCiCientityDictList(ciId).getData();
		return data.stream()
			.filter(item -> item.containsKey("dictKeyErp") && (item.get("dictKeyErp") != null && !"".equals(item.get("dictKeyErp"))))
			.collect(Collectors.toMap(map -> map.get("dictKey"), map1 -> map1.get("dictKeyErp")));
	}

	/**
	 * 转换方法 I6000
	 *
	 * @param ciId cmdb模型ID
	 * @return
	 */
	public Map<String, String> getDictI6000MapByCiId(Long ciId) {
		List<Map<String, Object>> data = CmdbCiAttrWrapper.build().getCiCientityDictList(ciId).getData();
		return data.stream()
			.filter(item -> item.containsKey("dictKeyI6000") && (item.get("dictKeyI6000") != null && !"".equals(item.get("dictKeyI6000"))))
			.collect(Collectors.toMap(map -> map.get("dictKey").toString(), map1 -> map1.get("dictKeyI6000").toString()));
	}

	/**
	 * 转换方法 特殊场景使用 dictKey == id (id + dictKey)
	 *
	 * @param ciId cmdb模型ID
	 * @return
	 */
	public Map<Object, Object> getDictMapByCiIdSpecial(Long ciId) {
		List<Map<String, Object>> data = CmdbCiAttrWrapper.build().getCiCientityDictList(ciId).getData();
		return data.stream()
			.collect(Collectors.toMap(map -> map.get("dictKey"), map1 -> map1.get("remarkTemp")));
	}

	/**
	 * 转换制造国家与地区专用 特殊场景使用 dictValue == dictKeyErp
	 *
	 * @param ciId cmdb模型ID
	 * @return
	 */
	public Map<Object, Object> getCountryAreaErpMapByCiId(Long ciId) {
		List<Map<String, Object>> data = CmdbCiAttrWrapper.build().getCiCientityDictList(ciId).getData();
		return data.stream()
			.filter(item -> item.containsKey("dictKeyErp") && (item.get("dictKeyErp") != null && !"".equals(item.get("dictKeyErp"))))
			.collect(Collectors.toMap(map -> map.get("dictValue"), map1 -> map1.get("dictKeyErp"), (entity1, entity2) -> entity1));
	}

	/**
	 * 转换工厂区域专用
	 *
	 * @param ciId cmdb模型ID
	 * @return
	 */
	public Map<Object, Object> getFactoryAreaErpMapByCiId(Long ciId) {
		List<Map<String, Object>> data = CmdbCiAttrWrapper.build().getCiCientityDictList(ciId).getData();
		return data.stream()
			.filter(item -> item.containsKey("dictKeyErp") && (item.get("dictKeyErp") != null && !"".equals(item.get("dictKeyErp"))))
			.collect(Collectors.toMap(map -> map.get("dictKey"), map1 -> map1.get("dictKeyErp"), (entity1, entity2) -> entity1));
	}

	/**
	 * 过滤出I6000和ERP的枚举项 转换方法 dictKey 和 dictKeyI6000
	 *
	 * @param ciId cmdb模型ID
	 * @return
	 */
	public Map<String, String> getErpI6000MapByCiId(Long ciId) {
		List<Map<String, Object>> data = CmdbCiAttrWrapper.build().getCiCientityDictList(ciId).getData();
		return data.stream()
			.filter(item -> item.containsKey("dictKeyI6000") && (item.get("dictKeyI6000") != null && !"".equals(item.get("dictKeyI6000"))))
			.filter(item -> item.containsKey("dictKeyErp") && (item.get("dictKeyErp") != null && !"".equals(item.get("dictKeyErp"))))
			.collect(Collectors.toMap(map -> map.get("dictKey").toString(), map1 -> map1.get("dictKeyI6000").toString()));
	}

	/**
	 * 获取 I6000编码 为Key 枚举项的为Value的 数据
	 *
	 * @param ciId cmdb模型ID
	 * @return
	 */
	public Map<String, Map<String, Object>> getI6000MapByI6000(Long ciId) {
		List<Map<String, Object>> data = CmdbCiAttrWrapper.build().getCiCientityDictList(ciId).getData();
		return data.stream()
			.filter(item -> item.containsKey("dictKeyI6000") && (item.get("dictKeyI6000") != null && !"".equals(item.get("dictKeyI6000"))))
			.collect(Collectors.toMap(map -> map.get("dictKeyI6000").toString(), map1 -> map1));
	}

}


