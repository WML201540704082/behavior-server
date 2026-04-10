package com.lnsoft.ipc.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lnsoft.ipc.dto.IpcNetworkLogDTO;
import com.lnsoft.ipc.vo.RankVO;
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
import com.lnsoft.ipc.entity.IpcNetworkLog;
import com.lnsoft.ipc.vo.IpcNetworkLogVO;
import com.lnsoft.ipc.service.IIpcNetworkLogService;
import com.lnsoft.core.boot.ctrl.IdevelopController;

import java.util.List;

/**
 * 工控机管控-网络访问记录表 控制器
 *
 * @author Idevelop
 * @since 2025-11-17
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ipcnetworklog")
@Api(value = "工控机管控-网络访问记录表", tags = "工控机管控-网络访问记录表接口")
public class IpcNetworkLogController extends IdevelopController {

	private IIpcNetworkLogService ipcNetworkLogService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入ipcNetworkLog")
	public R<IpcNetworkLog> detail(IpcNetworkLog ipcNetworkLog) {
		IpcNetworkLog detail = ipcNetworkLogService.getOne(Condition.getQueryWrapper(ipcNetworkLog));
		return R.data(detail);
	}

	/**
	 * 分页 工控机管控-网络访问记录表 主数据源
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页（主数据源）", notes = "传入ipcNetworkLog")
	public R<IPage<IpcNetworkLog>> list(IpcNetworkLog ipcNetworkLog, Query query) {
		QueryWrapper<IpcNetworkLog> queryWrapper = Condition.getQueryWrapper(ipcNetworkLog);
		queryWrapper.orderByDesc("access_length");
		IPage<IpcNetworkLog> pages = ipcNetworkLogService.page(Condition.getPage(query), queryWrapper);
		return R.data(pages);
	}
	/**
	 * 分页 工控机管控-网络访问记录表
	 */
	@GetMapping("/list/slave")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页（辅数据源）", notes = "传入ipcNetworkLog")
	public R<IPage<IpcNetworkLog>> slaveList(IpcNetworkLog ipcNetworkLog, Query query) {
		IPage<IpcNetworkLog> pages = ipcNetworkLogService.slaveList(ipcNetworkLog,query);
		return R.data(pages);
	}
	/**
	 * 访问次数排名
	 */
	@GetMapping("/countRank")
	public R<List<RankVO>> countRank(IpcNetworkLogDTO ipcNetworkLogDTO) {
		List<RankVO> list = ipcNetworkLogService.countRank(ipcNetworkLogDTO);
		return R.data(list);
	}

	/**
	 * 新增 工控机管控-网络访问记录表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入ipcNetworkLog")
	public R save(@Valid @RequestBody IpcNetworkLog ipcNetworkLog) {
		return R.status(ipcNetworkLogService.save(ipcNetworkLog));
	}

	/**
	 * 修改 工控机管控-网络访问记录表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入ipcNetworkLog")
	public R update(@Valid @RequestBody IpcNetworkLog ipcNetworkLog) {
		return R.status(ipcNetworkLogService.updateById(ipcNetworkLog));
	}



	/**
	 * 删除 工控机管控-网络访问记录表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove( @RequestBody IpcNetworkLog ipcNetworkLog) {
		return R.status(ipcNetworkLogService.removeById(ipcNetworkLog.getId()));
	}


}
