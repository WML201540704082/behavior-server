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

import com.lnsoft.system.entity.Remind;
import com.lnsoft.system.vo.RemindVO;
import com.lnsoft.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 通知表 服务类
 *
 * @author Idevelop
 * @since 2024-09-27
 */
public interface IRemindService extends BaseService<Remind> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param remind
	 * @return
	 */
	IPage<RemindVO> selectRemindPage(IPage<RemindVO> page, RemindVO remind);

	/**
	 * 查询通知列表
	 * @param remind
	 * @return
	 */
	List<RemindVO> getList(Remind remind);
}
