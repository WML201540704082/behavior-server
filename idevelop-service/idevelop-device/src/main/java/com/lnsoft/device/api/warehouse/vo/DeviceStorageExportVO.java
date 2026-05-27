package com.lnsoft.device.api.warehouse.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

/**
 * @ClassName: DeviceStorageExportVO
 * @description:
 * @author: zhangs
 * @create: 2024-02-26 17:27
 **/
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class DeviceStorageExportVO extends DeviceStorageExportDynamicVO {
	private static final long serialVersionUID = 1L;

	@ExcelProperty(value = "入库单号")
	private String serialNumber;
	@ExcelProperty(value = "设备来源")
	private String deviceSource;
	@ExcelProperty(value = "WBS项目")
	private String wbsProject;
	@ExcelProperty(value = "WBS元素")
	private String wbsElement;
	@ExcelProperty(value = "设备分类")
	private String deviceCategory;
	@ExcelProperty(value = "设备类型")
	private String deviceType;
	@ExcelProperty(value = "所在仓库")
	private String warehouseName;
//	@ExcelProperty(value = "入库数量")
//	private String deviceNum;
	//	@ExcelProperty(value = "运行单位")
//	private String oprtDept;
	@ExcelProperty(value = "受理人")
	private String receiver;
	@ExcelProperty(value = "入库时间")
	private String storageTime;
	//	@ExcelProperty(value = "使用保管人")
//	private String useKeepPerson;
	@ExcelProperty(value = "电压等级")
	private String voltageLevel;
	@ExcelProperty(value = "采购日期")
	private String procureDate;
	//	@ExcelProperty(value = "产权单位")
//	private String ownerUnit;
//	@ExcelProperty(value = "产权部门")
//	private String propertyDept;
//	@ExcelProperty(value = "使用保管部门")
//	private String useKeepDept;
//	@ExcelProperty(value = "实物保管部门")
//	private String entityKeepDept;
//	@ExcelProperty(value = "工厂区域")
//	private String factoryArea;
//	@ExcelProperty(value = "维护工厂")
//	private String maintenanceFactory;
//	@ExcelProperty(value = "线站标识(设备建档数据来源)")
//	private String lineStation;
//	@ExcelProperty(value = "铭牌号")
//	private String nameplateNo;
//	@ExcelProperty(value = "是否同步I6000")
//	private String isToI6000;
	//	@ExcelProperty(value = "是否同步ERP(是否立即转资)：0否1是")
//	private String isToErp;
//	@ExcelProperty(value = "是否可用：0否1是")
//	private String isUsable;
	@ExcelProperty(value = "是否暂存")
	private String isTemp;
//	@ExcelProperty(value = "创建部门")
//	private String createDept;

	/**
	 * 从表
	 */
	@ExcelProperty(value = "设备编码")
	private String deviceCode;
	@ExcelProperty(value = "erp资产编码")
	private String erpAssetCode;
	@ExcelProperty(value = "erp台账编码")
	private String erpAccountCode;
	@ExcelProperty(value = "设备名称")
	private String deviceName;
	@ExcelProperty(value = "设备全称")
	private String fullName;
	@ExcelProperty(value = "设备状态")
	private String deviceStatus;
	@ExcelIgnore
	private String deviceHardwareInfo;


}
