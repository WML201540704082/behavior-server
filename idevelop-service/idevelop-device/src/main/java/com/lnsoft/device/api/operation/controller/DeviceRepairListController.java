package com.lnsoft.device.api.operation.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.annotation.ApiLog;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.operation.service.IDeviceRepairListService;
import com.lnsoft.device.entity.DeviceRepairList;
import com.lnsoft.device.vo.DeviceRepairListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 设备报修详情表 控制器
 *
 * @author Idevelop
 * @since 2024-03-19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/repair/sub")
@Api(value = "设备报修详情表", tags = "设备报修详情表接口")
public class DeviceRepairListController extends IdevelopController {

	private IDeviceRepairListService deviceRepairListService;

	/**
	 * 详情
	 */
	@ApiLog("设备报修详情-详情")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入deviceRepairList")
	public R<DeviceRepairList> detail(DeviceRepairList deviceRepairList) {
		DeviceRepairList detail = deviceRepairListService.getOne(Condition.getQueryWrapper(deviceRepairList));
		return R.data(detail);
	}

	/**
	 * 分页 设备报修详情表
	 */
	@ApiLog("设备报修详情-分页")
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入deviceRepairList")
	public R<IPage<DeviceRepairList>> list(DeviceRepairList deviceRepairList, Query query) {
		IPage<DeviceRepairList> pages = deviceRepairListService.selectByRepairId(deviceRepairList,query);
		return R.data(pages);
	}

	/**
	 * 自定义分页 设备报修详情表
	 */
	@ApiLog("设备报修详情-自定义分页")
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入deviceRepairList")
	public R<IPage<DeviceRepairListVO>> page(DeviceRepairListVO deviceRepairList, Query query) {
		IPage<DeviceRepairListVO> pages = deviceRepairListService.selectDeviceRepairListPage(Condition.getPage(query), deviceRepairList);
		return R.data(pages);
	}

	/**
	 * 新增 设备报修详情表
	 */
	@ApiLog("设备报修详情-新增")
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入deviceRepairList")
	public R save(@Valid @RequestBody DeviceRepairList deviceRepairList) {
		return R.status(deviceRepairListService.save(deviceRepairList));
	}

	/**
	 * 修改 设备报修详情表
	 */
	@ApiLog("设备报修详情-修改")
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入deviceRepairList")
	public R update(@Valid @RequestBody DeviceRepairList deviceRepairList) {
		return R.status(deviceRepairListService.updateById(deviceRepairList));
	}

	/**
	 * 新增或修改 设备报修详情表
	 */
	@ApiLog("设备报修详情-新增或修改")
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入deviceRepairList")
	public R submit(@Valid @RequestBody DeviceRepairList deviceRepairList) {
		return R.status(deviceRepairListService.saveOrUpdate(deviceRepairList));
	}


	/**
	 * 删除 设备报修详情表
	 */
	@ApiLog("设备报修详情-删除")
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(deviceRepairListService.deleteLogic(Func.toLongList(ids)));
	}


}
