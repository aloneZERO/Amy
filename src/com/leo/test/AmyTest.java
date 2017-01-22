package com.leo.test;

import java.util.List;

import org.junit.Test;

import com.leo.dbassistant.Amy;
import com.leo.dbassistant.handler.impl.BeanHandler;
import com.leo.dbassistant.handler.impl.BeanListHandler;
import com.leo.test.domain.Account;
import com.leo.util.DBCPUtil;

public class AmyTest {
	private Amy amy = new Amy(DBCPUtil.getDataSource());
	
	/*
	 * 两种操作模式：
	 * 1. 数据管理：增删改，传入 SQL 和相应的参数，
	 *    或直接传入完整 SQL。
	 * 2. 数据查询：传入 SQL 和相应的参数，
	 *    或直接传入完整 SQL，还需传入 ResultSetHandler-可根据需求自己实现，
	 *    这里提供了两种实现：BeanHanlder 和 ListBeanHanler
	 *    返回封装好的 JavaBean。
	 */
	
	@Test
	public void testAdd() {
		String sql = "insert into account(id,name,money) values(?,?,?)";
		amy.update(sql, 6,"XXX",1000);
	}
	@Test
	public void testMod() {
		String sql = "update account set money=money-100 where name=?";
		amy.update(sql, "ttt");
	}
	@Test
	public void testDel() {
		String sql = "delete from account where name=?";
		amy.update(sql, "ttt");
	}
	@Test
	public void testFindOne() {
		String sql = "select * from account where id=1";
		Account account = amy.query(sql,
				new BeanHandler<Account>(Account.class));
		System.out.println(account);
	}
	@Test
	public void testFindAll() {
		String sql = "select * from account";
		List<Account> list = (List<Account>) amy.query(sql,
				new BeanListHandler<Account>(Account.class));
		for (Object obj : list) {
			System.out.println(obj);
		}
	}
}
