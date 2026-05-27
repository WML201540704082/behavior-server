package com.lnsoft.device.api.asset.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.log.annotation.ApiLog;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.data.dto.WarningListDTO;
import com.lnsoft.data.entity.DevelopWarning;
import com.lnsoft.device.api.asset.dto.DevicePressDoDTO;
import com.lnsoft.device.api.asset.dto.WorkBenchFinishDTO;
import com.lnsoft.device.api.asset.service.IDeviceWorkBenchService;
import com.lnsoft.device.api.asset.vo.WorkBenchCompleteVO;
import com.lnsoft.device.api.asset.vo.WorkBenchFinishVO;
import com.lnsoft.device.api.asset.vo.WorkBenchIPNumberVO;
import com.lnsoft.device.api.asset.vo.WorkBenchUnderwayVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: xuel
 * @CreateTime: 2024/7/18 11:23
 * @Description: 个人工作台控制器 DeviceWorkBenchController
 */
@RestController
@AllArgsConstructor
@RequestMapping("/work/bench")
@Api(value = "个人工作台控制器2.0", tags = "个人工作台控制器接口2.0")
public class DeviceWorkBenchController {


	private IDeviceWorkBenchService deviceWorkBenchService;

	/**
	 * 代办任务：我处理的
	 */
	@GetMapping("/finish/list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "代办任务：我处理的", notes = "传入workBenchFinishDTO")
	public R<IPage<WorkBenchFinishVO>> finishList(WorkBenchFinishDTO workBenchFinishDTO) {
		try {
			IPage<WorkBenchFinishVO> resultList = deviceWorkBenchService.getDoneList(workBenchFinishDTO);
			return R.data(resultList);
		} catch (Exception e) {
			throw new RuntimeException("获取个人已办列表失败:" + e.getMessage());
		}

	}

	/**
	 * 代办任务：我处理的-进行中
	 */
	@ApiLog
	@GetMapping("/wnderway/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "代办任务：我处理的-进行中", notes = "传入workBenchFinishDTO")
	public R<IPage<WorkBenchUnderwayVO>> wnderwayList(WorkBenchFinishDTO workBenchFinishDTO) {
		try {
			IPage<WorkBenchUnderwayVO> resultList = deviceWorkBenchService.underwayTask(workBenchFinishDTO);
			return R.data(resultList);
		} catch (Exception e) {
			throw new RuntimeException("获取个人已办列表失败:" + e.getMessage());
		}

	}


	/**
	 * 代办任务：我处理的-已完成
	 */
	@GetMapping("/complete/list")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "代办任务：我处理的-已完成", notes = "传入workBenchFinishDTO")
	public R<IPage<WorkBenchCompleteVO>> completeList(WorkBenchFinishDTO workBenchFinishDTO) {
		try {
			IPage<WorkBenchCompleteVO> resultList = deviceWorkBenchService.completeTask(workBenchFinishDTO);
			return R.data(resultList);
		} catch (Exception e) {
			throw new RuntimeException("获取个人已办列表失败:" + e.getMessage());
		}

	}

	/**
	 * IP资源：IP地址总数
	 */
	@GetMapping("/ip/all")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "IP资源：IP地址总数")
	public R<Map<String, Object>> getIpAllNumber() {
		try {
			Map<String, Object> resultMap = deviceWorkBenchService.getIpAllNumber();
			return R.data(resultMap);
		} catch (Exception e) {
			throw new RuntimeException("获取IP资源：IP地址总数失败:" + e.getMessage());
		}
	}

	/**
	 * IP资源：内网/外网使用率
	 */
	@GetMapping("/ip/assign")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "IP资源：内网/外网使用率")
	public R<List<WorkBenchIPNumberVO>> getIpAssignNumber() {
		try {
			List<WorkBenchIPNumberVO> jsonObject = deviceWorkBenchService.getIpAssignNumber();
			return R.data(jsonObject);
		} catch (Exception e) {
			throw new RuntimeException("获取个人已办列表失败:" + e.getMessage());
		}
	}

	/**
	 * 实时告警信息
	 */
	@GetMapping("/warning/list")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "实时告警信息")
	public R<Page<DevelopWarning>> warningList(DevelopWarning developWarning, Query query) {
		try {
			WarningListDTO warningListDTO = new WarningListDTO();
			warningListDTO.setDevelopWarning(developWarning);
			warningListDTO.setQuery(query);
			Page<DevelopWarning> resultPage = deviceWorkBenchService.warningList(warningListDTO);
			return R.data(resultPage);
		} catch (Exception e) {
			throw new RuntimeException("获取实时告警信息列表失败:" + e.getMessage());
		}
	}

	/**
	 * 告警处置一周统计
	 */
	@GetMapping("/warning/statistics")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "告警处置一周统计")
	public R<Map<String, Integer>> warningStatistics(DevelopWarning developWarning) {
		try {
			Map<String, Integer> resultMap = deviceWorkBenchService.warningStatistics(developWarning);
			return R.data(resultMap);
		} catch (Exception e) {
			throw new RuntimeException("获取告警处置一周统计失败:" + e.getMessage());
		}
	}


	/**
	 * 代办任务：我发起的统
	 */
	@GetMapping("/wnderway/complete/number")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "代办任务：我发起的统计")
	public R<Map<String, Integer>> wnderwayCompleteNum(WorkBenchFinishDTO workBenchFinishDTO) {
		try {
			Map<String, Integer> resultMap = deviceWorkBenchService.wnderwayCompleteNum(workBenchFinishDTO);
			return R.data(resultMap);
		} catch (Exception e) {
			throw new RuntimeException("获取告警处置一周统计失败:" + e.getMessage());
		}
	}

	/**
	 * 催办接口
	 */
	@ApiLog
	@PostMapping("/press/do")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "催办接口")
	public R pressDo(@RequestBody DevicePressDoDTO devicePressDoDTO){
		return deviceWorkBenchService.pressDo(devicePressDoDTO);
	}
}
