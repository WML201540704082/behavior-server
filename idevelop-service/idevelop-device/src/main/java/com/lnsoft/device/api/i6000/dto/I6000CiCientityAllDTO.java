package com.lnsoft.device.api.i6000.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2024/5/15 11:09
 * @Description: I6000CiCientity
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class I6000CiCientityAllDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// 待查询的配置项数据属性编码，多个属性时中间以英文逗号分隔。
	private I6000CiCientityDTO i6000CiCientityDTO;

	// 查询请求条件
	private String ciTypeId;

}
