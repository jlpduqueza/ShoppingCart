package com.example.shoppingapp.services;

import java.util.List;

import com.example.shoppingapp.domain.User;
import com.example.shoppingapp.exception.DataException;

public interface UserService {
	
	public List<User> getUserList() throws  DataException;
	public User getUser(String username) throws DataException;
	public boolean isAdmin(User user) throws  DataException;
	public boolean isUser(User user) throws  DataException;

}
