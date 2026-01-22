
package com.lnsoft.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.system.vo.RoleMenuVO;
import com.lnsoft.system.entity.RoleMenu;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author guozhao
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

	/**
	 * 自定义分页
	 * @param page
	 * @param roleMenu
	 * @return
	 */
	List<RoleMenuVO> selectRoleMenuPage(IPage page, RoleMenuVO roleMenu);

}
