package com.leo.dbassistant.handler;

import java.sql.ResultSet;

public interface ResultSetHandler {
	
	/**
	 * 将结果集数据封装到对象
	 * @param rs
	 * @return 封装好的对象
	 */
	Object handle(ResultSet rs);
	
}
