package com.lnsoft.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO implements Serializable {
	private static final long serialVersionUID = 8680675658055660795L;

	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	private String userId;
	/**
	 * 用户名称
	 */
	@ApiModelProperty(value = "用户名称")
	private String userName;
	/**
	 * 用户ISC账号
	 */
	@ApiModelProperty(value = "用户ISC账号")
	private String userIscAccount;
	/**
	 * 用户联系方式
	 */
	@ApiModelProperty(value = "用户联系方式")
	private String phone;
	/**
	 * 区域Code
	 */
	@ApiModelProperty(value = "区域Code")
	private String regionCode;
	@ApiModelProperty(value = "区域CodeFull")
	private String regionCodeFull;
	/**
	 * 区域名称
	 */
	@ApiModelProperty(value = "区域名称")
	private String regionName;
	/**
	 * 用户所在部门id
	 */
	@ApiModelProperty(value = "用户所在部门id")
	private String deptId;
	/**
	 * 用户所在部门名称
	 */
	@ApiModelProperty(value = "用户所在部门名称")
	private String deptName;
	/**
	 * 产权单位
	 */
	@ApiModelProperty(value = "产权单位")
	private String ownerUnit;
	/**
	 * 产权单位名称
	 */
	@ApiModelProperty(value = "产权单位名称")
	private String ownerUnitName;
	/**
	 * 所属单位
	 */
	private String institutionCode;
	private String institutionName;
	/**
	 * 产权部门
	 */
	@ApiModelProperty(value = "产权部门")
	private String propertyDept;
	/**
	 * 产权部门名称
	 */
	@ApiModelProperty(value = "产权部门名称")
	private String propertyDeptName;
	/**
	 * 使用保管部门
	 */
	@ApiModelProperty(value = "使用保管部门")
	private String useKeepDept;
	/**
	 * 使用保管部门名称
	 */
	@ApiModelProperty(value = "使用保管部门名称")
	private String useKeepDeptName;
	/**
	 * 实物保管部门
	 */
	@ApiModelProperty(value = "实物保管部门")
	private String entityKeepDept;
	/**
	 * 实物保管部门名称
	 */
	@ApiModelProperty(value = "实物保管部门名称")
	private String entityKeepDeptName;
	/**
	 * 班组id
	 */
	@ApiModelProperty(value = "班组id")
	private String groupId;
	/**
	 * 班组名称
	 */
	@ApiModelProperty(value = "班组名称")
	private String groupName;
	/**
	 * 系统时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "系统时间")
	private LocalDate systemTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "系统时间")
	private LocalDateTime systemDateTime;

	@ApiModelProperty(value = "erp维护工厂")
	private String erpUnit;

	@ApiModelProperty(value = "erp维护工厂编码")
	private String erpUnitCode;

	@ApiModelProperty(value = "erp成本中心")
	private String erpDept;

	@ApiModelProperty(value = "erp成本中心编码")
	private String erpDeptCode;

	@ApiModelProperty(value = "i6000单位")
	private String i6000Unit;

	@ApiModelProperty(value = "i6000单位编码")
	private String i6000UnitCode;

	@ApiModelProperty(value = "i6000部门")
	private String i6000Dept;

	@ApiModelProperty(value = "i6000部门编码")
	private String i6000DeptCode;

	@ApiModelProperty(value = "是否是运维 0 是 1 否")
	private Integer userRoleFlag;

	@ApiModelProperty(value = "是否是数字化部 0 是 1 否")
	private Integer digitalFlag;

	@ApiModelProperty(value = "用户身份证号")
	private String userIdCard;

	@ApiModelProperty(value = "用户真实姓名")
	private String realName;

	@ApiModelProperty(value = "用户头像")
	private String avatar;

	@ApiModelProperty(value = "部门全称")
	private String fullName;

	@ApiModelProperty(value = "角色集合")
	private String roleAlias;

	@ApiModelProperty(value = "是否是问题反馈人员 0 是 1 否")
	private Integer questionFlag;

	@ApiModelProperty(value = "是否是第一次登录 0 是 1 否")
	private Integer isLogin;
}
