package com.lnsoft.device.api.asset.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;

import javax.validation.Valid;

import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.tool.api.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.lnsoft.device.entity.DeviceOperationAgeConfig;
import com.lnsoft.device.api.asset.service.IDeviceOperationAgeConfigService;
import com.lnsoft.core.boot.ctrl.IdevelopController;

import java.util.List;

/**
 * 设备年限配置管理表 控制器
 *
 * @author Idevelop
 * @since 2024-03-26
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/operation/age/config")
@Api(value = "设备年限配置管理", tags = "设备年限配置管理表接口")
public class DeviceOperationAgeConfigController extends IdevelopController {

	private IDeviceOperationAgeConfigService deviceOperationAgeConfigService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入deviceOperationAgeConfig")
	public R<DeviceOperationAgeConfig> detail(DeviceOperationAgeConfig deviceOperationAgeConfig) {
		DeviceOperationAgeConfig detail = deviceOperationAgeConfigService.getOne(Condition.getQueryWrapper(deviceOperationAgeConfig));
		return R.data(detail);
	}

	/**
	 * 根据设备分类查询
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "根据设备分类查询", notes = "传入设备类型编码")
	public R<List<DeviceOperationAgeConfig>> list(DeviceOperationAgeConfig deviceOperationAgeConfig) {
		List<DeviceOperationAgeConfig> list = deviceOperationAgeConfigService.customList(deviceOperationAgeConfig);
		return R.data(list);
	}

	/**
	 * 批量保存或更新
	 */
	@PostMapping("/batchSaveOrUpdate")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "批量保存或更新", notes = "传入list")
	public R batchSaveOrUpdate(@Valid @RequestBody List<DeviceOperationAgeConfig> configs) {
		return R.status(deviceOperationAgeConfigService.saveOrUpdateBatch(configs));
	}


	/**
	 * 删除 设备年限配置管理表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam List<Long> ids) {
		return R.status(deviceOperationAgeConfigService.deleteLogic(ids));
	}

	/**
	 * 初始化数据
	 */
	@GetMapping("/initializeData")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "初始化数据")
	public R initializeData() {
		return R.status(deviceOperationAgeConfigService.initializeData());
	}


	/**
	 * 根据设备类型返回年限
	 */
	@GetMapping("/getOneByDeviceType")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "根据设备类型返回年限")
	public R getOneByDeviceType(@ApiParam(value = "设备类型", required = true) @RequestParam String deviceType) {
		return R.data(deviceOperationAgeConfigService.getOneByDeviceType(deviceType));
	}

	/**
	 * 同步字典数据
	 */
	@GetMapping("/syncBDC")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "更新设备类型id")
	public R syncByDeviceCategory() {
		return R.data(deviceOperationAgeConfigService.syncByDeviceCategory());
	}


}
