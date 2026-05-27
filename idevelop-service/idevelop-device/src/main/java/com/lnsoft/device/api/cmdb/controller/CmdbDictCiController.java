package com.lnsoft.device.api.cmdb.controller;

import com.lnsoft.device.api.cmdb.service.ICmdbDictCiService;
import com.lnsoft.device.entity.CmdbDictCi;
import com.lnsoft.device.vo.CmdbDictCiVO;
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
import com.lnsoft.core.boot.ctrl.IdevelopController;

/**
 * cmdb字典模型管理 控制器
 *
 * @author Idevelop
 * @since 2024-07-02
 */
@RestController
@AllArgsConstructor
@RequestMapping("/cmdbdictci")
@Api(value = "cmdb字典模型管理", tags = "cmdb字典模型管理接口")
public class CmdbDictCiController extends IdevelopController {

	private ICmdbDictCiService cmdbDictCiService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入cmdbDictCi")
	public R<CmdbDictCi> detail(CmdbDictCi cmdbDictCi) {
		CmdbDictCi detail = cmdbDictCiService.getOne(Condition.getQueryWrapper(cmdbDictCi));
		return R.data(detail);
	}

	/**
	 * 分页 cmdb字典模型管理
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入cmdbDictCi")
	public R<IPage<CmdbDictCi>> list(CmdbDictCi cmdbDictCi, Query query) {
		IPage<CmdbDictCi> pages = cmdbDictCiService.page(Condition.getPage(query), Condition.getQueryWrapper(cmdbDictCi));
		return R.data(pages);
	}

	/**
	 * 自定义分页 cmdb字典模型管理
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入cmdbDictCi")
	public R<IPage<CmdbDictCiVO>> page(CmdbDictCiVO cmdbDictCi, Query query) {
		IPage<CmdbDictCiVO> pages = cmdbDictCiService.selectCmdbDictCiPage(Condition.getPage(query), cmdbDictCi);
		return R.data(pages);
	}

	/**
	 * 新增 cmdb字典模型管理
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入cmdbDictCi")
	public R save(@Valid @RequestBody CmdbDictCi cmdbDictCi) {
		return R.status(cmdbDictCiService.save(cmdbDictCi));
	}

	/**
	 * 修改 cmdb字典模型管理
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入cmdbDictCi")
	public R update(@Valid @RequestBody CmdbDictCi cmdbDictCi) {
		return R.status(cmdbDictCiService.updateById(cmdbDictCi));
	}

	/**
	 * 新增或修改 cmdb字典模型管理
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入cmdbDictCi")
	public R submit(@Valid @RequestBody CmdbDictCi cmdbDictCi) {
		return R.status(cmdbDictCiService.saveOrUpdate(cmdbDictCi));
	}


	/**
	 * 删除 cmdb字典模型管理
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(cmdbDictCiService.deleteLogic(Func.toLongList(ids)));
	}


}
