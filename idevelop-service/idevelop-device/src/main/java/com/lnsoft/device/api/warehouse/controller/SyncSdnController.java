package com.lnsoft.device.api.warehouse.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.safeaccess.dto.SdnUserAccessDTO;
import com.lnsoft.device.api.safeaccess.service.ISdnUserAccessService;
import com.lnsoft.device.api.warehouse.entity.SyncSdn;
import com.lnsoft.device.api.warehouse.service.ISyncSdnService;
import com.lnsoft.device.api.warehouse.vo.SyncSdnVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * 各地市同步sdn和radius控制表 控制器
 *
 * @author Idevelop
 * @since 2026-01-27
 */
@RestController
@AllArgsConstructor
@RequestMapping("/syncsdn")
@Api(value = "各地市同步sdn和radius控制表", tags = "各地市同步sdn和radius控制表接口")
public class SyncSdnController extends IdevelopController {

	private ISyncSdnService syncSdnService;

	private ISdnUserAccessService sdnUserAccessService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入syncSdn")
	public R<SyncSdn> detail(SyncSdn syncSdn) {
		SyncSdn detail = syncSdnService.getOne(Condition.getQueryWrapper(syncSdn));
		return R.data(detail);
	}

	/**
	 * 分页 各地市同步sdn和radius控制表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入syncSdn")
	public R<IPage<SyncSdn>> list(SyncSdn syncSdn, Query query) {
		IPage<SyncSdn> pages = syncSdnService.page(Condition.getPage(query), Condition.getQueryWrapper(syncSdn));
		return R.data(pages);
	}

	/**
	 * 自定义分页 各地市同步sdn和radius控制表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入syncSdn")
	public R<IPage<SyncSdnVO>> page(SyncSdnVO syncSdn, Query query) {
		IPage<SyncSdnVO> pages = syncSdnService.selectSyncSdnPage(Condition.getPage(query), syncSdn);
		return R.data(pages);
	}

	/**
	 * 新增 各地市同步sdn和radius控制表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入syncSdn")
	public R save(@Valid @RequestBody SyncSdn syncSdn) {
		return R.status(syncSdnService.save(syncSdn));
	}

	/**
	 * 修改 各地市同步sdn和radius控制表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入syncSdn")
	public R update(@Valid @RequestBody SyncSdn syncSdn) {
		return R.status(syncSdnService.updateById(syncSdn));
	}

	/**
	 * 新增或修改 各地市同步sdn和radius控制表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入syncSdn")
	public R submit(@Valid @RequestBody SyncSdn syncSdn) {
		return R.status(syncSdnService.saveOrUpdate(syncSdn));
	}


	/**
	 * 删除 各地市同步sdn和radius控制表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(syncSdnService.deleteLogic(Func.toLongList(ids)));
	}


	/**
	 * 每三分钟处理SyncSdn中的数据
	 */
	@Scheduled(cron = "0 */3 * * * ?")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "每三分钟处理SyncSdn中的数据", notes = "")
	public void scheduledSyncSdn() {
		R.status(syncSdnService.syncSdnService());
	}


	/**
	 * 获取各地市sdn中用户信息表
	 */
	@GetMapping("/getUserAccessList")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "获取各地市sdn中用户信息表", notes = "传入sdnUserAccess")
	public Map<String, Object> getUserAccessList(SdnUserAccessDTO sdnUserAccessDTO) {
		try {
			return sdnUserAccessService.getUserAccessList(sdnUserAccessDTO);
		} catch (Exception e) {
			throw new RuntimeException("获取各地市sdn中用户信息表失败: " + e.getMessage());
		}
	}

	/**
	 * 删除获取各地市sdn中用户信息表
	 */
	@GetMapping("/delUserAccess")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "删除获取各地市sdn中用户信息表", notes = "传入sdnUserAccess")
	public Map<String, Object> delUserAccess(SdnUserAccessDTO sdnUserAccessDTO) {
		try {
			return sdnUserAccessService.delUserAccess(sdnUserAccessDTO);
		} catch (Exception e) {
			throw new RuntimeException("删除获取各地市sdn中用户信息表: " + e.getMessage());
		}
	}

	/**
	 * 修改获取各地市sdn中用户信息表
	 */
	@PostMapping("/updateUserAccess")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "修改获取各地市sdn中用户信息表", notes = "传入sdnUserAccess")
	public Map<String, Object> updateUserAccess(@RequestBody SdnUserAccessDTO sdnUserAccessDTO) {
		try {
			return sdnUserAccessService.updateUserAccess(sdnUserAccessDTO);
		} catch (Exception e) {
			throw new RuntimeException("修改获取各地市sdn中用户信息表: " + e.getMessage());
		}
	}
}
