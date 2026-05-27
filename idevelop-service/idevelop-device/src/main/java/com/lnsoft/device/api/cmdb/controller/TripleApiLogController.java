package com.lnsoft.device.api.cmdb.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.secure.annotation.PreAuth;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.cmdb.entity.TripleApiLog;
import com.lnsoft.device.api.cmdb.service.ITripleApiLogService;
import com.lnsoft.device.api.cmdb.vo.TripleApiLogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 三方系统操作日志 控制器
 *
 * @author Idevelop
 * @since 2024-03-29
 */
@RestController
@AllArgsConstructor
@RequestMapping("/tripleapilog")
@Api(value = "三方系统操作日志", tags = "三方系统操作日志接口")
public class TripleApiLogController extends IdevelopController {

	private ITripleApiLogService tripleApiLogService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "详情", notes = "传入tripleApiLog")
	public R<TripleApiLog> detail(TripleApiLog tripleApiLog) {
		TripleApiLog detail = tripleApiLogService.getOne(Condition.getQueryWrapper(tripleApiLog));
		return R.data(detail);
	}

	/**
	 * 分页 三方系统操作日志
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "分页", notes = "传入tripleApiLog")
	public R<IPage<TripleApiLog>> list(TripleApiLog tripleApiLog, Query query) {
		String text = tripleApiLog.getText();
		tripleApiLog.setText(null);
		QueryWrapper<TripleApiLog> queryWrapper = Condition.getQueryWrapper(tripleApiLog);
		queryWrapper.lambda().orderByDesc(TripleApiLog::getCreateTime);
		if (StringUtils.isNotEmpty(text)) {
			queryWrapper.lambda().like(TripleApiLog::getText, text);
		}
		IPage<TripleApiLog> pages = tripleApiLogService.page(Condition.getPage(query), queryWrapper);
		return R.data(pages);
	}

	/**
	 * 自定义分页 三方系统操作日志
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "分页", notes = "传入tripleApiLog")
	public R<IPage<TripleApiLogVO>> page(TripleApiLogVO tripleApiLog, Query query) {
		IPage<TripleApiLogVO> pages = tripleApiLogService.selectTripleApiLogPage(Condition.getPage(query), tripleApiLog);
		return R.data(pages);
	}

	/**
	 * 新增 三方系统操作日志
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增", notes = "传入tripleApiLog")
	public R save(@Valid @RequestBody TripleApiLog tripleApiLog) {
		return R.status(tripleApiLogService.save(tripleApiLog));
	}

	/**
	 * 修改 三方系统操作日志
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "修改", notes = "传入tripleApiLog")
	public R update(@Valid @RequestBody TripleApiLog tripleApiLog) {
		return R.status(tripleApiLogService.updateById(tripleApiLog));
	}

	/**
	 * 新增或修改 三方系统操作日志
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增或修改", notes = "传入tripleApiLog")
	public R submit(@Valid @RequestBody TripleApiLog tripleApiLog) {
		return R.status(tripleApiLogService.saveOrUpdate(tripleApiLog));
	}


	/**
	 * 删除 三方系统操作日志
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(tripleApiLogService.deleteLogic(Func.toLongList(ids)));
	}


}
