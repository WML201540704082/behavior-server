package com.lnsoft.device.api.warehouse.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.annotation.ApiLog;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.device.api.warehouse.entity.DeviceApplyDetail;
import com.lnsoft.device.api.warehouse.service.IDeviceApplyDetailService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备申请单设备详情 控制器
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/apply/detail")
// @Api(value = "设备申请单设备详情", tags = "设备申请单设备详情接口")
public class DeviceApplyDetailController extends IdevelopController {

	private IDeviceApplyDetailService deviceApplyDetailService;

	/**
	 * 分页 设备申请单设备详情
	 */
	@ApiLog("设备申请详情-分页")
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入deviceApplyDetail")
	public R<IPage<DeviceApplyDetail>> list(DeviceApplyDetail deviceApplyDetail, Query query) {
		deviceApplyDetail.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
		IPage<DeviceApplyDetail> pages = deviceApplyDetailService.page(Condition.getPage(query), Condition.getQueryWrapper(deviceApplyDetail));
		return R.data(pages);
	}
}
