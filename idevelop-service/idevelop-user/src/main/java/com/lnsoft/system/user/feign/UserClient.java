
package com.lnsoft.system.user.feign;

import com.lnsoft.core.tool.api.R;
import com.lnsoft.system.user.entity.User;
import com.lnsoft.system.user.entity.UserDTO;
import com.lnsoft.system.user.entity.UserInfo;
import com.lnsoft.system.user.entity.UserOauth;
import com.lnsoft.system.user.service.IUserService;
import com.lnsoft.system.user.vo.BpmUserVO;
import com.lnsoft.system.user.vo.UserVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户服务Feign实现类
 *
 * @author guozhao
 */
@RestController
@AllArgsConstructor
public class UserClient implements IUserClient {

	private IUserService service;

	@Override
	public R<UserInfo> userInfo(Long userId) {
		return R.data(service.userInfo(userId));
	}

	@Override
	@GetMapping(API_PREFIX + "/user-info")
	public R<UserInfo> userInfo(String tenantId, String account, String password) {
		return R.data(service.userInfo(tenantId, account, password));
	}

	@Override
	@GetMapping(API_PREFIX + "/user-info2")
	public R<UserInfo> userInfo(String tenantId, String account) {
		return R.data(service.userInfo(tenantId, account));
	}

	@Override
	@PostMapping(API_PREFIX + "/user-auth-info")
	public R<UserInfo> userAuthInfo(UserOauth userOauth) {
		return R.data(service.userInfo(userOauth));
	}

	@Override
	@PostMapping(API_PREFIX + "/save-user")
	public R<Boolean> saveUser(User user) {
		return R.data(service.save(user));
	}

	@Override
	@GetMapping(API_PREFIX + "/user-info-batch")
	public R<List<BpmUserVO>> batchQueryUserInfo(String userId) {
		return R.data(service.batchQueryUserInfo(userId));
	}
	@Override
	public R<UserInfo> getUserByRole(String roleName, Long userId) {
		return R.data(service.getUserByRole(roleName, userId));
	}

	@Override
	@GetMapping(API_PREFIX + "/get-user-by-dept")
	public R<List<BpmUserVO>> selectUserListByDept(String orderDept, String hussarUserList) {
		return R.data(service.selectUserListByDept(orderDept, hussarUserList));
	}

	@Override
	@GetMapping(API_PREFIX + "/get-user-and-group")
	public R<List<UserDTO>> getUserByIdCard(String realName, String deptId,String idcard) {
		return R.data(service.getUserByIdCard(realName,deptId,idcard));
	}

	@Override
	@GetMapping(API_PREFIX + "/dept-user-list")
	public R<List<UserVO>> getDeptUserList(String deptIdList) {
		return R.data(service.getDeptUserList(deptIdList));
	}
}
