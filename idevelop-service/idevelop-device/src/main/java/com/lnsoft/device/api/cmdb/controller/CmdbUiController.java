package com.lnsoft.device.api.cmdb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.secure.annotation.PreAuth;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.cmdb.entity.CmdbUi;
import com.lnsoft.device.api.cmdb.service.ICmdbUiService;
import com.lnsoft.device.api.cmdb.vo.CmdbUiVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 前端属性/配置项配置表 控制器
 *
 * @author Idevelop
 * @since 2024-06-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("/cmdbui")
@Api(value = "前端属性/配置项配置表", tags = "前端属性/配置项配置表接口")
public class CmdbUiController extends IdevelopController {

	private ICmdbUiService cmdbUiService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "详情", notes = "传入cmdbUi")
	public R<CmdbUi> detail(CmdbUi cmdbUi) {
		CmdbUi detail = cmdbUiService.getOne(Condition.getQueryWrapper(cmdbUi));
		return R.data(detail);
	}

	/**
	 * 分页 前端属性/配置项配置表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "分页", notes = "传入cmdbUi")
	public R<IPage<CmdbUi>> list(CmdbUi cmdbUi, Query query) {
		IPage<CmdbUi> pages = cmdbUiService.page(Condition.getPage(query), Condition.getQueryWrapper(cmdbUi));
		return R.data(pages);
	}

	/**
	 * 自定义分页 前端属性/配置项配置表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入cmdbUi")
	public R<IPage<CmdbUiVO>> page(CmdbUiVO cmdbUi, Query query) {
		IPage<CmdbUiVO> pages = cmdbUiService.selectCmdbUiPage(Condition.getPage(query), cmdbUi);
		return R.data(pages);
	}

	/**
	 * 新增 前端属性/配置项配置表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增", notes = "传入cmdbUi")
	public R save(@Valid @RequestBody CmdbUi cmdbUi) {
		return R.status(cmdbUiService.save(cmdbUi));
	}

	/**
	 * 修改 前端属性/配置项配置表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "修改", notes = "传入cmdbUi")
	public R update(@Valid @RequestBody CmdbUi cmdbUi) {
		return R.status(cmdbUiService.updateById(cmdbUi));
	}

	/**
	 * 新增或修改 前端属性/配置项配置表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增或修改", notes = "传入cmdbUi")
	public R submit(@Valid @RequestBody CmdbUi cmdbUi) {
		return R.status(cmdbUiService.saveOrUpdate(cmdbUi));
	}


	/**
	 * 删除 前端属性/配置项配置表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(cmdbUiService.deleteLogic(Func.toLongList(ids)));
	}


}
