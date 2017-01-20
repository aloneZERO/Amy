package com.leo.test;

import java.util.List;

import org.junit.Test;

import com.leo.dbassistant.DbAssistant;
import com.leo.dbassistant.handler.impl.BeanHandler;
import com.leo.dbassistant.handler.impl.ListBeanHandler;
import com.leo.domain.Account;
import com.leo.util.DBCPUtil;

public class DBAssistantTest {
	private DbAssistant dba = new DbAssistant(DBCPUtil.getDataSource());
	
	@Test
	public void testAdd() {
		String sql = "insert into t_account(id,name,money) values(?,?,?)";
		dba.update(sql, 6,"ttt",1000);
	}
	@Test
	public void testMod() {
		String sql = "update t_account set money=money-100 where name=?";
		dba.update(sql, "ttt");
	}
	@Test
	public void testDel() {
		String sql = "delete from t_account where name=?";
		dba.update(sql, "ttt");
	}
	@Test
	public void testFindOne() {
		String sql = "select * from t_account where id=1";
		Account account = (Account) dba.query(sql, new BeanHandler(Account.class));
		System.out.println(account);
	}
	@Test
	public void testFindAll() {
		String sql = "select * from t_account";
		@SuppressWarnings("unchecked")
		List<Object> list = (List<Object>) dba.query(sql, new ListBeanHandler(Account.class));
		for (Object obj : list) {
			System.out.println(obj);
		}
	}
}
