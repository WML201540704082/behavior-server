
package com.lnsoft.system.feign;

import com.lnsoft.core.tool.api.R;
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.entity.Role;
import com.lnsoft.system.entity.Tenant;
import com.lnsoft.system.service.IDeptService;
import com.lnsoft.system.service.IPostService;
import com.lnsoft.system.service.IRoleService;
import com.lnsoft.system.service.ITenantService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 系统服务Feign实现类
 *
 * @author guozhao
 */
@ApiIgnore
@RestController
@AllArgsConstructor
public class SysClient implements ISysClient {

	private IDeptService deptService;

	private IPostService postService;

	private IRoleService roleService;

	private ITenantService tenantService;

	@Override
	@GetMapping(API_PREFIX + "/getDept")
	public Dept getDept(Long id) {
		return deptService.getById(id);
	}

	@Override
	@GetMapping(API_PREFIX + "/getDeptName")
	public String getDeptName(Long id) {
		return deptService.getById(id).getDeptName();
	}

	@Override
	public String getDeptIds(String tenantId, String deptNames) {
		return deptService.getDeptIds(tenantId, deptNames);
	}

	@Override
	public List<String> getDeptNames(String deptIds) {
		return deptService.getDeptNames(deptIds);
	}

	@Override
	@GetMapping(API_PREFIX + "/getCorp")
	public Dept getCorp(Long id) {
		return deptService.getCorp(id);
	}

	@Override
	@GetMapping(API_PREFIX + "/getDeptOrGroup")
	public Dept getDeptOrGroup(Long id) {
		return deptService.getDeptOrGroup(id);
	}

	@Override
	@GetMapping(API_PREFIX + "/getDeptByGroup")
	public Dept getDeptByGroup(Long id) {
		return deptService.getDeptByGroup(id);
	}

	@Override
	public String getPostIds(String tenantId, String postNames) {
		return postService.getPostIds(tenantId, postNames);
	}

	@Override
	public List<String> getPostNames(String postIds) {
		return postService.getPostNames(postIds);
	}

	@Override
	@GetMapping(API_PREFIX + "/getRole")
	public Role getRole(Long id) {
		return roleService.getById(id);
	}

	@Override
	public String getRoleIds(String tenantId, String roleNames) {
		return roleService.getRoleIds(tenantId, roleNames);
	}

	@Override
	@GetMapping(API_PREFIX + "/getRoleName")
	public String getRoleName(Long id) {
		return roleService.getById(id).getRoleName();
	}

	@Override
	public List<String> getRoleNames(String roleIds) {
		return roleService.getRoleNames(roleIds);
	}

	@Override
	@GetMapping(API_PREFIX + "/getRoleAlias")
	public String getRoleAlias(Long id) {
		return roleService.getById(id).getRoleAlias();
	}

	@Override
	@GetMapping(API_PREFIX + "/tenant")
	public R<Tenant> getTenant(Long id) {
		return R.data(tenantService.getById(id));
	}

	@Override
	@GetMapping(API_PREFIX + "/tenant-id")
	public R<Tenant> getTenant(String tenantId) {
		return R.data(tenantService.getByTenantId(tenantId));
	}

	@Override
	@GetMapping(API_PREFIX + "/getRoleByRoleName")
	public Role getRoleByRoleName(String roleName) {
		return roleService.getRoleByRoleName(roleName);
	}
}
