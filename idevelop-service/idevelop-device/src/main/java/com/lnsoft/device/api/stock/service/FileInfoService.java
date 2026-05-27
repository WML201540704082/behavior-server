package com.lnsoft.device.api.stock.service;

import com.lnsoft.core.mp.base.BaseService;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.cmdb.entity.FileInfo;
import com.lnsoft.device.api.stock.vo.DownloadInfoVo;

import java.util.List;


public interface FileInfoService extends BaseService<FileInfo> {


	R findList(DownloadInfoVo vo);

	R downloadDelete(List<String> list);
}
