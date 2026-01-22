
package com.lnsoft.system.wrapper;

import com.lnsoft.common.constant.CommonConstant;
import com.lnsoft.system.entity.Role;
import com.lnsoft.system.vo.RoleVO;
import com.lnsoft.core.mp.support.BaseEntityWrapper;
import com.lnsoft.core.tool.node.ForestNodeMerger;
import com.lnsoft.core.tool.utils.BeanUtil;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.core.tool.utils.SpringUtil;
import com.lnsoft.system.service.IRoleService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author guozhao
 */
public class RoleWrapper extends BaseEntityWrapper<Role, RoleVO> {

	private static IRoleService roleService;

	static {
		roleService = SpringUtil.getBean(IRoleService.class);
	}

	public static RoleWrapper build() {
		return new RoleWrapper();
	}

	@Override
	public RoleVO entityVO(Role role) {
		RoleVO roleVO = BeanUtil.copy(role, RoleVO.class);
		if (Func.equals(role.getParentId(), CommonConstant.TOP_PARENT_ID)) {
			roleVO.setParentName(CommonConstant.TOP_PARENT_NAME);
		} else {
			Role parent = roleService.getById(role.getParentId());
			roleVO.setParentName(parent.getRoleName());
		}
		return roleVO;
	}

	public List<RoleVO> listNodeVO(List<Role> list) {
		List<RoleVO> collect = list.stream().map(this::entityVO).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
