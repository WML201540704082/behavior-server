package com.lnsoft.device.api.warehouse.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.BeanUtil;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.i6000.service.II6000Service;
import com.lnsoft.device.api.warehouse.dto.RoomWarehouseBatchDTO;
import com.lnsoft.device.api.warehouse.dto.WarehouseDTO;
import com.lnsoft.device.api.warehouse.entity.Warehouse;
import com.lnsoft.device.api.warehouse.service.IWarehouseService;
import com.lnsoft.device.api.warehouse.vo.WarehouseDictVO;
import com.lnsoft.device.api.warehouse.vo.WarehouseQueryVO;
import com.lnsoft.device.utils.OrderNumberUtil;
import com.lnsoft.device.vo.CiCientitySearchVO;
import com.lnsoft.system.feign.IDeptClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 仓库管理表 控制器
 *
 * @author Idevelop
 * @since 2024-03-05
 */
@RestController
@AllArgsConstructor
@RequestMapping("/warehouse")
@Api(value = "仓库管理表", tags = "仓库管理表接口")
public class WarehouseController extends IdevelopController {

	private IWarehouseService warehouseService;
	private ICmdbService cmdbService;
	private OrderNumberUtil orderNumberUtil;
	private IDeptClient deptClient;
	private II6000Service ii6000Service;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入warehouse")
	public R<Warehouse> detail(Warehouse warehouse) {
		Warehouse detail = warehouseService.getOne(Condition.getQueryWrapper(warehouse));
		return R.data(detail);
	}

