package com.lnsoft.device.api.cmdb.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * cmbd数据实体类
 */
@Data
public class CmdbAttrConstantVO {
	/** UUID  */
	//public static final String  UUID
	private static  String  UUID;
	/** ID  */
	//public static final String  ID
	private static  String  ID;
	/** 模型ID  */
	//public static final String  CI_ID
	private static  String  CI_ID;

	/** 区域  */
	//public static final String  AREA
	private static  String  area;
	/** 部门  */
	//public static final String  DEPT
	private static  String  dept;
	/** 设备名称  */
	//public static final String  DEVICE_NAME
	private static  String  deviceName;
	/** 设备编码  */
	//public static final String  DEVICE_CODE
	private static  String  deviceCode;
	/** 品牌  */
	//public static final String  BRAND
	private static  String  brand;
	/** 系列  */
	//public static final String  SERIES
	private static  String  series;
	/** 型号  */
	//public static final String  DEVICE_MODEL
	private static  String  deviceModel;
	/** 品牌编码  */
	//public static final String  BRAND_CODE
	private static  String  brandCode;
	/** 型号编码  */
	//public static final String  DEVICE_MODEL_CODE
	private static  String  deviceModelCode;
	/** 系列编码  */
	//public static final String  SERIES_CODE
	private static  String  seriesCode;

	/** 设备分类名称  */
	//public static final String  DEVICE_CATEGORY
	private static  String  deviceCategory;
	/** 设备分类  */
	//public static final String  DEVICE_CATEGORY_CODE
	private static  String  deviceCategoryCode;

	/** 设备类型名称  */
	//public static final String  DEVICE_TYPE
	private static  String  deviceType;
	/** 设备类型  */
	//public static final String  DEVICE_TYPE_CODE
	private static  String  deviceTypeCode;

	/** 设备状态名称  */
	//public static final String  DEVICE_STATUS
	private static  String  deviceStatus;
	/** 设备状态  */
	//public static final String  DEVICE_STATUS_CODE
	private static  String  deviceStatusCode;

	/** 制造商  */
	//public static final String  MAKER
	private static  String  maker;
	/** 制造商编码  */
	//public static final String  MAKER_CODE
	private static  String  makerCode;

	/** 制造国家与地区  */
	//public static final String  MAINTENANCE_COUNTRY
	private static  String  maintenanceCountry;

	/** 出厂序列号  */
	//public static final String  SN
	private static  String  sn;
	/** IP地址  */
	//public static final String  IP
	private static  String  IP;
	/** MAC地址  */
	//public static final String  MAC
	private static  String  MAC;
	/** 投运日期  */
	//public static final String  OPRT_DATE
	private static  String  oprtDate;
	/** 首次投运日期  */
	//public static final String  OPRT_DATE_FIRST
	private static  String  oprtDateFirst;
	/** 投运年限  */
	//public static final String  USE_AGE
	private static  String  useAge;

	/** 所在仓库  */
	//public static final String  IN_WAREHOUSE
	private static  String  inWarehouse;
	/** 是否同步给ERP  */
	//public static final String  IS_TO_ERP_CODE
	private static  String  isToErpCode;
	/** 是否同步I6000  */
	//public static final String  IS_TO_I6000
	private static  String  isToI6000;
	/** 用途  */
	//public static final String  USE_TO
	private static  String  useTo;
	/** 国网编号  */
	//public static final String  STATE_GRID_NO
	private static  String  stateGridNo;
	/** ERP转资状态  */
	//public static final String  ERP_TRANSFER_STATUS
	private static  String  erpTransferStatus;
	/** 所属网络  */
	//public static final String  NET_WORK_CODE
	private static  String  netWorkCode;
	/** 盘点日期  */
	//public static final String  INVENTORY_DATE
	private static  String  inventoryDate;
	/** 安装地点  */
	//public static final String  INSTALLATION_SITE
	private static  String  installationSite;

	/** 采购方式  */
	//public static final String  PROCURE_TYPE_CODE
	private static  String  procureTypeCode;

	/** 设备增加方式编码  */
	//public static final String  DEVICE_ADD_TYPE_CODE
	private static  String  deviceAddTypeCode;
	/** 设备增加方式  */
	//public static final String  DEVICE_ADD_TYPE
	private static  String  deviceAddType;

	/** 设备变动方式  */
	//public static final String  DEVICE_CHANGE_TYPE
	private static  String  deviceChangeType;
	/** 设备变动方式编码  */
	//public static final String  DEVICE_CHANGE_TYPE_CODE
	private static  String  deviceChangeTypeCode;

	/** 资产原值  */
	//public static final String  ASSET_ORIGINAL
	private static  String  assetOriginal;
	/** 项目定义  */
	//public static final String  PROJECT_DEFINE
	private static  String  projectDefine;
	/** 项目名称  */
	//public static final String  PROJECT_NAME
	private static  String  projectName;
	/** 项目编号  */
	//public static final String  PROJECT_CODE
	private static  String  projectCode;

	/** WBS项目  */
	//public static final String  WBS_ELEMENT_NAME
	private static  String  wbsElementName;
	/** WBS元素  */
	//public static final String  WBS_ELEMENT
	private static  String  wbsElement;
	/** 采购合同编号  */
	//public static final String  PROCURE_CONTRACT_NO
	private static  String  procureContractNo;
	/** 产权单位  */
	//public static final String  OWNER_UNIT
	private static  String  ownerUnit;
	/** 维护工厂  */
	//public static final String  MAINTENANCE_FACTORY
	private static  String  maintenanceFactory;
	/** 维护工厂编码  */
	//public static final String  MAINTENANCE_FACTORY_CODE
	private static  String  maintenanceFactoryCode;

