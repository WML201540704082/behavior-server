package com.lnsoft.device.api.erp.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.asset.dto.ProjectManagerDTO;
import com.lnsoft.device.api.asset.entity.ProjectManager;
import com.lnsoft.device.api.erp.dto.ErpTranstplnrDTO;
import com.lnsoft.device.api.erp.entity.ErpPersonAuth;
import com.lnsoft.device.api.erp.entity.ErpTransEqunr;
import com.lnsoft.device.api.erp.entity.ErpTransZcbf;
import com.lnsoft.device.api.erp.entity.ErpUpdateAnlnr;
import com.lnsoft.device.api.erp.response.ErpPersonAuthResp;
import com.lnsoft.device.api.erp.response.ErpTransEqunrResp;
import com.lnsoft.device.api.erp.response.ErpTransZcbfResp;
import com.lnsoft.device.api.erp.service.IErpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * i6000模型属性 控制器
 *
 * @author Idevelop
 * @since 2024-03-19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/erp")
@Api(value = "ERP主要接口", tags = "ERP主要接口")
public class ErpController extends IdevelopController {

	private IErpService erpService;

	/**
	 * 成本中心基础数据(实物保管部门,使用保管部门)  xtyth => erp
	 */
	@GetMapping("/get/kostl")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "成本中心", notes = "传入erp单位编码")
	public R<List<Map<String, String>>> getKostl(String maintainCode) {
		List<Map<String, String>> zfiFmXtMain = erpService.getKostl(maintainCode);
		return R.data(zfiFmXtMain);
	}


	/**
	 * 功能位置主数据接口 xtyth => erp
	 */
	@PostMapping("/trans/tplnr")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "功能位置主数据接口", notes = "传入 功能位置数据")
	public R<Map<String, String>> transTplnr(@Valid @RequestBody ErpTranstplnrDTO erpTranstplnrDTO) {
		Map<String, String> zfiFmXtMain = erpService.transTplnr(erpTranstplnrDTO);
		return R.data(zfiFmXtMain);
	}

	/**
	 * 获取WBS基础数据 xtyth => erp
	 */
	@GetMapping("/get/wbs")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "获取WBS基础数据接口", notes = "传入 WBS基础数据")
	public R<List<ProjectManager>> getWbs(ProjectManagerDTO projectManagerDTO) {
		List<ProjectManager> zfiFmXtMain = erpService.getWbs(projectManagerDTO);
		return R.data(zfiFmXtMain);
	}


	/**
	 * 设备台账主数据同步接口 实例代码,仅供参考 xtyth => erp
	 */
	@PostMapping("/trans/equnr")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "设备台账主数据同步接口", notes = "传入 WBS基础数据")
	public R<ErpTransEqunrResp> transEqunr(@Valid @RequestBody ErpTransEqunr erpTransEqunr) {
		ErpTransEqunrResp zfiFmXtMain = erpService.transEqunr(erpTransEqunr);
		return R.data(zfiFmXtMain);
	}

	/**
	 * 资产报废集成接口 xtyth => erp
	 */
	@PostMapping("/trans/zcbf")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "资产报废集成接口", notes = "传入 erpTransZcbf")
	public R<ErpTransZcbfResp> transEqunr1(@Valid @RequestBody ErpTransZcbf erpTransZcbf) {
		ErpTransZcbfResp zfiFmXtMain = erpService.transZcbf(erpTransZcbf);
		return R.data(zfiFmXtMain);
	}

	/**
	 * 获取人员权限 xtyth => erp
	 */
	@GetMapping("/person/auth")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "获取人员权限", notes = "传入 成本中心")
	public R<ErpPersonAuthResp> personAuth(ErpPersonAuth erpPersonAuth) {
		ErpPersonAuthResp zfiFmXtMain = erpService.personAuth(erpPersonAuth);
		return R.data(zfiFmXtMain);
	}

	/**
	 * ERP调用修改ERP资产编码 erp => xtyth
	 */
	@PostMapping("/update/anlnr")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "ERP调用修改ERP资产编码", notes = "传入 erpUpdateAnlnr")
	public R<String> updateAnlnr(@Valid @RequestBody ErpUpdateAnlnr erpUpdateAnlnr) {
		String result = erpService.updateAnlnr(erpUpdateAnlnr);
		return R.data(result);
	}


}
