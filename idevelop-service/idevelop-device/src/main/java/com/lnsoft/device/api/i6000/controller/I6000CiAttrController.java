package com.lnsoft.device.api.i6000.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.i6000.dto.I6000CmdbMappingDTO;
import com.lnsoft.device.api.i6000.dto.I6000ReqManualDTO;
import com.lnsoft.device.api.i6000.entity.I6000CiAttr;
import com.lnsoft.device.api.i6000.service.II6000CiAttrService;
import com.lnsoft.device.api.i6000.vo.I6000CiAttrVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * i6000模型属性 控制器
 *
 * @author Idevelop
 * @since 2024-03-19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/i6000ciattr")
@Api(value = "i6000模型属性", tags = "i6000模型属性接口")
public class I6000CiAttrController extends IdevelopController {

	private II6000CiAttrService i6000CiAttrService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入i6000CiAttr")
	public R<I6000CiAttr> detail(I6000CiAttr i6000CiAttr) {
		I6000CiAttr detail = i6000CiAttrService.getOne(Condition.getQueryWrapper(i6000CiAttr));
		return R.data(detail);
	}

	/**
	 * 分页 i6000模型属性
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入i6000CiAttr")
	public R<IPage<I6000CiAttr>> list(I6000CiAttr i6000CiAttr, Query query) {
		IPage<I6000CiAttr> pages = i6000CiAttrService.page(Condition.getPage(query), Condition.getQueryWrapper(i6000CiAttr));
		return R.data(pages);
	}

	/**
	 * 自定义分页 i6000模型属性
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入i6000CiAttr")
	public R<IPage<I6000CiAttrVO>> page(I6000CiAttrVO i6000CiAttr, Query query) {
		IPage<I6000CiAttrVO> pages = i6000CiAttrService.selectI6000CiAttrPage(Condition.getPage(query), i6000CiAttr);
		return R.data(pages);
	}

	/**
	 * 新增 i6000模型属性
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入i6000CiAttr")
	public R save(@Valid @RequestBody I6000ReqManualDTO i6000ReqManualDTO) {
		boolean save = i6000CiAttrService.saveManual(i6000ReqManualDTO);
		return R.status(save);
	}

	/**
	 * 修改 i6000模型属性
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入i6000CiAttr")
	public R update(@Valid @RequestBody I6000CiAttr i6000CiAttr) {
		return R.status(i6000CiAttrService.updateById(i6000CiAttr));
	}

	/**
	 * 新增或修改 i6000模型属性
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入i6000CiAttr")
	public R submit(@Valid @RequestBody I6000CiAttr i6000CiAttr) {
		return R.status(i6000CiAttrService.saveOrUpdate(i6000CiAttr));
	}


	/**
	 * 删除 i6000模型属性
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(i6000CiAttrService.deleteLogic(Func.toLongList(ids)));
	}
	/**
	 * 获取并更新i6000模型属性
	 */
	@PostMapping("/refresh")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "获取并更新i6000模型属性", notes = "")
	public R refresh(@Valid @RequestBody I6000CiAttr i6000CiAttr) {
		return i6000CiAttrService.refresh(i6000CiAttr);
	}


}
