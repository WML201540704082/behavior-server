
package com.lnsoft.system.user.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseService;
import com.lnsoft.system.user.entity.User;
import com.lnsoft.system.user.entity.UserDTO;
import com.lnsoft.system.user.entity.UserInfo;
import com.lnsoft.system.user.entity.UserOauth;
import com.lnsoft.system.user.excel.UserExcel;
import com.lnsoft.system.user.vo.BpmUserVO;
import com.lnsoft.system.user.vo.UserVO;

import java.util.List;

/**
 * 服务类
 *
 * @author guozhao
 */
public interface IUserService extends BaseService<User> {

	/**
	 * 新增或修改用户
	 * @param user
	 * @return
	 */
	boolean submit(User user);

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param user
	 * @return
	 */
	IPage<User> selectUserPage(IPage<User> page, User user);

	/**
	 * 用户信息
	 *
	 * @param userId
	 * @return
	 */
	UserInfo userInfo(Long userId);

	/**
	 * 用户信息
	 *
	 * @param tenantId
	 * @param account
	 * @return
	 */
	UserInfo userInfo(String tenantId, String account);

	/**
	 * 用户信息
	 *
	 * @param tenantId
	 * @param account
	 * @param password
	 * @return
	 */
	UserInfo userInfo(String tenantId, String account, String password);

	/**
	 * 用户信息
	 *
	 * @param userOauth
	 * @return
	 */
	UserInfo userInfo(UserOauth userOauth);

	/**
	 * 给用户设置角色
	 *
	 * @param userIds
	 * @param roleIds
	 * @return
	 */
	boolean grant(String userIds, String roleIds);

	/**
	 * 初始化密码
	 *
	 * @param userIds
	 * @return
	 */
	String resetPassword(String userIds);

	/**
	 * 修改密码
	 *
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @param newPassword1
	 * @return
	 */
	Boolean updatePassword(Long userId, String oldPassword, String newPassword, String newPassword1,Long updateId);

	/**
	 * 获取角色名
	 *
	 * @param roleIds
	 * @return
	 */
	List<String> getRoleName(String roleIds);

	/**
	 * 获取部门名
	 *
	 * @param deptIds
	 * @return
	 */
	List<String> getDeptName(String deptIds);

	/**
	 * 导入用户数据
	 *
	 * @param data
	 * @return
	 */
	void importUser(List<UserExcel> data);

	/**
	 * 获取导出用户数据
	 *
	 * @param queryWrapper
	 * @return
	 */
	List<UserExcel> exportUser(Wrapper<User> queryWrapper);

	/**
	 * 注册用户
	 *
	 * @param user
	 * @param oauthId
	 * @return
	 */
	boolean registerGuest(User user, Long oauthId);

	/**
	 * 根据userId批量查询用户信息（流程使用，用户名，手机号）
	 *
	 * @param userId userId
	 * @return List
	 */
	List<BpmUserVO> batchQueryUserInfo(String userId);

	/**
	 * 根据角色获取用户
	 * @param roleName
	 * @return
	 */
	UserInfo getUserByRole(String roleName, Long userId);

	/**
	 * 获取部门下的用户
	 *
	 * @param orderDept      部门id
	 * @param hussarUserList 用户信息（String)
	 * @return List
	 */
	List<BpmUserVO> selectUserListByDept(String orderDept, String hussarUserList);

	/**
	 * 根据名字和部门身份证号获取用户信息
	 * @param realName
	 * @param deptId
	 * @param idcard
	 * @return
	 */
	List<UserDTO> getUserByIdCard(String realName, String deptId,String idcard);

	/**
	 * 获取部门用户信息
	 *
	 * @param deptIdList 部门id
	 * @return List
	 */
	List<UserVO> getDeptUserList(String deptIdList);

	/**
	 * 获取区域名称
	 * @param regionCode
	 * @return
	 */
	List<String> getRegionName(String regionCode);


	String mask(String str, int prefix, int suffix);
}
