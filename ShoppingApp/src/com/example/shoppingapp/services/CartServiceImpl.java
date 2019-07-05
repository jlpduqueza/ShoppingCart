package com.example.shoppingapp.services;

import java.util.List;

import com.example.shoppingapp.domain.CartEntry;
import com.example.shoppingapp.domain.InventoryEntry;
import com.example.shoppingapp.domain.Product;
import com.example.shoppingapp.domain.User;
import com.example.shoppingapp.exception.DataException;
import com.example.shoppingapp.repository.CartRepository;
import com.example.shoppingapp.repository.CartRepositoryImpl;


public class CartServiceImpl implements CartService{
	
	private CartRepository cartRepository ;
	private InventoryService inventoryService;
	
	public CartServiceImpl() {
		
		cartRepository = new CartRepositoryImpl();
		inventoryService = new InventoryServiceImpl();
	}
	
	@Override
	public List<CartEntry> getCartList(User user) throws DataException {
		
		return cartRepository.getCartList(user);
	}
	@Override
	public void addProductInCart(User user, Product product, int quantityFromCart) 
			throws DataException {

		cartRepository.getCartList(user);
		boolean duplicateFlag = isProductExistInCart(user, product);
		
		cartRepository.addProductInCart(user, product, quantityFromCart, duplicateFlag);	
	}
	@Override
	public void updateProductInCart(User user, Product product, int quantity) throws DataException {

		cartRepository.updateProductInCart(user, product, quantity);
	}
	@Override
	public void checkOut(User user) throws DataException {

		cartRepository.checkOut(user);
	}
	
	@Override
	public void deleteProductInCart(User user, Product product) throws DataException {

		cartRepository.deleteProductInCart(user, product);
	}
	@Override
	public boolean isProductExistInCart(User user, Product product) throws DataException {

		return cartRepository.isProductExist(product, user);
	}

	@Override
	public CartEntry getCartEntry(User user, Product product) throws DataException {

		System.out.println(product);
		System.out.println(user);
		System.out.println(cartRepository.getCartEntry(user, product));
		return cartRepository.getCartEntry(user, product);
	}

	@Override
	public boolean isValidToAdd(User user,Product product,  int quantityToAdd)
			throws DataException {
		
		InventoryEntry inventoryEntry = inventoryService.getInventory(product.getProductCode());
		int totalQuantityFromCart = getCartEntry(user, product).getQuantityInCart()+quantityToAdd;
		int actualQuantityFromInventory = inventoryEntry.getQuantity();
		
		if(totalQuantityFromCart <= actualQuantityFromInventory) {
			
			return true;
		}
		
		return false;
	}

	@Override
	public boolean isCartValidForCheckout(User user) throws DataException {
		
		for(CartEntry cartEntry : getCartList(user)) {
			
			int cartEntryQuantity = cartEntry.getQuantityInCart();
			Product product = cartEntry.getProduct();
			
			int actualInventory = inventoryService.getInventory(product.getProductCode()).getQuantity();
			
			if(actualInventory < cartEntryQuantity) {
				return false;
			}
		}
		return true;
	}


	

	

}
