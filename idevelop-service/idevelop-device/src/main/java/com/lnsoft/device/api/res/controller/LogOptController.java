package com.lnsoft.device.api.res.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.res.service.ILogOptService;
import com.lnsoft.device.api.res.vo.LogOptVO;
import com.lnsoft.device.entity.LogOpt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 设备操作-日志表 控制器
 *
 * @author Idevelop
 * @since 2024-02-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/log/opt")
@Api(value = "设备操作-日志表", tags = "设备操作-日志表接口")
public class LogOptController extends IdevelopController {

	private ILogOptService logOptService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入logOpt")
	public R<LogOpt> detail(LogOpt logOpt) {
		LogOpt detail = logOptService.getOne(Condition.getQueryWrapper(logOpt));
		return R.data(detail);
	}

	/**
	 * 分页 设备操作-日志表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入logOpt")
	public R<IPage<LogOpt>> list(LogOpt logOpt, Query query) {
		query.setAscs("create_time");
		IPage<LogOpt> pages = logOptService.page(Condition.getPage(query), Condition.getQueryWrapper(logOpt));
		return R.data(pages);
	}

	/**
	 * 自定义分页 设备操作-日志表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "自定义分页", notes = "传入logOpt")
	public R<IPage<LogOptVO>> page(LogOptVO logOpt, Query query) {
		IPage<LogOptVO> pages = logOptService.selectLogOptPage(Condition.getPage(query), logOpt);
		return R.data(pages);
	}

	/**
	 * 新增 设备操作-日志表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入logOpt")
	public R save(@Valid @RequestBody LogOpt logOpt) {
		return R.status(logOptService.save(logOpt));
	}

	/**
	 * 修改 设备操作-日志表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入logOpt")
	public R update(@Valid @RequestBody LogOpt logOpt) {
		return R.status(logOptService.updateById(logOpt));
	}

	/**
	 * 新增或修改 设备操作-日志表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入logOpt")
	public R submit(@Valid @RequestBody LogOpt logOpt) {
		return R.status(logOptService.saveOrUpdate(logOpt));
	}


	/**
	 * 删除 设备操作-日志表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(logOptService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 新增 设备操作-日志
	 */
	@GetMapping("/add")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "新增", notes = "logOpt")
	public R add(@RequestParam("role") String role, @RequestParam("title") String title, @RequestParam("logId") String logId) {
		return R.status(logOptService.logOptAdd(role, title, logId));
	}


}
