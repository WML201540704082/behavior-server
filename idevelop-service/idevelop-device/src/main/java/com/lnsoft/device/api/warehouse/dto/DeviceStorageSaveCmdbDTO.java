package com.lnsoft.device.api.warehouse.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @ClassName: DeviceStorageSaveCmdbDTO
 * @description:
 * @author: zhangs
 * @create: 2024-03-01 17:20
 **/
@Data
public class DeviceStorageSaveCmdbDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String uuid;

	@ApiModelProperty("区域")
	private String area;

	@ApiModelProperty("部门")
	private String dept;

	@ApiModelProperty("入库日期")
	private String inWarehouseDate;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty(value = "运维单位")
	private String operationUnit;
	private String operationUnitCode;

	@ApiModelProperty("设备来源；0统一纳管，1非统一纳管")
	private String deviceSource;
	private String deviceSourceCode;

	@ApiModelProperty("WBS项目")
	private String wbsElementName;

	@ApiModelProperty("WBS元素")
	private String wbsElement;

	@ApiModelProperty("设备分类")
	private String deviceCategory;
	private String deviceCategoryCode;

	@ApiModelProperty("设备类型")
	private String deviceType;
	private String deviceTypeCode;

	@ApiModelProperty("所在仓库")
	private String inWarehouse;
	private String inWarehouseCode;

	@ApiModelProperty("功能位置")
	private String funLocation;
	private String funLocationCode;

	@ApiModelProperty("运行单位")
	private String oprtDept;
	private String oprtDeptName;

	@ApiModelProperty("电压等级")
	private String voltageLevel;
	private String voltageLevelCode;

	@ApiModelProperty("采购日期")
	private LocalDate procureDate;

	@ApiModelProperty("产权单位")
	private String ownerUnit;
	private String ownerUnitCode;
	private String ownerUnitName;

	@ApiModelProperty("产权部门")
	private String propertyDept;
	private String propertyDeptCode;
	private String propertyDeptName;

	@ApiModelProperty("使用保管部门")
	private String useKeepDept;
	private String useKeepDeptName;

	@ApiModelProperty("实物管理部门")
	private String realManageDept;
	private String entityManagementDeptName;

	@ApiModelProperty("工厂区域")
	private String factoryArea;
	private String factoryAreaCode;

	@ApiModelProperty("维护工厂")
	private String maintenanceFactory;
	private String maintenanceFactoryCode;

	@ApiModelProperty("线站标识(设备建档数据来源)")
	private String lineStation;
	private String lineStationSign;

	@ApiModelProperty("设备编码")
	private String deviceCode;

	@ApiModelProperty("erp资产编码")
	private String assetCodeErp;

	@ApiModelProperty("erp台账编码")
	private String deviceCodeErp;

	@ApiModelProperty("设备名称")
	private String deviceName;

	@ApiModelProperty("设备状态")
	private String deviceStatus;
	private String deviceStatusCode;

	private String deviceHardwareInfo;


	@ApiModelProperty("序列号")
	private String sn;

	@ApiModelProperty("出厂日期")
	private String factoryDate;

	@ApiModelProperty("制造国家与地区")
	private String maintenanceCountry;
	private String maintenanceCountryCode;

	@ApiModelProperty("供应商")
	private String supplierName;

	@ApiModelProperty("供应商联系电话")
	private String supplierTel;

	@ApiModelProperty("资产原值")
	private String assetOriginal;

	@ApiModelProperty("净值")
	private String netWorth;

	@ApiModelProperty("品牌")
	private String brand;
	private String brandCode;

	@ApiModelProperty("系列")
	private String series;
	private String seriesCode;

	@ApiModelProperty("型号")
	private String deviceModel;
	private String deviceModelCode;

	@ApiModelProperty("CPU型号")
	private String cpuModel;

	@ApiModelProperty("内存大小(GB)")
	private String memSize;

	@ApiModelProperty("硬盘容量(GB)")
	private String hardDiskCapability;

	@ApiModelProperty("电源模块")
	private String powerModel;

	@ApiModelProperty("操作系统版本号")
	private String OSVersion;
	private String OSVersionCode;

	@ApiModelProperty("是否信创设备")
	private String isITAI;
	private String isITAICode;

	@ApiModelProperty("硬盘类型")
	private String hardDiskType;
	private String hardDiskTypeCode;

	@ApiModelProperty("CPU架构")
	private String cpuArch;
	private String cpuArchCode;

	@ApiModelProperty("CPU品牌")
	private String cpuBrand;
	private String cpuBrandCode;
	private String cpuBrandName;

	@ApiModelProperty("CPU主频")
	private String cpuClockSpeed;

	@ApiModelProperty("标准全称")
	private String fullName;

	@ApiModelProperty("采购方式")
	private String procureType;
	private String procureTypeCode;

	@ApiModelProperty("产权状态")
	private String ownerStatus;

	@ApiModelProperty("服务商")
	private String serviceName;

	@ApiModelProperty("售后服务到期时间")
	private String afterSaleExpDate;

	@ApiModelProperty("是否可报废")
	private String isScrap;
	private String isScrapCode;

	@ApiModelProperty("是否可用")
	private String isUse;
	private String isUseCode;

	@ApiModelProperty("所在位置")
	private String warehouseLocation;

	@ApiModelProperty("操作系统类型")
	private String OSType;
	private String OSTypeCode;

	@ApiModelProperty("主机名称")
	private String hostName;

	@ApiModelProperty("是否纳入云管")
	private String isCloudMange;

	@ApiModelProperty("CPU核数")
	private String cpuCoreSize;

	@ApiModelProperty("CPU主频(GHZ)")
	private String cpuFrequecy;

	@ApiModelProperty("CPU数量(颗)")
	private String cpuSize;

	@ApiModelProperty("CPU信息")
	private String cpuInfo;

	@ApiModelProperty("硬盘信息")
	private String hddInfor;

	@ApiModelProperty("硬盘数量(个)")
	private String hddNum;

	@ApiModelProperty("内存条数量(个)")
	private String memoryCardSize;

	@ApiModelProperty("虚拟内存大小(G)")
	private String virtMemerySize;

	@ApiModelProperty("存储RAID冗余方式")
	private String raidStorageType;

	@ApiModelProperty("HBA卡WWN")
	private String hbaCardWWN;

	@ApiModelProperty("HBA卡数(个)")
	private String hbaCardSize;

	@ApiModelProperty("光端口数量")
	private String opticalPortNum;

	@ApiModelProperty("网口类型")
	private String netPortType;
	private String netPortTypeCode;

	@ApiModelProperty("网口数量")
	private String netPortNum;

	@ApiModelProperty("网卡数(个)")
	private String networkCardSize;

	@ApiModelProperty("电源信息")
	private String powerInfo;

	@ApiModelProperty("云类型")
	private String cloudType;

	@ApiModelProperty("主机设备用途类型")
	private String serverUseToType;

	@ApiModelProperty("残值处理及资产更新方案")
	private String residualValueAssetRenewal;

	@ApiModelProperty("服务到期时间")
	private String serviceExpDate;

	@ApiModelProperty("用途")
	private String useTo;

	@ApiModelProperty("存储容量(T)")
	private String storageCapacity;

	@ApiModelProperty("额定功率(W)")
	private String ratedPower;

	@ApiModelProperty("网络设备用途类型")
	private String networkDeviceType;

	@ApiModelProperty("安全备案编号")
	private String securityFilingNo;

	@ApiModelProperty("设备等级")
	private String deviceLevel;

	@ApiModelProperty("中间件")
	private String middleware;

	@ApiModelProperty("服务商联系电话")
	private String serviceTel;

	@ApiModelProperty("服务商联系人")
	private String serviceContacts;

	@ApiModelProperty("服务开始日期")
	private String afterSaleBeginDate;

	@ApiModelProperty("服务级别")
	private String serviceLevel;
	private String serviceLevelCode;

	@ApiModelProperty("是否外单位设备")
	private String isExternalUnit;
	private String isExternalUnitCode;

	@ApiModelProperty("布线节点数(个)")
	private String wiringNodeNo;

	@ApiModelProperty("机柜容量(U)")
	private String cabinetCapacity;

	@ApiModelProperty("空调类型")
	private String airConditionType;

	@ApiModelProperty("匹数")
	private String horsepower;

	@ApiModelProperty("剩余容量")
	private String remainingCapacity;

	@ApiModelProperty("制冷量")
	private String coolCapacity;

	@ApiModelProperty("PDU额定功率")
	private String pduRatedPower;

	@ApiModelProperty("PDU运行功率")
	private String pduOperatePower;

	@ApiModelProperty("UPS容量（KVA）")
	private String upsCapacity;

	@ApiModelProperty("电池数（块）")
	private String batteryNum;

	@ApiModelProperty("电池组数（组）")
	private String batteryPackNum;

	@ApiModelProperty("电源负载")
	private String powerLoad;

	@ApiModelProperty("额定容量")
	private String ratedCapacity;

	@ApiModelProperty("功能描述")
	private String functionDes;

	@ApiModelProperty("可用容量")
	private String usableCapacity;

	@ApiModelProperty("所属UPS")
	private String belongUps;

	@ApiModelProperty("服务合同编号")
	private String contractNo;

	@ApiModelProperty("开发商联系方式")
	private String developerTel;

	@ApiModelProperty(value = "电端口数量")
	private String electricPortNum;

	@ApiModelProperty(value = "备品备件类型")
	private String sparePartsType;
	private String sparePartsTypeId;

	@ApiModelProperty(value = "使用人")
	private String user;

	@ApiModelProperty(value = "设备增加方式")
	private String deviceAddType;
	private String deviceAddTypeCode;

	@ApiModelProperty(value = "设备变动方式")
	private String deviceChangeType;
	private String deviceChangeTypeCode;

	@ApiModelProperty(value = "办理入库人员")
	private String entryWarehousePerson;

	@ApiModelProperty(value = "ERP转资状态")
	private String erpTransferStatus;

	@ApiModelProperty(value = "是否同步给ERP")
	private String isToErpCode;

	@ApiModelProperty(value = "是否同步I6000")
	private String isToI6000;

	@ApiModelProperty(value = "制造商")
	private String maker;
	private String makerCode;

	@ApiModelProperty(value = "设备高度")
	private String deviceHeight;

	@ApiModelProperty(value = "交换机标签")
	private String switchLabel;

	@ApiModelProperty(value = "是否治理")
	private String isGovern;

	@ApiModelProperty(value = "计量单位")
	private String measureUnit;
}
