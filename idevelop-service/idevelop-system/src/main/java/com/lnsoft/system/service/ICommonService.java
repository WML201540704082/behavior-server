package com.lnsoft.system.service;

import com.lnsoft.core.tool.api.R;
import com.lnsoft.system.user.vo.UserVO;
import com.lnsoft.system.vo.UserInfoVO;

import java.util.List;

public interface ICommonService {

	/**
	 * 获取当前登录用户信息
	 *
	 * @return R
	 */
	R<UserInfoVO> getUserInfo();

	/**
	 * 获取部门用户信息
	 *
	 * @param deptId 部门id
	 * @return R
	 */
	R<List<UserVO>> getDeptUserList(Long deptId);
}
