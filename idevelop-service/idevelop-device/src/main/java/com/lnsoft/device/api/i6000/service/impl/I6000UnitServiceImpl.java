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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.device.api.i6000.dto.I6000OriViewDTO;
import com.lnsoft.device.api.i6000.entity.I6000External;
import com.lnsoft.device.api.i6000.entity.I6000Unit;
import com.lnsoft.device.api.i6000.mapper.I6000UnitMapper;
import com.lnsoft.device.api.i6000.service.II6000Service;
import com.lnsoft.device.api.i6000.service.II6000UnitService;
import com.lnsoft.device.api.i6000.vo.I6000UnitVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * I6000单位 服务实现类
 *
 * @author Idevelop
 * @since 2024-04-12
 */
@Service
public class I6000UnitServiceImpl extends BaseServiceImpl<I6000UnitMapper, I6000Unit> implements II6000UnitService {
	@Resource
	private II6000Service ii6000Service;
	@Override
	public IPage<I6000UnitVO> selectI6000UnitPage(IPage<I6000UnitVO> page, I6000UnitVO i6000Unit) {
		return page.setRecords(baseMapper.selectI6000UnitPage(page, i6000Unit));
	}

	@Override
	public R inert() {
		I6000OriViewDTO i6000OriViewDTO = new I6000OriViewDTO();
		i6000OriViewDTO.setOriViewId("EXT_101");
		i6000OriViewDTO.setPageStart("1");
		i6000OriViewDTO.setPageSize("10000");
		List<I6000External> i6000Externals = ii6000Service.selectOriView(i6000OriViewDTO);
		for (I6000External item : i6000Externals) {
			I6000Unit dept = new I6000Unit();
			dept.setI6000UnitId(item.getExtId());
			dept.setI6000UnitName(item.getExtName());
			dept.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
			LambdaQueryWrapper<I6000Unit> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(I6000Unit::getI6000UnitId,item.getExtId()).eq(I6000Unit::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
			I6000Unit selectOne = baseMapper.selectOne(queryWrapper);

			if (ObjectUtil.isEmpty(selectOne)){
				//新增
				dept.setCreateTime(new Date());
				dept.setStatus(0);
				baseMapper.insert(dept);
			}else {
				//更新
				dept.setUpdateTime(new Date());
				dept.setStatus(0);
				baseMapper.updateById(dept);
			}
		}
		return R.success("操作成功");
	}

//	@Override
//	public R marry() {
//		//匹配规则
//		return null;
//	}

}
