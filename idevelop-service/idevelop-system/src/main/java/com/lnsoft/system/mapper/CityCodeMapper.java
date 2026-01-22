package com.lnsoft.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lnsoft.system.entity.CityCode;
import com.lnsoft.system.vo.CityCodeVO;

import java.util.List;
import java.util.Map;

public interface CityCodeMapper extends BaseMapper<CityCode> {
	List<CityCodeVO> getCityCodeList(Map<String, Object> param);

	int insertList(List<CityCode> param);

	int updateByCityCode(CityCode param);

	int deleteList(List<CityCode> param);

	List<CityCodeVO> getRegion();
}
