package com.lnsoft.device.api.warehouse.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: DeviceStorageSyncErpDTO
 * @description:
 * @author: zhangs
 * @create: 2024-04-01 17:13
 **/
@Data
public class DeviceStorageSyncErpDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "制造商")
	private String maker;

	@ApiModelProperty(value = "投运日期")
	private Date oprtDate;

	@ApiModelProperty(value = "计量单位")
	private String unit;

	@ApiModelProperty(value = "铭牌号")
	private String nameplateNo;
}
