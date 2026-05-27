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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lnsoft.device.api.i6000.entity.I6000ExternalAdd;
import com.lnsoft.device.api.i6000.vo.I6000ExternalAddVO;
import com.lnsoft.device.api.i6000.mapper.I6000ExternalAddMapper;
import com.lnsoft.device.api.i6000.service.II6000ExternalAddService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 外部数据表 服务实现类
 *
 * @author Idevelop
 * @since 2024-06-18
 */
@Service
public class I6000ExternalAddServiceImpl extends BaseServiceImpl<I6000ExternalAddMapper, I6000ExternalAdd> implements II6000ExternalAddService {

	@Override
	public IPage<I6000ExternalAddVO> selectI6000ExternalAddPage(IPage<I6000ExternalAddVO> page, I6000ExternalAddVO i6000ExternalAdd) {
		return page.setRecords(baseMapper.selectI6000ExternalAddPage(page, i6000ExternalAdd));
	}

	@Override
	public Integer delete(String extCode) {
		LambdaQueryWrapper<I6000ExternalAdd> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(I6000ExternalAdd::getExtCode,extCode);
		return baseMapper.delete(queryWrapper);
	}

}
