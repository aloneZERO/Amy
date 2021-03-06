package com.leo.dbassistant.handler.impl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.leo.dbassistant.handler.ResultSetHandler;

/**
 * 类字段名和数据库字段名保持一致。
 * 仅适合结果集数据只有一条的情况。
 * 返回一个 JavaBean.
 *
 * @param <T> 目标 bean 的类型
 */
public class BeanHandler<T> implements ResultSetHandler<T> {
	/**
	 * 目标 bean 的类型
	 */
    private final Class<T> type;
	
	public BeanHandler(Class<T> type) {
		this.type = type;
	}
	
	/**
	 * 根据指定的目标 bean 类型，将结果集的第一行记录封装到一个 bean。
	 * 
	 * @param rs 被处理的结果集。
	 * @return 一个被初始化后的 JavaBean ；若结果集无记录则返回 null。
	 * 
	 * @throws SQLException 若数据库访问发生错误，则抛出该异常。
	 */
	@Override
	public T handle(ResultSet rs) throws SQLException {
		try{
			if(rs.next()) {
				T bean = type.newInstance();
				
				ResultSetMetaData rsmd = rs.getMetaData();
				int num = rsmd.getColumnCount(); // 字段数
				for(int i=0; i< num; i++) {
					String fieldName = rsmd.getColumnName(i+1);
					Object fieldValue = rs.getObject(i+1);
					// 反射字段
					Field field = type.getDeclaredField(fieldName); // 私有属性
					field.setAccessible(true); // 私有~强制访问
					field.set(bean, fieldValue);
				}
				return bean;
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new RuntimeException("数据封装失败..."+e.getMessage());
		}
	}

}
