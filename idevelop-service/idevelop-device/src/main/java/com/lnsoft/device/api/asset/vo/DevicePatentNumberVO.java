package com.lnsoft.device.api.asset.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2024/7/5 12:18
 * @Description: DevicePatentNumber
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "DevicePatentNumber", description = "DevicePatentNumber")
public class DevicePatentNumberVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "列名: 总量: all, 台式机: desktop, 笔记本: notebook")
	private String name;

	@ApiModelProperty(value = "设备数")
	private Integer allNumber;

	@ApiModelProperty(value = "在运数")
	private Integer inNumber;

	@ApiModelProperty(value = "在线数")
	private Integer onNumber;

	@ApiModelProperty(value = "分发进度")
	private Double distribution;


}
