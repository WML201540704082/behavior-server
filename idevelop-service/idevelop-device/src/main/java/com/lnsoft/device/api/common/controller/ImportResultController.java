package com.lnsoft.device.api.common.controller;

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
import com.lnsoft.device.api.common.entity.ImportResult;
import com.lnsoft.device.api.common.vo.ImportResultVO;
import com.lnsoft.device.api.common.service.IImportResultService;
import com.lnsoft.core.boot.ctrl.IdevelopController;

/**
 * 信通一体化平台导入文件结果下载 控制器
 *
 * @author Idevelop
 * @since 2026-03-01
 */
@RestController
@AllArgsConstructor
@RequestMapping("/importresult")
@Api(value = "信通一体化平台导入文件结果下载", tags = "信通一体化平台导入文件结果下载接口")
public class ImportResultController extends IdevelopController {

	private IImportResultService importResultService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入importResult")
	public R<ImportResult> detail(ImportResult importResult) {
		ImportResult detail = importResultService.getOne(Condition.getQueryWrapper(importResult));
		return R.data(detail);
	}

	/**
	 * 分页 信通一体化平台导入文件结果下载
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入importResult")
	public R<IPage<ImportResult>> list(ImportResult importResult, Query query) {
		IPage<ImportResult> pages = importResultService.page(Condition.getPage(query), Condition.getQueryWrapper(importResult));
		return R.data(pages);
	}

	/**
	 * 自定义分页 信通一体化平台导入文件结果下载
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入importResult")
	public R<IPage<ImportResultVO>> page(ImportResultVO importResult, Query query) {
		IPage<ImportResultVO> pages = importResultService.selectImportResultPage(Condition.getPage(query), importResult);
		return R.data(pages);
	}

	/**
	 * 新增 信通一体化平台导入文件结果下载
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入importResult")
	public R save(@Valid @RequestBody ImportResult importResult) {
		return R.status(importResultService.save(importResult));
	}

	/**
	 * 修改 信通一体化平台导入文件结果下载
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入importResult")
	public R update(@Valid @RequestBody ImportResult importResult) {
		return R.status(importResultService.updateById(importResult));
	}

	/**
	 * 新增或修改 信通一体化平台导入文件结果下载
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入importResult")
	public R submit(@Valid @RequestBody ImportResult importResult) {
		return R.status(importResultService.saveOrUpdate(importResult));
	}

	
	/**
	 * 删除 信通一体化平台导入文件结果下载
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(importResultService.deleteLogic(Func.toLongList(ids)));
	}

	
}
