package com.lnsoft.device.api.safeaccess.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.device.entity.SafeaccessUserAccessDisable;
import com.lnsoft.device.api.safeaccess.vo.SafeaccessUserAccessDisableVO;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessUserAccessDisableService;
import com.lnsoft.core.boot.ctrl.IdevelopController;

/**
 * 用户入网临时禁用表 控制器
 *
 * @author Idevelop
 * @since 2024-06-05
 */
@RestController
@AllArgsConstructor
@RequestMapping("/safeaccessuseraccessdisable")
@Api(value = "用户入网临时禁用表", tags = "用户入网临时禁用表接口")
public class SafeaccessUserAccessDisableController extends IdevelopController {

	private ISafeaccessUserAccessDisableService safeaccessUserAccessDisableService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入safeaccessUserAccessDisable")
	public R<SafeaccessUserAccessDisable> detail(SafeaccessUserAccessDisable safeaccessUserAccessDisable) {
		SafeaccessUserAccessDisable detail = safeaccessUserAccessDisableService.getOne(Condition.getQueryWrapper(safeaccessUserAccessDisable));
		return R.data(detail);
	}

	/**
	 * 分页 用户入网临时禁用表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入safeaccessUserAccessDisable")
	public R<IPage<SafeaccessUserAccessDisable>> list(SafeaccessUserAccessDisable safeaccessUserAccessDisable, Query query) {
		IPage<SafeaccessUserAccessDisable> pages = safeaccessUserAccessDisableService.page(Condition.getPage(query), Condition.getQueryWrapper(safeaccessUserAccessDisable));
		return R.data(pages);
	}

	/**
	 * 自定义分页 用户入网临时禁用表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入safeaccessUserAccessDisable")
	public R<IPage<SafeaccessUserAccessDisableVO>> page(SafeaccessUserAccessDisableVO safeaccessUserAccessDisable, Query query) {
		IPage<SafeaccessUserAccessDisableVO> pages = safeaccessUserAccessDisableService.selectSafeaccessUserAccessDisablePage(Condition.getPage(query), safeaccessUserAccessDisable);
		return R.data(pages);
	}

	/**
	 * 新增 用户入网临时禁用表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入safeaccessUserAccessDisable")
	public R save(@Valid @RequestBody SafeaccessUserAccessDisable safeaccessUserAccessDisable) {
		return R.status(safeaccessUserAccessDisableService.save(safeaccessUserAccessDisable));
	}

	/**
	 * 修改 用户入网临时禁用表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入safeaccessUserAccessDisable")
	public R update(@Valid @RequestBody SafeaccessUserAccessDisable safeaccessUserAccessDisable) {
		return R.status(safeaccessUserAccessDisableService.updateById(safeaccessUserAccessDisable));
	}

	/**
	 * 新增或修改 用户入网临时禁用表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入safeaccessUserAccessDisable")
	public R submit(@Valid @RequestBody SafeaccessUserAccessDisable safeaccessUserAccessDisable) {
		return R.status(safeaccessUserAccessDisableService.saveOrUpdate(safeaccessUserAccessDisable));
	}


	/**
	 * 删除 用户入网临时禁用表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(safeaccessUserAccessDisableService.deleteLogic(Func.toLongList(ids)));
	}


}
