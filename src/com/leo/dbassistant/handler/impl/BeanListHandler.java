package com.leo.dbassistant.handler.impl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.leo.dbassistant.handler.ResultSetHandler;

/**
 * 类字段名和数据库字段名保持一致。
 * 适合结果集数据多条的情况。返回 list.
 * 
 * @param <T> 目标 bean 的类型。
 */
public class BeanListHandler<T> implements ResultSetHandler<List<T>> {
	
	/**
	 * 目标 bean 的类型
	 */
    private final Class<T> type;
	
	public BeanListHandler(Class<T> type) {
		this.type = type;
	}
	
	/**
	 * 根据指定的目标 bean 类型，将结果集的每条记录封装到一个 bean，
	 * 将每个 bean 放到 List 集合中返回。
	 * 
	 * @param rs 被处理的结果集。
	 * @return 一个 bean list 集合。
	 * 
	 * @throws SQLException 若数据库访问发生错误，则抛出该异常。
	 */
	@Override
	public List<T> handle(ResultSet rs) throws SQLException {
		try{
			List<T> beanList = new ArrayList<>();
			while(rs.next()) {
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
				beanList.add(bean);
			}
			return beanList;
		} catch (Exception e) {
			throw new RuntimeException("数据封装失败..."+e.getMessage());
		}
	}

}
