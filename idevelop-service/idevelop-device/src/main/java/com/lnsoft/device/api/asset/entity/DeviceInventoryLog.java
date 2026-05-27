/**
 .
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lnsoft.device.api.asset.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 设备库存日志表实体类
 *
 * @author Idevelop
 * @since 2024-04-29
 */
@Data
@TableName("idevelop_device_inventory_log")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceInventoryLog对象", description = "设备库存日志表")
public class DeviceInventoryLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty(value = "id")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private String id;
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
	 * 设备类型名称
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
	 * 库存类型
	 */
	@ApiModelProperty(value = "库存类型")
	private String inventoryType;
	/**
	 * 操作人
	 */
	@ApiModelProperty(value = "操作人")
	private String inventoryMan;


}
