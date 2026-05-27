package com.lnsoft.device.api.warehouse.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.annotation.ApiLog;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.warehouse.entity.DeviceStorageTemplate;
import com.lnsoft.device.api.warehouse.service.IDeviceStorageTemplateService;
import com.lnsoft.device.api.warehouse.vo.DeviceStorageTemplateVO;
import com.lnsoft.device.constant.CmdbCientityConstant;
import com.lnsoft.device.props.CmdbCientityProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 设备入库导入模板表 控制器
 *
 * @author Idevelop
 * @since 2024-02-22
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/storage/template")
@Api(value = "设备入库导入模板表", tags = "设备入库导入模板表接口")
public class DeviceStorageTemplateController extends IdevelopController {

	private IDeviceStorageTemplateService deviceStorageTemplateService;
	@Resource
	private CmdbCientityProperties ciEntityProperties;

	/**
	 * 详情
	 */
	@ApiLog("设备入库导入模板-详情")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入deviceStorageTemplate")
	public R<DeviceStorageTemplate> detail(DeviceStorageTemplate deviceStorageTemplate) {
		DeviceStorageTemplate detail = deviceStorageTemplateService.getOne(Condition.getQueryWrapper(deviceStorageTemplate));
		return R.data(detail);
	}

	/**
	 * 分页 设备入库导入模板表
	 */
	@ApiLog("设备入库导入模板-分页")
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入deviceStorageTemplate")
	public R<IPage<DeviceStorageTemplate>> list(DeviceStorageTemplate deviceStorageTemplate, Query query) {
		IPage<DeviceStorageTemplate> pages = deviceStorageTemplateService.page(Condition.getPage(query), Condition.getQueryWrapper(deviceStorageTemplate));
		return R.data(pages);
	}

	/**
	 * 自定义分页 设备入库导入模板表
	 */
	@ApiLog("设备入库导入模板-自定义分页")
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入deviceStorageTemplate")
	@ApiIgnore
	public R<IPage<DeviceStorageTemplateVO>> page(DeviceStorageTemplateVO deviceStorageTemplate, Query query) {
		IPage<DeviceStorageTemplateVO> pages = deviceStorageTemplateService.selectDeviceStorageTemplatePage(Condition.getPage(query), deviceStorageTemplate);
		return R.data(pages);
	}

	/**
	 * 新增 设备入库导入模板表
	 */
	@ApiLog("设备入库导入模板-新增")
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入deviceStorageTemplate")
	public R save(@Valid @RequestBody DeviceStorageTemplate deviceStorageTemplate) {
		return R.status(deviceStorageTemplateService.save(deviceStorageTemplate));
	}

	/**
	 * 修改 设备入库导入模板表
	 */
	@ApiLog("设备入库导入模板-修改")
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入deviceStorageTemplate")
	public R update(@Valid @RequestBody DeviceStorageTemplate deviceStorageTemplate) {
		return R.status(deviceStorageTemplateService.updateById(deviceStorageTemplate));
	}

	/**
	 * 新增或修改 设备入库导入模板表
	 */
	@ApiLog("设备入库导入模板-新增或修改")
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入deviceStorageTemplate")
	@ApiIgnore
	public R submit(@Valid @RequestBody DeviceStorageTemplate deviceStorageTemplate) {
		return R.status(deviceStorageTemplateService.saveOrUpdate(deviceStorageTemplate));
	}


	/**
	 * 删除 设备入库导入模板表
	 */
	@ApiLog("设备入库导入模板-删除")
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(deviceStorageTemplateService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 根据设备分类下载模板
	 */
	@ApiLog("设备入库导入模板-根据设备分类下载模板")
	@PostMapping("/download")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "模板下载", notes = "传入设备分类编码")
	public void downloadTemplate(@ApiParam(value = "设备分类编码", required = true) @RequestBody DeviceStorageTemplate deviceCategory, HttpServletResponse response) {
		if (StringUtil.isBlank(deviceCategory.getDeviceCategory())) {
			throw new ServiceException("设备分类编码不能为空!");
		}
		if (ciEntityProperties.getCientityId(CmdbCientityConstant.T109).equals(deviceCategory)){
			if (StringUtil.isBlank(deviceCategory.getDeviceType())){
				throw new ServiceException("设备类型编码不能为空!");
			}
		}
		deviceStorageTemplateService.downloadTemplate(deviceCategory.getDeviceCategory(),deviceCategory.getDeviceType(), response);
	}


}
