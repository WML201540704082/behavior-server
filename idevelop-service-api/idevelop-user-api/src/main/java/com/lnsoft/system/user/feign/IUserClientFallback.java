
package com.lnsoft.system.user.feign;

import com.lnsoft.core.tool.api.R;
import com.lnsoft.system.user.entity.User;
import com.lnsoft.system.user.entity.UserDTO;
import com.lnsoft.system.user.entity.UserInfo;
import com.lnsoft.system.user.entity.UserOauth;
import com.lnsoft.system.user.vo.BpmUserVO;
import com.lnsoft.system.user.vo.UserVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Feign失败配置
 *
 * @author guozhao
 */
@Component
public class IUserClientFallback implements IUserClient {

	@Override
	public R<UserInfo> userInfo(Long userId) {
		return R.fail("未获取到账号信息");
	}

	@Override
	public R<UserInfo> userInfo(String tenantId, String account, String password) {
		return R.fail("未获取到账号信息");
	}

	@Override
	public R<UserInfo> userInfo(String tenantId, String account) {
		return R.fail("未获取到账号信息");
	}

	@Override
	public R<UserInfo> userAuthInfo(UserOauth userOauth) {
		return R.fail("未获取到账号信息");
	}

	@Override
	public R<Boolean> saveUser(User user) {
		return R.fail("创建用户失败");
	}

	@Override
	public R<List<BpmUserVO>> batchQueryUserInfo(String userId) {
		return R.fail("获取用户信息失败");
	}

	@Override
	public R<UserInfo> getUserByRole(String roleName, Long userId) {
		return R.fail("未获取到用户信息");
	}

	@Override
	public R<List<BpmUserVO>> selectUserListByDept(String orderDept, String hussarUserList) {
		return R.fail("获取用户信息失败");
	}

	@Override
	public R<List<UserDTO>> getUserByIdCard(String realName, String deptId, String idcard){
		return R.fail("获取用户信息失败");
	}

	@Override
	public R<List<UserVO>> getDeptUserList(String deptIdList) {
		return R.fail("获取用户信息失败");
	}

}
