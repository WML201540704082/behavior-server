package com.lnsoft.device.utils;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;


public class SubnetDivision {
	/**
	 * 获取子网地址与广播地址
	 *
	 * @param gateway
	 * @param mask
	 * @return
	 */
	public static String[] getSubnetInfo(String gateway, String mask) {
		String[] info = new String[]{"", ""};
		if (Common.isIpv4(gateway) && Common.isSubnetMask(mask)) {
			String[] gatewayBytes = gateway.split("\\.");
			String[] maskBytes = mask.split("\\.");
			for (int i = 0; i < 4; i++) {
				int temp1 = Integer.parseInt(gatewayBytes[i]);
				int temp2 = Integer.parseInt(maskBytes[i]);
				int temp3 = temp1 & temp2;
				if (i != 0) {
					info[0] += ".";
					info[1] += ".";
				}
				info[0] += temp3;
				info[1] += 255 - (temp3 ^ temp2);
			}
		}
		return info;
	}

	/**
	 * 校验网关格式
	 *
	 * @param gateway
	 * @param mask
	 * @return
	 */
	public static boolean isGateway(String gateway, String mask) {
		String[] info = getSubnetInfo(gateway, mask);
		return !gateway.equals(info[0]) && !gateway.equals(info[1]);
	}

	/**
	 * 把int->ip地址
	 *
	 * @param ipInt
	 * @return String
	 */
	public static String intToIp(int ipInt) {
		return new StringBuilder().append(((ipInt >> 24) & 0xff)).append('.')
			.append((ipInt >> 16) & 0xff).append('.').append(
				(ipInt >> 8) & 0xff).append('.').append((ipInt & 0xff))
			.toString();
	}

	/**
	 * 把IP地址转化为字节数组
	 *
	 * @param ipAddr
	 * @return byte[]
	 */
	public static byte[] ipToBytesByInet(String ipAddr) {
		try {
			return InetAddress.getByName(ipAddr).getAddress();
		} catch (Exception e) {
			throw new IllegalArgumentException(ipAddr + " is invalid IP");
		}
	}

	/**
	 * 根据位运算把 byte[] -> int
	 *
	 * @param bytes
	 * @return int
	 */
	public static int bytesToInt(byte[] bytes) {
		int addr = bytes[3] & 0xFF;
		addr |= ((bytes[2] << 8) & 0xFF00);
		addr |= ((bytes[1] << 16) & 0xFF0000);
		addr |= ((bytes[0] << 24) & 0xFF000000);
		return addr;
	}

	/**
	 * 把IP地址转化为int
	 *
	 * @param ipAddr
	 * @return int
	 */
//    public static int ipToInt(String ipAddr) {
//        try {
//            return bytesToInt(ipToBytesByInet(ipAddr));
//        } catch (Exception e) {
//            throw new IllegalArgumentException(ipAddr + " is invalid IP");
//        }
//    }

	/**
	 *
	 * @param ipAddr ipAddr
	 * @param mask   mask
	 * @return int[]
	 */
	public static int[] getIPIntScope(String ipAddr, String mask) {

		int ipInt;
		int netMaskInt = 0, ipcount = 0;
		try {
			ipInt = IPv4Util.ipToInt(ipAddr);
			if (null == mask || "".equals(mask)) {
				return new int[]{ipInt, ipInt};
			}
			netMaskInt = IPv4Util.ipToInt(mask);
			ipcount = IPv4Util.ipToInt("255.255.255.255") - netMaskInt;
			int netIP = ipInt & netMaskInt;
			int hostScope = netIP + ipcount;
			return new int[]{netIP, hostScope};
		} catch (Exception e) {
			throw new IllegalArgumentException("invalid ip scope express  ip:"
				+ ipAddr + "  mask:" + mask);
		}

	}

	/**
	 * 获取子网下所有IP
	 *
	 * @param ipAddr
	 * @param mask
	 * @return
	 */
	public static List<String> getIpsInSubnet(String ipAddr, String mask) {
		List<String> ipList = new ArrayList<String>();
		try {
			int ipInt = IPv4Util.ipToInt(ipAddr);
			int netMaskInt = 0, ipcount = 0;
			if (null == mask || "".equals(mask)) {
				return ipList;
			}
			netMaskInt = IPv4Util.ipToInt(mask);
			ipcount = IPv4Util.ipToInt("255.255.255.255") - netMaskInt;
			int ipStart = ipInt & netMaskInt;
			int ip;
			for (int i = 0; i < ipcount; i++) {
				ip = ipStart + i;
				ipList.add(intToIp(ip));
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("invalid ip scope express  ip:"
				+ ipAddr + "  mask:" + mask);
		}
		return ipList;
	}

	public static int ipToInt(String ip) throws Exception {
		ip = ip.trim();
		String regular = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
		String[] iparray = ip.split("\\.");
		if (!ip.matches(regular) || iparray.length != 4) {
			throw new Exception("Wrong IP.");
		}
		return Integer.parseInt(iparray[0]) << 24 | Integer.parseInt(iparray[1]) << 16 | Integer.parseInt(iparray[2]) << 8 | Integer.parseInt(iparray[3]);
	}

}
