package com.lnsoft.device.api.erp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.erp.dto.ErpTranstplnrDTO;
import com.lnsoft.device.api.erp.entity.ErpTranstplnr;
import com.lnsoft.device.api.erp.service.IErpTranstplnrService;
import com.lnsoft.device.api.erp.vo.ErpTranstplnrVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * ERP功能位置 控制器
 *
 * @author Idevelop
 * @since 2024-03-23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/erptranstplnr")
@Api(value = "ERP功能位置", tags = "ERP功能位置接口")
public class ErpTranstplnrController extends IdevelopController {

	private IErpTranstplnrService erpTranstplnrService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入erpTranstplnr")
	public R<ErpTranstplnr> detail(ErpTranstplnr erpTranstplnr) {
		ErpTranstplnr detail = erpTranstplnrService.getDetail(erpTranstplnr);
		return R.data(detail);
	}

	/**
	 * 分页 ERP功能位置
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入erpTranstplnr")
	public R<IPage<ErpTranstplnr>> list(ErpTranstplnr erpTranstplnr, Query query) {
		IdevelopUser sysUser = SecureUtil.getUser();
		if (!StringUtils.hasLength(erpTranstplnr.getSwerk()) && !StringUtils.pathEquals(sysUser.getRegionCode(), "37")) {
			throw new ServiceException("需要当前用户维护工厂编码");
		}
		QueryWrapper<ErpTranstplnr> queryWrapper = Condition.getQueryWrapper(erpTranstplnr);
		queryWrapper.lambda().eq(ErpTranstplnr::getOperation, "C").or().eq(ErpTranstplnr::getOperation, "M");
		IPage<ErpTranstplnr> pages = erpTranstplnrService.page(Condition.getPage(query), queryWrapper);
		return R.data(pages);
	}

	/**
	 * 自定义分页 ERP功能位置
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入erpTranstplnr")
	public R<IPage<ErpTranstplnrVO>> page(ErpTranstplnrVO erpTranstplnr, Query query) {
		IPage<ErpTranstplnrVO> pages = erpTranstplnrService.selectErpTranstplnrPage(Condition.getPage(query), erpTranstplnr);
		return R.data(pages);
	}

	/**
	 * 新增或修改 ERP功能位置
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入erpTranstplnr")
	public R<Map<String, String>> submit(@Valid @RequestBody ErpTranstplnrDTO erpTranstplnrDTO) {
		Map<String, String> returnStr = erpTranstplnrService.saveOrUpdateNew(erpTranstplnrDTO);
		return R.data(returnStr);
	}


	/**
	 * 删除 ERP功能位置
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		List<String> idList = Func.toStrList(ids);
		erpTranstplnrService.deleteByTrlnr(idList);
		return R.success("删除成功");
	}


	/**
	 * 随机生成功能位置编码
	 */
	@GetMapping("/build/code")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "随机生成功能位置编码", notes = "传入swerk")
	public R<String> buildCode(@ApiParam(value = "swerk", required = true) @RequestParam String swerk) {
		String code = erpTranstplnrService.buildCode(swerk);
		return R.success(code);
	}

}
