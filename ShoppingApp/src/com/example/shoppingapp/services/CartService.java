package com.example.shoppingapp.services;

import java.util.List;

import com.example.shoppingapp.domain.CartEntry;
import com.example.shoppingapp.domain.Product;
import com.example.shoppingapp.domain.User;
import com.example.shoppingapp.exception.DataException;

public interface CartService {
    public void addProductInCart(User user, Product product, int quantityToCart) throws DataException;

    public void checkOut(User user) throws DataException;

    public void deleteProductInCart(User user, Product product) throws DataException;

    public void updateProductInCart(User user, Product product, int quantity) throws DataException;

    public CartEntry getCartEntry(User user, Product product) throws DataException;

    public List<CartEntry> getCartList(User user) throws DataException;

    public boolean isCartValidForCheckout(User user) throws DataException;

    public boolean isProductExistInCart(User user, Product product) throws DataException;

    public boolean isValidToAdd(User user, Product product, int quantityToAdd) throws DataException;
}

