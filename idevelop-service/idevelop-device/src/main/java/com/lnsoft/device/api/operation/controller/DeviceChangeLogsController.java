package com.lnsoft.device.api.operation.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.annotation.ApiLog;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.operation.entity.DeviceChangeLogs;
import com.lnsoft.device.api.operation.service.IDeviceChangeLogsService;
import com.lnsoft.device.api.operation.vo.DeviceChangeLogsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 设备变更 控制器
 *
 * @author Idevelop
 * @since 2024-03-07
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/change/log")
@Api(value = "设备变更", tags = "设备变更接口")
public class DeviceChangeLogsController extends IdevelopController {

	private IDeviceChangeLogsService deviceChangeLogsService;

	/**
	 * 详情
	 */
	@ApiLog("设备变更详情日志-详情")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入deviceChangeLogs")
	public R<DeviceChangeLogs> detail(DeviceChangeLogs deviceChangeLogs) {
		DeviceChangeLogs detail = deviceChangeLogsService.getOne(Condition.getQueryWrapper(deviceChangeLogs));
		return R.data(detail);
	}

//	/**
//	 * 分页 设备变更
//	 */
//	@GetMapping("/list")
//	@ApiOperationSupport(order = 2)
//	@ApiOperation(value = "分页", notes = "传入deviceChangeLogs")
//	public R<IPage<DeviceChangeLogs>> list(DeviceChangeLogs deviceChangeLogs, Query query) {
//		IPage<DeviceChangeLogs> pages = deviceChangeLogsService.page(Condition.getPage(query), Condition.getQueryWrapper(deviceChangeLogs));
//		return R.data(pages);
//	}

	/**
	 * 自定义分页 设备变更
	 */
	@ApiLog("设备变更详情日志-自定义分页")
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入deviceChangeLogs")
	public R<IPage<DeviceChangeLogsVO>> page(DeviceChangeLogsVO deviceChangeLogs, Query query) {
		IPage<DeviceChangeLogsVO> pages = deviceChangeLogsService.selectDeviceChangeLogsPage(Condition.getPage(query), deviceChangeLogs);
		return R.data(pages);
	}

	/**
	 * 新增 设备变更
	 */
	@ApiLog("设备变更详情日志-新增")
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入deviceChangeLogs")
	public R save(@Valid @RequestBody DeviceChangeLogs deviceChangeLogs) {
		return R.status(deviceChangeLogsService.save(deviceChangeLogs));
	}

	/**
	 * 修改 设备变更
	 */
	@ApiLog("设备变更详情日志-修改")
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入deviceChangeLogs")
	public R update(@Valid @RequestBody DeviceChangeLogs deviceChangeLogs) {
		return R.status(deviceChangeLogsService.updateById(deviceChangeLogs));
	}

	/**
	 * 新增或修改 设备变更
	 */
	@ApiLog("设备变更详情日志-新增或修改")
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入deviceChangeLogs")
	public R submit(@Valid @RequestBody DeviceChangeLogs deviceChangeLogs) {
		return R.status(deviceChangeLogsService.saveOrUpdate(deviceChangeLogs));
	}


	/**
	 * 删除 设备变更
	 */
	@ApiLog("设备变更详情日志-删除")
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(deviceChangeLogsService.deleteLogic(Func.toLongList(ids)));
	}
	/**
	 * 根据变更编码和设备编码查询记录表
	 */
	@ApiLog("设备变更详情日志-根据变更编码和设备编码查询记录表")
	@GetMapping("/record")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "列表", notes = "传入deviceChangeLogs")
	public R<List<DeviceChangeLogs>> list(DeviceChangeLogs deviceChangeLogs) {
		return R.data(deviceChangeLogsService.getByChangeCode(deviceChangeLogs));
	}


}
