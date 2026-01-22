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
package com.lnsoft.system.service.impl;

import com.lnsoft.system.entity.RemindUser;
import com.lnsoft.system.vo.RemindUserVO;
import com.lnsoft.system.mapper.RemindUserMapper;
import com.lnsoft.system.service.IRemindUserService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 通知-用户绑定表 服务实现类
 *
 * @author Idevelop
 * @since 2024-09-27
 */
@Service
public class RemindUserServiceImpl extends BaseServiceImpl<RemindUserMapper, RemindUser> implements IRemindUserService {

	@Override
	public IPage<RemindUserVO> selectRemindUserPage(IPage<RemindUserVO> page, RemindUserVO remindUser) {
		return page.setRecords(baseMapper.selectRemindUserPage(page, remindUser));
	}

}
