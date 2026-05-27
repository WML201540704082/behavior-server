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
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.device.api.i6000.dto.I6000OriViewDTO;
import com.lnsoft.device.api.i6000.entity.I6000Dept;
import com.lnsoft.device.api.i6000.entity.I6000External;
import com.lnsoft.device.api.i6000.entity.I6000Unit;
import com.lnsoft.device.api.i6000.mapper.I6000DeptMapper;
import com.lnsoft.device.api.i6000.service.II6000DeptService;
import com.lnsoft.device.api.i6000.service.II6000Service;
import com.lnsoft.device.api.i6000.service.II6000UnitService;
import com.lnsoft.device.api.i6000.vo.I6000DeptVO;
import com.lnsoft.device.api.i6000.vo.I6000UnitDeptVO;
import com.lnsoft.device.api.i6000.vo.I6000XtythUnitVO;
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.feign.IDeptClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * I6000部门 服务实现类
 *
 * @author Idevelop
 * @since 2024-04-12
 */
@Service
@AllArgsConstructor
public class I6000DeptServiceImpl extends BaseServiceImpl<I6000DeptMapper, I6000Dept> implements II6000DeptService {

	private II6000UnitService ii6000UnitService;
	private II6000Service ii6000Service;
	private IDeptClient deptClient;

	@Override
	public IPage<I6000DeptVO> selectI6000DeptPage(IPage<I6000DeptVO> page, I6000DeptVO i6000Dept) {
		return page.setRecords(baseMapper.selectI6000DeptPage(page, i6000Dept));
	}

	@Override
	public List<I6000XtythUnitVO> lazyI6000Unit() {
		List<I6000Unit> i6000Units = ii6000UnitService.list(Condition.getQueryWrapper(new I6000Unit()));
		List<I6000XtythUnitVO> i6000XtythUnitVOList = i6000Units.stream().map(unit ->
			I6000XtythUnitVO.builder()
				.code(unit.getI6000UnitId())
				.name(unit.getI6000UnitName())
				.unitSort(1)
				.build()).collect(Collectors.toList());

		return i6000XtythUnitVOList;
	}

	/**
	 * 懒加载 I6000单位和部门
	 *
	 * @return
	 */
	@Override
	public I6000UnitDeptVO lazyI6000UnitDept(String unitCode) {
		I6000Unit i6000Unit = new I6000Unit();
		i6000Unit.setI6000UnitId(unitCode);
		I6000Unit i6000UnitOne = ii6000UnitService.getOne(Condition.getQueryWrapper(i6000Unit));

		I6000Dept i6000Dept = new I6000Dept();
		i6000Dept.setI6000UnitId(unitCode);
		List<I6000Dept> i6000DeptList = this.list(Condition.getQueryWrapper(i6000Dept));

		List<I6000UnitDeptVO.Children> childrenList = i6000DeptList.stream().map(dept ->
				I6000UnitDeptVO.Children.builder()
					.code(dept.getI6000DeptId())
					.name(dept.getI6000DeptName())
					.deptSort(2)
					.build())
			.collect(Collectors.toList());

		return I6000UnitDeptVO.builder()
			.code(i6000UnitOne.getI6000UnitId())
			.name(i6000UnitOne.getI6000UnitName())
			.childrenList(CollectionUtils.isEmpty(i6000DeptList) ? new ArrayList<>() : childrenList)
			.unitSort(1)
			.build();
	}

	@Override
	public R insert() {
//		I6000DeptDTO i6000Dept = new I6000DeptDTO();
//		i6000Dept.setExtCode("EXT_102");
		I6000OriViewDTO i6000OriViewDTO = new I6000OriViewDTO();
		i6000OriViewDTO.setOriViewId("EXT_102");
		i6000OriViewDTO.setPageStart("1");
		i6000OriViewDTO.setPageSize("10000");
		List<I6000External> i6000Externals = ii6000Service.selectOriView(i6000OriViewDTO);
		for (I6000External item : i6000Externals) {
			I6000Dept dept = new I6000Dept();
			dept.setI6000DeptId(item.getExtId());
			dept.setI6000DeptName(item.getExtName());
			dept.setI6000UnitId(item.getExtPid());
			dept.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);

			LambdaQueryWrapper<I6000Dept> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(I6000Dept::getI6000DeptId,item.getExtId());
			I6000Dept selectOne = baseMapper.selectOne(queryWrapper);
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

	@Override
	public R marry() {
		List<String> unitList = baseMapper.selectUnitId();
		for (String unitId : unitList) {
			//根据单位编码获取部门列表
			LambdaQueryWrapper<I6000Dept> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(I6000Dept::getI6000UnitId,unitId).eq(I6000Dept::getIsDeleted,IdevelopConstant.DB_NOT_DELETED);
			List<I6000Dept> i6000Depts = baseMapper.selectList(queryWrapper);
			List<I6000Dept> i6000DeptArrayList = new ArrayList<>();
			i6000DeptArrayList.addAll(i6000Depts);
			//根据单位编码
			R<List<Dept>> listR = deptClient.getByI6000Unit(unitId);
			if (ObjectUtil.isEmpty(listR.getData())){
				continue;
			}
			List<Dept> deptList = listR.getData();
			for (I6000Dept i6000Dept : i6000Depts) {
				for (Dept dept : deptList) {
					if (i6000Dept.getI6000DeptName().equals(dept.getDeptName())){
						dept.setI6000Dept(i6000Dept.getI6000DeptName());
						dept.setI6000DeptCode(i6000Dept.getI6000DeptId());
						deptClient.update(dept);
						i6000DeptArrayList.remove(i6000Dept);
					}
				}
			}
			//未同步的更新状态
			for (I6000Dept i6000Dept : i6000DeptArrayList) {
				i6000Dept.setStatus(1);
				baseMapper.updateById(i6000Dept);
			}

		}
		return R.success("操作成功");
	}

}
