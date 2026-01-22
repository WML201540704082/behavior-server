
package com.lnsoft.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体类
 *
 * @author guozhao
 */
@Data
@TableName("idevelop_dept")
@ApiModel(value = "Dept对象", description = "Dept对象")
public class Dept implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 租户ID
	 */
	@ApiModelProperty(value = "租户ID")
	private String tenantId;

	/**
	 * 父主键
	 */
	@ApiModelProperty(value = "父主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;

	/**
	 * 祖级机构主键
	 */
	@ApiModelProperty(value = "祖级机构主键")
	private String ancestors;

	/**
	 * 部门名
	 */
	@ApiModelProperty(value = "部门名")
	private String deptName;

	/**
	 * 部门全称
	 */
	@ApiModelProperty(value = "部门全称")
	private String fullName;

	/**
	 * 区域编码
	 */
	@ApiModelProperty(value = "区域编码")
	private String regionCode;
	/**
	 * 区域名称
	 */
	@ApiModelProperty(value = "区域名称")
	private String regionName;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型 CORP/DEPT,单位/部门")
	private String type;

	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	private Integer sort;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	/**
	 * erp维护工厂
	 */
	@ApiModelProperty(value = "erp维护工厂")
	private String erpUnit;

	/**
	 * erp维护工厂编码
	 */
	@ApiModelProperty(value = "erp维护工厂编码")
	private String erpUnitCode;

	/**
	 * erp成本中心
	 */
	@ApiModelProperty(value = "erp成本中心")
	private String erpDept;

	/**
	 * erp成本中心编码
	 */
	@ApiModelProperty(value = "erp成本中心编码")
	private String erpDeptCode;

	/**
	 * i6000单位
	 */
	@ApiModelProperty(value = "i6000单位")
	private String i6000Unit;

	/**
	 * i6000单位编码
	 */
	@ApiModelProperty(value = "i6000单位编码")
	private String i6000UnitCode;

	/**
	 * i6000部门
	 */
	@ApiModelProperty(value = "i6000部门")
	private String i6000Dept;

	/**
	 * i6000部门编码
	 */
	@ApiModelProperty(value = "i6000部门编码")
	private String i6000DeptCode;

	/**
	 * isc部门ID
	 */
	@ApiModelProperty(value = "isc部门ID")
	private String iscDeptId;

	/**
	 * isc部门父ID
	 */
	@ApiModelProperty(value = "isc部门父ID")
	private String iscParentId;

	/**
	 * 班组负责人
	 */
	@ApiModelProperty(value = "班组负责人")
	private String teamUser;

	/**
	 * 班组负责人联系电话
	 */
	@ApiModelProperty(value = "班组负责人联系电话")
	private String teamPhone;

	/**
	 * 是否已删除
	 */
	@TableLogic
	@ApiModelProperty(value = "是否已删除")
	private Integer isDeleted;


}
