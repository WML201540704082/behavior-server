package com.lnsoft.device.api.warehouse.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 导出入参
 *
 * @ClassName: DeviceStorageExportSO
 * @description:
 * @author: zhangs
 * @create: 2024-02-26 16:13
 **/
@Data
public class DeviceStorageExportSO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "入库单号")
	private String serialNumber;
	@ApiModelProperty(value = "入库日期")
	private LocalDate storageDate;
	@ApiModelProperty("工单状态")
	private Integer status;
	@ApiModelProperty(value = "WBS元素")
	private String wbsElement;
	@ApiModelProperty(value = "所在单位")
	private String ownerUnit;
	@ApiModelProperty(value = "WBS项目")
	private String wbsProject;
	@ApiModelProperty(value = "入库人")
	private String receiver;
	@ApiModelProperty(value = "入库仓库")
	private String warehouse;
	@ApiModelProperty(value = "是否同步I6000：0否1是")
	private Integer isToI6000;
	@ApiModelProperty(value = "导出ids，以,分隔")
	private String ids;
	@ApiModelProperty(value = "区域编码")
	private String regionCode;
}
