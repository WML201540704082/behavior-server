package com.lnsoft.device.api.erp.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.secure.annotation.PreAuth;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.erp.entity.ErpProjectType;
import com.lnsoft.device.api.erp.entity.ErpProjectTypeExcel;
import com.lnsoft.device.api.erp.service.IErpProjectTypeService;
import com.lnsoft.device.api.erp.vo.ErpProjectTypeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * erp项目类型 控制器
 *
 * @author Idevelop
 * @since 2024-03-28
 */
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/erpprojecttype")
@Api(value = "ERP项目类型", tags = "ERP项目类型接口")
public class ErpProjectTypeController extends IdevelopController {

	private IErpProjectTypeService erpProjectTypeService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "详情", notes = "传入erpProjectType")
	public R<ErpProjectType> detail(ErpProjectType erpProjectType) {
		ErpProjectType detail = erpProjectTypeService.getOne(Condition.getQueryWrapper(erpProjectType));
		return R.data(detail);
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "分页", notes = "传入erpProjectType")
	public R<IPage<ErpProjectType>> list(ErpProjectType erpProjectType, Query query) {
		IPage<ErpProjectType> pages = erpProjectTypeService.page(Condition.getPage(query), Condition.getQueryWrapper(erpProjectType));
		return R.data(pages);
	}

	/**
	 * 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "分页", notes = "传入erpProjectType")
	public R<IPage<ErpProjectTypeVO>> page(ErpProjectTypeVO erpProjectType, Query query) {
		IPage<ErpProjectTypeVO> pages = erpProjectTypeService.selectErpProjectTypePage(Condition.getPage(query), erpProjectType);
		return R.data(pages);
	}

	/**
	 * 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增", notes = "传入erpProjectType")
	public R save(@Valid @RequestBody ErpProjectType erpProjectType) {
		return R.status(erpProjectTypeService.save(erpProjectType));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "修改", notes = "传入erpProjectType")
	public R update(@Valid @RequestBody ErpProjectType erpProjectType) {
		return R.status(erpProjectTypeService.updateById(erpProjectType));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增或修改", notes = "传入erpProjectType")
	public R submit(@Valid @RequestBody ErpProjectType erpProjectType) {
		return R.status(erpProjectTypeService.saveOrUpdate(erpProjectType));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(erpProjectTypeService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 导入erp项目类型
	 */
	@PostMapping("/upload")
	@ApiOperationSupport(order = 8)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "导入erp项目类型", notes = "传入 file")
	public R<List<ErpProjectTypeExcel>> uploadEcho(@RequestParam("file") MultipartFile file) {

		try {
			String filename = file.getOriginalFilename();
			if (Objects.isNull(file)) {
				return R.fail("上传文件为空");
			}

			if ((!StringUtils.endsWithIgnoreCase(filename, ".xls") && !StringUtils.endsWithIgnoreCase(filename, ".xlsx")) && !StringUtils.endsWithIgnoreCase(filename, ".xlsm")) {
				throw new ServiceException("请上传正确的excel文件!");
			}
			if (file.getSize()>1024*1024*100){
				return R.fail("文件大小超过限制，最大允许"+ 1024*1024*100 + "MB");
			}
			List<ErpProjectTypeExcel> erpProjectTypes = new ArrayList<>();
			InputStream finalInputStream = file.getInputStream();

			EasyExcel.read(finalInputStream, new AnalysisEventListener<Map<String, String>>() {
				@Override
				public void invoke(Map<String, String> erpProjectType, AnalysisContext analysisContext) {
					erpProjectTypes.add(ErpProjectTypeExcel.builder()
						.projectType(erpProjectType.get(0))
						.projectDesc(erpProjectType.get(1))
						.build());
				}

				@Override
				public void doAfterAllAnalysed(AnalysisContext analysisContext) {
					try {
						finalInputStream.close();
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				}
			}).sheet().doRead();
			return R.data(erpProjectTypes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


}
