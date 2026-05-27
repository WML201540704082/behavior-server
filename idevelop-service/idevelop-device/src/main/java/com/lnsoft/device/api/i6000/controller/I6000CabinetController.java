package com.lnsoft.device.api.i6000.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.i6000.entity.I6000Cabinet;
import com.lnsoft.device.api.i6000.service.II6000CabinetService;
import com.lnsoft.device.api.i6000.vo.I6000CabinetVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * I6000单位 控制器
 *
 * @author Idevelop
 * @since 2025-02-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("/i6000cabinet")
@Api(value = "I6000单位", tags = "I6000单位接口")
public class I6000CabinetController extends IdevelopController {

	private II6000CabinetService i6000CabinetService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入i6000Cabinet")
	public R<I6000Cabinet> detail(I6000Cabinet i6000Cabinet) {
		I6000Cabinet detail = i6000CabinetService.getOne(Condition.getQueryWrapper(i6000Cabinet));
		return R.data(detail);
	}

	/**
	 * 分页 I6000单位
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入i6000Cabinet")
	public R<IPage<I6000Cabinet>> list(I6000Cabinet i6000Cabinet, Query query) {
		IPage<I6000Cabinet> pages = i6000CabinetService.page(Condition.getPage(query), Condition.getQueryWrapper(i6000Cabinet));
		return R.data(pages);
	}

	/**
	 * 自定义分页 I6000单位
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入i6000Cabinet")
	public R<IPage<I6000CabinetVO>> page(I6000CabinetVO i6000Cabinet, Query query) {
		IPage<I6000CabinetVO> pages = i6000CabinetService.selectI6000CabinetPage(Condition.getPage(query), i6000Cabinet);
		return R.data(pages);
	}

	/**
	 * 新增 I6000单位
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入i6000Cabinet")
	public R save(@Valid @RequestBody I6000Cabinet i6000Cabinet) {
		return R.status(i6000CabinetService.save(i6000Cabinet));
	}

	/**
	 * 修改 I6000单位
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入i6000Cabinet")
	public R update(@Valid @RequestBody I6000Cabinet i6000Cabinet) {
		return R.status(i6000CabinetService.updateById(i6000Cabinet));
	}

	/**
	 * 新增或修改 I6000单位
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入i6000Cabinet")
	public R submit(@Valid @RequestBody I6000Cabinet i6000Cabinet) {
		return R.status(i6000CabinetService.saveOrUpdate(i6000Cabinet));
	}


	/**
	 * 删除 I6000单位
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(i6000CabinetService.deleteLogic(Func.toLongList(ids)));
	}


}
