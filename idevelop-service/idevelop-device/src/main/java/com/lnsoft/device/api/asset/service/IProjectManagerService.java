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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseService;
import com.lnsoft.device.api.asset.entity.ProjectManager;
import com.lnsoft.device.api.asset.entity.ProjectManagerErp;
import com.lnsoft.device.api.asset.vo.ProjectManagerErpVO;
import com.lnsoft.device.api.asset.vo.ProjectManagerVO;

/**
 * 项目管理 服务类
 *
 * @author Idevelop
 * @since 2024-03-04
 */
public interface IProjectManagerService extends BaseService<ProjectManager> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param projectManager
	 * @return
	 */
	IPage<ProjectManagerVO> selectProjectManagerPage(IPage<ProjectManagerVO> page, ProjectManagerVO projectManager);

	/**
	 * ERP资产编码查看
	 *
	 * @param projectManagerErp
	 * @return
	 */
	IPage<ProjectManagerErpVO> getErpList(ProjectManagerErp projectManagerErp);

    Boolean input();
}
