package com.lnsoft.device.api.erp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2024/5/6 14:19
 * @Description: ErpUpdateAnlnr
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErpUpdateAnlnr implements Serializable {

	// 信通设备ID ==> UUID
	@NotBlank(message = "信通设备ID不能为空")
	private String xtbm;

	// 信通设备编码
	private String xtbmNo;

	// ERP设备台账编码
	@NotBlank(message = "ERP设备台账编码不能为空")
	private String equnr;

	// 新ERP资产编码
	@NotBlank(message = "新ERP资产编码不能为空")
	private String newAnlnr;
}
