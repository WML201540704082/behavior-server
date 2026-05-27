package com.lnsoft.device.api.asset.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.asset.dto.DevicePatentDistributionDTO;
import com.lnsoft.device.api.asset.dto.DevicePatentOnlineDTO;
import com.lnsoft.device.api.asset.service.IDevicePatentService;
import com.lnsoft.device.api.asset.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/device/patent")
@Api(value = "信创设备接口", tags = "信创设备接口")
public class DevicePatentController {


	private IDevicePatentService devicePatentService;

	/**
	 * 获取信创设备列表
	 */
	@PostMapping("/list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取信创设备列表", notes = "获取信创设备列表")
	public R<FeignCiCientity> deviceList(@ApiParam @RequestBody DeviceInfoVo vo) {
		return R.data(devicePatentService.deviceList(vo));
	}


	/**
	 * 设备数据概览
	 */
	@PostMapping("/number")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "设备数据概览", notes = "设备数据概览")
	public R<Map<String, DevicePatentNumberVO>> number() {
		try {
			Map<String, DevicePatentNumberVO> result = devicePatentService.number();
			return R.data(result);
		} catch (Exception e) {
			throw new RuntimeException("设备数据概览异常：" + e.getMessage());
		}
	}

	/**
	 * 采购方式概览
	 */
	@PostMapping("/purchase")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "采购方式概览", notes = "采购方式概览")
	public R<List<DevicePatentPurchaseVO>> purchase() {
		try {
			List<DevicePatentPurchaseVO> result = devicePatentService.purchase();
			return R.data(result);
		} catch (Exception e) {
			throw new RuntimeException("采购方式概览：" + e.getMessage());
		}
	}

	/**
	 * 数据概览-分发情况
	 */
	@PostMapping("/distribution")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "数据概览-分发情况", notes = "数据概览-分发情况")
	public R<List<DevicePatentDistributionVO>> distribution(@RequestBody DevicePatentDistributionDTO devicePatentDistributionDTO) {
		try {
			List<DevicePatentDistributionVO> result = devicePatentService.distribution(devicePatentDistributionDTO);
			return R.data(result);
		} catch (Exception e) {
			throw new RuntimeException("数据概览-分发情况：" + e.getMessage());
		}
	}

	/**
	 * 数据概览-在线情况
	 */
	@PostMapping("/online")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "数据概览-在线情况", notes = "数据概览-在线情况")
	public R<List<DevicePatentOnlineVO>> online() {
		try {
			List<DevicePatentOnlineVO> result = devicePatentService.online();
			return R.data(result);
		} catch (Exception e) {
			throw new RuntimeException("数据概览-在线情况：" + e.getMessage());
		}
	}

	/**
	 * 数据概览-最后同步时间
	 */
	@PostMapping("/online/time")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "数据概览-最后同步时间", notes = "数据概览-最后同步时间")
	public R<Map<String, String>> onlineTime() {
		try {
			Map<String, String> result = devicePatentService.onlineTime();
			return R.data(result);
		} catch (Exception e) {
			throw new RuntimeException("数据概览-在线情况：" + e.getMessage());
		}
	}

	/**
	 * 在线情况统计
	 */
	@PostMapping("/online/statistics")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "在线情况统计", notes = "在线情况统计")
	public R<List<DevicePatentOnlineVO>> onlineStatistics(@RequestBody DevicePatentOnlineDTO patentOnlineDTO) {
		try {
			List<DevicePatentOnlineVO> result = devicePatentService.onlineStatistics(patentOnlineDTO);
			return R.data(result);
		} catch (Exception e) {
			throw new RuntimeException("数据概览-在线情况：" + e.getMessage());
		}
	}


	/**
	 * 在线情况统计 - 单位
	 */
	@PostMapping("/online/unit")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "在线情况统计 - 单位", notes = "在线情况统计 - 单位")
	public R<List<Map<String, String>>> onlineStatisticsUnit(@RequestBody DevicePatentOnlineDTO patentOnlineDTO) {
		try {
			List<Map<String, String>> result = devicePatentService.onlineStatisticsUnit(patentOnlineDTO);
			return R.data(result);
		} catch (Exception e) {
			throw new RuntimeException("软硬件分布-芯片架构：" + e.getMessage());
		}
	}


	/**
	 * 软硬件分布 - 操作系统
	 */
	@PostMapping("/operating")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "软硬件分布-操作系统", notes = "软硬件分布-操作系统")
	public R<List<DevicePatentOperatingVO>> operating() {
		try {
			List<DevicePatentOperatingVO> result = devicePatentService.operating();
			return R.data(result);
		} catch (Exception e) {
			throw new RuntimeException("软硬件分布-操作系统：" + e.getMessage());
		}
	}

	/**
	 * 软硬件分布-芯片架构(ARM)
	 */
	@PostMapping("/framework/arm")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "软硬件分布-芯片架构(ARM)", notes = "软硬件分布-芯片架构(ARM)")
	public R<DevicePatentOperatingVO> frameworkArm() {
		try {
			DevicePatentOperatingVO result = devicePatentService.frameworkArm();
			return R.data(result);
		} catch (Exception e) {
			throw new RuntimeException("软硬件分布-芯片架构(ARM)：" + e.getMessage());
		}
	}

	/**
	 * 软硬件分布-芯片架构(X86)
	 */
	@PostMapping("/framework/x86")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "软硬件分布-芯片架构(X86)", notes = "软硬件分布-芯片架构(X86)")
	public R<DevicePatentOperatingVO> frameworkX86() {
		try {
			DevicePatentOperatingVO result = devicePatentService.frameworkX86();
			return R.data(result);
		} catch (Exception e) {
			throw new RuntimeException("软硬件分布-芯片架构(ARM)：" + e.getMessage());
		}
	}

	/**
	 * 软硬件分布-品牌分布
	 */
	@PostMapping("/brand")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "软硬件分布-品牌分布", notes = "软硬件分布-品牌分布")
	public R<List<DevicePatentOperatingVO>> brand() {
		try {
			List<DevicePatentOperatingVO> result = devicePatentService.brand();
			return R.data(result);
		} catch (Exception e) {
			throw new RuntimeException("软硬件分布-品牌分布：" + e.getMessage());
		}
	}

	/**
	 * 7日内每日在线数量趋势
	 */
	@PostMapping("/online/trend")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "7日内每日在线数量趋势", notes = "柱状图")
	public R<List<DevicePatentOperatingVO>> onlineTrend() {
		try {
			List<DevicePatentOperatingVO> result = devicePatentService.onlineTrend();
			return R.data(result);
		} catch (Exception e) {
			throw new RuntimeException("7日内每日在线数量趋势：" + e.getMessage());
		}
	}

	/**
	 * 设备替代数据
	 */
	@PostMapping("/replace")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "设备替代数据", notes = "设备替代数据")
	public R<Map<String, DevicePatentReplaceVO>> replace() {
		try {
			Map<String, DevicePatentReplaceVO> result = devicePatentService.replace();
			return R.data(result);
		} catch (Exception e) {
			throw new RuntimeException("设备替代数据：" + e.getMessage());
		}
	}


}