	/** 采购日期  */
	//public static final String  PROCURE_DATE
	private static  String  procureDate;
	/** 出厂日期  */
	//public static final String  FACTORY_DATE
	private static  String  factoryDate;

	/** 固定资产号  */
	//public static final String  FIXED_ASSET_NO
	private static  String  fixedAssetNo;

	/** 安全管理部门  */
	//public static final String  SECURITY_MANAGE_DEPT
	private static  String  securityManageDept;
	/** 安全管理部门编码  */
	//public static final String  SECURITY_MANAGE_DEPT_CODE
	private static  String  securityManageDeptCode;
	/** 督查部门编码  */
	//public static final String  SUPERVISION_DEPT_CODE
	private static  String  supervisionDeptCode;
	/** 督查部门  */
	//public static final String  SUPERVISION_DEPT
	private static  String  supervisionDept;

	/** 数据库  */
	//public static final String  DATABASE
	private static  String  database;
	/** 中间件  */
	//public static final String  MIDDLEWARE
	private static  String  middleware;

	/** 是否进行漏洞扫描  */
	//public static final String  IS_LOOPHOLE_SCAN
	private static  String  isLoopholeScan;
	/** 漏洞扫描时间  */
	//public static final String  VIRUS_SACN_TIME
	private static  String  virusSacnTime;
	/** 是否进行安全加固  */
	//public static final String  IS_SECURITY_REINFORCE
	private static  String  isSecurityReinforce;
	/** 安全加固时间  */
	//public static final String  SECURITY_CHECK_TIME
	private static  String  securityCheckTime;

	/** 病毒库更新时间  */
	//public static final String  ANTI_VIRUS_LIBRARY
	private static  String  antiVirusLibrary;
	/** 病毒防护系统版本  */
	//public static final String  ANTI_VIRUS_MODEL
	private static  String  antiVirusModel;
	/** 病毒防护系统产品  */
	//public static final String  ANTI_VIRUS_PRODUCT
	private static  String  antiVirusProduct;

	/** 是否热备  */
	//public static final String  IS_HOT_BACKUP
	private static  String  isHotBackup;
	/** 日志是否转储  */
	//public static final String  IS_LOG_DUMP
	private static  String  isLogDump;
	/** 是否安全配置检查  */
	//public static final String  IS_SECURITY_CONFIG
	private static  String  isSecurityConfig;
	/** 安全配置检查时间  */
	//public static final String  SECURITY_CONFIG_CHECK_TIME
	private static  String  securityConfigCheckTime;

	/** 所属安全边界  */
	//public static final String  SECURITY_BOUNDARY
	private static  String  securityBoundary;
	/** 监测平台  */
	//public static final String  MONITOR_SYSTEM
	private static  String  monitorSystem;
	/** 安全设备功能  */
	//public static final String  SECURITY_DEVICE_FUN
	private static  String  securityDeviceFun;
	/** 安全运维人员  */
	//public static final String  SECURITY_OPERATION_PERSON
	private static  String  securityOperationPerson;

	/** 是否严格策略  */
	//public static final String  IS_STRICT_STRATEGY
	private static  String  isStrictStrategy;
	/** 纳入IMS监控  */
	//public static final String  IMS_MONITOR
	private static  String  imsMonitor;

	/** 售后服务到期时间  */
	//public static final String  AFTER_SALE_EXP_DATE
	private static  String  afterSaleExpDate;
	/** 服务合同编号  */
	//public static final String  CONTRACT_NO
	private static  String  contractNo;
	/** 服务级别  */
	//public static final String  SERVICE_LEVEL
	private static  String  serviceLevel;
	/** 服务级别编码  */
	//public static final String  SERVICE_LEVEL_CODE
	private static  String  serviceLevelCode;

	/** 服务到期时间  */
	//public static final String  SERVICE_EXP_DATE
	private static  String  serviceExpDate;
	/** 服务开始日期  */
	//public static final String  AFTER_SALE_BEGIN_DATE
	private static  String  afterSaleBeginDate;
	/** 服务商  */
	//public static final String  SERVICE_NAME
	private static  String  serviceName;
	/** 服务商联系电话  */
	//public static final String  SERVICE_TEL
	private static  String  serviceTel;
	/** 服务商联系人  */
	//public static final String  SERVICE_CONTACTS
	private static  String  serviceContacts;
	/** 服务商名称  */
	//public static final String  SERVICE_FULL_NAME
	private static  String  serviceFullName;

	/** 实物管理部门编码  */
	//public static final String  REAL_MANAGE_DEPT
	private static  String  realManageDept;
	/** 实物管理部门  */
	//public static final String  ENTITY_MANAGEMENT_DEPT_NAME
	private static  String  entityManagementDeptName;
	/** 使用保管部门编码  */
	//public static final String  USE_KEEP_DEPT
	private static  String  useKeepDept;
	/** 使用保管部门  */
	//public static final String  USE_KEEP_DEPT_NAME
	private static  String  useKeepDeptName;

