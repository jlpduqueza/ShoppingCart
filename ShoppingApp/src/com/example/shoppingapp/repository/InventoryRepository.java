package com.example.shoppingapp.repository;

import java.util.List;

import com.example.shoppingapp.domain.InventoryEntry;
import com.example.shoppingapp.domain.Product;
import com.example.shoppingapp.exception.DataException;

public interface InventoryRepository {
    public void addProductQuantity(Product product, int quantity) throws DataException;

    public void updateProductQuantity(Product product, int quantity) throws DataException;

    public InventoryEntry getInventoryEntry(String productCode) throws DataException;

    public List<InventoryEntry> getInventoryList() throws DataException;
}
