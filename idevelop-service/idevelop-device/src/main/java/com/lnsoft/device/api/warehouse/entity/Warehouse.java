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
package com.lnsoft.device.api.warehouse.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 仓库管理表实体类
 *
 * @author Idevelop
 * @since 2024-03-05
 */
@Data
@TableName("idevelop_warehouse")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Warehouse对象", description = "仓库管理表")
public class Warehouse extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "uuid", type = IdType.ASSIGN_ID)
	@ExcelIgnore
	private String uuid;
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
	 * 所属单位编码
	 */
	@ApiModelProperty(value = "所属单位编码")
	@ExcelIgnore
	private String ownerUnitId;
	/**
	 * 区域编码
	 */
	@ApiModelProperty(value = "区域编码")
	@ExcelProperty("区域编码")
	private String regionCode;
	/**
	 * 详细地址
	 */
	@ApiModelProperty(value = "详细地址")
	@ExcelProperty("详细地址")
	private String address;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	/**
	 * 总数量
	 */
	@ApiModelProperty(value = "总数量")
	@ExcelIgnore
	private int devSize;
	/**
	 * 在库数量
	 */
	@ApiModelProperty(value = "在库数量")
	@ExcelIgnore
	private int wareSize;
	/**
	 * 已出库数量
	 */
	@ApiModelProperty(value = "已出库数量")
	@ExcelIgnore
	private int outSize;
	/**
	 * 最后统计时间
	 */
	@ApiModelProperty(value = "最后统计时间")
	@ExcelIgnore
	private Date lastUpdateTime;
	/**
	 * 是否同步i6000
	 */
	@ApiModelProperty(value = "是否同步i6000 否-0 是-1")
	@ExcelIgnore
	private String isI6000;

	/**
	 * 关联I6000仓库uuid
	 */
	@ApiModelProperty(value = "关联I6000仓库uuid")
	@ExcelIgnore
	private String i6000Uuid;

	/**
	 * 关联I6000仓库name
	 */
	@ApiModelProperty(value = "关联I6000仓库name")
	@ExcelProperty("关联I6000仓库")
	private String i6000Name;

}
