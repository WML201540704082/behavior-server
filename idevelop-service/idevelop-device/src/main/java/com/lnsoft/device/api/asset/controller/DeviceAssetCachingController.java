package com.lnsoft.device.api.asset.controller;

import com.lnsoft.device.api.asset.task.DeviceAssetTask;
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
import com.lnsoft.device.api.asset.entity.DeviceAssetCaching;
import com.lnsoft.device.api.asset.vo.DeviceAssetCachingVO;
import com.lnsoft.device.api.asset.service.IDeviceAssetCachingService;
import com.lnsoft.core.boot.ctrl.IdevelopController;

import java.util.List;

/**
 *  控制器
 *
 * @author Idevelop
 * @since 2024-03-30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/asset/caching")
@Api(value = "老旧设备资产缓存接口", tags = "接口")
public class DeviceAssetCachingController extends IdevelopController {
	private DeviceAssetTask deviceAssetTask;
	private IDeviceAssetCachingService deviceAssetCachingService;

	/**
	 * 获取分类资产总值列表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取分类资产总值列表", notes = "传入设备分类编码-deviceCategoryCode")
	public R list(DeviceAssetCaching deviceAssetCaching) {
		return R.data(deviceAssetCachingService.findAssetList(deviceAssetCaching));
	}
	/**
	 * 手动同步cmdb资产数据接口
	 */
	@PostMapping("sync")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "手动同步cmdb资产数据接口")
	public void sync(){
		deviceAssetTask.updateAsset();
	}

}
