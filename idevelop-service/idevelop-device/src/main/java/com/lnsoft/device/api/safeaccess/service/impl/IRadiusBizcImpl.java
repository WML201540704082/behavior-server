package com.lnsoft.device.api.safeaccess.service.impl;

import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.device.api.safeaccess.service.IRadiusBizc;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: IRadiusBizcImpl
 * @description:
 * @author: zhangs
 * @create: 2024-03-07 15:32
 **/
@Service
public class IRadiusBizcImpl implements IRadiusBizc {


	@Resource
	private DBDao hibernateDao1;

	private Map<String, String> fieldsMap = null;

	@Override
	public int updateOrsaveNas(String nasname, String shortname, String secret, String model, String version, String read, String write, String pid, IdevelopUser sysUser) {
		Object[] params1 = new Object[]{pid};
		List<Object> list = hibernateDao1.queryForListWithSql("select id from nas where pid =?", params1, sysUser);
		Object[] params = new Object[]{nasname, shortname, secret, model, version, read, write, pid};
		int flag = 0;
		if (list != null && list.size() == 0) {
			flag = hibernateDao1.updateWithSql("insert into  nas(nasname,shortname,secret,model,snmpversion,communityread,communitywrite,pid) values(?,?,?,?,?,?,?,?)", params, sysUser);
			logRadius("nas", "insert", sysUser);
		} else {
			flag = hibernateDao1.updateWithSql("update nas set nasname=?,shortname=?,secret=?,model=?,snmpversion=?,communityread=?,communitywrite=? where pid=?", params, sysUser);
			logRadius("nas", "update", sysUser);
		}
		return flag;

	}

	@Override
	public int updateOrsaveNas(String pid, Map<String, String> map, IdevelopUser sysUser) {
		Object[] params = new Object[]{pid};
		String sql = "select id from nas where pid =?";
		List list = hibernateDao1.queryForListWithSql(sql, params, sysUser);
		int flag = 0;
		Map<String, String> v_fieldsMap = getFieldsMap();
		if (list == null) {
			return -1;
		}
		if (list.size() == 0) {
			String sqlInsert = "insert into nas";
			String sqlFields = "(";
			String sqlValues = " values (";
			List<Object> listParams = new ArrayList<>();
			for (Map.Entry entry : map.entrySet()) {
				String UapFieldName = entry.getKey().toString();
				if ("mxVirtualId".equals(UapFieldName) || "swId".equals(UapFieldName)) {
					continue;
				}
				String RadiusFieldName = v_fieldsMap.get(UapFieldName);
				if (RadiusFieldName == null) {
					continue;
				}
				Object fieldValue = entry.getValue();
				if (fieldValue == null) {
					continue;
				}
				sqlFields += RadiusFieldName + ",";
				listParams.add(fieldValue);
				sqlValues += "?,";
			}
			sqlFields += "shortname,pid)";
			listParams.add(pid);
			listParams.add(pid);
			sqlValues += "?,?)";
			sqlInsert += sqlFields + sqlValues;
//			log.info(sqlInsert);
			flag = hibernateDao1.updateWithSql(sqlInsert, listParams.toArray(), sysUser);
			logRadius("nas", "insert", sysUser);
		} else {
			String sqlUpdate = "update nas set ";
			String sqlFieldsValues = "";
			List<Object> listParams = new ArrayList<>();
			for (Map.Entry entry : map.entrySet()) {
				String UapFieldName = entry.getKey().toString();
				if ("mxVirtualId".equals(UapFieldName) || "swId".equals(UapFieldName)) {
					continue;
				}
				String RadiusFieldName = v_fieldsMap.get(UapFieldName);
				if (RadiusFieldName == null) {
					continue;
				}
				Object fieldValue = entry.getValue();
				if (fieldValue == null) {
					continue;
				}
				if (!"".equals(sqlFieldsValues)) {
					sqlFieldsValues += ", ";
				}
				sqlFieldsValues += RadiusFieldName + "=?";
				listParams.add(fieldValue);
			}
			sqlUpdate += sqlFieldsValues + " where pid = ?";
			listParams.add(pid);
			flag = hibernateDao1.updateWithSql(sqlUpdate, listParams.toArray(), sysUser);
			logRadius("nas", "update", sysUser);
		}
		return flag;
	}

