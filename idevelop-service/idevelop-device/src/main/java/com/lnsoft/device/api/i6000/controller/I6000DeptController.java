package com.lnsoft.device.api.i6000.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.secure.annotation.PreAuth;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.i6000.entity.I6000Dept;
import com.lnsoft.device.api.i6000.service.II6000DeptService;
import com.lnsoft.device.api.i6000.vo.I6000DeptVO;
import com.lnsoft.device.api.i6000.vo.I6000UnitDeptVO;
import com.lnsoft.device.api.i6000.vo.I6000XtythUnitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * I6000部门 控制器
 *
 * @author Idevelop
 * @since 2024-04-12
 */
@RestController
@AllArgsConstructor
@RequestMapping("/i6000dept")
@Api(value = "I6000部门", tags = "I6000部门接口")
public class I6000DeptController extends IdevelopController {

	private II6000DeptService i6000DeptService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "详情", notes = "传入i6000Dept")
	public R<I6000Dept> detail(I6000Dept i6000Dept) {
		I6000Dept detail = i6000DeptService.getOne(Condition.getQueryWrapper(i6000Dept));
		return R.data(detail);
	}

	/**
	 * 分页 I6000部门
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "分页", notes = "传入i6000Dept")
	public R<IPage<I6000Dept>> list(I6000Dept i6000Dept, Query query) {
		IPage<I6000Dept> pages = i6000DeptService.page(Condition.getPage(query), Condition.getQueryWrapper(i6000Dept));
		return R.data(pages);
	}

	/**
	 * 自定义分页 I6000部门
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "分页", notes = "传入i6000Dept")
	public R<IPage<I6000DeptVO>> page(I6000DeptVO i6000Dept, Query query) {
		IPage<I6000DeptVO> pages = i6000DeptService.selectI6000DeptPage(Condition.getPage(query), i6000Dept);
		return R.data(pages);
	}

	/**
	 * 新增 I6000部门
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增 I6000部门 8.1", notes = "传入i6000Dept")
	public R save() {
		return i6000DeptService.insert();
	}

	/**
	 * 匹配部门
	 */
	@PostMapping("/marry")
	@ApiOperationSupport(order = 10)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "匹配部门", notes = "")
	public R marry() {
		return i6000DeptService.marry();
	}

	/**
	 * 修改 I6000部门
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "修改", notes = "传入i6000Dept")
	public R update(@Valid @RequestBody I6000Dept i6000Dept) {
		return R.status(i6000DeptService.updateById(i6000Dept));
	}

	/**
	 * 新增或修改 I6000部门
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增或修改", notes = "传入i6000Dept")
	public R submit(@Valid @RequestBody I6000Dept i6000Dept) {
		return R.status(i6000DeptService.saveOrUpdate(i6000Dept));
	}


	/**
	 * 删除 I6000部门
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(i6000DeptService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 懒加载 I6000单位
	 */
	@GetMapping("/lazy/unit")
	@ApiOperationSupport(order = 8)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "懒加载 I6000单位", notes = "")
	public R<List<I6000XtythUnitVO>> lazyI6000Unit() {
		List<I6000XtythUnitVO> i6000UnitDeptVOS = i6000DeptService.lazyI6000Unit();
		return R.data(i6000UnitDeptVOS);
	}

	/**
	 * 懒加载 I6000单位和部门
	 */
	@GetMapping("/lazy/unit/dept")
	@ApiOperationSupport(order = 8)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "懒加载 I6000单位和部门", notes = "传入unitCode")
	public R<I6000UnitDeptVO> lazyI6000UnitDept(String unitCode) {
		I6000UnitDeptVO i6000UnitDeptVOS = i6000DeptService.lazyI6000UnitDept(unitCode);
		return R.data(i6000UnitDeptVOS);
	}

}
