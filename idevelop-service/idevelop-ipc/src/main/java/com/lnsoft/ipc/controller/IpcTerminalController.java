package com.lnsoft.ipc.controller;

import com.lnsoft.ipc.dto.IpcTerminalDTO;
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
import com.lnsoft.ipc.entity.IpcTerminal;
import com.lnsoft.ipc.vo.IpcTerminalVO;
import com.lnsoft.ipc.service.IIpcTerminalService;
import com.lnsoft.core.boot.ctrl.IdevelopController;

import java.util.List;

/**
 * 工控机管控--终端管理表 控制器
 *
 * @author Idevelop
 * @since 2025-11-17
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ipcterminal")
@Api(value = "工控机管控--终端管理表", tags = "工控机管控--终端管理表接口")
public class IpcTerminalController extends IdevelopController {

	private IIpcTerminalService ipcTerminalService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入ipcTerminal")
	public R<IpcTerminal> detail(IpcTerminal ipcTerminal) {
		IpcTerminal detail = ipcTerminalService.getOne(Condition.getQueryWrapper(ipcTerminal));
		return R.data(detail);
	}

	/**
	 * 分页 工控机管控--终端管理表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入ipcTerminal")
	public R<IPage<IpcTerminal>> list(IpcTerminal ipcTerminal, Query query) {
		IPage<IpcTerminal> pages = ipcTerminalService.page(Condition.getPage(query), Condition.getQueryWrapper(ipcTerminal));
		return R.data(pages);
	}


	/**
	 * 新增 工控机管控--终端管理表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入ipcTerminal")
	public R save(@Valid @RequestBody IpcTerminal ipcTerminal) {
		return R.status(ipcTerminalService.save(ipcTerminal));
	}

	/**
	 * 修改 工控机管控--终端管理表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入ipcTerminal")
	public R update(@Valid @RequestBody IpcTerminal ipcTerminal) {
		return R.status(ipcTerminalService.updateById(ipcTerminal));
	}



	/**
	 * 删除 工控机管控--终端管理表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove( @RequestBody IpcTerminal ipcTerminal) {
		return R.status(ipcTerminalService.removeById(ipcTerminal.getId()));
	}
	/**
	 * 终端使用时长查询
	 */
	@GetMapping("/terminalRank")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "终端使用时长查询", notes = "")
	public R<List<IpcTerminalVO>> terminalRank(IpcTerminalDTO ipcTerminalDTO) {
		return R.data(ipcTerminalService.terminalRank(ipcTerminalDTO));
	}

}
