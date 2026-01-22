package com.lnsoft.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.system.entity.CityCode;
import com.lnsoft.system.mapper.CityCodeMapper;
import com.lnsoft.system.service.ICityCodeService;
import com.lnsoft.system.vo.CityCodeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CityCodeServiceImpl extends ServiceImpl<CityCodeMapper, CityCode> implements ICityCodeService {

	private final String provinceRegionCode = "37";
	private final String PARENT_CODE = "parentCode";

	/**
	 * 查询登录账号信息
	 */
	private String getRegionCode() {
		IdevelopUser user = SecureUtil.getUser();
		String regionCode = user.getRegionCode();
		log.info("regionCode：{}", regionCode);
		return regionCode;
	}

	@Override
	public List<CityCodeVO> getTree(Map<String, Object> param) {
		log.info("请求参数：{}", param);
		String regionCode = getRegionCode();
		// 省登录时可以查所有数据
		if (!provinceRegionCode.equals(regionCode)) {
			// 只能查登录账号对应的数据
			param.put(PARENT_CODE, regionCode);
		}
		List<CityCodeVO> cityCodeList = baseMapper.getCityCodeList(param);
		// type: 1省 2市 3县
		String cityType = "2";
		String countryType = "3";
		Map<String, CityCodeVO> cityMap = cityCodeList.stream()
			.filter(cityCode -> cityType.equals(cityCode.getType()))
			.collect(Collectors.toMap(CityCodeVO::getSelfCode, c -> c));
		cityCodeList.stream()
			.filter(c -> countryType.equals(c.getType()))
			.forEach(c -> {
				CityCodeVO r = cityMap.get(c.getParentCode());
				if (r != null) {
					r.getChildren().add(c);
				}
			});
		return new ArrayList<>(cityMap.values());
	}

	@Override
	public List<CityCodeVO> getList(Map<String, Object> param) {
		log.info("请求参数：{}", param);
		String regionCode = getRegionCode();
		// 省登录时可以查所有数据
		if (!provinceRegionCode.equals(regionCode)) {
			// 只能查登录账号对应的数据
			param.put(PARENT_CODE, regionCode);
		}
		return baseMapper.getCityCodeList(param);
	}

	@Override
	public boolean add(List<CityCode> param) {
		log.info("请求参数：{}", param);
		IdevelopUser user = SecureUtil.getUser();
		// 登录账号信息
		String regionCode = user.getRegionCode();
		String regionName = user.getRegionName();
		log.info("regionCode：{},regionName：{}", regionCode, regionName);
		// 省登录时不限制
		if (!provinceRegionCode.equals(regionCode)) {
			// 只能添加自己地区的数据
			Map<String, CityCodeVO> region = getRegion();
			List<CityCode> newParam = new ArrayList<>();
			for (CityCode cityCode : param) {
				CityCodeVO vo = region.get(cityCode.getSelfCode());
				if (vo != null) {
					// vo为空说明传过来的区域编码不对
					cityCode.setSelfCode(vo.getSelfCode());
					cityCode.setSelfName(vo.getSelfName());
					cityCode.setParentCode(vo.getParentCode());
					cityCode.setParentName(vo.getParentName());
					newParam.add(cityCode);
				}
			}
			param = newParam;
		}
		if (!param.isEmpty()) {
			int res = baseMapper.insertList(param);
			return res > 0;
		}
		return false;
	}

	/**
	 * 查询山东区域编码
	 */
	public Map<String, CityCodeVO> getRegion() {
		Map<String, CityCodeVO> res = new HashMap<>();
		// 查询idevelop_region表，保存地市编码信息
		List<CityCodeVO> codeVOList = baseMapper.getRegion();
		for (CityCodeVO codeVO : codeVOList) {
			res.put(codeVO.getSelfCode(), codeVO);
		}
		return res;
	}

	@Override
	public boolean update(List<CityCode> param) {
		log.info("请求参数：{}", param);
		// 单个修改
		CityCode cityCode = param.get(0);
		if (cityCode != null && cityCode.getId() != null) {
			int i = baseMapper.updateByCityCode(cityCode);
			return i > 0;
		}
		log.warn("参数为空");
		return false;
	}

	@Override
	public boolean delete(List<CityCode> param) {
		log.info("请求参数：{}", param);
		int res = baseMapper.deleteList(param);
		return res > 0;
	}
}
