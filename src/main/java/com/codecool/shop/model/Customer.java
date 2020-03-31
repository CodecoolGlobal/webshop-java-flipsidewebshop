package com.codecool.shop.model;

import java.util.List;

public class Customer {
    private Cart cart;
    private Cart instance = null;

    public Customer(){
        this.cart = getCartInstance();
    }

    public Cart getCartInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    public List<Item> getcartItems() {
        return cart.getShoppingCart();
    }

    public boolean updateCart(Product product, int quantity){
        return cart.update(product, quantity);
    }

    public void emptyCart(){
        cart.emptyCart();
    }

    public void removeItemFromCart(Product product){
        cart.removeItem(product);
    }
}
