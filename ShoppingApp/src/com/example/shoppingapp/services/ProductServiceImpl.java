package com.example.shoppingapp.services;

import java.util.List;

import com.example.shoppingapp.domain.Product;
import com.example.shoppingapp.exception.DataException;
import com.example.shoppingapp.repository.ProductRepository;
import com.example.shoppingapp.repository.ProductRepositoryImpl;

public class ProductServiceImpl implements ProductService{
	
	private ProductRepository productRepository;
	
	public ProductServiceImpl() {

		productRepository = new ProductRepositoryImpl();
	}

	@Override
	public List<Product> getProductList() throws  DataException {
		
		return productRepository.getProductList();
	}
	@Override
	public Product getProduct(String productCode) throws  DataException {

		return productRepository.getProduct(productCode);
		
	}

}