	/** 领用责任人  */
	//public static final String  RECEIVING_PERSON
	private static  String  receivingPerson;
	/** 领用责任人联系方式  */
	//public static final String  RECEIVING_TEL
	private static  String  receivingTel;
	/** 领用责任人身份证  */
	//public static final String  RECEIVING_I_D_CARD
	private static  String  receivingIDCard;
	/** 领用责任人班组  */
	//public static final String  RECEIVING_GROUP
	private static  String  receivingGroup;
	/** 领用责任人手机号  */
	//public static final String  RECEIVING_PHONE_NUMBER
	private static  String  receivingPhoneNumber;
	/** 领用责任人统一权限账号  */
	//public static final String  RECEIVE_PERSON_UNIFIED_ACC
	private static  String  receivePersonUnifiedAcc;

	/** 领用单位  */
	//public static final String  RECEIVE_UNIT
	private static  String  receiveUnit;
	/** 领用单位编码  */
	//public static final String  RECEIVE_UNIT_CODE
	private static  String  receiveUnitCode;
	/** 领用部门  */
	//public static final String  RECEIVE_DEPT
	private static  String  receiveDept;
	/** 领用部门编码  */
	//public static final String  RECEIVE_DEPT_CODE
	private static  String  receiveDeptCode;


	/** 领用日期  */
	//public static final String  RECEIVING_DATE
	private static  String  receivingDate;

	/** 运维部门  */
	//public static final String  OPERATION_DEPT
	private static  String  operationDept;
	/** 运维部门编码  */
	//public static final String  OPERATION_DEP_CODE
	private static  String  operationDepCode;
	/** 运维单位  */
	//public static final String  OPERATION_UNIT
	private static  String  operationUnit;
	/** 运维单位编码  */
	//public static final String  OPERATION_UNIT_CODE
	private static  String  operationUnitCode;
	/** 运维联系电话  */
	//public static final String  OPERATION_TEL
	private static  String  operationTel;
	/** 运维责任人  */
	//public static final String  OPERATION_PERSON
	private static  String  operationPerson;
	/** 运维责任人账号  */
	//public static final String  OPERATION_CHAGE_ACCOUNT
	private static  String  operationChageAccount;


	/** 线站标识  */
	//public static final String  LINE_STATION
	private static  String  lineStation;
	/** 线站标识名称  */
	//public static final String  LINE_STATION_SIGN
	private static  String  lineStationSign;

	/** 使用人  */
	//public static final String  USER
	private static  String  user;
	/** 设备使用人班组  */
	//public static final String  DEVICE_USER_TEAM
	private static  String  deviceUserTeam;
	/** 使用人联系电话  */
	//public static final String  USER_TEL
	private static  String  userTel;
	/** 设备使用人身份证  */
	//public static final String  DEVICE_USER_I_D_CARD
	private static  String  deviceUserIDCard;
	/** 使用人联系邮箱  */
	//public static final String  USER_EMAIL
	private static  String  userEmail;
	/** 设备使用部门  */
	//public static final String  DEVICE_USE_DEPT
	private static  String  deviceUseDept;


	/** 操作系统类型  */
	//public static final String  OS_TYPE_CODE
	private static  String  OSTypeCode;
	/** 操作系统发行版本  */
	//public static final String  OS_ISSUE_VERSION
	private static  String  OSIssueVersion;
	/** 部署应用系统  */
	//public static final String  DEPLOYMENT_SYSTEM
	private static  String  deploymentSystem;

	/** 硬盘类型  */
	//public static final String  HARD_DISK_TYPE_CODE
	private static  String  hardDiskTypeCode;
	/** 硬盘总容量(GB)  */
	//public static final String  HARD_DISK_CAPABILITY
	private static  String  hardDiskCapability;

	/** 硬盘尺寸  */
	//public static final String  HDD_SIZE
	private static  String  hddSize;
	/** 硬盘数量  */
	//public static final String  HDD_NUM
	private static  String  hddNum;
	/** 挂接存储  */
	//public static final String  ATTACH_STORAGE
	private static  String  attachStorage;
	/** 总内存大小(GB)  */
	//public static final String  MEM_SIZE
	private static  String  memSize;
	/** CPU型号  */
	//public static final String  CPU_MODEL
	private static  String  cpuModel;
	/** CPU主频  */
	//public static final String  CPU_CLOCK_SPEED
	private static  String  cpuClockSpeed;
	/** CPU核数  */
	//public static final String  CPU_CORE_SIZE
	private static  String  cpuCoreSize;
	/** 单CPU核数  */
	//public static final String  ALONE_C_P_U_CORE_SIZE
	private static  String  aloneCPUCoreSize;
	/** HBA卡数  */
	//public static final String  HBA_CARD_SIZE
	private static  String  hbaCardSize;
	/** RAID方式  */
	//public static final String  RAID_TYPE
	private static  String  raidType;
	/** 电源模块(个)  */
	//public static final String  POWER_MODEL
	private static  String  powerModel;
	/** 额定功率(W)  */
	//public static final String  RATED_POWER
	private static  String  ratedPower;
	/** 应用类型  */
	//public static final String  APPLICATION_TYPE
	private static  String  applicationType;
	/** 总插槽数（个）  */
	//public static final String  SLOT_NUM
	private static  String  slotNum;
	/** 存储总容量（T）  */
	//public static final String  STORAGE_CAPACITY_ALL
	private static  String  storageCapacityAll;
	/** 硬盘信息  */
	//public static final String  HDD_INFOR
	private static  String  hddInfor;
	/** 控制器数  */
	//public static final String  CONTROLLER_NUM
	private static  String  controllerNum;
	/** 吞吐量(GB/S)  */
	//public static final String  THROUGHPUT
	private static  String  throughput;
	/** 配置说明  */
	//public static final String  CONFIG_DESCRIPTION
	private static  String  configDescription;
	/** UPS容量（KVA）  */
	//public static final String  UPS_CAPACITY
	private static  String  upsCapacity;
	/** 电池组数（组）  */
	//public static final String  BATTERY_PACK_NUM
	private static  String  batteryPackNum;
	/** 电池数（块）  */
	//public static final String  BATTERY_NUM
	private static  String  batteryNum;
	/** 布线节点数（个）  */
	//public static final String  WIRING_NODE_NO
	private static  String  wiringNodeNo;

