package com.lnsoft.device.utils;

import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.device.api.safeaccess.mapper.CommonDao2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


/**
 * 公共方法
 *
 * @author gaotzh
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Slf4j
public class Common {
	private static char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	@Resource
	private CommonDao2 commonDao2;

	/**
	 * 注销时清理session
	 */
//    @RequestMapping(value = "/logoutSession", method = RequestMethod.GET)
//    public static void LogoutSession() {
//        ITUMPSession.logoutSession();
//    }

	// ---------------------获取当前登录用户相关信息------------------------
	/**
	 * 获取用户ID
	 *
	 * @return
	 */
//    public static String getUserID() {
//        return ITUMPSession.getItumpUserId();
//    }

	/**
	 * 获取用户帐号
	 *
	 * @return
	 */
//    public static String getUserName() {
//        return ITUMPSession.getItumpUserLoginName();
//    }

	/**
	 * 获取用户所属单位ID
	 *
	 * @return
	 */
//    public static String getDeptid() {
//        return ITUMPSession.getItumpCompId();
//    }

	/**
	 * 获取用户所属单位6位编码
	 *
	 * @return
	 */
//    public static String getUniCodeByOrgId() {
//        return ITUMPSession.getItumpCompCode6();
//    }

	/**
	 * 获取登录账号4位机构代码
	 */
	@RequestMapping(value = {"/getCompCode4"}, method = {RequestMethod.GET})
	@ResponseBody
	public static String getCompCode4() {
//        return ITUMPSession.getItumpCompCode4();
		return null;
	}

	/**
	 * 获取登录账号6位机构代码
	 */
	@RequestMapping(value = {"/getCompCode6"}, method = {RequestMethod.GET})
	@ResponseBody
	public static String getCompCode6() {
//        return ITUMPSession.getItumpCompCode6();
		return null;
	}

	/**
	 * 获取用户所属单位编码
	 *
	 * @return
	 */
//    public static String getUniCode() {
//        return ITUMPSession.getItumpCompCode6();
//    }

	/**
	 * 获取用户所属单位名称
	 *
	 * @return
	 */
//    public static String getDwName() {
//        return ITUMPSession.getItumpCompName();
//    }

	/**
	 * 获取用户所属部门ID
	 *
	 * @return
	 */
//    public static String getDepartmentById() {
//        return ITUMPSession.getItumpDeptId();
//    }

	/**
	 * 获取用户所属部门名称
	 *
	 * @param deptid
	 * @return
	 */
//    public static String deptName(String deptid) {
//        return ITUMPSession.getItumpDeptName();
//    }

	/**
	 * 获取用户所属部门编码
	 *
	 * @param deptid
	 * @return
	 */
//    public static String deptCod(String deptid) {
//        return ITUMPSession.getItumpDeptCode();
//    }

	/**
	 * 获取用户所属部门编码
	 * @return
	 */
//    public static String getItumpDeptCode() {
//        return ITUMPSession.getItumpDeptCode();
//    }

	/**
	 * 获取用户所属部门名称
	 *
	 * @param deptid
	 * @return
	 */
