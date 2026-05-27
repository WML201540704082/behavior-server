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

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 设备出库表实体类
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@Data
@TableName("idevelop_device_outbound")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceOutbound对象", description = "设备出库表")
public class DeviceOutbound extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private String id;
	/**
	 * 出库单号
	 */
	@ApiModelProperty(value = "出库单号")
	private String outboundNo;
	/**
	 * 申请编号
	 */
	@ApiModelProperty(value = "申请编号")
	private String applyNo;
	/**
	 * 投运单号
	 */
	@ApiModelProperty(value = "投运单号")
	private String operationNo;
	/**
	 * 是否以旧换新 0 是 1 否
	 */
	@ApiModelProperty(value = "是否以旧换新 0 是 1 否")
	private String oldToNew;
	/**
	 * 设备分类
	 */
	@ApiModelProperty(value = "设备分类")
	private String deviceCategory;
	/**
	 * 设备类型
	 */
	@ApiModelProperty(value = "设备类型")
	private String deviceType;
	/**
	 * 设备分类名称
	 */
	@ApiModelProperty(value = "设备分类名称")
	private String deviceCategoryName;
	/**
	 * 设备类型名称
	 */
	@ApiModelProperty(value = "设备类型名称")
	private String deviceTypeName;
	/**
	 * 申请数量
	 */
	@ApiModelProperty(value = "申请数量")
	private Integer applyNum;
	/**
	 * 出库数量
	 */
	@ApiModelProperty(value = "出库数量")
	private Integer outboundNum;
	/**
	 * 所在仓库
	 */
	@ApiModelProperty(value = "所在仓库")
	private String warehouse;
	/**
	 * 所在仓库名称
	 */
	@ApiModelProperty(value = "所在仓库名称")
	private String warehouseName;
	/**
	 * 领用单位
	 */
	@ApiModelProperty(value = "领用单位")
	private String receiveUnit;
	/**
	 * 领用单位名称
	 */
	@ApiModelProperty(value = "领用单位名称")
	private String receiveUnitName;
	/**
	 * 领用责任部门
	 */
	@ApiModelProperty(value = "领用责任部门")
	private String receiveDutyDept;
	/**
	 * 领用责任部门名称
	 */
	@ApiModelProperty(value = "领用责任部门")
	private String receiveDutyDeptName;
	/**
	 * 领用责任班组
	 */
	@ApiModelProperty(value = "领用责任班组")
	private String receiveDutyGroup;
	/**
	 * 领用责任班组名称
	 */
	@ApiModelProperty(value = "领用责任班组名称")
	private String receiveDutyGroupName;
	/**
	 * 是否立即投运 0 是 1 否
	 */
	@ApiModelProperty(value = "是否立即投运 0 是 1 否")
	private String operation;
	/**
	 * 出库人员
	 */
	@ApiModelProperty(value = "出库人员")
	private String applyUser;
	/**
	 * 出库人员名称
	 */
	@ApiModelProperty(value = "出库人员名称")
	private String applyUserName;
	/**
	 * 申请时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "申请时间")
	private Date applyDate;
	/**
	 * 出库时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "出库时间")
	private Date outboundTime;
	/**
	 * 流程实例ID
	 */
	@ApiModelProperty(value = "流程实例ID")
	private String processInsId;
	/**
	 * 流程状态
	 */
	@ApiModelProperty(value = "流程状态")
	private String processStatus;
	/**
	 * 区域编码
	 */
	@ApiModelProperty(value = "区域编码")
	private String regionCode;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;
	/**
	 * 创建部门
	 */
	@ApiModelProperty(value = "创建部门")
	private String createDept;
	/**
	 * 流程发起时间
	 */
	@ApiModelProperty(value = "流程发起时间")
	private Date submitTime;
	/**
	 * 出库状态 1 未出库 2 已出库
	 */
	@ApiModelProperty(value = "出库状态 1 未出库 2 已出库")
	private String outboundStatus;
}
