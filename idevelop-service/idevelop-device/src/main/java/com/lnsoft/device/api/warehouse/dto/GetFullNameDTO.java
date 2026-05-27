package com.lnsoft.device.api.warehouse.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: GetFullNameDTO
 * @description:
 * @author: zhangs
 * @create: 2024-05-09 10:05
 **/
@Data
public class GetFullNameDTO implements Serializable {
	private static final long serialVersionUID = -897933087556668312L;

	@ApiModelProperty(value = "设备来源类型（ID）", required = true)
	private String type;

	@ApiModelProperty(value = "WBS项目名称")
	private String projectName;

	@ApiModelProperty(value = "设备类型编码", required = true)
	private String deviceType;
}
