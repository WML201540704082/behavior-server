package com.lnsoft.device.api.erp.entity;

import com.lnsoft.device.eums.ErpOperationEnum;
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
public class ErpTransEqunr implements Serializable {

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
	private List<ErpTransEqunrItem> erpTransEqunrItemList;

	/**
	 * 操作标示符 C创建, M更新, D删除
	 */
	private ErpOperationEnum operationType;
}
