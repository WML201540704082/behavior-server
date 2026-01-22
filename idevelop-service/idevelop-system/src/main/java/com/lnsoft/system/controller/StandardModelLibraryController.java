package com.lnsoft.system.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.BeanUtil;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.system.dto.FeedbackExport;
import com.lnsoft.system.entity.StandardModelLibrary;
import com.lnsoft.system.service.IStandardModelLibraryService;
import com.lnsoft.system.vo.StandardModelLibraryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 标准型号库反馈表 控制器
 *
 * @author Idevelop
 * @since 2025-04-07
 */
@RestController
@AllArgsConstructor
@RequestMapping("/standardmodellibrary")
@Api(value = "标准型号库反馈表", tags = "标准型号库反馈表接口")
public class StandardModelLibraryController extends IdevelopController {

	private IStandardModelLibraryService standardModelLibraryService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入standardModelLibrary")
	public R<StandardModelLibrary> detail(StandardModelLibrary standardModelLibrary) {
		StandardModelLibrary detail = standardModelLibraryService.getOne(Condition.getQueryWrapper(standardModelLibrary));
		return R.data(detail);
	}

//	/**
//	 * 分页 标准型号库反馈表
//	 */
//	@GetMapping("/list")
//	@ApiOperationSupport(order = 2)
//	@ApiOperation(value = "分页", notes = "传入standardModelLibrary")
//	public R<IPage<StandardModelLibrary>> list(StandardModelLibrary standardModelLibrary, Query query) {
//		IPage<StandardModelLibrary> pages = standardModelLibraryService.page(Condition.getPage(query), Condition.getQueryWrapper(standardModelLibrary));
//		return R.data(pages);
//	}

	/**
	 * 自定义分页 标准型号库反馈表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入standardModelLibrary")
	public R<IPage<StandardModelLibrary>> page(StandardModelLibraryVO standardModelLibrary, Query query) {
		IPage<StandardModelLibrary> pages = standardModelLibraryService.selectStandardModelLibraryPage(Condition.getPage(query), standardModelLibrary);
		return R.data(pages);
	}

	/**
	 * 新增 标准型号库反馈表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入standardModelLibrary")
	public R save(@Valid @RequestBody StandardModelLibrary standardModelLibrary) {
		//未采纳
		standardModelLibrary.setIsAccept("0");
		//已提交
		standardModelLibrary.setBackStatus("0");
		return R.status(standardModelLibraryService.save(standardModelLibrary));
	}

	/**
	 * 修改 标准型号库反馈表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入standardModelLibrary")
	public R update(@Valid @RequestBody StandardModelLibrary standardModelLibrary) {
		return R.status(standardModelLibraryService.updateById(standardModelLibrary));
	}

	/**
	 * 采纳
	 */
	@PostMapping("/accept")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "采纳", notes = "传入standardModelLibrary")
	public R accept(@Valid @RequestBody StandardModelLibrary standardModelLibrary) {
		//已采纳
//		feedback.setIsAccept("1");
		//处理中
		standardModelLibrary.setBackStatus("1");
		if ("0".equals(standardModelLibrary.getIsAccept())){
			standardModelLibrary.setBackStatus("2");
			standardModelLibrary.setIsFinish("1");
		}
		return R.status(standardModelLibraryService.updateById(standardModelLibrary));
	}
	/**
	 *  完成
	 */
	@PostMapping("/finish")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "完成", notes = "传入standardModelLibrary")
	public R finish(@Valid @RequestBody StandardModelLibrary standardModelLibrary) {
		//已处理
		standardModelLibrary.setBackStatus("2");
		return R.status(standardModelLibraryService.updateById(standardModelLibrary));
	}

	/**
	 * 数据填充
	 */
	@GetMapping("/load")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "数据填充", notes = "")
	public R<StandardModelLibrary> load(){
		return standardModelLibraryService.load();
	}

	/**
	 * 导出
	 */
	@PostMapping("/export")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "导出")
	public void export(@RequestBody StandardModelLibraryVO standardModelLibraryVO, HttpServletResponse servletResponse){
		LambdaQueryWrapper<StandardModelLibrary> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.likeRight(StringUtil.isNotBlank(standardModelLibraryVO.getBackNum()), StandardModelLibrary::getBackNum, standardModelLibraryVO.getBackNum())
			.eq(StringUtil.isNotBlank(standardModelLibraryVO.getDept()), StandardModelLibrary::getDept, standardModelLibraryVO.getDept())
			.likeRight(StringUtil.isNotBlank(standardModelLibraryVO.getUserName()), StandardModelLibrary::getUserName, standardModelLibraryVO.getUserName())
			.between(ObjectUtil.isNotEmpty(standardModelLibraryVO.getStartTime()) && ObjectUtil.isNotEmpty(standardModelLibraryVO.getEndTime()), StandardModelLibrary::getCreateTime, standardModelLibraryVO.getStartTime(), standardModelLibraryVO.getEndTime())
			.eq(StringUtil.isNotBlank(standardModelLibraryVO.getBackStatus()), StandardModelLibrary::getBackStatus, standardModelLibraryVO.getBackStatus())
			.eq(StringUtil.isNotBlank(standardModelLibraryVO.getIsAccept()), StandardModelLibrary::getIsAccept, standardModelLibraryVO.getIsAccept())
			.orderByDesc(StandardModelLibrary::getCreateTime);
		List<StandardModelLibrary> list = standardModelLibraryService.list(queryWrapper);
		//todo
		List<FeedbackExport> feedbackExports = new ArrayList<>();
		for (StandardModelLibrary feedback : list) {
			if ("0".equals(feedback.getBackStatus())){
				feedback.setBackStatus("已提交");
			}else if ("1".equals(feedback.getBackStatus())){
				feedback.setBackStatus("处理中");
			}else if ("2".equals(feedback.getBackStatus())){
				feedback.setBackStatus("已处理");
			}
			if ("0".equals(feedback.getIsAccept())){
				feedback.setIsAccept("未采纳");
			}else if ("1".equals(feedback.getIsAccept())){
				feedback.setIsAccept("已采纳");
			}

			FeedbackExport feedbackExport = new FeedbackExport();
			BeanUtil.copy(feedback,feedbackExport);
			feedbackExports.add(feedbackExport);
		}
		try {
			servletResponse.setContentType("application/vnd.ms-excel;charset=utf-8");
			servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
			String fileName = URLEncoder.encode("用户反馈导出", StandardCharsets.UTF_8.name());
			servletResponse.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
			EasyExcel.write(servletResponse.getOutputStream(), FeedbackExport.class).sheet("用户反馈").doWrite(feedbackExports);
		} catch (IOException e) {
			throw new ServiceException(e.getMessage());
		}
	}


	/**
	 * 删除 标准型号库反馈表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(standardModelLibraryService.deleteLogic(Func.toLongList(ids)));
	}


}
