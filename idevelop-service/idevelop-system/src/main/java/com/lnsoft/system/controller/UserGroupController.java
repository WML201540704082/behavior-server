package com.lnsoft.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.secure.annotation.PreAuth;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.node.ForestNodeMerger;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.system.dto.DeptDTO;
import com.lnsoft.system.entity.UserGroup;
import com.lnsoft.system.service.IDeptService;
import com.lnsoft.system.service.IUserGroupService;
import com.lnsoft.system.vo.DeptVO;
import com.lnsoft.system.vo.UserGroupVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 班组表 控制器
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user/group")
@Api(value = "班组表", tags = "班组表接口")
public class UserGroupController extends IdevelopController {

	private IUserGroupService userGroupService;
	private IDeptService deptService;
	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "详情", notes = "传入userGroup")
	public R<UserGroup> detail(UserGroup userGroup) {
		UserGroup detail = userGroupService.getOne(Condition.getQueryWrapper(userGroup));
		return R.data(detail);
	}

	/**
	 *  班组表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入userGroup")
	@PreAuth("hasPerm('system:common:all')")
	public R<List<UserGroup>> list(UserGroup userGroup) {
		QueryWrapper<UserGroup> queryWrapper = Condition.getQueryWrapper(userGroup).orderByAsc("sort");
		List<UserGroup> list = userGroupService.list(queryWrapper);
		return R.data(list);
	}

	/**
	 * 自定义分页 班组表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入userGroup")
	@PreAuth("hasPerm('system:common:all')")
	public R<IPage<UserGroupVO>> page(UserGroupVO userGroup, Query query) {
		IPage<UserGroupVO> pages = userGroupService.selectUserGroupPage(Condition.getPage(query), userGroup);
		return R.data(pages);
	}
	/**
	 * 获取部门+班组的树形结构
	 *
	 * @return
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<DeptVO>> tree(String tenantId, DeptDTO deptDTO) {
		//获取指定的目录下所有分型
		List<DeptVO> list = deptService.tree(deptDTO);
		list.addAll( userGroupService.tree(deptDTO) );
		List<DeptVO> tree = new ArrayList<>();
		tree = ForestNodeMerger.merge(list);
		return R.data(tree);
	}

	/**
	 * 新增 班组表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "新增", notes = "传入userGroup")
	public R save(@Valid @RequestBody UserGroup userGroup) {
		return R.status(userGroupService.save(userGroup));
	}

	/**
	 * 修改 班组表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "修改", notes = "传入userGroup")
	public R update(@Valid @RequestBody UserGroup userGroup) {
		return R.status(userGroupService.updateById(userGroup));
	}

	/**
	 * 新增或修改 班组表
	 */
	@PostMapping("/submit")
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入userGroup")
	public R submit(@Valid @RequestBody UserGroup userGroup) {
		return R.status(userGroupService.saveOrUpdate(userGroup));
	}


	/**
	 * 删除 班组表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@PreAuth("hasPerm('system:common:all')")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(userGroupService.deleteLogic(Func.toLongList(ids)));
	}

}
