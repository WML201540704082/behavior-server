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
package com.lnsoft.system.service;

import com.lnsoft.system.entity.RemindUser;
import com.lnsoft.system.vo.RemindUserVO;
import com.lnsoft.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 通知-用户绑定表 服务类
 *
 * @author Idevelop
 * @since 2024-09-27
 */
public interface IRemindUserService extends BaseService<RemindUser> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param remindUser
	 * @return
	 */
	IPage<RemindUserVO> selectRemindUserPage(IPage<RemindUserVO> page, RemindUserVO remindUser);

}
