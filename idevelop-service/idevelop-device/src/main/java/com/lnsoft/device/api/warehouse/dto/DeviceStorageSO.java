package com.lnsoft.device.api.warehouse.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: DeviceStorageSO
 * @description:
 * @author: zhangs
 * @create: 2024-03-04 18:15
 **/
@Data
public class DeviceStorageSO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "产权单位")
	private String ownerUnitCode;
	@ApiModelProperty(value = "产权部门")
	private String deptCode;
	@ApiModelProperty(value = "设备来源")
	private String deviceSourceCode;
	@ApiModelProperty(value = "设备分类")
	private String deviceCategory;
	@ApiModelProperty(value = "设备类型")
	private String deviceType;
	@ApiModelProperty(value = "设备编码")
	private String deviceCode;
	@ApiModelProperty(value = "所在仓库")
	private String warehouse;
	@ApiModelProperty(value = "入库日期开始时间")
	private String startDate;
	@ApiModelProperty(value = "入库日期结束时间")
	private String endDate;
	@ApiModelProperty(value = "WBS项目")
	private String wbsProject;
	@ApiModelProperty(value = "WBS项目")
	private String regionCode;
}
