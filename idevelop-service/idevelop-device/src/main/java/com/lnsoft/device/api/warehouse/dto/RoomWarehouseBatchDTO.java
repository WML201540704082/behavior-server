package com.lnsoft.device.api.warehouse.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2025/2/26 14:47
 * @Description: RoomWarehouseBatchDTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomWarehouseBatchDTO implements Serializable {

	private static final long serialVersionUID = 4072914246135055285L;

	/**
	 * 批量机房和仓库批量id
	 */
	@ApiModelProperty(value = "批量机房和仓库批量id")
	private String roomWarehouseIds;

	/**
	 * 关联I6000仓库uuid
	 */
	@ApiModelProperty(value = "关联I6000仓库uuid")
	private String i6000Uuid;

	/**
	 * 关联I6000仓库name
	 */
	@ApiModelProperty(value = "关联I6000仓库name")
	private String i6000Name;

}
