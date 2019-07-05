package com.example.shoppingapp.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.example.shoppingapp.domain.CartEntry;
import com.example.shoppingapp.domain.CartProductInventoryBean;
import com.example.shoppingapp.domain.InventoryEntry;
import com.example.shoppingapp.domain.Product;
import com.example.shoppingapp.domain.User;
import com.example.shoppingapp.exception.DataException;

public class CartProductInventoryServiceImpl implements CartProductInventoryService{
	
	private CartService cartService;
	private ProductService productService;
	private InventoryService inventoryService;
	
	public CartProductInventoryServiceImpl() {
		
		productService = new ProductServiceImpl();
		inventoryService = new InventoryServiceImpl();
		cartService = new CartServiceImpl();
	}
	@Override
	public List<CartProductInventoryBean> getBeanList(User user) throws DataException {

		List<CartProductInventoryBean> cartProductBeanList = new ArrayList<>();
		List<CartEntry> cartEntryList = cartService.getCartList(user);
		
		for(CartEntry cartEntry : cartEntryList) {
			
			CartProductInventoryBean cartProductBean = new CartProductInventoryBean();
			
			if(cartProductBean.getTotalPrice() == null) {
				cartProductBean.setTotalPrice(new BigDecimal(0));
			}
			
			Product product = productService.getProduct(cartEntry.getProduct().getProductCode());
			InventoryEntry inventory = inventoryService.getInventory(product.getProductCode());
			
			cartProductBean.setProduct(product);
			cartProductBean.setInventoryEntry(inventory);
			cartProductBean.setCartEntry(cartEntry);
			cartProductBean.setTotalPrice(getTotalPrice(user));
			
			cartProductBeanList.add(cartProductBean);
			
		}
		
		return cartProductBeanList;
		
	}
	@Override
	public BigDecimal getTotalPrice(User user) throws DataException {
		
		List<CartEntry> cartEntryList = cartService.getCartList(user);
		
		BigDecimal totalPrice = new BigDecimal(0);

		for(CartEntry cartEntry : cartEntryList) {
			
			if(cartEntry.getProduct().getPrice() != null) {
				
				Product product = cartEntry.getProduct();
				BigDecimal price = productService.getProduct(product.getProductCode()).getPrice();
				BigDecimal quantity = new BigDecimal(cartEntry.getQuantityInCart());
				totalPrice = totalPrice.add(quantity.multiply(price));
			}
		}

		return totalPrice;
		
	}

}
