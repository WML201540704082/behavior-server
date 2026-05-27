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
package com.lnsoft.device.api.warehouse.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 仓库管理表实体类
 *
 * @author Idevelop
 * @since 2024-03-05
 */
@Data
@ApiModel(value = "ExportWarehouseDTO对象", description = "仓库导出表")
public class ExportWarehouseDTO {

	private static final long serialVersionUID = 1L;
	/**
	 * 仓库编号
	 */
	@ApiModelProperty(value = "仓库编号")
	@ExcelProperty("仓库编号")
	private String warehouseId;
	/**
	 * 仓库名称
	 */
	@ApiModelProperty(value = "仓库名称")
	@ExcelProperty("仓库名称")
	private String warehouseName;
	/**
	 * 仓库状态 0-未启用  1-启用
	 */
	@ApiModelProperty(value = "仓库状态 0-未启用  1-启用")
	@ExcelProperty("仓库状态")
	private String warehouseStatus;
	/**
	 * 负责人员
	 */
	@ApiModelProperty(value = "负责人员")
	@ExcelProperty("负责人员")
	private String chargeUser;
	/**
	 * 联系电话
	 */
	@ApiModelProperty(value = "联系电话")
	@ExcelProperty("联系电话")
	private String phoneNum;
	/**
	 * 所属单位
	 */
	@ApiModelProperty(value = "所属单位")
	@ExcelProperty("所属单位")
	private String ownerUnit;
	/**
	 * 详细地址
	 */
	@ApiModelProperty(value = "详细地址")
	@ExcelProperty("详细地址")
	private String address;

	/**
	 * 关联I6000仓库name
	 */
	@ApiModelProperty(value = "关联I6000仓库name")
	@ExcelProperty("关联I6000仓库")
	private String i6000Name;

}
