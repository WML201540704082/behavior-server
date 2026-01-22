package com.lnsoft.ipc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.ipc.dto.IpcUserDTO;
import com.lnsoft.ipc.entity.IpcUserTime;
import com.lnsoft.ipc.service.IIpcUserTimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.ipc.entity.IpcUser;
import com.lnsoft.ipc.vo.IpcUserVO;
import com.lnsoft.ipc.service.IIpcUserService;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 工控机管控--用户表 控制器
 *
 * @author Idevelop
 * @since 2025-11-17
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ipcuser")
@Api(value = "工控机管控--用户表", tags = "工控机管控--用户表接口")
public class IpcUserController extends IdevelopController {

	private IIpcUserService ipcUserService;

	private IIpcUserTimeService iIpcUserTimeService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入ipcUser")
	public R<IpcUser> detail(IpcUser ipcUser) {
		IpcUser detail = ipcUserService.getOne(Condition.getQueryWrapper(ipcUser));
		LambdaQueryWrapper<IpcUserTime> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(IpcUserTime::getUserId,ipcUser.getId());
		List<IpcUserTime> list = iIpcUserTimeService.list(queryWrapper);
		detail.setTimeList(list);
		return R.data(detail);
	}

	/**
	 * 分页 工控机管控--用户表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入ipcUser")
	public R<IPage<IpcUser>> list(IpcUser ipcUser, Query query) {
		IPage<IpcUser> pages = ipcUserService.page(Condition.getPage(query), Condition.getQueryWrapper(ipcUser));
		for (IpcUser record : pages.getRecords()) {
			LambdaQueryWrapper<IpcUserTime> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(IpcUserTime::getUserId,record.getId());
			List<IpcUserTime> list = iIpcUserTimeService.list(queryWrapper);
			record.setTimeList(list);
		}
		return R.data(pages);
	}


	/**
	 * 新增 工控机管控--用户表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入ipcUser")
	public R save(@Valid @RequestBody IpcUser ipcUser) {
		ipcUser.setIsSync("1");
		if ("管理员".equals(ipcUser.getUserType())){
			String terminal = ipcUser.getTerminal();
			LambdaQueryWrapper<IpcUser> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(IpcUser::getTerminal,terminal).eq(IpcUser::getIsDeleted,0);
			IpcUser user = ipcUserService.getOne(queryWrapper);
			if (ObjectUtil.isNotEmpty(user)){
				return R.fail("该终端ip下已有管理员账户，不可新增");
			}
		}
		return ipcUserService.insert(ipcUser);
	}

	/**
	 * 修改 工控机管控--用户表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入ipcUser")
	public R update(@Valid @RequestBody IpcUser ipcUser) {

		return ipcUserService.edit(ipcUser);
	}



	/**
	 * 删除 工控机管控--用户表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@RequestBody IpcUser ipcUser) {
		return ipcUserService.delete(ipcUser);
	}

	@PostMapping("/upload")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "用户人脸上传接口", notes = "传入file")
	public R<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
		R<String> result = ipcUserService.upload(file);
		return result;
	}

	@GetMapping("/userRank")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "用户使用时间排名", notes = "")
	public R<List<IpcUserVO>> userRank(IpcUserDTO ipcUserDTO) throws IOException {
		return ipcUserService.userRank(ipcUserDTO);
	}

}
