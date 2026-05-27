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
package com.lnsoft.device.api.warehouse.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 仓库管理表公共接口返回类
 *
 * @author Idevelop
 * @since 2024-03-05
 */
@Data
public class WarehouseDictVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 仓库编号
	 */
	@ApiModelProperty(value = "仓库编号")
	private String warehouseId;
	/**
	 * 仓库名称
	 */
	@ApiModelProperty(value = "仓库名称")
	private String warehouseName;
	/**
	 * 所属单位
	 */
	@ApiModelProperty(value = "所属单位")
	private String ownerUnit;
	/**
	 * 所属单位编码
	 */
	@ApiModelProperty(value = "所属单位编码")
	private String ownerUnitId;

	@ApiModelProperty(value = "主键id")
	private String uuid;

	/**
	 * 详细地址
	 */
	@ApiModelProperty(value = "详细地址")
	private String address;
}
