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
package com.lnsoft.device.api.asset.service.impl;

import com.lnsoft.device.api.asset.entity.ProjectPurchaseBatch;
import com.lnsoft.device.api.asset.vo.ProjectPurchaseBatchVO;
import com.lnsoft.device.api.asset.mapper.ProjectPurchaseBatchMapper;
import com.lnsoft.device.api.asset.service.IProjectPurchaseBatchService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 项目物料批次表 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-04
 */
@Service
public class ProjectPurchaseBatchServiceImpl extends BaseServiceImpl<ProjectPurchaseBatchMapper, ProjectPurchaseBatch> implements IProjectPurchaseBatchService {

	@Override
	public IPage<ProjectPurchaseBatchVO> selectProjectPurchaseBatchPage(IPage<ProjectPurchaseBatchVO> page, ProjectPurchaseBatchVO projectPurchaseBatch) {
		return page.setRecords(baseMapper.selectProjectPurchaseBatchPage(page, projectPurchaseBatch));
	}

}
