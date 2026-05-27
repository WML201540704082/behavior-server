package com.lnsoft.device.api.asset.controller;

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
import com.lnsoft.device.api.asset.entity.DeviceInventoryLog;
import com.lnsoft.device.api.asset.vo.DeviceInventoryLogVO;
import com.lnsoft.device.api.asset.service.IDeviceInventoryLogService;
import com.lnsoft.core.boot.ctrl.IdevelopController;

/**
 * 设备库存日志表 控制器
 *
 * @author Idevelop
 * @since 2024-04-29
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/inventory/log")
@Api(value = "设备库存日志表", tags = "设备库存日志表接口")
public class DeviceInventoryLogController extends IdevelopController {

	private IDeviceInventoryLogService deviceInventoryLogService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入deviceInventoryLog")
	public R<DeviceInventoryLog> detail(DeviceInventoryLog deviceInventoryLog) {
		DeviceInventoryLog detail = deviceInventoryLogService.getOne(Condition.getQueryWrapper(deviceInventoryLog));
		return R.data(detail);
	}

	/**
	 * 分页 设备库存日志表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入deviceInventoryLog")
	public R<IPage<DeviceInventoryLog>> list(DeviceInventoryLog deviceInventoryLog, Query query) {
		IPage<DeviceInventoryLog> pages = deviceInventoryLogService.page(Condition.getPage(query), Condition.getQueryWrapper(deviceInventoryLog));
		return R.data(pages);
	}

	/**
	 * 自定义分页 设备库存日志表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入deviceInventoryLog")
	public R<IPage<DeviceInventoryLogVO>> page(DeviceInventoryLogVO deviceInventoryLog, Query query) {
		IPage<DeviceInventoryLogVO> pages = deviceInventoryLogService.selectDeviceInventoryLogPage(Condition.getPage(query), deviceInventoryLog);
		return R.data(pages);
	}

	/**
	 * 新增 设备库存日志表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入deviceInventoryLog")
	public R save(@Valid @RequestBody DeviceInventoryLog deviceInventoryLog) {
		return R.status(deviceInventoryLogService.save(deviceInventoryLog));
	}

	/**
	 * 修改 设备库存日志表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入deviceInventoryLog")
	public R update(@Valid @RequestBody DeviceInventoryLog deviceInventoryLog) {
		return R.status(deviceInventoryLogService.updateById(deviceInventoryLog));
	}

	/**
	 * 新增或修改 设备库存日志表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入deviceInventoryLog")
	public R submit(@Valid @RequestBody DeviceInventoryLog deviceInventoryLog) {
		return R.status(deviceInventoryLogService.saveOrUpdate(deviceInventoryLog));
	}


	/**
	 * 删除 设备库存日志表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(deviceInventoryLogService.deleteLogic(Func.toLongList(ids)));
	}


}