	/**
	 * 分页 仓库管理表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入warehouse")
	public R<IPage<Warehouse>> list(Warehouse warehouse, Query query) {
		IPage<Warehouse> pages = warehouseService.page(Condition.getPage(query), Condition.getQueryWrapper(warehouse));
		return R.data(pages);
	}

	/**
	 * 自定义分页 仓库管理表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入warehouse")
	public R<IPage<Warehouse>> page(Warehouse warehouse, Query query) {

		IPage<Warehouse> pages = warehouseService.selectWarehousePage(query, warehouse);
		return R.data(pages);
	}

	/**
	 * 新增 仓库管理表  未使用
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入warehouse")
	public R save(@Valid @RequestBody Warehouse warehouse) {
		IdevelopUser user = SecureUtil.getUser();
		String code = orderNumberUtil.generateWarehouse();
		warehouse.setWarehouseId(code);
		warehouse.setWarehouseStatus("1");
		warehouse.setRegionCode(user.getRegionCode());
		return R.status(warehouseService.save(warehouse));
	}

	/**
	 * 修改 仓库管理表 未使用
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入warehouse")
	public R update(@Valid @RequestBody Warehouse warehouse) {
		return R.status(warehouseService.updateById(warehouse));
	}

	/**
	 * 新增或修改 仓库管理表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入warehouse")
	public R submit(@Valid @RequestBody Warehouse warehouse) {
		String code = orderNumberUtil.generateWarehouse();
		if (warehouse.getWarehouseId() == null) {
			warehouse.setWarehouseId(code);
		}
		if (StringUtils.isEmpty(warehouse.getWarehouseStatus())) {
			warehouse.setWarehouseStatus("1");
		}
		WarehouseDTO warehouseDTO = new WarehouseDTO();
		BeanUtil.copy(warehouse, warehouseDTO);
		warehouseDTO.setType("warehouse");
		boolean saveOrUpdate = warehouseService.saveOrUpdate(warehouse);
//		if (saveOrUpdate) {
//			Map<String, Map<String, Object>> map = Maps.newHashMap();
//			Map<String, Object> i6000Map = new HashMap<>();
//			i6000Map.put(CmdbAttrConstant.CI_NAME, warehouse.getWarehouseName());
//			i6000Map.put(I6000AttrConstant.CITYPE_ID, "T502");
//			i6000Map.put(I6000AttrConstant.CI_ID, warehouse.getUuid());
//			i6000Map.put(I6000AttrConstant.CITYPE, "T502");
//			i6000Map.put(I6000AttrConstant.DETAIL_ADDR, warehouse.getAddress());
//			R<Dept> deptR = deptClient.getById(warehouse.getOwnerUnitId());
//			String i6000UnitCode = "";
//			String i6000Unit = "";
//			if (StringUtil.isNotBlank(warehouse.getUuid())) {
//				//修改同步i6000
//				if (ObjectUtil.isNotEmpty(deptR.getData())) {
//					i6000UnitCode = deptR.getData().getI6000UnitCode();
//					i6000Unit = deptR.getData().getI6000Unit();
//					i6000Map.put(I6000AttrConstant.RUN_CORP_CODE, i6000UnitCode);
//					i6000Map.put(I6000AttrConstant.RUN_CORP_CODE_NAME, i6000Unit);
//					map.put(warehouse.getUuid(), i6000Map);
//					//同步
//					List<I6000ResultResp> i6000ResultResps = ii6000Service.i6000BatchupdateDirect(map);
//					if ("true".equals(i6000ResultResps.get(0).getSuccessful())) {
//						warehouse.setIsI6000("1");
//						warehouseService.saveOrUpdate(warehouse);
//					} else {
//						warehouse.setIsI6000("0");
//						warehouseService.saveOrUpdate(warehouse);
//					}
//				} else {
//					//获取单位失败，同步失败
//					warehouse.setIsI6000("0");
//					warehouseService.saveOrUpdate(warehouse);
//				}
//			} else {
//				// 新增同步i6000
//				if (ObjectUtil.isNotEmpty(deptR.getData())) {
//					i6000UnitCode = deptR.getData().getI6000UnitCode();
//					i6000Unit = deptR.getData().getI6000Unit();
//					i6000Map.put(I6000AttrConstant.RUN_CORP_CODE, i6000UnitCode);
//					i6000Map.put(I6000AttrConstant.RUN_CORP_CODE_NAME, i6000Unit);
//					map.put(warehouse.getUuid(), i6000Map);
//					//同步
//					I6000ResultResp i6000BatchsaveDirect = ii6000Service.i6000BatchsaveDirect("T501", map);
//					if ("true".equals(i6000BatchsaveDirect.getSuccessful())) {
//						warehouse.setIsI6000("1");
//						warehouseService.saveOrUpdate(warehouse);
//					} else {
//						warehouse.setIsI6000("0");
//						warehouseService.saveOrUpdate(warehouse);
//					}
//				} else {
//					//获取单位失败，同步失败
//					warehouse.setIsI6000("0");
//					warehouseService.saveOrUpdate(warehouse);
//				}
//			}
//		}
		return R.data(warehouseDTO);
	}


	/**
	 * 删除 仓库管理表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		List<String> idList = Func.toStrList(ids);
		for (String id : idList) {
			WarehouseQueryVO warehouseQueryVO = new WarehouseQueryVO();
			Warehouse warehouse = new Warehouse();
			warehouse.setUuid(id);
			Warehouse detail = warehouseService.getOne(Condition.getQueryWrapper(warehouse));
			warehouseQueryVO.setInWarehouseCode(detail.getUuid());
			Query query = new Query();
			List<CiCientitySearchVO> ciCientitySearchVOS = CiCientitySearchVO.convertCiCientitySearchVO(warehouseQueryVO);
			FeignCiCientity jsonObject = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
			if (jsonObject.getData() != null && jsonObject.getData().size() > 0) {
				return R.fail("仓库存在设备，禁止删除");
			}
		}
		Integer i = warehouseService.delete(idList);
		if (i > 0) {
			return R.success("删除成功");
		}
		return R.fail("删除失败");
	}

	/**
	 * 获取仓库列表
	 */
	@GetMapping("/dict/list")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "获取仓库列表", notes = "ownerUnitId")
	public R list(String ownerUnitId) {
		List<WarehouseDictVO> list = warehouseService.findByOwnerUnitId(ownerUnitId);
		return R.data(list);
	}

	/**
	 * 仓库数据导出
	 */
	@PostMapping("exportExcel")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "仓库导出", notes = "")
	public void export(@RequestBody WarehouseDTO warehouseDTO, HttpServletResponse response) {
		warehouseService.export(warehouseDTO, response);
	}

	@PostMapping("/batch/submit")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "批量修改关联仓库", notes = "roomWarehouseBatchDTO")
	public R batchUpdate(@RequestBody RoomWarehouseBatchDTO roomWarehouseBatchDTO) {
		return R.status(warehouseService.batchUpdate(roomWarehouseBatchDTO));
	}


//	/**
//	 * 仓库出库量
//	 */
//	@GetMapping("/statistics/out")
//	@ApiOperationSupport(order = 1)
//	@ApiOperation(value = "在库设备统计", notes = "")
//	public R outStockedStatistics(WarehouseDTO warehouse) {
//		IdevelopUser sysUser = SecureUtil.getUser();
//		warehouse.setRegionCode(sysUser.getRegionCode());
//		List<Warehouse> list = warehouseService.outStockedStatistics(warehouse);
//		return R.data(list);
//	}
//
//	/**
//	 * 仓库库存统计
//	 */
//	@GetMapping("/statistics/stocked")
//	@ApiOperationSupport(order = 1)
//	@ApiOperation(value = "仓库库存统计", notes = "")
//	public R deviceStatisticsStocked(WarehouseDTO warehouse) {
//		IdevelopUser sysUser = SecureUtil.getUser();
//		warehouse.setRegionCode(sysUser.getRegionCode());
//		List<Warehouse> list = warehouseService.deviceStatisticsStocked(warehouse);
//		return R.data(list);
//	}

}
