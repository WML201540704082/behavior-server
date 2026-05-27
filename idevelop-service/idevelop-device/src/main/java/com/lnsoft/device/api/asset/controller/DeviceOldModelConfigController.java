package com.lnsoft.device.api.asset.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.device.api.asset.entity.DeviceOldModelConfig;
import com.lnsoft.device.api.asset.vo.DeviceOldModelConfigVO;
import com.lnsoft.device.api.asset.service.IDeviceOldModelConfigService;
import com.lnsoft.core.boot.ctrl.IdevelopController;

import java.util.List;

/**
 * 老旧设备打分模型配置表 控制器
 *
 * @author Idevelop
 * @since 2024-06-19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/deviceoldmodelconfig")
@Api(value = "老旧设备打分模型配置表", tags = "老旧设备打分模型配置表接口")
public class DeviceOldModelConfigController extends IdevelopController {

	private IDeviceOldModelConfigService deviceOldModelConfigService;


	/**
	 * 分页 老旧设备打分模型配置表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入deviceOldModelConfig")
	public R<List<DeviceOldModelConfig>> list(DeviceOldModelConfig deviceOldModelConfig) {
		return R.data(deviceOldModelConfigService.findList(deviceOldModelConfig));
	}

	/**
	 * 自定义分页 老旧设备打分模型配置表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页（未使用）", notes = "传入deviceOldModelConfig")
	public R<IPage<DeviceOldModelConfigVO>> page(DeviceOldModelConfigVO deviceOldModelConfig, Query query) {
		IPage<DeviceOldModelConfigVO> pages = deviceOldModelConfigService.selectDeviceOldModelConfigPage(Condition.getPage(query), deviceOldModelConfig);
		return R.data(pages);
	}

	/**
	 * 修改 老旧设备打分模型配置表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入deviceOldModelConfig")
	public R update(@Valid @RequestBody List<DeviceOldModelConfig> deviceOldModelConfig) {

		return R.status(deviceOldModelConfigService.updateBatchById(deviceOldModelConfig));
	}

}
