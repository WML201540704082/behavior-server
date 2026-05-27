/**
 .
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lnsoft.device.api.res.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.entity.LogOpt;
import com.lnsoft.device.api.res.mapper.LogOptMapper;
import com.lnsoft.device.api.res.service.ILogOptService;
import com.lnsoft.device.api.res.vo.LogOptVO;
import com.lnsoft.device.constant.CommonConstant;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * 设备操作-日志表 服务实现类
 *
 * @author Idevelop
 * @since 2024-02-21
 */
@Service
public class LogOptServiceImpl extends BaseServiceImpl<LogOptMapper, LogOpt> implements ILogOptService {

	@Override
	public IPage<LogOptVO> selectLogOptPage(IPage<LogOptVO> page, LogOptVO logOpt) {
		return page.setRecords(baseMapper.selectLogOptPage(page, logOpt));
	}

	@Override
	public Boolean add(LogOpt logOpt) {
		return true;
	}

	@Override
	public Boolean logOptAdd(String role, String title, String logId) {
		//设置角色 操作人 操作内容 设备建档id
		LogOpt logOpt = new LogOpt();
		logOpt.setOptRole(role);
		logOpt.setTitle(title);
		logOpt.setLogId(logId);
		IdevelopUser user = SecureUtil.getUser();
		if (user != null) {
			logOpt.setCreateBy(user.getUserId());
			logOpt.setCreateBy(user.getUserId());
			logOpt.setOptName(user.getRealName());
			logOpt.setOptRole(user.getRoleName());
		}
		//是否为发起建档请求
		if ((CommonConstant.STARTDEVICERECORDREQUEST).equals(logOpt.getTitle())) {
			logOpt.setOptType(CommonConstant.DEVICERECORD);
		}
		logOpt.setTime(new Date());
		logOpt.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
		return baseMapper.insert(logOpt) == 1;
	}

	/**
	 * 新增操作记录日志（公用）
	 *
	 * @param logOpt 操作记录日志
	 */
	@Override
	public void commonLogOpt(LogOpt logOpt) {
		logOpt.setCreateTime(new Date());
		if (Objects.nonNull(logOpt.getStatus()) && logOpt.getStatus() == 0) {
			if (StringUtil.isBlank(logOpt.getOptName())) {
				logOpt.setOptName("ERP审核");
			} else {
				Calendar instance = Calendar.getInstance();
				instance.add(Calendar.MINUTE, 1);
				logOpt.setCreateTime(instance.getTime());
			}
		} else {
			IdevelopUser user = SecureUtil.getUser();
			if (Objects.isNull(user)){
				user = logOpt.getUser();
			}
			logOpt.setCreateUser(user.getUserId());
			logOpt.setOptName(user.getRealName());
			logOpt.setStatus(1);
			logOpt.setCreateBy(user.getUserId());
		}
		logOpt.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
		logOpt.setTime(new Date());
		baseMapper.insert(logOpt);
	}


}
