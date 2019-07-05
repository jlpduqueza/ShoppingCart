package com.example.shoppingapp.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.example.shoppingapp.exception.DataException;

public class DbConnectionImpl implements DbConnection{
	
	static Connection connection = null;
	static String databaseName = "shopping_app";
	static String url = "jdbc:mysql://localhost:3306/" + databaseName;
	
	static String username = "root";
	static String password = "";
	
	@Override
	public Connection getConnection() throws DataException {
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(url, username, password);
			
		} catch (SQLException e) {
	
			e.printStackTrace();
		} catch (InstantiationException e) {
	
			e.printStackTrace();
		} catch (IllegalAccessException e) {
	
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
	
			e.printStackTrace();
		}
	
		return connection;
	}
}


