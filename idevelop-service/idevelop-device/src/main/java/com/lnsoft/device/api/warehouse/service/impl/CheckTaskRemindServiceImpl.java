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
package com.lnsoft.device.api.warehouse.service.impl;

import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.device.api.warehouse.entity.CheckTaskRemind;
import com.lnsoft.device.api.warehouse.vo.CheckTaskRemindVO;
import com.lnsoft.device.api.warehouse.mapper.CheckTaskRemindMapper;
import com.lnsoft.device.api.warehouse.service.ICheckTaskRemindService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 盘点任务 服务实现类
 *
 * @author Idevelop
 * @since 2024-06-12
 */
@Service
public class CheckTaskRemindServiceImpl extends BaseServiceImpl<CheckTaskRemindMapper, CheckTaskRemind> implements ICheckTaskRemindService {

	@Override
	public IPage<CheckTaskRemindVO> selectCheckTaskRemindPage(IPage<CheckTaskRemindVO> page, CheckTaskRemindVO checkTaskRemind) {
		IdevelopUser sysUser = SecureUtil.getUser();
		checkTaskRemind.setUserId(sysUser.getUserId());
		return page.setRecords(baseMapper.selectCheckTaskRemindPage(page, checkTaskRemind));
	}

	@Override
	public Integer status(CheckTaskRemindVO checkTaskRemind) {
		return baseMapper.updateById(checkTaskRemind);
	}

}
