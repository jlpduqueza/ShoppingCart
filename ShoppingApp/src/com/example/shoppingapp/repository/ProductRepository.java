package com.example.shoppingapp.repository;

import java.util.List;

import com.example.shoppingapp.domain.Product;
import com.example.shoppingapp.exception.DataException;

public interface ProductRepository {
    public Product getProduct(String productCode) throws DataException;

    public List<Product> getProductList() throws DataException;
}
