package com.lnsoft.device.api.warehouse.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.annotation.ApiLog;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.warehouse.entity.DeviceOutboundDetail;
import com.lnsoft.device.api.warehouse.service.IDeviceOutboundDetailService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 设备出库单设备详情 控制器
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/outbound/detail")
// @Api(value = "设备出库单设备详情", tags = "设备出库单设备详情接口")
public class DeviceOutboundDetailController extends IdevelopController {

	private IDeviceOutboundDetailService deviceOutboundDetailService;

	/**
	 * 详情
	 */
	@ApiLog("设备出库单设备详情-详情")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入deviceOutboundDetail")
	public R<DeviceOutboundDetail> detail(DeviceOutboundDetail deviceOutboundDetail) {
		DeviceOutboundDetail detail = deviceOutboundDetailService.getOne(Condition.getQueryWrapper(deviceOutboundDetail));
		return R.data(detail);
	}

	/**
	 * 分页 设备出库单设备详情
	 */
	@ApiLog("设备出库单设备详情-分页")
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入deviceOutboundDetail")
	public R<IPage<DeviceOutboundDetail>> list(DeviceOutboundDetail deviceOutboundDetail, Query query) {
		IPage<DeviceOutboundDetail> pages = deviceOutboundDetailService.page(Condition.getPage(query), Condition.getQueryWrapper(deviceOutboundDetail));
		return R.data(pages);
	}

	/**
	 * 新增 设备出库单设备详情
	 */
	@ApiLog("设备出库单设备详情-新增")
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入deviceOutboundDetail")
	public R save(@Valid @RequestBody DeviceOutboundDetail deviceOutboundDetail) {
		return R.status(deviceOutboundDetailService.save(deviceOutboundDetail));
	}

	/**
	 * 修改 设备出库单设备详情
	 */
	@ApiLog("设备出库单设备详情-修改")
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入deviceOutboundDetail")
	public R update(@Valid @RequestBody DeviceOutboundDetail deviceOutboundDetail) {
		return R.status(deviceOutboundDetailService.updateById(deviceOutboundDetail));
	}

	/**
	 * 新增或修改 设备出库单设备详情
	 */
	@ApiLog("设备出库单设备详情-新增或修改")
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入deviceOutboundDetail")
	public R submit(@Valid @RequestBody DeviceOutboundDetail deviceOutboundDetail) {
		return R.status(deviceOutboundDetailService.saveOrUpdate(deviceOutboundDetail));
	}


	/**
	 * 删除 设备出库单设备详情
	 */
	@ApiLog("设备出库单设备详情-删除")
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(deviceOutboundDetailService.deleteLogic(Func.toLongList(ids)));
	}


}
