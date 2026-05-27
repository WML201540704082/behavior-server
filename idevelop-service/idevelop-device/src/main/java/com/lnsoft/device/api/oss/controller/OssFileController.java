package com.lnsoft.device.api.oss.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.entity.OssFile;
import com.lnsoft.device.api.oss.service.IOssFileService;
import com.lnsoft.device.vo.OssFileVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 信通一体化通用OSS上传文件 控制器
 *
 * @author Idevelop
 * @since 2026-02-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("/oss/file")
@Api(value = "信通一体化通用OSS上传文件", tags = "信通一体化通用OSS上传文件接口")
public class OssFileController extends IdevelopController {

	private IOssFileService ossFileService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入ossFile")
	public R<OssFile> detail(OssFile ossFile) {
		OssFile detail = ossFileService.getOne(Condition.getQueryWrapper(ossFile));
		return R.data(detail);
	}

	/**
	 * 分页 信通一体化通用OSS上传文件
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入ossFile")
	public R<IPage<OssFile>> list(OssFile ossFile, Query query) {

		IdevelopUser user = SecureUtil.getUser();
		if (!StringUtils.equals("37", user.getRegionCode())) {
			ossFile.setCreateUser(user.getUserId());
		}
		LambdaQueryWrapper<OssFile> wrapper = Condition.getQueryWrapper(ossFile).lambda().orderByDesc(OssFile::getCreateTime);

		IPage<OssFile> pages = ossFileService.page(Condition.getPage(query), wrapper);
		return R.data(pages);
	}

	/**
	 * 自定义分页 信通一体化通用OSS上传文件
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入ossFile")
	public R<IPage<OssFileVO>> page(OssFileVO ossFile, Query query) {
		IPage<OssFileVO> pages = ossFileService.selectOssFilePage(Condition.getPage(query), ossFile);
		return R.data(pages);
	}

	/**
	 * 新增 信通一体化通用OSS上传文件
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入ossFile")
	public R save(@Valid @RequestBody OssFile ossFile) {
		return R.status(ossFileService.save(ossFile));
	}

	/**
	 * 修改 信通一体化通用OSS上传文件
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入ossFile")
	public R update(@Valid @RequestBody OssFile ossFile) {
		return R.status(ossFileService.updateById(ossFile));
	}

	/**
	 * 新增或修改 信通一体化通用OSS上传文件
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入ossFile")
	public R submit(@Valid @RequestBody OssFile ossFile) {
		return R.status(ossFileService.saveOrUpdate(ossFile));
	}


	/**
	 * 删除 信通一体化通用OSS上传文件
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(ossFileService.deleteLogic(Func.toLongList(ids)));
	}


}
