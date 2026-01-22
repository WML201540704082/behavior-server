package com.lnsoft.ipc.demo.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.lnsoft.ipc.demo.entity.Notice;
import com.lnsoft.ipc.demo.mapper.NoticeMapper;
import com.lnsoft.ipc.demo.service.IDynamicService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DynamicServiceImpl
 *
 * @author guozhao
 */
@Service
public class DynamicServiceImpl extends BaseServiceImpl<NoticeMapper, Notice> implements IDynamicService {

	@Override
	public List<Notice> masterList() {
		return this.list();
	}

	@Override
	@DS("slave")
	public List<Notice> slaveList() {
		return this.list();
	}
}
