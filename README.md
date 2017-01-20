# LeoDB
*JDBC 轻量级框架，一行代码完成 CRUD*

## 项目编译

LeoDB 使用 Eclipse-neon-2 开发，项目导入 Eclipse ，导出 jar 包即完成编译构建。

或者直接下载提供编译好的 jar 包。



## 示例代码

``` java
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
		dba.update(sql, 6,"XXX",1000);
	}
	@Test
	public void testFindOne() {
		String sql = "select * from t_account where id=1";
		Account account = (Account) dba.query(sql, new BeanHandler(Account.class));
		System.out.println(account);
	}
}
```



## 测试依赖

单元测试建议使用 JUNIT4，此处测试依赖:

- mysql-connector-java-5.1.40


- apache-dbcp2

测试请修改 dbcp 配置文件：[dbcpconfig.properties](#)

使用其它数据源测试，请自行配置。



## 下载

点击下载：[leodb.jar](#)