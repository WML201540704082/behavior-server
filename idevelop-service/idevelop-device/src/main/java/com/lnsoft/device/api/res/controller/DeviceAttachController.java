package com.lnsoft.device.api.res.controller;

import com.lnsoft.core.tool.constant.IdevelopConstant;
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
import com.lnsoft.device.api.res.entity.DeviceAttach;
import com.lnsoft.device.api.res.vo.DeviceAttachVO;
import com.lnsoft.device.api.res.service.IDeviceAttachService;
import com.lnsoft.core.boot.ctrl.IdevelopController;

/**
 * 设备-附件表 控制器
 *
 * @author Idevelop
 * @since 2024-02-23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/attach")
@Api(value = "设备-附件表", tags = "设备-附件表接口")
public class DeviceAttachController extends IdevelopController {

	private IDeviceAttachService deviceAttachService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入deviceAttach")
	public R<DeviceAttach> detail(DeviceAttach deviceAttach) {
		DeviceAttach detail = deviceAttachService.getOne(Condition.getQueryWrapper(deviceAttach));
		return R.data(detail);
	}

	/**
	 * 分页 设备-附件表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入deviceAttach")
	public R<IPage<DeviceAttach>> list(DeviceAttach deviceAttach, Query query) {
		deviceAttach.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
		IPage<DeviceAttach> pages = deviceAttachService.page(Condition.getPage(query), Condition.getQueryWrapper(deviceAttach));
		return R.data(pages);
	}

	/**
	 * 自定义分页 设备-附件表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入deviceAttach")
	public R<IPage<DeviceAttachVO>> page(DeviceAttachVO deviceAttach, Query query) {
		IPage<DeviceAttachVO> pages = deviceAttachService.selectDeviceAttachPage(Condition.getPage(query), deviceAttach);
		return R.data(pages);
	}

	/**
	 * 新增 设备-附件表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入deviceAttach")
	public R save(@Valid @RequestBody DeviceAttach deviceAttach) {
		deviceAttach.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
		if (deviceAttachService.save(deviceAttach)) {
			return R.data(deviceAttach);
		}
		return R.fail("新增失败");
	}

	/**
	 * 修改 设备-附件表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入deviceAttach")
	public R update(@Valid @RequestBody DeviceAttach deviceAttach) {
		return R.status(deviceAttachService.updateById(deviceAttach));
	}

	/**
	 * 新增或修改 设备-附件表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入deviceAttach")
	public R submit(@Valid @RequestBody DeviceAttach deviceAttach) {
		return R.status(deviceAttachService.saveOrUpdate(deviceAttach));
	}


	/**
	 * 删除 设备-附件表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(deviceAttachService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 根据入库单号和附件id获取附件信息
	 */
	@GetMapping("/getAttach")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "获取附件信息", notes = "传入入库单号：storageId")
	public R getAttach(@ApiParam(value = "入库单号", required = true) String storageId) {
		return R.data(deviceAttachService.getAttach(storageId));
	}

}
