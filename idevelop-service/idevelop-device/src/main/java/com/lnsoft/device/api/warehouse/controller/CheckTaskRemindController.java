package com.lnsoft.device.api.warehouse.controller;

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
import com.lnsoft.device.api.warehouse.entity.CheckTaskRemind;
import com.lnsoft.device.api.warehouse.vo.CheckTaskRemindVO;
import com.lnsoft.device.api.warehouse.service.ICheckTaskRemindService;
import com.lnsoft.core.boot.ctrl.IdevelopController;

/**
 * 盘点任务 控制器
 *
 * @author Idevelop
 * @since 2024-06-12
 */
@RestController
@AllArgsConstructor
@RequestMapping("/checktaskremind")
@Api(value = "盘点任务提醒", tags = "盘点任务提醒接口")
public class CheckTaskRemindController extends IdevelopController {

	private ICheckTaskRemindService checkTaskRemindService;


	/**
	 * 自定义分页 盘点任务
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "分页", notes = "传入status")
	public R<IPage<CheckTaskRemindVO>> page(CheckTaskRemindVO checkTaskRemind, Query query) {
		IPage<CheckTaskRemindVO> pages = checkTaskRemindService.selectCheckTaskRemindPage(Condition.getPage(query), checkTaskRemind);
		return R.data(pages);
	}
	/**
	 * 更新提醒查看状态
	 */
	@PostMapping("/status")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "更新提醒查看状态", notes = "传入checkTaskRemind  id,status必传")
	public R<Integer> status(@RequestBody CheckTaskRemindVO checkTaskRemind) {
		return R.data(checkTaskRemindService.status(checkTaskRemind));
	}

}
