package com.lnsoft.device.api.stock.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lnsoft.core.mp.base.BaseService;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.cmdb.entity.FileImportInfo;
public interface FileImportInfoService extends BaseService<FileImportInfo> {


	// 分页列表
	R findList(Page page,FileImportInfo importInfo);

	// 删除
	R downloadDelete(FileImportInfo importInfo);
}
