package com.lnsoft.common.tool;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Base64;

/**
 * @author xyzadmin
 */
public class Base64Util {
	/**
	 * 图片url转base64编码
	 */
	public static String imageUrlToBase64(String imgUrl) throws IOException {

		return Base64.getEncoder().encodeToString(IOUtils.toByteArray(new URL(imgUrl)));
	}
}
