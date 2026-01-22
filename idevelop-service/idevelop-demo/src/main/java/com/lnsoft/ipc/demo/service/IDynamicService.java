
package com.lnsoft.ipc.demo.service;

import com.lnsoft.ipc.demo.entity.Notice;
import com.lnsoft.core.mp.base.BaseService;

import java.util.List;

/**
 * 服务类
 *
 * @author guozhao
 */
public interface IDynamicService extends BaseService<Notice> {

	/**
	 * master数据源的列表
	 *
	 * @return
	 */
	List<Notice> masterList();

	/**
	 * slave数据源的列表
	 *
	 * @return
	 */
	List<Notice> slaveList();

}
