package com.lnsoft.device.api.erp.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author xyzadmin
 */
public class TranstplnrCodeUtil {
	private static final String PREFIX = "6";
	public static String generateUniqueCode(){
		StringBuilder code = new StringBuilder(PREFIX);
		String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//		Random random = new Random();
		SecureRandom random = null;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		for (int i = 0; i < 4; i++) {
			char randomChar = characters.charAt(random.nextInt(characters.length()));
			code.append(randomChar);
			characters = characters.replace(String.valueOf(randomChar),"");
		}
		return code.toString();
	}
}
