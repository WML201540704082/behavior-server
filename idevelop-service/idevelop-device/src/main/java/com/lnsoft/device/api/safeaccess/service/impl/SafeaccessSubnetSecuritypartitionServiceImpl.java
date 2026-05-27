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

import com.lnsoft.device.api.safeaccess.entity.SafeaccessSubnetSecuritypartition;
import com.lnsoft.device.api.safeaccess.vo.SafeaccessSubnetSecuritypartitionVO;
import com.lnsoft.device.api.safeaccess.mapper.SafeaccessSubnetSecuritypartitionMapper;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessSubnetSecuritypartitionService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 子网安全分区管理 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-11
 */
@Service
public class SafeaccessSubnetSecuritypartitionServiceImpl extends BaseServiceImpl<SafeaccessSubnetSecuritypartitionMapper, SafeaccessSubnetSecuritypartition> implements ISafeaccessSubnetSecuritypartitionService {

	@Override
	public IPage<SafeaccessSubnetSecuritypartitionVO> selectSafeaccessSubnetSecuritypartitionPage(IPage<SafeaccessSubnetSecuritypartitionVO> page, SafeaccessSubnetSecuritypartitionVO safeaccessSubnetSecuritypartition) {
		return page.setRecords(baseMapper.selectSafeaccessSubnetSecuritypartitionPage(page, safeaccessSubnetSecuritypartition));
	}

}
