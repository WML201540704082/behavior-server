package com.lnsoft.device.api.warehouse.dto;

import com.lnsoft.endpoint.entity.EndpointPortUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderUpdateStatusDTO implements Serializable {
	private static final long serialVersionUID = 4072914246135055285L;

	/**
	 * id
	 */
	@ApiModelProperty(value = "工单id")
	private String id;
	/**
	 * 工单流程节点标识
	 */
	@ApiModelProperty(value = "工单流程节点标识")
	private String taskDefinitionKey;
	/**
	 * 审核意见
	 */
	@ApiModelProperty(value = "审核意见")
	private String comment;
	/**
	 * 审批意见
	 */
	@ApiModelProperty(value = "审批意见 0 同意 1 拒绝")
	private Integer workerStatus;
	/**
	 * 审批角色
	 */
	@ApiModelProperty(value = "审批角色")
	private String examineRole;
	/**
	 * 接口清单详情列表
	 */
	@ApiModelProperty(value = "接口清单详情列表")
	private List<EndpointPortUser> endpointPortUserList;
}
