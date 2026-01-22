package com.lnsoft.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.secure.annotation.PreAuth;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.system.entity.CityCode;
import com.lnsoft.system.service.ICityCodeService;
import com.lnsoft.system.vo.CityCodeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cityCode")
@Api(value = "入网认证区域编码配置接口", tags = "入网认证区域编码配置接口")
public class CityCodeController {
	@Autowired
	private ICityCodeService cityCodeService;


	@GetMapping("/province-tree")
	@ApiOperationSupport(order = 1)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "获取区域编码树", notes = "获取区域编码树")
	public R<List<CityCodeVO>> provinceTree(@RequestParam Map<String, Object> param) {
		List<CityCodeVO> list = cityCodeService.getTree(param);
		return R.data(list);
	}

	@GetMapping("/getList")
	@ApiOperationSupport(order = 2)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "获取区域编码", notes = "获取区域编码")
	public R<List<CityCodeVO>> getList(@RequestParam  Map<String, Object>  param) {
		List<CityCodeVO> list = cityCodeService.getList(param);
		return R.data(list);
	}

	@PostMapping("/add")
	@ApiOperationSupport(order = 3)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增区域编码", notes = "新增区域编码")
	public R add(@RequestBody List<CityCode> param) {
		boolean status = cityCodeService.add(param);
		return R.status(status);
	}

	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "修改区域编码", notes = "修改区域编码")
	public R update(@RequestBody List<CityCode> param) {
		boolean status = cityCodeService.update(param);
		return R.status(status);
	}

	@PostMapping("/delete")
	@ApiOperationSupport(order = 5)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "删除区域编码", notes = "删除区域编码")
	public R delete(@RequestBody List<CityCode> param) {
		boolean status = cityCodeService.delete(param);
		return R.status(status);
	}
}
