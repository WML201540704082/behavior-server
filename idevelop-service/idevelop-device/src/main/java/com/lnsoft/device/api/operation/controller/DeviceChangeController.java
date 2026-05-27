package com.lnsoft.device.api.operation.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.common.tool.CommonUtil;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.annotation.ApiLog;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.operation.dto.DeviceChangeDTO;
import com.lnsoft.device.api.operation.entity.DeviceChange;
import com.lnsoft.device.api.operation.entity.DeviceChangeList;
import com.lnsoft.device.api.operation.service.IDeviceChangeService;
import com.lnsoft.device.api.operation.vo.DeviceChangeVO;
import com.lnsoft.device.api.res.service.ILogOptService;
import com.lnsoft.device.api.warehouse.dto.OrderUpdateStatusDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 设备变更 控制器
 *
 * @author Idevelop
 * @since 2024-03-07
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/change")
@Api(value = "设备变更", tags = "设备变更接口")
public class DeviceChangeController extends IdevelopController {
	private ILogOptService logOptService;
	private IDeviceChangeService deviceChangeService;

	/**
	 * 详情
	 */
	@ApiLog("设备变更列表-详情")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入deviceChange")
	public R<DeviceChange> detail(DeviceChange deviceChange) {
		DeviceChange detail = deviceChangeService.getOne(Condition.getQueryWrapper(deviceChange));
		return R.data(detail);
	}

	/**
	 * 分页 设备变更
	 */
	@ApiLog("设备变更列表-分页")
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入deviceChange")
	public R<IPage<DeviceChange>> list(DeviceChangeDTO deviceChange, Query query) {
		return deviceChangeService.deviceChangeList(deviceChange,query);
	}

	/**
	 * 自定义分页 设备变更
	 */
	@ApiLog("设备变更列表-自定义分页")
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入deviceChange")
	public R<IPage<DeviceChangeVO>> page(DeviceChangeVO deviceChange, Query query) {
		IPage<DeviceChangeVO> pages = deviceChangeService.selectDeviceChangePage(Condition.getPage(query), deviceChange);
		return R.data(pages);
	}


	/**
	 * 修改 设备变更
	 */
	@ApiLog("设备变更列表-修改")
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入deviceChange")
	public R update(@Valid @RequestBody DeviceChange deviceChange) {
		deviceChange.setReceiverTime(LocalDateTime.now());
		return R.status(deviceChangeService.updateById(deviceChange));
	}

	/**
	 * 提交设备变更
	 */
	@ApiLog("设备变更列表-提交设备变更")
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "设备变更暂存/提交", notes = "传入deviceChangeDTO")
	public R save(@Valid @RequestBody DeviceChangeDTO deviceChangeDTO, BindingResult result) throws Exception{
		if (result.hasErrors()) {
			return R.fail(result.getAllErrors().get(0).getDefaultMessage());
		}
		return deviceChangeService.add(deviceChangeDTO);
	}
	/**
	 * 删除 设备变更
	 */
	@ApiLog("设备变更列表-删除")
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		return deviceChangeService.delete(ids);
	}
	/**
	 * 个人工作台审核更新工单状态，增加日志记录
	 */
	@ApiLog("设备变更列表-个人工作台审核更新工单状态")
	@PostMapping("/desk/edit")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "个人工作台审核更新工单状态", notes = "传入 orderUpdateStatusDTO")
	public R<Integer> deskUpdateStatus(@RequestBody OrderUpdateStatusDTO orderUpdateStatusDTO) {
		try {
			return deviceChangeService.deskUpdateStatus(orderUpdateStatusDTO);
		} catch (Exception e) {
			CommonUtil.StringWriter(e, "设备变更系统异常!");
			throw new RuntimeException(e);
		}
	}
	/**
	 * 同步erp接口
	 */
	@ApiLog("设备变更列表-同步erp接口")
	@PostMapping("/erp")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "同步erp接口", notes = "")
	public R erpSync(@RequestBody List<DeviceChangeList> deviceChangeLists) {
		return deviceChangeService.erpSync(deviceChangeLists);
	}
	/**
	 * 工作台获取工单列表
	 */
	@ApiLog("设备变更列表-工作台获取工单列表")
	@GetMapping("/desk/list")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "工作台获取设备变更列表")
	public R<IPage<DeviceChangeVO>> deskDeviceChangeList(DeviceChangeDTO deviceChange, Query query) {
		return deviceChangeService.deskDeviceChangeList(query, deviceChange);
	}
	/**
	 * 数据填充
	 */
	@ApiLog("设备变更列表-数据填充")
	@GetMapping("/load")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "数据填充", notes = "")
	public R load() {
		return deviceChangeService.load();
	}
	/**
	 * 校验
	 */
	@ApiLog("设备变更列表-校验")
	@PostMapping("/check")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "数据校验", notes = "")
	public R save(@RequestBody DeviceChangeDTO deviceChangeDTO){
		return deviceChangeService.check(deviceChangeDTO);
	}
}
