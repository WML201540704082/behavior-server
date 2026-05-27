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
package com.lnsoft.device.api.i6000.service.impl;

import com.lnsoft.device.api.i6000.entity.CmdbI6000;
import com.lnsoft.device.api.i6000.vo.CmdbI6000VO;
import com.lnsoft.device.api.i6000.mapper.CmdbI6000Mapper;
import com.lnsoft.device.api.i6000.service.ICmdbI6000Service;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 模型属性映射表(查询专用) 服务实现类
 *
 * @author Idevelop
 * @since 2024-11-06
 */
@Service
public class CmdbI6000ServiceImpl extends BaseServiceImpl<CmdbI6000Mapper, CmdbI6000> implements ICmdbI6000Service {

	@Override
	public IPage<CmdbI6000VO> selectCmdbI6000Page(IPage<CmdbI6000VO> page, CmdbI6000VO cmdbI6000) {
		return page.setRecords(baseMapper.selectCmdbI6000Page(page, cmdbI6000));
	}

}
