
package com.lnsoft.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.annotation.PreAuth;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.CacheUtil;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.system.entity.Role;
import com.lnsoft.system.entity.RoleMenu;
import com.lnsoft.system.service.IRoleMenuService;
import com.lnsoft.system.service.IRoleService;
import com.lnsoft.system.vo.GrantVO;
import com.lnsoft.system.vo.RoleVO;
import com.lnsoft.system.wrapper.RoleWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.lnsoft.core.tool.utils.CacheUtil.SYS_CACHE;

/**
 * 控制器
 *
 * @author guozhao
 */
@RestController
@AllArgsConstructor
@RequestMapping("/role")
@Api(value = "角色", tags = "角色")
public class RoleController extends IdevelopController {

	private IRoleService roleService;
	private IRoleMenuService roleMenuService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入role")
	@PreAuth("hasPerm('role_add')")
	public R<RoleVO> detail(Role role) {
		Role detail = roleService.getOne(Condition.getQueryWrapper(role));
		return R.data(RoleWrapper.build().entityVO(detail));
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "roleName", value = "参数名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "roleAlias", value = "角色别名", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入role")
	@PreAuth("hasPerm('role_view')")
	public R<List<RoleVO>> list(@ApiIgnore @RequestParam Map<String, Object> role, IdevelopUser idevelopUser) {
		QueryWrapper<Role> queryWrapper = Condition.getQueryWrapper(role, Role.class);
		List<Role> list = roleService.list((!idevelopUser.getTenantId().equals(IdevelopConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Role::getTenantId, idevelopUser.getTenantId()) : queryWrapper);
		return R.data(RoleWrapper.build().listNodeVO(list));
	}

	/**
	 * 获取角色树形结构
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 3)
	@PreAuth("hasPerm('role_view')")
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<RoleVO>> tree(String tenantId, IdevelopUser idevelopUser) {
		List<RoleVO> tree = roleService.tree(Func.toStr(tenantId, idevelopUser.getTenantId()));
		return R.data(tree);
	}


	/**
	 * 获取指定角色树形结构
	 */
	@GetMapping("/tree-by-id")
	@ApiOperationSupport(order = 4)
	@PreAuth("hasPerm('role_view')")
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<RoleVO>> treeById(Long roleId, IdevelopUser idevelopUser) {
		Role role = roleService.getById(roleId);
		List<RoleVO> tree = roleService.tree(Func.notNull(role) ? role.getTenantId() : idevelopUser.getTenantId());
		return R.data(tree);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@PreAuth("hasPerm('role_add')")
	@ApiOperation(value = "新增或修改", notes = "传入role")
	public R submit(@Valid @RequestBody Role role, IdevelopUser user) {
		CacheUtil.clear(SYS_CACHE);
		if (Func.isEmpty(role.getId())) {
			role.setTenantId(user.getTenantId());
		}
		if (Objects.nonNull(role.getParentId()) && Objects.isNull(role.getId())) {
			Role parentRole = roleService.getById(role.getParentId());
			RoleMenu menu = new RoleMenu();
			menu.setRoleId(parentRole.getId());
			QueryWrapper<RoleMenu> queryWrapper = Condition.getQueryWrapper(menu);
			List<RoleMenu> roleMenuList = roleMenuService.list(queryWrapper);

			roleService.saveOrUpdate(role);

			List<RoleMenu> roleMenuI = roleMenuList.stream().map(item -> {
				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setMenuId(item.getMenuId());
				roleMenu.setRoleId(role.getId());
				return roleMenu;
			}).collect(Collectors.toList());
			if (ObjectUtil.isEmpty(roleMenuI)){
				return R.success("操作成功");
			}
			return R.status(roleMenuService.saveOrUpdateBatch(roleMenuI));
		}

		return R.status(roleService.saveOrUpdate(role));
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@PreAuth("hasPerm('role_delete')")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		CacheUtil.clear(SYS_CACHE);
		return R.status(roleService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 设置菜单权限
	 */
	@PostMapping("/grant")
	@ApiOperationSupport(order = 7)
	@PreAuth("hasPerm('role_add')")
	@ApiOperation(value = "权限设置", notes = "传入roleId集合以及menuId集合")
	public R grant(@RequestBody GrantVO grantVO) {
		CacheUtil.clear(SYS_CACHE);
		List<Long> roleIds = grantVO.getRoleIds();
		List<Long> list = new ArrayList<>();
		list.addAll(roleIds);
		for (Long roleId : roleIds) {
			List<Role> roleList = roleService.getByPid(roleId);
			if (ObjectUtil.isNotEmpty(roleList)) {
				List<Long> collect = roleList.stream().map(item -> item.getId()).collect(Collectors.toList());
				list.addAll(collect);
			}
		}
		boolean temp = roleService.grant(list, grantVO.getMenuIds(), grantVO.getDataScopeIds());
		return R.status(temp);
	}


	/**
	 * 批量修改 新增或修改 临时处理
	 */
	@PostMapping("/batch/submit")
	@ApiOperationSupport(order = 8)
	@PreAuth("hasPerm('role_add')")
	@ApiOperation(value = "新增或修改", notes = "传入roleList")
	public R submit(@Valid @RequestBody List<Role> roleList, IdevelopUser user) {
		for (Role role : roleList) {
			CacheUtil.clear(SYS_CACHE);
			if (Func.isEmpty(role.getId())) {
				role.setTenantId(user.getTenantId());
			}
			if (Objects.nonNull(role.getParentId()) && Objects.isNull(role.getId())) {
				Role parentRole = roleService.getById(role.getParentId());
				RoleMenu menu = new RoleMenu();
				menu.setRoleId(parentRole.getId());
				QueryWrapper<RoleMenu> queryWrapper = Condition.getQueryWrapper(menu);
				List<RoleMenu> roleMenuList = roleMenuService.list(queryWrapper);

				roleService.saveOrUpdate(role);

				List<RoleMenu> roleMenuI = roleMenuList.stream().map(item -> {
					RoleMenu roleMenu = new RoleMenu();
					roleMenu.setMenuId(item.getMenuId());
					roleMenu.setRoleId(role.getId());
					return roleMenu;
				}).collect(Collectors.toList());

				roleMenuService.saveOrUpdateBatch(roleMenuI);
			}
			roleService.saveOrUpdate(role);
		}
		return R.status(Boolean.TRUE);
	}

}
