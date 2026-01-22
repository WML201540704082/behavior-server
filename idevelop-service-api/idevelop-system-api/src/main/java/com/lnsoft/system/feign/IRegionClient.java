package com.lnsoft.system.feign;


import com.lnsoft.core.launch.constant.AppConstant;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.system.entity.Region;
import com.lnsoft.system.vo.RegionVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Feign接口类
 *
 * @author xyz
 */
@FeignClient(
	value = AppConstant.APPLICATION_SYSTEM_NAME,
	fallback = IRegionClientFallback.class
)
public interface IRegionClient {

	String API_PREFIX = "/region";

	/**
	 * 懒加载列表
	 *
	 * @param parentCode
	 * @return
	 */
	@GetMapping(API_PREFIX + "/getLazyTree")
	R<List<RegionVO>> lazyTree(@RequestParam("parentCode") String parentCode);

	/**
	 * 获取山东省区域树形节点
	 *
	 * @param parentCode
	 * @param code
	 * @param name
	 * @return
	 */
	@GetMapping((API_PREFIX + "/get/tree"))
	R<String> tree(@RequestParam("parentCode") String parentCode, @RequestParam("code") String code, @RequestParam("name") String name);

	/**
	 * 根据code获取
	 */
	@GetMapping(API_PREFIX +"/getByCode")
	R<Region> getByCode(@RequestParam("code") String code);
}
