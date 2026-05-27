package com.lnsoft.device.api.safeaccess.controller;

import com.lnsoft.device.api.safeaccess.dto.SafeaccessIppoolDTO;
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
import com.lnsoft.device.api.safeaccess.entity.SafeaccessIppool;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessIppoolService;
import com.lnsoft.core.boot.ctrl.IdevelopController;

import java.util.List;
import java.util.Map;

/**
 * IP地址池 控制器
 *
 * @author Idevelop
 * @since 2024-03-08
 */
@RestController
@AllArgsConstructor
@RequestMapping("/safe/access/ippool")
@Api(value = "IP地址池", tags = "IP地址池接口")
public class SafeaccessIppoolController extends IdevelopController {

	private ISafeaccessIppoolService safeaccessIppoolService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入safeaccessIppool")
	public R<SafeaccessIppool> detail(SafeaccessIppool safeaccessIppool) {
		SafeaccessIppool detail = safeaccessIppoolService.getOne(Condition.getQueryWrapper(safeaccessIppool));
		return R.data(detail);
	}

	/**
	 * 分页 IP地址池
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入safeaccessIppool")
	public R<IPage<SafeaccessIppool>> list(SafeaccessIppool safeaccessIppool, Query query) {
		IPage<SafeaccessIppool> pages = safeaccessIppoolService.page(Condition.getPage(query), Condition.getQueryWrapper(safeaccessIppool));
		return R.data(pages);
	}


	/**
	 * 新增 IP地址池
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增", notes = "传入safeaccessIppool")
	public R save(@Valid @RequestBody SafeaccessIppool safeaccessIppool) {
		return R.status(safeaccessIppoolService.save(safeaccessIppool));
	}

	/**
	 * 修改 IP地址池
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "修改", notes = "传入safeaccessIppool")
	public R update(@Valid @RequestBody SafeaccessIppool safeaccessIppool) {
		return R.status(safeaccessIppoolService.updateById(safeaccessIppool));
	}


	/**
	 * 删除 IP地址池
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) List<Long> ids) {
		return R.status(safeaccessIppoolService.deleteLogic(ids));
	}

	/**
	 * 获取已用地址或空闲地址总数
	 */
	@GetMapping("/queryIpPoolCount")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "获取已用地址或空闲地址总数", notes = "传入subnet和isUsed")
	public R queryIpPoolCount(@RequestParam Map<String, String> ippool) {
		return R.data(safeaccessIppoolService.queryIpPoolCount(ippool));
	}

	/**
	 * 设置地址级别
	 */
	@PostMapping("/setIpLevel")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "设置地址级别", notes = "传入ippool")
	public R setIpLevel(@RequestBody SafeaccessIppoolDTO ippool) {
		return R.data(safeaccessIppoolService.setIpLevel(ippool));
	}

	/**
	 * 根据子网获取ip地址池
	 */
	@GetMapping("/search")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "根据子网获取ip地址池", notes = "传入safeaccessIppool")
	public R<IPage<SafeaccessIppoolDTO>> search(SafeaccessIppoolDTO safeaccessIppool, Query query) {
		IPage<SafeaccessIppoolDTO> pages = safeaccessIppoolService.searchByPage(safeaccessIppool, query);
		return R.data(pages);
	}

	@GetMapping("/searchNoPage")
	@ApiOperationSupport(order = 14)
	@ApiOperation(value = "根据子网获取ip地址池(无分页)", notes = "传入safeaccessIppool")
	public R<List<SafeaccessIppoolDTO>> searchNoPage(SafeaccessIppoolDTO safeaccessIppool) {
		List<SafeaccessIppoolDTO> pages = safeaccessIppoolService.searchNoPage(safeaccessIppool);
		return R.data(pages);
	}


	/**
	 * 设置新网关
	 */
	@PostMapping("/setNewGateway")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "设置新网关", notes = "传入ippool")
	public R setNewGateway(@RequestBody SafeaccessIppoolDTO ippool) {
		return R.data(safeaccessIppoolService.setNewGateway(ippool));
	}

	/**
	 * 查询ip地址池范围信息
	 */
	@GetMapping("/getQueryList")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "查询ip地址池范围信息", notes = "传入institutionCode或isPublic")
	public R getQueryList(@RequestParam Map<String, String> saTIppool) {
		return R.data(safeaccessIppoolService.getQueryList(saTIppool));
	}

	/**
	 * 根据地址池id查询
	 */
	@GetMapping("/getDetails")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "根据地址池id查询", notes = "传入ippoolId")
	public R getDetails(@ApiParam(value = "地址池id", required = true) String ippoolId) {
		return R.data(safeaccessIppoolService.getDetails(ippoolId));
	}

	/**
	 * 设置使用状态
	 */
	@PostMapping("/setIsUsed")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "设置使用状态", notes = "传入ippool")
	public R setIsUsed(@RequestBody SafeaccessIppoolDTO ippool) {
		return R.data(safeaccessIppoolService.setIsUsed(ippool));
	}

	/**
	 * 根据ip地址查询交换机详细信息
	 */
	@GetMapping("/getRadiusIp")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "根据ip地址查询交换机详细信息", notes = "传入authIp")
	public R getRadiusIp(@ApiParam(value = "ip地址", required = true) String ip) {
		return R.data(safeaccessIppoolService.getRadiusIp(ip));
	}

}
