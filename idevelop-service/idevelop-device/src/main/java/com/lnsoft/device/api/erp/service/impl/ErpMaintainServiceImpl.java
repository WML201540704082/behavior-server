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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.device.api.erp.entity.ErpMaintain;
import com.lnsoft.device.api.erp.mapper.ErpMaintainMapper;
import com.lnsoft.device.api.erp.service.IErpMaintainService;
import com.lnsoft.device.api.erp.vo.ErpMaintainVO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * erp维护工厂(对应单位) 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-22
 */
@Service
@AllArgsConstructor
public class ErpMaintainServiceImpl extends BaseServiceImpl<ErpMaintainMapper, ErpMaintain> implements IErpMaintainService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ErpMaintainServiceImpl.class);

	@Override
	public IPage<ErpMaintainVO> selectErpMaintainPage(IPage<ErpMaintainVO> page, ErpMaintainVO erpMaintain) {
		return page.setRecords(baseMapper.selectErpMaintainPage(page, erpMaintain));
	}

	/**
	 * 根据维护工厂获取 其所在的市县 维护工厂编码
	 *
	 * @param swerk
	 * @return
	 */
	@Override
	public List<String> selectErpListBySwerk(String swerk) {
		List<String> swerkList = baseMapper.selectErpListBySwerk(swerk);
		LOGGER.info("根据维护工厂获取 其所在的市县 维护工厂编码: {}", swerkList);
		return swerkList;
	}

	@Override
	public List<ErpMaintain> getSwerk(ErpMaintain erpMaintain) {
		return baseMapper.getSwerk(erpMaintain);

	}

}
