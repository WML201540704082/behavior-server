package com.lnsoft.device.api.cmdb.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.secure.annotation.PreAuth;
import com.lnsoft.device.entity.HardwareBasicTree;
import com.lnsoft.device.vo.HardwareBasicTreeVO;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.cmdb.service.ICmdbCiAttrService;
import com.lnsoft.device.api.cmdb.service.IHardwareBasicTreeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 资产台账模型树管理表 控制器
 *
 * @author Idevelop
 * @since 2024-02-22
 */
@RestController
@AllArgsConstructor
@RequestMapping("/hardwarebasictree")
@Api(value = "资产台账模型树管理表", tags = "资产台账模型树管理表接口")
public class HardwareBasicTreeController extends IdevelopController {

	private IHardwareBasicTreeService hardwareBasicTreeService;
	private ICmdbCiAttrService iCmdbCiAttrService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "详情", notes = "传入hardwareBasicTree")
	public R<HardwareBasicTree> detail(HardwareBasicTree hardwareBasicTree) {
		HardwareBasicTree detail = hardwareBasicTreeService.getOne(Condition.getQueryWrapper(hardwareBasicTree));
		return R.data(detail);
	}

	/**
	 * 分页 资产台账模型树管理表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入hardwareBasicTree")
	public R<IPage<HardwareBasicTree>> list(HardwareBasicTree hardwareBasicTree, Query query) {
		QueryWrapper<HardwareBasicTree> queryWrapper = Condition.getQueryWrapper(hardwareBasicTree);
		queryWrapper.lambda().orderByAsc(HardwareBasicTree::getSort);
		IPage<HardwareBasicTree> pages = hardwareBasicTreeService.page(Condition.getPage(query), queryWrapper);
		return R.data(pages);
	}

	/**
	 * 自定义分页 资产台账模型树管理表
	 */
	@GetMapping("/page/list")
	@ApiOperationSupport(order = 3)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "自定义分页", notes = "传入hardwareBasicTree")
	public R<IPage<HardwareBasicTreeVO>> page(HardwareBasicTreeVO hardwareBasicTree, Query query) {
		query.setSize(100);
		IPage<HardwareBasicTreeVO> pages = hardwareBasicTreeService.selectHardwareBasicTreePage(Condition.getPage(query), hardwareBasicTree);
		return R.data(pages);
	}

	/**
	 * 新增 资产台账模型树管理表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增", notes = "传入hardwareBasicTree")
	public R save(@Valid @RequestBody HardwareBasicTree hardwareBasicTree) {
		return R.status(hardwareBasicTreeService.save(hardwareBasicTree));
	}

	/**
	 * 修改 资产台账模型树管理表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "修改", notes = "传入hardwareBasicTree")
	public R update(@Valid @RequestBody HardwareBasicTree hardwareBasicTree) {
		return R.status(hardwareBasicTreeService.updateById(hardwareBasicTree));
	}

	/**
	 * 新增或修改 资产台账模型树管理表
	 */
	@PostMapping("/submit")
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入hardwareBasicTree")
	public R submit(@Valid @RequestBody HardwareBasicTree hardwareBasicTree) {
		return R.status(hardwareBasicTreeService.saveOrUpdate(hardwareBasicTree));
	}


	/**
	 * 删除 资产台账模型树管理表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(hardwareBasicTreeService.deleteByCiIds(Func.toLongList(ids)));
	}

	/**
	 * 刷新 资产台账模型树管理表
	 */
	@GetMapping("/refresh")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "刷新")
	@PreAuth("hasPerm('system:common:all')")
	public R<String> refresh(@ApiParam(value = "关键字") @RequestParam String keyword) {
		String refresh = hardwareBasicTreeService.refresh(keyword);
		return R.data(refresh);
	}

	/**
	 * 获取CMDB和I6000的需映射模型
	 */
	@GetMapping("/cmdb/i6000/mapping")
	@ApiOperationSupport(order = 9)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "获取CMDB和I6000的需映射模型")
	public R<List<Map<String, String>>> cmdbI6000Mapping() {
		HardwareBasicTree hardwareBasicTree = new HardwareBasicTree();
		QueryWrapper<HardwareBasicTree> queryWrapper = Condition.getQueryWrapper(hardwareBasicTree);
		queryWrapper.lambda().orderByAsc(HardwareBasicTree::getSort)
			.isNotNull(HardwareBasicTree::getErpCode).isNotNull(HardwareBasicTree::getI6000Code);
		List<HardwareBasicTree> list = hardwareBasicTreeService.list(queryWrapper);

		List<Map<String, String>> returnList = list.stream().map(item -> {
			Map<String, String> map = new HashMap<>();
			map.put("cmdbCiId", String.valueOf(item.getCiId()));
			map.put("cmdbCiName", String.valueOf(item.getCiLabel()));
			map.put("i6000CiId", item.getI6000Code());
			return map;
		}).collect(Collectors.toList());

		return R.data(returnList);
	}


}
