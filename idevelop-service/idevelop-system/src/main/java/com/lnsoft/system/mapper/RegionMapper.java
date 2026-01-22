
package com.lnsoft.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lnsoft.system.entity.Region;
import com.lnsoft.system.vo.RegionVO;

import java.util.List;
import java.util.Map;

/**
 * 行政区划表 Mapper 接口
 *
 * @author guozhao
 */
public interface RegionMapper extends BaseMapper<Region> {

	/**
	 * 懒加载列表
	 *
	 * @param parentCode
	 * @param param
	 * @return
	 */
	List<RegionVO> lazyList(String parentCode, Map<String, Object> param);

	/**
	 * 懒加载列表
	 *
	 * @param parentCode
	 * @param param
	 * @return
	 */
	List<RegionVO> lazyTree(String parentCode, Map<String, Object> param);

	/**
	 * 山东区域树下拉列表
	 *
	 * @param parentCode 父级code
	 * @param param      查询条件
	 * @return List
	 */
	List<RegionVO> provinceList(String parentCode, Map<String, Object> param);


	/**
	 * 获取山东省区域树形节点
	 *
	 * @param parentCode 父级code
	 * @param param      查询条件
	 * @return
	 */
	List<RegionVO> tree(String parentCode, Map<String, Object> param);

}
