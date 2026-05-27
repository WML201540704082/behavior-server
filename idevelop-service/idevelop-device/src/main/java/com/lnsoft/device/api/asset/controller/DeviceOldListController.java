package com.lnsoft.device.api.asset.controller;

import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.device.api.asset.dto.DeviceCmdbDTO;
import com.lnsoft.device.api.asset.dto.DeviceOldListDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.device.api.asset.entity.DeviceOldList;
import com.lnsoft.device.api.asset.vo.DeviceOldListVO;
import com.lnsoft.device.api.asset.service.IDeviceOldListService;
import com.lnsoft.core.boot.ctrl.IdevelopController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 老旧设备表	 控制器
 *
 * @author Idevelop
 * @since 2024-06-19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/deviceoldlist")
@Api(value = "老旧设备表	", tags = "老旧设备表	接口")
public class DeviceOldListController extends IdevelopController {

	private IDeviceOldListService deviceOldListService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入deviceOldList")
	public R<DeviceOldList> detail(DeviceOldListDTO deviceOldList) {
		DeviceOldList detail = deviceOldListService.detail(deviceOldList);
		return R.data(detail);
	}

	/**
	 * 自评库-分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "自评库-分页", notes = "传入deviceOldList")
	public R<IPage<DeviceOldList>> page(DeviceOldListVO deviceOldList, Query query) {
		IPage<DeviceOldList> pages = deviceOldListService.selectDeviceOldListPage(query, deviceOldList);
		return R.data(pages);
	}

	/**
	 * 新增 老旧设备表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入deviceOldList")
	public R save(@Valid @RequestBody List<DeviceCmdbDTO> deviceCmdbDTOList) {
		List<DeviceOldList> deviceOldLists = build(deviceCmdbDTOList);
		List<DeviceOldList> oldList = deviceOldListService.insert(deviceOldLists);
		if (ObjectUtil.isEmpty(oldList)){
			return R.fail("请不要选择重复的设备");
		}
		return R.data(oldList);
	}

	/**
	 * 修改自评 老旧设备表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改自评 老旧设备表", notes = "传入deviceOldList")
	public R update(@Valid @RequestBody List<DeviceOldList> deviceOldList) {
		return deviceOldListService.change(deviceOldList);
	}


	/**
	 * 删除 老旧设备表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(deviceOldListService.deleteLogic(Func.toLongList(ids)));
	}
	/**
	 * 计算分数
	 */
	@PostMapping("/compute")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "计算分数", notes = "")
	public R compute(@Valid @RequestBody List<DeviceOldList> deviceOldList){
		return deviceOldListService.compute(deviceOldList);
	}
	/**
	 * 获取设备列表（带参考年限）
	 *
	 */
	@GetMapping("/getDeviceList")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "获取设备列表（带参考年限）", notes = "")
	public R getDeviceList(DeviceOldListDTO deviceOldListDTO,Query query){
		return deviceOldListService.getDeviceList(deviceOldListDTO,query);
	}
	/**
	 * 分数排名获取接口（获取本类设备的排名）
	 *
	 */
	@GetMapping("/getRank")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "分数排名获取接口（获取本类设备的排名）", notes = "id 与分类编码必传")
	public R getRank(DeviceOldListDTO deviceOldListDTO){
		Integer rank = deviceOldListService.getRank(deviceOldListDTO);
		if (rank == -1){
			return R.fail("排名获取失败！");
		}
		return R.data(rank);
	}
	/**
	 * 完善评审信息上报
	 */
	@PostMapping("/refine")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "完善评审信息上报", notes = "传入List<deviceOldListDTO>")
	public R refine(@Valid @RequestBody List<DeviceOldListDTO> deviceOldListDTOList) throws Exception{
		return deviceOldListService.refine(deviceOldListDTOList);
	}
	/**
	 * 评审库审批
	 */
	@PostMapping("/approval")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "评审库审批", notes = "传入List<deviceOldListDTO>")
	public R approval(@Valid @RequestBody List<DeviceOldList> deviceOldListDTOList) {
		return deviceOldListService.approval(deviceOldListDTOList);
	}
	/**
	 * 评审库列表查询
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "评审库列表查询,分页", notes = "传入deviceOldList")
	public R<IPage<DeviceOldList>> list(DeviceOldListVO deviceOldList, Query query) {
		IPage<DeviceOldList> pages = deviceOldListService.getList(query, deviceOldList);
		return R.data(pages);
	}
	private List<DeviceOldList> build(List<DeviceCmdbDTO> deviceCmdbDTOList){
		List<DeviceOldList> oldLists = new ArrayList<>();
		for (DeviceCmdbDTO dto : deviceCmdbDTOList) {
			DeviceOldList oldList = new DeviceOldList();
			if (ObjectUtil.isNotEmpty(dto.getAfterSaleExpDate())){
				LocalDate now = LocalDate.now();
				LocalDate afterSaleExpDate = dto.getAfterSaleExpDate();
				if (now.isBefore(afterSaleExpDate)){
					oldList.setOperationCondition(8);
				}else {
					oldList.setOperationCondition(7);
				}
			}
			oldList.setDeviceName(dto.getDeviceName());
			oldList.setFaultCount(dto.getFaultCount());
			oldList.setFaultDetail(dto.getFaultDetail());
			oldList.setHiddenCount(dto.getHiddenCount());
			oldList.setHiddenDetail(dto.getHiddenDetail());
			oldList.setRegionCode(dto.getArea());
			oldList.setDeviceCategory(dto.getDeviceCategoryCode());
			oldList.setDeviceCategoryName(dto.getDeviceCategory());
			oldList.setDeviceType(dto.getDeviceTypeCode());
			oldList.setDeviceTypeName(dto.getDeviceType());
			oldList.setDeviceCode(dto.getDeviceCode());
			oldList.setDeviceStatus(dto.getDeviceStatusCode());
			oldList.setOwnerUnit(dto.getOwnerUnit());
			oldList.setOwnerUnitCode(dto.getOwnerUnitCode());
			oldList.setPropertyDept(dto.getPropertyDept());
			oldList.setPropertyDeptCode(dto.getPropertyDeptCode());
			oldList.setOperationUnit(dto.getOperationUnit());
			oldList.setOperationUnitCode(dto.getOperationUnitCode());
			oldList.setOperationDept(dto.getOperationDept());
			oldList.setOperationDeptCode(dto.getOperationDepCode());
			oldList.setOprtDateFirst(dto.getOprtDateFirst());
			oldList.setRoomCode(dto.getComputerRoomCode());
			oldLists.add(oldList);
		}
		return oldLists;
	}

}
