package com.lnsoft.device.api.warehouse.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.asset.service.IHardwareBasicService;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.warehouse.dto.CheckTaskDTO;
import com.lnsoft.device.api.warehouse.dto.CheckTaskQueryDto;
import com.lnsoft.device.api.warehouse.entity.CheckTask;
import com.lnsoft.device.api.warehouse.entity.CheckTaskExport;
import com.lnsoft.device.api.warehouse.mapper.CheckTaskDeviceMapper;
import com.lnsoft.device.api.warehouse.service.ICheckTaskService;
import com.lnsoft.device.api.warehouse.vo.CheckDeviceNumVo;
import com.lnsoft.device.api.warehouse.vo.CheckTaskVO;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.props.ThirdProperties;
import com.lnsoft.system.feign.IDeptClient;
import com.lnsoft.system.vo.DeptVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 盘点任务 控制器
 *
 * @author Idevelop
 * @since 2024-04-19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/check-task")
@Api(value = "盘点任务", tags = "盘点任务接口")
public class CheckTaskController extends IdevelopController {

	@Autowired
	private ICheckTaskService checkTaskService;
	@Resource
	private IDeptClient deptClient;
	@Resource
	private IHardwareBasicService hardwareBasicService;
	@Resource
	private ICmdbService cmdbService;
	@Resource
	private CmdbCientityProperties ciEntityProperties;
	@Resource
	private ThirdProperties thirdProperties;
	@Resource
	private CheckTaskDeviceMapper taskDeviceMapper;
 	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入checkTask")
	public R<CheckTaskVO> detail(@RequestParam String id) {
		CheckTaskVO detail = checkTaskService.getDetail(id);
		return R.data(detail);
	}

	/**
	 * 分页 盘点任务
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "CheckTaskQueryDto")
	public R<IPage<CheckTaskVO>> list(CheckTaskQueryDto queryDto, Query query) {
		IPage<CheckTaskVO> pages = checkTaskService.getList(Condition.getPage(query), queryDto);
		return R.data(pages);
	}

	/**
	 * 暂存 盘点任务
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "暂存盘点任务", notes = "传入checkTaskDTO")
	public R<CheckTaskVO> save(@RequestBody CheckTaskDTO checkTask) {
		return checkTaskService.saveTask(checkTask);
	}

	/**
	 * 删除 盘点任务
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R<Integer> remove(@ApiParam(value = "主键集合", required = true) @RequestBody CheckTaskDTO checkTaskDTO) {
		return checkTaskService.removeTask(checkTaskDTO.getIds());
	}

	/**
	 * 提交盘点任务
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "提交盘点任务", notes = "传入checkTaskDTO")
	public R<CheckTaskVO> submit(@RequestBody CheckTaskDTO dto, BindingResult result) throws Exception {
		if (result.hasErrors()) {
			return R.fail(result.getAllErrors().get(0).getDefaultMessage());
		}
		return checkTaskService.submitCheck(dto);
	}

	/**
	 * 更新盘点任务工单状态
	 */
	@PostMapping("/edit/status")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "更新盘点任务工单状态")
	public R<Integer> deskCheckTaskStatus(@RequestBody CheckTaskDTO dto) throws Exception {
		return checkTaskService.deskCheckTaskStatus(dto);
	}

	/**
	 * 工作台获取盘点任务列表
	 */
	@GetMapping("/desk/list")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "工作台获取盘点任务列表")
	public R<IPage<CheckTaskVO>> deskCheckTaskList(CheckTaskDTO dto, Query query) {
		IPage<CheckTaskVO> result = checkTaskService.deskCheckTaskList(dto, query);
		return R.data(result);
	}

	@GetMapping("/historyTask")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "获取历史盘点任务")
	public R<List<CheckDeviceNumVo>> historyTask() {
		List<CheckDeviceNumVo> result = checkTaskService.historyTask();
		return R.data(result);
	}

	@GetMapping("/getDept")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "获取单位下部门")
	public R<List<DeptVO>> getDept(@RequestParam("parentId") String parentId) {
		return deptClient.getDeptList(parentId);
	}

	@GetMapping("/getCheckListByUser")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "获取个人盘点任务列表")
	public R<List<CheckTask>> getCheckListByUser() {
		return checkTaskService.getCheckListByUser();
	}

	/**
	 * 导出
	 */
	@PostMapping("/export")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "导出")
	public void export(HttpServletResponse response){
		List<CheckTask> list = checkTaskService.list();
		List<CheckTaskExport> checkTaskExports = new ArrayList<>();
		for (CheckTask checkTask : list) {
			CheckTaskExport checkTaskExport = new CheckTaskExport();
			BeanUtils.copyProperties(checkTask,checkTaskExport);
			checkTaskExports.add(checkTaskExport);
		}
		for (CheckTaskExport checkTaskExport : checkTaskExports) {
			String id = checkTaskExport.getId();
			Long checkNumNo = taskDeviceMapper.selectCheckNumNo(id);
			Long checkNumIs = taskDeviceMapper.selectCheckNumIs(id);
			Long checkNumPy = taskDeviceMapper.selectCheckNumPy(id);
			Long checkNumPk = taskDeviceMapper.selectCheckNumPk(id);
			checkTaskExport.setCheckProgress((checkNumIs + checkNumPy + checkNumPk) + "/" + (checkNumIs + checkNumPy + checkNumPk+ checkNumNo));
			checkTaskExport.setIsCheckNum(checkNumIs);
			checkTaskExport.setNoCheckNum(checkNumNo);
			checkTaskExport.setPk(checkNumPk);
			checkTaskExport.setPy(checkNumPy);
			Long returned = taskDeviceMapper.selectCheckReturned(id);
			Long checklstw = taskDeviceMapper.selectChecklstw(id);
			checkTaskExport.setReturnedDevice(returned);
			checkTaskExport.setLstwDevice(checklstw);
		}
		try {
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			String fileName = URLEncoder.encode("导出", StandardCharsets.UTF_8.name());
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
			EasyExcel.write(response.getOutputStream(), CheckTaskExport.class).sheet("列表").doWrite(checkTaskExports);
		} catch (IOException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	/**
	 * 在线预览
	 */
	@GetMapping("/online")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "在线预览")
	public R<String> online() {
		return checkTaskService.online();
	}

}
