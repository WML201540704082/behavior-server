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
package com.lnsoft.device.api.cmdb.service.impl;

import com.lnsoft.device.api.cmdb.entity.TripleApiLog;
import com.lnsoft.device.api.cmdb.vo.TripleApiLogVO;
import com.lnsoft.device.api.cmdb.mapper.TripleApiLogMapper;
import com.lnsoft.device.api.cmdb.service.ITripleApiLogService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 三方系统操作日志 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-29
 */
@Service
public class TripleApiLogServiceImpl extends BaseServiceImpl<TripleApiLogMapper, TripleApiLog> implements ITripleApiLogService {

	@Override
	public IPage<TripleApiLogVO> selectTripleApiLogPage(IPage<TripleApiLogVO> page, TripleApiLogVO tripleApiLog) {
		return page.setRecords(baseMapper.selectTripleApiLogPage(page, tripleApiLog));
	}

}
