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


import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.device.api.cmdb.entity.CmdbUi;
import com.lnsoft.device.api.cmdb.mapper.CmdbUiMapper;
import com.lnsoft.device.api.cmdb.service.ICmdbUiService;
import com.lnsoft.device.api.cmdb.vo.CmdbUiVO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 前端属性/配置项配置表 服务实现类
 *
 * @author Idevelop
 * @since 2024-06-24
 */
@Service
public class CmdbUiServiceImpl extends BaseServiceImpl<CmdbUiMapper, CmdbUi> implements ICmdbUiService {

	@Override
	public IPage<CmdbUiVO> selectCmdbUiPage(IPage<CmdbUiVO> page, CmdbUiVO cmdbUi) {
		return page.setRecords(baseMapper.selectCmdbUiPage(page, cmdbUi));
	}

}
