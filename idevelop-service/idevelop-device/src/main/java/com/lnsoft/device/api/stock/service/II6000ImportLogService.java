/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
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
package com.lnsoft.device.api.stock.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseService;
import com.lnsoft.device.api.stock.entity.I6000ImportLog;
import com.lnsoft.device.api.stock.vo.I6000ImportLogVO;

import javax.servlet.http.HttpServletResponse;

/**
 * 管理员批量导入I6000数据记录表 服务类
 *
 * @author Idevelop
 * @since 2025-11-02
 */
public interface II6000ImportLogService extends BaseService<I6000ImportLog> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param i6000ImportLog
	 * @return
	 */
	IPage<I6000ImportLogVO> selectI6000ImportLogPage(IPage<I6000ImportLogVO> page, I6000ImportLogVO i6000ImportLog);

	/**
	 * 自定义导出
	 *
	 * @param i6000ImportLog
	 * @param response
	 */
	void exportByExcel(I6000ImportLog i6000ImportLog, HttpServletResponse response);
}
