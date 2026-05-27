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
import com.lnsoft.device.api.asset.entity.DeviceOldFile;
import com.lnsoft.device.api.asset.vo.DeviceOldFileVO;
import com.lnsoft.device.api.asset.service.IDeviceOldFileService;
import com.lnsoft.core.boot.ctrl.IdevelopController;

/**
 * 设备工单附件 控制器
 *
 * @author Idevelop
 * @since 2024-06-25
 */
@RestController
@AllArgsConstructor
@RequestMapping("/deviceoldfile")
@Api(value = "设备工单附件", tags = "设备工单附件接口")
public class DeviceOldFileController extends IdevelopController {

	private IDeviceOldFileService deviceOldFileService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入deviceOldFile")
	public R<DeviceOldFile> detail(DeviceOldFile deviceOldFile) {
		DeviceOldFile detail = deviceOldFileService.getOne(Condition.getQueryWrapper(deviceOldFile));
		return R.data(detail);
	}

	/**
	 * 分页 设备工单附件
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入deviceOldFile")
	public R<IPage<DeviceOldFile>> list(DeviceOldFile deviceOldFile, Query query) {
		IPage<DeviceOldFile> pages = deviceOldFileService.page(Condition.getPage(query), Condition.getQueryWrapper(deviceOldFile));
		return R.data(pages);
	}

	/**
	 * 自定义分页 设备工单附件
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入deviceOldFile")
	public R<IPage<DeviceOldFileVO>> page(DeviceOldFileVO deviceOldFile, Query query) {
		IPage<DeviceOldFileVO> pages = deviceOldFileService.selectDeviceOldFilePage(Condition.getPage(query), deviceOldFile);
		return R.data(pages);
	}

	/**
	 * 新增 设备工单附件
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入deviceOldFile")
	public R save(@Valid @RequestBody DeviceOldFile deviceOldFile) {
		return R.status(deviceOldFileService.save(deviceOldFile));
	}

	/**
	 * 修改 设备工单附件
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入deviceOldFile")
	public R update(@Valid @RequestBody DeviceOldFile deviceOldFile) {
		return R.status(deviceOldFileService.updateById(deviceOldFile));
	}

	/**
	 * 新增或修改 设备工单附件
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入deviceOldFile")
	public R submit(@Valid @RequestBody DeviceOldFile deviceOldFile) {
		return R.status(deviceOldFileService.saveOrUpdate(deviceOldFile));
	}

	
	/**
	 * 删除 设备工单附件
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(deviceOldFileService.deleteLogic(Func.toLongList(ids)));
	}

	
}
