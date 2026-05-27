package com.lnsoft.device.api.safeaccess.service.impl;

import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.device.api.safeaccess.entity.SysTRadiusConnection;
import com.lnsoft.device.api.safeaccess.mapper.CommonDao2;
import com.sgcc.uap.persistence.criterion.QueryCriteria;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName: DBManager
 * @description:
 * @author: zhangs
 * @create: 2024-03-07 15:34
 **/
@Service
@Slf4j
public class DBManager {

	@Resource
	private CommonDao2 commonDao2;


	private static Map<String, DataSource> dsMap = new HashMap<String, DataSource>();


	/**
	 * 测试数据库连通性
	 *
	 * @param connectUrl
	 * @param username
	 * @param password
	 * @return boolean
	 */
	public static boolean testConnection(String connectUrl, String username, String password) {
		try {
			String passwd = password;
            /*try {
                passwd = decryptAES(passwd);
            } catch (Exception e) {
                log.warn(e.getMessage());
            }*/
			BasicDataSource tds = (BasicDataSource) createDataSource("jdbc:mysql://" + connectUrl + "?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&serverTimezone=GMT%2B8&useSSL=true",
				username, passwd);
			Connection connection = tds.getConnection();
			JdbcUtils.closeConnection(connection);
			tds.close();
			return true;
		} catch (SQLException e) {
			log.warn("创建数据源失败：{}，异常信息：{}", connectUrl, e.getMessage(), e);
		}
		return false;
	}

	/**
	 * 测试数据库连通性
	 *
	 * @param orgNo
	 * @return boolean
	 */
	public boolean testConnection(String orgNo) {
		try {
			BasicDataSource tds = (BasicDataSource) getDataSource(orgNo);
			Connection connection = tds.getConnection();
			JdbcUtils.closeConnection(connection);
			tds.close();
			return true;
		} catch (SQLException e) {
			log.warn(e.getMessage());
			;
		}
		return false;
	}

	/**
	 * 更新数据源
	 *
	 * @param orgNo
	 * @return boolean
	 */
	public boolean updateDataSource(String orgNo) {

		DataSource ds = null;
		SysTRadiusConnection radiusInfo = getRadiusInfo(orgNo);
		if (radiusInfo == null) {
			return false;
		}
		String radiusIp = radiusInfo.getRadiusIp();
		String radiusName = radiusInfo.getRadiusName();
		String radiusPort = radiusInfo.getRadiusPort();
		String radiusUser = radiusInfo.getRadiusUser();
		String radiusPasswd = radiusInfo.getRadiusPasswd();
		if (radiusIp == null || radiusName == null ||
			radiusPort == null || radiusUser == null ||
			radiusPasswd == null) {
			return false;
		}

		String connectUrl = "jdbc:mysql://" + radiusIp + ":" + radiusPort + "/" + radiusName + "?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&serverTimezone=GMT%2B8&useSSL=true";
        /*try {
            radiusPasswd = decryptAES(radiusPasswd);
        } catch (Exception e) {
        	log.warn(e.getMessage());
        }*/
		ds = createDataSource(connectUrl, radiusUser, radiusPasswd);
		if (ds == null) {
			return false;
		}
		dsMap.put(orgNo, ds);
		return true;
	}

	/**
	 * 获取数据源
	 *
	 * @param orgNo
	 * @return DataSource
	 */
	public DataSource getDataSource(String orgNo) {
		if (dsMap.get(orgNo) == null) {
			if (!updateDataSource(orgNo)) {
				return null;
			}
		}
		return dsMap.get(orgNo);
	}

	/**
	 * 获取数据库连接
	 *
	 * @param orgNo
	 * @return Connection
	 */
	public Connection getConnection(String orgNo) {
		try {
			DataSource ds = getDataSource(orgNo);
			if (ds == null) {
				return null;
			}
			return ds.getConnection();
		} catch (SQLException e) {
			log.warn("获取数据库连接失败：{}，异常信息：{}", orgNo, e.getMessage(), e);
			throw new ServiceException("操作失败，获取数据库连接失败，请联系管理员处理!");
		}
	}

	//创建数据源
	private static DataSource createDataSource(String connectUrl, String username,
											   String password) {
		return createDataSource(connectUrl, username, password, "com.mysql.cj.jdbc.Driver", "800", "800", "5000");
	}

	//创建数据源
	private static DataSource createDataSource(String connectUrl, String username, String password,
											   String driverClass, String maxActive, String maxIdle,
											   String maxWait) {
		DataSource ds = null;
		Properties properties = new Properties();
		properties.setProperty("driverClassName", driverClass);
		properties.setProperty("url", connectUrl);
		properties.setProperty("username", username);
		properties.setProperty("password", password);
		properties.setProperty("maxActive", maxActive);
		properties.setProperty("maxIdle", maxIdle);
		properties.setProperty("maxWait", maxWait);
		properties.setProperty("minEvictableIdleTimeMillis", "3600000");
		properties.setProperty("timeBetweenEvictionRunsMillis", "1800000");
		properties.setProperty("connectionProperties", "connectTimeout=3000");

		try {
			ds = BasicDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			log.warn("创建数据源失败：{}，异常信息：{}", connectUrl, e.getMessage(), e);
			throw new ServiceException("数据库连接失败:" + connectUrl);
		}
		return ds;
	}

	public SysTRadiusConnection getRadiusInfo(String orgNo) {
		QueryCriteria qc = new QueryCriteria();
		List<SysTRadiusConnection> result = null;

		result = commonDao2.fingMySqlSource(orgNo);
		if (result.size() == 0) {
			return null;
		}
		return result.get(0);
	}

	/**
	 * 加密函数
	 *
	 * @param str
	 * @return
	 */
//	public static String encryptAES(String str) throws Exception {
//		return DataEncryption.encryptByAES(str, "radius+P@ss-05*Ln");
//	}


	/**
	 * 解密函数
	 *
	 * @param str
	 * @return
	 */
//	public static String decryptAES(String str) throws Exception {
//		return DataEncryption.decryptByAES(str, "radius+P@ss-05*Ln");
//	}

//	public static void main(String[] args) throws Exception {
//		System.out.println(decryptAES("3588d71f7e0a0f5ae995e39cf21ff47b"));
//	}

//	public String getPw(String orgNo) {
//		SysTRadiusConnection radiusInfo = this.getRadiusInfo(orgNo);
//		if (radiusInfo == null) {
//			return null;
//		}
//		String radiusPasswd = radiusInfo.getRadiusPasswd();
//		try {
//			radiusPasswd = decryptAES(radiusPasswd);
//		} catch (Exception e) {
//			log.warn(e.getMessage());
//		}
//		return radiusPasswd;
//	}
}
