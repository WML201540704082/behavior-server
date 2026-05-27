package com.lnsoft.device.api.asset.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.asset.dto.DeviceCmdbDTO;
import com.lnsoft.device.api.asset.dto.ExportRoom;
import com.lnsoft.device.api.asset.dto.ResourceQuery;
import com.lnsoft.device.api.asset.dto.ResourceRoomDTO;
import com.lnsoft.device.api.asset.entity.ResourceRoom;
import com.lnsoft.device.api.asset.service.IResourceCabinetsService;
import com.lnsoft.device.api.asset.service.IResourceRoomService;
import com.lnsoft.device.api.asset.vo.ResourceRoomVO;
import com.lnsoft.device.api.asset.vo.ResourceTreeVO;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.warehouse.dto.RoomWarehouseBatchDTO;
import com.lnsoft.device.api.warehouse.vo.DeviceResourceVo;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.utils.OrderNumberUtil;
import com.lnsoft.common.utils.UuidUtils;
import com.lnsoft.device.vo.CiCientitySearchVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 空间资源管理机房表 控制器
 *
 * @author Idevelop
 * @since 2024-02-21
 */
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/resource/room")
@Api(value = "空间资源管理机房表", tags = "空间资源管理机房表接口")
public class ResourceRoomController extends IdevelopController {

	private IResourceRoomService resourceRoomService;
	private IResourceCabinetsService resourceCabinetsService;
	private ICmdbService cmdbService;
	private OrderNumberUtil orderNumberUtil;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入resourceRoom")
	public R<ResourceRoom> detail(ResourceRoom resourceRoom) {
		ResourceRoom detail = resourceRoomService.getOne(Condition.getQueryWrapper(resourceRoom));
		return R.data(detail);
	}

