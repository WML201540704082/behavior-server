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
package com.lnsoft.system.service;

import com.lnsoft.system.dto.DeptDTO;
import com.lnsoft.system.entity.UserGroup;
import com.lnsoft.system.vo.DeptVO;
import com.lnsoft.system.vo.UserGroupVO;
import com.lnsoft.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 班组表 服务类
 *
 * @author Idevelop
 * @since 2024-03-06
 */
public interface IUserGroupService extends BaseService<UserGroup> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param userGroup
	 * @return
	 */
	IPage<UserGroupVO> selectUserGroupPage(IPage<UserGroupVO> page, UserGroupVO userGroup);

	/**
	 * 部门+ 班组树形
	 * @param  deptDTO
	 * @return
	 */
	List<DeptVO> tree(DeptDTO deptDTO);
}
