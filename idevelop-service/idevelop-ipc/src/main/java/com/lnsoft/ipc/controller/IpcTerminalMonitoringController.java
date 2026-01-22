package com.lnsoft.ipc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.CollectionUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.ipc.dto.IpcTerminalMonitoringDTO;
import com.lnsoft.ipc.entity.IpcUser;
import com.lnsoft.ipc.service.IIpcUserService;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.ipc.entity.IpcTerminalMonitoring;
import com.lnsoft.ipc.vo.IpcTerminalMonitoringVO;
import com.lnsoft.ipc.service.IIpcTerminalMonitoringService;
import com.lnsoft.core.boot.ctrl.IdevelopController;

import java.util.List;

/**
 * 工控机管控--终端运行状态监控表 控制器
 *
 * @author Idevelop
 * @since 2025-11-17
 */
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/ipcterminalmonitoring")
@Api(value = "工控机管控--终端运行状态监控表", tags = "工控机管控--终端运行状态监控表接口")
public class IpcTerminalMonitoringController extends IdevelopController {

	private IIpcTerminalMonitoringService ipcTerminalMonitoringService;

	private IIpcUserService iIpcUserService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入ipcTerminalMonitoring")
	public R<IpcTerminalMonitoring> detail(IpcTerminalMonitoring ipcTerminalMonitoring) {
		IpcTerminalMonitoring detail = ipcTerminalMonitoringService.getOne(Condition.getQueryWrapper(ipcTerminalMonitoring));
		return R.data(detail);
	}

	/**
	 * 分页 工控机管控--终端运行状态监控表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入ipcTerminalMonitoring")
	public R<IPage<IpcTerminalMonitoring>> list(IpcTerminalMonitoring ipcTerminalMonitoring, Query query) {
		LambdaQueryWrapper<IpcTerminalMonitoring> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.like(StringUtils.isNotBlank(ipcTerminalMonitoring.getIp()),IpcTerminalMonitoring::getIp,ipcTerminalMonitoring.getIp())
			.like(StringUtils.isNotBlank(ipcTerminalMonitoring.getUserName()),IpcTerminalMonitoring::getUserName,ipcTerminalMonitoring.getUserName())
			.orderByDesc(IpcTerminalMonitoring::getOpenTime);
		IPage<IpcTerminalMonitoring> pages = ipcTerminalMonitoringService.page(Condition.getPage(query), queryWrapper);
		List<IpcTerminalMonitoring> records = pages.getRecords();
		for (IpcTerminalMonitoring record : records) {
			String ip = record.getIp();
			LambdaQueryWrapper<IpcUser> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(IpcUser::getUserType,"管理员").eq(IpcUser::getTerminal,ip).eq(IpcUser::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
			List<IpcUser> list = iIpcUserService.list(wrapper);
			if (CollectionUtil.isEmpty(list)){
				log.error("终端未创建管理员账号"+ip);
			}else if (list.size()>1){
				log.error("终端包含多个管理员账号");
			}else {
				record.setUserName(list.get(0).getName());
				record.setDept(list.get(0).getDepartment());
			}
		}
		return R.data(pages);
	}



	/**
	 * 新增 工控机管控--终端运行状态监控表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入ipcTerminalMonitoring")
	public R save(@Valid @RequestBody IpcTerminalMonitoring ipcTerminalMonitoring) {
		return R.status(ipcTerminalMonitoringService.save(ipcTerminalMonitoring));
	}

	/**
	 * 修改 工控机管控--终端运行状态监控表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入ipcTerminalMonitoring")
	public R update(@Valid @RequestBody IpcTerminalMonitoring ipcTerminalMonitoring) {
		return R.status(ipcTerminalMonitoringService.updateById(ipcTerminalMonitoring));
	}



	/**
	 * 删除 工控机管控--终端运行状态监控表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove( @RequestBody IpcTerminalMonitoring ipcTerminalMonitoring) {
		return R.status(ipcTerminalMonitoringService.removeById(ipcTerminalMonitoring.getId()));
	}

	/**
	 * 部门使用时长排名
	 * @param ipcTerminalMonitoringDTO
	 * @return
	 */
	@GetMapping("/deptRank")
	public R<List<RankVO>> deptRank(IpcTerminalMonitoringDTO ipcTerminalMonitoringDTO){
		return ipcTerminalMonitoringService.deptRank(ipcTerminalMonitoringDTO);
	}


}
