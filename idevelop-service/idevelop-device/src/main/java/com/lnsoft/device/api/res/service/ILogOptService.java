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
package com.lnsoft.device.api.res.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseService;
import com.lnsoft.device.entity.LogOpt;
import com.lnsoft.device.api.res.vo.LogOptVO;

/**
 * 设备操作-日志表 服务类
 *
 * @author Idevelop
 * @since 2024-02-21
 */
public interface ILogOptService extends BaseService<LogOpt> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param logOpt
	 * @return
	 */
	IPage<LogOptVO> selectLogOptPage(IPage<LogOptVO> page, LogOptVO logOpt);

	Boolean add(LogOpt logOpt);
	/**
	 * 新增 设备操作-日志
	 *
	 * @param role
	 * @param title
	 * @return
	 */
	Boolean logOptAdd(String role, String title, String logId);

	/**
	 * 新增操作记录日志（公用）
	 *
	 * @param logOpt 操作记录日志
	 */
	void commonLogOpt(LogOpt logOpt);

}
