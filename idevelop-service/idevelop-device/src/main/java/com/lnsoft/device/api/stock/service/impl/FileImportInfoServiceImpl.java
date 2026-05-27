package com.lnsoft.device.api.stock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.cmdb.entity.FileImportInfo;
import com.lnsoft.device.api.cmdb.mapper.FileImportInfoMapper;
import com.lnsoft.device.api.stock.service.FileImportInfoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@AllArgsConstructor
public class FileImportInfoServiceImpl extends BaseServiceImpl<FileImportInfoMapper, FileImportInfo> implements FileImportInfoService {

	// 按类型和用户查询
	@Override
	public R findList(Page page,FileImportInfo importInfo) {
		IPage<FileImportInfo> iPage = new Page<>();
		iPage.setSize(page.getSize());
		iPage.setCurrent(page.getCurrent());
		QueryWrapper<FileImportInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("create_user",importInfo.getCreateUser());
		IPage<FileImportInfo> infoIPage = baseMapper.selectPage(iPage,queryWrapper);
		return R.data(infoIPage);
	}

	@Override
	public R downloadDelete(FileImportInfo importInfo) {
		baseMapper.deleteById(importInfo);
		return R.success("success");
	}
}
