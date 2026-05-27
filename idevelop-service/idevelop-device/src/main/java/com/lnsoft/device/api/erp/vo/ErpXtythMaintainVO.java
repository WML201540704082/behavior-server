package com.lnsoft.device.api.erp.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2024/4/12 14:20
 * @Description: ErpMaintainKostl
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErpXtythMaintainVO implements Serializable {

	private String code;

	private String name;

	private Integer unitSort = 1;

}
