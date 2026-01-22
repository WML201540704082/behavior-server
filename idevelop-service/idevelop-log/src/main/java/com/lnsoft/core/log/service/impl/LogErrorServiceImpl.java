
package com.lnsoft.core.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lnsoft.core.log.mapper.LogErrorMapper;
import com.lnsoft.core.log.model.LogError;
import com.lnsoft.core.log.service.ILogErrorService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author guozhao
 * @since 2018-09-26
 */
@Service
public class LogErrorServiceImpl extends ServiceImpl<LogErrorMapper, LogError> implements ILogErrorService {

}
