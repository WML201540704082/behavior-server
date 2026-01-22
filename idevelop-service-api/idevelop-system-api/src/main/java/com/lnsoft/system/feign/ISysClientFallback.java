
package com.lnsoft.system.feign;

import com.lnsoft.core.tool.api.R;
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.entity.Role;
import com.lnsoft.system.entity.Tenant;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Feign失败配置
 *
 * @author guozhao
 */
@Component
public class ISysClientFallback implements ISysClient {

	@Override
	public Dept getDept(Long id) {
		return null;
	}

	@Override
	public String getDeptName(Long id) {
		return null;
	}

	@Override
	public String getDeptIds(String tenantId, String deptNames) {
		return null;
	}

	@Override
	public List<String> getDeptNames(String deptIds) {
		return null;
	}

	@Override
	public Dept getCorp(Long id) {
		return null;
	}

	@Override
	public Dept getDeptOrGroup(Long id) {
		return null;
	}

	@Override
	public Dept getDeptByGroup(Long id) {
		return null;
	}

	@Override
	public String getPostIds(String tenantId, String postNames) {
		return null;
	}

	@Override
	public List<String> getPostNames(String postIds) {
		return null;
	}

	@Override
	public Role getRole(Long id) {
		return null;
	}

	@Override
	public String getRoleIds(String tenantId, String roleNames) {
		return null;
	}

	@Override
	public String getRoleName(Long id) {
		return null;
	}

	@Override
	public List<String> getRoleNames(String roleIds) {
		return null;
	}

	@Override
	public String getRoleAlias(Long id) {
		return null;
	}

	@Override
	public R<Tenant> getTenant(Long id) {
		return null;
	}

	@Override
	public R<Tenant> getTenant(String tenantId) {
		return null;
	}

	@Override
	public Role getRoleByRoleName(String roleName) {
		return null;
	}

}
