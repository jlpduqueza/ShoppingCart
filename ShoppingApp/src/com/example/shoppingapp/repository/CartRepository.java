package com.example.shoppingapp.repository;

import java.util.List;

import com.example.shoppingapp.domain.CartEntry;
import com.example.shoppingapp.domain.Product;
import com.example.shoppingapp.domain.User;
import com.example.shoppingapp.exception.DataException;

public interface CartRepository {
	
	public List<CartEntry> getCartList(User user) throws  DataException;
	public CartEntry getCartEntry(User user, Product product) throws  DataException;
	
	public void addProductInCart(User user, Product product, int quantity , boolean duplicateFlag) throws  DataException;
	public void deleteProductInCart(User user, Product product) throws  DataException;
	public void updateProductInCart(User user, Product product, int quantity) throws  DataException;
	public void checkOut(User user) throws  DataException;
	public boolean isProductExist(Product product, User user) throws  DataException;

}
