
package com.lnsoft.ipc.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.ipc.demo.mapper.NoticeMapper;
import com.lnsoft.ipc.demo.service.INoticeService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.ipc.demo.entity.Notice;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author guozhao
 */
@Service
public class NoticeServiceImpl extends BaseServiceImpl<NoticeMapper, Notice> implements INoticeService {

	@Override
	public IPage<Notice> selectNoticePage(IPage<Notice> page, Notice notice) {
		return page.setRecords(baseMapper.selectNoticePage(page, notice));
	}

}
