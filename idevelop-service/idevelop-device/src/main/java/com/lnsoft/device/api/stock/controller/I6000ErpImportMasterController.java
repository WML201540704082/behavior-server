package com.lnsoft.device.api.stock.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.stock.entity.I6000ErpImportMaster;
import com.lnsoft.device.api.stock.service.II6000ErpImportMasterService;
import com.lnsoft.device.api.stock.vo.I6000ErpImportMasterVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 管理员批量导入I6000数据记录表-主表 控制器
 *
 * @author Idevelop
 * @since 2025-11-02
 */
@RestController
@AllArgsConstructor
@RequestMapping("/i6000erpimportmaster")
@Api(value = "管理员批量导入I6000数据记录表-主表", tags = "管理员批量导入I6000数据记录表-主表接口")
public class I6000ErpImportMasterController extends IdevelopController {

	private II6000ErpImportMasterService i6000ErpImportMasterService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入i6000ErpImportMaster")
	public R<I6000ErpImportMaster> detail(I6000ErpImportMaster i6000ErpImportMaster) {
		I6000ErpImportMaster detail = i6000ErpImportMasterService.getOne(Condition.getQueryWrapper(i6000ErpImportMaster));
		return R.data(detail);
	}

	/**
	 * 分页 管理员批量导入I6000数据记录表-主表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入i6000ErpImportMaster")
	public R<IPage<I6000ErpImportMaster>> list(I6000ErpImportMaster i6000ErpImportMaster, Query query) {
		IPage<I6000ErpImportMaster> pages = i6000ErpImportMasterService.page(Condition.getPage(query), Condition.getQueryWrapper(i6000ErpImportMaster));
		return R.data(pages);
	}

	/**
	 * 自定义分页 管理员批量导入I6000数据记录表-主表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入i6000ErpImportMaster")
	public R<IPage<I6000ErpImportMasterVO>> page(I6000ErpImportMasterVO i6000ErpImportMaster, Query query) {
		IPage<I6000ErpImportMasterVO> pages = i6000ErpImportMasterService.selectI6000ErpImportMasterPage(Condition.getPage(query), i6000ErpImportMaster);
		return R.data(pages);
	}

	/**
	 * 新增 管理员批量导入I6000数据记录表-主表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入i6000ErpImportMaster")
	public R save(@Valid @RequestBody I6000ErpImportMaster i6000ErpImportMaster) {
		return R.status(i6000ErpImportMasterService.save(i6000ErpImportMaster));
	}

	/**
	 * 修改 管理员批量导入I6000数据记录表-主表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入i6000ErpImportMaster")
	public R update(@Valid @RequestBody I6000ErpImportMaster i6000ErpImportMaster) {
		return R.status(i6000ErpImportMasterService.updateById(i6000ErpImportMaster));
	}

	/**
	 * 新增或修改 管理员批量导入I6000数据记录表-主表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入i6000ErpImportMaster")
	public R submit(@Valid @RequestBody I6000ErpImportMaster i6000ErpImportMaster) {
		return R.status(i6000ErpImportMasterService.saveOrUpdate(i6000ErpImportMaster));
	}


	/**
	 * 删除 管理员批量导入I6000数据记录表-主表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(i6000ErpImportMasterService.deleteLogic(Func.toLongList(ids)));
	}


}
