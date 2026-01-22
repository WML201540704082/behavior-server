
package com.lnsoft.system.user.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.system.entity.Role;
import com.lnsoft.system.user.entity.User;
import com.lnsoft.system.user.entity.UserDTO;
import com.lnsoft.system.user.excel.UserExcel;
import com.lnsoft.system.user.vo.BpmUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author guozhao
 */
public interface UserMapper extends BaseMapper<User> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param user
	 * @return
	 */
	List<User> selectUserPage(IPage page, User user);

	/**
	 * 获取用户
	 *
	 * @param tenantId
	 * @param account
	 * @param password
	 * @return
	 */
	User getUser(String tenantId, String account, String password);

	/**
	 * 获取用户
	 *
	 * @param tenantId
	 * @param account
	 * @return
	 */
	User getUserInfo(String tenantId, String account);

	User getUserInfoById(Long id);

	/**
	 * 获取角色名
	 *
	 * @param ids
	 * @return
	 */
	List<String> getRoleName(String[] ids);

	/**
	 * 获取角色别名
	 *
	 * @param ids
	 * @return
	 */
	Role getRoleAlias(String[] ids);

	/**
	 * 获取部门名
	 *
	 * @param ids
	 * @return
	 */
	List<String> getDeptName(String[] ids);

	/**
	 * 获取导出用户数据
	 *
	 * @param queryWrapper
	 * @return
	 */
	List<UserExcel> exportUser(@Param("ew") Wrapper<User> queryWrapper);

	/**
	 * 根据角色获取用户
	 * @param roleName
	 */
    User getUserByRole(@Param("roleName") String roleName, @Param("userId") Long userId);

	/**
	 * 获取部门下的用户
	 *
	 * @param orderDept      部门id
	 * @param hussarUserList 用户信息（String)
	 * @return List
	 */
	List<BpmUserVO> selectUserListByDept(@Param("orderDept") String orderDept, @Param("hussarUserList") List<String> hussarUserList);
	/**
	 * 根据名字和部门身份证号获取用户信息
	 * @param realName
	 * @param deptId
	 * @param idcard
	 * @return
	 */
	List<UserDTO> getUserByIdCard(@Param("realName")String realName, @Param("deptId")String deptId,@Param("idcard")String idcard);
	/**
	 * 获取区域名
	 *
	 * @param ids
	 * @return
	 */
	List<String> getRegionName(String[] ids);

	/**
	 * 获取区域名
	 *
	 * @param ids
	 * @return
	 */
	List<String> getPermissions(String[] ids);
}
