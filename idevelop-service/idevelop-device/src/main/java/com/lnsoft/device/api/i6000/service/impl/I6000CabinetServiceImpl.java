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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.device.api.i6000.entity.I6000Cabinet;
import com.lnsoft.device.api.i6000.mapper.I6000CabinetMapper;
import com.lnsoft.device.api.i6000.service.II6000CabinetService;
import com.lnsoft.device.api.i6000.vo.I6000CabinetVO;
import org.springframework.stereotype.Service;

/**
 * I6000单位 服务实现类
 *
 * @author Idevelop
 * @since 2025-02-24
 */
@Service
public class I6000CabinetServiceImpl extends BaseServiceImpl<I6000CabinetMapper, I6000Cabinet> implements II6000CabinetService {

	@Override
	public IPage<I6000CabinetVO> selectI6000CabinetPage(IPage<I6000CabinetVO> page, I6000CabinetVO i6000Cabinet) {
		return page.setRecords(baseMapper.selectI6000CabinetPage(page, i6000Cabinet));
	}

}
