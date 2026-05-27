package com.lnsoft.device.api.operation.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.annotation.ApiLog;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.common.enums.hussar.DeviceRepairEnum;
import com.lnsoft.device.api.operation.service.IDeviceRepairService;
import com.lnsoft.device.api.operation.vo.HardwareBasicQueryRepairVO;
import com.lnsoft.device.api.warehouse.dto.OrderUpdateStatusDTO;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.dto.DeviceRepairDTO;
import com.lnsoft.device.entity.CiCientitySearch;
import com.lnsoft.device.entity.DeviceRepair;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.props.ThirdProperties;
import com.lnsoft.device.vo.CiCientitySearchVO;
import com.lnsoft.device.vo.DeviceRepairVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * 设备报修 控制器
 *
 * @author Idevelop
 * @since 2024-03-19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/repair")
@Api(value = "设备报修", tags = "设备报修接口")
public class DeviceRepairController extends IdevelopController {

	private IDeviceRepairService deviceRepairService;
	private ICmdbService cmdbService;
	private CmdbCientityProperties ciEntityProperties;
	private ThirdProperties thirdProperties;


	/**
	 * 详情
	 */
	@ApiLog("设备报修列表-详情")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入deviceRepair")
	public R<DeviceRepair> detail(DeviceRepair deviceRepair) {
		DeviceRepair detail = deviceRepairService.getOne(Condition.getQueryWrapper(deviceRepair));
		return R.data(detail);
	}

	/**
	 * 分页 设备报修
	 */
	@ApiLog("设备报修列表-分页")
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入deviceRepair")
	public R<IPage<DeviceRepair>> list(DeviceRepairVO deviceRepair, Query query) {
		IPage<DeviceRepair> pages = deviceRepairService.deviceRepairPage(deviceRepair,query);
		return R.data(pages);
	}

	/**
	 * 自定义分页 设备报修  废弃
	 */
	@ApiLog("设备报修列表-自定义分页")
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页废弃", notes = "传入deviceRepair")
	public R<IPage<DeviceRepairVO>> page(DeviceRepairVO deviceRepair, Query query) {
		IPage<DeviceRepairVO> pages = deviceRepairService.selectDeviceRepairPage(Condition.getPage(query), deviceRepair);
		return R.data(pages);
	}

	/**
	 * 新增 设备报修
	 */
	@ApiLog("设备报修列表-新增")
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增废弃", notes = "传入deviceRepair")
	public R save(@Valid @RequestBody DeviceRepair deviceRepair) {
		return R.status(deviceRepairService.save(deviceRepair));
	}

	/**
	 * 修改 设备报修
	 */
	@ApiLog("设备报修列表-修改")
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改废弃", notes = "传入deviceRepair")
	public R update(@Valid @RequestBody DeviceRepair deviceRepair) {
		return R.status(deviceRepairService.updateById(deviceRepair));
	}

	/**
	 * 新增或修改 设备报修
	 */
	@ApiLog("设备报修列表-新增或修改")
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "deviceRepairDTO")
	public R submit(@Valid @RequestBody DeviceRepairDTO deviceRepairDTO) throws Exception{
		return deviceRepairService.submit(deviceRepairDTO);
	}


	/**
	 * 删除 设备报修
	 */
	@ApiLog("设备报修列表-删除")
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		deviceRepairService.delete(ids);
		return R.success("删除成功");
	}
	/**
	 * 数据填充
	 */
	@ApiLog("设备报修列表-数据填充")
	@GetMapping("/load")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "数据填充", notes = "")
	public R load() {
		return deviceRepairService.load();
	}
	/**
	 * 个人工作台审核更新工单状态，增加日志记录
	 */
	@ApiLog("设备报修列表-个人工作台审核更新工单状态")
	@PostMapping("/desk/edit")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "个人工作台审核更新工单状态", notes = "传入 orderUpdateStatusDTO")
	public R<Integer> deskUpdateStatus(@RequestBody OrderUpdateStatusDTO orderUpdateStatusDTO) throws Exception {
		return deviceRepairService.deskUpdateStatus(orderUpdateStatusDTO);
	}
	/**
	 * 流程状态获取
	 */
	@ApiLog("设备报修列表-流程状态获取")
	@GetMapping("/status")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "流程状态获取", notes = "")
	public R status() {
		DeviceRepairEnum[] repairEnums = DeviceRepairEnum.values();
		HashMap<Integer, String> map = new HashMap<>();
		for (DeviceRepairEnum repairEnum : repairEnums) {
			map.put(repairEnum.getCode(),repairEnum.getMessage());
		}
		return R.data(map);
	}
	/**
	 * 工作台获取设备报修列表
	 */
	@ApiLog("设备报修列表-工作台获取设备报修列表")
	@GetMapping("/desk/list")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "工作台获取设备报修列表")
	public R<IPage<DeviceRepairVO>> deskDeviceRepairList(DeviceRepairDTO dto, Query query) {
		return deviceRepairService.deskDeviceRepairList(dto, query);
	}
	/**
	 * 根据条件分页查询 资产台账 列转行(设备报修)
	 */
	@ApiLog("设备报修列表-根据条件分页查询 资产台账 列转行(设备报修)")
	@GetMapping("/cmdb/list")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "根据条件分页查询 资产台账 (设备报修)", notes = "传入deviceTransferDetail")
	public R<FeignCiCientity> repairList(HardwareBasicQueryRepairVO hardwareBasicQueryVO, Query query) {
		try {
			List<CiCientitySearchVO> ciCientitySearchVOS = deviceRepairService.assemble(hardwareBasicQueryVO);

			String areaSub = SecureUtil.getUser().getRegionCode().substring(0, 4);

			Boolean isChange = hardwareBasicQueryVO.getIsChange();
			// 2024-12-03 先查询入库管理的设备
			if (!StringUtils.contains(thirdProperties.getRegions(), areaSub)  && !isChange) {
				CiCientitySearchVO ciCientitySearchVO = new CiCientitySearchVO();
				ciCientitySearchVO.setAttrName(CmdbAttrConstant.SOURCE_SYSTEM);
				ciCientitySearchVO.setExpression(Expression.EQUAL);
				ciCientitySearchVO.setAttrValue(ciEntityProperties.getSourceSystem1() + "," + ciEntityProperties.getSourceSystem2());
				ciCientitySearchVOS.add(ciCientitySearchVO);
			}

			CiCientitySearch cientitySearch = new CiCientitySearch();
			cientitySearch.setEntity(ciCientitySearchVOS);
			cientitySearch.setFullField(Boolean.TRUE);
			cientitySearch.setQuery(query);
			FeignCiCientity jsonObject = cmdbService.getCiCientityListByCondition(cientitySearch);

			return R.data(jsonObject);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 校验
	 */
	@ApiLog("设备报修列表-校验")
	@PostMapping("/check")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "校验", notes = "deviceRepairDTO")
	public R check(@Valid @RequestBody DeviceRepairDTO deviceRepairDTO) throws Exception{

		return deviceRepairService.check(deviceRepairDTO);
	}

}
