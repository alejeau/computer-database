package com.excilys.formation.cdb.mapper;

import java.sql.Connection;
import java.sql.SQLException;

import com.excilys.formation.cdb.persistance.ConnectionManager;

public class ComputerMapper {
	public static void main(String[] args) {
		ConnectionManager cm = ConnectionManager.INSTANCE;
		Connection c = cm.getConnection();
		try {
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
