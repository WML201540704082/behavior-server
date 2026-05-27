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
package com.lnsoft.device.api.asset.mapper;

import com.lnsoft.device.api.asset.entity.ProjectManager;
import com.lnsoft.device.api.asset.entity.ProjectManagerErp;
import com.lnsoft.device.api.asset.vo.ProjectManagerErpVO;
import com.lnsoft.device.api.asset.vo.ProjectManagerVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 项目管理 Mapper 接口
 *
 * @author xuel
 * @since 2024-03-04
 */
public interface ProjectManagerMapper extends BaseMapper<ProjectManager> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param projectManager
	 * @return
	 */
	List<ProjectManagerVO> selectProjectManagerPage(IPage page, ProjectManagerVO projectManager);

	/**
	 * ERP资产编码查看
	 *
	 * @param projectManagerErp
	 * @return
	 */
	List<ProjectManagerErpVO> getErpList(ProjectManagerErp projectManagerErp);

	/**
	 * ERP资产编码查看 总数
	 *
	 * @param projectManagerErp
	 * @return
	 */
	Long getErpListCount(ProjectManagerErp projectManagerErp);

}
