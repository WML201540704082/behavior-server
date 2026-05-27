package com.lnsoft.device.api.asset.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.data.vo.WainingDetailVO;
import com.lnsoft.data.vo.WarningCountVO;
import com.lnsoft.device.api.asset.service.IHomeService;
import com.lnsoft.device.api.asset.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xyzadmin
 */
@RestController
@AllArgsConstructor
@RequestMapping("/home")
@Api(value = "首页", tags = "首页统计接口")
public class HomeController extends IdevelopController {
	private IHomeService homeService;

	/**
	 * 台账概览设备统计接口
	 * @return
	 */
	@PostMapping("/deviceCount")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "台账概览设备统计接口")
	public R<DeviceCount> deviceCount(){
		return homeService.deviceCount();
	}
	/**
	 * 信创设备分发统计
	 * @return
	 */
	@PostMapping("/distributeCount")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "信创设备分发统计")
	public R<PatentDeviceCount> distributeCount(){
		return homeService.distributeCount();
	}

	/**
	 * 芯片架构(ARM)
	 * @return
	 */
	@PostMapping("/frameworkArm")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "芯片架构(ARM)")
	public R<DevicePatentOperatingVO> frameworkArm(){
		return homeService.frameworkArm();
	}
	/**
	 * 芯片架构(X86)
	 * @return
	 */
	@PostMapping("/frameworkX86")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "芯片架构(X86)")
	public R<DevicePatentOperatingVO> frameworkX86(){
		return homeService.frameworkX86();
	}
	/**
	 * 实时在线分布趋势图
	 */
	@PostMapping("/online")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "实时在线分布趋势图")
	public R<OnlineVO> online(String date){

		return R.data(homeService.online(date));
	}

	/**
	 * 告警处置一周统计
	 * @return
	 */
	@PostMapping("/warningCount")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "告警处置一周统计")
	public R<WarningCountVO> warningCount(){
		return homeService.warningCount();
	}

	/**
	 * 实时告警信息
	 * @return
	 */
	@PostMapping("/warningDetail")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "实时告警信息")
	public R<List<WainingDetailVO>> warningDetail(){
		return homeService.warningDetail();
	}

	/**
	 * 超龄分布图
	 * @return
	 */
	@PostMapping("/oldAge")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "超龄分布图")
	public R<List<OldAgeVO>> oldAge(){
		return homeService.oldAge();
	}
	/**
	 * 设备老旧趋势图
	 * @return
	 */
	@PostMapping("/oldTrend")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "设备老旧趋势图")
	public R<OldTrend> oldTrend(){
		return homeService.oldTrend();
	}

}
