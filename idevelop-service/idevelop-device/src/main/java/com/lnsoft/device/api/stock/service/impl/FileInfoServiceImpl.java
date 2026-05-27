package com.lnsoft.device.api.stock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.oss.AliossTemplate;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.cmdb.entity.FileInfo;
import com.lnsoft.device.api.cmdb.mapper.FileInfoMapper;
import com.lnsoft.device.api.stock.service.FileInfoService;
import com.lnsoft.device.api.stock.vo.DownloadInfoVo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
@AllArgsConstructor
public class FileInfoServiceImpl extends BaseServiceImpl<FileInfoMapper, FileInfo> implements FileInfoService {

	@Resource
	private AliossTemplate aliossTemplate;

	@Override
	public R findList(DownloadInfoVo vo) {
		IPage<FileInfo> iPage = new Page<>();
		iPage.setSize(vo.getPage().getSize());
		iPage.setCurrent(vo.getPage().getCurrent());
		IPage<FileInfo> infoIPage = baseMapper.selectPage(iPage,
			new QueryWrapper<FileInfo>().eq("create_user",vo.getUserId())
				.orderByDesc("create_time"));
		List<FileInfo> records = infoIPage.getRecords();
		for (FileInfo record : records) {
			String link = record.getLink();
			if (StringUtils.isNotEmpty(record.getLink())) {
				String filePath = aliossTemplate.convertToCurrentModePath(link);
				record.setLink(filePath);
			}
		}
		return R.data(infoIPage);
	}

	@Override
	public R downloadDelete(List<String> list) {
		for (String id : list){
			FileInfo info = baseMapper.selectById(id);
			baseMapper.deleteById(id);
			try {
				aliossTemplate.removeFile(info.getOssName());
			}catch (Exception e){
				log.error(e.getMessage());
			}
		}
		return R.success("success");
	}
}
