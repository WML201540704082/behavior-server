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
package com.lnsoft.device.api.safeaccess.service.impl;

import com.lnsoft.device.entity.SafeaccessUserAccessDisable;
import com.lnsoft.device.api.safeaccess.vo.SafeaccessUserAccessDisableVO;
import com.lnsoft.device.api.safeaccess.mapper.SafeaccessUserAccessDisableMapper;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessUserAccessDisableService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 用户入网临时禁用表 服务实现类
 *
 * @author Idevelop
 * @since 2024-06-05
 */
@Service
public class SafeaccessUserAccessDisableServiceImpl extends BaseServiceImpl<SafeaccessUserAccessDisableMapper, SafeaccessUserAccessDisable> implements ISafeaccessUserAccessDisableService {

	@Override
	public IPage<SafeaccessUserAccessDisableVO> selectSafeaccessUserAccessDisablePage(IPage<SafeaccessUserAccessDisableVO> page, SafeaccessUserAccessDisableVO safeaccessUserAccessDisable) {
		return page.setRecords(baseMapper.selectSafeaccessUserAccessDisablePage(page, safeaccessUserAccessDisable));
	}

}
