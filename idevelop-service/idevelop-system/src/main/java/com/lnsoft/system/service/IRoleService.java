
package com.lnsoft.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lnsoft.system.entity.Role;
import com.lnsoft.system.vo.RoleVO;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 服务类
 *
 * @author guozhao
 */
public interface IRoleService extends IService<Role> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param role
	 * @return
	 */
	IPage<RoleVO> selectRolePage(IPage<RoleVO> page, RoleVO role);

	/**
	 * 树形结构
	 *
	 * @param tenantId
	 * @return
	 */
	List<RoleVO> tree(String tenantId);

	/**
	 * 权限配置
	 *
	 * @param roleIds 角色id集合
	 * @param menuIds 菜单id集合
	 * @return 是否成功
	 */
	boolean grant(@NotEmpty List<Long> roleIds, @NotEmpty List<Long> menuIds, List<Long> dataScopeIds);

	/**
	 * 获取角色ID
	 *
	 * @param tenantId
	 * @param roleNames
	 * @return
	 */
	String getRoleIds(String tenantId, String roleNames);

	/**
	 * 获取角色名
	 *
	 * @param roleIds
	 * @return
	 */
	List<String> getRoleNames(String roleIds);

	/**
	 * 根据角色名称获取角色信息
	 *
	 * @param roleName 角色名称
	 * @return Role
	 */
	Role getRoleByRoleName(String roleName);

	/**
	 * 根据父id获取角色列表
	 * @param roleId
	 * @return
	 */
	List<Role> getByPid(Long roleId);
}
