
package com.wanda3.service.pool;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 数据库连接池
 * 
 * @author liyi
 *
 */
public class PoolManager {
	 private final static Logger LOGGER = LoggerFactory.getLogger(PoolManager.class);
	static ResourceBundle bundle = PropertyResourceBundle.getBundle("init");
	private static String driver = bundle.getString("datasource.driverClassName");// 驱动
	private static String url = bundle.getString("datasource.url");// URL
	private static String Name = bundle.getString("datasource.username");// 用户名
	private static String Password = bundle.getString("datasource.password"); // 密码
	private static Class driverClass = null;
	private static ObjectPool connectionPool = null;
	public PoolManager() {}
	/**
	 * 初始化数据源
	 */
	private static synchronized void initDataSource() {
		if (driverClass == null) {
			try {
				driverClass = Class.forName(driver);
				LOGGER.info("mysql装载驱动成功");
			} catch (ClassNotFoundException e) {
				LOGGER.info("mysql装载驱动失败"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	/**
	 * 连接池启动
	 * 
	 * @throws Exception
	 */
	public static void StartPool() {
		// loadProperties();
		initDataSource();
		if (connectionPool != null) {
			ShutdownPool();
		}
		try {
			connectionPool = new GenericObjectPool(null);
			ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
					url, Name, Password);
			PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(
					connectionFactory, connectionPool, null, null, false, true);
			Class.forName("org.apache.commons.dbcp.PoolingDriver");

			PoolingDriver driver = (PoolingDriver) DriverManager
					.getDriver("jdbc:apache:commons:dbcp:");
			driver.registerPool("dbpool", connectionPool);
			// ObjectPool pool = driver.getConnectionPool("dbpool");
			LOGGER.info("数据库连接池成功打开");
		} catch (Exception e) {
			LOGGER.info("数据库连接池打开失败");
			e.printStackTrace();
		}
	}

	/**
	 * 释放连接池
	 */
	public static void ShutdownPool() {
		try {
			PoolingDriver driver = (PoolingDriver) DriverManager
					.getDriver("jdbc:apache:commons:dbcp:");
			driver.closePool("dbpool");
			LOGGER.info("数据库连接池释放连接");
		} catch (SQLException e) {
			LOGGER.info("数据库连接池释放连接失败");
			e.printStackTrace();
		}
	}

	/**
	 * 取得连接池中的连接
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = null;
		if (connectionPool == null)
			StartPool();
		try {
			conn = DriverManager
					.getConnection("jdbc:apache:commons:dbcp:dbpool");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 获取连接 getConnection
	 * @param name
	 * @return
	 */
	public static Connection getConnection(String name) {
		return getConnection();
	}

	/**
	 * 释放连接 freeConnection
	 * @param conn
	 */
	public static void freeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 释放连接 freeConnection
	 * 
	 * @param name
	 * @param con
	 */
	public static void freeConnection(String name, Connection con) {
		freeConnection(con);
	}

	public static Statement getStatement() throws SQLException {
		Connection conn = PoolManager.getConnection();
		if (conn != null)
			return conn.createStatement();
		return null;
	}

	
	public static void getCallableStatement(String sql) throws SQLException{
		Connection conn = PoolManager.getConnection();
		CallableStatement c=conn.prepareCall(sql);
		c.execute();
		freeConnection(conn);
	}
	public static PreparedStatement getPreStatement(String sql)
			throws SQLException {
		Connection conn = PoolManager.getConnection();
		if (conn != null)
			return conn.prepareStatement(sql);
		return null;
	}

}
