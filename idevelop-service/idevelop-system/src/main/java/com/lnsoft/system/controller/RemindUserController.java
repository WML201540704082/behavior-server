package com.lnsoft.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.system.entity.RemindUser;
import com.lnsoft.system.service.IRemindUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 通知-用户绑定表 控制器
 *
 * @author Idevelop
 * @since 2024-09-27
 */
@RestController
@AllArgsConstructor
@RequestMapping("/reminduser")
@Api(value = "通知-用户绑定表", tags = "通知-用户绑定表接口")
public class RemindUserController extends IdevelopController {

	private IRemindUserService remindUserService;



	/**
	 * 新增 通知-用户绑定表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入remindUser")
	public R save(@Valid @RequestBody RemindUser remindUser) {
		return R.status(remindUserService.save(remindUser));
	}



}
