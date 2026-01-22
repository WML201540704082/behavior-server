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
package com.lnsoft.system.service.impl;

import com.lnsoft.system.dto.DeptDTO;
import com.lnsoft.system.entity.UserGroup;
import com.lnsoft.system.vo.DeptVO;
import com.lnsoft.system.vo.UserGroupVO;
import com.lnsoft.system.mapper.UserGroupMapper;
import com.lnsoft.system.service.IUserGroupService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 班组表 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@Service
public class UserGroupServiceImpl extends BaseServiceImpl<UserGroupMapper, UserGroup> implements IUserGroupService {

	@Override
	public IPage<UserGroupVO> selectUserGroupPage(IPage<UserGroupVO> page, UserGroupVO userGroup) {
		return page.setRecords(baseMapper.selectUserGroupPage(page, userGroup));
	}

	@Override
	public List<DeptVO> tree(DeptDTO deptDTO) {
		return baseMapper.tree(deptDTO);
	}

}
