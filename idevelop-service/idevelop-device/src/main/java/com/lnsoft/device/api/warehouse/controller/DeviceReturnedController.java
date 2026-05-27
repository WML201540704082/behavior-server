package com.lnsoft.device.api.warehouse.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.annotation.ApiLog;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.dto.DeviceReturnedDTO;
import com.lnsoft.device.entity.DeviceReturned;
import com.lnsoft.device.api.warehouse.service.IDeviceReturnedService;
import com.lnsoft.device.vo.DeviceReturnedVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * 设备退运 控制器
 *
 * @author cwb
 * @since 2024-03-25
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/returned")
@Api(value = "设备退运", tags = "设备退运接口")
public class DeviceReturnedController extends IdevelopController {

	private IDeviceReturnedService deviceReturnedService;

	/**
	 * 详情
	 */
	@ApiLog("设备退运-详情")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入deviceReturned")
	public R<DeviceReturnedVO> detail(DeviceReturnedDTO dto) {
		if (Objects.isNull(dto.getId())) {
			return R.fail("请选择要查看的设备退运");
		}
		return deviceReturnedService.detail(dto);
	}

	/**
	 * 分页 设备退运
	 */
	@ApiLog("设备退运-分页")
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入deviceReturned")
	public R<IPage<DeviceReturned>> list(DeviceReturnedDTO dto, Query query) {
		IPage<DeviceReturned> pages = deviceReturnedService.returnedPage(dto, query);
		return R.data(pages);
	}

	/**
	 * 保存 设备退运
	 */
	@ApiLog("设备退运-保存")
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "保存", notes = "传入deviceReturned")
	public R<DeviceReturnedVO> save(@Valid @RequestBody DeviceReturnedDTO dto, BindingResult result) {
		if (result.hasErrors()) {
			return R.fail(result.getAllErrors().get(0).getDefaultMessage());
		}
		return deviceReturnedService.saveReturned(dto);
	}

	/**
	 * 删除 设备退运
	 */
	@ApiLog("设备退运-删除")
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return deviceReturnedService.removeReturned(ids);
	}

	/**
	 * 提交退运
	 */
	@ApiLog("设备退运-提交退运")
	@PostMapping("/submit")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "退运", notes = "传入deviceScrapDTO")
	public R<DeviceReturnedVO> submit(@Valid @RequestBody DeviceReturnedDTO dto, BindingResult result) throws Exception {
		if (result.hasErrors()) {
			return R.fail(result.getAllErrors().get(0).getDefaultMessage());
		}
		return deviceReturnedService.deviceReturnedSubmit(dto);
	}

	/**
	 * 工作台获取设备退运列表
	 */
	@ApiLog("设备退运-工作台获取设备退运列表")
	@GetMapping("/desk/list")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "工作台获取设备退运列表")
	public R<IPage<DeviceReturnedVO>> deskDeviceReturnedList(DeviceReturnedDTO dto, Query query) {
		return deviceReturnedService.deskDeviceReturnedList(dto, query);
	}

	/**
	 * 更新设备退运工单状态
	 */
	@ApiLog("设备退运-更新设备退运工单状态")
	@PostMapping("/edit/status")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "更新设备退运工单状态")
	public R<Integer> deskDeviceReturnedStatus(@RequestBody DeviceReturnedDTO dto) throws Exception {
		return deviceReturnedService.deskDeviceReturnedStatus(dto);
	}

	/**
	 * 审批前校验是否全部归还
	 */
	@ApiLog("设备退运-审批前校验是否全部归还")
	@PostMapping("/check")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "审批前校验是否全部归还")
	public R check(@RequestBody DeviceReturnedDTO dto) {
		return deviceReturnedService.checkIsAllReturn(dto);
	}
}
