package com.lnsoft.device.api.i6000.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xyzadmin
 */
@Data
public class I6000ExternalDTO {
	private String extCode;

	private String resultHint;

	private String resultValue;

	private String successful;

	// 分页起始页，从1开始
	@ApiModelProperty(value = "分页起始页，从1开始")
	private String pageStart;
	// 分页每页显示条数据，从1开始
	@ApiModelProperty(value = "分页每页显示条数据，从1开始")
	private String pageSize;

}
