package com.lnsoft.system.service;

import com.lnsoft.system.entity.CityCode;
import com.lnsoft.system.vo.CityCodeVO;

import java.util.List;
import java.util.Map;

public interface ICityCodeService {
	List<CityCodeVO> getTree(Map<String, Object> param);

	boolean add(List<CityCode> param);

	boolean update(List<CityCode> param);

	boolean delete(List<CityCode> param);

	List<CityCodeVO> getList(Map<String, Object> param);
}
