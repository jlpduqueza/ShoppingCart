package com.example.shoppingapp.services;

import java.util.List;

import com.example.shoppingapp.domain.InventoryEntry;
import com.example.shoppingapp.domain.Product;
import com.example.shoppingapp.exception.DataException;

public interface InventoryService {
	
	public List<InventoryEntry> getInventoryList() throws DataException;
	public List<InventoryEntry> getInventoryListForUser() throws DataException;
	public InventoryEntry getInventory(String productCode) throws DataException;
	
	public void updateProductQuantity(Product product, int quantity) throws DataException;
	public void addProductQuantity(Product product, int quantity)throws DataException;
	
	public boolean isStockSufficient(Product product, int cartEntryQuantity) throws DataException;
	public boolean hasStock(Product product) throws DataException;
	
	
}
