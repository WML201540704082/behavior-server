
package com.lnsoft.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.system.entity.Param;
import com.lnsoft.system.vo.ParamVO;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.system.mapper.ParamMapper;
import com.lnsoft.system.service.IParamService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author guozhao
 */
@Service
public class ParamServiceImpl extends BaseServiceImpl<ParamMapper, Param> implements IParamService {

	@Override
	public IPage<ParamVO> selectParamPage(IPage<ParamVO> page, ParamVO param) {
		return page.setRecords(baseMapper.selectParamPage(page, param));
	}

}
