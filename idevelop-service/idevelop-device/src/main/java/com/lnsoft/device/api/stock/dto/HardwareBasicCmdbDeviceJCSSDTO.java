package com.lnsoft.device.api.stock.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.lnsoft.device.annotation.*;
import com.lnsoft.device.constant.CommonConstant;
import lombok.Data;


/**
 * 数据治理-基础设施  设备台账实体类
 */
@Data
public class HardwareBasicCmdbDeviceJCSSDTO extends HardwareBasicCmdbDeviceDTO {

	private static final long serialVersionUID = 1L;

	@HeadFontStyle(color = 8, bold = false)
	@ExcelProperty(index = 0, value = "序号")
	private Integer index;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelSelected(ciId = "device-claccify", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 1, value = "设备分类")
	private String deviceCategory;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelSelected(ciId = "device-type", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 2, value = "设备类型")
	private String deviceType;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelProperty(index = 3, value = "标准全称")
	private String fullName;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelSelected(ciId = "device-source", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 4, value = "设备来源")
	private String deviceSource;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelSelected(ciId = "device-add-type", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 5, value = "设备增加方式")
	private String deviceAddType;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelSelected(ciId = "procure-type-code", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 6, value = "采购方式")
	private String procureType;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelProperty(index = 7, value = "ERP资产编码")
	private String assetCodeErp;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelProperty(index = 8, value = "出厂序列号")
	private String sn;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelSelected(ciId = "maker", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 9,value = "制造商")
	private String maker;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelSelected(ciId = "brand", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 10, value = "品牌")
	private String brand;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelSelected(ciId = "series", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 11, value = "系列")
	private String series;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelSelected(ciId = "model", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 12, value = "型号")
	private String deviceModel;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelSelected(ciId = "device-status", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 13, value = "设备状态")
	private String deviceStatus;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelValidDate(message = CommonConstant.DATE_FORMAT)
	@DateTimeFormat(CommonConstant.DATE_FORMAT)
	@ExcelProperty(index = 14, value = "采购日期")
	private String procureDate;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelValidDate(message = CommonConstant.DATE_FORMAT)
	@DateTimeFormat(CommonConstant.DATE_FORMAT)
	@ExcelProperty(index = 15, value = "出厂日期")
	private String factoryDate;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelValidDate(message = CommonConstant.DATE_FORMAT)
	@DateTimeFormat(CommonConstant.DATE_FORMAT)
	@ExcelProperty(index = 16, value = "首次投运日期")
	private String oprtDateFirst;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelValidDate(message = CommonConstant.DATE_FORMAT)
	@DateTimeFormat(CommonConstant.DATE_FORMAT)
	@ExcelProperty(index = 17, value = "投运日期")
	private String oprtDate;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelValidDate(message = CommonConstant.DATE_FORMAT)
	@DateTimeFormat(CommonConstant.DATE_FORMAT)
	@ExcelProperty(index = 18, value = "服务到期时间")
	private String serviceExpDate;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelValidDate(message = CommonConstant.DATE_FORMAT)
	@DateTimeFormat(CommonConstant.DATE_FORMAT)
	@ExcelProperty(index = 19, value = "退运日期")
	private String retireDate;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValidDate(message = CommonConstant.DATE_FORMAT)
	@DateTimeFormat(CommonConstant.DATE_FORMAT)
	@ExcelProperty(index = 20, value = "报废日期")
	private String scrapDate;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelProperty(index = 21, value = "所在仓库")
	private String inWarehouse;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelCorpSelected
	@ExcelProperty(index = 22, value = "领用单位")
	private String receiveUnit;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelDeptSelected
	@ExcelProperty(index = 23, value = "领用部门")
	private String receiveDept;

	@HeadFontStyle(color = 13, bold = false)
	@ExcelProperty(index = 24, value = "运维单位")
	private String operationUnit;

