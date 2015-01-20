package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnPool {

	private String driver;
	
	private String url;
	
	private String username;
	
	private String password;
	
	private int size;
	
	private Stack<Connection> pool = new Stack<Connection>();

	public ConnPool(String driver, String url, String username,
			String password, int size) {
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
		this.size = size;
		try {
			initPool();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initPool() throws Exception {
		Class.forName(driver);
		for (int i=0; i<size; i++) {
			pool.add(DriverManager.getConnection(url, username, password));
		}
	}
	
	public synchronized Connection getConnection() {
		return pool.pop();
	}
	
	public synchronized void releaseConnection(Connection conn) {
		pool.push(conn);
	}
	
}
