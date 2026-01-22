package com.lnsoft.common.msg.service;

import com.alibaba.fastjson.JSONObject;
import com.lnsoft.common.msg.configure.ConfigProperties;
import com.lnsoft.common.msg.utils.HttpClientResult;
import com.lnsoft.common.msg.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: tms-platform
 * @description: 短信发送工具类
 * @author: xyz
 * @create: 2024-08-21 15:16
 **/
@Slf4j
@Component
public class MessageSendService {

	@Autowired
	public ConfigProperties config;

	/**
	 * 发送短信
	 *
	 * @Param: [信息, 电话号码]
	 * @return: java.lang.String
	 * @Author: ZFLIU
	 * @Date: 4/23/23
	 */
	public synchronized String send(String msg, String phone) {
		try {
			log.info("================================");
			log.info("准备发送msg：" + msg);
			log.info("准备发送phonenumber：" + phone);
			log.info("================================");

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String timestamp = dateFormat.format(new Date());
			String token = getToken(config.getAccount() + config.getPassword() + timestamp);
			if (token == null) {
				return "3";
			}
			String content = msg;
			//todo 测试改变编码 -存在问题，暂时屏蔽
			//            try {
			//                content = URLEncoder.encode(config.getTitle() + "：" + msg, "UTF-8");
			//            } catch ( UnsupportedEncodingException e ) {
			//                log.error(e.toString());
			//                return "3";
			//            }
			Map<String, String> map = new HashMap<>();

			//短消息平台分配的业务账号，一般为6位的字母或数字。账号主要用来区分业务端口及出具对账数据。
			map.put("account", config.getAccount());
			//访问令牌。算法为：
			//sha1(account + password + timestamp)
			//password为业务账号密码；
			//timestamp为系统当前的时间签，格式为 “yyyyMMddHHmmss”， 如 “20180816152916”。
			map.put("token", token);
			//在token中参与运算的系统时间签（timestamp），格式为
			//“yyyyMMddHHmmss”， 如 “20180816152916”。
			//注意事项：系统时间签过期时间为5分钟。
			map.put("ts", timestamp);
			//注意事项：如果是批量提交，每个请求最大支持一个包500个号码。
			map.put("dest", phone);
			//短信内容。如有中文应以UTF-8编码。（本平台目前支持智能识别汉字编码，也支持GBK，但推荐使用UTF-8以获得更好的发送性能）
			//个性化短信的content格式：
			//若需发送个性化的短信（多个目标手机号码，多个内容），需注意要保持目标手机号码与内容个数的一致。个性化短信的content参数生成方法如下：
			//URLEncode(content_1) + “|||” + URLEncode(content_2)；即将各内容进行URLEncode编码后以连接符“|||”（三个连续的管道符）进行连接即可。
			//注意事项：单条短信内容长度不能超过500个字符。
			map.put("content", content);
			//客户可以对提交的短信加入reference以便后续进行跟踪，设置的ref参数最终会随短信状态报告同步给客户。
			//注意事项：ref的长度限定在32位以内，格式只能包含字母或数字；提交方应保证ref的全局唯一。
			map.put("ref", "");
			//自定义的扩展号，将会出现在发送方号码的尾部。
			map.put("ext", "");
			try {
				log.info("短信内容：" + map);
				//修改http客户端
				HttpClientResult result = HttpClientUtils.doPost(config.getUrl(), map);
				log.info("Result >> " + result);
				JSONObject jsonObject = JSONObject.parseObject(result.getContent());
				return jsonObject.getInteger("status") == 0 ? "1" : "2";
			} catch (Exception e) {

				log.error("连接短信平台报错： " + e.getMessage(), e);
				return "3";

			}
		} catch (Exception e) {
			log.error("发送失败：" + e.getMessage(), e);
		}
		return "3";
	}

	public String getToken(String value) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(value.getBytes());
			byte[] md = digest.digest();
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < md.length; i++) {
				String shaHex = Integer.toHexString(md[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			log.error("混肴：" + e.getMessage(), e);
		}
		return null;
	}

}
