package com.lnsoft.device.api.warehouse.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.annotation.ApiLog;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.warehouse.dto.DeviceTransferExportDTO;
import com.lnsoft.device.api.warehouse.entity.DeviceTransferDetail;
import com.lnsoft.device.api.warehouse.service.IDeviceTransferDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 设备转资明细表 控制器
 *
 * @author Idevelop
 * @since 2024-02-27
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/transfer/detail")
@Api(value = "设备转资明细表", tags = "设备转资明细表接口")
public class DeviceTransferDetailController extends IdevelopController {

	private IDeviceTransferDetailService deviceTransferDetailService;

	/**
	 * 详情
	 */
	@ApiLog("设备转资明细表-详情")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入deviceTransferDetail")
	public R<DeviceTransferDetail> detail(DeviceTransferDetail deviceTransferDetail) {
		DeviceTransferDetail detail = deviceTransferDetailService.getOne(Condition.getQueryWrapper(deviceTransferDetail));
		return R.data(detail);
	}

	/**
	 * 分页 设备转资明细表
	 */
	@ApiLog("设备转资明细表-分页")
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入deviceTransferDetail")
	public R<IPage<DeviceTransferDetail>> list(DeviceTransferDetail deviceTransferDetail, Query query) {
		deviceTransferDetail.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
		IPage<DeviceTransferDetail> pages = deviceTransferDetailService.page(Condition.getPage(query), Condition.getQueryWrapper(deviceTransferDetail));
		return R.data(pages);
	}

	/**
	 * 新增 设备转资明细表
	 */
	@ApiLog("设备转资明细表-新增")
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入deviceTransferDetail")
	public R save(@Valid @RequestBody DeviceTransferDetail deviceTransferDetail) {
		return R.status(deviceTransferDetailService.save(deviceTransferDetail));
	}

	/**
	 * 新增或修改 设备转资明细表
	 */
	@ApiLog("设备转资明细表-新增或修改")
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入deviceTransferDetail")
	public R submit(@Valid @RequestBody DeviceTransferDetail deviceTransferDetail) {
		return R.status(deviceTransferDetailService.saveOrUpdate(deviceTransferDetail));
	}


	/**
	 * 删除 设备转资明细表
	 */
	@ApiLog("设备转资明细表-删除")
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(deviceTransferDetailService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 导出设备列表
	 *
	 * @param deviceTransferDetailDTOList 导出设备列表数据
	 */
	@ApiLog("设备转资明细表-导出设备列表")
	@PostMapping("/export")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "导出设备列表", notes = "传入deviceTransferDetailDTOList")
	public void exportDeviceTransferDetail(@RequestBody List<DeviceTransferExportDTO> deviceTransferDetailDTOList, HttpServletResponse response) throws IOException {
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		String fileName = URLEncoder.encode("设备列表导出", StandardCharsets.UTF_8.name());
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
		EasyExcel.write(response.getOutputStream(), DeviceTransferExportDTO.class).sheet("设备列表").doWrite(deviceTransferDetailDTOList);
	}
}