	/** CPU信息  */
	//public static final String  CPU_INFO
	private static  String  cpuInfo;
	/** 内存信息  */
	//public static final String  MEMORY_INFOR
	private static  String  memoryInfor;
	/** 主板信息  */
	//public static final String  BOARD_INFOS
	private static  String  boardInfos;

	/** 显卡型号  */
	//public static final String  GRAPHIC_CARD_MODEL
	private static  String  graphicCardModel;

	/** 设备来源名称  */
	//public static final String  DEVICE_SOURCE
	private static  String  deviceSource;
	/** 设备来源  */
	//public static final String  DEVICE_SOURCE_CODE
	private static  String  deviceSourceCode;

	/** 备注  */
	//public static final String  REMARK
	private static  String  remark;





	/** 网卡信息  */
	//public static final String  NETWORK_INFO
	private static  String  networkInfo;
	/** 网卡数(个)  */
	//public static final String  NETWORK_CARD_SIZE
	private static  String  networkCardSize;


	/** CPU品牌  */
	//public static final String  CPU_BRAND
	private static  String  cpuBrand;
	/** CPU数量(颗)  */
	//public static final String  CPU_SIZE
	private static  String  cpuSize;

	/** 内存代次  */
	//public static final String  MEMORY_LEVEL
	private static  String  memoryLevel;
	/** ERP设备台账编码  */
	//public static final String  DEVICE_CODE_ERP
	private static  String  deviceCodeErp;
	/** ERP资产编码  */
	//public static final String  ASSET_CODE_ERP
	private static  String  assetCodeErp;
	/** 入库日期  */
	//public static final String  IN_WAREHOUSE_DATE
	private static  String  inWarehouseDate;
	/** 所在仓库编码  */
	//public static final String  IN_WAREHOUSE_CODE
	private static  String  inWarehouseCode;
	/** 转资剩余天数  */
	//public static final String  TRANSFER_DATE
	private static  String  transferDate;

	/** 运行单位  */
	//public static final String  OPRT_DEPT
	private static  String  oprtDept;
	/** 设备报废时间  */
	//public static final String  SCRAP_DATE
	private static  String  scrapDate;

	/** 其他配件  */
	//public static final String  OTHER_PARTS
	private static  String  otherParts;
	/** 光驱型号  */
	//public static final String  CD_DRIVE_MODEL
	private static  String  cdDriveModel;
	/** 简称  */
	//public static final String  SHORT_NAME
	private static  String  shortName;
	/** 设备高度  */
	//public static final String  DEVICE_HEIGHT
	private static  String  deviceHeight;
	/** 设备腾退结果  */
	//public static final String  DEVICE_RETURN
	private static  String  deviceReturn;
	/** 实物ID  */
	//public static final String  REAL_ID
	private static  String  realID;
	/** 数据来源  */
	//public static final String  DATA_SOURCE
	private static  String  dataSource;
	/** 所属应用实例访问地址  */
	//public static final String  SYSTEM_CASE_PATH
	private static  String  systemCasePath;
	/** 业务建模标识  */
	//public static final String  CI_NAME
	private static  String  ciName;


	/** IPV4地址  */
	//public static final String  IPV4_PATH
	private static  String  ipv4Path;
	/** IPV6地址  */
	//public static final String  IPV6_PATH
	private static  String  ipv6Path;
	/** 故障次数  */
	//public static final String  FAULT_SIZE
	private static  String  faultSize;
	/** 设备等级  */
	//public static final String  DEVICE_LEVEL
	private static  String  deviceLevel;
	/** 所属应用系统  */
	//public static final String  SYSTEM_NAME
	private static  String  systemName;

	/** 设备起始高度(U)  */
	//public static final String  DEVICE_HEIGHT_BEGIN
	private static  String  deviceHeightBegin;
	/** 设备终止高度(U)  */
	//public static final String  DEVICE_HEIGHT_END
	private static  String  deviceHeightEnd;
	/** 机架编号  */
	//public static final String  RACK_CODE
	private static  String  rackCode;
	/** 机架  */
	//public static final String  RACK
	private static  String  rack;
	/** 电压等级  */
	//public static final String  VOLTAGE_LEVEL
	private static  String  voltageLevel;
	/** 机房编号  */
	//public static final String  COMPUTER_ROOM_CODE
	private static  String  computerRoomCode;
	/** 机房  */
	//public static final String  COMPUTER_ROOM
	private static  String  computerRoom;
	/** 机柜编号  */
	//public static final String  CABINET_CODE
	private static  String  cabinetCode;
	/** 机柜  */
	//public static final String  CABINET
	private static  String  cabinet;
	/** 设备转资失败原因  */
	//public static final String  DEVICE_STATE_ERROR
	private static  String  deviceStateError;
	/** 报废比例  */
	//public static final String  SCRAP_RATIO
	private static  String  scrapRatio;
	/** 残值处理及资产更新方案  */
	//public static final String  RESIDUAL_VALUE_ASSET_RENEWAL
	private static  String  residualValueAssetRenewal;

