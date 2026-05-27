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
import com.lnsoft.core.tool.api.ResultCode;
import com.lnsoft.device.api.operation.vo.DeviceChangeVO;
import com.lnsoft.device.api.warehouse.dto.CheckDeviceQueryDto;
import com.lnsoft.device.api.warehouse.dto.CheckTaskDeviceDTO;
import com.lnsoft.device.api.warehouse.entity.CheckStatistic;
import com.lnsoft.device.api.warehouse.entity.CheckTaskDevice;
import com.lnsoft.device.api.warehouse.service.ICheckTaskDeviceService;
import com.lnsoft.device.api.warehouse.service.impl.CheckTaskDeviceServiceImpl;
import com.lnsoft.device.api.warehouse.vo.CheckDeviceCountVO;
import com.lnsoft.device.api.warehouse.vo.CheckDeviceRecordVo;
import com.lnsoft.device.api.warehouse.vo.CheckTaskDeviceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 盘点任务设备详情 控制器
 *
 * @author Idevelop
 * @since 2024-04-19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/check-task-device")
@Api(value = "盘点任务设备详情", tags = "盘点任务设备详情接口")
public class CheckTaskDeviceController extends IdevelopController {

	@Autowired
	private ICheckTaskDeviceService checkTaskDeviceService;

	@Resource
	private CheckTaskDeviceServiceImpl checkTaskDeviceServiceImpl;
	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入checkTaskDevice")
	public R<CheckTaskDevice> detail(CheckTaskDevice checkTaskDevice) {
		CheckTaskDevice detail = checkTaskDeviceService.getOne(Condition.getQueryWrapper(checkTaskDevice));
		return R.data(detail);
	}

