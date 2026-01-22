
package com.lnsoft.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.secure.annotation.PreAuth;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.CacheUtil;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.system.entity.DataScope;
import com.lnsoft.system.service.IDataScopeService;
import com.lnsoft.system.vo.DataScopeVO;
import com.lnsoft.system.wrapper.DataScopeWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.lnsoft.core.tool.utils.CacheUtil.SYS_CACHE;

/**
 * 数据权限控制器
 *
 * @author IdevelopX
 */
@RestController
@AllArgsConstructor
@RequestMapping("/data-scope")
@Api(value = "数据权限", tags = "数据权限")
public class DataScopeController extends IdevelopController {

	private final IDataScopeService dataScopeService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "详情", notes = "传入dataScope")
	public R<DataScope> detail(DataScope dataScope) {
		DataScope detail = dataScopeService.getOne(Condition.getQueryWrapper(dataScope));
		return R.data(detail);
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "分页", notes = "传入dataScope")
	public R<IPage<DataScopeVO>> list(DataScope dataScope, Query query) {
		IPage<DataScope> pages = dataScopeService.page(Condition.getPage(query), Condition.getQueryWrapper(dataScope));
		return R.data(DataScopeWrapper.build().pageVO(pages));
	}

	/**
	 * 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增", notes = "传入dataScope")
	public R save(@Valid @RequestBody DataScope dataScope) {
		CacheUtil.clear(SYS_CACHE);
		return R.status(dataScopeService.save(dataScope));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "修改", notes = "传入dataScope")
	public R update(@Valid @RequestBody DataScope dataScope) {
		CacheUtil.clear(SYS_CACHE);
		return R.status(dataScopeService.updateById(dataScope));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增或修改", notes = "传入dataScope")
	public R submit(@Valid @RequestBody DataScope dataScope) {
		CacheUtil.clear(SYS_CACHE);
		return R.status(dataScopeService.saveOrUpdate(dataScope));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		CacheUtil.clear(SYS_CACHE);
		return R.status(dataScopeService.deleteLogic(Func.toLongList(ids)));
	}

}