	/** 净值  */
	//public static final String  NET_WORTH
	private static  String  netWorth;
	/** 来源说明  */
	//public static final String  SOURCE_REMARK
	private static  String  sourceRemark;
	/** 退役存储地点  */
	//public static final String  RETIRE_LOCATION
	private static  String  retireLocation;
	/** 退役时间  */
	//public static final String  RETIRE_DATE
	private static  String  retireDate;
	/** 产权部门  */
	//public static final String  PROPERTY_DEPT
	private static  String  propertyDept;
	/** 产权部门编码  */
	//public static final String  PROPERTY_DEPT_CODE
	private static  String  propertyDeptCode;
	/** 工厂区域  */
	//public static final String  FACTORY_AREA
	private static  String  factoryArea;
	/** 功能位置  */
	//public static final String  FUN_LOCATION
	private static  String  funLocation;
	/** 功能位置编码  */
	//public static final String  FUN_LOCATION_CODE
	private static  String  funLocationCode;

	/** 供应商  */
	//public static final String  SUPPLIER_NAME
	private static  String  supplierName;
	/** 供应商联系电话  */
	//public static final String  SUPPLIER_TEL
	private static  String  supplierTel;
	/** 供应商联系人  */
	//public static final String  SUPPLIER_CONTACTS
	private static  String  supplierContacts;
	/** 供应商名称  */
	//public static final String  SUPPLIER_FULL_NAME
	private static  String  supplierFullName;
	/** 开发厂商  */
	//public static final String  DEVELOPER_NAME
	private static  String  developerName;
	/** 开发厂商名称  */
	//public static final String  DEVELOPER_FULL_NAME
	private static  String  developerFullName;
	/** 开发商联系方式  */
	//public static final String  DEVELOPER_TEL
	private static  String  developerTel;
	/** 开发商联系人  */
	//public static final String  DEVELOPER_PERSON
	private static  String  developerPerson;

	/** 检修部门  */
	//public static final String  OVERHAUL_DEPT
	private static  String  overhaulDept;
	/** 检修单位  */
	//public static final String  OVERHAUL_UNIT
	private static  String  overhaulUnit;
	/** 检修联系电话  */
	//public static final String  OVERHAUL_TEL
	private static  String  overhaulTel;
	/** 检修责任人  */
	//public static final String  OVERHAUL_PERSON
	private static  String  overhaulPerson;


	// 办理入库人员  */
	//public static final String  ENTRY_WAREHOUSE_PERSON
	private static  String  entryWarehousePerson;
	/** 出库日期  */
	//public static final String  OUT_WAREHOUSE_DATE
	private static  String  outWarehouseDate;
	/** 所在位置  */
	//public static final String  WAREHOUSE_LOCATION
	private static  String  warehouseLocation;
	/** 办理出库人员  */
	//public static final String  OUT_WAREHOUSE_PERSON
	private static  String  outWarehousePerson;
	/** 安全备案编号  */
	//public static final String  SECURITY_FILING_NO
	private static  String  securityFilingNo;


