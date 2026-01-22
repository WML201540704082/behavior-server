
package com.lnsoft.system.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.node.ForestNodeMerger;
import com.lnsoft.core.tool.utils.*;
import com.lnsoft.system.dto.DeptDTO;
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.mapper.DeptMapper;
import com.lnsoft.system.service.IDeptService;
import com.lnsoft.system.vo.DeptVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author guozhao
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

	@Override
	public IPage<DeptVO> selectDeptPage(IPage<DeptVO> page, DeptVO dept) {
		return page.setRecords(baseMapper.selectDeptPage(page, dept));
	}

	@Override
	public List<DeptVO> tree(String tenantId) {
		return ForestNodeMerger.merge(baseMapper.tree(tenantId));
	}

	@Override
	public List<DeptVO> tree(DeptDTO dept) {
		return baseMapper.treeByPid(dept);
	}

	@Override
	public String getDeptIds(String tenantId, String deptNames) {
		List<Dept> deptList = baseMapper.selectList(Wrappers.<Dept>query().lambda().eq(Dept::getTenantId, tenantId).in(Dept::getDeptName, Func.toStrList(deptNames)));
		if (deptList != null && deptList.size() > 0) {
			return deptList.stream().map(dept -> Func.toStr(dept.getId())).distinct().collect(Collectors.joining(","));
		}
		return null;
	}

	@Override
	public List<String> getDeptNames(String deptIds) {
		return baseMapper.getDeptNames(Func.toLongArray(deptIds));
	}

	@Override
	public Dept getCorp(Long deptId) {
		return getCorpByDeptId(deptId);
	}

	public Dept getCorpByDeptId(Long deptId) {
		Dept dept = baseMapper.selectById(deptId);
		if ("CORP".equals(dept.getType())) {
			return dept;
		} else {
			return getCorpByDeptId(dept.getParentId());
		}
	}

	@Override
	public Dept getDeptOrGroup(Long deptId) {
		return baseMapper.selectById(deptId);
	}

	@Override
	public Dept getDeptByGroup(Long deptId) {
		return getDeptByGroupId(deptId);
	}

	public Dept getDeptByGroupId(Long deptId) {
		Dept dept = baseMapper.selectById(deptId);
		if ("DEPT".equals(dept.getType())) {
			return dept;
		} else {
			return getDeptByGroupId(dept.getParentId());
		}
	}


	@Override
	public boolean submit(Dept dept) {
		CacheUtil.clear(CacheUtil.SYS_CACHE);
		if (Func.isEmpty(dept.getParentId())) {
			dept.setTenantId(SecureUtil.getTenantId());
			dept.setParentId(IdevelopConstant.TOP_PARENT_ID);
			dept.setAncestors(String.valueOf(IdevelopConstant.TOP_PARENT_ID));
		}
		if (dept.getParentId() > 0) {
			Dept parent = getById(dept.getParentId());
			if (Func.toLong(dept.getParentId()) == Func.toLong(dept.getId())) {
				throw new ServiceException("父节点不可选择自身!");
			}
			dept.setTenantId(parent.getTenantId());
			String ancestors = parent.getAncestors() + StringPool.COMMA + dept.getParentId();
			dept.setAncestors(ancestors);
		}
		if (StringUtils.isNotEmpty(dept.getTeamPhone())) {
			String mask = this.mask(dept.getTeamPhone(), 3, 4);
			dept.setTeamPhone(mask);
		}
		dept.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
		return saveOrUpdate(dept);
	}

	public String mask(String str, int prefix, int suffix) {
		if (str == null) {
			return str;
		}
		if (StringUtils.contains(str, "****")) {
			return str;
		}
		if (str.length() <= prefix + suffix) {
			return DigestUtil.encrypt(str);
		}

		return str.substring(0, prefix) + "****" + str.substring(str.length() - suffix);
	}

	@Override
	public List<Dept> queryDept() {
		Map<String, Object> param = Maps.newHashMap();
		IdevelopUser user = SecureUtil.getUser();
		String corpId = user.getCorpId();
		Dept dept = baseMapper.selectById(corpId);
		param.put("parent_id", dept.getParentId());
		param.put("type", "CORP");
		return baseMapper.selectByMap(param);
	}


	/**
	 * 获取部门树形结构
	 *
	 * @return R
	 */
	@Override
	public R<DeptVO> treeList() {
		IdevelopUser user = SecureUtil.getUser();
		List<DeptVO> deptVOArrayList = new ArrayList<>();
		String deptId = user.getDeptId();
		Dept dept = baseMapper.selectOne(new LambdaQueryWrapper<Dept>().eq(Dept::getId, deptId).eq(Dept::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		DeptVO deptVO = Convert.convert(DeptVO.class, dept);
		String[] split = dept.getAncestors().split(",");
		String parentId;
		if (split.length == 1) {
			parentId = dept.getAncestors();
		} else if (split.length == 2) {
			parentId = dept.getId().toString();
		} else {
			parentId = split[split.length - 1];
		}
		getDeptList(Long.parseLong(parentId), deptVO, deptVOArrayList);
		return R.data(deptVO);
	}

	@Override
	public List<Dept> getTreeList(String parentId) {
		List<Dept> deptList = baseMapper.selectList(new LambdaQueryWrapper<Dept>().eq(Dept::getParentId, parentId).eq(Dept::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		return deptList;
	}

	@Override
	public List<Dept> getAllCorp() {
		return baseMapper.getAllCorp();
	}

	/**
	 * 刷新ISC单位和部门数据
	 *
	 * @param type
	 * @return
	 */
	@Override
	public Boolean refresh(String type) {
		Boolean result = Boolean.FALSE;
		if (StringUtils.equals(type, "CORP")) {
			List<Dept> corpList = baseMapper.selectList(new LambdaQueryWrapper<Dept>()
				.eq(Dept::getType, "CORP")
				.eq(Dept::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
			List<Map<String, String>> iscDictList = baseMapper.getIscDict("CORP", "", "");
			Map<String, Map<String, String>> iscDictMap = iscDictList.stream().collect(Collectors.toMap(map -> map.get("deptName"), map -> map));
			for (Dept dept : corpList) {
				if (dept.getRegionCode().length() <= 2) {
					// 处理省公司
					Map<String, String> map = iscDictMap.get("国网山东省电力公司");
					dept.setFullName(map.get("deptName"));
					dept.setIscDeptId(map.get("iscDeptId"));
					dept.setIscParentId(map.get("iscParentId"));
					dept.setRemark(type);
				} else if (dept.getRegionCode().length() > 2 && dept.getRegionCode().length() <= 4) {
					// 处理17个地市公司
					String substring = dept.getRegionName().substring(0, 2);
					iscDictMap.entrySet().stream()
						.filter(item -> item.getKey().contains("本部") && item.getKey().contains(substring))
						.forEach(item -> {
							Map<String, String> value = item.getValue();
							dept.setIscDeptId(value.get("iscDeptId"));
							dept.setIscParentId(value.get("iscParentId"));
							dept.setRemark(type);
						});
					// 枣庄市
					if (StringUtils.equals("3704", dept.getRegionCode())) {
						Map<String, String> map = iscDictMap.get("国网山东省电力公司枣庄供电公司");
						dept.setFullName(map.get("deptName"));
						dept.setIscDeptId(map.get("iscDeptId"));
						dept.setIscParentId(map.get("iscParentId"));
						dept.setRemark(type);
					}
					// 青岛市
					if (StringUtils.equals("3702", dept.getRegionCode())) {
						Map<String, String> map = iscDictMap.get("国网山东省电力公司青岛供电公司");
						dept.setFullName(map.get("deptName"));
						dept.setIscDeptId(map.get("iscDeptId"));
						dept.setIscParentId(map.get("iscParentId"));
						dept.setRemark(type);
					}
					// 莱芜市
					if (StringUtils.equals("3712", dept.getRegionCode())) {
						Map<String, String> map = iscDictMap.get("国网山东省电力公司莱芜供电公司");
						dept.setFullName(map.get("deptName"));
						dept.setIscDeptId(map.get("iscDeptId"));
						dept.setIscParentId(map.get("iscParentId"));
						dept.setRemark(type);
					}
				} else {
					// 处理剩下的地市公司
					iscDictMap.entrySet().stream()
						.filter(item -> item.getKey().contains(dept.getRegionName()))
						.forEach(item -> {
							Map<String, String> value = item.getValue();
							dept.setFullName(value.get("deptName"));
							dept.setIscDeptId(value.get("iscDeptId"));
							dept.setIscParentId(value.get("iscParentId"));
							dept.setRemark(type);
						});
				}
			}
			result = this.saveOrUpdateBatch(corpList);
		} else if (StringUtils.equals(type, "DEPT")) {
			List<Dept> corpList = baseMapper.selectList(new LambdaQueryWrapper<Dept>()
				.eq(Dept::getType, "CORP")
				.eq(Dept::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
			List<Dept> addDept = new ArrayList<>();
			for (Dept dept : corpList) {
				if (dept.getRegionCode().length() > 2) {
					String iscDeptId = dept.getIscDeptId();
					Integer sort = 0;
					List<Map<String, String>> iscDictList = baseMapper.getIscDict(type, "", iscDeptId);
					for (Map<String, String> deptMap : iscDictList) {
						Dept newDept = new Dept();
						newDept.setTenantId("000000");
						newDept.setParentId(dept.getId());
						newDept.setAncestors(dept.getAncestors() + "," + dept.getId());
						newDept.setDeptName(deptMap.get("deptName"));
						newDept.setFullName(deptMap.get("deptName"));
						newDept.setRegionCode(dept.getRegionCode());
						newDept.setRegionName(dept.getRegionName());
						newDept.setType(type);
						newDept.setSort(sort++);
						newDept.setRemark(type);
						newDept.setErpUnit(dept.getErpUnit());
						newDept.setErpUnitCode(dept.getErpUnitCode());
						// newDept.setErpDept(dept.getErpDept());
						// newDept.setErpDeptCode(dept.getErpDeptCode());
						newDept.setI6000Unit(dept.getI6000Unit());
						newDept.setI6000UnitCode(dept.getI6000UnitCode());
						// newDept.setI6000Dept(dept.getI6000Dept());
						// newDept.setI6000DeptCode(dept.getI6000DeptCode());
						newDept.setIscDeptId(deptMap.get("iscDeptId"));
						newDept.setIscParentId(deptMap.get("iscParentId"));
						newDept.setIsDeleted(0);
						addDept.add(newDept);
					}
				}
			}
			result = this.saveOrUpdateBatch(addDept);
		} else if (StringUtils.equals(type, "TEAM")) {
			List<Dept> corpList = baseMapper.selectList(new LambdaQueryWrapper<Dept>()
				.eq(Dept::getType, "DEPT")
				.eq(Dept::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
			for (int i = 0; i < corpList.size(); i++) {
				Dept dept = corpList.get(i);

				result = forEachDept(dept);
			}
		}
		return result;
	}

	private Boolean forEachDept(Dept dept) {
		List<Dept> addDept = new ArrayList<>();
		String iscDeptId = dept.getIscDeptId();
		Integer sort = 0;
		if (StringUtils.isNotEmpty(iscDeptId)) {
			List<Map<String, String>> iscDictList = baseMapper.getIscDict("", "", iscDeptId);
			for (Map<String, String> deptMap : iscDictList) {
				Dept newDept = new Dept();
				newDept.setTenantId("000000");
				newDept.setParentId(dept.getId());
				newDept.setAncestors(dept.getAncestors() + "," + dept.getId());
				newDept.setDeptName(deptMap.get("deptName"));
				newDept.setFullName(deptMap.get("deptName"));
				newDept.setRegionCode(dept.getRegionCode());
				newDept.setRegionName(dept.getRegionName());
				String deptType = deptMap.get("deptName").endsWith("班") ? "TEAM" : "DEPT";
				newDept.setType(deptType);
				newDept.setSort(sort++);
				newDept.setRemark(deptType);
				newDept.setErpUnit(dept.getErpUnit());
				newDept.setErpUnitCode(dept.getErpUnitCode());
				// newDept.setErpDept(dept.getErpDept());
				// newDept.setErpDeptCode(dept.getErpDeptCode());
				newDept.setI6000Unit(dept.getI6000Unit());
				newDept.setI6000UnitCode(dept.getI6000UnitCode());
				// newDept.setI6000Dept(dept.getI6000Dept());
				// newDept.setI6000DeptCode(dept.getI6000DeptCode());
				newDept.setIscDeptId(deptMap.get("iscDeptId"));
				newDept.setIscParentId(deptMap.get("iscParentId"));
				newDept.setIsDeleted(0);
				addDept.add(newDept);
			}
			this.saveOrUpdateBatch(addDept);
			for (Dept dept1 : addDept) {
				forEachDept(dept1);
			}
		}
		return Boolean.TRUE;
	}


	public void getDeptList(Long parentId, DeptVO deptVO, List<DeptVO> deptVOArrayList) {
		List<Dept> deptList = baseMapper.selectList(new LambdaQueryWrapper<Dept>().eq(Dept::getParentId, parentId).eq(Dept::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		List<DeptVO> deptVOList = Convert.convert(new TypeReference<List<DeptVO>>() {
		}, deptList);
		deptVO.setChildren(deptVOList);
		deptVOList.forEach(item -> {
			List<Dept> selectList = baseMapper.selectList(new LambdaQueryWrapper<Dept>().eq(Dept::getParentId, item.getId()).eq(Dept::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
			if (CollectionUtil.isNotEmpty(selectList)) {
				List<DeptVO> deptVOListChildren = Convert.convert(new TypeReference<List<DeptVO>>() {
				}, selectList);
				item.setChildren(deptVOListChildren);
				getDeptList(item.getId(), item, deptVOListChildren);
			}
		});
	}


}
