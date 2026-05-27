package com.lnsoft.device.utils;


import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DBManagerToMysql {

	//初始化地市的数据库连接地址
	private static final Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();

	private static DataSource ds = null;

	private static final Log log = LogFactory.getLog(DBManagerToMysql.class);
	/**
	 * 数据库链接地址
	 */
	private String connectUrl = "";
	/**
	 * 数据库登陆用户名
	 */
	private String user = "";
	/**
	 * 登录密码
	 */
	private String password;


	public DBManagerToMysql() {

	}

	/**
	 * 初始化数据库连接
	 *
	 * @param address 连接地理地址                    （泰安：taianSql ，）
	 */
	public DBManagerToMysql(String address) {
		if (address != null && !"".equals(address)) {
			//获取初始化的数据库连接信息
			/** 数据库登陆用户名 */
			this.user = PropertiesUtil.getValue(address + "user");
			/** 登录密码 */
			this.password = PropertiesUtil.getValue(address + "password");
			/** 数据库链接地址 */
			this.connectUrl = PropertiesUtil.getValue(address + "connectUrl");
//			/** 数据库登陆用户名 */
//			this.user = properties.getProperty(address + "user");
//			/** 登录密码 */
//			this.password = properties.getProperty(address + "password");
//			/** 数据库链接地址 */
//			this.connectUrl = properties.getProperty(address + "connectUrl");
		}

	}

	/**
	 * 初始化数据源
	 *
	 * @param user       用户名
	 * @param password   密码
	 * @param connectUrl 连接地址
	 */
	public DBManagerToMysql(String user, String password, String connectUrl) {
		/** 数据库登陆用户名 */
		this.user = user;
		/** 登录密码 */
		this.password = password;
		/** 数据库链接地址 */
		this.connectUrl = connectUrl;
	}

	/**
	 * 更新数据源
	 *
	 * @return boolean
	 */
	public boolean updateDataSource() {

		ds = createDataSource(connectUrl, user, password);
		return ds == null;
	}

	/**
	 * 获取数据源
	 *
	 * @return DataSource
	 */
	public DataSource getDataSource() {
		if (ds == null) {
			if (!updateDataSource()) {
				return null;
			}
		}
		return ds;
	}

	/**
	 * 获取数据库连接
	 *
	 * @return Connection
	 */
	public Connection getConnection() {
		Connection conn = null;
		try {
			// 读取配置文件
			log.info("开始读取数据库配置文件");
			log.info("开始加载mysql数据库驱动！");
			Class.forName(PropertiesUtil.getValue("driverClassName"));
//			Class.forName("com.mysql.jdbc.Driver");
			log.info("开始获取数据库连接！");
			conn = DriverManager.getConnection(connectUrl, user, password);

//			DataSource ds = getDataSource();
//			if(ds == null)return null;
//			return ds.getConnection();
		} catch (Exception e) {
			log.error("推送数据时数据库连接出现异常:" + e.getMessage());
		}
		return conn;
	}

	//创建数据源
	private DataSource createDataSource(String connectUrl, String username,
										String password) {
		return createDataSource(connectUrl, username, password, "com.mysql.cj.jdbc.Driver", "80", "80", "500");
	}

	//创建数据源
	private DataSource createDataSource(String connectUrl, String username, String password,
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
			log.info("11111111111111");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ds;
	}

	/**
	 * 加密函数
	 *
	 * @param str
	 * @return
	 */
//	public String encryptAES(String str) {
//		return DataEncryption.encryptByAES(str, "ddw+P@ss-05*Ln");
//	}

	/**
	 * 解密函数
	 *
	 * @param str
	 * @return
	 */
//	public String decryptAES(String str) {
//		return DataEncryption.decryptByAES(str, "ddw+P@ss-05*Ln");
//	}

	/**
	 * 查询
	 */
	public List queryForListWithSql(String sql, Object[] args) {

		ResultSet rs = null;
		List res = null;
		PreparedStatement ps = null;
		Connection connection = null;

		RowMapper rowMapper = new ColumnMapRowMapper();
		final ResultSetExtractor rse = new RowMapperResultSetExtractor(
			rowMapper);

		try {
			connection = getConnection();
			if (connection == null) {
				return null;
			}
			log.info("获取mysql数据库连接成功，开始执行sql:");
			ps = connection.prepareStatement(sql);
			Object arg;
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					arg = args[i];
					if ((arg instanceof SqlParameterValue)) {
						SqlParameterValue paramValue = (SqlParameterValue) arg;
						StatementCreatorUtils.setParameterValue(ps, i + 1,
							paramValue, paramValue.getValue());
					} else {
						StatementCreatorUtils.setParameterValue(ps, i + 1,
							-2147483648, arg);
					}
				}
			}
			rs = ps.executeQuery();
			log.info("开始把查询数据转为list");
			res = (List) rse.extractData(rs);
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(ps);
			JdbcUtils.closeConnection(connection);
			StatementCreatorUtils.cleanupParameters(args);
		}
		return res;
	}

	/**
	 * 执行sql的增删改操作
	 */
	public int updateWithSql(final String sql) {

		Connection con = null;
		PreparedStatement statement = null;
		int rows = -1;
		try {
			con = getConnection();
			if (con == null) {
				return -1;
			}
			statement = con.prepareStatement(sql);
			rows = statement.executeUpdate();

			if (log.isDebugEnabled()) {
				log.debug("SQL statement affect record number");
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {
			JdbcUtils.closeStatement(statement);
			JdbcUtils.closeConnection(con);
		}
		return rows;
	}


	public int updateWithSql(final String sql, final Object[] args) {
		PreparedStatement ps = null;
		Connection con = null;
		int rows = -1;
		try {
			con = getConnection();
			if (con == null) {
				return -1;
			}
			ps = con.prepareStatement(sql);
			Object arg;
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					arg = args[i];
					if ((arg instanceof SqlParameterValue)) {
						SqlParameterValue paramValue = (SqlParameterValue) arg;
						StatementCreatorUtils.setParameterValue(ps, i + 1, paramValue, paramValue.getValue());
					} else {
						StatementCreatorUtils.setParameterValue(ps, i + 1, -2147483648, arg);
					}
				}
			}
			rows = ps.executeUpdate();
		} catch (SQLException e) {
			log.warn(e.getMessage());
		} finally {
			JdbcUtils.closeStatement(ps);
			JdbcUtils.closeConnection(con);
			StatementCreatorUtils.cleanupParameters(args);
		}
		return rows;
	}

	/**
	 * 批量更新
	 */
	public int[] batchUpdateWithSql(final String sql, final List<Object[]> list) {
		int[] rowsAffected = new int[list.size()];
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = getConnection();
			if (con == null) {
				return rowsAffected;
			}
			ps = con.prepareStatement(sql);
			if (ps == null) {
				return rowsAffected;
			}
			if (JdbcUtils.supportsBatchUpdates(con)) {
				for (int i = 0; i < list.size(); i++) {
					Object[] args = (Object[]) list.get(i);
					for (int j = 0; j < args.length; j++) {
						ps.setObject(j + 1, args[j]);
					}
					ps.addBatch();
				}

				rowsAffected = ps.executeBatch();
			} else {
				for (int i = 0; i < list.size(); i++) {
					Object[] args = (Object[]) list.get(i);
					for (int j = 0; j < args.length; j++) {
						ps.setObject(j + 1, args[j]);
					}
					if (!ps.execute(sql)) {
						rowsAffected[i] = ps.getUpdateCount();
					} else {
						log.info("DBDao:[DepartmentDailyWork]"
							+ "SQL statement error : " + sql + "。");
					}
				}
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {
			JdbcUtils.closeStatement(ps);
			JdbcUtils.closeConnection(con);
		}
		return rowsAffected;
	}


}
