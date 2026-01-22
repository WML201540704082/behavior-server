
package com.lnsoft.system.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import com.lnsoft.system.user.entity.UserOauth;
import com.lnsoft.system.user.mapper.UserOauthMapper;
import com.lnsoft.system.user.service.IUserOauthService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author guozhao
 */
@Service
@AllArgsConstructor
public class UserOauthServiceImpl extends ServiceImpl<UserOauthMapper, UserOauth> implements IUserOauthService {

}
