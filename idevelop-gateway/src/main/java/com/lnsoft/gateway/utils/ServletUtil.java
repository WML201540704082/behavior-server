package com.lnsoft.gateway.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ServletUtil {

	public static String urlEncode(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch ( UnsupportedEncodingException e ) {
			return "";
		}
	}

}
