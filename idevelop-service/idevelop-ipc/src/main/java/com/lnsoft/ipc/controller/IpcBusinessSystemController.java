package com.lnsoft.ipc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.ipc.entity.IpcBusinessSystem;
import com.lnsoft.ipc.service.IIpcBusinessSystemService;
import com.lnsoft.core.boot.ctrl.IdevelopController;

/**
 * 工控机管控--业务系统维护表 控制器
 *
 * @author Idevelop
 * @since 2025-11-17
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ipcbusinesssystem")
@Api(value = "工控机管控--业务系统维护表", tags = "工控机管控--业务系统维护表接口")
public class IpcBusinessSystemController extends IdevelopController {

	private IIpcBusinessSystemService ipcBusinessSystemService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入ipcBusinessSystem")
	public R<IpcBusinessSystem> detail(IpcBusinessSystem ipcBusinessSystem) {
		IpcBusinessSystem detail = ipcBusinessSystemService.getOne(Condition.getQueryWrapper(ipcBusinessSystem));
		return R.data(detail);
	}

	/**
	 * 分页 工控机管控--业务系统维护表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入ipcBusinessSystem")
	public R<IPage<IpcBusinessSystem>> list(IpcBusinessSystem ipcBusinessSystem, Query query) {
		IPage<IpcBusinessSystem> pages = ipcBusinessSystemService.page(Condition.getPage(query), Condition.getQueryWrapper(ipcBusinessSystem));
		return R.data(pages);
	}


	/**
	 * 新增 工控机管控--业务系统维护表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入ipcBusinessSystem")
	public R save(@Valid @RequestBody IpcBusinessSystem ipcBusinessSystem) {
		return R.status(ipcBusinessSystemService.save(ipcBusinessSystem));
	}

	/**
	 * 修改 工控机管控--业务系统维护表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入ipcBusinessSystem")
	public R update(@Valid @RequestBody IpcBusinessSystem ipcBusinessSystem) {
		return R.status(ipcBusinessSystemService.updateById(ipcBusinessSystem));
	}



	/**
	 * 删除 工控机管控--业务系统维护表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入id")
	public R remove(@RequestBody IpcBusinessSystem ipcBusinessSystem) {
		return R.status(ipcBusinessSystemService.removeById(ipcBusinessSystem.getId()));
	}


}