	@HeadFontStyle(color = 13, bold = false)
	@ExcelProperty(index = 25, value = "运维部门")
	private String operationDept;

	@HeadFontStyle(color = 13, bold = false)
	@ExcelProperty(index = 26, value = "产权单位")
	private String ownerUnit;

	@HeadFontStyle(color = 13, bold = false)
	@ExcelProperty(index = 27, value = "维保情况")
	private String wbqk;

	@HeadFontStyle(color = 13, bold = false)
	@ExcelProperty(index = 28, value = "近三年故障情况")
	private String jsngzqk;

	@HeadFontStyle(color = 13, bold = false)
	@ExcelProperty(index = 29, value = "近三年故障次数")
	private String yzgzcs;

	@HeadFontStyle(color = 13, bold = false)
	@ExcelProperty(index = 30, value = "近三年隐患情况")
	private String jsnyhqk;

	@HeadFontStyle(color = 13, bold = false)
	@ExcelProperty(index = 31, value = "近三年隐患个数")
	private String yzyhgs;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelProperty(index = 32, value = "责任人")
	private String receivingPerson;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelProperty(index = 33, value = "责任人ISC账号")
	private String receivePersonUnifiedAcc;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelProperty(index = 34, value = "责任人联系方式")
	private String receivingTel;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelProperty(index = 35, value = "责任人身份证号")
	private String receivingIDCard;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelProperty(index = 36, value = "所属机房")
	private String computerRoom;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelProperty(index = 37, value = "安装地点")
	private String installationSite;

	@HeadFontStyle(color = 13, bold = false)
	@ExcelProperty(index = 38, value = "机房类型")
	private String jfjb;

	@HeadFontStyle(color = 13, bold = false)
	@ExcelProperty(index = 39, value = "机房功能")
	private String jfgn;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelSelected(ciId = "power-level", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 40, value = "电压等级")
	private String voltageLevel;

	@HeadFontStyle(color = 13, bold = false)
	@ExcelSelected(ciId = "yes-no", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 41, value = "是否需要更换")
	private String sfxygh;

	@HeadFontStyle(color = 13, bold = false)
	@ExcelSelected(ciId = "yes-no", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 42, value = "是否完成更换立项")
	private String sfwcghlx;

	@HeadFontStyle(color = 13, bold = false)
	@ExcelProperty(index = 43, value = "立项更换时间")
	private String lxghsj;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelDataReplace
	@ExcelSelected(ciId = "air-condition-type", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 44, value = "空调类型")
	private String airConditionType;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelProperty(index = 45, value = "匹数(匹)")
	private String horsepower;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelProperty(index = 46, value = "制冷量(W)")
	private Double coolCapacity;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelProperty(index = 47, value = "UPS容量(KVA)")
	private String upsCapacity;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelProperty(index = 48, value = "电源负载(VA)")
	private String powerLoad;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelProperty(index = 49, value = "电池数(块)")
	private Double batteryNum;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelProperty(index = 50, value = "电池组数(组)")
	private Double batteryPackNum;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelProperty(index = 51, value = "额定容量(A·h)")
	private String ratedCapacity;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelProperty(index = 52, value = "所属UPS")
	private String belongUps;

	@HeadFontStyle(color = 8, bold = false)
	@ExcelProperty(index = 53, value = "备注")
	private String remark;

	@HeadFontStyle(color = 0, bold = false)
	@ExcelDeptSelected
	@ExcelProperty(index = 54, value = "产权部门")
	private String propertyDept;

	@HeadFontStyle(color = 8, bold = false)
	@ExcelSelected(ciId = "data-opt", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 55, value = "操作类型")
	private String dataOptType;

	@HeadFontStyle(color = 8, bold = false)
	@ExcelProperty(index = 56, value = "设备编码")
	private String deviceCode;

	@HeadFontStyle(color = 8, bold = false)
	@ExcelProperty(index = 57, value = "id")
	private String id;

}
