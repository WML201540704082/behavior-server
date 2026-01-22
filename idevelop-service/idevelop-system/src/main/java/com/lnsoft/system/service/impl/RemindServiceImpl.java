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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.system.entity.Remind;
import com.lnsoft.system.vo.RemindVO;
import com.lnsoft.system.mapper.RemindMapper;
import com.lnsoft.system.service.IRemindService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知表 服务实现类
 *
 * @author Idevelop
 * @since 2024-09-27
 */
@Service
public class RemindServiceImpl extends BaseServiceImpl<RemindMapper, Remind> implements IRemindService {

	@Override
	public IPage<RemindVO> selectRemindPage(IPage<RemindVO> page, RemindVO remind) {
		return page.setRecords(baseMapper.selectRemindPage(page, remind));
	}

	@Override
	public List<RemindVO> getList(Remind remind) {
		Long userId = SecureUtil.getUserId();
		LambdaQueryWrapper<Remind> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.orderByDesc(Remind::getCreateTime);
		List<Remind> reminds = baseMapper.selectList(queryWrapper);
		List<RemindVO> remindVOS = new ArrayList<>();
		for (Remind item : reminds) {
			RemindVO remindVO = new RemindVO();
			remindVO.setId(item.getId());
			remindVO.setTitle(item.getTitle());
			remindVO.setRemind(item.getRemind());
			remindVO.setCreateTime(item.getCreateTime());
			remindVO.setIsDeleted(item.getIsDeleted());
			remindVO.setStatus(item.getStatus());
			remindVO.setCreateUser(item.getCreateUser());
			remindVO.setUpdateUser(item.getUpdateUser());
			remindVO.setUpdateTime(item.getUpdateTime());
			Long id = item.getId();
			List<RemindVO> list = baseMapper.getList(id, userId);
			if (ObjectUtil.isEmpty(list)){
				remindVO.setIsLook(0);
			}else {
				remindVO.setIsLook(1);
			}
			remindVOS.add(remindVO);
		}
		return remindVOS;
	}

}
