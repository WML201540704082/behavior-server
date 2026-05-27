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

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 设备投运表实体类
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@Data
@TableName("idevelop_device_operation")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceOperation对象", description = "设备投运表")
public class DeviceOperation extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private String id;
	/**
	 * 投运单号
	 */
	@ApiModelProperty(value = "投运单号")
	private String operationNo;
	/**
	 * 申请编号
	 */
	@ApiModelProperty(value = "申请编号")
	private String applyNo;
	/**
	 * 出库单号
	 */
	@ApiModelProperty(value = "出库单号")
	private String outboundNo;
	/**
	 * 是否以旧换新 0 是 1 否
	 */
	@ApiModelProperty(value = "是否以旧换新 0 是 1 否")
	private String oldToNew;
	/**
	 * 是否立即投运 0 是 1 否
	 */
	@ApiModelProperty(value = "是否立即投运 0 是 1 否")
	private String operation;
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
	 * 领用（申请）单位
	 */
	@ApiModelProperty(value = "领用（申请）单位")
	private String receiveUnit;
	/**
	 * 领用（申请）单位名称
	 */
	private String receiveUnitName;
	/**
	 * 领用（申请）部门
	 */
	@ApiModelProperty(value = "申请部门")
	private String receiveDutyDept;
	/**
	 * 领用（申请）部门名称
	 */
	@ApiModelProperty(value = "申请部门名称")
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
	 * 受理人（工单发起人）
	 */
	@ApiModelProperty(value = "受理人（工单发起人）")
	private String applyUser;
	/**
	 * 受理人名称
	 */
	@ApiModelProperty(value = "受理人名称")
	private String applyUserName;
	/**
	 * 受理时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "受理时间")
	private Date applyDate;
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
	 * 流程发起时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "流程发起时间")
	private Date submitTime;
	/**
	 * 投运工单来源 0 设备申请生成  1 手动新增
	 */
	@ApiModelProperty(value = "投运工单来源 0 设备申请生成  1 手动新增")
	private String operationType;
	/**
	 * 区域编码
	 */
	@ApiModelProperty(value = "区域编码")
	private String regionCode;
	/**
	 * 运维单位
	 */
	@ApiModelProperty(value = "运维单位")
	@NotBlank(message = "运维单位不能为空")
	private String operationUnit;
	/**
	 * 运维单位名称
	 */
	@ApiModelProperty(value = "运维单位名称")
	@NotBlank(message = "运维单位名称不能为空")
	private String operationUnitName;
	/**
	 * 运维部门
	 */
	@ApiModelProperty(value = "运维部门")
	@NotBlank(message = "运维部门不能为空")
	private String operationDept;
	/**
	 * 运维部门名称
	 */
	@ApiModelProperty(value = "运维部门名称")
	@NotBlank(message = "运维部门名称不能为空")
	private String operationDeptName;
	/**
	 * 运维责任人
	 */
	@ApiModelProperty(value = "运维责任人")
	@NotBlank(message = "运维责任人不能为空")
	private String operationUse;
	/**
	 * 运维责任人姓名
	 */
	@ApiModelProperty(value = "运维责任人姓名")
	@NotBlank(message = "运维责任人姓名不能为空")
	private String operationUseName;
	/**
	 * 运维联系电话
	 */
	@ApiModelProperty(value = "运维联系电话")
	@NotBlank(message = "运维联系电话不能为空")
	private String operationPhone;
	/**
	 * 设备数量
	 */
	@ApiModelProperty(value = "设备数量")
	private Integer operationNum;
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

	@ApiModelProperty(value = "产权单位")
	private String ownerUnit;

	@ApiModelProperty(value = "产权单位编码")
	private String ownerUnitCode;

	@ApiModelProperty(value = "产权部门")
	private String propertyDept;

	@ApiModelProperty(value = "产权部门编码")
	private String propertyDeptCode;
}
