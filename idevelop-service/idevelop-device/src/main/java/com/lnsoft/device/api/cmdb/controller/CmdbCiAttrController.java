package com.lnsoft.device.api.cmdb.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.cmdb.entity.CmdbCiAttr;
import com.lnsoft.cmdb.vo.CmdbCiAttrVO;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.secure.annotation.PreAuth;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.cmdb.service.ICmdbCiAttrService;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * 模型属性映射表 控制器
 *
 * @author Idevelop
 * @since 2024-02-23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/cmdbciattr")
@Api(value = "模型属性映射表", tags = "模型属性映射表接口")
public class CmdbCiAttrController{

	private ICmdbCiAttrService cmdbCiAttrService;
	private ICmdbService iCmdbService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "详情", notes = "传入cmdbCiAttr")
	public R<CmdbCiAttr> detail(CmdbCiAttr cmdbCiAttr) {
		CmdbCiAttr detail = cmdbCiAttrService.getOne(Condition.getQueryWrapper(cmdbCiAttr));
		return R.data(detail);
	}

	/**
	 * 分页 模型属性映射表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "分页", notes = "传入cmdbCiAttr")
	public R<IPage<CmdbCiAttr>> list(CmdbCiAttr cmdbCiAttr, Query query) {
		if (Objects.isNull(cmdbCiAttr.getCiId())) {
			throw new ServiceException("模型ID不能为空!");
		}
		String attrCi = cmdbCiAttr.getCiId() + "-";
		cmdbCiAttr.setCiId(null);

		QueryWrapper<CmdbCiAttr> queryWrapper = Condition.getQueryWrapper(cmdbCiAttr);
		queryWrapper.lambda().like(CmdbCiAttr::getAttrCiId, attrCi);

		IPage<CmdbCiAttr> pages = cmdbCiAttrService.page(Condition.getPage(query), queryWrapper);
		return R.data(pages);
	}

	/**
	 * 自定义分页 模型属性映射表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "分页", notes = "传入cmdbCiAttr")
	public R<IPage<CmdbCiAttrVO>> page(CmdbCiAttrVO cmdbCiAttr, Query query) {
		IPage<CmdbCiAttrVO> pages = cmdbCiAttrService.selectCmdbCiAttrPage(Condition.getPage(query), cmdbCiAttr);
		return R.data(pages);
	}

	/**
	 * 新增 模型属性映射表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增", notes = "传入cmdbCiAttr")
	public R save(@Valid @RequestBody CmdbCiAttr cmdbCiAttr) {
		return R.status(cmdbCiAttrService.save(cmdbCiAttr));
	}

	/**
	 * 修改 模型属性映射表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "修改", notes = "传入cmdbCiAttr")
	public R update(@Valid @RequestBody CmdbCiAttr cmdbCiAttr) {
		return R.status(cmdbCiAttrService.updateById(cmdbCiAttr));
	}

	/**
	 * 新增或修改 模型属性映射表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增或修改", notes = "传入cmdbCiAttr")
	public R submit(@Valid @RequestBody CmdbCiAttr cmdbCiAttr) {
		return R.status(cmdbCiAttrService.saveOrUpdate(cmdbCiAttr));
	}


	/**
	 * 删除 模型属性映射表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(cmdbCiAttrService.deleteLogic(Func.toLongList(ids)));
	}


	/**
	 * 刷新 模型属性映射表
	 */
	@GetMapping("/refresh")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "刷新")
	@PreAuth("hasPerm('system:common:all')")
	public R<String> refreshCiAttr(@ApiParam(value = "模型ID") Long ciId,
								   @ApiParam(value = "模型名称") String ciName) {

		String refresh = cmdbCiAttrService.refreshCiAttr(ciId, ciName);
		return R.data(refresh);
	}

}
