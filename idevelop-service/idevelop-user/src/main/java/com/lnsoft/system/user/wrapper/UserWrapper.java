
package com.lnsoft.system.user.wrapper;

import com.lnsoft.system.feign.IDictClient;
import com.lnsoft.system.user.entity.User;
import com.lnsoft.system.user.vo.UserVO;
import com.lnsoft.core.mp.support.BaseEntityWrapper;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.BeanUtil;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.core.tool.utils.SpringUtil;
import com.lnsoft.system.user.service.IUserService;

import java.util.List;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author guozhao
 */
public class UserWrapper extends BaseEntityWrapper<User, UserVO> {

	private static IUserService userService;

	private static IDictClient dictClient;

	static {
		userService = SpringUtil.getBean(IUserService.class);
		dictClient = SpringUtil.getBean(IDictClient.class);
	}

	public static UserWrapper build() {
		return new UserWrapper();
	}

	@Override
	public UserVO entityVO(User user) {
		UserVO userVO = BeanUtil.copy(user, UserVO.class);
		List<String> roleName = userService.getRoleName(user.getRoleId());
		List<String> deptName = userService.getDeptName(user.getDeptId());
		List<String> regionName = userService.getRegionName(user.getRegionCode());
		userVO.setRoleName(Func.join(roleName));
		userVO.setDeptName(Func.join(deptName));
		userVO.setRegionName(Func.join(regionName));
		R<String> dict = dictClient.getValue("sex", Func.toInt(user.getSex()));
		if (dict.isSuccess()) {
			userVO.setSexName(dict.getData());
		}
		return userVO;
	}

}
