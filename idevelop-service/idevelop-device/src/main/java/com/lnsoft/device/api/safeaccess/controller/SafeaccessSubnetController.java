package com.lnsoft.device.api.safeaccess.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.google.common.collect.Lists;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.safeaccess.entity.SafeaccessEditSubnet;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessSubnetService;
import com.lnsoft.device.entity.SafeaccessSubnet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 子网管理表 控制器
 *
 * @author Idevelop
 * @since 2024-03-08
 */
@RestController
@AllArgsConstructor
@RequestMapping("/safe/access/subnet")
@Api(value = "子网管理表", tags = "子网管理接口")
public class SafeaccessSubnetController extends IdevelopController {

	private ISafeaccessSubnetService safeaccessSubnetService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入safeaccessSubnet")
	public R<SafeaccessSubnet> detail(SafeaccessSubnet safeaccessSubnet) {
		SafeaccessSubnet detail = safeaccessSubnetService.getOne(Condition.getQueryWrapper(safeaccessSubnet));
		return R.data(detail);
	}

	/**
	 * 分页 子网管理表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入safeaccessSubnet")
	public R<IPage<SafeaccessSubnet>> list(SafeaccessSubnet safeaccessSubnet, Query query) {
		// 数据权限
		IdevelopUser sysUser = SecureUtil.getUser();
		IPage<SafeaccessSubnet> pages = safeaccessSubnetService.selectSafeaccessSubnetPage(safeaccessSubnet, query, sysUser);
		return R.data(pages);
	}


	/**
	 * 新增 子网管理表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增/修改", notes = "传入safeaccessSubnet")
	public R save(@Valid @RequestBody SafeaccessSubnet safeaccessSubnet) {
		return R.data(safeaccessSubnetService.customSave(safeaccessSubnet));
	}

	/**
	 * 修改 子网管理表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入safeaccessSubnet")
	@ApiIgnore
	public R update(@Valid @RequestBody SafeaccessSubnet safeaccessSubnet) {
		safeaccessSubnetService.syncUpdateBySafeaccessSubnetId(safeaccessSubnet);
		return R.status(safeaccessSubnetService.updateById(safeaccessSubnet));
	}


	/**
	 * 删除 子网管理表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestBody ArrayList<SafeaccessSubnet> ids) {
		return R.data(safeaccessSubnetService.customRemove(ids));
	}

	/**
	 * 获取子网地址与广播地址
	 */
	@GetMapping("/getSubnetInfo")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "获取子网地址与广播地址", notes = "传入网关 和 掩码")
	public R<List> getSubnetInfo(String id, @ApiParam(value = "子网网关", required = true) String subnetGateway,
								 @ApiParam(value = "子网掩码", required = true) String subnetMask) {
		return R.data(safeaccessSubnetService.getSubnetInfo(id, subnetGateway, subnetMask));
	}

	/**
	 * 查询子网中是否有终端存在
	 */
	@GetMapping("/hasTerminal")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "查询子网中是否有终端存在", notes = "传入子网id")
	public R hasTerminal(@ApiParam(value = "子网id", required = true) String subnetId) {
		return R.status(safeaccessSubnetService.hasTerminal(subnetId));
	}

	/**
	 * 初始化并更新单个子网的地址池
	 */
	@GetMapping("/initIpPool")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "初始化并更新单个子网的地址池", notes = "传入子网id")
	public R initIpPool(@ApiParam(value = "子网id", required = true) String subnetId) {
		return R.status(safeaccessSubnetService.initIpPool(subnetId));
	}

	/**
	 * 初始化并更新单个子网的地址池
	 */
	@GetMapping("/initIpPool1")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "初始化并更新单个子网的地址池", notes = "传入子网id")
	public R initIpPool1(@ApiParam(value = "子网id", required = true) String subnetId) {
		return R.status(safeaccessSubnetService.initIpPool1(subnetId));
	}

	/**
	 * 子网同步radius
	 */
//	@GetMapping("/syncSubnet")
//	@ApiOperationSupport(order = 11)
//	@ApiOperation(value = "子网同步radius", notes = "传入子网id")
//	public R syncSubnet(@ApiParam(value = "子网id", required = true) String subnetId) {
//		return R.status(safeaccessSubnetService.syncSubnet(subnetId));
//	}

	/**
	 * 子网网段变更
	 */
	@PostMapping("/updateSubnet")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "子网网段变更", notes = "传入子网id")
	public R updateSubnet(@RequestBody SafeaccessEditSubnet editSubnet) {
		ArrayList<SafeaccessEditSubnet> list = Lists.newArrayList(editSubnet);
		return R.data(safeaccessSubnetService.updateSubnet(list));
	}

	/**
	 * 根据子网id查询是否存在交换机信息
	 */
	@GetMapping("/selectSwitches")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "根据子网id查询是否存在交换机信息", notes = "传入子网id")
	public R selectSwitches(@ApiParam(value = "子网id", required = true) String subnetId) {
		return R.status(safeaccessSubnetService.selectSwitches(subnetId));
	}


	/**
	 * 设置公共子网
	 */
	@PostMapping("/setCommonSubNet")
	@ApiOperationSupport(order = 14)
	@ApiOperation(value = "设置公共子网", notes = "传入subnet")
	public R setCommonSubNet(@RequestBody SafeaccessSubnet subnet) {
		return R.status(safeaccessSubnetService.setCommonSubNet(subnet));
	}

	/**
	 * 返回子网段
	 */
	@GetMapping("/ipPoolIdSegmentList")
	@ApiOperationSupport(order = 15)
	@ApiOperation(value = "返回子网段", notes = "传入子网id")
	public R ipPoolIdSegmentList(@ApiParam(value = "子网id", required = true) String subnetId) {
		return R.data(safeaccessSubnetService.ipPoolIdSegmentList(subnetId));
	}

	/**
	 * 根据所属单位、公共子网获取子网
	 */
	@GetMapping("/query")
	@ApiOperationSupport(order = 16)
	@ApiOperation(value = "根据所属单位、公共子网获取子网", notes = "传入institutionCode或isPublic")
	public R query(@ApiParam(value = "所属单位", required = false) String institutionCode,
				   @ApiParam(value = "是否公共子网0否1是", required = false) String isPublic) {
		return R.data(safeaccessSubnetService.queryByParam(institutionCode, isPublic));
	}


	@GetMapping("/initIpPool/data")
	@ApiOperationSupport(order = 17)
	@ApiOperation(value = "(数据迁移)初始化并更新用户入网信息、子网管理信息")
	public R initIpPoolData() {
		return R.status(safeaccessSubnetService.initIpPoolData());
	}

	@GetMapping("/initIpPool/info")
	@ApiOperationSupport(order = 18)
	@ApiOperation(value = "(数据迁移)初始化IP资源池")
	public R initIpPoolInfo(@ApiParam(value = "部门编码4位", required = false) String deptCode) {
		return R.status(safeaccessSubnetService.initIpPoolInfo(deptCode));
	}

}
