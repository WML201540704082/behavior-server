package com.lnsoft.device.api.safeaccess.service;

import com.lnsoft.core.pojo.IdevelopUser;

import java.util.List;
import java.util.Map;

/**
 * @author zhang
 */
public interface IRadiusBizc {
	/**
	 * 根据PID保存或更新NAS表，pid为本地表中的主键
	 *
	 * @param nasname   交换机IP
	 * @param shortname
	 * @param secret    密码
	 * @param pid       关联ID
	 * @return
	 */
	public int updateOrsaveNas(String nasname, String shortname, String secret, String model, String version, String read, String write, String pid, IdevelopUser sysUser);

	/**
	 * 根据PID删除NAS表数据，pid为本地表中的主键
	 *
	 * @param pid
	 * @return
	 */
	public int deleteNas(String pid, IdevelopUser sysUser);

	/**
	 * 根据PID保存或更新Radcheck表，pid为本地表中的主键
	 *
	 * @param pid      关联ID
	 * @param username 802.1x用户名
	 * @param value    802.1x密码
	 * @return
	 */
	public int updateOrsaveRadcheck(String pid, String username, String value, String ip, String mac, String is802, IdevelopUser sysUser);

	/**
	 * 根据PID删除Radcheck表数据，pid为本地表中的主键
	 *
	 * @param pid 关联ID
	 * @return
	 */
	public int deleteRadcheck(String pid, IdevelopUser sysUser);

	/**
	 * 根据PID保存或更新Radusergroup表，pid为本地表中的主键
	 *
	 * @param pid
	 * @param username  802.1x用户名
	 * @param groupname 所属子网的groupname
	 * @return
	 */
	public int updateOrsaveRadusergroup(String pid, String username, String groupname, IdevelopUser sysUser);

	/**
	 * 根据PID删除Radusergroup表数据，pid为本地表中的主键
	 *
	 * @param pid 关联ID
	 * @return
	 */
	public int deleteRadusergroup(String pid, IdevelopUser sysUser);

	/***
	 *  更新子网（Radgroupreply）表数据
	 * @param uuid_ 关联ID
	 * @param groupname 子网的groupname
	 * @param value 值：VLAN、IEEE-802、VLAN ID（VLAN号）
	 * @param attribute 枚举值：Tunnel-Type、Tunnel-Medium-Type、Tunnel-Private-Group-ID
	 * @param broadcast 广播地址
	 * @param subnet 子网地址
	 * @param route 子网网关
	 * @param mask 子网掩码
	 * @param dns DNS IP
	 * @return
	 */
	public int updateRadgroupreply(String uuid_, String groupname, String value, String attribute, String broadcast, String subnet, String route, String mask, String dns, String default_lease_time, IdevelopUser sysUser);

	/***
	 * 新增子网（Radgroupreply）
	 * @param groupname 子网的groupname
	 * @param attribute 枚举值：Tunnel-Type、Tunnel-Medium-Type、Tunnel-Private-Group-ID
	 * @param op :=
	 * @param value 值：VLAN、IEEE-802、VLAN ID（VLAN号）
	 * @param broadcast 广播地址
	 * @param subnet 子网地址
	 * @param route 子网网关
	 * @param mask 子网掩码
	 * @param dns  DNS IP
	 * @param uuid_ 关联ID
	 * @return
	 */
	public int addRadgroupreply(String groupname, String attribute, String op, String value, String broadcast, String subnet, String route, String mask, String dns, String uuid_, String default_lease_time, IdevelopUser sysUser);

	/**
	 * 根据uuid删除Radgroupreply表数据
	 *
	 * @param uuid_
	 * @return
	 */
	public int deleteRadgroupreply(String uuid_, IdevelopUser sysUser);

	/**
	 * 修改网段
	 *
	 * @param
	 * @return
	 */
	public int changeIps(String ip1s, String ip2s, String groupname1, String groupname2, IdevelopUser sysUser);


	/**
	 * 根据用户名称查询port信息
	 *
	 * @param userName
	 * @return
	 */
	public String queryRadpostauth(String userName, IdevelopUser sysUser);

	/**
	 * 根据userName查询radcheck对应PID
	 *
	 * @param userName
	 * @return
	 */
	public String queryRadcheckPid(String userName, IdevelopUser sysUser);

	/**
	 * 根据IP查询radcheck对应PID
	 *
	 * @param
	 * @return
	 */
	public String queryRadcheckPid_(String Ip, IdevelopUser sysUser);

	/**
	 * 根据PID保存或更新NAS表，pid为本地表中的主键
	 *
	 * @param map
	 * @return
	 */
	int updateOrsaveNas(String pid, Map<String, String> map, IdevelopUser sysUser);

	/**
	 * 网段变更同步radius
	 *
	 * @param groupnameOld
	 * @param groupnameNew
	 * @param ipList
	 * @return
	 */
	public int changeSubnet(String groupnameOld, String groupnameNew, List<Map<String, String>> ipList, IdevelopUser sysUser);
}
