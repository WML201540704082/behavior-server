
package com.lnsoft.system.feign;

import com.lnsoft.core.tool.api.R;
import com.lnsoft.system.entity.Region;
import com.lnsoft.system.vo.RegionVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Feign失败配置
 *
 * @author xyz
 */
@Component
public class IRegionClientFallback implements IRegionClient {


	@Override
	public R<List<RegionVO>> lazyTree(String parentCode) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> tree(String parentCode, String code, String name) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<Region> getByCode(String code) {
		return R.fail("获取数据失败");
	}

}
