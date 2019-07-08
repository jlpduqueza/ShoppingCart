package com.example.shoppingapp.domain;

import java.math.BigDecimal;

public class CartProductInventoryBean {
    private Product product;
    private CartEntry cartEntry;
    private InventoryEntry inventoryEntry;
    private BigDecimal totalPrice;

    public CartEntry getCartEntry() {
        return cartEntry;
    }

    public void setCartEntry(CartEntry cartEntry) {
        this.cartEntry = cartEntry;
    }

    public InventoryEntry getInventoryEntry() {
        return inventoryEntry;
    }

    public void setInventoryEntry(InventoryEntry inventoryEntry) {
        this.inventoryEntry = inventoryEntry;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
