package com.lnsoft.device.api.safeaccess.service.impl;

import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: DBDao
 * @description:
 * @author: zhangs
 * @create: 2024-03-07 15:33
 **/
@Service
@Slf4j
public class DBDao {

	@Resource
	private DBManager dbManager;


	public List queryForListWithSql(String sql, Object[] args, IdevelopUser sysUser) {
		//获取地市编码
//		String orgNo = Common.getCompCode4();
		//userUtils.getCompCode4();
		String orgNo = getCompCode4(sysUser);

		ResultSet rs = null;
		List res = null;
		PreparedStatement ps = null;
		Connection connection = null;

		RowMapper rowMapper = new ColumnMapRowMapper();
		ResultSetExtractor rse = new RowMapperResultSetExtractor(rowMapper);

		try {
			connection = dbManager.getConnection(orgNo);
			if (connection == null) {
				return null;
			}
			ps = connection.prepareStatement(sql);
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
			rs = ps.executeQuery();
			log.info("DBDao:[" + orgNo + "]" + sql);
			res = (List) rse.extractData(rs);
		} catch (SQLException e) {
			log.warn(e.getMessage());
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(ps);
			JdbcUtils.closeConnection(connection);
			StatementCreatorUtils.cleanupParameters(args);
		}
		return res;
	}

	public int updateWithSql(final String sql, IdevelopUser sysUser) {
//		String orgNo = Common.getCompCode4();
		//userUtils.getCompCode4();
		String orgNo = getCompCode4(sysUser);

		Connection con = null;
		Statement statement = null;
		int rows = -1;
		try {
			con = dbManager.getConnection(orgNo);
			if (con == null) {
				return -1;
			}
			statement = con.createStatement();
			rows = statement.executeUpdate(sql);
//			log.info("DBDao:["+orgNo+"]" + sql);
//			if (log.isDebugEnabled()) {
//			  log.debug("SQL statement affect record number： " + rows + ".");
//			}
		} catch (SQLException e) {
			log.warn(e.getMessage());
		} finally {
			JdbcUtils.closeStatement(statement);
			JdbcUtils.closeConnection(con);
		}
		return rows;
	}

	public int updateWithSql(final String sql, final Object[] args, IdevelopUser sysUser) {
//		String orgNo = Common.getCompCode4();
		//userUtils.getCompCode4();
		String orgNo = getCompCode4(sysUser);
		PreparedStatement ps = null;
		Connection con = null;
		int rows = -1;
		try {
			con = dbManager.getConnection(orgNo);
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

	public int updateWithSql(String orgNo, final String sql, final Object[] args) {
		PreparedStatement ps = null;
		Connection con = null;
		int rows = -1;
		try {
			con = dbManager.getConnection(orgNo);
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
			log.info("DBDao:[" + orgNo + "]" + sql);
			if (log.isDebugEnabled()) {
				log.debug("Executing SQL statements [" + sql + "]。");
			}
		} catch (SQLException e) {
			log.warn(e.getMessage());
			;
		} finally {
			JdbcUtils.closeStatement(ps);
			JdbcUtils.closeConnection(con);
			StatementCreatorUtils.cleanupParameters(args);
		}

		return rows;
	}

	public List executeSqlQuery(final String sql, IdevelopUser sysUser) {
		List oldList = queryForListWithSql(sql, null, sysUser);
		if (oldList == null) {
			return null;
		}
		List newList = new ArrayList<List>();
		Map rowMap;
		List rowList;
		for (int i = 0; i < oldList.size(); i++) {
			rowList = new ArrayList();
			rowMap = (Map) oldList.get(i);
			Iterator iterator = rowMap.keySet().iterator();
			while (iterator.hasNext()) {
				rowList.add(rowMap.get(iterator.next().toString()));
			}
			newList.add(rowList);
		}
		return newList;
	}

	public int executeSqls(final List<String> sqls, IdevelopUser sysUser) {
		String orgNo = getCompCode4(sysUser);//userUtils.getCompCode4();
		Connection con = null;
		Statement statement = null;
		int rows = 0;
		try {
			con = dbManager.getConnection(orgNo);
			if (con == null) {
				return -1;
			}
			statement = con.createStatement();
			for (String sql : sqls) {
				int row = statement.executeUpdate(sql);
				if (row != -1) {
					rows++;
				}
//				log.info("DBDao:["+orgNo+"]" + sql);
				if (log.isDebugEnabled()) {
					log.debug("SQL statement affect record number： " + rows + ".");
				}
			}
		} catch (SQLException e) {
			log.warn(e.getMessage());
			;
		} finally {
			JdbcUtils.closeStatement(statement);
			JdbcUtils.closeConnection(con);
		}
		log.info("DBDao:[" + orgNo + "]共" + sqls.size() + "条记录，插入成功" + rows + "条记录。");
		return rows;
	}

	public String getCompCode4(IdevelopUser sysUser) {
		log.info("当前操作用户信息：{}", sysUser.getUserName());
		String orgNo = sysUser.getRegionCode();
		log.info("获取区域编码：{}", orgNo);
		return orgNo.length() >= 4 ? orgNo.substring(0, 4) : orgNo;
	}

	public void loginfo(String info) {

		String orgNo = getCompCode4(SecureUtil.getUser());
		log.info("DBDao:[" + orgNo + "]" + info);
	}
}
