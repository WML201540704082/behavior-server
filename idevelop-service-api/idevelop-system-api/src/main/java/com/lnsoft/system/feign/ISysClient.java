
package com.lnsoft.system.feign;

import com.lnsoft.core.launch.constant.AppConstant;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.entity.Role;
import com.lnsoft.system.entity.Tenant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Feign接口类
 *
 * @author guozhao
 */
@FeignClient(
	value = AppConstant.APPLICATION_SYSTEM_NAME,
	fallback = ISysClientFallback.class
)
public interface ISysClient {

	String API_PREFIX = "/sys";


	/**
	 * 获取部门
	 *
	 * @param id 主键
	 * @return Dept
	 */
	@GetMapping(API_PREFIX + "/getDept")
	Dept getDept(@RequestParam("id") Long id);

	/**
	 * 获取部门名
	 *
	 * @param id 主键
	 * @return 部门名
	 */
	@GetMapping(API_PREFIX + "/getDeptName")
	String getDeptName(@RequestParam("id") Long id);

	/**
	 * 获取部门id
	 *
	 * @param tenantId  租户id
	 * @param deptNames 部门名
	 * @return 部门id
	 */
	@GetMapping(API_PREFIX + "/getDeptIds")
	String getDeptIds(@RequestParam("tenantId") String tenantId, @RequestParam("deptNames") String deptNames);

	/**
	 * 获取部门名
	 *
	 * @param deptIds 主键
	 * @return
	 */
	@GetMapping(API_PREFIX + "/getDeptNames")
	List<String> getDeptNames(@RequestParam("deptIds") String deptIds);

	/**
	 * 根据部门ID获取最近的上级单位信息
	 *
	 * @param id 主键
	 * @return Dept
	 */
	@GetMapping(API_PREFIX + "/getCorp")
	Dept getCorp(@RequestParam("id") Long id);

	/**
	 * 根据部门ID获取所在班组或部门信息
	 *
	 * @param id 主键
	 * @return Dept
	 */
	@GetMapping(API_PREFIX + "/getDeptOrGroup")
	Dept getDeptOrGroup(@RequestParam("id") Long id);

	/**
	 * 根据部门ID获取最近的上级部门信息
	 *
	 * @param id 主键
	 * @return Dept
	 */
	@GetMapping(API_PREFIX + "/getDeptByGroup")
	Dept getDeptByGroup(@RequestParam("id") Long id);

	/**
	 * 获取岗位id
	 *
	 * @param tenantId  租户id
	 * @param postNames 岗位名
	 * @return 岗位id
	 */
	@GetMapping(API_PREFIX + "/getPostIds")
	String getPostIds(@RequestParam("tenantId") String tenantId, @RequestParam("postNames") String postNames);

	/**
	 * 获取岗位名
	 *
	 * @param postIds 主键
	 * @return
	 */
	@GetMapping(API_PREFIX + "/getPostNames")
	List<String> getPostNames(@RequestParam("postIds") String postIds);


	/**
	 * 获取角色
	 *
	 * @param id 主键
	 * @return Role
	 */
	@GetMapping(API_PREFIX + "/getRole")
	Role getRole(@RequestParam("id") Long id);

	/**
	 * 获取角色id
	 *
	 * @param tenantId  租户id
	 * @param roleNames 角色名
	 * @return 角色id
	 */
	@GetMapping(API_PREFIX + "/getRoleIds")
	String getRoleIds(@RequestParam("tenantId") String tenantId, @RequestParam("roleNames") String roleNames);

	/**
	 * 获取角色名
	 *
	 * @param id 主键
	 * @return 角色名
	 */
	@GetMapping(API_PREFIX + "/getRoleName")
	String getRoleName(@RequestParam("id") Long id);

	/**
	 * 获取角色名
	 *
	 * @param roleIds 主键
	 * @return
	 */
	@GetMapping(API_PREFIX + "/getRoleNames")
	List<String> getRoleNames(@RequestParam("roleIds") String roleIds);

	/**
	 * 获取角色别名
	 *
	 * @param id 主键
	 * @return 角色别名
	 */
	@GetMapping(API_PREFIX + "/getRoleAlias")
	String getRoleAlias(@RequestParam("id") Long id);

	/**
	 * 获取租户
	 *
	 * @param id 主键
	 * @return Tenant
	 */
	@GetMapping(API_PREFIX + "/tenant")
	R<Tenant> getTenant(@RequestParam("id") Long id);

	/**
	 * 获取租户
	 *
	 * @param tenantId 租户id
	 * @return Tenant
	 */
	@GetMapping(API_PREFIX + "/tenant-id")
	R<Tenant> getTenant(@RequestParam("tenantId") String tenantId);

	/**
	 * 根据角色名称获取角色信息
	 *
	 * @param roleName 角色名称
	 * @return Role
	 */
	@GetMapping(API_PREFIX + "/getRoleByRoleName")
	Role getRoleByRoleName(@RequestParam("roleName") String roleName);

}
