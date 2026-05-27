package com.lnsoft.device.api.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.common.entity.ImportLog;
import com.lnsoft.device.api.common.service.IImportLogService;
import com.lnsoft.device.api.common.vo.ImportLogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 信通一体化平台导入文件结果下载日志 控制器
 *
 * @author Idevelop
 * @since 2026-03-01
 */
@RestController
@AllArgsConstructor
@RequestMapping("/importlog")
@Api(value = "信通一体化平台导入文件结果下载日志", tags = "信通一体化平台导入文件结果下载日志接口")
public class ImportLogController extends IdevelopController {

	private IImportLogService importLogService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入importLog")
	public R<ImportLog> detail(ImportLog importLog) {
		ImportLog detail = importLogService.getOne(Condition.getQueryWrapper(importLog));
		return R.data(detail);
	}

	/**
	 * 分页 信通一体化平台导入文件结果下载日志
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入importLog")
	public R<IPage<ImportLog>> list(ImportLog importLog, Query query) {
		IPage<ImportLog> pages = importLogService.page(Condition.getPage(query), Condition.getQueryWrapper(importLog));
		return R.data(pages);
	}

	/**
	 * 自定义分页 信通一体化平台导入文件结果下载日志
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入importLog")
	public R<IPage<ImportLogVO>> page(ImportLogVO importLog, Query query) {
		IPage<ImportLogVO> pages = importLogService.selectImportLogPage(Condition.getPage(query), importLog);
		return R.data(pages);
	}

	/**
	 * 新增 信通一体化平台导入文件结果下载日志
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入importLog")
	public R save(@Valid @RequestBody ImportLog importLog) {
		return R.status(importLogService.save(importLog));
	}

	/**
	 * 修改 信通一体化平台导入文件结果下载日志
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入importLog")
	public R update(@Valid @RequestBody ImportLog importLog) {
		return R.status(importLogService.updateById(importLog));
	}

	/**
	 * 新增或修改 信通一体化平台导入文件结果下载日志
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入importLog")
	public R submit(@Valid @RequestBody ImportLog importLog) {
		return R.status(importLogService.saveOrUpdate(importLog));
	}


	/**
	 * 删除 信通一体化平台导入文件结果下载日志
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(importLogService.deleteLogic(Func.toLongList(ids)));
	}


	/**
	 * excel导出问题
	 */
	@PostMapping("/export")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "excel导出问题", notes = "传入 导出问题数据")
	public void exportByExcel(@RequestBody ImportLog importLog, HttpServletResponse response) {
		importLogService.exportByExcel(importLog, response);
	}

}
