package com.lnsoft.device.api.warehouse.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.annotation.ApiLog;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.entity.DeviceRecordList;
import com.lnsoft.device.api.warehouse.service.IDeviceRecordListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/**
 * 设备建档-设备列表 控制器
 *
 * @author Idevelop
 * @since 2024-02-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/record/device")
@Api(value = "设备建档-设备列表", tags = "设备建档-设备列表接口")
public class DeviceRecordListController extends IdevelopController {

	private IDeviceRecordListService deviceRecordListService;

	/**
	 * 详情
	 */
	@ApiLog("设备建档详情-提交建档")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入deviceRecordList")
	public R<DeviceRecordList> detail(DeviceRecordList deviceRecordList) {
		DeviceRecordList detail = deviceRecordListService.getOne(Condition.getQueryWrapper(deviceRecordList));
		return R.data(detail);
	}

	/**
	 * 分页 设备建档-设备列表
	 */
	@ApiLog("设备建档详情-分页")
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入deviceRecordList")
	public R<IPage<DeviceRecordList>> list(DeviceRecordList deviceRecordList, Query query) {
		deviceRecordList.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
		query.setAscs("create_time");
		IPage<DeviceRecordList> pages = deviceRecordListService.page(Condition.getPage(query), Condition.getQueryWrapper(deviceRecordList));
		return R.data(pages);
	}

	/**
	 * 新增 设备建档-设备列表
	 */
	@ApiLog("设备建档详情-新增")
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入deviceRecordList")
	public R save(@Valid @RequestBody DeviceRecordList deviceRecordList) {
		if(StringUtil.isBlank(deviceRecordList.getDeviceUuid())){
			deviceRecordList.setDeviceUuid(UUID.randomUUID().toString().replace("-",""));
		}
		return R.status(deviceRecordListService.save(deviceRecordList));
	}

	/**
	 * 修改 设备建档-设备列表
	 */
	@ApiLog("设备建档详情-修改")
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入deviceRecordList")
	public R update(@Valid @RequestBody DeviceRecordList deviceRecordList) {
		if(StringUtil.isBlank(deviceRecordList.getDeviceUuid())){
			deviceRecordList.setDeviceUuid(UUID.randomUUID().toString().replace("-",""));
		}
		return R.status(deviceRecordListService.updateById(deviceRecordList));
	}

	/**
	 * 新增或修改 设备建档-设备列表
	 */
	@ApiLog("设备建档详情-新增或修改")
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入deviceRecordList")
	public R submit(@Valid @RequestBody DeviceRecordList deviceRecordList) {
		if(StringUtil.isBlank(deviceRecordList.getDeviceUuid())){
			deviceRecordList.setDeviceUuid(UUID.randomUUID().toString().replace("-",""));
		}
		return R.status(deviceRecordListService.saveOrUpdate(deviceRecordList));
	}


	/**
	 * 删除 设备建档-设备列表
	 */
	@ApiLog("设备建档详情-删除")
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R<Integer> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return deviceRecordListService.removeDeviceDetail(ids);
	}

}
