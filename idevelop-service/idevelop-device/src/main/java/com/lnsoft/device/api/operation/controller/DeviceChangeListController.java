package com.lnsoft.device.api.operation.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.annotation.ApiLog;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.operation.entity.DeviceChangeList;
import com.lnsoft.device.api.operation.entity.DeviceChangeLogs;
import com.lnsoft.device.api.operation.service.IDeviceChangeListService;
import com.lnsoft.device.api.operation.service.IDeviceChangeLogsService;
import com.lnsoft.device.api.operation.vo.DeviceChangeListVO;
import com.lnsoft.device.api.stock.dto.HardwareBasicCmdbQueryDTO;
import com.lnsoft.device.utils.ChangeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * 设备变更 控制器
 *
 * @author Idevelop
 * @since 2024-03-07
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/change/list")
@Api(value = "设备变更", tags = "设备变更接口")
public class DeviceChangeListController extends IdevelopController {

	private IDeviceChangeListService deviceChangeListService;
	private ICmdbService cmdbService;
	private IDeviceChangeLogsService deviceChangeLogsService;

	/**
	 * 详情
	 */
	@ApiLog("设备变更设备详情-详情")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入deviceChangeList")
	public R<DeviceChangeList> detail(DeviceChangeList deviceChangeList) {
		DeviceChangeList detail = deviceChangeListService.getOne(Condition.getQueryWrapper(deviceChangeList));
		return R.data(detail);
	}

	/**
	 * 分页 设备变更
	 */
	@ApiLog("设备变更设备详情-分页")
	@GetMapping("/device")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入deviceChangeList")
	public R<IPage<DeviceChangeList>> list(DeviceChangeList deviceChangeList, Query query) {
		IPage<DeviceChangeList> pages = deviceChangeListService.page(Condition.getPage(query), Condition.getQueryWrapper(deviceChangeList));
		return R.data(pages);
	}

	/**
	 * 自定义分页 设备变更
	 */
	@ApiLog("设备变更设备详情-自定义分页")
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入deviceChangeList")
	public R<IPage<DeviceChangeListVO>> page(DeviceChangeListVO deviceChangeList, Query query) {
		IPage<DeviceChangeListVO> pages = deviceChangeListService.selectDeviceChangeListPage(Condition.getPage(query), deviceChangeList);
		return R.data(pages);
	}

	/**
	 * 新增 设备变更
	 */
	@ApiLog("设备变更设备详情-新增")
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入deviceChangeList")
	public R save(@Valid @RequestBody List<DeviceChangeList> deviceChangeList) {
		if (deviceChangeList!=null && deviceChangeList.size()>0){
			for (DeviceChangeList device : deviceChangeList) {
				deviceChangeListService.save(device);
			}
		}
		return R.success("导入成功");
	}

	/**
	 * 修改 设备变更
	 */
	@ApiLog("设备变更设备详情-修改")
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入deviceChangeList")
	public R update(@Valid @RequestBody DeviceChangeList deviceChangeList) {
		//判断新旧数据并写入记录表
		String id = deviceChangeList.getId();
		DeviceChangeList oldData = deviceChangeListService.getById(id);
		HashMap<String, List<Object>> map = ChangeUtils.getChangedFields(oldData, deviceChangeList);
		for (String key : map.keySet()) {
			DeviceChangeLogs logs = new DeviceChangeLogs();
			List<Object> values = map.get(key);
			logs.setAttributeName(key);
			logs.setChangeBefore(values.get(0).toString());
			logs.setChangeAfter(values.get(1).toString());
			logs.setChangeId(deviceChangeList.getChangeId());
			logs.setDeviceCode(deviceChangeList.getDeviceCode());
			deviceChangeLogsService.saveOrUpdate(logs);
		}
		return R.status(deviceChangeListService.updateById(deviceChangeList));
	}

	/**
	 * 新增或修改 设备变更
	 */
	@ApiLog("设备变更设备详情-新增或修改")
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入deviceChangeList")
	public R submit(@Valid @RequestBody DeviceChangeList deviceChangeList) {
		return R.status(deviceChangeListService.saveOrUpdate(deviceChangeList));
	}


	/**
	 * 删除 设备变更
	 */
	@ApiLog("设备变更设备详情-删除")
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(deviceChangeListService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 设备列表导出接口
	 */
	@ApiLog("设备变更设备详情-设备列表导出接口")
	@PostMapping("/export")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "设备列表导出接口", notes = "传入id的集合")
	public void export(@RequestBody HardwareBasicCmdbQueryDTO hardwareBasicCmdbQuery, HttpServletResponse response) {
		deviceChangeListService.export(hardwareBasicCmdbQuery, response);
	}

	/**
	 * excel导入并回显数据
	 */
	@ApiLog("设备变更设备详情-excel导入并回显数据")
	@PostMapping("/import")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "excel导入并回显数据", notes = "传入excel")
	public R importByExcel(MultipartFile file) {
		String filename = file.getOriginalFilename();
		if (StringUtils.isEmpty(filename)) {
			throw new ServiceException("请上传文件!");
		}
		if ((!StringUtils.endsWithIgnoreCase(filename, ".xls") && !StringUtils.endsWithIgnoreCase(filename, ".xlsx")) && !StringUtils.endsWithIgnoreCase(filename, ".xlsm")) {
			throw new ServiceException("请上传正确的excel文件!");
		}
		if (file.getSize()>1024*1024*100){
			return R.fail("文件大小超过限制，最大允许"+ 1024*1024*100 + "MB");
		}
		//数据回显
		return R.data(deviceChangeListService.importByExcel(file));
	}

}