//    public static String getDeptNameByDeptId(String deptid) {
//        return ITUMPSession.getItumpDeptName();
//    }

	/**
	 * 加密函数
	 *
	 * @param str
	 * @return
	 */
	public static String encryptAES(String str) {
		if (str == null) {
			return null;
		}
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed("sguap+P@ss-05*Ln".getBytes());
			kgen.init(128, random);
			// kgen.init(128, new SecureRandom(encryptPwd.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();

			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");

			Cipher cipher = Cipher.getInstance("AES");

			cipher.init(1, key);
			byte[] result = cipher.doFinal(str.getBytes("UTF-8"));
			return toHexString(result);
		} catch (Exception e) {
			log.error("使用 AES解密" + str + "时出错。", e);
			throw new ServiceException("使用 AES解密" + str + "时出错。");
		}

		// return DataEncryption.encryptByAES(str, "sguap+P@ss-05*Ln");
	}

	public static String toHexString(byte[] byteArray)
		/*     */ {
		/* 174 */
		StringBuffer sb = new StringBuffer(byteArray.length * 2);
		/* 175 */
		for (int i = 0; i < byteArray.length; ++i) {
			/* 176 */
			sb.append(hexChar[((byteArray[i] & 0xF0) >>> 4)]);
			/* 177 */
			sb.append(hexChar[(byteArray[i] & 0xF)]);
			/*     */
		}
		/* 179 */
		return sb.toString();
		/*     */
	}


	/**
	 * 解密函数
	 *
	 * @param str
	 * @return
	 */
	public static String decryptAES(String str) {
		if (str == null) {
			return null;
		}
		try {

			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed("sguap+P@ss-05*Ln".getBytes());
			/* 428 */
			kgen.init(128, random);
			/// * 428 */ kgen.init(128, new
			/// SecureRandom(encryptPwd.getBytes()));
			/* 429 */
			SecretKey secretKey = kgen.generateKey();
			/* 430 */
			byte[] enCodeFormat = secretKey.getEncoded();
			/* 431 */
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			/* 432 */
			Cipher cipher = Cipher.getInstance("AES");
			/* 433 */
			cipher.init(2, key);
			/* 434 */
			byte[] result = cipher.doFinal(hex2byte(str.getBytes("UTF-8")));
			/* 435 */
			return new String(result);
		} catch (Exception e) {
			//log.error("使用 AES解密" + str + "时出错。",e);
			throw new ServiceException("使用 AES解密" + str + "时出错。");
		}
		// return DataEncryption.decryptByAES(str, "sguap+P@ss-05*Ln");
	}

	public static byte[] hex2byte(byte[] b)
		/*     */ {
		/* 129 */
		if (b.length % 2 != 0) {
			/* 130 */
			throw new IllegalArgumentException("长度不是偶数!");
			/*     */
		}
		/* 132 */
		byte[] b2 = new byte[b.length / 2];
		/*     */
		/* 134 */
		for (int n = 0; n < b.length; n += 2) {
			/* 135 */
			String item = new String(b, n, 2);
			/* 136 */
			b2[(n / 2)] = (byte) Integer.parseInt(item, 16);
			/*     */
		}
		/* 138 */
		return b2;
		/*     */
	}

	/**
	 * 验证ip地址格式
	 *
	 * @param ipAddress
	 * @return
	 */
	public static boolean isIpv4(String ipAddress) {
		String patternIp = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
				+ "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
		return Pattern.compile(patternIp).matcher(ipAddress).matches();
	}

	/**
	 * 验证mac地址格式
	 *
	 * @param mac
	 * @return
	 */
	public static boolean isMac(String mac) {
		String patternMac = "^[A-F0-9]{2}(:[A-F0-9]{2}){5}$";
		return Pattern.compile(patternMac).matcher(mac).matches();
	}

	/**
	 * 验证数字格式
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		String patternNum = "[0-9]*";
		return Pattern.compile(patternNum).matcher(str).matches();
	}

	/**
	 * 验证子网掩码格式
	 *
	 * @param mask
	 * @return
	 */
	public static boolean isSubnetMask(String mask) {
		String[] masks = new String[]{"255.255.255.0", "255.255.255.128", "255.255.255.192", "255.255.255.224",
				"255.255.255.240", "255.255.248.0", "255.255.252.0", "255.255.254.0", "255.255.255.248",
				"255.255.255.252", "255.255.255.254", "255.255.255.255", "255.255.128.0", "255.255.192.0",
				"255.255.224.0", "255.255.240.0", "255.248.0.0", "255.252.0.0", "255.254.0.0", "255.255.0.0",
				"255.128.0.0", "255.192.0.0", "255.224.0.0", "255.240.0.0", "240.0.0.0", "248.0.0.0", "252.0.0.0",
				"254.0.0.0", "255.0.0.0", "128.0.0.0", "192.0.0.0", "224.0.0.0"};
		boolean flag = false;
		for (int i = 0; i < masks.length; i++) {
			if (mask.equals(masks[i])) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 获取当前日期和时间
	 *
	 * @return String
	 */
	public static String getCurrentDateStr2() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(new Date());
	}

	/**
	 * 获取编码前一部分
	 *
	 * @return String
	 */
	public static String getCode(String flag, String formatStr) {
		SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
		String dateCode = formatter.format(new Date());
		String rsCode = flag + dateCode;
		return rsCode;
	}

	private static List<Map<String, String>> netWorkCategoryList = new ArrayList<Map<String, String>>();

	/**
	 * 终端出入网申请获得设备类别
	 */
	public List<Map<String, String>> getNetWorkCategoryMap() {
		// List<Map<String, String>> netWorkCategoryList = new
		// ArrayList<Map<String, String>>();
		if (netWorkCategoryList.size() == 0) {
			getCategoryMap();
			getUserAccessCategoryMap();
		}
		return netWorkCategoryList;
	}

	/**
	 * 台账用,network入网时查询
	 *
	 * @return
	 */
	public Object[] getNetWorkCategoryArray() {
		List<String> categoryList = new ArrayList<String>();
		getNetWorkCategoryMap();
		Iterator<Map<String, String>> itor = netWorkCategoryList.iterator();
		while (itor.hasNext()) {
			categoryList.add(itor.next().get("value"));
		}
		return categoryList.toArray();
	}

	/**
	 * 获得终端设备类型
	 *
	 * @return
	 */
	@RequestMapping(value = "/netWork/update/category", method = RequestMethod.GET)
	@ResponseBody
	private String getCategoryMap() {
		List<Map<String, String>> list = commonDao2.findCategoryMap();
		LinkedList<Map<String, String>> lkList = new LinkedList<Map<String, String>>();
		for (Map<String, String> strs : list) {
			String typeCode = strs.get("typeCode");
			String typeName = strs.get("typeName");
			String parentId = strs.get("parentId");
			Map<String, String> _map = new HashMap<String, String>();
			_map.put("text", typeName);
			_map.put("value", typeCode);
			if ("00007".equals(parentId)) {
				lkList.addFirst(_map);
			} else {
				lkList.add(_map);
			}
		}
		netWorkCategoryList = lkList;
		return "update success!";
	}

	public String getUserAccessCategoryMap() {
		List<Map<String, String>> list = commonDao2.findWorkType();
		LinkedList<Map<String, String>> lkList = new LinkedList<Map<String, String>>();
		for (Map<String, String> m : list) {
			String typeCode = m.get("type_code");
			String typeName = m.get("type_name");
			String parentId = m.get("parent_id");
			Map<String, String> _map = new HashMap<String, String>();
			_map.put("text", typeName);
			_map.put("value", typeCode);
			if ("00007".equals(parentId)) {
				lkList.addFirst(_map);
			} else {
				lkList.add(_map);
			}
		}
		synchronized (netWorkCategoryList) {
			netWorkCategoryList = lkList;
		}
		return "update success!";
	}

	/**
	 * 获取当前服务器日期和时间 yyyy-MM-dd HH:mm:ss
	 *
	 * @return String
	 */
	@RequestMapping(value = "/getCurrentDate", method = RequestMethod.GET)
	@ResponseBody
	public static String getCurrentDateStr() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(new Date());
	}

}
