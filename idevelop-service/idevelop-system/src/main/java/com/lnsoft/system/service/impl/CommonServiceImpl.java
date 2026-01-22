package com.lnsoft.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lnsoft.common.tool.Base64Util;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.api.ResultCode;
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.service.ICommonService;
import com.lnsoft.system.service.IDeptService;
import com.lnsoft.system.user.entity.User;
import com.lnsoft.system.user.entity.UserInfo;
import com.lnsoft.system.user.feign.IUserClient;
import com.lnsoft.system.user.vo.UserVO;
import com.lnsoft.system.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Slf4j
@Service
public class CommonServiceImpl implements ICommonService {

	@Resource
	private IUserClient userClient;
	@Resource
	private IDeptService deptService;
	private static final Integer ORDER_NO_LENGTH = 6;
	private static final String PAD_STR = "0";

	/**
	 * 获取当前登录用户信息
	 *
	 * @return R
	 */
	@Override
	public R<UserInfoVO> getUserInfo() {
		IdevelopUser user = SecureUtil.getUser();
		UserInfoVO userInfoVO = new UserInfoVO();
		R<UserInfo> userInfoR = userClient.userInfo(user.getUserId());
		if (ResultCode.SUCCESS.getCode() == userInfoR.getCode()) {
			UserInfo userInfo = userInfoR.getData();
			User userFeign = userInfo.getUser();
			userInfoVO.setPhone(userFeign.getPhone());
			String urlToBase64 = null;
			try {
				urlToBase64 = Base64Util.imageUrlToBase64(userFeign.getAvatar());
			} catch (IOException e) {
				log.error("Base64转码失败");
			}
			userInfoVO.setAvatar(urlToBase64);
			if ("2".equals(userFeign.getUserType())) {
				userInfoVO.setUserIscAccount(userFeign.getAccount());
			}
		}
		userInfoVO.setUserId(user.getUserId().toString());
		userInfoVO.setUserName(user.getUserName());
		userInfoVO.setRealName(user.getRealName());
		userInfoVO.setRegionCode(user.getRegionCode());//StringUtils.rightPad(user.getRegionCode(), ORDER_NO_LENGTH, PAD_STR));
		userInfoVO.setRegionCodeFull(StringUtils.rightPad(user.getRegionCode(), ORDER_NO_LENGTH, PAD_STR));
		userInfoVO.setRegionName(user.getRegionName());
		userInfoVO.setDeptId(user.getDeptId());
		userInfoVO.setDeptName(user.getDeptName());
		userInfoVO.setOwnerUnit(user.getCorpId());
		userInfoVO.setOwnerUnitName(user.getCorpName());
		userInfoVO.setInstitutionCode(user.getCorpId());
		userInfoVO.setInstitutionName(user.getCorpName());
		userInfoVO.setPropertyDept(user.getDeptId());
		userInfoVO.setPropertyDeptName(user.getDeptName());
		userInfoVO.setUseKeepDept(user.getDeptId());
		userInfoVO.setUseKeepDeptName(user.getDeptName());
		userInfoVO.setEntityKeepDept(user.getDeptId());
		userInfoVO.setEntityKeepDeptName(user.getDeptName());
		userInfoVO.setSystemTime(LocalDate.now());
		userInfoVO.setSystemDateTime(LocalDateTime.now());
		userInfoVO.setErpUnit(user.getErpUnit());
		userInfoVO.setErpUnitCode(user.getErpUnitCode());
		userInfoVO.setI6000Unit(user.getI6000Unit());
		userInfoVO.setI6000UnitCode(user.getI6000UnitCode());
		userInfoVO.setErpDept(user.getErpDept());
		userInfoVO.setErpDeptCode(user.getErpDeptCode());
		userInfoVO.setI6000Dept(user.getI6000Dept());
		userInfoVO.setI6000DeptCode(user.getI6000DeptCode());
		userInfoVO.setDigitalFlag(user.getExt().get("tag").toString().equals("1") ? 0 : 1);
		userInfoVO.setUserIdCard(Objects.isNull(user.getExt().get("idcard")) ? "" : user.getExt().get("idcard").toString());
		userInfoVO.setGroupId(Objects.isNull(user.getExt().get("groupId")) ? "" : user.getExt().get("groupId").toString());
		userInfoVO.setGroupName(Objects.isNull(user.getExt().get("groupName")) ? "" : user.getExt().get("groupName").toString());
		userInfoVO.setFullName(Objects.isNull(user.getExt().get("corpFullName")) ? "" : user.getExt().get("corpFullName").toString());
		userInfoVO.setIsLogin(Objects.isNull(user.getExt().get("isLogin")) ? 0 : Integer.parseInt(user.getExt().get("isLogin").toString()));
		Dept dept = deptService.getById(Long.valueOf(user.getDeptId()));
		Dept corp = deptService.getCorp(Long.valueOf(user.getCorpId()));
		//替换为名称全程
		if (dept != null) {
			userInfoVO.setDeptName(dept.getFullName() != null ? dept.getFullName() : dept.getDeptName());
			userInfoVO.setPropertyDeptName(dept.getFullName() != null ? dept.getFullName() : dept.getDeptName());
			userInfoVO.setUseKeepDeptName(dept.getFullName() != null ? dept.getFullName() : dept.getDeptName());
			userInfoVO.setEntityKeepDeptName(dept.getFullName() != null ? dept.getFullName() : dept.getDeptName());
		}
		if (corp != null) {
			userInfoVO.setOwnerUnitName(corp.getFullName() != null ? corp.getFullName() : corp.getDeptName());
		}
		String roleName = user.getRoleName();
		if (roleName.contains("运维人员")) {
			userInfoVO.setUserRoleFlag(0);
		}
		if (roleName.contains("问题反馈人员")){
			userInfoVO.setQuestionFlag(0);
		}
		userInfoVO.setRoleAlias(user.getRoleCode());
		return R.data(userInfoVO);
	}

	/**
	 * 获取部门用户信息
	 *
	 * @param deptId 部门id
	 * @return R
	 */
	@Override
	public R<List<UserVO>> getDeptUserList(Long deptId) {
		List<Dept> list = deptService.list(new LambdaQueryWrapper<Dept>().eq(Dept::getParentId, deptId).select(Dept::getId));
		List<String> deptList = list.stream().map(Dept::getId).map(Objects::toString).collect(Collectors.toList());
		deptList.add(deptId.toString());
		return userClient.getDeptUserList(String.join(",", deptList));
	}
}
