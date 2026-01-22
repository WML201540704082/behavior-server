package com.lnsoft.gateway.utils;

import com.antherd.smcrypto.sm3.Sm3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 签名摘要算法
 * @author guozhao on 2022/10/22
 */
public class SM3Util {

    private static final Logger log = LoggerFactory.getLogger(SM3Util.class);

    public static String sm3Hash(String data) {
        try{
            return Sm3.sm3(data);
        }catch(Exception e){
            log.error("使用国密SM3Hash出现异常: {}", e.getMessage(), e);
        }
        return null;
    }



}
