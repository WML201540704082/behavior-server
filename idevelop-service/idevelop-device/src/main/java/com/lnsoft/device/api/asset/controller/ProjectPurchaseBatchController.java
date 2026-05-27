package com.lnsoft.device.api.asset.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.asset.entity.ProjectPurchaseBatch;
import com.lnsoft.device.api.asset.service.IProjectPurchaseBatchService;
import com.lnsoft.device.api.asset.vo.ProjectPurchaseBatchVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 项目物料批次表 控制器
 *
 * @author Idevelop
 * @since 2024-03-04
 */
@RestController
@AllArgsConstructor
@RequestMapping("/projectpurchasebatch")
@Api(value = "项目物料批次表", tags = "项目物料批次表接口")
public class ProjectPurchaseBatchController extends IdevelopController {

	private IProjectPurchaseBatchService projectPurchaseBatchService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入projectPurchaseBatch")
	public R<ProjectPurchaseBatch> detail(ProjectPurchaseBatch projectPurchaseBatch) {
		ProjectPurchaseBatch detail = projectPurchaseBatchService.getOne(Condition.getQueryWrapper(projectPurchaseBatch));
		return R.data(detail);
	}

	/**
	 * 分页 项目物料批次表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入projectPurchaseBatch")
	public R<IPage<ProjectPurchaseBatch>> list(ProjectPurchaseBatch projectPurchaseBatch, Query query) {
		IPage<ProjectPurchaseBatch> pages = projectPurchaseBatchService.page(Condition.getPage(query), Condition.getQueryWrapper(projectPurchaseBatch));
		return R.data(pages);
	}

	/**
	 * 自定义分页 项目物料批次表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入projectPurchaseBatch")
	public R<IPage<ProjectPurchaseBatchVO>> page(ProjectPurchaseBatchVO projectPurchaseBatch, Query query) {
		IPage<ProjectPurchaseBatchVO> pages = projectPurchaseBatchService.selectProjectPurchaseBatchPage(Condition.getPage(query), projectPurchaseBatch);
		return R.data(pages);
	}

	/**
	 * 新增 项目物料批次表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入projectPurchaseBatch")
	public R save(@Valid @RequestBody ProjectPurchaseBatch projectPurchaseBatch) {
		return R.status(projectPurchaseBatchService.save(projectPurchaseBatch));
	}

	/**
	 * 修改 项目物料批次表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入projectPurchaseBatch")
	public R update(@Valid @RequestBody ProjectPurchaseBatch projectPurchaseBatch) {
		return R.status(projectPurchaseBatchService.updateById(projectPurchaseBatch));
	}

	/**
	 * 新增或修改 项目物料批次表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入projectPurchaseBatch")
	public R submit(@Valid @RequestBody ProjectPurchaseBatch projectPurchaseBatch) {
		return R.status(projectPurchaseBatchService.saveOrUpdate(projectPurchaseBatch));
	}


	/**
	 * 删除 项目物料批次表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(projectPurchaseBatchService.deleteLogic(Func.toLongList(ids)));
	}


}
