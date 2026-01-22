
package com.lnsoft.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.system.entity.Param;
import com.lnsoft.system.vo.ParamVO;
import com.lnsoft.core.mp.base.BaseService;

/**
 * 服务类
 *
 * @author guozhao
 */
public interface IParamService extends BaseService<Param> {

	/***
	 * 自定义分页
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<ParamVO> selectParamPage(IPage<ParamVO> page, ParamVO param);

}
