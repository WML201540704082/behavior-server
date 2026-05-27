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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.device.api.cmdb.entity.CmdbResourcecenterTypeCi;
import com.lnsoft.device.api.cmdb.mapper.CmdbResourcecenterTypeCiMapper;
import com.lnsoft.device.api.cmdb.service.ICmdbResourcecenterTypeCiService;
import com.lnsoft.device.api.cmdb.vo.CmdbResourcecenterTypeCiVO;
import org.springframework.stereotype.Service;

/**
 * IT设备模型ID管理(唯一数据) 服务实现类
 *
 * @author xuel
 * @since 2024-03-01
 */
@Service
public class CmdbResourcecenterTypeCiServiceImpl extends BaseServiceImpl<CmdbResourcecenterTypeCiMapper, CmdbResourcecenterTypeCi> implements ICmdbResourcecenterTypeCiService {

	@Override
	public IPage<CmdbResourcecenterTypeCiVO> selectCmdbResourcecenterTypeCiPage(IPage<CmdbResourcecenterTypeCiVO> page, CmdbResourcecenterTypeCiVO cmdbResourcecenterTypeCi) {
		return page.setRecords(baseMapper.selectCmdbResourcecenterTypeCiPage(page, cmdbResourcecenterTypeCi));
	}

	@Override
	public Long getItDeviceCiId() {
		Long ciId = baseMapper.getItDeviceCiId();
		return ciId;
	}


	@Override
	public Long getAssetStandCiId() {
		Long ciId = baseMapper.getAssetStandCiId();
		return ciId;
	}


}
