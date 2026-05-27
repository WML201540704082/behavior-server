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
import com.lnsoft.device.api.cmdb.service.ICmdbCiAttrGradeService;
import com.lnsoft.device.api.cmdb.service.IHardwareBasicTreeService;
import com.lnsoft.device.api.cmdb.vo.CmdbCiAttrGradeVO;
import com.lnsoft.device.entity.CmdbCiAttrGrade;
import com.lnsoft.device.entity.HardwareBasicTree;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 模型属性映射表(编辑) 控制器
 *
 * @author Idevelop
 * @since 2024-03-14
 */
@RestController
@AllArgsConstructor
@RequestMapping("/cmdbciattrgrade")
@Api(value = "模型属性映射表(编辑)", tags = "模型属性映射表(编辑)接口")
public class CmdbCiAttrGradeController extends IdevelopController {

	private ICmdbCiAttrGradeService cmdbCiAttrGradeService;
	private IHardwareBasicTreeService hardwareBasicTreeService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "详情", notes = "传入cmdbCiAttrGrade")
	public R<CmdbCiAttrGrade> detail(CmdbCiAttrGrade cmdbCiAttrGrade) {
		CmdbCiAttrGrade detail = cmdbCiAttrGradeService.getOne(Condition.getQueryWrapper(cmdbCiAttrGrade));
		return R.data(detail);
	}

	/**
	 * 分页 模型属性映射表(编辑)
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "分页", notes = "传入cmdbCiAttrGrade")
	public R<IPage<CmdbCiAttrGrade>> list(CmdbCiAttrGrade cmdbCiAttrGrade, Query query) {
		CmdbCiAttrGrade cmdbCiAttrGradeQuery = new CmdbCiAttrGrade();

		QueryWrapper<CmdbCiAttrGrade> queryWrapper = Condition.getQueryWrapper(cmdbCiAttrGradeQuery);
		queryWrapper.lambda().like(CmdbCiAttrGrade::getAttrCiId, cmdbCiAttrGrade.getAttrCiId() + "-")
			.orderByDesc(CmdbCiAttrGrade::getId);
		if (StringUtils.isNotEmpty(cmdbCiAttrGrade.getLabel())) {
			queryWrapper.lambda().like(CmdbCiAttrGrade::getLabel, cmdbCiAttrGrade.getLabel());
		}
		if (StringUtils.isNotEmpty(cmdbCiAttrGrade.getName())) {
			queryWrapper.lambda().like(CmdbCiAttrGrade::getName, cmdbCiAttrGrade.getName());
		}

		IPage<CmdbCiAttrGrade> pages = cmdbCiAttrGradeService.page(Condition.getPage(query), queryWrapper);
		return R.data(pages);
	}

	/**
	 * 自定义分页 模型属性映射表(编辑)
	 */
	@GetMapping("/page")
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入cmdbCiAttrGrade")
	public R<IPage<CmdbCiAttrGradeVO>> page(CmdbCiAttrGradeVO cmdbCiAttrGrade, Query query) {
		IPage<CmdbCiAttrGradeVO> pages = cmdbCiAttrGradeService.selectCmdbCiAttrGradePage(Condition.getPage(query), cmdbCiAttrGrade);
		return R.data(pages);
	}

	/**
	 * 新增 模型属性映射表(编辑)
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增", notes = "传入cmdbCiAttrGrade")
	public R save(@Valid @RequestBody CmdbCiAttrGrade cmdbCiAttrGrade) {
		return R.status(cmdbCiAttrGradeService.save(cmdbCiAttrGrade));
	}

	/**
	 * 修改 模型属性映射表(编辑)
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "修改", notes = "传入cmdbCiAttrGrade")
	public R update(@Valid @RequestBody CmdbCiAttrGrade cmdbCiAttrGrade) {
		return R.status(cmdbCiAttrGradeService.updateById(cmdbCiAttrGrade));
	}

	/**
	 * 新增或修改 模型属性映射表(编辑)
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增或修改", notes = "传入cmdbCiAttrGrade")
	public R submit(@Valid @RequestBody CmdbCiAttrGrade cmdbCiAttrGrade) {
		return R.status(cmdbCiAttrGradeService.saveOrUpdate(cmdbCiAttrGrade));
	}


	/**
	 * 删除 模型属性映射表(编辑)
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(cmdbCiAttrGradeService.deleteLogicNew(Func.toStrList(ids)));
	}

	/**
	 * 刷新 模型属性映射表
	 */
	@GetMapping("/refresh")
	@ApiOperationSupport(order = 8)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "刷新 模型属性映射表")
	public R<String> refreshCiAttr(@ApiParam(value = "模型ID") Long ciId,
								   @ApiParam(value = "模型名称") String ciName) {

		String refresh = cmdbCiAttrGradeService.refreshCiAttr(ciId, ciName);
		return R.data(refresh);
	}

	/**
	 * 刷新 模型属性映射表 临时
	 */
	@GetMapping("/refresh/ls")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "临时")
	public R<String> refreshCiAttrLs(Long deviceClaccify) {

		String refresh = cmdbCiAttrGradeService.refreshCiAttrLs(deviceClaccify);
		return R.data(refresh);
	}
	/**
	 * 获取 模型属性映射表字段 (台账导出用)
	 */
	@GetMapping("/getAttr")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "获取 模型属性映射表字段")
	public R<List<CmdbCiAttrGrade>> getAttr(CmdbCiAttrGrade cmdbCiAttrGrade){
		CmdbCiAttrGrade cmdbCiAttrGradeQuery = new CmdbCiAttrGrade();

		String attrCiId = cmdbCiAttrGrade.getAttrCiId();
		HardwareBasicTree hardwareBasicTree = new HardwareBasicTree();
		hardwareBasicTree.setCiId(Long.valueOf(cmdbCiAttrGrade.getAttrCiId()));
		HardwareBasicTree hardwareBasicTreeOne = hardwareBasicTreeService.getOne(Condition.getQueryWrapper(hardwareBasicTree));
		String isUpdate = hardwareBasicTreeOne.getIsUpdate();
		if (StringUtils.equals("0", isUpdate)) {
			attrCiId = String.valueOf(hardwareBasicTreeOne.getParentCiId());
		}

		QueryWrapper<CmdbCiAttrGrade> queryWrapper = Condition.getQueryWrapper(cmdbCiAttrGradeQuery);
		queryWrapper.lambda().like(CmdbCiAttrGrade::getAttrCiId, attrCiId + "-").eq(CmdbCiAttrGrade::getIsExport,1)
			.orderByDesc(CmdbCiAttrGrade::getId);
		if (StringUtils.isNotEmpty(cmdbCiAttrGrade.getLabel())) {
			queryWrapper.lambda().like(CmdbCiAttrGrade::getLabel, cmdbCiAttrGrade.getLabel());
		}
		if (StringUtils.isNotEmpty(cmdbCiAttrGrade.getName())) {
			queryWrapper.lambda().like(CmdbCiAttrGrade::getName, cmdbCiAttrGrade.getName());
		}

		return R.data(cmdbCiAttrGradeService.list(queryWrapper));
	}


}