	/** 资产安全号  */
	//public static final String  ASSET_SECURITY_NO
	private static  String  assetSecurityNo;
	/** 备案编号  */
	//public static final String  FILING_NO
	private static  String  filingNo;
	/** 具有相互冗余设备  */
	//public static final String  REDUNDANCE
	private static  String  redundance;
	/** 屏蔽措施  */
	//public static final String  SHUELDING_PROTECTION
	private static  String  shueldingProtection;
	/** 上联链路数量  */
	//public static final String  UPLINK_NUM
	private static  String  uplinkNum;
	/** 数据备份保留期限  */
	//public static final String  DATA_BACKUP_RETENTION_PERIOD
	private static  String  dataBackupRetentionPeriod;
	/** 完全备份周期  */
	//public static final String  FUN_BACKUP_PERIOD
	private static  String  funBackupPeriod;
	/** 引擎配置数量  */
	//public static final String  ENGINE_CONFIGURATION_NUM
	private static  String  engineConfigurationNum;
	/** 增量备份次数(次)  */
	//public static final String  ADD_BACKUP_SIZE
	private static  String  addBackupSize;
	/** 电端口数量  */
	//public static final String  ELECTRIC_PORT_NUM
	private static  String  electricPortNum;
	/** 光端口数量  */
	//public static final String  OPTICAL_PORT_NUM
	private static  String  opticalPortNum;
	/** 备用名称  */
	//public static final String  RESERVE
	private static  String  reserve;
	/** 运行单位编码  */
	//public static final String  OPRT_DEPT_CODE
	private static  String  oprtDeptCode;
	/** 机柜容量（U）  */
	//public static final String  CABINET_CAPACITY
	private static  String  cabinetCapacity;
	/** 匹数  */
	//public static final String  HORSEPOWER
	private static  String  horsepower;
	/** 剩余容量  */
	//public static final String  REMAINING_CAPACITY
	private static  String  remainingCapacity;
	/** 制冷量（匹）  */
	//public static final String  COOL_CAPACITY
	private static  String  coolCapacity;
	/** CPU主频(GHZ)  */
	//public static final String  CPU_FREQUECY
	private static  String  cpuFrequecy;
	/** 系统信息  */
	//public static final String  SYSTEM_INFO
	private static  String  systemInfo;
	/** 小型机种类  */
	//public static final String  MINI_COMPUTER_TYPE
	private static  String  miniComputerType;
	/** 内存条数量(个)  */
	//public static final String  MEMORY_CARD_SIZE
	private static  String  memoryCardSize;
	/** 内存主频  */
	//public static final String  MEMORY_FREQUECY
	private static  String  memoryFrequecy;
	/** 主机名称  */
	//public static final String  HOST_NAME
	private static  String  hostName;
	/** 分辨率  */
	//public static final String  RESOLUTION
	private static  String  resolution;
	/** 幅面大小  */
	//public static final String  FORMAT_SIZE
	private static  String  formatSize;
	/** 型号规格  */
	//public static final String  MODEL_SPECIFICATIONS
	private static  String  modelSpecifications;
	/** 存储容量(T)  */
	//public static final String  STORAGE_CAPACITY
	private static  String  storageCapacity;
	/** 电源信息  */
	//public static final String  POWER_INFO
	private static  String  powerInfo;
	/** 所属虚拟化平台  */
	//public static final String  VIRTUALIZATION_PLATFORM
	private static  String  virtualizationPlatform;
	/** 总带宽  */
	//public static final String  TOTAL_BROADBAND
	private static  String  totalBroadband;
	/** 最大并发会话数(个)  */
	//public static final String  MAX_SESSION_NUM
	private static  String  maxSessionNum;
	/** 机柜号  */
	//public static final String  CABINET_NO
	private static  String  cabinetNo;
	/** 管理用户  */
	//public static final String  MANAGE_USERS
	private static  String  manageUsers;
	/** 管理密码  */
	//public static final String  MANAGE_PASSWORD
	private static  String  managePassword;
	/** 交换机密码  */
	//public static final String  SWITCH_PASSWORD
	private static  String  switchPassword;
	/** SNMP读字符串  */
	//public static final String  SNMP_READ_STRING
	private static  String  snmpReadString;
	/** SNMP写字符串  */
	//public static final String  SNMP_WRITE_STRING
	private static  String  snmpWriteString;
	/** SNMP版本号  */
	//public static final String  SNMP_VERSION
	private static  String  snmpVersion;
	/** 入网方式  */
	//public static final String  NETWORK_ACCESS_METHOD
	private static  String  networkAccessMethod;
	/** 工作VLAN号  */
	//public static final String  WORK_VLAN
	private static  String  workVlan;
	/** 交换机标签  */
	//public static final String  SWITCH_LABEL
	private static  String  switchLabel;
	/** 配置密码  */
	//public static final String  CONFIG_PASSWORD
	private static  String  configPassword;
	/** PDU额定功率  */
	//public static final String  PDU_RATED_POWER
	private static  String  pduRatedPower;
	/** PDU运行功率  */
	//public static final String  PDU_OPERATE_POWER
	private static  String  pduOperatePower;
	/** 额定容量  */
	//public static final String  RATED_CAPACITY
	private static  String  ratedCapacity;
	/** 可用容量  */
	//public static final String  USABLE_CAPACITY
	private static  String  usableCapacity;
	/** 所属UPS  */
	//public static final String  BELONG_UPS
	private static  String  belongUps;
	/** 电源负载  */
	//public static final String  POWER_LOAD
	private static  String  powerLoad;
	/** CPU平均利用率  */
	//public static final String  CPU_USE_RATIO
	private static  String  cpuUseRatio;
	/** CPU总线程数  */
	//public static final String  CPU_THREAD_SIZE
	private static  String  cpuThreadSize;
	/** HBA卡WWN  */
	//public static final String  HBA_CARD_W_W_N
	private static  String  hbaCardWWN;
	/** IPMI_IPV4地址  */
	//public static final String  IPMI_IPV4_PATH
	private static  String  ipmiIpv4Path;
	/** IPMI_IPV6_ADDR地址  */
	//public static final String  IPMI_IPV6_ADDR
	private static  String  ipmiIpv6Addr;
	/** IPMI地址  */
	//public static final String  IPMI_ADDR
	private static  String  ipmiAddr;
	/** Swap内存大小  */
	//public static final String  MEMERY_SWAP_SIZE
	private static  String  memerySwapSize;
	/** 操作系统服务器名称  */
	//public static final String  OS_SERVER_NAME
	private static  String  OSServerName;
	/** 操作系统位数  */
	//public static final String  OS_BITS
	private static  String  OSBits;
	/** 操作系统信息  */
	//public static final String  OS_INFO
	private static  String  OSInfo;
	/** 磁盘分区大小  */
	//public static final String  HARD_DISK_PARTION_SIZE
	private static  String  hardDiskPartionSize;
	/** 发行版本  */
	//public static final String  ISSUE_VERSION
	private static  String  issueVersion;
	/** 告警状态  */
	//public static final String  WARNING_STATUS
	private static  String  warningStatus;
	/** 管理IPv4地址  */
	//public static final String  MANAGE_IPV4_PATH
	private static  String  manageIpv4Path;
	/** 接口数量  */
	//public static final String  INTERFACE_NUM
	private static  String  interfaceNum;
	/** 空闲端口数量  */
	//public static final String  FREE_INTERFACE_NUM
	private static  String  freeInterfaceNum;
	/** 内存平均利用率  */
	//public static final String  MEMERY_USE_RATIO
	private static  String  memeryUseRatio;
	/** 设备路数  */
	//public static final String  DEVICE_LINE
	private static  String  deviceLine;
	/** 网络端口带宽  */
	//public static final String  NETWORK_BROADBAND
	private static  String  networkBroadband;
	/** 文件系统大小  */
	//public static final String  FILE_SYSTEM_SIZE
	private static  String  fileSystemSize;
	/** 虚拟内存大小(G)  */
	//public static final String  VIRT_MEMERY_SIZE
	private static  String  virtMemerySize;
	/** 硬盘平均利用率  */
	//public static final String  HARD_DISK_USE_RATIO
	private static  String  hardDiskUseRatio;
	/** 用户数  */
	//public static final String  USER_NUM
	private static  String  userNum;
	/** 前端通道端口类型  */
	//public static final String  FRONT_CHANNEL_PORT_TYPE
	private static  String  frontChannelPortType;

