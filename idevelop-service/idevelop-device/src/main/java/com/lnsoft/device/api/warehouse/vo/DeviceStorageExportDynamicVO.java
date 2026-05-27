package com.lnsoft.device.api.warehouse.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: DeviceStorageExportDynamicVO
 * @description:
 * @author: zhangs
 * @create: 2024-02-27 09:45
 **/
@Data
public class DeviceStorageExportDynamicVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ExcelIgnore
	private String exceptionField;

	@ExcelProperty(value = "品牌")
	private String brand;
	private String brandCode;
	@ExcelProperty(value = "系列")
	private String series;
	private String seriesCode;
	@ExcelProperty(value = "型号")
	private String deviceModel;
	private String deviceModelCode;
	@ExcelProperty(value = "是否信创设备")
	private String isITAI;

	@ExcelProperty(value = "硬盘类型")
	private String hardDiskType;

	@ExcelProperty(value = "硬盘容量(GB)")
	private String hardDiskCapability;

	@ExcelProperty(value = "内存大小(GB)")
	private String memSize;

	@ExcelProperty(value = "CPU架构")
	private String cpuArch;

	@ExcelProperty(value = "CPU品牌")
	private String cpuBrand;

	@ExcelProperty(value = "CPU型号")
	private String cpuModel;

	@ExcelProperty(value = "CPU主频")
	private String cpuClockSpeed;

	@ExcelProperty(value = "采购方式")
	private String procureType;

	@ExcelProperty(value = "产权状态")
	private String ownerStatus;

	@ExcelProperty(value = "出厂日期")
	private String factoryDate;

	@ExcelProperty(value = "出厂序列号")
	private String sn;

	@ExcelProperty(value = "净值")
	private String netWorth;

	@ExcelProperty(value = "资产原值")
	private String assetOriginal;

	@ExcelProperty(value = "服务商")
	private String serviceName;

	@ExcelProperty(value = "售后服务到期时间")
	private String afterSaleExpDate;

	@ExcelProperty(value = "是否可报废")
	private String isScrap;

	@ExcelProperty(value = "是否可用")
	private String isUse;

//	@ExcelProperty(value = "所在位置")
//	private String warehouseLocation;

	@ExcelProperty(value = "操作系统版本号")
	private String OSVersion;

	@ExcelProperty(value = "操作系统类型")
	private String OSType;

	@ExcelProperty(value = "备注")
	private String remark;

	@ExcelProperty(value = "主机名称")
	private String hostName;

	@ExcelProperty(value = "是否纳入云管")
	private String isCloudMange;

	@ExcelProperty(value = "CPU核数")
	private String cpuCoreSize;

	@ExcelProperty(value = "CPU主频(GHZ)")
	private String cpuFrequecy;

	@ExcelProperty(value = "CPU数量(颗)")
	private String cpuSize;

	@ExcelProperty(value = "CPU信息")
	private String cpuInfo;

	@ExcelProperty(value = "硬盘信息")
	private String hddInfor;

	@ExcelProperty(value = "硬盘数量(个)")
	private String hddNum;

	@ExcelProperty(value = "内存条数量(个)")
	private String memoryCardSize;

	@ExcelProperty(value = "虚拟内存大小(G)")
	private String virtMemerySize;

	@ExcelProperty(value = "存储RAID冗余方式")
	private String raidStorageType;

	@ExcelProperty(value = "HBA卡WWN")
	private String hbaCardWWN;

	@ExcelProperty(value = "HBA卡数(个)")
	private String hbaCardSize;

	@ExcelProperty(value = "光端口数量")
	private String opticalPortNum;

	@ExcelProperty(value = "网口类型")
	private String netPortType;

	@ExcelProperty(value = "网口数量")
	private String netPortNum;

	@ExcelProperty(value = "网卡数(个)")
	private String networkCardSize;

	@ExcelProperty(value = "电源信息")
	private String powerInfo;

	@ExcelProperty(value = "电源模块(个)")
	private String powerModel;

	@ExcelProperty(value = "云类型")
	private String cloudType;

	@ExcelProperty(value = "主机设备用途类型")
	private String serverUseToType;

	@ExcelProperty(value = "残值处理及资产更新方案")
	private String residualValueAssetRenewal;

	@ExcelProperty(value = "服务到期时间")
	private String serviceExpDate;

	@ExcelProperty(value = "用途")
	private String useTo;

	@ExcelProperty(value = "存储容量(T)")
	private String storageCapacity;

	@ExcelProperty(value = "额定功率(W)")
	private String ratedPower;

	@ExcelProperty(value = "网络设备用途类型")
	private String networkDeviceType;

	@ExcelProperty(value = "安全备案编号")
	private String securityFilingNo;

	@ExcelProperty(value = "设备等级")
	private String deviceLevel;

	@ExcelProperty(value = "中间件")
	private String middleware;

	@ExcelProperty(value = "服务商联系电话")
	private String serviceTel;

	@ExcelProperty(value = "服务商联系人")
	private String serviceContacts;

	@ExcelProperty(value = "服务开始日期")
	private String afterSaleBeginDate;

	@ExcelProperty(value = "服务级别")
	private String serviceLevel;

	@ExcelProperty(value = "是否外单位设备")
	private String isExternalUnit;

	@ExcelProperty(value = "布线节点数(个)")
	private String wiringNodeNo;

	@ExcelProperty(value = "机柜容量(U)")
	private String cabinetCapacity;

	@ExcelProperty(value = "空调类型")
	private String airConditionType;

	@ExcelProperty(value = "匹数")
	private String horsepower;

	@ExcelProperty(value = "剩余容量")
	private String remainingCapacity;

	@ExcelProperty(value = "制冷量")
	private String coolCapacity;

	@ExcelProperty(value = "PDU额定功率")
	private String pduRatedPower;

	@ExcelProperty(value = "PDU运行功率")
	private String pduOperatePower;

	@ExcelProperty(value = "UPS容量（KVA）")
	private String upsCapacity;

	@ExcelProperty(value = "电池数（块）")
	private String batteryNum;

	@ExcelProperty(value = "电池组数（组）")
	private String batteryPackNum;

	@ExcelProperty(value = "电源负载")
	private String powerLoad;

	@ExcelProperty(value = "额定容量")
	private String ratedCapacity;

	@ExcelProperty(value = "功能描述")
	private String functionDes;

	@ExcelProperty(value = "可用容量")
	private String usableCapacity;

	@ExcelProperty(value = "所属UPS")
	private String belongUps;

	@ExcelProperty(value = "服务合同编号")
	private String contractNo;

	@ExcelProperty(value = "开发商联系方式")
	private String developerTel;

	@ExcelProperty(value = "电端口数量")
	private String electricPortNum;

	@ExcelProperty(value = "备品备件类型")
	private String sparePartsType;

//	@ExcelProperty(value = "功能位置")
//	private String funLocation;

	@ExcelProperty(value = "制造商")
	private String maker;
	private String makerCode;
	@ExcelProperty(value = "设备高度")
	private String deviceHeight;

	@ExcelProperty(value = "设备增加方式")
	private String deviceAddType;
}
