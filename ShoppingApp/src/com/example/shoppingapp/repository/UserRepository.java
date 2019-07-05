package com.example.shoppingapp.repository;

import java.util.List;

import com.example.shoppingapp.domain.User;
import com.example.shoppingapp.exception.DataException;


public interface UserRepository {
	
	public List<User> getUserList() throws  DataException ;
	public User getUser(String username) throws  DataException;
	
}
