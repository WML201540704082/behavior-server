package com.lnsoft.device.api.i6000.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.i6000.entity.CmdbI6000;
import com.lnsoft.device.api.i6000.service.ICmdbI6000Service;
import com.lnsoft.device.api.i6000.vo.CmdbI6000VO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 模型属性映射表(查询专用) 控制器
 *
 * @author Idevelop
 * @since 2024-11-06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/cmdbi6000")
@Api(value = "模型属性映射表(查询专用)", tags = "模型属性映射表(查询专用)接口")
public class CmdbI6000Controller extends IdevelopController {

	private ICmdbI6000Service cmdbI6000Service;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入cmdbI6000")
	public R<CmdbI6000> detail(CmdbI6000 cmdbI6000) {
		CmdbI6000 detail = cmdbI6000Service.getOne(Condition.getQueryWrapper(cmdbI6000));
		return R.data(detail);
	}

	/**
	 * 分页 模型属性映射表(查询专用)
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入cmdbI6000")
	public R<IPage<CmdbI6000>> list(CmdbI6000 cmdbI6000, Query query) {
		IPage<CmdbI6000> pages = cmdbI6000Service.page(Condition.getPage(query), Condition.getQueryWrapper(cmdbI6000));
		return R.data(pages);
	}

	/**
	 * 自定义分页 模型属性映射表(查询专用)
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入cmdbI6000")
	public R<IPage<CmdbI6000VO>> page(CmdbI6000VO cmdbI6000, Query query) {
		IPage<CmdbI6000VO> pages = cmdbI6000Service.selectCmdbI6000Page(Condition.getPage(query), cmdbI6000);
		return R.data(pages);
	}

	/**
	 * 新增 模型属性映射表(查询专用)
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入cmdbI6000")
	public R save(@Valid @RequestBody CmdbI6000 cmdbI6000) {
		return R.status(cmdbI6000Service.save(cmdbI6000));
	}

	/**
	 * 修改 模型属性映射表(查询专用)
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入cmdbI6000")
	public R update(@Valid @RequestBody CmdbI6000 cmdbI6000) {
		return R.status(cmdbI6000Service.updateById(cmdbI6000));
	}

	/**
	 * 新增或修改 模型属性映射表(查询专用)
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入cmdbI6000")
	public R submit(@Valid @RequestBody CmdbI6000 cmdbI6000) {
		return R.status(cmdbI6000Service.saveOrUpdate(cmdbI6000));
	}


	/**
	 * 删除 模型属性映射表(查询专用)
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(cmdbI6000Service.deleteLogic(Func.toLongList(ids)));
	}




}
