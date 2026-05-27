package com.lnsoft.device.api.stock.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.lnsoft.device.annotation.ExcelDeptSelected;
import com.lnsoft.device.annotation.ExcelDynamicSelectImpl;
import com.lnsoft.device.annotation.ExcelSelected;
import com.lnsoft.device.annotation.ExcelValid;
import lombok.Data;

/**
 * 数据治理-非数字化入网设备 设备台账实体类
 */
@Data
public class HardwareBasicCmdbDeviceFSZHRWSBDTO extends HardwareBasicCmdbDeviceDTO{

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
	@ExcelSelected(ciId = "maker", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 4,value = "制造商")
	private String maker;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelSelected(ciId = "brand", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 5, value = "品牌")
	private String brand;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelSelected(ciId = "series", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 6, value = "系列")
	private String series;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelSelected(ciId = "model", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 7, value = "型号")
	private String deviceModel;

	@HeadFontStyle(color = 13, bold = false)
	@ExcelProperty(index = 8, value = "产权单位")
	private String ownerUnit;

	@HeadFontStyle(color = 0, bold = false)
	@ExcelDeptSelected
	@ExcelProperty(index = 9, value = "产权部门")
	private String propertyDept;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelSelected(ciId = "net-work-code", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 10, value = "所属网络")
	private String netWork;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelProperty(index = 11, value = "IP地址")
	private String IP;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelProperty(index = 12, value = "MAC地址")
	private String MAC;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelProperty(index = 13, value = "安装地点")
	private String installationSite;

	@HeadFontStyle(color = 53, bold = false)
	@ExcelSelected(ciId = "server-use-to-type", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 14, value = "设备用途类型")
	private String serverUseToType;

	@HeadFontStyle(color = 0, bold = false)
	@ExcelSelected(ciId = "yes-no", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 15, value = "非数字化入网设备")
	private String isAccessEquipment;

	@HeadFontStyle(color = 8, bold = false)
	@ExcelProperty(index = 16, value = "备注")
	private String remark;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelSelected(ciId = "data-opt", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 17, value = "操作类型")
	private String dataOptType;
}
