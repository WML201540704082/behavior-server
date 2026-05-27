package com.lnsoft.device.api.safeaccess.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;

import javax.validation.Valid;

import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.device.api.safeaccess.entity.SafeaccessSubnetSecuritypartition;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessSubnetSecuritypartitionService;
import com.lnsoft.core.boot.ctrl.IdevelopController;

import java.util.List;

/**
 * 子网安全分区管理 控制器
 *
 * @author Idevelop
 * @since 2024-03-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("/safe/access/subnet/security/partition")
@Api(value = "子网安全分区管理", tags = "子网安全分区管理接口")
public class SafeaccessSubnetSecuritypartitionController extends IdevelopController {

	private ISafeaccessSubnetSecuritypartitionService safeaccessSubnetSecuritypartitionService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入safeaccessSubnetSecuritypartition")
	public R<SafeaccessSubnetSecuritypartition> detail(SafeaccessSubnetSecuritypartition safeaccessSubnetSecuritypartition) {
		SafeaccessSubnetSecuritypartition detail = safeaccessSubnetSecuritypartitionService.getOne(Condition.getQueryWrapper(safeaccessSubnetSecuritypartition));
		return R.data(detail);
	}

	/**
	 * 分页 子网安全分区管理
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入safeaccessSubnetSecuritypartition")
	public R<IPage<SafeaccessSubnetSecuritypartition>> list(SafeaccessSubnetSecuritypartition safeaccessSubnetSecuritypartition, Query query) {
		IPage<SafeaccessSubnetSecuritypartition> pages = safeaccessSubnetSecuritypartitionService.page(Condition.getPage(query), Condition.getQueryWrapper(safeaccessSubnetSecuritypartition));
		return R.data(pages);
	}

	/**
	 * 新增 子网安全分区管理
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入safeaccessSubnetSecuritypartition")
	public R save(@Valid @RequestBody SafeaccessSubnetSecuritypartition safeaccessSubnetSecuritypartition) {
		return R.status(safeaccessSubnetSecuritypartitionService.save(safeaccessSubnetSecuritypartition));
	}

	/**
	 * 修改 子网安全分区管理
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入safeaccessSubnetSecuritypartition")
	public R update(@Valid @RequestBody SafeaccessSubnetSecuritypartition safeaccessSubnetSecuritypartition) {
		return R.status(safeaccessSubnetSecuritypartitionService.updateById(safeaccessSubnetSecuritypartition));
	}


	/**
	 * 删除 子网安全分区管理
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) List<Long> ids) {
		return R.status(safeaccessSubnetSecuritypartitionService.deleteLogic(ids));
	}


}
