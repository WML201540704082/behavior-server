
package com.lnsoft.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.annotation.PreAuth;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.service.IDeptService;
import com.lnsoft.system.vo.DeptVO;
import com.lnsoft.system.wrapper.DeptWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * @author guozhao
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dept")
@Api(value = "部门", tags = "部门")
public class DeptController extends IdevelopController {

	private IDeptService deptService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "详情", notes = "传入dept")
	public R<DeptVO> detail(Dept dept) {
		Dept detail = deptService.getOne(Condition.getQueryWrapper(dept));
		return R.data(DeptWrapper.build().entityVO(detail));
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptName", value = "部门名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "fullName", value = "部门全称", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@PreAuth("hasPerm('system:dept:list')")
	@ApiOperation(value = "列表", notes = "传入dept")
	public R<List<DeptVO>> list(@ApiIgnore @RequestParam Map<String, Object> dept, IdevelopUser idevelopUser) {
		QueryWrapper<Dept> queryWrapper = Condition.getQueryWrapper(dept, Dept.class);
		List<Dept> list = deptService.list((!idevelopUser.getTenantId().equals(IdevelopConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Dept::getTenantId, idevelopUser.getTenantId()) : queryWrapper);
		List<DeptVO> deptVo = DeptWrapper.build().listNodeVO(list);
		return R.data(deptVo);
	}

	/**
	 * 获取部门树形结构
	 *
	 * @return
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 3)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<DeptVO>> tree(String tenantId, IdevelopUser idevelopUser) {
		List<DeptVO> tree = deptService.tree(Func.toStr(tenantId, idevelopUser.getTenantId()));
		return R.data(tree);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 4)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增或修改", notes = "传入dept")
	public R submit(@Valid @RequestBody Dept dept) {
		return R.status(deptService.submit(dept));
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 5)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(deptService.removeByIds(Func.toLongList(ids)));
	}

	@GetMapping("/queryDept")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "查询所属单位", notes = "树形结构")
	public R<List<Dept>> queryDept() {
		return R.data(deptService.queryDept());
	}

	/**
	 * 获取部门树形结构
	 *
	 * @return R
	 */
	@GetMapping("/tree/list")
	@ApiOperationSupport(order = 7)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "获取部门树形结构", notes = "树形结构")
	public R<DeptVO> treeList() {
		return deptService.treeList();
	}


	/**
	 * 刷新ISC单位和部门数据
	 *
	 * @param type
	 * @return
	 */
	@GetMapping("/refresh")
	@ApiOperationSupport(order = 8)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "刷新ISC部门数据", notes = "type:CORP/DEPT")
	public R<Boolean> refresh(String type) {
		return R.data(deptService.refresh(type));
	}

	/**
	 * 懒加载 单位和部门列表
	 */
	@GetMapping("/lazy/list")
	@ApiOperationSupport(order = 9)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", paramType = "query", dataType = "long"),
		@ApiImplicitParam(name = "parentId", value = "父主键ID", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "type", value = "类型 CORP/DEPT/TEAM   单位/部门/班组", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "deptName", value = "部门名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "fullName", value = "部门全称", paramType = "query", dataType = "string")
	})
	@ApiOperation(value = "懒加载 单位和部门列表", notes = "传入dept")
	public R<List<DeptVO>> lazyList(@ApiIgnore @RequestParam Map<String, Object> dept, IdevelopUser idevelopUser) {
		QueryWrapper<Dept> queryWrapper = Condition.getQueryWrapper(dept, Dept.class);
		List<Dept> list = deptService.list((!idevelopUser.getTenantId().equals(IdevelopConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Dept::getTenantId, idevelopUser.getTenantId()) : queryWrapper);
		List<DeptVO> deptVo = DeptWrapper.build().listNodeVO(list);
		return R.data(deptVo);
	}

}
