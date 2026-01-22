
package com.lnsoft.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.constant.RoleConstant;
import com.lnsoft.core.tool.node.ForestNodeMerger;
import com.lnsoft.core.tool.utils.CollectionUtil;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.system.entity.Role;
import com.lnsoft.system.entity.RoleMenu;
import com.lnsoft.system.entity.RoleScope;
import com.lnsoft.system.mapper.RoleMapper;
import com.lnsoft.system.service.IRoleMenuService;
import com.lnsoft.system.service.IRoleScopeService;
import com.lnsoft.system.service.IRoleService;
import com.lnsoft.system.vo.RoleVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.security.auth.callback.LanguageCallback;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author guozhao
 */
@Service
@Validated
@AllArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

	private final IRoleMenuService roleMenuService;
	private final IRoleScopeService roleScopeService;

	@Override
	public IPage<RoleVO> selectRolePage(IPage<RoleVO> page, RoleVO role) {
		return page.setRecords(baseMapper.selectRolePage(page, role));
	}

	@Override
	public List<RoleVO> tree(String tenantId) {
		String userRole = SecureUtil.getUserRole();
		String excludeRole = null;
		if (!CollectionUtil.contains(Func.toStrArray(userRole), RoleConstant.ADMIN)) {
			excludeRole = RoleConstant.ADMIN;
		}
		return ForestNodeMerger.merge(baseMapper.tree(tenantId, excludeRole));
	}

	@Override
	public boolean grant(@NotEmpty List<Long> roleIds, @NotEmpty List<Long> menuIds, List<Long> dataScopeIds) {
		// 删除角色配置的菜单集合
		roleMenuService.remove(Wrappers.<RoleMenu>update().lambda().in(RoleMenu::getRoleId, roleIds));
		// 组装配置
		List<RoleMenu> roleMenus = new ArrayList<>();
		roleIds.forEach(roleId -> menuIds.forEach(menuId -> {
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setRoleId(roleId);
			roleMenu.setMenuId(menuId);
			roleMenus.add(roleMenu);
		}));
		// 新增配置
		roleMenuService.saveBatch(roleMenus);

		// 删除角色配置的数据权限集合
		roleScopeService.remove(Wrappers.<RoleScope>update().lambda().in(RoleScope::getRoleId, roleIds));
		// 组装配置
		List<RoleScope> roleDataScopes = new ArrayList<>();
		roleIds.forEach(roleId -> dataScopeIds.forEach(scopeId -> {
			RoleScope roleScope = new RoleScope();
			roleScope.setRoleId(roleId);
			roleScope.setScopeId(scopeId);
			roleDataScopes.add(roleScope);
		}));
		// 新增配置
		roleScopeService.saveBatch(roleDataScopes);

		return true;
	}

	@Override
	public String getRoleIds(String tenantId, String roleNames) {
		List<Role> roleList = baseMapper.selectList(Wrappers.<Role>query().lambda().eq(Role::getTenantId, tenantId).in(Role::getRoleName, Func.toStrList(roleNames)));
		if (roleList != null && roleList.size() > 0) {
			return roleList.stream().map(role -> Func.toStr(role.getId())).distinct().collect(Collectors.joining(","));
		}
		return null;
	}

	@Override
	public List<String> getRoleNames(String roleIds) {
		return baseMapper.getRoleNames(Func.toLongArray(roleIds));
	}

	/**
	 * 根据角色名称获取角色信息
	 *
	 * @param roleName 角色名称
	 * @return Role
	 */
	@Override
	public Role getRoleByRoleName(String roleName) {
		return baseMapper.selectOne(new LambdaQueryWrapper<Role>().eq(Role::getRoleName, roleName));
	}

	@Override
	public List<Role> getByPid(Long roleId) {
		LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
		roleLambdaQueryWrapper.eq(Role::getParentId,roleId).eq(Role::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
		List<Role> roleList = baseMapper.selectList(roleLambdaQueryWrapper);
		return roleList;
	}

}
