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
package com.lnsoft.device.api.asset.service;

import com.lnsoft.device.api.asset.entity.ProjectPurchaseBatch;
import com.lnsoft.device.api.asset.vo.ProjectPurchaseBatchVO;
import com.lnsoft.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 项目物料批次表 服务类
 *
 * @author Idevelop
 * @since 2024-03-04
 */
public interface IProjectPurchaseBatchService extends BaseService<ProjectPurchaseBatch> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param projectPurchaseBatch
	 * @return
	 */
	IPage<ProjectPurchaseBatchVO> selectProjectPurchaseBatchPage(IPage<ProjectPurchaseBatchVO> page, ProjectPurchaseBatchVO projectPurchaseBatch);

}
