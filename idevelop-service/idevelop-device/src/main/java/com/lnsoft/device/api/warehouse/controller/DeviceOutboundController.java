package com.lnsoft.device.api.warehouse.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.annotation.ApiLog;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.desk.vo.DictValueVO;
import com.lnsoft.device.api.warehouse.dto.DeviceOutboundDTO;
import com.lnsoft.device.api.warehouse.service.IDeviceOutboundService;
import com.lnsoft.device.api.warehouse.vo.DeviceOutboundVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设备出库表 控制器
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/outbound")
@Api(value = "设备出库表", tags = "设备出库表接口")
public class DeviceOutboundController extends IdevelopController {

	private IDeviceOutboundService deviceOutboundService;

	/**
	 * 详情
	 */
	@ApiLog("设备出库-详情")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "deviceOutboundDTO")
	public R<DeviceOutboundVO> detail(DeviceOutboundDTO deviceOutboundDTO) {
		return deviceOutboundService.detail(deviceOutboundDTO);
	}

	/**
	 * 分页 设备出库表
	 */
	@ApiLog("设备出库-分页")
	@GetMapping("/list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "分页", notes = "传入deviceOutboundDTO")
	public R<IPage<DeviceOutboundVO>> deviceOutboundList(DeviceOutboundDTO deviceOutboundDTO, Query query) {
		return deviceOutboundService.deviceOutboundList(Condition.getPage(query), deviceOutboundDTO);
	}

	/**
	 * 个人工作台查询出库工单
	 */
	@ApiLog("设备出库-个人工作台查询出库工单")
	@GetMapping("/desk/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "个人工作台查询出库工单", notes = "传入 deviceOutboundDTO 的orderNoList")
	public R<IPage<DeviceOutboundVO>> deskList(DeviceOutboundDTO deviceOutboundDTO, Query query) {
		return deviceOutboundService.deskList(deviceOutboundDTO, query);
	}

	/**
	 * 个人工作台审核更新工单
	 */
	@ApiLog("设备出库-个人工作台审核更新工单")
	@PostMapping("/desk/edit")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "个人工作台审核更新工单", notes = "传入 deviceApplyDTO")
	public R<Integer> deskUpdateStatus(@RequestBody DeviceOutboundDTO deviceOutboundDTO) throws Exception {
		return deviceOutboundService.deskUpdateStatus(deviceOutboundDTO);
	}

	/**
	 * 获取设备出库工单状态字典
	 */
	@ApiLog("设备出库-获取设备出库工单状态字典")
	@GetMapping("/dict")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "获取设备出库工单状态字典")
	public R<List<DictValueVO>> deviceOutboundDict() {
		return R.data(deviceOutboundService.deviceOutboundDict());
	}

	/**
	 * 工单审核之前校验设备信息
	 */
	@ApiLog("设备出库-工单审核之前校验设备信息")
	@PostMapping("/device/check")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "工单审核之前校验设备信息", notes = "传入 deviceOutboundDTO")
	public R<Integer> checkDeviceOperation(@RequestBody DeviceOutboundDTO deviceOutboundDTO) {
		return deviceOutboundService.checkDeviceOperation(deviceOutboundDTO);
	}
}
