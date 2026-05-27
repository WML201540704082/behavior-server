package com.lnsoft.device.api.stock.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.stock.entity.I6000ImportLog;
import com.lnsoft.device.api.stock.service.II6000ImportLogService;
import com.lnsoft.device.api.stock.vo.I6000ImportLogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 管理员批量导入I6000数据记录表 控制器
 *
 * @author Idevelop
 * @since 2025-11-02
 */
@RestController
@AllArgsConstructor
@RequestMapping("/i6000importlog")
@Api(value = "管理员批量导入I6000数据记录表", tags = "管理员批量导入I6000数据记录表接口")
public class I6000ImportLogController extends IdevelopController {

	private II6000ImportLogService i6000ImportLogService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入i6000ImportLog")
	public R<I6000ImportLog> detail(I6000ImportLog i6000ImportLog) {
		I6000ImportLog detail = i6000ImportLogService.getOne(Condition.getQueryWrapper(i6000ImportLog));
		return R.data(detail);
	}

	/**
	 * 分页 管理员批量导入I6000数据记录表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入i6000ImportLog")
	public R<IPage<I6000ImportLog>> list(I6000ImportLog i6000ImportLog, Query query) {
		IPage<I6000ImportLog> pages = i6000ImportLogService.page(Condition.getPage(query), Condition.getQueryWrapper(i6000ImportLog));
		return R.data(pages);
	}

	/**
	 * 自定义分页 管理员批量导入I6000数据记录表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入i6000ImportLog")
	public R<IPage<I6000ImportLogVO>> page(I6000ImportLogVO i6000ImportLog, Query query) {
		IPage<I6000ImportLogVO> pages = i6000ImportLogService.selectI6000ImportLogPage(Condition.getPage(query), i6000ImportLog);
		return R.data(pages);
	}

	/**
	 * 新增 管理员批量导入I6000数据记录表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入i6000ImportLog")
	public R save(@Valid @RequestBody I6000ImportLog i6000ImportLog) {
		return R.status(i6000ImportLogService.save(i6000ImportLog));
	}

	/**
	 * 修改 管理员批量导入I6000数据记录表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入i6000ImportLog")
	public R update(@Valid @RequestBody I6000ImportLog i6000ImportLog) {
		return R.status(i6000ImportLogService.updateById(i6000ImportLog));
	}

	/**
	 * 新增或修改 管理员批量导入I6000数据记录表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入i6000ImportLog")
	public R submit(@Valid @RequestBody I6000ImportLog i6000ImportLog) {
		return R.status(i6000ImportLogService.saveOrUpdate(i6000ImportLog));
	}


	/**
	 * 删除 管理员批量导入I6000数据记录表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(i6000ImportLogService.deleteLogic(Func.toLongList(ids)));
	}


	/**
	 * excel导出问题
	 */
	@PostMapping("/export")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "excel导出问题", notes = "传入 导出问题数据")
	public void exportByExcel(@RequestBody I6000ImportLog i6000ImportLog, HttpServletResponse response) {
		i6000ImportLogService.exportByExcel(i6000ImportLog, response);
	}


}
