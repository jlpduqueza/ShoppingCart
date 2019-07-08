package com.example.shoppingapp.domain;

public class CartEntry {
    private String username;
    private Product product;
    private int quantityInCart;

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantityInCart() {
        return quantityInCart;
    }

    public void setQuantityInCart(int quantityInCart) {
        this.quantityInCart = quantityInCart;
    }
}
