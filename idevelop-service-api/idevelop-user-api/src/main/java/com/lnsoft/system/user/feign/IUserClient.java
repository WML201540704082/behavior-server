
package com.lnsoft.system.user.feign;


import com.lnsoft.core.launch.constant.AppConstant;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.system.user.entity.User;
import com.lnsoft.system.user.entity.UserDTO;
import com.lnsoft.system.user.entity.UserInfo;
import com.lnsoft.system.user.entity.UserOauth;
import com.lnsoft.system.user.vo.BpmUserVO;
import com.lnsoft.system.user.vo.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * User Feign接口类
 *
 * @author guozhao
 */
@FeignClient(
	value = AppConstant.APPLICATION_USER_NAME,
	fallback = IUserClientFallback.class
)
public interface IUserClient {

	String API_PREFIX = "/user";

	/**
	 * 获取用户信息
	 *
	 * @param userId 用户id
	 * @return
	 */
	@GetMapping(API_PREFIX + "/user-info-by-id")
	R<UserInfo> userInfo(@RequestParam("userId") Long userId);

	/**
	 * 获取用户信息
	 *
	 * @param tenantId 租户ID
	 * @param account  账号
	 * @param password 密码
	 * @return
	 */
	@GetMapping(API_PREFIX + "/user-info")
	R<UserInfo> userInfo(@RequestParam("tenantId") String tenantId, @RequestParam("account") String account, @RequestParam("password") String password);

	/**
	 * 获取用户信息
	 *
	 * @param tenantId 租户ID
	 * @param account  账号
	 * @return
	 */
	@GetMapping(API_PREFIX + "/user-info2")
	R<UserInfo> userInfo(@RequestParam("tenantId") String tenantId, @RequestParam("account") String account);


	/**
	 * 获取第三方平台信息
	 *
	 * @param userOauth 第三方授权用户信息
	 * @return UserInfo
	 */
	@PostMapping(API_PREFIX + "/user-auth-info")
	R<UserInfo> userAuthInfo(@RequestBody UserOauth userOauth);

	/**
	 * 新建用户
	 *
	 * @param user 用户实体
	 * @return
	 */
	@PostMapping(API_PREFIX + "/save-user")
	R<Boolean> saveUser(@RequestBody User user);

	/**
	 * 根据用户id批量获取用户信息（流程使用，用户名，手机号）
	 *
	 * @param userId 用户id（批量，逗号分隔开）
	 * @return R
	 */
	@GetMapping(API_PREFIX + "/user-info-batch")
	R<List<BpmUserVO>> batchQueryUserInfo(@RequestParam("userId") String userId);

	/**
	 * 根据角色获取用户信息
	 *
	 * @param roleName 用户实体
	 * @return
	 */
	@GetMapping(API_PREFIX + "/get-user-by-role")
	R<UserInfo> getUserByRole(@RequestParam("roleName") String roleName,@RequestParam("userId") Long userId);

	/**
	 * 获取部门下的用户信息
	 *
	 * @param orderDept      部门
	 * @param hussarUserList 用户信息
	 * @return List
	 */
	@GetMapping(API_PREFIX + "/get-user-by-dept")
	R<List<BpmUserVO>> selectUserListByDept(@RequestParam("orderDept") String orderDept,
											@RequestParam("hussarUserList") String hussarUserList);
	/**
	 * 根据名字和部门身份证号获取用户信息
	 * @param realName
	 * @param deptId
	 * @param idcard
	 * @return
	 */
	@GetMapping(API_PREFIX + "/get-user-and-group")
	R<List<UserDTO>> getUserByIdCard(@RequestParam("realName") String realName,
									 @RequestParam("deptId") String deptId,
									 @RequestParam("idcard")String idcard);

	/**
	 * 获取部门用户信息
	 *
	 * @param deptIdList 部门id
	 * @return R
	 */
	@GetMapping(API_PREFIX + "/dept-user-list")
	R<List<UserVO>> getDeptUserList(@RequestParam("deptIdList") String deptIdList);
}
