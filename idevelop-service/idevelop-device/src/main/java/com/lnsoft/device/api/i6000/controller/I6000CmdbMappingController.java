package com.lnsoft.device.api.i6000.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.secure.annotation.PreAuth;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.cmdb.service.IHardwareBasicTreeService;
import com.lnsoft.device.api.i6000.entity.I6000CiAttr;
import com.lnsoft.device.api.i6000.entity.I6000CmdbMapping;
import com.lnsoft.device.api.i6000.entity.I6000CmdbMappingBatch;
import com.lnsoft.device.api.i6000.service.II6000CiAttrService;
import com.lnsoft.device.api.i6000.service.II6000CmdbMappingService;
import com.lnsoft.device.api.i6000.vo.CmdbI6000MappingVO;
import com.lnsoft.device.api.i6000.vo.I6000CmdbMappingVO;
import com.lnsoft.device.entity.HardwareBasicTree;
import com.lnsoft.device.props.CmdbDictProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * cmdb和i6000的映射关系表 控制器
 *
 * @author Idevelop
 * @since 2024-03-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("/I6000Cmdbmapping")
@Api(value = "cmdb和i6000的映射关系表", tags = "cmdb和i6000的映射关系表接口")
public class I6000CmdbMappingController extends IdevelopController {

	private II6000CmdbMappingService I6000CmdbMappingService;
	private II6000CiAttrService ii6000CiAttrService;
	private IHardwareBasicTreeService hardwareBasicTreeService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "详情", notes = "传入I6000CmdbMapping")
	public R<I6000CmdbMapping> detail(I6000CmdbMapping I6000CmdbMapping) {
		I6000CmdbMapping detail = I6000CmdbMappingService.getOne(Condition.getQueryWrapper(I6000CmdbMapping));
		return R.data(detail);
	}

	/**
	 * 分页 cmdb和i6000的映射关系表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "分页", notes = "传入I6000CmdbMapping")
	public R<IPage<I6000CmdbMapping>> list(I6000CmdbMapping I6000CmdbMapping, Query query) {
		QueryWrapper<com.lnsoft.device.api.i6000.entity.I6000CmdbMapping> queryWrapper = Condition.getQueryWrapper(I6000CmdbMapping);
		queryWrapper.lambda().orderByDesc(com.lnsoft.device.api.i6000.entity.I6000CmdbMapping::getCreateTime);

		IPage<I6000CmdbMapping> pages = I6000CmdbMappingService.page(Condition.getPage(query), queryWrapper);
		return R.data(pages);
	}

	/**
	 * 自定义分页 cmdb和i6000的映射关系表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "分页", notes = "传入I6000CmdbMapping")
	public R<IPage<I6000CmdbMappingVO>> page(I6000CmdbMappingVO I6000CmdbMapping, Query query) {
		IPage<I6000CmdbMappingVO> pages = I6000CmdbMappingService.selectI6000CmdbMappingPage(Condition.getPage(query), I6000CmdbMapping);
		return R.data(pages);
	}

	/**
	 * 新增 cmdb和i6000的映射关系表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增", notes = "传入I6000CmdbMapping")
	public R save(@Valid @RequestBody I6000CmdbMapping I6000CmdbMapping) {
		return R.status(I6000CmdbMappingService.save(I6000CmdbMapping));
	}

	/**
	 * 修改 cmdb和i6000的映射关系表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "修改", notes = "传入I6000CmdbMapping")
	public R update(@Valid @RequestBody I6000CmdbMapping I6000CmdbMapping) {
		return R.status(I6000CmdbMappingService.updateById(I6000CmdbMapping));
	}

	/**
	 * 新增或修改 cmdb和i6000的映射关系表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增或修改", notes = "传入I6000CmdbMapping")
	public R submit(@Valid @RequestBody I6000CmdbMapping I6000CmdbMapping) {
		ii6000CiAttrService.updateByAttrCode(I6000CmdbMapping.getI6000AttrCode(), I6000CmdbMapping.getI6000CiId());
		return R.status(I6000CmdbMappingService.saveOrUpdate(I6000CmdbMapping));
	}


	/**
	 * 物理删除 cmdb和i6000的映射关系表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "物理删除", notes = "传入id")
	public R remove(@ApiParam(value = "id", required = true) @RequestParam String id) {
		I6000CmdbMapping i6000CmdbMapping = I6000CmdbMappingService.getById(id);
		I6000CiAttr i6000CiAttr = new I6000CiAttr();
		i6000CiAttr.setIsMapping(0);
		QueryWrapper<I6000CiAttr> i6000CiAttrQueryWrapper = new QueryWrapper<>();
		i6000CiAttrQueryWrapper.lambda().eq(I6000CiAttr::getCiCode, i6000CmdbMapping.getI6000CiId())
			.eq(I6000CiAttr::getAttrCode, i6000CmdbMapping.getI6000AttrCode())
				.eq(I6000CiAttr::getIsMapping, 1);
		ii6000CiAttrService.update(i6000CiAttr,i6000CiAttrQueryWrapper);
		return R.status(I6000CmdbMappingService.deleteLogic(id));
	}

	/**
	 * 批量新增或修改 cmdb和i6000的映射关系表
	 */
	@PostMapping("/submit/batch")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "批量新增或修改", notes = "传入i6000CmdbMappingList")
	public R submitBatch(@Valid @RequestBody I6000CmdbMappingBatch i6000CmdbMappingBatch) {
		for (I6000CmdbMapping i6000CmdbMapping : i6000CmdbMappingBatch.getI6000CmdbMappingList()) {
			i6000CmdbMapping.setCmdbCiId(i6000CmdbMappingBatch.getCmdbCiId());
			i6000CmdbMapping.setCmdbCiName(i6000CmdbMappingBatch.getCmdbCiName());
			i6000CmdbMapping.setI6000CiId(i6000CmdbMappingBatch.getI6000CiId());
		}
		return R.status(I6000CmdbMappingService.saveOrUpdateBatch(i6000CmdbMappingBatch.getI6000CmdbMappingList()));
	}

	private CmdbDictProperties cmdbDictProperties;
	/**
	 * 临时处理
	 */
	@PostMapping("/submit/refresh")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "临时处理", notes = "传入i6000CmdbMappingList")
	public R submitBatchRefresh() {
		List<I6000CmdbMapping> i6000CmdbMappings = new ArrayList<>();
		Map<String, String> dictErpMapByCiId = cmdbDictProperties.getErpI6000MapByCiId(cmdbDictProperties.getDeviceType());
		Map<Object, Object> dictValue = cmdbDictProperties.getDictMapByCiId(cmdbDictProperties.getDeviceType());

		HardwareBasicTree hardwareBasicTree = new HardwareBasicTree();
		QueryWrapper<HardwareBasicTree> queryWrapper = Condition.getQueryWrapper(hardwareBasicTree);
		queryWrapper.lambda().orderByAsc(HardwareBasicTree::getSort)
			.isNotNull(HardwareBasicTree::getErpCode).isNotNull(HardwareBasicTree::getI6000Code);
		List<HardwareBasicTree> list3 = hardwareBasicTreeService.list(queryWrapper);

		HashMap<String, HardwareBasicTree> treeMap = new HashMap<>();
		for (HardwareBasicTree basicTree : list3) {
			treeMap.put(basicTree.getDeviceType(), basicTree);
		}

		for (Map.Entry<String, String> objectObjectEntry : dictErpMapByCiId.entrySet()) {
			String key = objectObjectEntry.getKey();
			String value = objectObjectEntry.getValue();
			if (objectObjectEntry.getKey().equals("1110899382616064") ||
				objectObjectEntry.getKey().equals("1106425461145600")) {
				continue;
			}
			List<I6000CmdbMapping> list = I6000CmdbMappingService.list().stream().distinct()
				.collect(Collectors.collectingAndThen(
					Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(I6000CmdbMapping::getCmdbAttrCode))), ArrayList::new));

			QueryWrapper<I6000CiAttr> i6000CiAttrQueryWrapper = new QueryWrapper<>();
			i6000CiAttrQueryWrapper.lambda().eq(I6000CiAttr::getCiCode, value);
			List<I6000CiAttr> list1 = ii6000CiAttrService.list(i6000CiAttrQueryWrapper);
			HashMap<String, I6000CiAttr> stringI6000CiAttrHashMap = new HashMap<>();
			for (I6000CiAttr i6000CiAttr : list1) {
				stringI6000CiAttrHashMap.put(i6000CiAttr.getAttrCode(),i6000CiAttr);
			}
			for (I6000CmdbMapping i6000CmdbMapping : list) {
				I6000CiAttr i6000CiAttr = stringI6000CiAttrHashMap.get(i6000CmdbMapping.getI6000AttrCode());
				if (Objects.nonNull(i6000CiAttr)) {
					I6000CmdbMapping i6000CmdbMapping1 = new I6000CmdbMapping();
					HardwareBasicTree hardwareBasicTree1 = treeMap.get(key);
					i6000CmdbMapping1.setCmdbCiId(hardwareBasicTree1.getCiId());
					i6000CmdbMapping1.setCmdbCiName(hardwareBasicTree1.getCiLabel());
					i6000CmdbMapping1.setCmdbAttrCode(i6000CmdbMapping.getCmdbAttrCode());
					i6000CmdbMapping1.setCmdbAttrLabel(i6000CmdbMapping.getCmdbAttrLabel());
					i6000CmdbMapping1.setCmdbAttrType(i6000CmdbMapping.getCmdbAttrType());
					i6000CmdbMapping1.setI6000CiId(i6000CiAttr.getCiCode());
					i6000CmdbMapping1.setI6000AttrCode(i6000CmdbMapping.getI6000AttrCode());
					i6000CmdbMapping1.setI6000Datatype(i6000CmdbMapping.getI6000Datatype());
					i6000CmdbMapping1.setI6000OriType(i6000CmdbMapping.getI6000OriType());
					i6000CmdbMapping1.setI6000Origin(i6000CmdbMapping.getI6000Origin());
					i6000CmdbMapping1.setI6000Expan(i6000CmdbMapping.getI6000Expan());
					i6000CmdbMapping1.setCreateTime(new Date());
					i6000CmdbMapping1.setUpdateTime(new Date());
					i6000CmdbMappings.add(i6000CmdbMapping1);
				}
			}
		}
		for (I6000CmdbMapping i6000CmdbMapping : i6000CmdbMappings) {
			ii6000CiAttrService.updateByAttrCode(i6000CmdbMapping.getI6000AttrCode(), i6000CmdbMapping.getI6000CiId());
		}
		return R.status(I6000CmdbMappingService.saveBatch(i6000CmdbMappings));
	}
	/**
	 * 刷新映射关系
	 */
	@PostMapping("/checkRefresh")
	@ApiOperationSupport(order = 10)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "刷新映射关系", notes = "传入I6000CmdbMapping")
	public R checkRefresh(@Valid @RequestBody I6000CmdbMapping I6000CmdbMapping) {
		return I6000CmdbMappingService.checkRefresh(I6000CmdbMapping);

	}

	/**
	 * 获取字段列表
	 */
	@GetMapping("/cmdb/getAttrListCmdb")
	@ApiOperationSupport(order = 11)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "根据设备类型获取字段列表", notes = "传入设备类型模型id")
	public R<CmdbI6000MappingVO> getAttrListCmdb(String deviceType) {
		return I6000CmdbMappingService.getAttrListCmdb(deviceType);
	}

	/**
	 * 新增关联关系
	 */
	@PostMapping("/cmdb/insertRelation")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "新增关联关系", notes = "")
	public R insertRelation(@RequestBody List<I6000CmdbMapping> i6000CmdbMappingList){
		return I6000CmdbMappingService.insertRelation(i6000CmdbMappingList);
	}
	/**
	 * 列表查询接口
	 */
	@GetMapping("/cmdb/getList")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "获取列表", notes = "")
	public R<List<I6000CmdbMappingVO>> getList(){
		return I6000CmdbMappingService.getList();
	}
	/**
	 * 根据cmdb的ciid获取映射关系
	 */
	@GetMapping("/cmdb/getMappingList")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "获取映射关系详情列表", notes = "传入cmdb的 cIid")
	public R<List<I6000CmdbMappingVO>> getMappingList(String ciId){
		return I6000CmdbMappingService.getMappingList(ciId);
	}




}
