package com.example.shoppingapp.services;

import java.util.List;

import com.example.shoppingapp.domain.Product;
import com.example.shoppingapp.exception.DataException;

public interface ProductService {
    public Product getProduct(String productCode) throws DataException;

    public List<Product> getProductList() throws DataException;
}
