
package com.lnsoft.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.core.tool.utils.StringPool;
import com.lnsoft.system.entity.Region;
import com.lnsoft.system.mapper.RegionMapper;
import com.lnsoft.system.service.IRegionService;
import com.lnsoft.system.vo.RegionVO;
import com.lnsoft.system.wrapper.RegionWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 行政区划表 服务实现类
 *
 * @author guozhao
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements IRegionService {
	public static final int PROVINCE_LEVEL = 1;
	public static final int CITY_LEVEL = 2;
	public static final int DISTRICT_LEVEL = 3;
	public static final int TOWN_LEVEL = 4;
	public static final int VILLAGE_LEVEL = 5;

	@Override
	public boolean submit(Region region) {
		Long cnt = baseMapper.selectCount(Wrappers.<Region>query().lambda().eq(Region::getCode, region.getCode()));
		if (cnt > 0) {
			return this.updateById(region);
		}
		// 设置祖区划编号
		String parentCode = region.getParentCode();
		if (StringUtils.startsWith(parentCode, "00")) {
			parentCode = parentCode.substring(2);
			region.setParentCode(parentCode);
		}
		Region parent = baseMapper.selectById(parentCode);
		if (Func.isNotEmpty(parent) || Func.isNotEmpty(parent.getCode())) {
			String ancestors = parent.getAncestors() + StringPool.COMMA + parent.getCode();
			region.setAncestors(ancestors);

			String shortName = region.getShortName();
			String fullName = parent.getFullName() + StringPool.SLASH + shortName;
			region.setFullName(fullName);

		}
		String code = region.getCode();
		if (StringUtils.startsWith(code, "00")) {
			code = code.substring(2);
			region.setCode(code);
		}

		// 设置省、市、区、镇、村
		Integer level = region.getLevel();
		String name = region.getName();
		if (level == PROVINCE_LEVEL) {
			region.setProvinceCode(code);
			region.setProvinceName(name);
		} else if (level == CITY_LEVEL) {
			region.setCityCode(code);
			region.setCityName(name);
		} else if (level == DISTRICT_LEVEL) {
			region.setDistrictCode(code);
			region.setDistrictName(name);
		} else if (level == TOWN_LEVEL) {
			region.setTownCode(code);
			region.setTownName(name);
		} else if (level == VILLAGE_LEVEL) {
			region.setVillageCode(code);
			region.setVillageName(name);
		}
		return this.save(region);
	}

	@Override
	public boolean removeRegion(String id) {
		Long cnt = baseMapper.selectCount(Wrappers.<Region>query().lambda().eq(Region::getParentCode, id));
		if (cnt > 0) {
			throw new ServiceException("请先删除子节点!");
		}
		return removeById(id);
	}

	@Override
	public List<RegionVO> lazyList(String parentCode, Map<String, Object> param) {
		return baseMapper.lazyList(parentCode, param);
	}

	@Override
	public List<RegionVO> lazyTree(String parentCode, Map<String, Object> param) {
		return baseMapper.lazyTree(parentCode, param);
	}

	/**
	 * 山东区域树下拉列表
	 *
	 * @param parentCode 父级code
	 * @param param      查询条件
	 * @return List
	 */
	@Override
	public List<RegionVO> provinceList(String parentCode, Map<String, Object> param) {
		return baseMapper.provinceList(parentCode, param);
	}

	@Override
	public List<Region> allList(String parentCode) {
		ArrayList<Region> arrayList = new ArrayList<>();
		LambdaQueryWrapper<Region> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.likeRight(Region::getParentCode,parentCode);
		List<Region> regions = baseMapper.selectList(queryWrapper);
		LambdaQueryWrapper<Region> queryWrapper1 = new LambdaQueryWrapper<>();
		queryWrapper1.eq(Region::getCode,"37");
		Region region = baseMapper.selectOne(queryWrapper1);
		arrayList.add(region);
		arrayList.addAll(regions);
		return arrayList;
	}

	/**
	 * 获取山东省区域树形节点
	 *
	 * @param parentCode 父级code
	 * @param param      查询条件
	 * @return
	 */
	@Override
	public List<RegionVO> tree(String parentCode, Map<String, Object> param) {
		List<RegionVO> regionVOS = baseMapper.tree(parentCode, param);

		List<RegionVO> regionVOS1 = RegionWrapper.build().listNodeLazyVO(regionVOS);

		return regionVOS1;
	}
}
