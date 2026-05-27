package com.lnsoft.device.api.warehouse.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErpDeviceOrderDTO implements Serializable {
	private static final long serialVersionUID = 6301535840007728202L;

	/**
	 * 设备工单id
	 */
	@ApiModelProperty(value = "设备工单id")
	private String deviceRecordId;
	/**
	 * 设备工单编号
	 */
	@ApiModelProperty(value = "设备工单编号")
	private String filingNo;
	/**
	 * 使用保管部门
	 */
	@ApiModelProperty(value = "使用保管部门")
	private String useKeepDept;
	/**
	 * 使用保管部门名称
	 */
	@ApiModelProperty(value = "使用保管部门名称")
	private String useKeepDeptName;
	/**
	 * erp审核状态 0 同意 1 不同意
	 */
	@ApiModelProperty(value = "erp审核状态 0 同意 1 不同意")
	private Integer erpExamineStatus;
	/**
	 * erp审核意见
	 */
	@ApiModelProperty(value = "erp审核意见")
	private String erpExamineReason;
	/**
	 * ERP回传设备信息
	 */
	@ApiModelProperty(value = "ERP回传设备信息")
	private List<ErpDeviceDetailDTO> deviceRecordListDTOList;
}
