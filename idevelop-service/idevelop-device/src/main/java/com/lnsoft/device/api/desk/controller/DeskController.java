package com.lnsoft.device.api.desk.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.desk.service.IDeskService;
import com.lnsoft.device.api.desk.vo.DeskOrderNumVO;
import com.lnsoft.device.api.desk.vo.DictValueVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/desk")
@Api(value = "个人工作台", tags = "个人工作台")
public class DeskController {

	@Resource
	private IDeskService deskService;

	/**
	 * 获取个人工作台工单数量
	 */
	@GetMapping("/query/order/num")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取个人工作台工单数量")
	public R<DeskOrderNumVO> queryOrderNum() throws Exception {
		return R.data(deskService.queryOrderNum());
	}

	/**
	 * 获取工单节点字典
	 */
	@GetMapping("/device/record/dict")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "获取工单节点字典", notes = "传入工单类型")
	public R<List<DictValueVO>> deviceRecordDict(@ApiParam(value = "工单类型", required = true) @RequestParam String orderType, @ApiParam(value = "工单编号") String orderNo) {
		return R.data(deskService.deviceRecordDict(orderType, orderNo));
	}
	/**
	 *  短信测试
	 */
	@PostMapping("/send")
	public R send(String phone,String msg){
		return deskService.send(phone,msg);
	}
}
