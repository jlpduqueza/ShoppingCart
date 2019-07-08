package com.example.shoppingapp.services;

import java.math.BigDecimal;

import java.util.List;

import com.example.shoppingapp.domain.CartProductInventoryBean;
import com.example.shoppingapp.domain.User;
import com.example.shoppingapp.exception.DataException;

public interface CartProductInventoryService {
    public List<CartProductInventoryBean> getBeanList(User user) throws DataException;

    public BigDecimal getTotalPrice(User user) throws DataException;
}
