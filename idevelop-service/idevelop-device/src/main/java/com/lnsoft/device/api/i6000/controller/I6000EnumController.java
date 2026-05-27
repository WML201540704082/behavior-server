package com.lnsoft.device.api.i6000.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.secure.annotation.PreAuth;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.i6000.entity.I6000Enum;
import com.lnsoft.device.api.i6000.enums.I6000EnumEnum;
import com.lnsoft.device.api.i6000.service.II6000EnumService;
import com.lnsoft.device.api.i6000.vo.I6000EnumVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * i6000枚举数据表 控制器
 *
 * @author Idevelop
 * @since 2024-08-02
 */
@RestController
@AllArgsConstructor
@RequestMapping("/i6000enum")
@Api(value = "i6000枚举数据表", tags = "i6000枚举数据表接口")
public class I6000EnumController extends IdevelopController {

	private II6000EnumService i6000EnumService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "详情", notes = "传入i6000Enum")
	public R<I6000Enum> detail(I6000Enum i6000Enum) {
		I6000Enum detail = i6000EnumService.getOne(Condition.getQueryWrapper(i6000Enum));
		return R.data(detail);
	}

	/**
	 * 分页 i6000枚举数据表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "分页", notes = "传入i6000Enum")
	public R<IPage<I6000Enum>> list(I6000Enum i6000Enum, Query query) {
		IPage<I6000Enum> pages = i6000EnumService.page(Condition.getPage(query), Condition.getQueryWrapper(i6000Enum));
		return R.data(pages);
	}

	/**
	 * 自定义分页 i6000枚举数据表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "分页", notes = "传入i6000Enum")
	public R<IPage<I6000EnumVO>> page(I6000EnumVO i6000Enum, Query query) {
		IPage<I6000EnumVO> pages = i6000EnumService.selectI6000EnumPage(Condition.getPage(query), i6000Enum);
		return R.data(pages);
	}

	/**
	 * 新增 i6000枚举数据表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增", notes = "传入i6000Enum")
	public R save(@Valid @RequestBody I6000Enum i6000Enum) {

		return i6000EnumService.insert(i6000Enum);
	}

	/**
	 * 修改 i6000枚举数据表
	 */
	@PostMapping("/update")
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入i6000Enum")
	public R update(@Valid @RequestBody I6000Enum i6000Enum) {
		return R.status(i6000EnumService.updateById(i6000Enum));
	}

	/**
	 * 新增或修改 i6000枚举数据表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入i6000Enum")
	public R submit(@Valid @RequestBody I6000Enum i6000Enum) {
		return R.status(i6000EnumService.saveOrUpdate(i6000Enum));
	}


	/**
	 * 删除 i6000枚举数据表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(i6000EnumService.deleteLogic(Func.toLongList(ids)));
	}
	/**
	 * 枚举返回列表
	 */
	@GetMapping("/enums")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "枚举返回列表", notes = "")
	public R enums() {
		List<Map<String, String>> maps = I6000EnumEnum.toList();
		return R.data(maps);
	}

}
