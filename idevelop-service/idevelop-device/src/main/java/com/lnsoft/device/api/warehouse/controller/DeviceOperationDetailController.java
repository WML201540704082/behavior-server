package com.lnsoft.device.api.warehouse.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.annotation.ApiLog;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.warehouse.entity.DeviceOperationDetail;
import com.lnsoft.device.api.warehouse.service.IDeviceOperationDetailService;
import com.lnsoft.device.api.warehouse.vo.DeviceOperationDetailVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 设备投运单设备详情 控制器
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/operation/detail")
// @Api(value = "设备投运单设备详情", tags = "设备投运单设备详情接口")
public class DeviceOperationDetailController extends IdevelopController {

	private IDeviceOperationDetailService deviceOperationDetailService;

	/**
	 * 详情
	 */
	@ApiLog("设备投运详情-详情")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入deviceOperationDetail")
	public R<DeviceOperationDetail> detail(DeviceOperationDetail deviceOperationDetail) {
		DeviceOperationDetail detail = deviceOperationDetailService.getOne(Condition.getQueryWrapper(deviceOperationDetail));
		return R.data(detail);
	}

	/**
	 * 分页 设备投运单设备详情
	 */
	@ApiLog("设备投运详情-分页")
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入deviceOperationDetail")
	public R<IPage<DeviceOperationDetail>> list(DeviceOperationDetail deviceOperationDetail, Query query) {
		IPage<DeviceOperationDetail> pages = deviceOperationDetailService.page(Condition.getPage(query), Condition.getQueryWrapper(deviceOperationDetail));
		return R.data(pages);
	}

	/**
	 * 自定义分页 设备投运单设备详情
	 */
	@ApiLog("设备投运详情-自定义分页")
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入deviceOperationDetail")
	public R<IPage<DeviceOperationDetailVO>> page(DeviceOperationDetailVO deviceOperationDetail, Query query) {
		IPage<DeviceOperationDetailVO> pages = deviceOperationDetailService.selectDeviceOperationDetailPage(Condition.getPage(query), deviceOperationDetail);
		return R.data(pages);
	}

	/**
	 * 新增 设备投运单设备详情
	 */
	@ApiLog("设备投运详情-新增")
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入deviceOperationDetail")
	public R save(@Valid @RequestBody DeviceOperationDetail deviceOperationDetail) {
		return R.status(deviceOperationDetailService.save(deviceOperationDetail));
	}

	/**
	 * 修改 设备投运单设备详情
	 */
	@ApiLog("设备投运详情-修改")
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入deviceOperationDetail")
	public R update(@Valid @RequestBody DeviceOperationDetail deviceOperationDetail) {
		return R.status(deviceOperationDetailService.updateById(deviceOperationDetail));
	}

	/**
	 * 新增或修改 设备投运单设备详情
	 */
	@ApiLog("设备投运详情-新增或修改")
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入deviceOperationDetail")
	public R submit(@Valid @RequestBody DeviceOperationDetail deviceOperationDetail) {
		return R.status(deviceOperationDetailService.saveOrUpdate(deviceOperationDetail));
	}


	/**
	 * 删除 设备投运单设备详情
	 */
	@ApiLog("设备投运详情-删除")
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(deviceOperationDetailService.deleteLogic(Func.toLongList(ids)));
	}


}
