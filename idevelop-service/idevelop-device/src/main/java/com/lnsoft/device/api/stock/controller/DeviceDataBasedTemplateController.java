package com.lnsoft.device.api.stock.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.oss.AliossTemplate;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.cmdb.entity.DeviceDataBasedTemplate;
import com.lnsoft.device.api.stock.service.IDeviceDataBasedTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 设备-数据治理模板表 控制器
 *
 * @author Idevelop
 * @since 2024-06-19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/data/based/template")
@Api(value = "设备-数据治理模板表", tags = "设备-数据治理模板表接口")
public class DeviceDataBasedTemplateController extends IdevelopController {

	@Resource
	private AliossTemplate aliossTemplate;
	private IDeviceDataBasedTemplateService deviceDataBasedTemplateService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入deviceDataBasedTemplate")
	public R<DeviceDataBasedTemplate> detail(DeviceDataBasedTemplate deviceDataBasedTemplate) {
		DeviceDataBasedTemplate detail = deviceDataBasedTemplateService.getOne(Condition.getQueryWrapper(deviceDataBasedTemplate));
		return R.data(detail);
	}

	/**
	 * 分页 设备-数据治理模板表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入deviceDataBasedTemplate")
	public R<IPage<DeviceDataBasedTemplate>> list(DeviceDataBasedTemplate deviceDataBasedTemplate, Query query) {
		query.setDescs("create_time");
		IPage<DeviceDataBasedTemplate> pages = deviceDataBasedTemplateService.page(Condition.getPage(query), Condition.getQueryWrapper(deviceDataBasedTemplate));
		List<DeviceDataBasedTemplate> records = pages.getRecords();
		for (DeviceDataBasedTemplate record : records) {
			String filePath = aliossTemplate.convertToCurrentModePath(record.getFilePath());
			record.setFilePath(filePath);
		}
		return R.data(pages);
	}

	/**
	 * 新增 设备-数据治理模板表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增", notes = "传入deviceDataBasedTemplate")
	public R save(@Valid @RequestBody DeviceDataBasedTemplate deviceDataBasedTemplate) {
		String filePath = deviceDataBasedTemplate.getFilePath().split("\\?Expires=")[0];
		deviceDataBasedTemplate.setFilePath(filePath);
		return R.status(deviceDataBasedTemplateService.save(deviceDataBasedTemplate));
	}

	/**
	 * 删除 设备-数据治理模板表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(deviceDataBasedTemplateService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 修改 设备-数据治理模板表
	 */
//	@PostMapping("/update")
//	@ApiOperationSupport(order = 5)
//	@ApiOperation(value = "修改", notes = "传入deviceDataBasedTemplate")
//	public R update(@Valid @RequestBody DeviceDataBasedTemplate deviceDataBasedTemplate) {
//		return R.status(deviceDataBasedTemplateService.updateById(deviceDataBasedTemplate));
//	}


}