	//获取交换机对应字段
	private Map<String, String> getFieldsMap() {
		if (fieldsMap == null) {
			fieldsMap = new HashMap<String, String>();
			fieldsMap.put("swIp", "nasname");
			fieldsMap.put("swId", "shortname");
			fieldsMap.put("portsCount", "ports");
			fieldsMap.put("swPass", "secret");
			fieldsMap.put("swName", "swname");
			fieldsMap.put("telIp", "tel_ip");
			fieldsMap.put("telUser", "tel_user");
			fieldsMap.put("telPass", "tel_pass");
			fieldsMap.put("configPass", "config_pass");
			fieldsMap.put("swWhere", "swwhere");
			fieldsMap.put("swPurpose", "purpose");
			fieldsMap.put("configBak", "runconfig");
			fieldsMap.put("isAdmin", "isadm");
			fieldsMap.put("is3", "is3");
			fieldsMap.put("swState", "swstate");
			fieldsMap.put("is802", "is802");
			fieldsMap.put("vlans", "vlans");
			fieldsMap.put("orgCode", "sguap_org");
			fieldsMap.put("swModel", "model");
			fieldsMap.put("snmpVersion", "snmpversion");
			fieldsMap.put("snmpWriteStr", "communitywrite");
			fieldsMap.put("snmpReadStr", "communityread");
			fieldsMap.put("swFirm", "factory");
//			fieldsMap.put("fillMan", "fill_man");
			fieldsMap.put("fillDate", "fill_date");
			fieldsMap.put("bakCol1", "bakcol1");
			fieldsMap.put("bakCol2", "bakcol2");
			fieldsMap.put("bakCol3", "bakcol3");
		}
		return fieldsMap;
	}

	@Override
	public int deleteNas(String pid, IdevelopUser sysUser) {
		Object[] params = new Object[]{pid};
		int flag = 0;
		if (pid != null) {
			flag = hibernateDao1.updateWithSql("delete from nas where pid=?", params, sysUser);
			logRadius("nas", "delete", sysUser);
		}
		return flag;
	}

	@Override
	public int updateOrsaveRadcheck(String pid, String username, String value, String ip, String mac, String is802, IdevelopUser sysUser) {
		Object[] params1 = new Object[]{pid};
		List<Object> list = hibernateDao1.queryForListWithSql("select id from radcheck where pid =?", params1, sysUser);
		Object[] params = new Object[]{username, value, ip, mac, is802, pid};
		int flag = 0;
		if (list == null) {
			return -1;
		}
		if (list.size() == 0) {
			flag = hibernateDao1.updateWithSql("INSERT INTO radcheck(username, value, ip, mac, is802, pid) VALUES(?, ?, ?, ?, ?, ?)", params, sysUser);
			logRadius("radcheck", "insert", sysUser);
		} else {
			flag = hibernateDao1.updateWithSql("update radcheck set username=?, value=?, ip=?, mac=?, is802=? where pid=?", params, sysUser);
			logRadius("radcheck", "update", sysUser);
		}
		return flag;
	}

	@Override
	public int deleteRadcheck(String pid, IdevelopUser sysUser) {
		Object[] params = new Object[]{pid};
		int flag = 0;
		if (pid != null) {
			flag = hibernateDao1.updateWithSql("delete from radcheck where pid=?", params, sysUser);
			logRadius("radcheck", "delete", sysUser);
		}
		return flag;
	}

	@Override
	public int updateOrsaveRadusergroup(String pid, String username, String groupname, IdevelopUser sysUser) {
		Object[] params1 = new Object[]{pid};
		List<Object> list = hibernateDao1.queryForListWithSql("select * from radusergroup where pid =?", params1, sysUser);
		Object[] params = new Object[]{username, groupname, pid};
		int flag = 0;
		if (list == null) {
			return -1;
		}
		if (list.size() == 0) {
			flag = hibernateDao1.updateWithSql("INSERT INTO radusergroup(username, groupname,pid) VALUES(?,?,?) ", params, sysUser);
			logRadius("radusergroup", "insert", sysUser);
		} else {
			flag = hibernateDao1.updateWithSql("update radusergroup set username=?, groupname=? where pid=? ", params, sysUser);
			logRadius("radusergroup", "update", sysUser);
		}
		return flag;
	}

	@Override
	public int deleteRadusergroup(String pid, IdevelopUser sysUser) {
		Object[] params = new Object[]{pid};
		int flag = 0;
		if (pid != null) {
			flag = hibernateDao1.updateWithSql("delete from radusergroup where pid=? ", params, sysUser);
			logRadius("radusergroup", "delete", sysUser);
		}
		return flag;
	}

	@Override
	public int updateRadgroupreply(String uuid_, String groupname, String value, String attribute, String broadcast, String subnet, String route, String mask, String dns, String default_lease_time, IdevelopUser sysUser) {
		Object[] params = new Object[]{groupname, value, broadcast, subnet, route, mask, dns, default_lease_time, uuid_, attribute};
		int flag = 0;
		flag = hibernateDao1.updateWithSql("update radgroupreply set groupname=?, value=?, broadcast=?, subnet=?, route=?, mask=?, dns=? , default_lease_time=? where uuid_=? and attribute=? ", params, sysUser);
		logRadius("radgroupreply", "update", sysUser);
		return flag;
	}

