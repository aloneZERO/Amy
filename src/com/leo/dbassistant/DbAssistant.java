package com.leo.dbassistant;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.leo.dbassistant.handler.ResultSetHandler;

public class DbAssistant {
	private DataSource dataSource;
	
	public DbAssistant(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * 执行DML语句：INSERT,UPDATE,DELETE
	 * @param sql
	 * @param params
	 * @return either (1) the row count for SQL Data Manipulation Language (DML) 
	 *          statements or (2) 0 for SQL statements that return nothing
	 */
	public int update(String sql, Object...params) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sql);
			// 参数检查
			ParameterMetaData pmd = stmt.getParameterMetaData();
			int num = pmd.getParameterCount();
			if(num > 0) {
				if(params == null)
					throw new IllegalArgumentException("大哥，缺少 SQL 参数！");
				if(num != params.length)
					throw new IllegalArgumentException("SQL 参数个数对不上啊？");
				for(int i=0; i< num; i++)
					stmt.setObject(i+1, params[i]);
			}
			return stmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			release(rs, stmt, conn);
		}
	}
	
	/**
	 * 执行查询：SELECT
	 * @param sql
	 * @param handler
	 * @param params
	 * @return 结果集处理器的结果
	 */
	public Object query(String sql, ResultSetHandler handler, Object...params) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sql);
			// 参数检查
			ParameterMetaData pmd = stmt.getParameterMetaData();
			int num = pmd.getParameterCount();
			if(num > 0) {
				if(params == null)
					throw new IllegalArgumentException("大哥，缺少 SQL 参数！");
				if(num != params.length)
					throw new IllegalArgumentException("SQL 参数个数对不上啊？");
				for(int i=0; i< num; i++)
					stmt.setObject(i+1, params[i]);
			}
			rs = stmt.executeQuery();
			return handler.handle(rs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			release(rs, stmt, conn);
		}
	}
	
	private void release(ResultSet rs, Statement stmt, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			stmt = null;
		}
		if (conn != null) {
			try {
				conn.close(); // 即连接归还连接池
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
