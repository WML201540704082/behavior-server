package com.lnsoft.device.api.warehouse.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.annotation.ApiLog;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.secure.annotation.PreAuth;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.device.api.warehouse.dto.DeviceRecordDTO;
import com.lnsoft.device.api.warehouse.dto.ErpDeviceOrderDTO;
import com.lnsoft.device.api.warehouse.entity.DeviceRecord;
import com.lnsoft.device.api.warehouse.service.IDeviceRecordService;
import com.lnsoft.device.api.warehouse.vo.DeviceRecordVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 设备建档 控制器
 *
 * @author Idevelop
 * @since 2024-02-07
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/record")
@Api(value = "设备建档", tags = "设备建档接口")
public class DeviceRecordController extends IdevelopController {

	private IDeviceRecordService deviceRecordService;

	/**
	 * 详情
	 */
	@ApiLog("设备建档-详情")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@PreAuth("hasPerm('deivce:deviceRecord:view')")
	@ApiOperation(value = "详情", notes = "传入deviceRecord")
	public R<DeviceRecord> detail(DeviceRecord deviceRecord) {
		deviceRecord.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
		DeviceRecord detail = deviceRecordService.getOne(Condition.getQueryWrapper(deviceRecord));
		return R.data(detail);
	}

	/**
	 * 分页 设备建档
	 */
	@ApiLog("设备建档-分页")
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@PreAuth("hasPerm('deivce:deviceRecord:list')")
	@ApiOperation(value = "分页", notes = "传入deviceRecord")
	public R<IPage<DeviceRecord>> list(DeviceRecordDTO deviceRecordDTO, Query query) {
		return deviceRecordService.deviceRecordList(deviceRecordDTO, query);
	}

	/**
	 * 新增或修改 设备建档
	 */
	@ApiLog("设备建档-新增或修改")
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@PreAuth("hasPerm('deivce:deviceRecord:add')")
	@ApiOperation(value = "新增或修改暂存", notes = "传入deviceRecordDTO")
	public R<DeviceRecordVO> save(@RequestBody DeviceRecordDTO deviceRecordDTO) {
		return deviceRecordService.deviceRecordSaveOrUpdate(deviceRecordDTO);
	}

	/**
	 * 提交建档
	 */
	@ApiLog("设备建档-提交建档")
	@PostMapping("/submit")
	@ApiOperationSupport(order = 4)
	@PreAuth("hasPerm('deivce:deviceRecord:add')")
	@ApiOperation(value = "建档", notes = "传入deviceRecordDTO")
	public R<DeviceRecordVO> submit(@Valid @RequestBody DeviceRecordDTO deviceRecordDTO, BindingResult result) throws Exception {
		if (result.hasErrors()) {
			return R.fail(result.getAllErrors().get(0).getDefaultMessage());
		}
		return deviceRecordService.deviceRecordSubmit(deviceRecordDTO);
	}

	/**
	 * 删除 设备建档
	 */
	@ApiLog("设备建档-逻辑删除")
	@PostMapping("/remove")
	@ApiOperationSupport(order = 5)
	@PreAuth("hasPerm('deivce:deviceRecord:delete')")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R<Integer> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return deviceRecordService.removeDeviceRecord(ids);
	}

	/**
	 * 获取 新增设备建档时填充数据
	 */
	@ApiLog("设备建档-新增设备建档时填充数据")
	@GetMapping("/get")
	@ApiOperationSupport(order = 6)
	@PreAuth("hasPerm('deivce:deviceRecord:view')")
	@ApiOperation(value = "获取")
	public R deviceRecordGet() {
		return R.data(deviceRecordService.deviceRecordGet());
	}

	/**
	 * 工作台获取设备建档列表
	 */
	@ApiLog("设备建档-工作台获取设备建档列表")
	@GetMapping("/desk/list")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "工作台获取设备建档列表")
	public R<IPage<DeviceRecordVO>> deskDeviceRecodeList(DeviceRecordDTO deviceRecordDTO, Query query) {
		return deviceRecordService.deskDeviceRecodeList(deviceRecordDTO, query);
	}

	/**
	 * 更新设备建档工单状态
	 */
	@ApiLog("设备建档-更新设备建档工单状态")
	@PostMapping("/edit/status")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "更新设备建档工单状态")
	public R<Integer> deskDeviceRecodeStatus(@RequestBody DeviceRecordDTO deviceRecordDTO) throws Exception {
		return deviceRecordService.deskDeviceRecodeStatus(deviceRecordDTO);
	}

	/**
	 * ERP回调接口
	 */
	@ApiLog("设备建档-ERP回调接口更新设备建档工单状态")
	@PostMapping("/erp/record")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "ERP回调接口")
	public R<Integer> erpDeviceRecord(@RequestBody ErpDeviceOrderDTO erpDeviceOrderDTO) throws Exception {
		return deviceRecordService.erpDeviceRecord(erpDeviceOrderDTO);
	}

}
