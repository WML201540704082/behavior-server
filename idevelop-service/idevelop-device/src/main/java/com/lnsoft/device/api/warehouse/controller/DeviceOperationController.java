package com.lnsoft.device.api.warehouse.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.annotation.ApiLog;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.desk.vo.DictValueVO;
import com.lnsoft.device.api.warehouse.dto.DeviceOperationDTO;
import com.lnsoft.device.api.warehouse.service.IDeviceOperationService;
import com.lnsoft.device.api.warehouse.vo.DeviceOperationDetailVO;
import com.lnsoft.device.api.warehouse.vo.DeviceOperationVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 设备投运表 控制器
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/operation")
@Api(value = "设备投运表", tags = "设备投运表接口")
public class DeviceOperationController extends IdevelopController {

	private IDeviceOperationService deviceOperationService;

	/**
	 * 详情
	 */
	@ApiLog("设备投运-详情")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入deviceOperation")
	public R<DeviceOperationVO> detail(DeviceOperationDTO deviceOperationDTO) {
		return deviceOperationService.detailOperation(deviceOperationDTO);
	}

	/**
	 * 分页 设备投运表
	 */
	@ApiLog("设备投运-分页")
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入deviceOperationDTO")
	public R<IPage<DeviceOperationVO>> list(DeviceOperationDTO deviceOperationDTO, Query query) {
		return deviceOperationService.selectOperationList(Condition.getPage(query), deviceOperationDTO);
	}

	/**
	 * 新增/修改设备投运
	 */
	@ApiLog("设备投运-新增/修改设备投运")
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增/修改", notes = "传入deviceOperation")
	public R<DeviceOperationVO> insertOperation(@RequestBody DeviceOperationDTO deviceOperationDTO) {
		return deviceOperationService.insertOperation(deviceOperationDTO);
	}

	/**
	 * 提交设备投运
	 */
	@ApiLog("设备投运-提交设备投运")
	@PostMapping("/submit")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "提交设备投运", notes = "传入deviceOperation")
	public R<DeviceOperationVO> submitOperation(@Valid @RequestBody DeviceOperationDTO deviceOperationDTO, BindingResult result) throws Exception {
		if (result.hasErrors()) {
			return R.fail(result.getAllErrors().get(0).getDefaultMessage());
		}
		return deviceOperationService.submitOperation(deviceOperationDTO);
	}

	/**
	 * 删除设备投运
	 */
	@ApiLog("设备投运-删除设备投运")
	@PostMapping("/remove")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "删除设备投运", notes = "传入ids")
	public R<Integer> removeOperation(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return deviceOperationService.removeOperation(ids);
	}

	/**
	 * 获取设备投运工单状态字典
	 */
	@ApiLog("设备投运-获取设备投运工单状态字典")
	@GetMapping("/dict")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "获取设备投运工单状态字典")
	public R<List<DictValueVO>> deviceOperationDict() {
		return deviceOperationService.deviceOperationDict();
	}

	/**
	 * 个人工作台查询投运工单
	 */
	@ApiLog("设备投运-个人工作台查询投运工单")
	@GetMapping("/desk/list")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "个人工作台查询投运工单", notes = "传入 deviceOperationDTO 的orderNoList")
	public R<IPage<DeviceOperationVO>> deskList(DeviceOperationDTO deviceOperationDTO, Query query) {
		return deviceOperationService.deskList(deviceOperationDTO, query);
	}

	/**
	 * 个人工作台审核更新工单
	 */
	@ApiLog("设备投运-个人工作台审核更新工单")
	@PostMapping("/desk/edit")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "个人工作台审核更新工单", notes = "传入 deviceOperationDTO")
	public R<List<DeviceOperationDetailVO>> deskUpdateStatus(@RequestBody DeviceOperationDTO deviceOperationDTO) throws Exception {
		return deviceOperationService.deskUpdateStatus(deviceOperationDTO);
	}

	/**
	 * 工单审核之前校验设备信息
	 */
	@ApiLog("设备投运-工单审核之前校验设备信息")
	@PostMapping("/device/check")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "工单审核之前校验设备信息", notes = "传入 deviceOperationDTO")
	public R<Integer> checkDeviceOperation(@RequestBody DeviceOperationDTO deviceOperationDTO) {
		return deviceOperationService.checkDeviceOperation(deviceOperationDTO);
	}

	/**
	 * 生成标准全称
	 *
	 * @param deviceType 设备类型
	 * @return R
	 */
	@ApiLog("设备投运-生成标准全称")
	@GetMapping("/full/name")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "生成标准全称", notes = "传入设备类型 deviceType")
	public R<String> createFullName(@ApiParam(value = "设备类型") @RequestParam String deviceType) {
		return deviceOperationService.createFullName(deviceType);
	}
}
