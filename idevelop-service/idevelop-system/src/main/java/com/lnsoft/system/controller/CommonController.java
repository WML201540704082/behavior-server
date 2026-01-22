package com.lnsoft.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.service.ICommonService;
import com.lnsoft.system.service.IDeptService;
import com.lnsoft.system.user.vo.UserVO;
import com.lnsoft.system.vo.DeptVO;
import com.lnsoft.system.vo.UserInfoVO;
import com.lnsoft.system.wrapper.DeptWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/common")
@Api(value = "公共接口", tags = "公共接口")
public class CommonController {

	@Resource
	private ICommonService commonService;
	@Resource
	private IDeptService deptService;

	/**
	 * 获取当前登录用户信息
	 */
	@GetMapping("/user/info")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取当前登录用户信息")
	public R<UserInfoVO> getUserInfo() {
		return commonService.getUserInfo();
	}

	/**
	 * 获取部门用户信息
	 */
	@GetMapping("/dept/user/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "获取部门用户信息")
	public R<List<UserVO>> getDeptUserList(@ApiParam(value = "部门id", required = true) Long deptId) {
		return commonService.getDeptUserList(deptId);
	}


	/**
	 * 列表
	 */
	@GetMapping("/user/dept")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptName", value = "部门名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "fullName", value = "部门全称", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "列表", notes = "传入dept")
	public R<List<DeptVO>> list(@ApiIgnore @RequestParam Map<String, Object> dept, IdevelopUser idevelopUser) {
		QueryWrapper<Dept> queryWrapper = Condition.getQueryWrapper(dept, Dept.class);
		List<Dept> list = deptService.list((!idevelopUser.getTenantId().equals(IdevelopConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Dept::getTenantId, idevelopUser.getTenantId()) : queryWrapper);
		List<DeptVO> deptVo = DeptWrapper.build().listNodeVO(list);
		return R.data(deptVo);
	}

}
