
package com.lnsoft.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.lnsoft.system.user.entity.User;

/**
 * 视图实体类
 *
 * @author guozhao
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserVO对象", description = "UserVO对象")
public class UserVO extends User {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 角色名
	 */
	private String roleName;

	/**
	 * 部门名
	 */
	private String deptName;

	/**
	 * 岗位名
	 */
	private String postName;

	/**
	 * 性别
	 */
	private String sexName;
}
