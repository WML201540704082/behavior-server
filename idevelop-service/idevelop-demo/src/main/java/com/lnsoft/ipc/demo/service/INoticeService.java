
package com.lnsoft.ipc.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseService;
import com.lnsoft.ipc.demo.entity.Notice;

/**
 * 服务类
 *
 * @author guozhao
 */
public interface INoticeService extends BaseService<Notice> {

	/**
	 * 自定义分页
	 * @param page
	 * @param notice
	 * @return
	 */
	IPage<Notice> selectNoticePage(IPage<Notice> page, Notice notice);

}