	@Override
	public int addRadgroupreply(String groupname, String attribute, String op, String value, String broadcast, String subnet, String route, String mask, String dns, String uuid_, String default_lease_time, IdevelopUser sysUser) {
		Object[] params = new Object[]{groupname, attribute, op, value, broadcast, subnet, route, mask, dns, uuid_, default_lease_time};
		int flag = 0;
		flag = hibernateDao1.updateWithSql("insert into radgroupreply(groupname, attribute, op, value, broadcast, subnet, route, mask, dns, uuid_,default_lease_time) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", params, sysUser);
		logRadius("radgroupreply", "update", sysUser);
		return flag;
	}

	@Override
	public int deleteRadgroupreply(String uuid_, IdevelopUser sysUser) {
		Object[] params = new Object[]{uuid_};
		int flag = 0;
		if (uuid_ != null) {
			flag = hibernateDao1.updateWithSql("delete from radgroupreply where uuid_=? ", params, sysUser);
			logRadius("radgroupreply", "delete", sysUser);
		}
		return flag;
	}

	@Override
	public int changeIps(String ip1s, String ip2s, String groupname1, String groupname2, IdevelopUser sysUser) {
		Object[] params1 = new Object[]{groupname1, groupname2};
		String[] a = ip1s.split("@");
		String[] b = ip2s.split("@");
		String sql1 = "";
		for (int i = 0; i < a.length; i++) {
			Object[] params2 = new Object[]{b[i], a[i]};
			sql1 = "update radcheck set IP = ? where IP = ?";
			hibernateDao1.updateWithSql(sql1, params2, sysUser);
			logRadius("radcheck", "update", sysUser);
		}
		String sql2 = "update radusergroup set groupname = ? where groupname = ?";
		hibernateDao1.updateWithSql(sql2, params1, sysUser);
		logRadius("radusergroup", "update", sysUser);
		return 0;
	}


	/**
	 * 根据用户名称查询port信息
	 *
	 * @param userName
	 * @return
	 */
	@Override
	public String queryRadpostauth(String userName, IdevelopUser sysUser) {
		Object[] params = new Object[]{userName};
		String port = "";
		String sql = "select port from radpostauth  where username = ? ORDER BY authdate DESC";

		List<?> list = hibernateDao1.queryForListWithSql(sql, params, sysUser);
		if (list != null && list.size() > 0) {
			port = (String) list.get(0);
		}
		return port;
	}

	/**
	 * 根据userName查询radcheck对应PID
	 *
	 * @param userName
	 * @return
	 */
	@Override
	public String queryRadcheckPid(String userName, IdevelopUser sysUser) {
		Object[] params = new Object[]{userName};
		String ip_ = "";
		String sql = "select pid from radcheck  where username= ? ";

		List<?> list = hibernateDao1.queryForListWithSql(sql, params, sysUser);
		if (list == null) {
			return null;
		}
		if (list != null && list.size() > 0) {
			ip_ = list.get(0).toString().replace("{", "").replace("}", "").split("=")[1];
		}
		return ip_;
	}


	/**
	 * 根据IP查询radcheck对应PID
	 *
	 * @param
	 * @return
	 */
	@Override
	public String queryRadcheckPid_(String Ip, IdevelopUser sysUser) {
		Object[] params = new Object[]{Ip};
		String ip_ = "";
		String sql = "select pid from radcheck  where ip = ?";

		List<?> list = hibernateDao1.queryForListWithSql(sql, params, sysUser);
		if (list != null && list.size() > 0) {
			ip_ = list.get(0).toString().replace("{", "").replace("}", "").split("=")[1];
		}
		return ip_;
	}

	/**
	 * 写入radius日志
	 *
	 * @param tablename
	 * @param detail
	 * @return
	 */
	public int logRadius(String tablename, String detail, IdevelopUser sysUser) {

		Object[] params = new Object[]{tablename, detail};
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "insert into log_radius (tablename,detail,addtime) values (?,?,'" + formatter.format(new Date()) + "')";
		return hibernateDao1.updateWithSql(sql, params, sysUser);
	}

	/**
	 * 网段变更同步radius
	 *
	 * @param groupnameOld
	 * @param groupnameNew
	 * @param ipList
	 * @return
	 */
	@Override
	public int changeSubnet(String groupnameOld, String groupnameNew, List<Map<String, String>> ipList, IdevelopUser sysUser) {
		Object[] params = new Object[]{groupnameOld, groupnameNew};

		for (Map<String, String> tmap : ipList) {

			String oldIp = tmap.get("oldIp");
			String newIp = tmap.get("newIp");
			Object[] params1 = new Object[]{newIp, oldIp};
			String sql1 = "update radcheck set IP =? where IP = ?";
			String sql2 = "update n_computer set IP = ? where IP = ?";

			hibernateDao1.updateWithSql(sql1, params1, sysUser);
			hibernateDao1.updateWithSql(sql2, params1, sysUser);
		}

		String sql3 = "update radusergroup set groupname = ? where groupname =?";
		logRadius("radcheck", "update", sysUser);
		logRadius("radusergroup", "update", sysUser);

		return hibernateDao1.updateWithSql(sql3, params, sysUser);
	}

}
