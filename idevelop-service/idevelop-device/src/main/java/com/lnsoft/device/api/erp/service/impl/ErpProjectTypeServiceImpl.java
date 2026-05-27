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

import com.lnsoft.device.api.erp.entity.ErpProjectType;
import com.lnsoft.device.api.erp.vo.ErpProjectTypeVO;
import com.lnsoft.device.api.erp.mapper.ErpProjectTypeMapper;
import com.lnsoft.device.api.erp.service.IErpProjectTypeService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务实现类
 *
 * @author Idevelop
 * @since 2024-03-28
 */
@Service
public class ErpProjectTypeServiceImpl extends BaseServiceImpl<ErpProjectTypeMapper, ErpProjectType> implements IErpProjectTypeService {

	@Override
	public IPage<ErpProjectTypeVO> selectErpProjectTypePage(IPage<ErpProjectTypeVO> page, ErpProjectTypeVO erpProjectType) {
		return page.setRecords(baseMapper.selectErpProjectTypePage(page, erpProjectType));
	}

}
