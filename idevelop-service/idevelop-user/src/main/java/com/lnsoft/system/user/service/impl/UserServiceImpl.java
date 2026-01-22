
package com.lnsoft.system.user.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.secure.constant.SecureConstant;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.*;
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.entity.Role;
import com.lnsoft.system.entity.Tenant;
import com.lnsoft.system.feign.ISysClient;
import com.lnsoft.system.user.entity.User;
import com.lnsoft.system.user.entity.UserDTO;
import com.lnsoft.system.user.entity.UserInfo;
import com.lnsoft.system.user.entity.UserOauth;
import com.lnsoft.system.user.excel.UserExcel;
import com.lnsoft.system.user.mapper.UserMapper;
import com.lnsoft.system.user.service.IUserOauthService;
import com.lnsoft.system.user.service.IUserService;
import com.lnsoft.system.user.vo.BpmUserVO;
import com.lnsoft.system.user.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 服务实现类
 *
 * @author guozhao
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {

	public static final String REGEX = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[^a-zA-Z0-9]).{8,30}$";
	public static final String REGEX_STRING = "[!@#$%^&*()_+={}:;|,.?/~'-<>]";

	private static final String GUEST_NAME = "guest";
	private static final String MINUS_ONE = "-1";

	@Value("${user.defaultPassword}")
	private String defaultPassword;
	@Resource
	private ISysClient sysClient;
	@Resource
	private IUserOauthService userOauthService;
	@Resource
	private RedisUtil redisUtil;

	@Override
	public boolean submit(User user) {
		if (Func.isNotEmpty(user.getPassword())) {
			user.setPassword(DigestUtil.encrypt(user.getPassword()));
		} else {
			user.setPassword(DigestUtil.encrypt(defaultPassword));
		}
		if (Func.isNotEmpty(user.getIdcard())) {
			String idcard = this.mask(user.getIdcard(), 4, 4);
			user.setIdcard(idcard);
		}
		if (Func.isNotEmpty(user.getPhone())) {
			String phone = this.mask(user.getPhone(), 3, 4);
			user.setPhone(phone);
		}
		Long cnt = baseMapper.selectCount(Wrappers.<User>query().lambda().eq(User::getTenantId, user.getTenantId()).eq(User::getAccount, user.getAccount()));
		if (cnt > 0) {
			throw new ServiceException("当前用户已存在!");
		}
		if (StringUtil.isNotBlank(user.getAccount())) {
			Set<String> accountKeys = redisUtil.keys(SecureConstant.LOGIN_TOKEN_KEY + user.getAccount() + "*");
			redisUtil.delsKey(accountKeys);
		}
		return saveOrUpdate(user);
	}

	@Override
	public IPage<User> selectUserPage(IPage<User> page, User user) {
		return page.setRecords(baseMapper.selectUserPage(page, user));
	}

	@Override
	public UserInfo userInfo(Long userId) {
		UserInfo userInfo = new UserInfo();
		User user = baseMapper.getUserInfoById(userId);
		// 获取部门名称
		String deptName = sysClient.getDeptName(Long.parseLong(user.getDeptId()));
		// 获取公司信息
		Dept corp = sysClient.getCorp(Long.parseLong(user.getDeptId()));
		user.setDeptName(deptName);
		user.setCorpId(corp.getId().toString());
		user.setCorpName(corp.getDeptName());
		user.setCorpFullName(corp.getFullName());

		// I6000和ERP信息
		Dept dept = sysClient.getDept(Long.parseLong(user.getDeptId()));

		user.setErpUnit(dept.getErpUnit());
		user.setErpUnitCode(dept.getErpUnitCode());
		user.setErpDept(dept.getErpDept());
		user.setErpDeptCode(dept.getErpDeptCode());
		user.setI6000Unit(dept.getI6000Unit());
		user.setI6000UnitCode(dept.getI6000UnitCode());
		user.setI6000Dept(dept.getI6000Dept());
		user.setI6000DeptCode(dept.getI6000DeptCode());

		userInfo.setUser(user);
		if (Func.isNotEmpty(user)) {
			/*List<String> roleAlias = baseMapper.getRoleAlias(Func.toStrArray(user.getRoleId()));
			userInfo.setRoles(roleAlias);*/
			Role roleAlias = baseMapper.getRoleAlias(Func.toStrArray(user.getRoleId()));
			userInfo.setRoles(Arrays.asList(roleAlias.getRoleAlias().split(",")));
			userInfo.setRoleName(Arrays.asList(roleAlias.getRoleName().split(",")));
		}
		return userInfo;
	}

	@Override
	public UserInfo userInfo(String tenantId, String account) {
		UserInfo userInfo = new UserInfo();
		User user = baseMapper.getUserInfo(tenantId, account);
		// 获取部门名称
		// String deptName = sysClient.getDeptName(Long.parseLong(user.getDeptId()));
		// 获取公司信息
		Dept corp = sysClient.getCorp(Long.parseLong(user.getDeptId()));
		// 获取部门或班组信息
		Dept deptOrGroup = sysClient.getDeptOrGroup(Long.parseLong(user.getDeptId()));
		if ("TEAM".equals(deptOrGroup.getType())) {
			user.setGroupId(deptOrGroup.getId().toString());
			user.setGroupName(deptOrGroup.getDeptName());
			Dept dept = sysClient.getDeptByGroup(Long.parseLong(user.getDeptId()));
			user.setDeptId(dept.getId().toString());
			user.setDeptName(dept.getDeptName());
		} else {
			user.setDeptName(deptOrGroup.getDeptName());
		}
		user.setCorpId(corp.getId().toString());
		user.setCorpName(corp.getDeptName());
		user.setCorpFullName(corp.getFullName());

		// I6000和ERP信息
		Dept dept = sysClient.getDept(Long.parseLong(user.getDeptId()));
		user.setErpUnit(dept.getErpUnit());
		user.setErpUnitCode(dept.getErpUnitCode());
		user.setErpDept(dept.getErpDept());
		user.setErpDeptCode(dept.getErpDeptCode());
		user.setI6000Unit(dept.getI6000Unit());
		user.setI6000UnitCode(dept.getI6000UnitCode());
		user.setI6000Dept(dept.getI6000Dept());
		user.setI6000DeptCode(dept.getI6000DeptCode());

		userInfo.setUser(user);
		if (Func.isNotEmpty(user)) {
			Role roleAlias = baseMapper.getRoleAlias(Func.toStrArray(user.getRoleId()));
			userInfo.setRoles(Arrays.asList(roleAlias.getRoleAlias().split(",")));
			userInfo.setRoleName(Arrays.asList(roleAlias.getRoleName().split(",")));
		}
		return userInfo;
	}


	@Override
	public UserInfo userInfo(String tenantId, String account, String password) {
		UserInfo userInfo = new UserInfo();
		User user = baseMapper.getUser(tenantId, account, password);
		// 获取部门名称
		// String deptName = sysClient.getDeptName(Long.parseLong(user.getDeptId()));
		// 获取公司信息
		Dept corp = sysClient.getCorp(Long.parseLong(user.getDeptId()));
		// 获取部门或班组信息
		Dept deptOrGroup = sysClient.getDeptOrGroup(Long.parseLong(user.getDeptId()));
		if ("TEAM".equals(deptOrGroup.getType())) {
			user.setGroupId(deptOrGroup.getId().toString());
			user.setGroupName(deptOrGroup.getDeptName());
			Dept dept = sysClient.getDeptByGroup(Long.parseLong(user.getDeptId()));
			user.setDeptId(dept.getId().toString());
			user.setDeptName(dept.getDeptName());
		} else {
			user.setDeptName(deptOrGroup.getDeptName());
		}
		user.setCorpId(corp.getId().toString());
		user.setCorpName(corp.getDeptName());
		user.setCorpFullName(corp.getFullName());

		// I6000和ERP信息
		Dept dept = sysClient.getDept(Long.parseLong(user.getDeptId()));
		user.setErpUnit(dept.getErpUnit());
		user.setErpUnitCode(dept.getErpUnitCode());
		user.setErpDept(dept.getErpDept());
		user.setErpDeptCode(dept.getErpDeptCode());
		user.setI6000Unit(dept.getI6000Unit());
		user.setI6000UnitCode(dept.getI6000UnitCode());
		user.setI6000Dept(dept.getI6000Dept());
		user.setI6000DeptCode(dept.getI6000DeptCode());

		userInfo.setUser(user);
		if (Func.isNotEmpty(user)) {
			String[] roles = Func.toStrArray(user.getRoleId());
			Role roleAlias = baseMapper.getRoleAlias(roles);
			userInfo.setRoles(Arrays.asList(roleAlias.getRoleAlias().split(",")));
			userInfo.setRoleName(Arrays.asList(roleAlias.getRoleName().split(",")));

			// 权限标识集合
			List<String> permissions = baseMapper.getPermissions(roles);
			userInfo.setPermissions(permissions);
		}
		return userInfo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UserInfo userInfo(UserOauth userOauth) {
		UserOauth uo = userOauthService.getOne(Wrappers.<UserOauth>query().lambda().eq(UserOauth::getUuid, userOauth.getUuid()).eq(UserOauth::getSource, userOauth.getSource()));
		UserInfo userInfo;
		if (Func.isNotEmpty(uo) && Func.isNotEmpty(uo.getUserId())) {
			userInfo = this.userInfo(uo.getUserId());
			userInfo.setOauthId(Func.toStr(uo.getId()));
		} else {
			userInfo = new UserInfo();
			if (Func.isEmpty(uo)) {
				userOauthService.save(userOauth);
				userInfo.setOauthId(Func.toStr(userOauth.getId()));
			} else {
				userInfo.setOauthId(Func.toStr(uo.getId()));
			}
			User user = new User();
			user.setAccount(userOauth.getUsername());
			userInfo.setUser(user);
			userInfo.setRoles(Collections.singletonList(GUEST_NAME));
		}
		return userInfo;
	}

	@Override
	public boolean grant(String userIds, String roleIds) {
		User user = new User();
		user.setRoleId(roleIds);
		boolean update = this.update(user, Wrappers.<User>update().lambda().in(User::getId, Func.toLongList(userIds)));
		if (update) {
			Set<String> accountKeys = redisUtil.keys(SecureConstant.LOGIN_TOKEN_KEY + user.getAccount() + "*");
			redisUtil.delsKey(accountKeys);
		}
		return update;
	}

	@Override
	public String resetPassword(String userIds) {
		User user = new User();
		// 生成随机密码
		String baseStr = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%";
		String password = RandomUtil.randomString(baseStr, 8);
		user.setPassword(DigestUtil.encrypt(password));
		user.setUpdateTime(DateUtil.now());
		boolean temp = this.update(user, Wrappers.<User>update().lambda().in(User::getId, Func.toLongList(userIds)));
		if (temp) {
			Set<String> accountKeys = redisUtil.keys(SecureConstant.LOGIN_TOKEN_KEY + user.getAccount() + "*");
			redisUtil.delsKey(accountKeys);
		}
		return password;
	}

	@Override
	public Boolean updatePassword(Long userId, String oldPassword, String newPassword, String newPassword1, Long updateId) {
		Long id = null;

		if (updateId == null) {
			id = userId;
		} else {
			id = updateId;
		}

		User user = getById(id);
		if (StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword)) {
			throw new ServiceException("旧密码或者新密码不能为空!");
		}

		if (newPassword.contains(user.getAccount())) {
			throw new ServiceException("密码中不能包含用户名信息!");
		}
		// 验证密码复杂度（包含大小写，特殊符号和数字）
		boolean matches = newPassword.matches(REGEX);
		boolean regex = Pattern.compile(REGEX_STRING).matcher(newPassword).find();
		Boolean length = newPassword.length() > 8 ? Boolean.TRUE : Boolean.FALSE;
		if (!matches || !regex || !length) {
			throw new ServiceException("密码中必须包含大小字母、数字，至少8个字符，最多30个字符");
		}


		if (!newPassword.equals(newPassword1)) {
			throw new ServiceException("请输入正确的确认密码!");
		}
		if (!user.getPassword().equals(DigestUtil.encrypt(oldPassword))) {
			throw new ServiceException("原密码不正确!");
		}

		boolean temp = this.update(Wrappers.<User>update().lambda()
			.set(User::getPassword, DigestUtil.encrypt(newPassword1))
			.set(User::getIsLogin, 1)
			.eq(User::getId, id));
		if (temp) {
			Set<String> accountKeys = redisUtil.keys(SecureConstant.LOGIN_TOKEN_KEY + user.getAccount() + "*");
			redisUtil.delsKey(accountKeys);
		}
		return temp;
	}

	@Override
	public List<String> getRoleName(String roleIds) {
		return baseMapper.getRoleName(Func.toStrArray(roleIds));
	}

	@Override
	public List<String> getDeptName(String deptIds) {
		return baseMapper.getDeptName(Func.toStrArray(deptIds));
	}

	@Override
	public void importUser(List<UserExcel> data) {
		data.forEach(userExcel -> {
			User user = Objects.requireNonNull(BeanUtil.copy(userExcel, User.class));
			// 设置部门ID
			user.setDeptId(sysClient.getDeptIds(userExcel.getTenantId(), userExcel.getDeptName()));
			// 设置岗位ID
			user.setPostId(sysClient.getPostIds(userExcel.getTenantId(), userExcel.getPostName()));
			// 设置角色ID
			user.setRoleId(sysClient.getRoleIds(userExcel.getTenantId(), userExcel.getRoleName()));
			// 设置默认密码
			user.setPassword(defaultPassword);
			this.submit(user);
		});
	}

	@Override
	public List<UserExcel> exportUser(Wrapper<User> queryWrapper) {
		List<UserExcel> userList = baseMapper.exportUser(queryWrapper);
		userList.forEach(user -> {
			user.setRoleName(StringUtil.join(sysClient.getRoleNames(user.getRoleId())));
			user.setDeptName(StringUtil.join(sysClient.getDeptNames(user.getDeptId())));
			user.setPostName(StringUtil.join(sysClient.getPostNames(user.getPostId())));
		});
		return userList;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean registerGuest(User user, Long oauthId) {
		R<Tenant> result = sysClient.getTenant(user.getTenantId());
		Tenant tenant = result.getData();
		if (!result.isSuccess() || tenant == null || tenant.getId() == null) {
			throw new ServiceException("租户信息错误!");
		}
		UserOauth userOauth = userOauthService.getById(oauthId);
		if (userOauth == null || userOauth.getId() == null) {
			throw new ServiceException("第三方登陆信息错误!");
		}
		user.setRealName(user.getName());
		user.setAvatar(userOauth.getAvatar());
		user.setRoleId(MINUS_ONE);
		user.setDeptId(MINUS_ONE);
		user.setPostId(MINUS_ONE);
		boolean userTemp = this.submit(user);
		userOauth.setUserId(user.getId());
		userOauth.setTenantId(user.getTenantId());
		boolean oauthTemp = userOauthService.updateById(userOauth);
		return (userTemp && oauthTemp);
	}

	/**
	 * 根据userId批量查询用户信息（流程使用，用户名，手机号）
	 *
	 * @param userId userId
	 * @return List
	 */
	@Override
	public List<BpmUserVO> batchQueryUserInfo(String userId) {
		List<User> userList = baseMapper.selectList(new LambdaQueryWrapper<User>()
			.in(User::getId, Arrays.asList(userId.split(",")))
			.select(User::getId, User::getName, User::getRealName, User::getPhone));
		return Convert.convert(new TypeReference<List<BpmUserVO>>() {
		}, userList);
	}

	@Override
	public UserInfo getUserByRole(String roleName, Long userId) {
		UserInfo result = new UserInfo();
		User user = baseMapper.getUserByRole(roleName, userId);
		if (Objects.isNull(user)) {
			return null;
		}
		result.setUser(user);
		return result;
	}

	/**
	 * 获取部门下的用户
	 *
	 * @param orderDept      部门id
	 * @param hussarUserList 用户信息（String)
	 * @return List
	 */
	@Override
	public List<BpmUserVO> selectUserListByDept(String orderDept, String hussarUserList) {
		List<User> userList = baseMapper.selectList(new LambdaQueryWrapper<User>()
			.eq(User::getDeptId, orderDept)
			.in(User::getId, Arrays.asList(hussarUserList.split(",")))
			.select(User::getId, User::getName, User::getRealName, User::getPhone));
		return Convert.convert(new TypeReference<List<BpmUserVO>>() {
		}, userList);
		// return baseMapper.selectUserListByDept(orderDept, Arrays.asList(hussarUserList.split(",")));
	}

	@Override
	public List<UserDTO> getUserByIdCard(String realName, String deptId, String idcard) {
		List<UserDTO> userDTOList = baseMapper.getUserByIdCard(realName, deptId, idcard);
		return userDTOList;
	}

	/**
	 * 获取部门用户信息
	 *
	 * @param deptIdList 部门id
	 * @return List
	 */
	@Override
	public List<UserVO> getDeptUserList(String deptIdList) {
		List<User> users = baseMapper.selectList(new LambdaQueryWrapper<User>()
			.in(User::getDeptId, Arrays.asList(deptIdList.split(",")))
			.select(User::getId, User::getName, User::getRealName, User::getIdcard, User::getAccount, User::getPhone, User::getEmail));
		return Convert.convert(new TypeReference<List<UserVO>>() {
		}, users);
	}

	@Override
	public List<String> getRegionName(String regionCode) {
		return baseMapper.getRegionName(Func.toStrArray(regionCode));
	}

	@Override
	public String mask(String str, int prefix, int suffix) {
		if (str == null) {
			return str;
		}
		if (StringUtils.contains(str, "****")) {
			return str;
		}
		if (str.length() <= prefix + suffix) {
			return DigestUtil.encrypt(str);
		}

		return str.substring(0, prefix) + "****" + str.substring(str.length() - suffix);
	}
}
