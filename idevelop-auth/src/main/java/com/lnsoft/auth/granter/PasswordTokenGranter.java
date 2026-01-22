
package com.lnsoft.auth.granter;

import com.lnsoft.auth.enums.IdevelopUserEnum;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.DigestUtil;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.system.user.entity.UserInfo;
import com.lnsoft.system.user.feign.IUserClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * PasswordTokenGranter
 *
 * @author guozhao
 */
@Component
public class PasswordTokenGranter implements ITokenGranter {

	private static final Logger logger = LoggerFactory.getLogger(PasswordTokenGranter.class);

	public static final String GRANT_TYPE = "password";

	@Resource
	private IUserClient userClient;

	@Override
	public UserInfo grant(TokenParameter tokenParameter) {
		String tenantId = tokenParameter.getArgs().getStr("tenantId");
		String account = tokenParameter.getArgs().getStr("account");
		String password = tokenParameter.getArgs().getStr("password");
		UserInfo userInfo = null;
		if (Func.isNoneBlank(account, password)) {
			// 获取用户类型
			String userType = tokenParameter.getArgs().getStr("userType");
			Boolean iscCheck = tokenParameter.getArgs().getBool("iscCheck");
			R<UserInfo> result;
			// 根据不同用户类型调用对应的接口返回数据，用户可自行拓展
			if (userType.equals(IdevelopUserEnum.WEB.getName())) {
				if (iscCheck) {
					result = userClient.userInfo(tenantId, account);
				} else {
					result = userClient.userInfo(tenantId, account, DigestUtil.encrypt(password));
				}
			} else if (userType.equals(IdevelopUserEnum.APP.getName())) {
				if (iscCheck) {
					result = userClient.userInfo(tenantId, account);
				} else {
					result = userClient.userInfo(tenantId, account, DigestUtil.encrypt(password));
				}
			} else {
				if (iscCheck) {
					result = userClient.userInfo(tenantId, account);
				} else {
					result = userClient.userInfo(tenantId, account, DigestUtil.encrypt(password));
				}
			}
			userInfo = result.isSuccess() ? result.getData() : null;
		}
		return userInfo;
	}


}
