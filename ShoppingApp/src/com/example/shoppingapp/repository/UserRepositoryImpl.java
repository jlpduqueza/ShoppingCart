package com.example.shoppingapp.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.sql.PreparedStatement;

import com.example.shoppingapp.domain.User;
import com.example.shoppingapp.exception.DataException;

public class UserRepositoryImpl implements UserRepository{
	
	private List<User> userList;
	
	@Override
	public List<User> getUserList() throws DataException {
		
		Connection conn = null;
		Statement s = null;
		ResultSet rs = null;
		
		try {
			DbConnection dbConnection = new DbConnectionImpl();
			conn = dbConnection.getConnection();
			String selectQuery = "SELECT username,password FROM users";
			
			s = conn.createStatement();
			
			rs = s.executeQuery(selectQuery);
			
			while (rs.next()){
				User user = new User();
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				userList.add(user);
		    }
			
		} catch (SQLException ex) {

			
		} finally {
		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (s != null) {
		        try {
		            s.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (conn != null) {
		        try {
		            conn.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		}
		
		
		return userList;
	}

	@Override
	public User getUser(String username) throws DataException {

		User user = new User();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			DbConnection dbConnection = new DbConnectionImpl();
			conn = dbConnection.getConnection();
			
			String selectQuery = "SELECT id,username,password,role FROM users WHERE username =?";
			
			ps =  conn.prepareStatement(selectQuery);
			ps.setString(1, username);
			
			rs = ps.executeQuery();
			
			while (rs.next()){
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getString("role"));
				return user;
		    }

		} catch (SQLException ex) {

			
		} finally {
		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (ps != null) {
		        try {
		            ps.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (conn != null) {
		        try {
		            conn.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		}
		
		return user;
	}

}
