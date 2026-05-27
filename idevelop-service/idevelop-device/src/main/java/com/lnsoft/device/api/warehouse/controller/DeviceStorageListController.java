package com.lnsoft.device.api.warehouse.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.annotation.ApiLog;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.warehouse.dto.DeviceStorageExportDTO;
import com.lnsoft.device.api.warehouse.entity.DeviceStorageList;
import com.lnsoft.device.api.warehouse.service.IDeviceStorageListService;
import com.lnsoft.device.api.warehouse.vo.DeviceStorageListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 设备入库明细表 控制器
 *
 * @author Idevelop
 * @since 2024-02-22
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/storage/info")
@Api(value = "设备入库明细表", tags = "设备入库明细表接口")
public class DeviceStorageListController extends IdevelopController {

	private IDeviceStorageListService deviceStorageListService;

	/**
	 * 详情
	 */
	@ApiLog("设备入库明细-详情")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入deviceStorageList")
	public R<DeviceStorageList> detail(DeviceStorageList deviceStorageList) {
		DeviceStorageList detail = deviceStorageListService.getOne(Condition.getQueryWrapper(deviceStorageList));
		return R.data(detail);
	}

	/**
	 * 分页 设备入库明细表
	 */
	@ApiLog("设备入库明细-分页")
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入deviceStorageList")
	public R<IPage<DeviceStorageList>> list(DeviceStorageList deviceStorageList, Query query) {
		IPage<DeviceStorageList> pages = deviceStorageListService.page(Condition.getPage(query), Condition.getQueryWrapper(deviceStorageList));
		return R.data(pages);
	}

	/**
	 * 自定义分页 设备入库明细表
	 */
	@ApiLog("设备入库明细-自定义分页")
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入deviceStorageList")
	@ApiIgnore
	public R<IPage<DeviceStorageListVO>> page(DeviceStorageListVO deviceStorageList, Query query) {
		IPage<DeviceStorageListVO> pages = deviceStorageListService.selectDeviceStorageListPage(Condition.getPage(query), deviceStorageList);
		return R.data(pages);
	}

	/**
	 * 新增 设备入库明细表
	 */
	@ApiLog("设备入库明细-新增")
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入deviceStorageList")
	public R save(@Valid @RequestBody DeviceStorageList deviceStorageList) {
		return R.status(deviceStorageListService.save(deviceStorageList));
	}

	/**
	 * 修改 设备入库明细表
	 */
	@ApiLog("设备入库明细-修改")
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入deviceStorageList")
	public R update(@Valid @RequestBody DeviceStorageList deviceStorageList) {
		return R.status(deviceStorageListService.updateById(deviceStorageList));
	}

	/**
	 * 新增或修改 设备入库明细表
	 */
	@ApiLog("设备入库明细-新增或修改")
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入deviceStorageList")
	@ApiIgnore
	public R submit(@Valid @RequestBody DeviceStorageList deviceStorageList) {
		return R.status(deviceStorageListService.saveOrUpdate(deviceStorageList));
	}


	/**
	 * 删除 设备入库明细表
	 */
	@ApiLog("设备入库明细-删除")
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(deviceStorageListService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * excel导入并回显数据
	 */
	@ApiLog("设备入库明细-excel导入并回显数据")
	@PostMapping("/import")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "excel导入并回显数据", notes = "传入excel")
	public R importByExcel(MultipartFile file,
						   @ApiParam(value = "设备分类编码", required = true) String deviceCategory,
						   @ApiParam(value = "设备类型编码", required = true) String deviceType,
						   @ApiParam(value = "设备来源编码", required = true) String deviceSource) {
		String filename = file.getOriginalFilename();
		if (StringUtils.isEmpty(filename)) {
			throw new ServiceException("请上传文件!");
		}
		if ((!StringUtils.endsWithIgnoreCase(filename, ".xls") && !StringUtils.endsWithIgnoreCase(filename, ".xlsx"))) {
			throw new ServiceException("请上传正确的excel文件!");
		}
		if (file.getSize() > 1024 * 1024 * 100) {
			return R.fail("文件大小超过限制，最大允许" + 1024 * 1024 * 100 + "MB");
		}
		return R.data(deviceStorageListService.importByExcel(file, deviceCategory, deviceType, deviceSource));
	}

	/**
	 * 批量保存
	 */
	@ApiLog("设备入库明细-批量保存")
	@PostMapping("/batchSave")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "批量保存", notes = "传入deviceStorageList")
	public R batchSave(@Valid @RequestBody List<DeviceStorageList> deviceStorageList) {
		return R.status(deviceStorageListService.customSaveBatch(deviceStorageList));
	}

	/**
	 * 根据主表id获取设备列表
	 */
	@ApiLog("设备入库明细-根据主表id获取设备列表")
	@GetMapping("/findByStorageId")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "根据主表id获取设备列表", notes = "传入storageId")
	public R<List<DeviceStorageList>> findByStorageId(@ApiParam(value = "主表id", required = true) @RequestParam String storageId) {
		DeviceStorageList entry = new DeviceStorageList();
		entry.setStorageId(storageId);
		return R.data(deviceStorageListService.list(new QueryWrapper(entry)));
	}

	/**
	 * excel导出问题
	 */
	@ApiLog("设备入库明细-excel导出问题")
	@PostMapping("/export")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "excel导出问题", notes = "传入 导出问题数据")
	public void exportByExcel(@RequestBody DeviceStorageExportDTO deviceStorageExportDTO, HttpServletResponse response) {
		deviceStorageListService.exportByExcel(deviceStorageExportDTO, response);
	}
}
