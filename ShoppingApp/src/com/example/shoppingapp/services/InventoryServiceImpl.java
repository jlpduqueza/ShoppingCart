package com.example.shoppingapp.services;

import java.util.ArrayList;
import java.util.List;

import com.example.shoppingapp.domain.InventoryEntry;
import com.example.shoppingapp.domain.Product;
import com.example.shoppingapp.exception.DataException;
import com.example.shoppingapp.repository.InventoryRepository;
import com.example.shoppingapp.repository.InventoryRepositoryImpl;

public class InventoryServiceImpl implements InventoryService {

	private InventoryRepository inventoryEntryRepository;
	
	public InventoryServiceImpl() {
		
		inventoryEntryRepository = new InventoryRepositoryImpl();
	}
	@Override
	public List<InventoryEntry> getInventoryList() throws  DataException {
		
		return inventoryEntryRepository.getInventoryList();
	}
	@Override
	public List<InventoryEntry> getInventoryListForUser() throws  DataException {

		List<InventoryEntry> inventoryList = inventoryEntryRepository.getInventoryList();
		List<InventoryEntry> inventoryListForUser = new ArrayList<InventoryEntry>();
		
		for(InventoryEntry inventoryEntry : inventoryList) {
			
			if(hasStock(inventoryEntry.getProduct())) {
				inventoryListForUser.add(inventoryEntry);
			}
		}
		
		
		return inventoryListForUser;
	}

	@Override
	public InventoryEntry getInventory(String productCode) throws  DataException {

		return inventoryEntryRepository.getInventoryEntry(productCode);
	}
	@Override
	public void updateProductQuantity(Product product, int quantity) throws  DataException {

		inventoryEntryRepository.updateProductQuantity(product, quantity);	
	}
	
	@Override
	public boolean hasStock(Product product) throws  DataException {
		
		InventoryEntry inventoryEntry = getInventory(product.getProductCode());
		
		if(inventoryEntry.getQuantity() <= 0) {
			
			return false;
		}
		return true;
	}
	@Override
	public boolean isStockSufficient(Product product, int cartEntryQuantity) throws  DataException {
		
		if(getInventory(product.getProductCode()).getQuantity() < cartEntryQuantity) {
			
			return false;
		}
		
		return true;
	}
	@Override
	public void addProductQuantity(Product product, int quantity) throws  DataException {

		inventoryEntryRepository.addProductQuantity(product, quantity);	
	}
	

}
