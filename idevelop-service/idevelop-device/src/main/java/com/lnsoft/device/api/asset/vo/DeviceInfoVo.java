package com.lnsoft.device.api.asset.vo;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.device.constant.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 主机设备 设备台账实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "DeviceInfoVo", description = "DeviceInfoVo")
public class DeviceInfoVo implements Serializable {

	private static final long serialVersionUID = 1L;

	// 查询分页
	@ApiModelProperty(value = "query")
	private Query query;

	@ApiModelProperty(value = "设备来源")
	private String deviceSourceCode;

	@ApiModelProperty(value = "*品牌")
	private String brandCode;

	@ApiModelProperty(value = "*系列")
	private String seriesCode;

	@ApiModelProperty(value = "*型号")
	private String deviceModelCode;

	@ApiModelProperty(value = "*设备状态")
	private String deviceStatusCode;

	@ApiModelProperty(value = "总数量")
	private String receiveUnit;

	@DateTimeFormat(CommonConstant.DATE_FORMAT)
	@ApiModelProperty(value = "投运开始日期")
	private String startOprtDate;

	@DateTimeFormat(CommonConstant.DATE_FORMAT)
	@ApiModelProperty(value = "投运结束日期")
	private String endOprtDate;

	@DateTimeFormat(CommonConstant.DATE_FORMAT)
	@ApiModelProperty(value = "退运日期")
	private String retireDate;

	@ApiModelProperty(value = "所属网络")
	private String netWorkCode;

	@ApiModelProperty(value = "IP地址")
	private String ip;

	@ApiModelProperty(value = "MAC")
	private String mac;

	@ApiModelProperty(value = "*CPU架构")
	private String cpuArchCode;

	@ApiModelProperty(value = "*CPU品牌")
	private String cpuBrandCode;

	@ApiModelProperty(value = "制造商")
	private String makerCode;

	@ApiModelProperty(value = "区域")
	private String area;

	@ApiModelProperty(value = "领用单位")
	private String receiveUnitCode;

	@ApiModelProperty(value = "领用部门")
	private String receiveDeptCode;

	@ApiModelProperty(value = "设备类型")
	private String deviceType;

	@ApiModelProperty(value = "设备分类")
	private String deviceCategoryCode;

	@ApiModelProperty(value = "设备类型")
	private String deviceTypeCode;

}
