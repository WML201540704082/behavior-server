
package com.lnsoft.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.system.entity.Tenant;
import com.lnsoft.core.mp.base.BaseService;

/**
 * 服务类
 *
 * @author guozhao
 */
public interface ITenantService extends BaseService<Tenant> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param tenant
	 * @return
	 */
	IPage<Tenant> selectTenantPage(IPage<Tenant> page, Tenant tenant);

	/**
	 * 根据租户编号获取实体
	 *
	 * @param tenantId
	 * @return
	 */
	Tenant getByTenantId(String tenantId);

	/**
	 * 新增
	 *
	 * @param tenant
	 * @return
	 */
	boolean saveTenant(Tenant tenant);

}
