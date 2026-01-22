package com.lnsoft.gateway.utils;

import com.antherd.smcrypto.sm4.Sm4;
import com.antherd.smcrypto.sm4.Sm4Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SM4国密对称加密算法
 * @author guozhao on 2022/10/27
 */
public class SM4Util {


    private static Logger log = LoggerFactory.getLogger(SM4Util.class);

    /**
     * 加密
     */
    public static String encrypt(String data, String key, String iv) {
        try{
            Sm4Options sm4Options = new Sm4Options();
            //sm4Options.setIv(iv);
            //sm4Options.setMode("cbc");
            return Sm4.encrypt(data, key);
        }catch(Exception e){
            log.error("SM4加密出现异常: {}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 解密
     */
    public static String decrypt(String enData, String key, String iv) {
        try{
            Sm4Options sm4Options = new Sm4Options();
            //sm4Options.setIv(iv);
            //sm4Options.setMode("cbc");
            return Sm4.decrypt(enData, key);
        }catch(Exception e){
            log.error("SM4解密出现异常: {}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 加密
     */
    public static String encryptCBC(String data, String key, String iv) {
        try{

//            return SM4Utils.encrypt(data, key, iv, DataType.hex);
			return null;
        }catch(Exception e){
            log.error("SM4加密出现异常: {}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 解密
     */
    public static String decryptCBC(String enData, String key, String iv) {
        try{
			return null;
        }catch(Exception e){
            log.error("SM4解密出现异常: {}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取key
     * @return
     */
    public static String generateKey() {
        try {
			return null;
        }catch (Exception e) {
            log.error("generateKey exception:{}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取iv
     * @return
     */
    public static String generateIv() {
        try {
			return null;
        }catch (Exception e) {
            log.error("generateIv exception:{}", e.getMessage(), e);
        }
        return null;
    }


}
