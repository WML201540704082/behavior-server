package com.lnsoft.device.api.warehouse.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.annotation.ApiLog;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.desk.vo.DictValueVO;
import com.lnsoft.device.api.warehouse.dto.DeviceApplyDTO;
import com.lnsoft.device.api.warehouse.entity.DeviceApply;
import com.lnsoft.device.api.warehouse.service.IDeviceApplyService;
import com.lnsoft.device.api.warehouse.vo.DeviceApplyVO;
import com.lnsoft.device.api.warehouse.vo.DeviceOperationDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 设备申请表 控制器
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/apply")
@Api(value = "设备申请表", tags = "设备申请表接口")
public class DeviceApplyController extends IdevelopController {

	private IDeviceApplyService deviceApplyService;

	/**
	 * 详情
	 */
	@ApiLog("设备申请列表-详情")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入deviceApply")
	public R<DeviceApplyVO> detail(DeviceApply deviceApply) {
		return deviceApplyService.deviceApply(deviceApply);
	}

	/**
	 * 分页 设备申请表
	 */
	@ApiLog("设备申请列表-分页")
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入deviceApply")
	public R<IPage<DeviceApplyVO>> list(DeviceApplyDTO deviceApplyDTO, Query query) {
		return deviceApplyService.selectDeviceApplyList(deviceApplyDTO, query);
	}

	/**
	 * 新增/暂存设备申请表
	 */
	@ApiLog("设备申请列表-新增/暂存")
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增/暂存", notes = "传入deviceApplyDTO")
	public R<Integer> save(@RequestBody DeviceApplyDTO deviceApplyDTO) {
		return deviceApplyService.insertDeviceApply(deviceApplyDTO);
	}

	/**
	 * 设备申请
	 */
	@ApiLog("设备申请列表-提交")
	@PostMapping("/submit")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "设备申请", notes = "传入deviceApplyDTO")
	public R<DeviceApplyVO> submit(@Valid @RequestBody DeviceApplyDTO deviceApplyDTO, BindingResult result) throws Exception {
		if (result.hasErrors()) {
			return R.fail(result.getAllErrors().get(0).getDefaultMessage());
		}
		return deviceApplyService.submitDeviceApply(deviceApplyDTO);
	}

	/**
	 * 删除 设备申请表
	 */
	@ApiLog("设备申请列表-删除")
	@PostMapping("/remove")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R<Integer> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return deviceApplyService.removeDeviceApply(ids);
	}

	/**
	 * 个人工作台查询申请单工单
	 */
	@ApiLog("设备申请列表-个人工作台查询申请单工单")
	@GetMapping("/desk/list")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "个人工作台查询申请单工单", notes = "传入 deviceApplyDTO 的orderNoList")
	public R<IPage<DeviceApplyVO>> deskList(DeviceApplyDTO deviceApplyDTO, Query query) {
		return deviceApplyService.deskList(deviceApplyDTO, query);
	}

	/**
	 * 个人工作台审核更新工单状态，增加日志记录
	 */
	@ApiLog("设备申请列表-个人工作台审核更新工单状态，增加日志记录")
	@PostMapping("/desk/edit")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "个人工作台审核更新工单状态", notes = "传入 deviceApplyDTO")
	public R<Integer> deskUpdateStatus(@RequestBody DeviceApplyDTO deviceApplyDTO) throws Exception {
		return deviceApplyService.deskUpdateStatus(deviceApplyDTO);
	}

	/**
	 * 获取设备申请工单状态字典
	 */
	@ApiLog("设备申请列表-获取设备申请工单状态字典")
	@GetMapping("/dict")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "获取设备申请工单状态字典")
	public R<List<DictValueVO>> deviceApplyDict() {
		return R.data(deviceApplyService.deviceApplyDict());
	}

	/**
	 * 根据设备id查询旧设备的使用类型
	 */
	@ApiLog("设备申请列表-根据设备id查询旧设备的使用类型")
	@GetMapping("/user/type")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "根据设备id查询旧设备的使用类型")
	public R<DeviceOperationDetailVO> queryUserType(@ApiParam(value = "查询设备", required = true) String deviceId) {
		return R.data(deviceApplyService.queryUserType(deviceId));
	}
}
