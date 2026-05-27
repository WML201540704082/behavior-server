package com.lnsoft.device.api.erp.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.secure.annotation.PreAuth;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.erp.entity.ErpMaintain;
import com.lnsoft.device.api.erp.service.IErpMaintainService;
import com.lnsoft.device.api.erp.vo.ErpMaintainVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * erp维护工厂(对应单位) 控制器
 *
 * @author Idevelop
 * @since 2024-03-22
 */
@RestController
@AllArgsConstructor
@RequestMapping("/erpmaintain")
@Api(value = "ERP维护工厂(对应单位)", tags = "ERP维护工厂(对应单位)接口")
public class ErpMaintainController extends IdevelopController {

	private IErpMaintainService erpMaintainService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入erpMaintain")
	public R<ErpMaintain> detail(ErpMaintain erpMaintain) {
		ErpMaintain detail = erpMaintainService.getOne(Condition.getQueryWrapper(erpMaintain));
		return R.data(detail);
	}

	/**
	 * 分页 erp维护工厂(对应单位)
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入erpMaintain")
	public R<IPage<ErpMaintain>> list(ErpMaintain erpMaintain, Query query) {
		IPage<ErpMaintain> pages = erpMaintainService.page(Condition.getPage(query), Condition.getQueryWrapper(erpMaintain));
		return R.data(pages);
	}

	/**
	 * 根据维护工厂编码获取市县维护工厂数据
	 */
	@GetMapping("/swerk")
	public R<List<ErpMaintain>> getSwerk(ErpMaintain erpMaintain) {
		return R.data(erpMaintainService.getSwerk(erpMaintain));
	}

	/**
	 * 自定义分页 erp维护工厂(对应单位)
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入erpMaintain")
	public R<IPage<ErpMaintainVO>> page(ErpMaintainVO erpMaintain, Query query) {
		IPage<ErpMaintainVO> pages = erpMaintainService.selectErpMaintainPage(Condition.getPage(query), erpMaintain);
		return R.data(pages);
	}

	/**
	 * 新增 erp维护工厂(对应单位)
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增", notes = "传入erpMaintain")
	public R save(@Valid @RequestBody ErpMaintain erpMaintain) {
		return R.status(erpMaintainService.save(erpMaintain));
	}

	/**
	 * 修改 erp维护工厂(对应单位)
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "修改", notes = "传入erpMaintain")
	public R update(@Valid @RequestBody ErpMaintain erpMaintain) {
		return R.status(erpMaintainService.updateById(erpMaintain));
	}

	/**
	 * 新增或修改 erp维护工厂(对应单位)
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增或修改", notes = "传入erpMaintain")
	public R submit(@Valid @RequestBody ErpMaintain erpMaintain) {
		return R.status(erpMaintainService.saveOrUpdate(erpMaintain));
	}


	/**
	 * 删除 erp维护工厂(对应单位)
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(erpMaintainService.deleteLogic(Func.toLongList(ids)));
	}

}
