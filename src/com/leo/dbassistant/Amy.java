package com.leo.dbassistant;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.leo.dbassistant.handler.ResultSetHandler;

public class Amy {
	
	private DataSource dataSource;
	
	/**
	 * 构造一个新的 Amy 实例。
	 * 
	 * @param dataSource 接收使用者提供的数据源。
	 */
	public Amy(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * 执行DML语句：INSERT,UPDATE,DELETE.
	 * 
	 * @param sql 进行数据库交互的 SQL 语句。
	 * @param params SQL 语句的参数：0 个或 多个。
	 * @return DML 语句影响的数据库记录行数。
	 *          
	 * @throws RuntimeException 若运行过程发生错误，则抛出该异常。
	 */
	public int update(String sql, Object...params) throws RuntimeException {
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
					throw new IllegalArgumentException("SQL 语句缺少参数！");
				if(num != params.length)
					throw new IllegalArgumentException("SQL 语句参数个数不匹配！");
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
	 * 
	 * @param sql 进行数据库交互的 SQL 语句。
	 * @param handler 适合自己处理需求的结果集处理器。
	 * @param params SQL 语句的参数：0 个或 多个。
	 * @return 结果集处理器的结果。
	 * 
	 * @throws RuntimeException 若运行过程发生错误，则抛出该异常。
	 */
	public <T> T query(String sql, ResultSetHandler<T> handler, Object...params)
		throws RuntimeException
	{
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
					throw new IllegalArgumentException("SQL 语句缺少参数！");
				if(num != params.length)
					throw new IllegalArgumentException("SQL 语句参数个数不匹配！");
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
	
	/**
	 * 释放资源。
	 * 
	 * @param rs 目标结果集。
	 * @param stmt SQL 对象。
	 * @param conn 数据库连接。
	 */
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
				conn.close(); // 即将连接归还连接池
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
