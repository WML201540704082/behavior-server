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
package com.lnsoft.device.api.erp.service.impl;

import com.lnsoft.device.entity.ZfitXtCwzt;
import com.lnsoft.device.api.erp.vo.ZfitXtCwztVO;
import com.lnsoft.device.api.erp.mapper.ZfitXtCwztMapper;
import com.lnsoft.device.api.erp.service.IZfitXtCwztService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 *  服务实现类
 *
 * @author Idevelop
 * @since 2024-07-29
 */
@Service
public class ZfitXtCwztServiceImpl extends BaseServiceImpl<ZfitXtCwztMapper, ZfitXtCwzt> implements IZfitXtCwztService {

	@Override
	public IPage<ZfitXtCwztVO> selectZfitXtCwztPage(IPage<ZfitXtCwztVO> page, ZfitXtCwztVO zfitXtCwzt) {
		return page.setRecords(baseMapper.selectZfitXtCwztPage(page, zfitXtCwzt));
	}

	@Override
	public List<ZfitXtCwztVO> selectData() {
		return baseMapper.selectData();
	}

}
