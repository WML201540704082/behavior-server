package com.lnsoft.device.api.i6000.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.i6000.dto.I6000InfoDTO;
import com.lnsoft.device.api.i6000.entity.I6000Info;
import com.lnsoft.device.api.i6000.service.II6000InfoService;
import com.lnsoft.device.api.i6000.vo.I6000InfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用于根据条件获取I6000信息插入CMDB模块 控制器
 *
 * @author Idevelop
 * @since 2024-05-18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/i6000info")
@Api(value = "用于根据条件获取I6000信息插入CMDB模块", tags = "用于根据条件获取I6000信息插入CMDB模块接口")
public class I6000InfoController extends IdevelopController {

	private II6000InfoService i6000InfoService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入i6000Info")
	public R<I6000Info> detail(I6000Info i6000Info) {
		I6000Info detail = i6000InfoService.getOne(Condition.getQueryWrapper(i6000Info));
		return R.data(detail);
	}

	/**
	 * 分页 用于根据条件获取I6000信息插入CMDB模块
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入i6000Info")
	public R<IPage<I6000Info>> list(I6000Info i6000Info, Query query) {
		IPage<I6000Info> pages = i6000InfoService.page(Condition.getPage(query), Condition.getQueryWrapper(i6000Info));
		return R.data(pages);
	}

	/**
	 * 自定义分页 用于根据条件获取I6000信息插入CMDB模块
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入i6000Info")
	public R<IPage<I6000InfoVO>> page(I6000InfoVO i6000Info, Query query) {
		IPage<I6000InfoVO> pages = i6000InfoService.selectI6000InfoPage(Condition.getPage(query), i6000Info);
		return R.data(pages);
	}

	/**
	 * 新增 用于根据条件获取I6000信息插入CMDB模块
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入i6000Info")
	public R save(@Valid @RequestBody I6000InfoDTO i6000InfoDTO) {
		return R.status(i6000InfoService.saveNew(i6000InfoDTO));
	}

	/**
	 * 修改 用于根据条件获取I6000信息插入CMDB模块
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入i6000Info")
	public R update(@Valid @RequestBody I6000Info i6000Info) {
		return R.status(i6000InfoService.updateById(i6000Info));
	}

	/**
	 * 新增或修改 用于根据条件获取I6000信息插入CMDB模块
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入i6000Info")
	public R submit(@Valid @RequestBody I6000Info i6000Info) {
		return R.status(i6000InfoService.saveOrUpdate(i6000Info));
	}


	/**
	 * 删除 用于根据条件获取I6000信息插入CMDB模块
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(i6000InfoService.removeByIds(Func.toLongList(ids)));
	}


}
