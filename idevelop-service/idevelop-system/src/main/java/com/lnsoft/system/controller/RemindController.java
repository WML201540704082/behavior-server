package com.lnsoft.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.secure.annotation.PreAuth;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.system.entity.Remind;
import com.lnsoft.system.service.IRemindService;
import com.lnsoft.system.vo.RemindVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 通知表 控制器
 *
 * @author Idevelop
 * @since 2024-09-27
 */
@RestController
@AllArgsConstructor
@RequestMapping("/remind")
@Api(value = "通知表", tags = "通知表接口")
public class RemindController extends IdevelopController {

	private IRemindService remindService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入remind")
	@PreAuth("hasPerm('system:common:all')")
	public R<Remind> detail(Remind remind) {
		Remind detail = remindService.getOne(Condition.getQueryWrapper(remind));
		return R.data(detail);
	}

	/**
	 * 分页 通知表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "弹框查看列表", notes = "传入remind")
	public R<List<RemindVO>> list(Remind remind) {
		List<RemindVO> list = remindService.getList(remind);
		return R.data(list);
	}

	/**
	 * 自定义分页 通知表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入remind")
	public R<IPage<RemindVO>> page(RemindVO remind, Query query) {
		IPage<RemindVO> pages = remindService.selectRemindPage(Condition.getPage(query), remind);
		return R.data(pages);
	}

	/**
	 * 新增 通知表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入remind")
	public R save(@Valid @RequestBody Remind remind) {
		return R.status(remindService.save(remind));
	}

	/**
	 * 修改 通知表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入remind")
	public R update(@Valid @RequestBody Remind remind) {
		return R.status(remindService.updateById(remind));
	}

	/**
	 * 删除 通知表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(remindService.deleteLogic(Func.toLongList(ids)));
	}


}
