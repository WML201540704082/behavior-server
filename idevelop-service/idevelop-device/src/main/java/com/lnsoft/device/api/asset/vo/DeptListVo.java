package com.lnsoft.device.api.asset.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "DeviceInfoVo", description = "DeviceInfoVo")
public class DeptListVo {

	private String name;

	private Integer value;

	private String type;
}