	/**
	 * 自定义分页 盘点任务设备详情
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页(查这个)", notes = "传入checkTaskDevice")
	public R<IPage<CheckTaskDevice>> page(CheckDeviceQueryDto queryDto, Query query) {
		IPage<CheckTaskDevice> pages = checkTaskDeviceService.getList(Condition.getPage(query), queryDto);
		return R.data(pages);
	}

	/**
	 * 保存 盘点任务设备详情
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "保存", notes = "传入checkTaskDevice")
	public R save(@Valid @RequestBody CheckTaskDeviceDTO checkTaskDevice) throws Exception{
		return R.data(checkTaskDeviceService.saveDevice(checkTaskDevice));
	}

	/**
	 * 更新盘点任务设备状态
	 */
	@PostMapping("/edit/status")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "更新盘点任务设备处置状态")
	public R<Integer> editTaskDevice(@RequestBody CheckTaskDeviceDTO dto) throws Exception {
		return checkTaskDeviceService.editTaskDevice(dto);
	}

	/**
	 * 查看盘点任务处置信息
	 */
	@GetMapping("/getTaskDevice")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "查看盘点任务设备处置信息")
	public R<CheckTaskDeviceVO> getTaskDevice(@RequestParam(name = "id") String id, @RequestParam(name = "editType") String editType) {
		CheckTaskDeviceVO vo = checkTaskDeviceService.getTaskDevice(id, editType);
		return R.data(vo);
	}

	/**
	 * 建立测试数据
	 * @return
	 */
	@GetMapping("/addTestData")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "建立测试数据")
	public R addTestData() {
		return checkTaskDeviceService.addTestData();
	}

	/**
	 * 获取个人盘点任务列表
	 * @param type 盘点类型(0:已盘点，1:待盘点，2:注册/盘盈设备, 3:盘亏设备)
	 *
	 * @return
	 */
	@GetMapping("/getCheckDeviceListByUser")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "获取个人盘点任务列表")
	public R<IPage<CheckTaskDevice>> getCheckDeviceListByUser(@RequestParam("type") String type, Query query) {
		return checkTaskDeviceService.getCheckDeviceListByUser(type,query);
	}


	@GetMapping("/submitCheck")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "提交盘点")
	public R submitCheck(@RequestParam("id") String id) {
		checkTaskDeviceService.submitCheck(id);
		return R.success(ResultCode.SUCCESS);
	}
	/**
	 * 获取个人设备列表(所有)
	 * @param type 盘点类型(0-只看个人名下，1-单位名下)
	 *
	 * @return
	 */
	@GetMapping("/getDeviceByUser")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "获取个人设备列表(所有)", notes = "type 0-只看个人名下，1-查看单位名下")
	public R<FeignCiCientity> getDeviceByUser(@RequestParam("type")String type,Query query) {
		return checkTaskDeviceService.getDeviceByUser(type,query);
	}
	/**
	 * 获取个人设备数量
	 *
	 * @return
	 */
	@GetMapping("/getDeviceCount")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "获取个人设备数量")
	public R<CheckDeviceCountVO> getDeviceCount() {
		return checkTaskDeviceService.getDeviceCount();
	}

	@GetMapping("/getDeviceByIpAndMac")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "根据ip和mac获取设备")
	public R<FeignCiCientity> getDeviceByIpAndMac(CheckTaskDevice checkTaskDevice,Query query) {
		return checkTaskDeviceService.getDeviceByIpAndMac(checkTaskDevice,query);
	}
	/**
	 * 提交设备变更
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "设备变更提交", notes = "checkTaskDevice")
	public R<DeviceChangeVO> save(@Valid @RequestBody CheckTaskDeviceDTO checkTaskDevice, BindingResult result) throws Exception{
		if (result.hasErrors()) {
			return R.fail(result.getAllErrors().get(0).getDefaultMessage());
		}
		return checkTaskDeviceService.add(checkTaskDevice);
	}
	/**
	 * 获取设备履历信息
	 */
	@GetMapping("getRecord")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "获取设备履历信息", notes = "传入设备编码deviceCode")
	public R<CheckDeviceRecordVo> getRecord(String deviceCode){
		CheckDeviceRecordVo checkDeviceRecordVo = checkTaskDeviceService.getRecord(deviceCode);
		return R.data(checkDeviceRecordVo);
	}
	/**
	 * 判断
	 */
	@GetMapping("/isNewDevice")
	@ApiOperationSupport(order = 14)
	@ApiOperation(value = "判断", notes = "传入checkTaskDevice")
	public R<CheckTaskDevice> isNewDevice(CheckTaskDevice checkTaskDevice) {
		CheckTaskDevice detail = checkTaskDeviceService.isNewDevice(checkTaskDevice);
		return R.data(detail);
	}
	/**
	 * 设备注册
	 */
	@PostMapping("/deviceAdd")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "设备注册（未使用）", notes = "传入id")
	public R deviceAdd(String id) throws Exception{
		return checkTaskDeviceService.deviceAdd(id);
	}
	/**
	 * 获取个人盘点任务列表
	 * @param type 0-只看名下 1-看任务下所有
	 *
	 * @return
	 */
	@GetMapping("/getListByUser")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "获取盘点任务列表(任务详情用)")
	public R<IPage<CheckTaskDevice>> getListByUser(@RequestParam("type") String type, @RequestParam("id") String id, Query query) {
		return checkTaskDeviceService.getListByUser(type,id,query);
	}
	/**
	 * 盘盈设备投运（2025-11-4）
	 */
	@PostMapping("/operation")
	@ApiOperationSupport(order = 16)
	@ApiOperation(value = "盘盈设备投运（2025-11-4）")
	public R operation(String id){
		IdevelopUser user = SecureUtil.getUser();
		try {
			String s = checkTaskDeviceServiceImpl.taskCreateDeviceOperation(user, id);
			return R.success(s);
		} catch (Exception e) {
			return R.fail("操作失败");
		}
	}
//	/**
//	 * 盘亏设备退运（2025-11-4）
//	 */
//	@PostMapping("/operation")
//	@ApiOperationSupport(order = 16)
//	@ApiOperation(value = "盘盈设备投运（2025-11-4）")
//	public R operation(String id){
//		IdevelopUser user = SecureUtil.getUser();
//		try {
//			String s = checkTaskDeviceServiceImpl.taskCreateDeviceOperation(user, id);
//			return R.success(s);
//		} catch (Exception e) {
//			return R.fail("操作失败");
//		}
//	}
	/**
	 * 盘点操作
	 */
	@PostMapping("/checkTest")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "盘点操作", notes = "传入id")
	public R checkTest(@RequestBody CheckTaskDevice checkTaskDevice){
		boolean update = checkTaskDeviceService.updateById(checkTaskDevice);
		return R.success("盘点成功");
	}
	/**
	 * 异常分析
	 */
	@GetMapping("/statistic")
	@ApiOperationSupport(order = 15)
	@ApiOperation(value = "异常分析")
	public R<CheckStatistic> statistic(){
		return R.data(checkTaskDeviceService.statistic());

	}
	/**
	 * 异常分析
	 */
	@GetMapping("/statistic/dept")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "异常分析(单位)")
	public R<List<CheckStatistic>> dept() {
		return checkTaskDeviceService.dept();
	}

}
