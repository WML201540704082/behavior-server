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
package com.lnsoft.ipc.service;

import com.lnsoft.core.mp.support.Query;
import com.lnsoft.ipc.entity.IpcLocalAppLog;
import com.lnsoft.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.ipc.vo.RankVO;

import java.util.List;

/**
 * 工控机管控-本地应用访问记录表 服务类
 *
 * @author Idevelop
 * @since 2026-02-13
 */
public interface IIpcLocalAppLogService extends BaseService<IpcLocalAppLog> {

	/**
	 * 辅数据源分页
	 * @param ipcLocalAppLog
	 * @param query
	 * @return
	 */
    IPage<IpcLocalAppLog> slaveList(IpcLocalAppLog ipcLocalAppLog, Query query);

	/**
	 * 访问次数排名
	 * @param ipcLocalAppLog
	 * @return
	 */
	List<RankVO> countRank(IpcLocalAppLog ipcLocalAppLog);

}