	/** 存储空间平均利用率  */
	//public static final String  STORAGE_USE_RATIO
	private static  String  storageUseRatio;
	/** WWN号  */
	//public static final String  WWN_NO
	private static  String  wwnNo;
	/** 额定电压  */
	//public static final String  RATED_VOLTAGE
	private static  String  ratedVoltage;
	/** 容量  */
	//public static final String  CAPACITY
	private static  String  capacity;
	/** 备品备件类型ID  */
	//public static final String  SPARE_PARTS_TYPE_ID
	private static  String  sparePartsTypeId;
	/** 插槽号  */
	//public static final String  SLOT_NO
	private static  String  slotNo;
	/** 供电功率  */
	//public static final String  SUPPLY_POWER
	private static  String  supplyPower;
	/** 电压  */
	//public static final String  VOLTAGE
	private static  String  voltage;
	/** 采购订单号  */
	//public static final String  PROCURE_ORDER_NO
	private static  String  procureOrderNo;
	/** 制造商联系电话  */
	//public static final String  MAINTENANCE_TEL
	private static  String  maintenanceTel;
	/** 售后服务期限  */
	//public static final String  AFER_SALE_SERVICE_PERIOD
	private static  String  aferSaleServicePeriod;
	/** 备品备件类型  */
	//public static final String  SPARE_PARTS_TYPE
	private static  String  sparePartsType;
	/** 运维责任人账号名称  */
	//public static final String  OPERATION_CHAGE_ACCOUN_NAME
	private static  String  operationChageAccounName;
	/** 设备责任人联系方式  */
	//public static final String  DEVICE_CHARGE_TEL
	private static  String  deviceChargeTel;
	/** 版本号  */
	//public static final String  VERSION_NUM
	private static  String  versionNum;
	/** 配置项ID  */
	//public static final String  CONFIG_ITEM_ID
	private static  String  configItemId;
	/** 配置类型ID  */
	//public static final String  CONFIG_TYPE_I_D
	private static  String  configTypeID;
	/** 资源ID  */
	//public static final String  RESOURCE_ID
	private static  String  resourceId;
	/** 类型  */
	//public static final String  TYPE
	private static  String  type;
	/** 其它配件  */
	//public static final String  OTHER_ACCESSORIES
	private static  String  otherAccessories;
	/** 网口数量  */
	//public static final String  NET_PORT_NUM
	private static  String  netPortNum;
	/** 标准全称  */
	//public static final String  FULL_NAME
	private static  String  fullName;
	/** 所属子网ID  */
	//public static final String  SUBNET_ID
	private static  String  subnetId;
	/** 所属子网名称  */
	//public static final String  SUBNET_NAME
	private static  String  subnetName;
	/** 老旧设备标识  */
	//public static final String  OLD_MARK
	private static  String  oldMark;
	/** 是否启用防火墙  */
	//public static final String  IS_FIREWALL
	private static  String  isFirewall;
	/** 是否启用时钟同步  */
	//public static final String  IS_CLOCK_SYNCHRONIZATION
	private static  String  isClockSynchronization;
	/** 产权单位编码  */
	//public static final String  OWNER_UNIT_CODE
	private static  String  ownerUnitCode;


	/** 是否信创设备  */
	//public static final String  IS_I_T_A_I_CODE
	private static  String  isITAICode;
	/** CPU架构  */
	//public static final String  CPU_ARCH_CODE
	private static  String  cpuArchCode;
	/** 是否外单位设备  */
	//public static final String  IS_EXTERNAL_UNIT_CODE
	private static  String  isExternalUnitCode;
	/** 信息是否完备  */
	//public static final String  IS_DATA_COMPLETE_CODE
	private static  String  isDataCompleteCode;
	/** 审核状态  */
	//public static final String  EXAMINE_STATUS
	private static  String  examineStatus;
	/** 备份保留期限单位  */
	//public static final String  BACK_TERM_UNIT
	private static  String  backTermUnit;
	/** 型号批次隐患缺陷  */
	//public static final String  MODE_BATCH_HIDDEN_TROUBLE
	private static  String  modeBatchHiddenTrouble;
	/** 是否可报废  */
	//public static final String  IS_SCRAP_CODE
	private static  String  isScrapCode;
	/** 是否可用  */
	//public static final String  IS_USE
	private static  String  isUse;
	/** 是否拟报废  */
	//public static final String  IS_SCRAP_PLAN_CODE
	private static  String  isScrapPlanCode;
	/** 资源类型  */
	//public static final String  RESOURCE_TYPE
	private static  String  resourceType;
	/** 资源分类ID  */
	//public static final String  RESOURCE_CATEGORY_ID
	private static  String  resourceCategoryId;
	/** 云类型  */
	//public static final String  CLOUD_TYPE
	private static  String  cloudType;
	/** 供电方式  */
	//public static final String  POWER_SUPPLY_MODEL
	private static  String  powerSupplyModel;
	/** 使用状态  */
	//public static final String  USE_STATUS
	private static  String  useStatus;
	/** 完全备份方式  */
	//public static final String  FUN_BACKUP
	private static  String  funBackup;
	/** 完全备份周期单位  */
	//public static final String  FUN_BACKUP_PERIOD_UNIT
	private static  String  funBackupPeriodUnit;

