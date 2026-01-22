
package com.lnsoft.system.wrapper;

import com.lnsoft.system.vo.RegionVO;
import com.lnsoft.core.mp.support.BaseEntityWrapper;
import com.lnsoft.core.tool.node.ForestNodeMerger;
import com.lnsoft.core.tool.utils.BeanUtil;
import com.lnsoft.core.tool.utils.SpringUtil;
import com.lnsoft.system.entity.Region;
import com.lnsoft.system.service.IRegionService;

import java.util.List;
import java.util.Objects;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author guozhao
 */
public class RegionWrapper extends BaseEntityWrapper<Region, RegionVO> {

	private static IRegionService regionService;

	static {
		regionService = SpringUtil.getBean(IRegionService.class);
	}

	public static RegionWrapper build() {
		return new RegionWrapper();
	}

	@Override
	public RegionVO entityVO(Region region) {
		RegionVO regionVO = Objects.requireNonNull(BeanUtil.copy(region, RegionVO.class));
		Region parentRegion = regionService.getById(region.getParentCode());
		regionVO.setParentName(parentRegion.getName());
		return regionVO;
	}

	public List<RegionVO> listNodeLazyVO(List<RegionVO> list) {
		return ForestNodeMerger.merge(list);
	}

}
