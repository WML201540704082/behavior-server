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

import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.dto.ProjectManagerDetailDTO;
import com.lnsoft.device.entity.ProjectManagerDetail;
import com.lnsoft.device.vo.ProjectManagerDetailVO;
import com.lnsoft.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.servlet.http.HttpServletResponse;

/**
 * 项目下的ERP资产编码 服务类
 *
 * @author Idevelop
 * @since 2024-04-29
 */
public interface IProjectManagerDetailService extends BaseService<ProjectManagerDetail> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param projectManagerDetail
	 * @return
	 */
	IPage<ProjectManagerDetailVO> selectProjectManagerDetailPage(IPage<ProjectManagerDetailVO> page, ProjectManagerDetailVO projectManagerDetail);

	/**
	 * 数据导入
	 * @return
	 */
    R input();

	/**
	 * 刷新设备编码
	 * @return
	 */
	R updateDeviceCode();

	/**
	 * 导出
	 * @param projectManagerDetailDTO
	 * @return
	 */
    R export(ProjectManagerDetailDTO projectManagerDetailDTO, HttpServletResponse servletResponse);
}
