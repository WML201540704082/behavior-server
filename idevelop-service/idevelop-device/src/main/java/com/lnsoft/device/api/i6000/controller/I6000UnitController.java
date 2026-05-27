package com.lnsoft.device.api.i6000.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.secure.annotation.PreAuth;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.i6000.entity.I6000Unit;
import com.lnsoft.device.api.i6000.service.II6000UnitService;
import com.lnsoft.device.api.i6000.vo.I6000UnitVO;
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
 * @since 2024-04-12
 */
@RestController
@AllArgsConstructor
@RequestMapping("/i6000unit")
@Api(value = "I6000单位", tags = "I6000单位接口")
public class I6000UnitController extends IdevelopController {

	private II6000UnitService i6000UnitService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "详情", notes = "传入i6000Unit")
	public R<I6000Unit> detail(I6000Unit i6000Unit) {
		I6000Unit detail = i6000UnitService.getOne(Condition.getQueryWrapper(i6000Unit));
		return R.data(detail);
	}

	/**
	 * 分页 I6000单位
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "分页", notes = "传入i6000Unit")
	public R<IPage<I6000Unit>> list(I6000Unit i6000Unit, Query query) {
		IPage<I6000Unit> pages = i6000UnitService.page(Condition.getPage(query), Condition.getQueryWrapper(i6000Unit));
		return R.data(pages);
	}

	/**
	 * 自定义分页 I6000单位
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "分页", notes = "传入i6000Unit")
	public R<IPage<I6000UnitVO>> page(I6000UnitVO i6000Unit, Query query) {
		IPage<I6000UnitVO> pages = i6000UnitService.selectI6000UnitPage(Condition.getPage(query), i6000Unit);
		return R.data(pages);
	}

	/**
	 * 新增 I6000单位
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增", notes = "传入i6000Unit")
	public R save() {

		return i6000UnitService.inert();
	}

	/**
	 * 修改 I6000单位
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "修改", notes = "传入i6000Unit")
	public R update(@Valid @RequestBody I6000Unit i6000Unit) {
		return R.status(i6000UnitService.updateById(i6000Unit));
	}

	/**
	 * 新增或修改 I6000单位
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增或修改", notes = "传入i6000Unit")
	public R submit(@Valid @RequestBody I6000Unit i6000Unit) {
		return R.status(i6000UnitService.saveOrUpdate(i6000Unit));
	}


	/**
	 * 删除 I6000单位
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(i6000UnitService.deleteLogic(Func.toLongList(ids)));
	}
//	/**
//	 * 单位匹配接口
//	 */
//	@PostMapping("/marry")
//	@ApiOperationSupport(order = 8)
//	@ApiOperation(value = "单位匹配接口")
//	public R marry(){
//		return i6000UnitService.marry();
//	}


}
