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
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.entity.Feedback;
import com.lnsoft.system.service.IFeedbackService;
import com.lnsoft.system.vo.FeedbackVO;
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
 * 用户反馈 控制器
 *
 * @author Idevelop
 * @since 2025-03-26
 */
@RestController
@AllArgsConstructor
@RequestMapping("/feedback")
@Api(value = "用户反馈", tags = "用户反馈接口")
public class FeedbackController extends IdevelopController {

	private IFeedbackService feedbackService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入feedback")
	public R<Feedback> detail(Feedback feedback) {
		Feedback detail = feedbackService.getOne(Condition.getQueryWrapper(feedback));
		return R.data(detail);
	}

	/**
	 * 自定义分页 用户反馈
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入feedback")
	public R<IPage<Feedback>> page(FeedbackVO feedback, Query query) {
		IPage<Feedback> pages = feedbackService.selectFeedbackPage(Condition.getPage(query), feedback);
		return R.data(pages);
	}

	/**
	 * 新增 用户反馈
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order =3)
	@ApiOperation(value = "新增", notes = "传入feedback")
	public R save(@Valid @RequestBody Feedback feedback) {
		//未采纳
		feedback.setIsAccept("0");
		//已提交
		feedback.setBackStatus("0");
		return R.status(feedbackService.save(feedback));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "修改", notes = "传入feedback")
	public R update(@Valid @RequestBody Feedback feedback) {
		return R.status(feedbackService.updateById(feedback));
	}

	/**
	 * 采纳
	 */
	@PostMapping("/accept")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "采纳", notes = "传入feedback")
	public R accept(@Valid @RequestBody Feedback feedback) {
		//已采纳
//		feedback.setIsAccept("1");
		//处理中
		feedback.setBackStatus("1");
		if ("0".equals(feedback.getIsAccept())){
			feedback.setBackStatus("2");
			feedback.setIsFinish("1");
		}
		return R.status(feedbackService.updateById(feedback));
	}
	/**
	 *  完成
	 */
	@PostMapping("/finish")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "完成", notes = "传入feedback")
	public R finish(@Valid @RequestBody Feedback feedback) {
		//已处理
		feedback.setBackStatus("2");
		return R.status(feedbackService.updateById(feedback));
	}


	/**
	 * 删除 用户反馈
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(feedbackService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 反馈单位列表
	 */
	@GetMapping("/dept")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "单位列表", notes = "")
	public R<List<Dept>> getDeptList(){
		return feedbackService.getDeptList();
	}
	/**
	 * 数据填充
	 */
	@GetMapping("/load")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "数据填充", notes = "")
	public R<Feedback> load(){
		return feedbackService.load();
	}

	/**
	 * 导出
	 */
	@PostMapping("/export")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "导出")
	public void export(@RequestBody FeedbackVO feedbackVO,HttpServletResponse servletResponse){
		LambdaQueryWrapper<Feedback> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.likeRight(StringUtil.isNotBlank(feedbackVO.getBackNum()), Feedback::getBackNum, feedbackVO.getBackNum())
			.eq(StringUtil.isNotBlank(feedbackVO.getDept()), Feedback::getDept, feedbackVO.getDept())
			.likeRight(StringUtil.isNotBlank(feedbackVO.getUserName()), Feedback::getUserName, feedbackVO.getUserName())
			.between(ObjectUtil.isNotEmpty(feedbackVO.getStartTime()) && ObjectUtil.isNotEmpty(feedbackVO.getEndTime()), Feedback::getCreateTime, feedbackVO.getStartTime(), feedbackVO.getEndTime())
			.eq(StringUtil.isNotBlank(feedbackVO.getBackStatus()), Feedback::getBackStatus, feedbackVO.getBackStatus())
			.eq(StringUtil.isNotBlank(feedbackVO.getIsAccept()), Feedback::getIsAccept, feedbackVO.getIsAccept())
			.like(StringUtil.isNotBlank(feedbackVO.getBackDetail()), Feedback::getBackDetail, feedbackVO.getBackDetail())
			.orderByDesc(Feedback::getCreateTime);
		List<Feedback> list = feedbackService.list(queryWrapper);
		List<FeedbackExport> feedbackExports = new ArrayList<>();
		for (Feedback feedback : list) {
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
			if ("0".equals(feedback.getBackType())){
				feedback.setBackType("系统问题");
			}else if ("1".equals(feedback.getBackType())){
				feedback.setBackType("用户体验");
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


}