	/** 故障部件  */
	//public static final String  FAULT_PARTS
	private static  String  faultParts;
	/** 增量备份方式  */
	//public static final String  ADD_BACKUP_TYPE
	private static  String  addBackupType;
	/** 停机状态  */
	//public static final String  DOWN_STATUS
	private static  String  downStatus;
	/** 主机设备用途类型  */
	//public static final String  SERVER_USE_TO_TYPE
	private static  String  serverUseToType;

	/** 是否纳入云管  */
	//public static final String  IS_CLOUD_MANGE
	private static  String  isCloudMange;
	/** 是否支持链路聚合  */
	//public static final String  IS_AGGREGATION
	private static  String  isAggregation;
	/** 设备管理部门  */
	//public static final String  MANAGEMENT_DEPT
	private static  String  managementDept;
	/** 主备属性  */
	//public static final String  STANDBY_ATTR
	private static  String  standbyAttr;
	/** 防小动物措施  */
	//public static final String  ANIMAL_PROTECTION
	private static  String  animalProtection;
	/** 接地措施  */
	//public static final String  EARTHING_PROTECTION
	private static  String  earthingProtection;
	/** 封堵措施  */
	//public static final String  PLUGGING_PROTECTION
	private static  String  pluggingProtection;
	/** 防雷措施  */
	//public static final String  LIGHTNING_PROTECTION
	private static  String  lightningProtection;
	/** 网口类型  */
	//public static final String  NET_PORT_TYPE
	private static  String  netPortType;

	/** 网络设备用途类型  */
	//public static final String  NETWORK_DEVICE_TYPE
	private static  String  networkDeviceType;
	/** 产权状态  */
	//public static final String  OWNER_STATUS
	private static  String  ownerStatus;
	/** 存储RAID冗余方式  */
	//public static final String  RAID_STORAGE_TYPE
	private static  String  raidStorageType;
	/** 运维等级  */
	//public static final String  OPERATION_LEVEL
	private static  String  operationLevel;

	/** 报废原因  */
	//public static final String  SCRAP_REASON_CODE
	private static  String  scrapReasonCode;
	/** 来源  */
	//public static final String  SOURCE
	private static  String  source;
	/** 切换测试开展  */
	//public static final String  SWITCH_TEST
	private static  String  switchTest;
	/** 是否为办公终端  */
	//public static final String  IS_OFFICE_DEVICE
	private static  String  isOfficeDevice;
	/** 虚拟化平台类型  */
	//public static final String  VIRTUALIZATION_PLAYFORM_TYP
	private static  String  virtualizationPlayformTyp;
	/** 检修状态  */
	//public static final String  OVERHAUL_STATUS
	private static  String  overhaulStatus;
	/** 运行重要程度  */
	//public static final String  OPRT_IMPORTANCE
	private static  String  oprtImportance;
	/** 运行状态  */
	//public static final String  OPRT_STATUS
	private static  String  oprtStatus;



	/** 购买类型  */
	//public static final String  PURCHASE_METHOD
	private static  String  purchaseMethod;
	/** 检修单位编码  */
	//public static final String  OVERHAUL_UNIT_CODE
	private static  String  overhaulUnitCode;
	/** 检修部门编码  */
	//public static final String  OVERHAUL_DEPT_CODE
	private static  String  overhaulDeptCode;

	/** 高可用类型  */
	//public static final String  HA_TYPE
	private static  String  HAType;
	/** CPU品牌编码  */
	//public static final String  CPU_BRAND_CODE
	private static  String  cpuBrandCode;

	/** 工厂区域编码  */
	//public static final String  FACTORY_AREA_CODE
	private static  String  factoryAreaCode;
	/** 操作系统版本号  */
	//public static final String  OS_VERSION
	private static  String  OSVersion;
	/** 电压等级编码  */
	//public static final String  VOLTAGE_LEVEL_CODE
	private static  String  voltageLevelCode;
	/** 功能描述  */
	//public static final String  FUNCTION_DESCRIP
	private static  String  functionDescrip;
	/** 故障时间  */
	//public static final String  FAULT_TIME
	private static  String  faultTime;
	/** 最后活跃日期  */
	//public static final String  LAST_ACTIVE_DATE
	private static  String  lastActiveDate;
	/** 注册时间/发现日期  */
	//public static final String  DISCOVERY_DATE
	private static  String  discoveryDate;



}
