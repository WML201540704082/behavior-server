/**
 .
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lnsoft.device.api.operation.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.lnsoft.device.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * 设备变更列表导出模板类
 *
 * @author Idevelop
 * @since 2024-03-07
 */
@Data
public class DeviceChangeListExport implements Serializable {

	private static final long serialVersionUID = 1L;

	@HeadFontStyle(color = 8, bold = false)
	@ExcelProperty(index = 0, value = "序号")
	private Integer index;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelProperty(index = 1, value = "设备编码")
	private String deviceCode;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelProperty(index = 2, value = "标准全称")
	private String fullName;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelProperty(index = 3, value = "出场序列号")
	private String sn;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelCorpSelected
	@ExcelProperty(index = 4, value = "领用单位")
	private String receiveUnit;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelDeptSelected
	@ExcelProperty(index = 5, value = "领用部门")
	private String receiveDept;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelProperty(index = 6, value = "责任人")
	private String receivingPerson;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelProperty(index = 7, value = "责任人班组")
	private String receivingGroup;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelProperty(index = 8, value = "责任人身份证号")
	private String receivingIDCard;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelProperty(index = 9, value = "责任人联系方式")
	private String receivingTel;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelProperty(index = 10, value = "责任人ISC账号")
	private String receivePersonUnifiedAcc;


	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelSelected(ciId = "maker", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 11, value = "制造商")
	private String maker;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelSelected(ciId = "brand", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 12, value = "品牌")
	private String brand;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelSelected(ciId = "series", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 13, value = "系列")
	private String series;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelSelected(ciId = "model", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 14, value = "型号")
	private String deviceModel;

	//	@HeadFontStyle(color = 10, bold = false)
//	@ExcelSelected(ciId = "model", sourceClassWbs = ExcelDynamicSelectWbsImpl.class)
//	@ExcelProperty(index = 20, value = "WBS元素")
//	private String wbsElement;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelProperty(index = 15, value = "erp资产编码")
	private String assetCodeErp;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelProperty(index = 16, value = "erp设备台账编码")
	private String deviceCodeErp;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelSelected(ciId = "use-keep-dept-name", sourceClassDept = ExcelDynamicSelectNewImpl.class)
	@ExcelProperty(index = 17, value = "使用保管部门")
	private String useKeepDeptName;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelSelected(ciId = "use-keep-dept-name", sourceClassDept = ExcelDynamicSelectNewImpl.class)
	@ExcelProperty(index = 18, value = "实物管理部门")
	private String entityManagementDeptName;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelSelected(ciId = "power-level", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 19, value = "电压等级")
	private String voltageLevel;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelSelected(ciId = "device-add-type", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 20, value = "设备增加方式")
	private String deviceAddType;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelSelected(ciId = "device-change-type", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 21, value = "设备变动方式")
	private String deviceChangeType;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelSelected(ciId = "transtplnr", sourceClassTran = ExcelDynamicSelectTranImpl.class)
	@ExcelProperty(index = 22, value = "功能位置")
	private String funLocation;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelSelected(ciId = "factory-area-code", sourceClass = ExcelDynamicSelectImpl.class)
	@ExcelProperty(index = 23, value = "工厂区域")
	private String factoryArea;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelProperty(index = 24, value = "投运日期")
	private String oprtDate;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelProperty(index = 25, value = "出厂日期")
	private String factoryDate;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelValid(required = true)
	@ExcelSelected(ciId = "main", sourceClassMain = ExcelDynamicSelectMaintainImpl.class)
	@ExcelProperty(index = 26, value = "维护工厂")
	private String maintenanceFactory;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelProperty(index = 27, value = "使用人")
	private String user;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelProperty(index = 28, value = "使用人联系方式")
	private String userTel;

	@HeadFontStyle(color = 10, bold = false)
	@ExcelProperty(index = 29, value = "使用人身份证号")
	private String deviceUserIDCard;

//	@HeadFontStyle(color = 10, bold = false)
//	@ExcelProperty(index = 29, value = "运维责任人")
//	private String deviceUserIDCard;
//
//	@HeadFontStyle(color = 10, bold = false)
//	@ExcelProperty(index = 29, value = "运维等级")
//	private String deviceUserIDCard;
//
//	@HeadFontStyle(color = 10, bold = false)
//	@ExcelProperty(index = 29, value = "运维电话")
//	private String deviceUserIDCard;

//	@HeadFontStyle(color = 10, bold = false)
//	@ExcelSelected(ciId = "unit", sourceClass = ExcelDynamicSelectImpl.class)
//	@ExcelProperty(index = 27, value = "计量单位")
//	private String measureUnit;




}
