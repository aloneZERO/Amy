package com.leo.dbassistant.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 实现此接口来完成结果集数据到对象的封装。
 * 
 * @param <T> 传入的结果集将被转换为的目标对象类型。
 */
public interface ResultSetHandler<T> {
	
	/**
	 * 将结果集数据封装为对象。
	 * 
	 * @param rs 将被操作的结果集。
	 * @return 结果集数封装好的对象；若结果集没有数据将返回 null。
	 * 
	 * @throws SQLException 若数据库访问发生错误，将抛出该异常。
	 */
	T handle(ResultSet rs) throws SQLException;
	
}
