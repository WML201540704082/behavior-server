package com.lnsoft.device.api.safeaccess.entity;

import com.lnsoft.device.dto.SafeaccessSwitcheDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SdnQingDaoNetWork{

	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 投运单号
	 */
	private String operationNo;
	/**
	 * 申请编号
	 */
	private String applyNo;
	/**
	 * 出库单号
	 */
	private String outboundNo;
	/**
	 * 是否以旧换新 0 是 1 否
	 */
	private String oldToNew;
	/**
	 * 是否立即投运 0 是 1 否
	 */
	private String operation;
	/**
	 * 设备分类
	 */
	private String deviceCategory;
	/**
	 * 设备类型
	 */
	private String deviceType;
	/**
	 * 设备分类名称
	 */
	private String deviceCategoryName;
	/**
	 * 设备类型名称
	 */
	private String deviceTypeName;
	/**
	 * 领用（申请）单位
	 */
	private String receiveUnit;
	/**
	 * 领用（申请）单位名称
	 */
	private String receiveUnitName;
	/**
	 * 领用（申请）部门
	 */
	private String receiveDutyDept;
	/**
	 * 领用（申请）部门名称
	 */
	private String receiveDutyDeptName;
	/**
	 * 领用责任班组
	 */
	private String receiveDutyGroup;
	/**
	 * 领用责任班组名称
	 */
	private String receiveDutyGroupName;
	/**
	 * 受理人（工单发起人）
	 */
	private String applyUser;
	/**
	 * 受理人名称
	 */
	private String applyUserName;
	/**
	 * 受理时间
	 */
	private String applyDate;
	/**
	 * 流程实例ID
	 */
	private String processInsId;
	/**
	 * 流程状态
	 */
	private String processStatus;
	/**
	 * 流程发起时间
	 */
	private String submitTime;
	/**
	 * 投运工单来源 0 设备申请生成  1 手动新增
	 */
	private String operationType;
	/**
	 * 区域编码
	 */
	private String regionCode;
	/**
	 * 运维单位
	 */
	private String operationUnit;
	/**
	 * 运维单位名称
	 */
	private String operationUnitName;
	/**
	 * 运维部门
	 */
	private String operationDept;
	/**
	 * 运维部门名称
	 */
	private String operationDeptName;
	/**
	 * 运维责任人
	 */
	private String operationUse;
	/**
	 * 运维责任人姓名
	 */
	private String operationUseName;
	/**
	 * 运维联系电话
	 */
	private String operationPhone;
	/**
	 * 设备数量
	 */
	private String operationNum;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建部门
	 */
	private String createDept;

	private String ownerUnit;

	private String ownerUnitCode;

	private String propertyDept;

	private String propertyDeptCode;

	private String syncSign;
	/**
	 * 子表交换机数据
	 */
	private List<SafeaccessSwitcheDTO> switchesList;


	private String createUser;


	private String createTime;

	private String updateUser;

	private String updateTime;

	private String opinion;

	private String user;

	/**
	 * 设备来源，01：单位自购，02：公司配发
	 */
	private String sourcr;
}
