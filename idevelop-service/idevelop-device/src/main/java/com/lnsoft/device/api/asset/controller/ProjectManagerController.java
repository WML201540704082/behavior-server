package com.lnsoft.device.api.asset.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.secure.annotation.PreAuth;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.asset.dto.ProjectManagerExportDTO;
import com.lnsoft.device.api.asset.entity.ProjectManager;
import com.lnsoft.device.api.asset.entity.ProjectManagerErp;
import com.lnsoft.device.api.asset.service.IProjectManagerService;
import com.lnsoft.device.api.asset.vo.ProjectManagerErpVO;
import com.lnsoft.device.api.asset.vo.ProjectManagerVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目管理 控制器
 *
 * @author xuel
 * @since 2024-03-04
 */
@RestController
@AllArgsConstructor
@RequestMapping("/projectmanager")
@Api(value = "项目管理", tags = "项目管理接口")
public class ProjectManagerController extends IdevelopController {

	private IProjectManagerService projectManagerService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@PreAuth("hasPerm('deivce:projectManage:view')")
	@ApiOperation(value = "详情", notes = "传入projectManager")
	public R<ProjectManager> detail(ProjectManager projectManager) {
		ProjectManager detail = projectManagerService.getOne(Condition.getQueryWrapper(projectManager));
		return R.data(detail);
	}

	/**
	 * 分页 项目管理
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@PreAuth("hasPerm('deivce:projectManage:list')")
	@ApiOperation(value = "分页", notes = "传入projectManager")
	public R<IPage<ProjectManager>> list(ProjectManager projectManager, Query query) {
		projectManager.setLoevm("Y");
		IPage<ProjectManager> pages = projectManagerService.page(Condition.getPage(query), Condition.getQueryWrapper(projectManager));
		return R.data(pages);
	}

	/**
	 * 自定义分页 项目管理
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@PreAuth("hasPerm('deivce:projectManage:list')")
	@ApiOperation(value = "分页", notes = "传入projectManager")
	public R<IPage<ProjectManagerVO>> page(ProjectManagerVO projectManager, Query query) {
		IPage<ProjectManagerVO> pages = projectManagerService.selectProjectManagerPage(Condition.getPage(query), projectManager);
		return R.data(pages);
	}

	/**
	 * 新增 项目管理
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@PreAuth("hasPerm('deivce:projectManage:add')")
	@ApiOperation(value = "新增", notes = "传入projectManager")
	public R save(@Valid @RequestBody ProjectManager projectManager) {
		return R.status(projectManagerService.save(projectManager));
	}

	/**
	 * 修改 项目管理
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@PreAuth("hasPerm('deivce:projectManage:edit')")
	@ApiOperation(value = "修改", notes = "传入projectManager")
	public R update(@Valid @RequestBody ProjectManager projectManager) {
		return R.status(projectManagerService.updateById(projectManager));
	}

	/**
	 * 新增或修改 项目管理
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@PreAuth("hasPerm('deivce:projectManage:add')")
	@ApiOperation(value = "新增或修改", notes = "传入projectManager")
	public R submit(@Valid @RequestBody ProjectManager projectManager) {
		return R.status(projectManagerService.saveOrUpdate(projectManager));
	}


	/**
	 * 删除 项目管理
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@PreAuth("hasPerm('deivce:projectManage:delete')")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(projectManagerService.deleteLogic(Func.toLongList(ids)));
	}


	/**
	 * 分页 项目管理的ERP数据
	 */
	@GetMapping("/erp/list")
	@ApiOperationSupport(order = 8)
	@PreAuth("hasPerm('deivce:projectManage:list')")
	@ApiOperation(value = "分页 项目管理的ERP集合", notes = "传入 projectManagerErp")
	public R<IPage<ProjectManagerErpVO>> getErpList(ProjectManagerErp projectManagerErp) {
		IPage<ProjectManagerErpVO> pages = projectManagerService.getErpList(projectManagerErp);
		return R.data(pages);
	}

	/**
	 * 导出
	 */
	@SneakyThrows
	@PostMapping("/export")
	@ApiOperationSupport(order = 9)
	@PreAuth("hasPerm('deivce:projectManage:export')")
	@ApiOperation(value = "根据条件导出 项目管理", notes = "projectManager")
	public void export(@RequestBody ProjectManagerVO projectManager, Query query, HttpServletResponse response)  {
		try {
			IPage<ProjectManagerVO> pages = projectManagerService.selectProjectManagerPage(Condition.getPage(query), projectManager);
			List<ProjectManagerExportDTO> dtoList = pages.getRecords().stream().map(vo -> {
				ProjectManagerExportDTO build = ProjectManagerExportDTO.builder()
					.projectDefine(vo.getProjectDefine())
					.projectUnitName(vo.getProjectUnitName())
					.wbsCode(vo.getWbsCode())
					.wbsName(vo.getWbsName())
					.build();
				return build;
			}).collect(Collectors.toList());

			try {
				response.setContentType("application/vnd.ms-excel");
				response.setCharacterEncoding(StandardCharsets.UTF_8.name());
				String fileName = URLEncoder.encode("项目管理列表导出", StandardCharsets.UTF_8.name());
				response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
				EasyExcel.write(response.getOutputStream(), ProjectManagerExportDTO.class).sheet("项目管理表格").doWrite(dtoList);
			} catch (IOException e) {
				throw new ServiceException(e.getMessage());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 数据导入 项目管理
	 */
	@PostMapping("/input")
	@ApiOperationSupport(order = 4)
	@PreAuth("hasPerm('deivce:projectManage:import')")
	@ApiOperation(value = "数据导入", notes = "传入projectManager")
	public R input() {
		return R.status(projectManagerService.input());
	}

}
