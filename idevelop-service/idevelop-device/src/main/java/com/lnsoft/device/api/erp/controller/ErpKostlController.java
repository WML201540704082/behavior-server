package com.lnsoft.device.api.erp.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.secure.annotation.PreAuth;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.erp.entity.ErpKostl;
import com.lnsoft.device.api.erp.service.IErpKostlService;
import com.lnsoft.device.api.erp.vo.ErpKostlVO;
import com.lnsoft.device.api.erp.vo.ErpMaintainKostlVO;
import com.lnsoft.device.api.erp.vo.ErpXtythMaintainVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * erp成本中心 控制器
 *
 * @author Idevelop
 * @since 2024-04-02
 */
@RestController
@AllArgsConstructor
@RequestMapping("/erpkostl")
@Api(value = "ERP成本中心", tags = "ERP成本中心接口")
public class ErpKostlController extends IdevelopController {

	private IErpKostlService erpKostlService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "详情", notes = "传入erpKostl")
	public R<ErpKostl> detail(ErpKostl erpKostl) {
		ErpKostl detail = erpKostlService.getOne(Condition.getQueryWrapper(erpKostl));
		return R.data(detail);
	}

	/**
	 * 分页 erp成本中心
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "分页", notes = "传入erpKostl")
	public R<IPage<ErpKostl>> list(ErpKostl erpKostl, Query query) {
		IPage<ErpKostl> pages = erpKostlService.page(Condition.getPage(query), Condition.getQueryWrapper(erpKostl));
		return R.data(pages);
	}

	/**
	 * 自定义分页 erp成本中心
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "分页", notes = "传入erpKostl")
	public R<IPage<ErpKostlVO>> page(ErpKostlVO erpKostl, Query query) {
		IPage<ErpKostlVO> pages = erpKostlService.selectErpKostlPage(Condition.getPage(query), erpKostl);
		return R.data(pages);
	}

	/**
	 * 新增 erp成本中心
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增", notes = "传入erpKostl")
	public R save(@Valid @RequestBody ErpKostl erpKostl) {
		return R.status(erpKostlService.save(erpKostl));
	}

	/**
	 * 修改 erp成本中心
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "修改", notes = "传入erpKostl")
	public R update(@Valid @RequestBody ErpKostl erpKostl) {
		return R.status(erpKostlService.updateById(erpKostl));
	}

	/**
	 * 新增或修改 erp成本中心
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增或修改", notes = "传入erpKostl")
	public R submit(@Valid @RequestBody ErpKostl erpKostl) {
		return R.status(erpKostlService.saveOrUpdate(erpKostl));
	}

	/**
	 * 同步ERP成本中心
	 */
	@GetMapping("/synchronous/refresh")
	@ApiOperationSupport(order = 6)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "同步ERP成本中心", notes = "传入成本中心")
	public R synchronousRefresh(String swerk) {
		return R.status(erpKostlService.synchronousRefresh(swerk));
	}


	/**
	 * 删除 erp成本中心
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(erpKostlService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 懒加载 ERP维护工厂(对应单位)
	 */
	@GetMapping("/lazy/maintain")
	@ApiOperationSupport(order = 8)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "懒加载 ERP维护工厂(对应单位)")
	public R<List<ErpXtythMaintainVO>> lazyMaintain() {
		List<ErpXtythMaintainVO> erpMaintainKostlList = erpKostlService.lazyMaintain();
		return R.data(erpMaintainKostlList);
	}

	/**
	 * 懒加载 ERP维护工厂(对应单位)和成本中心
	 */
	@GetMapping("/lazy/maintain/kostl")
	@ApiOperationSupport(order = 9)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "懒加载 ERP维护工厂(对应单位)和成本中心", notes = "传入unitCode")
	public R<ErpMaintainKostlVO> lazyMaintainKostl(String unitCode) {
		ErpMaintainKostlVO erpMaintainKostlList = erpKostlService.lazyMaintainKostl(unitCode);
		return R.data(erpMaintainKostlList);
	}

}
