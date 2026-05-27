package com.lnsoft.device.api.warehouse.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.annotation.ApiLog;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.warehouse.service.IDeviceReturnedDetailService;
import com.lnsoft.device.entity.DeviceReturnedDetail;
import com.lnsoft.device.vo.DeviceReturnedDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 设备退运详情 控制器
 *
 * @author Idevelop
 * @since 2024-03-25
 */
@RestController
@AllArgsConstructor
@RequestMapping("/devicereturneddetail")
@Api(value = "设备退运详情", tags = "设备退运详情接口")
public class DeviceReturnedDetailController extends IdevelopController {

	private IDeviceReturnedDetailService deviceReturnedDetailService;

	/**
	 * 详情
	 */
	@ApiLog("设备退运详情-详情")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入deviceReturnedDetail")
	public R<DeviceReturnedDetail> detail(DeviceReturnedDetail deviceReturnedDetail) {
		DeviceReturnedDetail detail = deviceReturnedDetailService.getOne(Condition.getQueryWrapper(deviceReturnedDetail));
		return R.data(detail);
	}

	/**
	 * 分页 设备退运详情
	 */
	@ApiLog("设备退运详情-分页")
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入deviceReturnedDetail")
	public R<IPage<DeviceReturnedDetail>> list(DeviceReturnedDetail deviceReturnedDetail, Query query) {
		deviceReturnedDetail.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
		IPage<DeviceReturnedDetail> pages = deviceReturnedDetailService.page(Condition.getPage(query), Condition.getQueryWrapper(deviceReturnedDetail));
		return R.data(pages);
	}

	/**
	 * 自定义分页 设备退运详情
	 */
	@ApiLog("设备退运详情-自定义分页")
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入deviceReturnedDetail")
	public R<IPage<DeviceReturnedDetailVO>> page(DeviceReturnedDetailVO deviceReturnedDetail, Query query) {
		IPage<DeviceReturnedDetailVO> pages = deviceReturnedDetailService.selectDeviceReturnedDetailPage(Condition.getPage(query), deviceReturnedDetail);
		return R.data(pages);
	}

	/**
	 * 新增 设备退运详情
	 */
	@ApiLog("设备退运详情-新增")
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入deviceReturnedDetail")
	public R save(@Valid @RequestBody DeviceReturnedDetail deviceReturnedDetail) {
		return R.status(deviceReturnedDetailService.save(deviceReturnedDetail));
	}

	/**
	 * 修改 设备退运详情
	 */
	@ApiLog("设备退运详情-修改")
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入deviceReturnedDetail")
	public R update(@Valid @RequestBody DeviceReturnedDetail deviceReturnedDetail) {
		return R.status(deviceReturnedDetailService.updateById(deviceReturnedDetail));
	}

	/**
	 * 新增或修改 设备退运详情
	 */
	@ApiLog("设备退运详情-新增或修改")
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入deviceReturnedDetail")
	public R submit(@Valid @RequestBody DeviceReturnedDetail deviceReturnedDetail) {
		return R.status(deviceReturnedDetailService.saveOrUpdate(deviceReturnedDetail));
	}


	/**
	 * 删除 设备退运详情
	 */
	@ApiLog("设备退运详情-删除")
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(deviceReturnedDetailService.deleteLogic(Func.toLongList(ids)));
	}


}
