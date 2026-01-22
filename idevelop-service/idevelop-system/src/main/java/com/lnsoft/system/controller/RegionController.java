
package com.lnsoft.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.secure.annotation.PreAuth;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.system.entity.Region;
import com.lnsoft.system.service.IRegionService;
import com.lnsoft.system.vo.RegionVO;
import com.lnsoft.system.wrapper.RegionWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 行政区划表 控制器
 *
 * @author guozhao
 */
@RestController
@AllArgsConstructor
@RequestMapping("/region")
@Api(value = "行政区划表", tags = "行政区划表接口")
public class RegionController extends IdevelopController {

	private final IRegionService regionService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入region")
	@PreAuth("hasPerm('system:common:all')")
	public R<RegionVO> detail(Region region) {
		Region detail = regionService.getOne(Condition.getQueryWrapper(region));
		return R.data(RegionWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 行政区划表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入region")
	@PreAuth("hasPerm('system:common:all')")
	public R<IPage<Region>> list(Region region, Query query) {
		IPage<Region> pages = regionService.page(Condition.getPage(query), Condition.getQueryWrapper(region));
		return R.data(pages);
	}

	/**
	 * 懒加载列表
	 */
	@GetMapping("/lazy-list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "区划编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "name", value = "区划名称", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 3)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "懒加载列表", notes = "传入menu")
	public R<List<RegionVO>> lazyList(String parentCode, @ApiIgnore @RequestParam Map<String, Object> menu) {
		List<RegionVO> list = regionService.lazyList(parentCode, menu);
		return R.data(RegionWrapper.build().listNodeLazyVO(list));
	}

	/**
	 * 懒加载列表
	 */
	@GetMapping("/lazy-tree")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "区划编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "name", value = "区划名称", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "懒加载列表", notes = "传入menu")
	public R<List<RegionVO>> lazyTree(String parentCode, @ApiIgnore @RequestParam Map<String, Object> menu) {
		List<RegionVO> list = regionService.lazyTree(parentCode, menu);
		return R.data(RegionWrapper.build().listNodeLazyVO(list));
	}

	/**
	 * 全量加载（山东）
	 */
	@GetMapping("/all/list")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "全量加载（山东）", notes = "")
	public R allList(String parentCode){
		List<Region> list = regionService.allList(parentCode);
		return R.data(list);
	}
	/**
	 * 新增 行政区划表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "新增", notes = "传入region")
	public R save(@Valid @RequestBody Region region) {
		return R.status(regionService.save(region));
	}

	/**
	 * 修改 行政区划表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "修改", notes = "传入region")
	public R update(@Valid @RequestBody Region region) {
		return R.status(regionService.updateById(region));
	}

	/**
	 * 新增或修改 行政区划表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "新增或修改", notes = "传入region")
	public R submit(@Valid @RequestBody Region region) {
		return R.status(regionService.submit(region));
	}


	/**
	 * 删除 行政区划表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入主键")
	public R remove(@ApiParam(value = "主键", required = true) @RequestParam String id) {
		return R.status(regionService.removeRegion(id));
	}

	/**
	 * 行政区划下拉数据源
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "下拉数据源", notes = "传入tenant")
	public R<List<Region>> select(@RequestParam(required = false, defaultValue = "00") String code) {
		List<Region> list = regionService.list(Wrappers.<Region>query().lambda().eq(Region::getParentCode, code));
		return R.data(list);
	}

	/**
	 * 山东区域树下拉列表
	 */
	@GetMapping("/province-tree")
	@ApiOperationSupport(order = 10)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "区划编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "name", value = "区划名称", paramType = "query", dataType = "string")
	})
	@ApiOperation(value = "山东区域树下拉列表")
	public R<List<RegionVO>> provinceList(String parentCode, @ApiIgnore @RequestParam Map<String, Object> menu) {
		List<RegionVO> list = regionService.provinceList(parentCode, menu);
		return R.data(RegionWrapper.build().listNodeLazyVO(list));
	}
}
