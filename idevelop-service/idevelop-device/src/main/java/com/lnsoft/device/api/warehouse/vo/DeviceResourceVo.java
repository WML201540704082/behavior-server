package com.lnsoft.device.api.warehouse.vo;


import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 空间资源 查询条件
 *
 * @author xyz
 * @since 2024-03-04
 */
@Data
@ApiModel(value = "resource", description = "空间资源查询条件")
public class DeviceResourceVo extends BaseEntity {

	@ApiModelProperty(value = "每页大小")
	private Integer size;

	@ApiModelProperty(value = "当前页")
	private Integer current;

	@ApiModelProperty("机房主键id")
	private String computerRoomCode;

	@ApiModelProperty("机柜主键id")
	private String cabinetCode;

	@ApiModelProperty("机架主键id")
	private String rackCode;

	private String deviceCategoryCode;

	private String deviceName;

	private String deviceCode;
}
