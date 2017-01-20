package com.leo.dbassistant.handler.impl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import com.leo.dbassistant.handler.ResultSetHandler;

/*
 * 类字段名和数据库字段名保持一致。
 * 适合结果集数据多条的情况。返回 list.
 */
public class ListBeanHandler implements ResultSetHandler {
	
	private Class<?> clazz; // 目标类型
	
	public ListBeanHandler(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public Object handle(ResultSet rs) {
		try{
			List<Object> beanList = new ArrayList<>();
			while(rs.next()) {
				Object bean = clazz.newInstance();
				
				ResultSetMetaData rsmd = rs.getMetaData();
				int num = rsmd.getColumnCount(); // 列数
				for(int i=0; i< num; i++) {
					String fieldName = rsmd.getColumnName(i+1);
					Object fieldValue = rs.getObject(i+1);
					// 反射字段
					Field field = clazz.getDeclaredField(fieldName); // 私有属性
					field.setAccessible(true); // 私有~哥强制访问
					field.set(bean, fieldValue);
				}
				beanList.add(bean);
			}
			return beanList;
		} catch (Exception e) {
			throw new RuntimeException("数据封装失败...");
		}
	}

}
