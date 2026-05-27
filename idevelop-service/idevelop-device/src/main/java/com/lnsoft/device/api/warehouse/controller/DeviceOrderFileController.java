package com.lnsoft.device.api.warehouse.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.annotation.ApiLog;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.entity.DeviceOrderFile;
import com.lnsoft.device.api.warehouse.service.IDeviceOrderFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 设备工单附件 控制器
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/order/file")
@Api(value = "设备工单附件", tags = "设备工单附件接口")
public class DeviceOrderFileController extends IdevelopController {

	private IDeviceOrderFileService deviceOrderFileService;

	/**
	 * 详情
	 */
	@ApiLog("设备工单附件-详情")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入deviceOrderFile")
	public R<DeviceOrderFile> detail(DeviceOrderFile deviceOrderFile) {
		DeviceOrderFile detail = deviceOrderFileService.getOne(Condition.getQueryWrapper(deviceOrderFile));
		return R.data(detail);
	}

	/**
	 * 分页 设备工单附件
	 */
	@ApiLog("设备工单附件-分页")
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入deviceOrderFile")
	public R<IPage<DeviceOrderFile>> list(DeviceOrderFile deviceOrderFile, Query query) {
		IPage<DeviceOrderFile> pages = deviceOrderFileService.page(Condition.getPage(query), Condition.getQueryWrapper(deviceOrderFile));
		return R.data(pages);
	}

	/**
	 * 新增 设备工单附件
	 */
	@ApiLog("设备工单附件-新增")
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入deviceOrderFile")
	public R save(@Valid @RequestBody DeviceOrderFile deviceOrderFile) {
		return R.status(deviceOrderFileService.save(deviceOrderFile));
	}

	/**
	 * 修改 设备工单附件
	 */
	@ApiLog("设备工单附件-修改")
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入deviceOrderFile")
	public R update(@Valid @RequestBody DeviceOrderFile deviceOrderFile) {
		return R.status(deviceOrderFileService.updateById(deviceOrderFile));
	}

	/**
	 * 新增或修改 设备工单附件
	 */
	@ApiLog("设备工单附件-新增或修改")
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入deviceOrderFile")
	public R submit(@Valid @RequestBody DeviceOrderFile deviceOrderFile) {
		return R.status(deviceOrderFileService.saveOrUpdate(deviceOrderFile));
	}


	/**
	 * 删除 设备工单附件
	 */
	@ApiLog("设备工单附件-删除")
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(deviceOrderFileService.deleteLogic(Func.toLongList(ids)));
	}


}
