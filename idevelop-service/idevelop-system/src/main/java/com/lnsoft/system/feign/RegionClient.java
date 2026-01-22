package com.lnsoft.system.feign;

import com.alibaba.fastjson.JSONObject;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.system.entity.Region;
import com.lnsoft.system.service.IRegionService;
import com.lnsoft.system.vo.RegionVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xyzadmin
 */
@ApiIgnore
@RestController
@AllArgsConstructor
public class RegionClient implements IRegionClient {

	IRegionService regionService;

	@Override
	@GetMapping((API_PREFIX + "/getLazyTree"))
	public R<List<RegionVO>> lazyTree(String parentCode) {
		Map<String, Object> menu = new HashMap<>();
		menu.put("parentCode", parentCode);
		return R.data(regionService.lazyList(parentCode, menu));
	}


	/**
	 * 获取山东省区域树形节点
	 *
	 * @param parentCode
	 * @param code
	 * @param name
	 * @return
	 */
	@Override
	@GetMapping((API_PREFIX + "/get/tree"))
	public R<String> tree(String parentCode, String code, String name) {
		Map<String, Object> menu = new HashMap<>();
		menu.put("code", code);
		menu.put("name", name);
		List<RegionVO> tree = regionService.tree(parentCode, menu);

		String treeString = JSONObject.toJSONString(tree);

		return R.data(treeString);
	}

	@Override
	@GetMapping(API_PREFIX +"/getByCode")
	public R<Region> getByCode(String code) {
		Region region = regionService.getById(code);
		return R.data(region);
	}

}
