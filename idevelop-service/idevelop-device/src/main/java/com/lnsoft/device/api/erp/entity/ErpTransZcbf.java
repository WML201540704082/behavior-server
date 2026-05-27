package com.lnsoft.device.api.erp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: xuel
 * @CreateTime: 2024/3/30 14:49
 * @Description: ErpTransEqunr
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErpTransZcbf implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 信通工单ID
	 */
	@NotEmpty(message = "信通工单ID不能为空")
	private String xtdocId;

	/**
	 * 信通工单编码
	 */
	@NotEmpty(message = "信通工单编码不能为空")
	private String xtdocNo;

	/**
	 * 设备信息
	 */
	private List<ErpTransZcbfItem> erpTransZcbfItemList;

}
