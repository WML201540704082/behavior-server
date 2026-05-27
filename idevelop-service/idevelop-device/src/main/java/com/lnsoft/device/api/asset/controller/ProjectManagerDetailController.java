package com.lnsoft.device.api.asset.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.dto.ProjectManagerDetailDTO;
import com.lnsoft.device.entity.ProjectManagerDetail;
import com.lnsoft.device.api.asset.service.IProjectManagerDetailService;
import com.lnsoft.device.vo.ProjectManagerDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 项目下的ERP资产编码 控制器
 *
 * @author Idevelop
 * @since 2024-04-29
 */
@RestController
@AllArgsConstructor
@RequestMapping("/projectmanagerdetail")
@Api(value = "项目下的ERP资产编码", tags = "项目下的ERP资产编码接口")
public class ProjectManagerDetailController extends IdevelopController {

	private IProjectManagerDetailService projectManagerDetailService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入projectManagerDetail")
	public R<ProjectManagerDetail> detail(ProjectManagerDetail projectManagerDetail) {
		ProjectManagerDetail detail = projectManagerDetailService.getOne(Condition.getQueryWrapper(projectManagerDetail));
		return R.data(detail);
	}

	/**
	 * 分页 项目下的ERP资产编码
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入projectManagerDetail")
	public R<IPage<ProjectManagerDetail>> list(ProjectManagerDetail projectManagerDetail, Query query) {
		QueryWrapper<ProjectManagerDetail> queryWrapper = Condition.getQueryWrapper(projectManagerDetail);
		queryWrapper.lambda().orderByDesc(ProjectManagerDetail::getCreateTime);
		IPage<ProjectManagerDetail> pages = projectManagerDetailService.page(Condition.getPage(query), queryWrapper);
		return R.data(pages);
	}

	/**
	 * 自定义分页 项目下的ERP资产编码
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入projectManagerDetail")
	public R<IPage<ProjectManagerDetailVO>> page(ProjectManagerDetailVO projectManagerDetail, Query query) {
		IPage<ProjectManagerDetailVO> pages = projectManagerDetailService.selectProjectManagerDetailPage(Condition.getPage(query), projectManagerDetail);
		return R.data(pages);
	}

	/**
	 * 新增 项目下的ERP资产编码
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入projectManagerDetail")
	public R save(@Valid @RequestBody ProjectManagerDetail projectManagerDetail) {
		return R.status(projectManagerDetailService.save(projectManagerDetail));
	}

	/**
	 * 修改 项目下的ERP资产编码
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入projectManagerDetail")
	public R update(@Valid @RequestBody ProjectManagerDetail projectManagerDetail) {
		return R.status(projectManagerDetailService.updateById(projectManagerDetail));
	}

	/**
	 * 新增或修改 项目下的ERP资产编码
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入projectManagerDetail")
	public R submit(@Valid @RequestBody ProjectManagerDetail projectManagerDetail) {
		return R.status(projectManagerDetailService.saveOrUpdate(projectManagerDetail));
	}


	/**
	 * 删除 项目下的ERP资产编码
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(projectManagerDetailService.deleteLogic(Func.toLongList(ids)));
	}
	/**
	 * 数据导入 项目管理
	 */
	@PostMapping("/input")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "数据导入", notes = "传入projectManager")
	public R input() {
		return projectManagerDetailService.input();
	}
	/**
	 * 映射设备编码
	 */
	@PostMapping("/updateDeviceCode")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "映射设备编码", notes = "传入projectManager")
	public R updateDeviceCode() {
		return projectManagerDetailService.updateDeviceCode();
	}

	/**
	 * 导出
	 */
	@PostMapping("/export")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "导出", notes = "传入projectManager")
	public R export(@RequestBody ProjectManagerDetailDTO projectManagerDetailDTO, HttpServletResponse servletResponse) {
		return projectManagerDetailService.export(projectManagerDetailDTO,servletResponse);
	}

}
