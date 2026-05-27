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
import com.lnsoft.device.api.cmdb.mapper.CmdbDictCiMapper;
import com.lnsoft.device.api.cmdb.service.ICmdbDictCiService;
import com.lnsoft.device.entity.CmdbDictCi;
import com.lnsoft.device.vo.CmdbDictCiVO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * cmdb字典模型管理 服务实现类
 *
 * @author Idevelop
 * @since 2024-07-02
 */
@Service
public class CmdbDictCiServiceImpl extends BaseServiceImpl<CmdbDictCiMapper, CmdbDictCi> implements ICmdbDictCiService {

	@Override
	public IPage<CmdbDictCiVO> selectCmdbDictCiPage(IPage<CmdbDictCiVO> page, CmdbDictCiVO cmdbDictCi) {
		return page.setRecords(baseMapper.selectCmdbDictCiPage(page, cmdbDictCi));
	}

}
