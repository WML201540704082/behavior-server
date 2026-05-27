package com.lnsoft.device.api.endpoint.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.annotation.ApiLog;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.secure.annotation.PreAuth;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.endpoint.dto.EndpointApplyDTO;
import com.lnsoft.common.enums.hussar.InterfaceBpmNodeEnum;
import com.lnsoft.device.api.endpoint.entity.EndpointApply;
import com.lnsoft.device.api.endpoint.entity.EndpointPortVO;
import com.lnsoft.device.api.endpoint.service.IEndpointApplyService;
import com.lnsoft.device.api.endpoint.vo.EndpointApplyVO;
import com.lnsoft.device.api.warehouse.dto.OrderUpdateStatusDTO;
import com.lnsoft.system.user.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 数据共享接口申请表 控制器
 *
 * @author Idevelop
 * @since 2024-07-17
 */
@RestController
@AllArgsConstructor
@RequestMapping("/endpoint/apply")
@Api(value = "数据共享接口申请表", tags = "数据共享接口申请表接口")
public class EndpointApplyController extends IdevelopController {

	private IEndpointApplyService endpointApplyService;

	/**
	 * 详情
	 */
	@ApiLog("数据共享接口申请-详情")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@PreAuth("hasPerm('endpoint:dataApply:view')")
	@ApiOperation(value = "详情", notes = "传入endpointApply")
	public R<EndpointApplyVO> detail(EndpointApply endpointApply) {
		EndpointApply detail = endpointApplyService.getOne(Condition.getQueryWrapper(endpointApply));
		EndpointApplyVO endpointApplyVO = new EndpointApplyVO();
		BeanUtils.copyProperties(detail, endpointApplyVO);
		EndpointPortVO portId = endpointApplyService.getPortById(detail);
		endpointApplyVO.setPortId(portId.getId());
		endpointApplyVO.setEndpointPortVOList(portId);
		return R.data(endpointApplyVO);
	}

	/**
	 * 分页 数据共享接口申请表
	 */
	@ApiLog("数据共享接口申请-分页")
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@PreAuth("hasPerm('endpoint:dataApply:list')")
	@ApiOperation(value = "分页", notes = "传入endpointApply")
	public R<IPage<EndpointApply>> list(EndpointApply endpointApply, Query query) {
		IPage<EndpointApply> pages = endpointApplyService.page(Condition.getPage(query), Condition.getQueryWrapper(endpointApply));
		return R.data(pages);
	}

	/**
	 * 自定义分页 数据共享接口申请表
	 */
	@ApiLog("数据共享接口申请-自定义分页")
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@PreAuth("hasPerm('endpoint:dataApply:list')")
	@ApiOperation(value = "分页", notes = "传入endpointApply")
	public R<IPage<EndpointApplyVO>> page(EndpointApplyVO endpointApply, Query query) {
		IPage<EndpointApplyVO> pages = endpointApplyService.selectEndpointApplyPage(Condition.getPage(query), endpointApply);
		return R.data(pages);
	}

	/**
	 * 接口申请暂存 数据共享接口申请表
	 */
	@ApiLog("数据共享接口申请-接口申请暂存")
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@PreAuth("hasPerm('endpoint:dataApply:add')")
	@ApiOperation(value = "接口申请暂存", notes = "传入endpointApply")
	public R<EndpointApplyVO> save(@Valid @RequestBody EndpointApplyDTO endpointApply) {
		EndpointApplyVO endpoint = endpointApplyService.staging(endpointApply);
		return R.data(endpoint);
	}

	/**
	 * 接口申请提交 数据共享接口申请表
	 */
	@ApiLog("数据共享接口申请-接口申请提交")
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@PreAuth("hasPerm('endpoint:dataApply:add')")
	@ApiOperation(value = "接口申请提交", notes = "传入endpointApply")
	public R submit(@Valid @RequestBody EndpointApplyDTO endpointApply) throws Exception {
		EndpointApplyVO endpoint = endpointApplyService.submit(endpointApply);
		return R.data(endpoint);
	}

	/**
	 * 审批
	 */
	@ApiLog("数据共享接口申请-审批")
	@PostMapping("/approval")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "审批", notes = "传入endpointApply")
	public R approval(@Valid @RequestBody OrderUpdateStatusDTO orderUpdateStatusDTO) throws Exception {
		return endpointApplyService.approval(orderUpdateStatusDTO);
	}

	/**
	 * 删除 数据共享接口申请表
	 */
	@ApiLog("数据共享接口申请-删除")
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@PreAuth("hasPerm('endpoint:dataApply:delete')")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(endpointApplyService.deleteLogic(Func.toLongList(ids)));
	}

	@ApiLog("数据共享接口申请-获取枚举字典")
	@GetMapping("/getEnums")
	@ApiOperationSupport(order = 8)
	@ApiOperation("获取枚举字典")
	@PreAuth("hasPerm('endpoint:dataApply:list')")
	public R<String> getEnums(String node) {
		String message = InterfaceBpmNodeEnum.getMessage(node);
		return R.data(message);
	}

	@ApiLog("数据共享接口申请-userList")
	@GetMapping("/userList")
	@PreAuth("hasPerm('endpoint:dataApply:list')")
	public R<List<User>> getUserList(String name) {
		return endpointApplyService.getUserList(name);
	}


}
