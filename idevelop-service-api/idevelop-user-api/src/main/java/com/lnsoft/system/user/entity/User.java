
package com.lnsoft.system.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lnsoft.core.mp.base.TenantEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 实体类
 *
 * @author guozhao
 */
@Data
@TableName("idevelop_user")
@EqualsAndHashCode(callSuper = true)
public class User extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 编号
	 */
	private String code;
	/**
	 * 账号
	 */
	private String account;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 昵称
	 */
	private String name;
	/**
	 * 真名
	 */
	private String realName;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 手机
	 */
	private String phone;
	/**
	 * 生日
	 */
	private Date birthday;
	/**
	 * 性别
	 */
	private Integer sex;
	/**
	 * 角色id
	 */
	private String roleId;
	/**
	 * 部门id
	 */
	private String deptId;
	/**
	 * 部门名称
	 */
	@TableField(exist = false)
	private String deptName;
	/**
	 * 公司id
	 */
	@TableField(exist = false)
	private String corpId;
	/**
	 * 公司名称
	 */
	@TableField(exist = false)
	private String corpName;
	/**
	 * 单位名称全称
	 */
	@TableField(exist = false)
	private String corpFullName;
	/**
	 * 部门id
	 */
	private String postId;
	/**
	 * 区域名称
	 */
	@TableField(exist = false)
	private String regionName;
	/**
	 * 区域全称
	 */
	@TableField(exist = false)
	private String regionFullName;
	/**
	 * 区域code
	 */
	private String regionCode;
	/**
	 * 用户类型
	 */
	private String userType;

	/**
	 * erp维护工厂
	 */
	@TableField(exist = false)
	private String erpUnit;

	/**
	 * erp维护工厂编码
	 */
	@TableField(exist = false)
	private String erpUnitCode;

	/**
	 * erp成本中心
	 */
	@TableField(exist = false)
	private String erpDept;

	/**
	 * erp成本中心编码
	 */
	@TableField(exist = false)
	private String erpDeptCode;

	/**
	 * i6000单位
	 */
	@TableField(exist = false)
	private String i6000Unit;

	/**
	 * i6000单位编码
	 */
	@TableField(exist = false)
	private String i6000UnitCode;

	/**
	 * i6000部门
	 */
	@TableField(exist = false)
	private String i6000Dept;

	/**
	 * i6000部门编码
	 */
	@TableField(exist = false)
	private String i6000DeptCode;

	/**
	 * 扩展字段
	 */
	private String ext;

	/**
	 * 用户标签
	 */
	private String tag;

	/**
	 * 身份证
	 */
	private String idcard;

	/**
	 * 班组id
	 */
	@TableField(exist = false)
	private String groupId;

	/**
	 * 班组名称
	 */
	@TableField(exist = false)
	private String groupName;

	/**
	 * 是否是第一次登录
	 */
	private Integer isLogin;
}