	/**
	 * 分页 空间资源管理机房表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入resourceRoom")
	public R<IPage<ResourceRoom>> list(ResourceRoom resourceRoom, Query query) {
		//默认展示未删除的数据
		resourceRoom.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
		IPage<ResourceRoom> pages = resourceRoomService.page(Condition.getPage(query), Condition.getQueryWrapper(resourceRoom));
		return R.data(pages);
	}

	/**
	 * 自定义分页 空间资源管理机房表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入resourceRoom")
	public R<IPage<ResourceRoomVO>> page(ResourceRoomVO resourceRoom, Query query) {
		IdevelopUser user = SecureUtil.getUser();
		if (StringUtil.isBlank(resourceRoom.getMaintenanceUnit())) {
			resourceRoom.setMaintenanceUnit(user.getCorpId());
		}
		Page<ResourceRoomVO> resourceRoomVOPage = new Page<>();
		resourceRoomVOPage.setMaxLimit(2000L);
		if (ObjectUtil.isNotEmpty(query)){
			resourceRoomVOPage.setSize(query.getSize());
			resourceRoomVOPage.setCurrent(query.getCurrent());
		}
		IPage<ResourceRoomVO> pages = resourceRoomService.selectResourceRoomPage(resourceRoomVOPage, resourceRoom);
//		IPage<ResourceRoomVO> pages = resourceRoomService.selectResourceRoomPage(Condition.getPage(query), resourceRoom);
		return R.data(pages);
	}

	/**
	 * 新增 空间资源管理机房表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增  未使用", notes = "传入resourceRoom")
	public R save(@Valid @RequestBody ResourceRoom resourceRoom) {
		resourceRoom.setType("room");
		return R.status(resourceRoomService.save(resourceRoom));
	}

	/**
	 * 修改 空间资源管理机房表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改 未使用", notes = "传入resourceRoom")
	public R update(@Valid @RequestBody ResourceRoom resourceRoom) {
		return R.status(resourceRoomService.updateById(resourceRoom));
	}

	/**
	 * 新增或修改 空间资源管理机房表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入resourceRoom")
	public R submit(@Valid @RequestBody ResourceRoom resourceRoom) {
		// try {
		// 	Thread.sleep(3000);
		// } catch (InterruptedException e) {
		// 	throw new RuntimeException(e);
		// }
		IdevelopUser user = SecureUtil.getUser();
		if (StringUtil.isBlank(resourceRoom.getRegionCode())) {
			resourceRoom.setRegionCode(user.getRegionCode());
			resourceRoom.setRegionName(user.getRegionName());
		}
		resourceRoom.setRoomId(UuidUtils.uuid());
		resourceRoom.setRegionCode(user.getRegionCode());
		resourceRoom.setRegionName(user.getRegionName());
		resourceRoom.setType("room");
		resourceRoom.setCreateDept(user.getDeptId());
		if (resourceRoomService.saveOrUpdate(resourceRoom)) {
			return R.data(resourceRoom, "操作成功");
		}
		return R.fail("操作失败");
	}


	/**
	 * 删除 空间资源管理机房表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		List<String> idList = Func.toStrList(ids);
		Query query = new Query();
		query.setCurrent(1);
		query.setSize(999);
		for (String computerCode : idList) {
			ArrayList<CiCientitySearchVO> ciCientitySearchVOS = new ArrayList<>();
			CiCientitySearchVO ciCientitySearchVO = new CiCientitySearchVO();
			ciCientitySearchVO.setAttrName(CmdbAttrConstant.COMPUTER_ROOM_CODE);
			ciCientitySearchVO.setExpression(Expression.EQUAL);
			ciCientitySearchVO.setAttrValue(computerCode);
			ciCientitySearchVOS.add(ciCientitySearchVO);
			FeignCiCientity jsonObject = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
			if (jsonObject.getTotal()>0){
				return R.fail("存在关联的下级机柜，禁止删除");
			}
		}
		Integer i = resourceRoomService.delete(idList);
		if (i > 0) {
			return R.success("删除成功");
		}
		return R.fail("删除失败");
	}

	/**
	 * 机房数据导出
	 */
	@PostMapping("exportExcel")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "机房导出", notes = "")
	public void export(@RequestBody ResourceRoomDTO resourceRoomDTO, HttpServletResponse response) {
		resourceRoomService.export(resourceRoomDTO, response);
	}

	/**
	 * 机房模板下载
	 */
	@PostMapping("downExcel")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "机房模板下载", notes = "")
	public void down(HttpServletResponse response) {
		try {
			ArrayList<ExportRoom> exportRooms = new ArrayList<>();
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			String fileName = URLEncoder.encode("机房列表模板", StandardCharsets.UTF_8.name());
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
			EasyExcel.write(response.getOutputStream(), ExportRoom.class).sheet("机房列表").doWrite(exportRooms);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 根据条件分页查询 机房辅助设备
	 */
	@GetMapping("/dev/list")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "根据条件分页查询 机房辅助设备", notes = "传入deviceTransferDetail")
	public R<FeignCiCientity> list(DeviceResourceVo deviceResourceVo, Query query) {
		try {
			CiCientitySearchVO searchVO1 = new CiCientitySearchVO();
			searchVO1.setAttrName(CmdbAttrConstant.DEVICE_CATEGORY_CODE);
			searchVO1.setAttrValue(deviceResourceVo.getDeviceCategoryCode());
			searchVO1.setExpression(Expression.EQUAL);
			CiCientitySearchVO searchVO2 = new CiCientitySearchVO();
			searchVO2.setAttrName(CmdbAttrConstant.COMPUTER_ROOM_CODE);
			searchVO2.setAttrValue(deviceResourceVo.getComputerRoomCode());
			searchVO2.setExpression(Expression.EQUAL);
			List<CiCientitySearchVO> cientitySearchVOS = new ArrayList<>();
			if (StringUtils.isNotEmpty(deviceResourceVo.getDeviceName())) {
				CiCientitySearchVO searchVO3 = new CiCientitySearchVO();
				searchVO3.setAttrName(CmdbAttrConstant.DEVICE_NAME);
				searchVO3.setAttrValue(deviceResourceVo.getDeviceName());
				searchVO3.setExpression(Expression.LIKE);
				cientitySearchVOS.add(searchVO3);
			}
			cientitySearchVOS.add(searchVO1);
			cientitySearchVOS.add(searchVO2);
			FeignCiCientity jsonObject = cmdbService.getCiCientityListByClaccify(cientitySearchVOS, query);
			return R.data(jsonObject);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据地区父id查询列表
	 */
	@GetMapping("/area/list")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "分页", notes = "传入resourceRoom")
	public R<IPage<ResourceRoomVO>> findRoomList(ResourceRoomVO resourceRoom, Query query) {
		IPage<ResourceRoomVO> list = resourceRoomService.selectRoomByAreaId(Condition.getPage(query), resourceRoom);
		return R.data(list);
	}

	/**
	 * 懒加载列表 地区
	 */
	@GetMapping("/lazy-tree")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "懒加载列表-区域", notes = "传入ResourceQuery")
	public R lazyTree(ResourceQuery resourceQuery) {
		List<ResourceTreeVO> resourceTreeVO = resourceRoomService.lazyTree(resourceQuery);
		return R.data(resourceTreeVO);
	}

	/**
	 * 懒加载列表 部门
	 */
	@GetMapping("/deptTree")
	@ApiOperationSupport(order = 14)
	@ApiOperation(value = "懒加载列表-单位", notes = "传入ResourceQuery")
	public R deptTree(ResourceQuery resourceQuery) {
		List<ResourceTreeVO> resourceTreeVO = resourceRoomService.deptTree(resourceQuery);
		return R.data(resourceTreeVO);
	}

	/**
	 * 机房选择设备
	 */
	@GetMapping("getDeviceList")
	@ApiOperationSupport(order = 15)
	@ApiOperation(value = "机房选择设备(新)+机房设备列表", notes = "")
	public R getDeviceList(DeviceCmdbDTO deviceCmdbDTO, Query query) {
		return resourceRoomService.getDeviceList(deviceCmdbDTO, query);
	}

	/**
	 * 提交关联设备
	 */
	@PostMapping("/submitDevice")
	@ApiOperationSupport(order = 16)
	@ApiOperation(value = "提交关联设备", notes = "")
	public R submit(@RequestBody List<DeviceCmdbDTO> resourceCabinetsList) {
		return resourceRoomService.submit(resourceCabinetsList);
	}

	@PostMapping("/batch/submit")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "批量修改关联机房", notes = "roomWarehouseBatchDTO")
	public R batchUpdate(@RequestBody RoomWarehouseBatchDTO roomWarehouseBatchDTO) {
		return R.status(resourceRoomService.batchUpdate(roomWarehouseBatchDTO));
	}
}
