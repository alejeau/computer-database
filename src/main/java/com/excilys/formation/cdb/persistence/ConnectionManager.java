package com.excilys.formation.cdb.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public enum ConnectionManager {
	INSTANCE;
	
	private Properties props = new Properties();
	private FileInputStream in;

    private String url;
    private String username;
    private String password;
	
	{
		try {
			in = new FileInputStream("config/db/db.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			props.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ConnectionManager() {
		String driver = props.getProperty("jdbc.driver");
	    try {
	      Class.forName(driver);       
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    
		url = props.getProperty("jdbc.url");
		username = props.getProperty("jdbc.username");
		password = props.getProperty("jdbc.password");
	}
	
	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void closeElements(Connection conn, Statement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
