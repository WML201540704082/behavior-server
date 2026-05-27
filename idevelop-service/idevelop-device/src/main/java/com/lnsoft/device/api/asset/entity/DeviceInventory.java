package com.lnsoft.device.api.asset.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * idevelop_device_inventory
 *
 * @author
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@TableName("idevelop_device_inventory")
public class DeviceInventory extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 3651690455057785279L;
	/**
	 * id
	 */
	@ApiModelProperty(value = "id")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private String id;

	/**
	 * 区域编码
	 */
	@ApiModelProperty(value = "区域编码")
	private String regionCode;

	/**
	 * 单位id
	 */
	@ApiModelProperty(value = "单位id")
	private Long cropId;

	/**
	 * 设备分类
	 */
	@ApiModelProperty(value = "设备分类")
	private String deviceCategory;

	/**
	 * 设备分类名称
	 */
	@ApiModelProperty(value = "设备分类名称")
	private String deviceCategoryName;

	/**
	 * 设备类型
	 */
	@ApiModelProperty(value = "设备类型")
	private String deviceType;

	/**
	 * 设备分类名称
	 */
	@ApiModelProperty(value = "设备类型名称")
	private String deviceTypeName;

	/**
	 * 仓库id
	 */
	@ApiModelProperty(value = "仓库id")
	private String warehouse;

	/**
	 * 仓库名称
	 */
	@ApiModelProperty(value = "仓库名称")
	private String warehouseName;

	/**
	 * 库存数量
	 */
	@ApiModelProperty(value = "库存数量")
	private Integer inventoryNum;

	/**
	 * 乐观锁
	 */
	@ApiModelProperty(value = "乐观锁")
	private Long version;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;
}
