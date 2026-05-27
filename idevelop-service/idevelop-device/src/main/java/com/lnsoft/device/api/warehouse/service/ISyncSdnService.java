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
package com.lnsoft.device.api.warehouse.service;

import com.lnsoft.device.api.warehouse.entity.SyncSdn;
import com.lnsoft.device.api.warehouse.vo.SyncSdnVO;
import com.lnsoft.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 各地市同步sdn和radius控制表 服务类
 *
 * @author Idevelop
 * @since 2026-01-27
 */
public interface ISyncSdnService extends BaseService<SyncSdn> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param syncSdn
	 * @return
	 */
	IPage<SyncSdnVO> selectSyncSdnPage(IPage<SyncSdnVO> page, SyncSdnVO syncSdn);

	boolean syncSdnService();


}
