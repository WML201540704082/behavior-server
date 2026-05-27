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

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 设备申请表实体类
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@Data
@TableName("idevelop_device_apply")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceApply对象", description = "设备申请表")
public class DeviceApply extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private String id;
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
	 * 投运单号
	 */
	@ApiModelProperty(value = "投运单号")
	private String operationNo;
	/**
	 * 领用单位
	 */
	@ApiModelProperty(value = "领用单位")
	@NotBlank(message = "领用单位不能为空")
	private String receiveUnit;
	/**
	 * 领用单位名称
	 */
	@ApiModelProperty(value = "领用单位名称")
	@NotBlank(message = "领用单位名称不能为空")
	private String receiveUnitName;
	/**
	 * 设备分类
	 */
	@ApiModelProperty(value = "设备分类")
	@NotBlank(message = "设备分类不能为空")
	private String deviceCategory;
	/**
	 * 设备类型
	 */
	@ApiModelProperty(value = "设备类型")
	@NotBlank(message = "设备类型不能为空")
	private String deviceType;
	/**
	 * 设备分类名称
	 */
	@ApiModelProperty(value = "设备分类名称")
	@NotBlank(message = "设备分类名称不能为空")
	private String deviceCategoryName;
	/**
	 * 设备类型名称
	 */
	@ApiModelProperty(value = "设备类型名称")
	@NotBlank(message = "设备类型名称不能为空")
	private String deviceTypeName;
	/**
	 * 申请数量
	 */
	@ApiModelProperty(value = "申请数量")
	@NotNull(message = "申请数量不能为空")
	@Min(value = 1, message = "申请数量需要大于0")
	@Max(value = 999, message = "申请数量不能大于999")
	private Integer applyNum;
	/**
	 * 申请原因
	 */
	@ApiModelProperty(value = "申请原因")
	private String applyReason;
	/**
	 * 领用责任人
	 */
	@ApiModelProperty(value = "领用责任人")
	@NotBlank(message = "领用责任人不能为空")
	private String receiveDutyPerson;
	/**
	 * 领用责任人名称
	 */
	@ApiModelProperty(value = "领用责任人名称")
	@NotBlank(message = "领用责任人名称不能为空")
	private String receiveDutyPersonName;
	/**
	 * 领用责任人身份证号
	 */
	@ApiModelProperty(value = "领用责任人身份证号")
	private String receiveDutyCard;
	/**
	 * 领用责任人ISC账号
	 */
	@ApiModelProperty(value = "领用责任人ISC账号")
//	@NotBlank(message = "领用责任人ISC账号不能为空")
	private String receiveDutyIscAccount;
	/**
	 * 领用责任人联系方式
	 */
	@ApiModelProperty(value = "领用责任人联系方式")
	private String receiveDutyPhone;
	/**
	 * 领用责任部门
	 */
	@ApiModelProperty(value = "领用责任部门")
	@NotBlank(message = "领用责任部门不能为空")
	private String receiveDutyDept;
	/**
	 * 领用责任部门名称
	 */
	@ApiModelProperty(value = "领用责任部门")
	@NotBlank(message = "领用责任部门名称不能为空")
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
	 * 是否以旧换新 0 是 1 否
	 */
	@NotNull(message = "请选择是否以旧换新")
	@ApiModelProperty(value = "是否以旧换新 0 是 1 否")
	private String oldToNew;
	/**
	 * 是否立即投运 0 是 1 否
	 */
	@NotNull(message = "请选择是否立即投运")
	@ApiModelProperty(value = "是否立即投运 0 是 1 否")
	private String operation;
	/**
	 * 网络类型
	 */
	@ApiModelProperty(value = "网络类型")
	private String networkType;
	/**
	 * 网络类型
	 */
	@ApiModelProperty(value = "所属网络名称")
	private String networkTypeName;
	/**
	 * 受理人（申请人）
	 */
	@ApiModelProperty(value = "受理人（申请人）")
	@NotBlank(message = "受理人不能为空")
	private String applyUser;
	/**
	 * 受理人名称
	 */
	@ApiModelProperty(value = "受理人名称")
	@NotBlank(message = "受理人名称不能为空")
	private String applyUserName;
	/**
	 * 申请时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "申请时间")
	@NotNull(message = "申请时间不能为空")
	private Date applyDate;
	/**
	 * 流程发起时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "流程发起时间")
	private Date submitTime;
	/**
	 * 流程发起人
	 */
	@ApiModelProperty(value = "流程发起人")
	private String submitUser;
	/**
	 * 流程发起是否是数字化部 0 是 1 否
	 */
	@ApiModelProperty(value = "流程发起是否是数字化部 0 是 1 否")
	private Integer submitDigitalFlag;
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
	 * 是否临时使用 0 是 1 否
	 */
	@ApiModelProperty(value = "是否临时使用 0 是 1 否")
	private Integer temporaryType;
	/**
	 * 临时使用开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "临时使用开始时间")
	private Date temporaryStartTime;
	/**
	 * 临时使用结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "临时使用结束时间")
	private Date temporaryEndTime;
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
}
