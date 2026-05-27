package com.lnsoft.device.api.erp.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.entity.ZfitXtCwzt;
import com.lnsoft.device.api.erp.service.IZfitXtCwztService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  控制器
 *
 * @author Idevelop
 * @since 2024-07-29
 */
@RestController
@AllArgsConstructor
@RequestMapping("/zfitxtcwzt")
@Api(value = "", tags = "接口")
public class ZfitXtCwztController extends IdevelopController {

	private IZfitXtCwztService zfitXtCwztService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入zfitXtCwzt")
	public R<ZfitXtCwzt> detail(ZfitXtCwzt zfitXtCwzt) {
		ZfitXtCwzt xtCwzt = new ZfitXtCwzt();
		if (StringUtil.isNotBlank(zfitXtCwzt.getEqunr())){
			xtCwzt.setEqunr(zfitXtCwzt.getEqunr());
		}
		if (StringUtil.isNotBlank(zfitXtCwzt.getAnlnr())){
			xtCwzt.setAnlnr(zfitXtCwzt.getAnlnr());
		}
		ZfitXtCwzt detail = zfitXtCwztService.getOne(Condition.getQueryWrapper(xtCwzt));
		return R.data(detail);
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入zfitXtCwzt")
	public R<IPage<ZfitXtCwzt>> list(ZfitXtCwzt zfitXtCwzt, Query query) {
		IPage<ZfitXtCwzt> pages = zfitXtCwztService.page(Condition.getPage(query), Condition.getQueryWrapper(zfitXtCwzt));
		return R.data(pages);
	}
}
