# Amy
*JDBC 轻量级框架，一行代码完成 CRUD* !



## 编译项目

Amy 开发环境： Eclipse-neon-2 、JDK7.0+ 。

 项目导入 Eclipse ，导出 jar 包即完成编译构建，

或者直接下载提供编译好的 jar 包。



## 示例代码

``` java
public class AmyTest {
  	// Step.1 创建 amy 对象，注入自己的数据源
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
		String sql = "insert into t_account(id,name,money) values(?,?,?)";
		amy.update(sql, 6,"XXX",1000);
	}
	@Test
	public void testFindOne() {
		String sql = "select * from t_account where id=1";
		Account account = (Account) amy.query(sql, new BeanHandler(Account.class));
		System.out.println(account);
	}
}
```



## 测试依赖

测试请修改 dbcp 配置文件：[dbcpconfig.properties](https://github.com/alonezero/amy/src/dbcpconfig.properties)

详细的测试依赖请见：[test-readme.md](https://github.com/alonezero/amy/lib/README.md)



## 下载

点击下载：[amy-1.0.jar](https://raw.githubusercontent.com/aloneZERO/Amy/master/resource/amy-1.0.jar)
