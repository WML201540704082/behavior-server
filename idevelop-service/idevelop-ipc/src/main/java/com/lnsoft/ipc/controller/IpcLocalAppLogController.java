package com.lnsoft.ipc.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.lnsoft.ipc.entity.IpcLocalAppLog;
import com.lnsoft.ipc.service.IIpcLocalAppLogService;
import com.lnsoft.core.boot.ctrl.IdevelopController;

import java.util.List;
import com.lnsoft.ipc.vo.RankVO;

/**
 * 工控机管控-本地应用访问记录表 控制器
 *
 * @author Idevelop
 * @since 2026-02-13
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ipclocalapplog")
@Api(value = "工控机管控-本地应用访问记录表", tags = "工控机管控-本地应用访问记录表接口")
public class IpcLocalAppLogController extends IdevelopController {

	private IIpcLocalAppLogService ipcLocalAppLogService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入ipcLocalAppLog")
	public R<IpcLocalAppLog> detail(IpcLocalAppLog ipcLocalAppLog) {
		IpcLocalAppLog detail = ipcLocalAppLogService.getOne(Condition.getQueryWrapper(ipcLocalAppLog));
		return R.data(detail);
	}

	/**
	 * 分页 工控机管控-本地应用访问记录表 主数据源
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页（主数据源）", notes = "传入ipcLocalAppLog")
	public R<IPage<IpcLocalAppLog>> list(IpcLocalAppLog ipcLocalAppLog, Query query) {
		QueryWrapper<IpcLocalAppLog> queryWrapper = Condition.getQueryWrapper(ipcLocalAppLog);
		queryWrapper.orderByDesc("access_length");
		IPage<IpcLocalAppLog> pages = ipcLocalAppLogService.page(Condition.getPage(query), queryWrapper);
		return R.data(pages);
	}
	/**
	 * 分页 工控机管控-本地应用访问记录表
	 */
	@GetMapping("/list/slave")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页（辅数据源）", notes = "传入ipcLocalAppLog")
	public R<IPage<IpcLocalAppLog>> slaveList(IpcLocalAppLog ipcLocalAppLog, Query query) {
		IPage<IpcLocalAppLog> pages = ipcLocalAppLogService.slaveList(ipcLocalAppLog,query);
		return R.data(pages);
	}
	/**
	 * 访问次数排名
	 */
	@GetMapping("/countRank")
	public R<List<RankVO>> countRank(IpcLocalAppLog ipcLocalAppLog) {
		List<RankVO> list = ipcLocalAppLogService.countRank(ipcLocalAppLog);
		return R.data(list);
	}

	/**
	 * 新增 工控机管控-本地应用访问记录表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入ipcLocalAppLog")
	public R save(@Valid @RequestBody IpcLocalAppLog ipcLocalAppLog) {
		return R.status(ipcLocalAppLogService.save(ipcLocalAppLog));
	}

	/**
	 * 修改 工控机管控-本地应用访问记录表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入ipcLocalAppLog")
	public R update(@Valid @RequestBody IpcLocalAppLog ipcLocalAppLog) {
		return R.status(ipcLocalAppLogService.updateById(ipcLocalAppLog));
	}

	/**
	 * 删除 工控机管控-本地应用访问记录表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove( @RequestBody IpcLocalAppLog ipcLocalAppLog) {
		return R.status(ipcLocalAppLogService.removeById(ipcLocalAppLog.getId()));
	}

}
