package com.lnsoft.device.api.i6000.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.secure.annotation.PreAuth;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.i6000.dto.I6000ExternalDTO;
import com.lnsoft.device.api.i6000.dto.I6000OriViewDTO;
import com.lnsoft.device.api.i6000.entity.I6000External;
import com.lnsoft.device.api.i6000.enums.I6000ExternalEnum;
import com.lnsoft.device.api.i6000.service.II6000ExternalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author xyzadmin
 */
@RestController
@AllArgsConstructor
@RequestMapping("/I6000/external")
@Api(value = "i6000外部数据主要接口", tags = "i6000外部数据主要接口")
public class I6000ExternalController {
	private II6000ExternalService ii6000ExternalService;

	/**
	 * 新增 i6000模型属性
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 1)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增外部数据（自动获取i6000接口）", notes = "i6000ExternalDTO")
	public R save(@Valid @RequestBody I6000ExternalDTO i6000ExternalDTO) {
		boolean save = ii6000ExternalService.saveExternal(i6000ExternalDTO);
		return R.status(save);
	}
	/**
	 * 查看详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 2)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "查看详情", notes = "传入id")
	public R detail( I6000External i6000External) {
		return R.data(ii6000ExternalService.detail(i6000External));
	}
	/**
	 * 查看列表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 3)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "查看列表", notes = "i6000External")
	public R<IPage<I6000External>> list(I6000External i6000External, Query query) {
		return R.data(ii6000ExternalService.seleteI6000ExternalPage(Condition.getPage(query),i6000External));
	}
	/**
	 * 批量删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 4)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R delete(@ApiParam(value = "主键集合", required = true) @RequestParam String ids){
		List<Long> idList = Func.toLongList(ids);
		ii6000ExternalService.delete(idList);
//		ii6000ExternalService.deleteLogic(Func.toLongList(ids))
		return R.success("删除成功");
	}
	/**
	 * 新增 i6000模型属性  (仓库)
	 */
	@PostMapping("/insert")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "新增", notes = "i6000ExternalDTO")
	public R insert(@Valid @RequestBody I6000ExternalDTO i6000ExternalDTO) {
		boolean save = ii6000ExternalService.insertExternal(i6000ExternalDTO);
		return R.status(save);
	}
	/**
	 * 新增 i6000模型属性  (机房)
	 */
	@PostMapping("/addRoom")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "新增", notes = "i6000ExternalDTO")
	public R addRoom(@Valid @RequestBody I6000ExternalDTO i6000ExternalDTO) {
		boolean save = ii6000ExternalService.addExternal(i6000ExternalDTO);
		return R.status(save);
	}
	/**
	 * 新增 i6000模型属性  (机柜)
	 */
	@PostMapping("/addCabinetsOfRoom")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "新增", notes = "i6000ExternalDTO")
	public R addCabinets(@Valid @RequestBody Map<String, Map<String,Object>> map) {
		boolean save = ii6000ExternalService.addCabinets(map);
		return R.status(save);
	}
	/**
	 * 新增 i6000模型属性  (机柜)
	 */
	@PostMapping("/addCabinets")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "新增", notes = "i6000ExternalDTO")
	public R addCabinets(@Valid @RequestBody I6000ExternalDTO i6000ExternalDTO) {
		boolean save = ii6000ExternalService.insertCabinets(i6000ExternalDTO);
		return R.status(save);
	}
	/**
	 *	获取i6000数据并新增
	 */
	@PostMapping("/getI6000AndAdd")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "获取i6000数据并新增", notes = "i6000ExternalDTO")
	public R getI6000AndAdd(@RequestBody I6000OriViewDTO i6000OriViewDTO){
		Boolean result = ii6000ExternalService.getI6000AndAdd(i6000OriViewDTO);
		return R.data(result);
	}
	/**
	 * 对比i6000品牌系列型号制造商数据
	 *
	 */
	@PostMapping("/checkData")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "对比i6000品牌系列型号制造商数据", notes = "i6000ExternalDTO")
	public R checkData(@Valid @RequestBody List<I6000External> i6000Externals){
		List<I6000External> data = ii6000ExternalService.checkData(i6000Externals);
		return R.data(data);
	}
	/**
	 * 机柜数据导入cmdb
	 */
	@PostMapping("inCmdb")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "机柜数据导入cmdb", notes = "")
	public R inCmdb() {
		return ii6000ExternalService.inCmdb();
	}
	/**
	 * 机柜数据导入cmdb测试
	 */
	@PostMapping("/inCmdb/test")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "机柜数据导入cmdb测试", notes = "")
	public R inCmdbTest(String id) {
		return ii6000ExternalService.inCmdbTest(id);
	}
	/**
	 * 机柜数据导入cmdb(无i6000)
	 */
	@PostMapping("/inCmdb/try")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "机柜数据导入cmdb(无i6000)", notes = "")
	public R inCmdbTry() {
		return ii6000ExternalService.inCmdbTry();
	}
	/**
	 * 刷新机柜数据临时
	 */
	@PostMapping("/update/try")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "刷新机柜数据临时", notes = "")
	public R update(@RequestBody List<String> cabinetsNames){
		return ii6000ExternalService.refresh(cabinetsNames);
	}
	/**
	 * 刷新区域
	 */
	@PostMapping("/update/region")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "刷新区域", notes = "")
	public R updateRegion(@RequestBody List<String> cabinetCodes){
		return ii6000ExternalService.updateRegion(cabinetCodes);
	}
	/**
	 * 获取外部数据code集合
	 */
	@GetMapping("/getExtCodeList")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取外部数据code集合", notes = "")
	public R getExtCodeList(){
		List<Map<String, String>> list = I6000ExternalEnum.toList();
		return R.data(list);
	}
}
