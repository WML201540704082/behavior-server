package com.lnsoft.device.api.res.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.entity.ApproveRecord;
import com.lnsoft.device.api.res.service.IApproveRecordService;
import com.lnsoft.device.api.res.vo.ApproveRecordVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 审批流程记录表 控制器
 *
 * @author Idevelop
 * @since 2024-02-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/approve/record")
@Api(value = "审批流程记录表", tags = "审批流程记录表接口")
public class ApproveRecordController extends IdevelopController {

	private IApproveRecordService approveRecordService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入approveRecord")
	public R<ApproveRecord> detail(ApproveRecord approveRecord) {
		ApproveRecord detail = approveRecordService.getOne(Condition.getQueryWrapper(approveRecord));
		return R.data(detail);
	}

	/**
	 * 分页 审批流程记录表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入approveRecord")
	public R<List<ApproveRecord>> list(ApproveRecord approveRecord, Query query) {
		return approveRecordService.approveRecordList(Condition.getPage(query), approveRecord);
	}

	/**
	 * 自定义分页 审批流程记录表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入approveRecord")
	public R<IPage<ApproveRecordVO>> page(ApproveRecordVO approveRecord, Query query) {
		IPage<ApproveRecordVO> pages = approveRecordService.selectApproveRecordPage(Condition.getPage(query), approveRecord);
		return R.data(pages);
	}

	/**
	 * 新增 审批流程记录表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入approveRecord")
	public R save(@Valid @RequestBody ApproveRecord approveRecord) {
		return R.status(approveRecordService.save(approveRecord));
	}

	/**
	 * 修改 审批流程记录表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入approveRecord")
	public R update(@Valid @RequestBody ApproveRecord approveRecord) {
		return R.status(approveRecordService.updateById(approveRecord));
	}

	/**
	 * 新增或修改 审批流程记录表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入approveRecord")
	public R submit(@Valid @RequestBody ApproveRecord approveRecord) {
		return R.status(approveRecordService.saveOrUpdate(approveRecord));
	}


	/**
	 * 删除 审批流程记录表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(approveRecordService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 新增 审批流程记录
	 */
	@GetMapping("/add")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "新增", notes = "approveRecord")
	public R approveRecordAdd(@RequestParam("nodeId") String nodeId, @RequestParam("nodeName") String nodeName,
							  @RequestParam("optRole") String optRole, @RequestParam("optTitle") String optTitle,
							  @RequestParam("optOpinion") String optOpinion, @RequestParam("filingNo") String filingNo) {
		return R.status(approveRecordService.approveRecordAdd(nodeId, nodeName, optRole, optTitle, optOpinion, filingNo));
	}


}
